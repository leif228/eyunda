package com.hangyi.eyunda.controller.hyquan.back.power;

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
import com.hangyi.eyunda.service.EnumConst.BackMenuCode;
import com.hangyi.eyunda.service.manage.LoginService;
import com.hangyi.eyunda.util.CookieUtil;

@Controller
@RequestMapping("/back/login")
public class HyqAdminLoginController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String ADMIN_COOKIE = "ack";

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/back/index");

		String ack = CookieUtil.getCookieValue(request, ADMIN_COOKIE);
		AdminInfoData data = loginService.getByCookie(ack);
		if (data == null)
			mav.setViewName("redirect:/back/login/login");

		mav.addObject("menuMap", BackMenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", BackMenuCode.POWER);
		mav.addObject("menuAct", BackMenuCode.POWER_ADMIN);

		return mav;
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public ModelAndView info(String info, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/back/info");

		if (info == null || "".equals(info))
			info = "对不起，你没有该模块的操作权限......";

		mav.addObject("info", info);

		return mav;
	}

	@RequestMapping(value = "/administrator", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView administrator(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/back/admin");

		//String errMessage = (String) request.getAttribute("errMessage");

		//mav.addObject("errMessage", errMessage);
		return mav;
	}

	@RequestMapping(value = "/emptyPage", method = RequestMethod.GET)
	public ModelAndView emptyPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/back/empty_page");
		return mav;
	}
	
	@RequestMapping(value = "/welcomeEyd", method = RequestMethod.GET)
	public ModelAndView welcomeEyd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/back/welcome");
		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginShow(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/back/login");
		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginAuth(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		ModelAndView mav = new ModelAndView("redirect:/back/login/home");

		// 取出输入参数并给出缺省值
		String username = ServletRequestUtils.getStringParameter(request, "username", "");
		String password = ServletRequestUtils.getStringParameter(request, "password", "");

		// 验证输入参数
		if ("".equals(username)) {
			throw new Exception("错误！请输入登录名。");
		} else if ("".equals(password)) {
			throw new Exception("错误！请输入密码。");
		}

		// 处理业务逻辑，为了可以rpc，返回数据必须是序列化的
		AdminInfoData adminData = loginService.login(username, password);

		if (adminData != null) {
			// 写Cookie
			String adminId = Long.toString(adminData.getId());
			String loginToken = loginService.getLoginToken(adminData);
			String ack = adminId + "," + loginToken;
			int maxAge = -1;

			CookieUtil.setCookie(request, response, ADMIN_COOKIE, ack, maxAge);

			mav = new ModelAndView("redirect:/back/login/home");
			return mav;
		} else {
			throw new Exception("登录名或密码错误，登录失败！");
		}

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/back/login");

		CookieUtil.deleteCookie(request, response, ADMIN_COOKIE);

		return mav;
	}

}
