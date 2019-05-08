package com.hangyi.eyunda.chat.mina.server;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

public class SessionManager {
	private static final Map<Long, IoSession> clientSessions = new ConcurrentHashMap<Long, IoSession>();

	public static IoSession addSession(Long userId, IoSession session) {
		return clientSessions.put(userId, session);
	}

	public static IoSession removeSession(Long userId) {
		return clientSessions.remove(userId);
	}

	public static Long removeSession(IoSession session) {
		Long ret = null;

		Set<Long> ks = clientSessions.keySet();
		for (Long k : ks) {
			if (session.getId() == clientSessions.get(k).getId()) {
				clientSessions.remove(k);
				ret = k;
				break;
			}
		}

		return ret;
	}

	public static IoSession getSession(Long userId) {
		return clientSessions.get(userId);
	}

	public static Map<Long, IoSession> getSessions() {
		return clientSessions;
	}

}
