package com.hangyi.eyunda.controller.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hangyi.eyunda.controller.portal.login.LoginController;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.service.EnumConst.SpaceMenuCode;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CookieUtil;
import com.hangyi.eyunda.util.SpringBeanUtil;

public class SpcLoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			String uri = request.getServletPath();

			UserService userService = (UserService) SpringBeanUtil.getBean(request.getServletContext(), "userService");

			if (uri.startsWith("/space/chat")) {
				String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
				UserData userData = userService.getByCookie(uck);

				// 未登录
				if (userData == null) {
					response.sendRedirect(request.getContextPath() + "/portal/login/info");
					return false;
				} else {
					return true;
				}
			} else {
				@SuppressWarnings("unused")
				SpaceMenuCode eMenuCode = null;
				boolean isSpace = false;
				for (SpaceMenuCode menu : SpaceMenuCode.values()) {
					if (uri.startsWith(menu.getUrl())) {
						eMenuCode = menu;
						isSpace = true;
						break;
					}
				}
				if (!isSpace)
					return true;

				String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
				UserData userData = userService.getByCookie(uck);

				// 未登录
				if (userData == null){
					response.sendRedirect(request.getContextPath() + "/portal/login/info");
					return false;
				}
				else
					return true;
			}
		} catch (Exception e) {
			try {
				response.sendRedirect(request.getContextPath() + "/portal/login/info");
			} catch (IOException e1) {
			}
			return false;
		}
	}

}
