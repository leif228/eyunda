package com.hangyi.eyunda.chat.timer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;
import com.hangyi.eyunda.service.hyquan.chat.HyqRecordLoginService;

@Service
public class OnlineUserCheckService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private HyqRecordLoginService recordLoginService;

	public void checkUserStatus() {
		List<OnlineUser> ous = onlineUserRecorder.getAllOnlineUsers();
		logger.info("在线用户:");
		if (ous != null && !ous.isEmpty()) {
			for (OnlineUser ou : ous) {
				if (!ou.isPresence()) {
					recordLoginService.recordLogout(ou, LoginSourceCode.mobile);
					recordLoginService.recordLogout(ou, LoginSourceCode.web);
					onlineUserRecorder.removeOnlineUser(ou, LoginSourceCode.mobile);
					onlineUserRecorder.removeOnlineUser(ou, LoginSourceCode.web);
				} else {
					logger.info(ou.getId() + ",sessionId=" + ou.getSessionId() + ";");
				}
			}
		}
	}

}