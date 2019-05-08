package com.hangyi.eyunda.chat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.event.LoginEvent;
import com.hangyi.eyunda.chat.event.LogoutEvent;
import com.hangyi.eyunda.chat.event.MessageEvent;
import com.hangyi.eyunda.chat.event.ReportEvent;
import com.hangyi.eyunda.chat.event.StatusEvent;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.chat.redis.service.MessageSender;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;
import com.hangyi.eyunda.service.hyquan.chat.HyqChatMessageService;
import com.hangyi.eyunda.service.hyquan.chat.HyqChatRoomService;
import com.hangyi.eyunda.service.hyquan.chat.HyqContactsService;
import com.hangyi.eyunda.service.hyquan.chat.HyqRecordLoginService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.SessionUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ChatService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private HyqRecordLoginService recordLoginService;
	@Autowired
	private MessageSender messageSender;

	@Autowired
	private HyqChatRoomService chatRoomService;
	@Autowired
	private HyqChatMessageService chatMessageService;

	@Autowired
	private HyqContactsService contactsService;

	public OnlineUser report(ReportEvent event) throws Exception {
		OnlineUser ou = onlineUserRecorder.getOnlineUser(event.getUserId());
		if (ou == null) {
			ou = new OnlineUser();

			ou.setId(event.getUserId());
			ou.setLoginName(event.getLoginName());
			ou.setTrueName(event.getTrueName());
			ou.setNickName(event.getNickName());
			ou.setEnterTime(new Date().getTime());
		}
		ou.setLoginServerName(Constants.MESSAGE_SERVER_NAME);
		ou.setOntime(new Date().getTime());
		if (event.getLoginSource() == LoginSourceCode.mobile) {
			// 产生新的sessionId
			// ou.setSessionId(SessionUtil.getNewSessionId(event.getUserId(),
			// event.getSessionId()));
			// 测试改报到事件sessionId不变
			ou.setSessionId(event.getSessionId());
			ou.setSimNo(event.getSimNo());
		} else if (event.getLoginSource() == LoginSourceCode.web)
			ou.setDwrSessionId(event.getSessionId());
		// 增加在线用户
		onlineUserRecorder.addOnlineUser(ou, event.getLoginSource());

		return ou;
	}

	public OnlineUser login(LoginEvent event) throws Exception {
		OnlineUser ou = onlineUserRecorder.getOnlineUser(event.getUserId());
		if (ou != null) {
			Map<String, String> eventMap = new HashMap<String, String>();
			LogoutEvent logoutEvent = new LogoutEvent(eventMap);
			logoutEvent.setMessageType(MessageConstants.LOGOUT_EVENT);
			logoutEvent.setDateTime(Calendar.getInstance().getTimeInMillis());
			logoutEvent.setSessionId(event.getSessionId());
			logoutEvent.setLoginSource(event.getLoginSource());
			logoutEvent.setUserId(event.getUserId());
			logoutEvent.setLoginName(event.getLoginName());
			logoutEvent.setTrueName(event.getTrueName());
			logoutEvent.setNickName(event.getNickName());
			this.logout(logoutEvent);
		}

		ou = new OnlineUser();

		ou.setId(event.getUserId());
		ou.setLoginName(event.getLoginName());
		ou.setTrueName(event.getTrueName());
		ou.setNickName(event.getNickName());
		ou.setEnterTime(new Date().getTime());

		ou.setLoginServerName(Constants.MESSAGE_SERVER_NAME);
		ou.setOntime(new Date().getTime());
		// 产生新的sessionId
		if (event.getLoginSource() == LoginSourceCode.mobile) {
			// 产生新的sessionId
			ou.setSimNo(event.getSimCardNo());
			ou.setSessionId(SessionUtil.getFirstSessionId(event.getUserId()));
		} else if (event.getLoginSource() == LoginSourceCode.web)
			ou.setDwrSessionId(event.getSessionId());
		// 增加在线用户
		onlineUserRecorder.addOnlineUser(ou, event.getLoginSource());
		// 增加在线记录
		recordLoginService.recordLogin(ou, event.getLoginSource(), event.getIpAddress());

		return ou;
	}

	public void logout(LogoutEvent event) throws Exception {
		OnlineUser ou = onlineUserRecorder.getOnlineUser(event.getUserId());
		if (ou != null) {
			// 修改在线记录
			recordLoginService.recordLogout(ou, event.getLoginSource());
			// 移除在线用户
			onlineUserRecorder.removeOnlineUser(ou, event.getLoginSource());
		}
	}

	public void sendStatus(StatusEvent event) throws Exception {
		// 改变自己的在线状态
		try {
			OnlineUser self = onlineUserRecorder.getOnlineUser(event.getFromUserId());
			self.setOnlineStatus(event.getOnlineStatus());
			onlineUserRecorder.addOnlineUser(self, event.getLoginSource());
		} catch (Exception e) {

		}

		// 我的好友列表
		List<HyqUserData> friends = contactsService.getFriends(event.getFromUserId());

		if (!friends.isEmpty()) {
			for (HyqUserData friend : friends) {
				if (!event.getFromUserId().equals(friend.getId())) {
					OnlineUser ou = onlineUserRecorder.getOnlineUser(friend.getId());
					// 如果找到而且在线
					if (ou != null && ou.isPresence()) {
						event.setToUserId(ou.getId());
						event.setToUserName(ou.getNickName());
						// 通知好友自己的状态变化
						messageSender.send(ou.getLoginServerName(), event);
					}
				}
			}
		}
	}

	public void sendMessage(MessageEvent event, long[] receiverIds) throws Exception {
		Long messageId = chatMessageService.saveMessage(event.getRoomId(), event.getFromUserId(), event.getContent());

		event.setId(messageId);

		List<HyqUserData> roomMembers = chatRoomService.getRoomMembers(event.getRoomId()); // 取得聊天室成员列表
		for (HyqUserData roomMember : roomMembers) {
			if (!event.getFromUserId().equals(roomMember.getId())) {
				boolean flag = false;
				for (long receiverId : receiverIds)
					if (receiverId == roomMember.getId())
						flag = true;

				if (flag) {
					event.setToUserId(roomMember.getId());
					event.setToUserName(roomMember.getNickName());

					OnlineUser ou = onlineUserRecorder.getOnlineUser(roomMember.getId());
					if (ou != null) {
						messageSender.send(ou.getLoginServerName(), event);
						// chatMessageRecorder.addNoReadMessage(roomMember.getId(),
						// event.toJson());
					} else {
						// chatMessageRecorder.addNoReadMessage(roomMember.getId(),
						// event.toJson());
					}
				}
			}
		}
	}

	public void sendMessage(MessageEvent event) throws Exception {
		Long messageId = chatMessageService.saveMessage(event.getRoomId(), event.getFromUserId(), event.getContent());

		event.setId(messageId);

		List<HyqUserData> roomMembers = chatRoomService.getRoomMembers(event.getRoomId()); // 取得聊天室成员列表
		for (HyqUserData roomMember : roomMembers) {
			if (!event.getFromUserId().equals(roomMember.getId())) {
				event.setToUserId(roomMember.getId());
				event.setToUserName(roomMember.getNickName());

				OnlineUser ou = onlineUserRecorder.getOnlineUser(roomMember.getId());
				if (ou != null) {
					messageSender.send(ou.getLoginServerName(), event);
					// chatMessageRecorder.addNoReadMessage(roomMember.getId(),
					// event.toJson());
				} else {
					// chatMessageRecorder.addNoReadMessage(roomMember.getId(),
					// event.toJson());
				}
			}
		}
	}

}
