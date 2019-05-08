package com.hangyi.eyunda.chat.dwr;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpSession;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hangyi.eyunda.chat.ChatService;
import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.event.BaseEvent;
import com.hangyi.eyunda.chat.event.LoginEvent;
import com.hangyi.eyunda.chat.event.LogoutEvent;
import com.hangyi.eyunda.chat.event.MessageEvent;
import com.hangyi.eyunda.chat.event.NotifyEvent;
import com.hangyi.eyunda.chat.event.ReportEvent;
import com.hangyi.eyunda.chat.event.StatusEvent;
import com.hangyi.eyunda.controller.portal.login.LoginController;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CookieUtil;

@Service
public class DwrChatService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ChatService chatService;

	public void chatTask(Map<String, String> map) throws Exception {
		try {
			ServletContext servletContext = WebContextFactory.get().getServletContext();
			ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
			// HttpSession session = WebContextFactory.get().getSession();
			HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();

			WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			UserService userService = (UserService) wac.getBean("userService");

			// 取出登录用户，登录聊天服务器
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);

			if (userData != null) {// 判断是否登录
				BaseEvent baseEvent = new BaseEvent(map);

				map.put(BaseEvent.DATE_TIME, Long.toString(new Date().getTime()));
				map.put(BaseEvent.SESSION_ID, scriptSession.getId());
				map.put(BaseEvent.LOGIN_SOURCE, LoginSourceCode.web.toString());

				String messageType = baseEvent.getMessageType();

				if (messageType != null) {
					if (MessageConstants.REPORT_EVENT.equalsIgnoreCase(messageType)) {
						map.put(ReportEvent.USERID, Long.toString(userData.getId()));
						map.put(ReportEvent.LOGIN_NAME, userData.getLoginName());
						map.put(ReportEvent.NICK_NAME, userData.getNickName());

						ReportEvent event = new ReportEvent(baseEvent.getEventMap());
						chatService.report(event);
					} else if (MessageConstants.LOGIN_EVENT.equalsIgnoreCase(messageType)) {
						map.put(LoginEvent.USERID, Long.toString(userData.getId()));
						map.put(LoginEvent.LOGIN_NAME, userData.getLoginName());
						map.put(LoginEvent.TRUE_NAME, userData.getTrueName());
						map.put(LoginEvent.NICK_NAME, userData.getNickName());

						LoginEvent event = new LoginEvent(baseEvent.getEventMap());
						event.setIpAddress(CookieUtil.getRemoteAddr(request));
						chatService.login(event);
					} else if (MessageConstants.LOGOUT_EVENT.equalsIgnoreCase(messageType)) {
						map.put(LogoutEvent.USERID, Long.toString(userData.getId()));
						map.put(LogoutEvent.LOGIN_NAME, userData.getLoginName());
						map.put(LogoutEvent.TRUE_NAME, userData.getTrueName());
						map.put(LogoutEvent.NICK_NAME, userData.getNickName());

						LogoutEvent event = new LogoutEvent(baseEvent.getEventMap());
						chatService.logout(event);
						scriptSession.removeAttribute("userSessionId");
					} else if (MessageConstants.MESSAGE_EVENT.equalsIgnoreCase(messageType)) {
						map.put(MessageEvent.FROM_USERID, Long.toString(userData.getId()));
						map.put(MessageEvent.FROM_USERNAME, userData.getNickName());

						MessageEvent event = new MessageEvent(baseEvent.getEventMap());
						chatService.sendMessage(event);
					} else if (MessageConstants.NOTIFY_EVENT.equalsIgnoreCase(messageType)) {
						map.put(NotifyEvent.FROM_USERID, Long.toString(userData.getId()));
						map.put(NotifyEvent.FROM_USERNAME, userData.getNickName());

						// NotifyEvent event = new NotifyEvent(baseEvent.getEventMap());
						// chatService.sendNotify(event);
					} else if (MessageConstants.STATUS_EVENT.equalsIgnoreCase(messageType)) {
						map.put(StatusEvent.FROM_USERID, Long.toString(userData.getId()));
						map.put(StatusEvent.FROM_USERNAME, userData.getNickName());

						StatusEvent event = new StatusEvent(baseEvent.getEventMap());

						chatService.sendStatus(event);
					}
				}
				logger.info("Web chat message:" + baseEvent.toJson());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
