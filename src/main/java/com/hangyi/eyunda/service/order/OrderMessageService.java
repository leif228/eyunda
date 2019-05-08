package com.hangyi.eyunda.service.order;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.chat.ChatService;
import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.event.MessageEvent;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.chat.ChatRoomData;
import com.hangyi.eyunda.data.order.OrderCommonData;
import com.hangyi.eyunda.service.chat.ChatRoomService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class OrderMessageService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ChatService chatService;
	@Autowired
	private ChatRoomService chatRoomService;

	// 发送消息
	public void sendOrderMsg(String msg, UserData userData, OrderCommonData orderData) throws Exception {

		Set<Long> ids = new HashSet<Long>();
		ids.add(userData.getId());

		if (!ids.contains(orderData.getOwner().getId())) {
			this.sendMsg(msg, userData, orderData.getOwner());
			ids.add(orderData.getOwner().getId());
		}
		if (!ids.contains(orderData.getBroker().getId())) {
			this.sendMsg(msg, userData, orderData.getBroker());
			ids.add(orderData.getBroker().getId());
		}
		if (orderData.getMaster() != null && !ids.contains(orderData.getMaster().getId())) {
			this.sendMsg(msg, userData, orderData.getMaster());
			ids.add(orderData.getMaster().getId());
		}
		if (!ids.contains(orderData.getHandler().getId())) {
			this.sendMsg(msg, userData, orderData.getHandler());
			ids.add(orderData.getHandler().getId());
		}
	}

	// 发送消息
	public void sendMsg(String content, UserData senderData, UserData receiverData) throws Exception {
		ChatRoomData chatRoomData = chatRoomService.getByTwoUserId(senderData.getId(), receiverData.getId());
		if (chatRoomData == null) {
			chatRoomData = new ChatRoomData();

			Long roomId = chatRoomService.saveChatRoom(chatRoomData,
					new Long[] { senderData.getId(), receiverData.getId() });
			chatRoomData = chatRoomService.getChatRoomData(roomId, receiverData.getId());
		}
		// 发送通知到手机
		Map<String, String> mapMsg = new HashMap<String, String>();

		mapMsg.put(MessageEvent.MESSAGE_TYPE, MessageConstants.MESSAGE_EVENT);
		mapMsg.put(MessageEvent.CONTENT, content);
		mapMsg.put(MessageEvent.DATE_TIME, Long.toString(new Date().getTime()));
		mapMsg.put(MessageEvent.ROOM_ID, Long.toString(chatRoomData.getId()));
		mapMsg.put(MessageEvent.FROM_USERID, Long.toString(senderData.getId()));
		mapMsg.put(MessageEvent.TO_USERID, Long.toString(receiverData.getId()));
		mapMsg.put(MessageEvent.FROM_USERNAME, senderData.getNickName());
		mapMsg.put(MessageEvent.TO_USERNAME, receiverData.getNickName());

		MessageEvent event = new MessageEvent(mapMsg);

		chatService.sendMessage(event);
	}

}
