package com.hangyi.eyunda.chat;

import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.event.LoginEvent;
import com.hangyi.eyunda.chat.event.LogoutEvent;
import com.hangyi.eyunda.chat.event.MessageEvent;
import com.hangyi.eyunda.chat.event.NotifyEvent;
import com.hangyi.eyunda.chat.event.ReportEvent;
import com.hangyi.eyunda.chat.event.StatusEvent;
import com.hangyi.eyunda.chat.mina.server.SessionManager;
import com.hangyi.eyunda.service.hyquan.chat.HyqChatMessageService;

@Service
public class MinaMessageListener implements MessageListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqChatMessageService chatMessageService;

	@Override
	public OnlineUser report(ReportEvent event) throws Exception {
		logger.info("not to send the ReportEvent:" + event.toJson());
		return null;
	}

	@Override
	public OnlineUser login(LoginEvent event) throws Exception {
		logger.info("not to send the LoginEvent:" + event.toJson());
		return null;
	}

	@Override
	public void logout(LogoutEvent event) throws Exception {
		logger.info("not to send the LogoutEvent:" + event.toJson());
	}

	@Override
	public void sendNotify(NotifyEvent event) throws Exception {
		IoSession session = SessionManager.getSession(event.getToUserId());
		if (session != null) {
			// 推送该消息
			WriteFuture writeResult = session.write(event.toJson());
			writeResult.awaitUninterruptibly(1, TimeUnit.SECONDS);
			if (writeResult.isWritten()) {
				logger.info("send to mina the NotifyEvent:" + event.toJson());

				/*YydOrderNotify yydNotify = notifyService.get(event.getId());
				yydNotify.setReadStatus(ReadStatusCode.read);
				notifyService.save(yydNotify);*/

				// chatMessageRecorder.removeNoReadMessage(event.getToUserId(), event.getMessageType() + event.getId());
			}
		}
	}

	@Override
	public void sendMessage(MessageEvent event) throws Exception {
		IoSession session = SessionManager.getSession(event.getToUserId());
		if (session != null) {
			// 推送该消息
			WriteFuture writeResult = session.write(event.toJson());
			writeResult.awaitUninterruptibly(1, TimeUnit.SECONDS);
			if (writeResult.isWritten()) {
				logger.info("send to mina the MessageEvent:" + event.toJson());

				chatMessageService.updateReadStatus(event.getId(), event.getToUserId());

				// chatMessageRecorder.removeNoReadMessage(event.getToUserId(), event.getMessageType() + event.getId());
			}
		}
	}

	@Override
	public void sendStatus(StatusEvent event) throws Exception {
		logger.info("Mina send the MessageEvent:" + event.toJson());

		String jsonStr = event.toJson();

		IoSession session = SessionManager.getSession(event.getToUserId());
		if (session != null) {
			// 推送该消息
			WriteFuture writeResult = session.write(event.toJson());
			writeResult.awaitUninterruptibly(1, TimeUnit.SECONDS);
			if (writeResult.isWritten()) {
				logger.info("successfully send to mina the MessageEvent:" + jsonStr);
			} else {
				logger.info("failed send to mina the MessageEvent:" + jsonStr);
			}

		} else {
			logger.info("failed send to mina the MessageEvent:" + jsonStr);
		}
	}

}
