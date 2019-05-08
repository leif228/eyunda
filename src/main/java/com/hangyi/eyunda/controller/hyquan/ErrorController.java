package com.hangyi.eyunda.controller.hyquan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/error")
public class ErrorController {

	@RequestMapping(value = "/err400", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView err400(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/400");
		return mav;
	}

	@RequestMapping(value = "/err404", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView err404(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/404");
		return mav;
	}

	@RequestMapping(value = "/err500", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView err500(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/500");
		return mav;
	}

	@RequestMapping(value = "/info", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView info(String info, HttpServletRequest request, HttpServletResponse response) {
		if (info == null || "".equals(info))
			info = "系统正在开发中......";

		ModelAndView mav = new ModelAndView("/info");
		mav.addObject("info", info);
		return mav;
	}

}
