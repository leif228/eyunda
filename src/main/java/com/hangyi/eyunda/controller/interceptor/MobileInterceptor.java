package com.hangyi.eyunda.controller.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.service.EnumConst.MobileMenuCode;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.SpringBeanUtil;

public class MobileInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			String uri = request.getServletPath();

			OnlineUserRecorder onlineUserRecorder = (OnlineUserRecorder) SpringBeanUtil.getBean(
					request.getServletContext(), "onlineUserRecorder");
			UserService userService = (UserService) SpringBeanUtil.getBean(request.getServletContext(), "userService");

			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");
			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			UserData userData = null;
			if (ou != null)
				userData = userService.getById(ou.getId());

			if (uri.startsWith("/mobile/chat")) {
				// 未登录
				if (userData == null) {
					response.sendRedirect(request.getContextPath() + "/mobile/common/error?errorMsg=" + "你还未登录，不能聊天！");
					return false;
				} else {
					return true;
				}
			} else {
				@SuppressWarnings("unused")
				MobileMenuCode eMenuCode = null;
				boolean isMobile = false;
				for (MobileMenuCode menu : MobileMenuCode.values()) {
					if (uri.startsWith(menu.getUrl())) {
						eMenuCode = menu;
						isMobile = true;
						break;
					}
				}
				if (!isMobile)
					return true;

				// 未登录
				if (userData == null) {
					response.sendRedirect(request.getContextPath() + "/mobile/common/error?errorMsg=" + "你还未登录，无权访问！");
					return false;
				}
				
				return true;
				/*if (eMenuCode == MobileMenuCode.MY_ORDER || eMenuCode == MobileMenuCode.MY_SETTLE) {
					// 是否是实名制的托运人或承运人
					if (userData.isConsigner() || userData.isCarrier()) {
						return true;
					} else {
						response.sendRedirect(request.getContextPath() + "/mobile/common/error?errorMsg="
								+ "子帐号无权访问合同及帐务模块！");
						return false;
					}
				} else {
					return true;
				}*/
			}
		} catch (Exception e) {
			try {
				response.sendRedirect(request.getContextPath() + "/mobile/common/error?errorMsg=" + "拦截手机端接口服务请求时出现错误！");
			} catch (IOException e1) {
			}
			return false;
		}
	}

}
