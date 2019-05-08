package com.hangyi.eyunda.chat.mina.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangyi.eyunda.chat.ChatService;
import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.event.BaseEvent;
import com.hangyi.eyunda.chat.event.LoginEvent;
import com.hangyi.eyunda.chat.event.LogoutEvent;
import com.hangyi.eyunda.chat.event.MessageEvent;
import com.hangyi.eyunda.chat.event.ReportEvent;
import com.hangyi.eyunda.chat.event.StatusEvent;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.service.hyquan.HyqUserService.ResultType;
import com.hangyi.eyunda.service.hyquan.chat.HyqRecordLoginService;

public class MinaServerHandle implements IoHandler {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqUserService userService;
	@Autowired
	private ChatService chatService;
	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private HyqRecordLoginService recordLoginService;

	@Override
	public void exceptionCaught(IoSession session, Throwable arg1) throws Exception {
		Long userId = SessionManager.removeSession(session);

		OnlineUser ou = onlineUserRecorder.getOnlineUser(userId);

		recordLoginService.recordLogout(ou, LoginSourceCode.mobile);
		onlineUserRecorder.removeOnlineUser(ou, LoginSourceCode.mobile);

		logger.error(session.getId() + " session发生异常，have a exception : " + arg1.getMessage());
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {

		Gson gson = new Gson();
		HashMap<String, String> map = gson.fromJson((String) message, new TypeToken<Map<String, String>>() {
		}.getType());

		BaseEvent baseEvent = new BaseEvent(map);

		String messageType = baseEvent.getMessageType();

		baseEvent.setLoginSource(LoginSourceCode.mobile);

		if (messageType != null) {
			if (MessageConstants.LOGIN_EVENT.equals(messageType)) {
				LoginEvent event = new LoginEvent(baseEvent.getEventMap());

				ResultType result = userService.autoLogin(event.getSimCardNo(), event.getBindingCode());

				if (result == ResultType.SUCCESS) {
					HyqUserData userData = userService.getBySimCardNo(event.getSimCardNo());
					event.setUserId(userData.getId());
					event.setLoginName(userData.getLoginName());
					event.setTrueName(userData.getTrueName());
					event.setNickName(userData.getNickName());
					event.setEmail(userData.getEmail());
					event.setMobile(userData.getMobile());
					// 产生第一个sessionId,增加在线用户
					OnlineUser ou = chatService.login(event);
					event.setSessionId(ou.getSessionId());
					event.setOnlineStatus(ou.getOnlineStatus());

					// 记住用户的session
					SessionManager.addSession(event.getUserId(), session);

					// 推送回去报到消息
					session.write(event.toJson());
					logger.info("loginevent", "返回:" + event.toJson());

					// 1.发送未发的聊天消息
					// redisMessageRecorder.sendNoReadMessages(event.getUserId());
					// 2.发送未发的通知消息
					// redisMessageRecorder.sendNoReadNotifies(event.getUserId());
				}
			} else if (MessageConstants.REPORT_EVENT.equals(messageType)) {
				try {
					ReportEvent event = new ReportEvent(baseEvent.getEventMap());

					OnlineUser currUser = onlineUserRecorder.getOnlineUser(event.getUserId());
					if (currUser != null) {
						if (!currUser.getSimNo().equals(event.getSimNo()))
							return;
					}

					// 产生新的sessionId,增加在线用户
					OnlineUser ou = chatService.report(event);

					// 记住用户的session
					SessionManager.addSession(event.getUserId(), session);

					// 推送回去报到消息,修改了sessionId
					event.setSessionId(ou.getSessionId());
					session.write(event.toJson());
					logger.info("reportevent", "返回:" + event.toJson());

					// 1.发送未发的聊天消息
					// redisMessageRecorder.sendNoReadMessages(event.getUserId());
					// 2.发送未发的通知消息
					// redisMessageRecorder.sendNoReadNotifies(event.getUserId());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			} else if (MessageConstants.LOGOUT_EVENT.equals(messageType)) {
				LogoutEvent event = new LogoutEvent(baseEvent.getEventMap());

				chatService.logout(event);

				SessionManager.removeSession(event.getUserId());

			} else if (MessageConstants.NOTIFY_EVENT.equals(messageType)) {
				;
				// NotifyEvent event = new NotifyEvent(baseEvent.getEventMap());
				// chatService.sendNotify(event);

			} else if (MessageConstants.MESSAGE_EVENT.equals(messageType)) {
				MessageEvent event = new MessageEvent(baseEvent.getEventMap());

				chatService.sendMessage(event);

			} else if (MessageConstants.STATUS_EVENT.equals(messageType)) {
				StatusEvent event = new StatusEvent(baseEvent.getEventMap());

				chatService.sendStatus(event);

			}
		}

	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// logger.info(session.getId() + " session发送一个消息: " + message);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void sessionClosed(final IoSession session) throws Exception {
		logger.info(session.getId() + " session关闭");
		CloseFuture future = session.close(true);
		future.addListener(new IoFutureListener() {
			public void operationComplete(IoFuture future) {
				if (future instanceof CloseFuture) {
					Long userId = SessionManager.removeSession(session);

					OnlineUser ou = onlineUserRecorder.getOnlineUser(userId);

					recordLoginService.recordLogout(ou, LoginSourceCode.mobile);
					onlineUserRecorder.removeOnlineUser(ou, LoginSourceCode.mobile);

					((CloseFuture) future).setClosed();
					logger.info("完成session关闭");
				}
			}
		});
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info(session.getId() + " session建立");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus arg1) throws Exception {
		logger.info(session.getId() + " session处于空闲状态-->" + arg1);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info(session.getId() + " session打开");
	}

}
