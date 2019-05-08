package com.hangyi.eyunda.controller.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.data.manage.FilterWordsData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.manage.FilterWordsService;

@Controller
@RequestMapping("/manage/power")
public class FilterWordsController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FilterWordsService filterWordsService;

	@RequestMapping(value = "/filterWords", method = { RequestMethod.GET })
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) {
		FilterWordsData filterWordsData = filterWordsService.getFilterWordsData();

		ModelAndView mav = new ModelAndView("/manage/manage-filterWords");
		mav.addObject("filterWordsData", filterWordsData);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.POWER);
		mav.addObject("menuAct", MenuCode.POWER_WORDS);

		return mav;
	}

	@RequestMapping(value = "/filterWords/save", method = { RequestMethod.POST })
	public ModelAndView save(FilterWordsData filterWordsData, HttpServletRequest request, HttpServletResponse response) {
		filterWordsService.saveFilterWords(filterWordsData);

		ModelAndView mav = new ModelAndView("redirect:/manage/power/filterWords");
		return mav;
	}

}
