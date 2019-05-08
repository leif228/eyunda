package com.hangyi.eyunda.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.event.LoginEvent;
import com.hangyi.eyunda.chat.event.LogoutEvent;
import com.hangyi.eyunda.chat.event.MessageEvent;
import com.hangyi.eyunda.chat.event.NotifyEvent;
import com.hangyi.eyunda.chat.event.ReportEvent;
import com.hangyi.eyunda.chat.event.StatusEvent;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.service.hyquan.chat.HyqChatMessageService;

@Service
public class DwrMessageListener implements MessageListener, ApplicationContextAware {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private HyqChatMessageService chatMessageService;

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}

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
	public void sendMessage(MessageEvent event) throws Exception {
		OnlineUser ou = onlineUserRecorder.getOnlineUser(event.getToUserId());
		if (ou != null) { // 在线
			if (ou.getDwrSessionId() != null && !"".equals(ou.getDwrSessionId())) {
				// 产生事件，向web客户端推送
				event.setSessionId(ou.getDwrSessionId());
				ctx.publishEvent(event);

				logger.info("send to dwr the MessageEvent:" + event.toJson());

				chatMessageService.updateReadStatus(event.getId(), event.getToUserId());

				// chatMessageRecorder.removeNoReadMessage(event.getToUserId(), event.getMessageType() + event.getId());
			}
		}
	}

	@Override
	public void sendStatus(StatusEvent event) throws Exception {
		OnlineUser ou = onlineUserRecorder.getOnlineUser(event.getToUserId());
		if (ou != null) { // 在线
			if (ou.getDwrSessionId() != null && !"".equals(ou.getDwrSessionId())) {
				// 产生事件，向web客户端推送
				event.setSessionId(ou.getDwrSessionId());
				ctx.publishEvent(event);
			}
		}
	}

	@Override
	public void sendNotify(NotifyEvent event) throws Exception {
		OnlineUser ou = onlineUserRecorder.getOnlineUser(event.getToUserId());
		if (ou != null) { // 在线
			if (ou.getDwrSessionId() != null && !"".equals(ou.getDwrSessionId())) {
				// 产生事件，向web客户端推送
				event.setSessionId(ou.getDwrSessionId());
				ctx.publishEvent(event);

				logger.info("send to dwr the NotifyEvent:" + event.toJson());

				/*YydOrderNotify yydNotify = notifyService.get(event.getId());
				yydNotify.setReadStatus(ReadStatusCode.read);
				notifyService.save(yydNotify);*/
			}
		}
	}

}
