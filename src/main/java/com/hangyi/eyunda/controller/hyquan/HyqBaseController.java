package com.hangyi.eyunda.controller.hyquan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.util.CookieUtil;

@Controller
public class HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected OnlineUserRecorder onlineUserRecorder;
	@Autowired
	protected HyqUserService userService;

	protected HyqUserData getLoginUserData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionId = ServletRequestUtils.getStringParameter(request, HyqLoginController.MBL_SESSION_ID, "");
		OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);

		HyqUserData userData = null;
		if (ou == null) {
			String uck = CookieUtil.getCookieValue(request, HyqLoginController.USER_COOKIE);
			userData = userService.getByCookie(uck);
		} else {
			userData = userService.getById(ou.getId());
			// 写Cookie
			String userId = Long.toString(userData.getId());
			String loginToken = userService.getLoginToken(userData);
			String uck = userId + "," + loginToken;
			int maxAge = 1 * 24 * 60 * 60;
			CookieUtil.setCookie(request, response, HyqLoginController.USER_COOKIE, uck, maxAge);
		}

		if (userData == null)
			throw new Exception("用户信息未找到，请重新登录！");

		return userData;
	}

}
