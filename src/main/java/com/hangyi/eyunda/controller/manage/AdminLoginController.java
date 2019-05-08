package com.hangyi.eyunda.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.data.manage.AdminInfoData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.manage.LoginService;
import com.hangyi.eyunda.util.CookieUtil;

@Controller
@RequestMapping("/manage/login")
public class AdminLoginController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String ADMIN_COOKIE = "ack";

	@Autowired
	private LoginService loginService;
	//test 2
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/manage/manage-home");

		String ack = CookieUtil.getCookieValue(request, ADMIN_COOKIE);
		AdminInfoData data = loginService.getByCookie(ack);
		if (data == null)
			mav.setViewName("redirect:/manage/login/login");

		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.POWER);
		mav.addObject("menuAct", MenuCode.POWER_ADMIN);

		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginShow(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/manage/manage-login");
		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginAuth(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		ModelAndView mav = new ModelAndView("/manage/manage-home");

		Map<String, Object> map = new HashMap<String, Object>();

		// 取出输入参数并给出缺省值
		String username = ServletRequestUtils.getStringParameter(request, "username", "");
		String password = ServletRequestUtils.getStringParameter(request, "password", "");

		// 验证输入参数
		List<String> contents = new ArrayList<String>();
		if ("".equals(username)) {
			contents.add("请输入登录名！");
		} else if ("".equals(password)) {
			contents.add("请输入密码！");
		}

		if (!contents.isEmpty()) {
			logger.error(contents.toString());
			throw new Exception(contents.toString());
		}

		// 处理业务逻辑，为了可以rpc，返回数据必须是序列化的
		AdminInfoData adminData = loginService.login(username, password);
		if (adminData != null) {
			map.put("returnCode", "Success");
			map.put("message", "登录成功！");

			mav.addObject("menuMap", MenuCode.getLayerMenuMap());
			mav.addObject("menuOpen", MenuCode.POWER);
			mav.addObject("menuAct", MenuCode.POWER_ADMIN);

			// 写Cookie
			String adminId = Long.toString(adminData.getId());
			String loginToken = loginService.getLoginToken(adminData);
			String ack = adminId + "," + loginToken;
			int maxAge = -1;

			CookieUtil.setCookie(request, response, ADMIN_COOKIE, ack, maxAge);
		} else {
			logger.error("帐号不存在或密码错误！");
			throw new Exception("帐号不存在或密码错误！");
		}

		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/manage/manage-login");

		CookieUtil.deleteCookie(request, response, ADMIN_COOKIE);

		return mav;
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public ModelAndView info(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/manage/manage-info");

		String ack = CookieUtil.getCookieValue(request, ADMIN_COOKIE);
		AdminInfoData data = loginService.getByCookie(ack);
		if (data == null)
			mav.setViewName("redirect:/manage/login/login");

		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.POWER);
		mav.addObject("menuAct", MenuCode.POWER_ADMIN);

		return mav;
	}

}
