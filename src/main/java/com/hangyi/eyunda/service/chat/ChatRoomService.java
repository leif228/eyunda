package com.hangyi.eyunda.service.chat;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydChatMessageDao;
import com.hangyi.eyunda.dao.YydChatMsgStatusDao;
import com.hangyi.eyunda.dao.YydChatRoomDao;
import com.hangyi.eyunda.dao.YydChatRoomStatusDao;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.chat.ChatRoomData;
import com.hangyi.eyunda.domain.YydChatMessage;
import com.hangyi.eyunda.domain.YydChatMsgStatus;
import com.hangyi.eyunda.domain.YydChatRoom;
import com.hangyi.eyunda.domain.YydChatRoomStatus;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.ImageUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ChatRoomService extends BaseService<YydChatRoom, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected final static Integer CHAT_PAGESIZE = 15;

	@Autowired
	private YydChatRoomDao chatRoomDao;
	@Autowired
	private YydChatRoomStatusDao chatRoomStatusDao;
	@Autowired
	private YydChatMessageDao chatMessageDao;
	@Autowired
	private YydChatMsgStatusDao chatMsgStatusDao;

	@Autowired
	private UserService userService;

	@Override
	public PageHibernateDao<YydChatRoom, Long> getDao() {
		return (PageHibernateDao<YydChatRoom, Long>) chatRoomDao;
	}

	public boolean isRoomMember(Long roomId, Long userId) throws Exception {
		YydChatRoomStatus ss = chatRoomStatusDao.getByRoomIdAndUserId(roomId, userId);
		return ss != null;
	}

	// 取得聊天室列表
	public List<ChatRoomData> getMyChatRoomDatas(Long userId) throws Exception {
		List<ChatRoomData> roomDatas = new ArrayList<ChatRoomData>();

		List<YydChatRoom> rooms = chatRoomDao.getByUserId(userId);

		for (YydChatRoom room : rooms)
			roomDatas.add(this.getChatRoomData(room, userId));

		return roomDatas;
	}

	public ChatRoomData getByTwoUserId(Long userId, Long contactId) throws Exception {
		YydChatRoomStatus yydChatRoomStatus = chatRoomStatusDao.getByTwoUserId(userId, contactId);

		if (yydChatRoomStatus != null) {
			ChatRoomData data = this.getChatRoomData(yydChatRoomStatus.getRoomId(), userId);
			return data;
		}
		return null;
	}

	public ChatRoomData getChatRoomData(YydChatRoom yydChatRoom, Long userId) throws Exception {
		ChatRoomData data = new ChatRoomData();

		if (yydChatRoom != null) {
			CopyUtil.copyProperties(data, yydChatRoom);

			List<YydChatRoomStatus> crss = chatRoomStatusDao.getByRoomId(yydChatRoom.getId());
			if (!crss.isEmpty() && crss.size() == 2) {
				for (YydChatRoomStatus roomStatus : crss) {
					if (!roomStatus.getUserId().equals(userId)) {
						UserData u = userService.getUserData(roomStatus.getUserId());
						data.setRoomSubject(u.getTrueName());
						data.setRoomLogo(u.getUserLogo());
						break;
					}
				}
			}

			data.setRecentlyTime(CalendarUtil.toHumanFormat(yydChatRoom.getRecentlyTime()));

			// 统计数据
			data.setNoReadCount(chatMessageDao.getNoReadCount(yydChatRoom.getId(), userId));

			return data;
		}
		return null;
	}

	public ChatRoomData getChatRoomData(Long roomId, Long userId) throws Exception {
		return this.getChatRoomData(chatRoomDao.get(roomId), userId);
	}

	// 新建或更新聊天室
	@SuppressWarnings("static-access")
	public Long saveChatRoom(ChatRoomData chatRoomData, Long[] userIds) throws Exception {
		Long roomId = chatRoomData.getId();
		YydChatRoom yydChatRoom = chatRoomDao.get(roomId);

		// 去除重复ID
		Set<Long> hs = new TreeSet<Long>();
		CollectionUtils.addAll(hs, userIds);
		List<Long> uids = new ArrayList<Long>(hs);

		String recentlyTitle = "";
		if (yydChatRoom == null) {
			yydChatRoom = new YydChatRoom();
			recentlyTitle = "新建聊天室";
		} else {
			List<YydChatRoomStatus> crss = chatRoomStatusDao.getByRoomId(roomId);
			for (YydChatRoomStatus crs : crss) {
				if (!uids.contains(crs.getUserId())) {
					// 删除指定聊天室关于指定用户的消息内容
					Long userId = crs.getUserId();
					int n = 1;
					Page<YydChatMessage> pg = null;
					do {
						pg = chatMessageDao.getPageByRoomId(this.CHAT_PAGESIZE, n++, roomId, userId);
						for (YydChatMessage msg : pg.getResult()) {
							YydChatMsgStatus msgStatus = chatMsgStatusDao.getByMsgIdAndUserId(msg.getId(), userId);
							if (msgStatus != null)
								chatMsgStatusDao.delete(msgStatus);// 某用户的消息阅读状态被删除，该用户就看不见了
						}
					} while (pg.isHasNext());

					// 移除要去除的聊天室成员
					chatRoomStatusDao.delete(crs);
				}
			}

			recentlyTitle = "增删聊天成员";
		}

		String realPath = Constants.SHARE_DIR;
		String[] logoPaths = new String[uids.size()];

		String groupName = "";
		if (uids != null && uids.size() > 0) {

			for (int i = 0; i < uids.size(); i++) {
				UserData u = userService.getUserData(uids.get(i));
				groupName += u.getTrueName() + ",";
				logoPaths[i] = realPath + u.getUserLogo();// UserData中的userLogo一定不为空
			}

			if (!"".equals(groupName))
				groupName = groupName.substring(0, groupName.length() - 1);
		}
		yydChatRoom.setRoomSubject(groupName);
		yydChatRoom.setRoomLogo("");
		yydChatRoom.setRecentlyTitle(recentlyTitle);

		Calendar nowTime = CalendarUtil.now();
		yydChatRoom.setRecentlyTime(nowTime);
		yydChatRoom.setCreateTime(nowTime);

		chatRoomDao.save(yydChatRoom);

		String logo_bg = realPath + "/default/group_bg.jpg";
		String rLogo = ShareDirService.getChatDir(Long.toString(yydChatRoom.getId())) + "/roomLogo.jpg";
		String roomLogo = realPath + rLogo;
		ImageUtil.makeRoomLogo(roomLogo, logo_bg, logoPaths);

		yydChatRoom.setRoomLogo(rLogo);
		chatRoomDao.save(yydChatRoom);

		// 插入聊天室成员
		for (Long userId : uids) {
			if (chatRoomStatusDao.getByRoomIdAndUserId(roomId, userId) == null) {
				YydChatRoomStatus yydChatRoomStatus = new YydChatRoomStatus();

				yydChatRoomStatus.setRoomId(yydChatRoom.getId());
				yydChatRoomStatus.setUserId(userId);

				chatRoomStatusDao.save(yydChatRoomStatus);
			}
		}

		return yydChatRoom.getId();
	}

	// 删除一个用户的全部聊天室
	public boolean deleteRooms(Long userId) throws Exception {
		List<YydChatRoom> rooms = chatRoomDao.getByUserId(userId);

		boolean b = true;
		for (YydChatRoom room : rooms) {
			b = this.deleteOneRoom(room.getId(), userId);
			if (!b) {
				return b;
			}
		}
		return b;
	}

	// 删除一个用户的一个联系人
	public boolean deleteOneContact(Long userId, Long contactId) throws Exception {
		List<ChatRoomData> rooms = this.getMyChatRoomDatas(userId);

		boolean b = true;
		for (ChatRoomData room : rooms) {
			b = this.deleteOneRoom(room.getId(), contactId);
			if (!b) {
				return b;
			}
		}
		return b;
	}

	// 删除一个用户的一个聊天室
	@SuppressWarnings("static-access")
	public boolean deleteOneRoom(Long roomId, Long userId) {
		try {
			// 删除指定聊天室的指定用户成员
			YydChatRoomStatus roomStatus = chatRoomStatusDao.getByRoomIdAndUserId(roomId, userId);
			if (roomStatus != null) {
				// 删除指定聊天室关于指定用户的消息内容
				int n = 1;
				Page<YydChatMessage> pg = null;
				do {
					pg = chatMessageDao.getPageByRoomId(this.CHAT_PAGESIZE, n++, roomId, userId);
					for (YydChatMessage msg : pg.getResult()) {
						YydChatMsgStatus msgStatus = chatMsgStatusDao.getByMsgIdAndUserId(msg.getId(), userId);
						if (msgStatus != null)
							chatMsgStatusDao.delete(msgStatus);// 某用户的消息阅读状态被删除，该用户就看不见了
					}
				} while (pg.isHasNext());

				// 删除指定聊天室的指定用户成员
				chatRoomStatusDao.delete(roomStatus);// 删除用户在该聊天室的成员记录，即用户离开该聊天室

				String realPath = Constants.SHARE_DIR;
				YydChatRoom yydChatRoom = chatRoomDao.get(roomId);
				List<YydChatRoomStatus> crss = chatRoomStatusDao.getByRoomId(roomId);
				if (crss.isEmpty()) {
					// 若聊天室已空，则删除该聊天室，要求聊天室成员变动时先添加后移除，反之会出现添加不进去的情况
					chatMessageDao.deleteAllMsgsByRoomId(roomId);

					String roomLogo = realPath + yydChatRoom.getRoomLogo();
					File rlFile = new File(roomLogo);

					if (rlFile.exists())
						rlFile.delete();

					chatRoomDao.delete(yydChatRoom);
				} else {
					// 更换指定聊天室的标题及Logo
					String[] logoPaths = new String[crss.size()];

					String groupName = "";

					for (int i = 0; i < crss.size(); i++) {
						UserData u = userService.getUserData(crss.get(i).getUserId());
						groupName += u.getTrueName() + ",";
						logoPaths[i] = realPath + u.getUserLogo();// UserData中的userLogo一定不为空
					}

					if (!"".equals(groupName))
						groupName = groupName.substring(0, groupName.length() - 1);

					yydChatRoom.setRoomSubject(groupName);
					yydChatRoom.setRoomLogo("");

					yydChatRoom.setRecentlyTitle(userService.getUserData(userId).getTrueName() + "退出了聊天室");

					Calendar nowTime = CalendarUtil.now();
					yydChatRoom.setRecentlyTime(nowTime);
					yydChatRoom.setCreateTime(nowTime);

					String logo_bg = realPath + "/default/group_bg.jpg";
					String rLogo = ShareDirService.getChatDir(Long.toString(yydChatRoom.getId())) + "/roomLogo.jpg";
					String roomLogo = realPath + rLogo;
					ImageUtil.makeRoomLogo(roomLogo, logo_bg, logoPaths);

					yydChatRoom.setRoomLogo(rLogo);
					chatRoomDao.save(yydChatRoom);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public List<UserData> getRoomMembers(Long roomId) throws Exception {
		YydChatRoom yydChatRoom = chatRoomDao.get(roomId);

		List<UserData> us = new ArrayList<UserData>();

		if (yydChatRoom != null) {
			List<YydChatRoomStatus> statuss = chatRoomStatusDao.getByRoomId(roomId);
			for (YydChatRoomStatus status : statuss) {
				UserData u = userService.getUserData(status.getUserId());
				us.add(u);
			}
		}
		return us;
	}

}
