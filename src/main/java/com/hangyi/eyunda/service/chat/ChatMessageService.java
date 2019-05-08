package com.hangyi.eyunda.service.chat;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydChatMessageDao;
import com.hangyi.eyunda.dao.YydChatMsgStatusDao;
import com.hangyi.eyunda.dao.YydChatRoomDao;
import com.hangyi.eyunda.dao.YydChatRoomStatusDao;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.chat.ChatMessageData;
import com.hangyi.eyunda.domain.YydChatMessage;
import com.hangyi.eyunda.domain.YydChatMsgStatus;
import com.hangyi.eyunda.domain.YydChatRoom;
import com.hangyi.eyunda.domain.YydChatRoomStatus;
import com.hangyi.eyunda.domain.enumeric.ImgTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReadStatusCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MultipartUtil;
import com.hangyi.eyunda.util.OSUtil;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.FFMPEGLocator;
import it.sauronsoftware.jave.InputFormatException;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ChatMessageService extends BaseService<YydChatMessage, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final int CHAT_PAGESIZE = 20;

	@Autowired
	private UserService userService;

	@Autowired
	private YydChatRoomDao chatRoomDao;
	@Autowired
	private YydChatRoomStatusDao chatRoomStatusDao;
	@Autowired
	private YydChatMessageDao chatMessageDao;
	@Autowired
	private YydChatMsgStatusDao chatMsgStatusDao;

	@Override
	public PageHibernateDao<YydChatMessage, Long> getDao() {
		return (PageHibernateDao<YydChatMessage, Long>) chatMessageDao;
	}

	public List<ChatMessageData> getPageChatMsgDatas(int pageSize, int pageNo, Long roomId, Long userId)
			throws Exception {
		List<ChatMessageData> chatMsgsDatas = new ArrayList<ChatMessageData>();

		Page<YydChatMessage> page = chatMessageDao.getPageByRoomId(pageSize, pageNo, roomId, userId);

		for (YydChatMessage chatMsg : page.getResult()) {
			YydChatMsgStatus status = chatMsgStatusDao.getByMsgIdAndUserId(chatMsg.getId(), userId);
			if (status.getReadStatus() == ReadStatusCode.noread) {
				status.setReadStatus(ReadStatusCode.read);
				chatMsgStatusDao.save(status);
			}
			chatMsgsDatas.add(this.getChatMessageData(chatMsg));
		}
		return chatMsgsDatas;
	}

	public ChatMessageData getChatMessageData(YydChatMessage chatMsg) throws Exception {
		ChatMessageData chatMsgsData = new ChatMessageData();

		if (chatMsg != null) {
			CopyUtil.copyProperties(chatMsgsData, chatMsg);

			UserData sender = userService.getUserData(chatMsg.getSenderId());
			chatMsgsData.setSenderName(sender.getTrueName());
			chatMsgsData.setSenderLogo(sender.getUserLogo());

			chatMsgsData.setCreateTime(CalendarUtil.toHumanFormat(chatMsg.getCreateTime()));

			return chatMsgsData;
		}
		return null;
	}

	// 我向聊天室发送一条消息
	public Long saveMessage(Long roomId, Long userId, String content) throws Exception {
		YydChatRoom room = chatRoomDao.get(roomId);
		if (room == null)
			throw new Exception("聊天室不存在，无法发送消息！");

		// 插入聊天记录
		YydChatMessage message = new YydChatMessage();

		message.setRoomId(roomId);
		message.setContent(content.replaceAll("[\\n\\r]", "<br/>"));
		message.setSenderId(userId);
		message.setCreateTime(Calendar.getInstance());

		chatMessageDao.save(message);

		// 插入聊天状态
		List<YydChatRoomStatus> roomStatuss = chatRoomStatusDao.getByRoomId(roomId);
		for (YydChatRoomStatus roomStatus : roomStatuss) {
			YydChatMsgStatus msgStatus = new YydChatMsgStatus();

			msgStatus.setRoomId(roomStatus.getRoomId()); // 聊天室
			msgStatus.setMsgId(message.getId()); // 消息ID
			msgStatus.setUserId(roomStatus.getUserId()); // 聊天室成员ID
			msgStatus
					.setReadStatus(roomStatus.getUserId().equals(userId) ? ReadStatusCode.read : ReadStatusCode.noread); // 阅读状态

			chatMsgStatusDao.save(msgStatus);
		}

		// 更改聊天室标题
		String recentlyTitle = content;
		if (recentlyTitle.startsWith("IMAGE:"))
			recentlyTitle = "图片";
		else if (recentlyTitle.startsWith("FILE:"))
			recentlyTitle = "文档";
		else if (recentlyTitle.startsWith("VOICE:"))
			recentlyTitle = "音频";
		else if (recentlyTitle.startsWith("BONUS:"))
			recentlyTitle = "红包";
		else {
			recentlyTitle = content.trim().replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "");
			if (recentlyTitle.length() > 100)
				recentlyTitle = recentlyTitle.substring(0, 100);
		}

		room.setRecentlyTitle(recentlyTitle);
		room.setRecentlyTime(Calendar.getInstance());
		chatRoomDao.save(room);

		return message.getId();
	}

	public boolean deleteOneMessage(Long msgId, Long userId) throws Exception {
		try {
			YydChatMsgStatus msgStatus = chatMsgStatusDao.getByMsgIdAndUserId(msgId, userId);
			if (msgStatus != null)
				chatMsgStatusDao.delete(msgStatus);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	// 删除一个用户的一个聊天室
	public boolean deleteAllMessagesOfOneRoom(Long roomId, Long userId) {
		try {
			int n = 1;
			Page<YydChatMessage> pg = null;
			do {
				pg = chatMessageDao.getPageByRoomId(ChatMessageService.CHAT_PAGESIZE, n++, roomId, userId);
				for (YydChatMessage msg : pg.getResult()) {
					YydChatMsgStatus msgStatus = chatMsgStatusDao.getByMsgIdAndUserId(msg.getId(), userId);
					if (msgStatus != null)
						chatMsgStatusDao.delete(msgStatus);// 某用户的消息阅读状态被删除，该用户就看不见了
				}
			} while (pg.isHasNext());

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public void updateReadStatus(Long msgId, Long userId) throws Exception {
		YydChatMsgStatus yydChatMsgStatus = chatMsgStatusDao.getByMsgIdAndUserId(msgId, userId);
		if (yydChatMsgStatus != null) {
			yydChatMsgStatus.setReadStatus(ReadStatusCode.read);
			chatMsgStatusDao.save(yydChatMsgStatus);
		}
	}

	// 上传图片
	public String savePic(MultipartFile file, Long roomId) throws Exception {
		if (file.getSize() > 0) {
			if (file.getSize() > 5000000) {
				throw new Exception("上传失败：图片大小不能超过5M!");
			}
			String filename = file.getOriginalFilename();
			String ext = FilenameUtils.getExtension(filename);
			if (!ImgTypeCode.contains(ext)) {
				throw new Exception("图片格式不支持！");
			}
			String relativePath = ShareDirService.getChatDir(Long.toString(roomId));
			String realPath = Constants.SHARE_DIR;
			String prefix = "uploadImage";
			String url = MultipartUtil.uploadFile(file, realPath, relativePath, prefix);
			return url;
		} else {
			throw new Exception("上传失败：文件不能为空！");
		}
	}

	public String saveFile(MultipartFile file, Long roomId) throws Exception {
		if (file.getSize() >= 0) {
			String relativePath = ShareDirService.getChatDir(Long.toString(roomId));
			String realPath = Constants.SHARE_DIR;
			String prefix = "uploadFile";
			String url = MultipartUtil.uploadFile(file, realPath, relativePath, prefix);
			return url;
		} else {
			throw new Exception("上传失败：文件不能为空！");
		}
	}

	public String saveVoice(MultipartFile file, Long roomId) throws Exception {
		if (file.getSize() >= 0) {
			String relativePath = ShareDirService.getChatDir(Long.toString(roomId));
			String realPath = Constants.SHARE_DIR;
			String prefix = "voice";
			String url = MultipartUtil.uploadFile(file, realPath, relativePath, prefix);
			return url;
		} else {
			throw new Exception("上传失败：文件不能为空！");
		}
	}

	public String changeAMRToMP3(String url) throws Exception {
		if (url != null && url.indexOf("/", 0) > -1) {
			File sourceFile = new File(Constants.SHARE_DIR + url);
			File targetFile = new File(Constants.SHARE_DIR + url.substring(0, url.lastIndexOf(".")) + ".mp3");
			String newVoiceUrl = url.substring(0, url.lastIndexOf(".")) + ".mp3";
			if (targetFile.exists())
				return newVoiceUrl;
			AudioAttributes audio = new AudioAttributes();
			Encoder encoder = null;
			if (OSUtil.isWindows()) {
				encoder = new Encoder();
			} else {
				MyFFMPEGLocator myFFMP = new MyFFMPEGLocator();
				encoder = new Encoder(myFFMP);
			}
			audio.setCodec("libmp3lame");
			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setFormat("mp3");
			attrs.setAudioAttributes(audio);
			try {
				encoder.encode(sourceFile, targetFile, attrs);
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage());
			} catch (InputFormatException e) {
				logger.error(e.getMessage());
			} catch (EncoderException e) {
				logger.error(e.getMessage());
			}
			return newVoiceUrl;
		}
		return "";
	}

	class MyFFMPEGLocator extends FFMPEGLocator {
		@Override
		protected String getFFMPEGExecutablePath() {
			return "/usr/bin/ffmpeg";
		}
	}

}
