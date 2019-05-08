package com.hangyi.eyunda.controller.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hangyi.eyunda.controller.hyquan.back.power.HyqAdminLoginController;
import com.hangyi.eyunda.data.manage.ModuleInfoData;
import com.hangyi.eyunda.service.EnumConst.BackMenuCode;
import com.hangyi.eyunda.service.manage.LogService;
import com.hangyi.eyunda.service.manage.LoginService;
import com.hangyi.eyunda.service.manage.ModuleService;
import com.hangyi.eyunda.util.CookieUtil;
import com.hangyi.eyunda.util.SpringBeanUtil;

public class BackLoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			String uri = request.getServletPath();

			BackMenuCode eMenuCode = null;
			boolean isManaged = false;
			for (BackMenuCode menu : BackMenuCode.getAllLeaf()) {
				if (uri.startsWith(menu.getUrl())) {
					eMenuCode = menu;
					isManaged = true;
					break;
				}
			}
			if (!isManaged)
				return true;

			String ack = CookieUtil.getCookieValue(request, HyqAdminLoginController.ADMIN_COOKIE);
			String[] ss = ack.split(",");
			Long adminId = Long.parseLong(ss[0]);

			LoginService loginService = (LoginService) SpringBeanUtil.getBean(request.getServletContext(),
					"loginService");
			List<String> ms = loginService.getPowerModules(adminId);// 这儿需要修改使用缓存
			boolean hasPower = ms.contains(eMenuCode.getUrl());
			if (hasPower) {
				ModuleService moduleService = (ModuleService) SpringBeanUtil.getBean(request.getServletContext(),
						"moduleService");
				LogService logService = (LogService) SpringBeanUtil.getBean(request.getServletContext(), "logService");

				ModuleInfoData moduleData = moduleService.getModuleDataByUrl(eMenuCode.getUrl());

				if (eMenuCode != BackMenuCode.POWER_LOG)
					logService.saveOptLog(adminId, moduleData.getId());

				return true;
			}

			response.sendRedirect(request.getContextPath() + "/back/login/info");
			return false;
		} catch (Exception e) {
			try {
				response.sendRedirect(request.getContextPath() + "/back/login/info");
			} catch (IOException e1) {
			}
			return false;
		}
	}

}
