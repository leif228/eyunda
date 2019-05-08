package com.hangyi.eyunda.weixing.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hangyi.eyunda.data.manage.UpdateInfoData;
import com.hangyi.eyunda.util.FileUtil;

@Controller
@RequestMapping("/wx")
public class AppDownController {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/appdown", method = { RequestMethod.GET })
	public ModelAndView down(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/wx/appdown");
		// 读取update.txt信息，获取升级信息
		String staticPath = request.getSession().getServletContext().getRealPath("/");
		String filePath = staticPath + "/WEB-INF/static/phone/update.json";
		String json = FileUtil.readFromFile(filePath);
		Gson gson = new Gson();
		UpdateInfoData res = gson.fromJson(json, UpdateInfoData.class);
		
		mav.addObject("url", res.getUrl());// 软件下载地址

		return mav;
	}
	
	@RequestMapping(value = "/appdoc_mobile", method = { RequestMethod.GET })
	public ModelAndView docdown_m(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/wx/appdoc_mobile");

		return mav;
	}
	
	@RequestMapping(value = "/appdoc_web", method = { RequestMethod.GET })
	public ModelAndView docdown_w(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/wx/appdoc_web");

		return mav;
	}
}
