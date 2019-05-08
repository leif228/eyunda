package com.hangyi.eyunda.chat.dwr;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.hangyi.eyunda.chat.event.BaseEvent;

@SuppressWarnings("rawtypes")
@Service
public class ChatMessageListener implements ApplicationListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public void onApplicationEvent(ApplicationEvent event) {
		// 如果事件类型是BaseEvent就执行下面操作
		if (event instanceof BaseEvent) {
			final BaseEvent be = (BaseEvent) event;

			Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
				public boolean match(ScriptSession session) {
					if (session.getId().equals(be.getSessionId()))
						return true;
					else
						return false;
				}
			}, new Runnable() {
				private ScriptBuffer sb = new ScriptBuffer();

				public void run() {
					sb.appendScript(be.getMessageType() + "EventReceive(" + be.toJson() + ")");

					Collection<ScriptSession> sessions = Browser.getTargetSessions();
					if (sessions != null && !sessions.isEmpty()) {
						for (ScriptSession scriptSession : sessions) {
							logger.info(sb.toString());
							scriptSession.addScript(sb);
						}
					}
				}
			});

		}
	}

}
