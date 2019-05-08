package com.hangyi.eyunda.controller.hyquan.back.power;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.hyquan.HyqRecordLoginData;
import com.hangyi.eyunda.service.hyquan.chat.HyqRecordLoginService;
import com.hangyi.eyunda.util.CalendarUtil;

@Controller
@RequestMapping("/back/power")
public class HyqUserLoginLogsController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	HyqRecordLoginService recordLoginService;

	@RequestMapping(value = "/userLoginLogs", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView userLoginLogs(Page<HyqRecordLoginData> pageData, String userInfo, String startDate,
			String endDate) throws Exception {
		if (userInfo == null)
			userInfo = "";
		if (startDate == null || "".equals(startDate))
			startDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());
		if (endDate == null || "".equals(endDate))
			endDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());

		recordLoginService.findUserLoginLogs(pageData, userInfo, startDate, endDate);

		ModelAndView mav = new ModelAndView("/back/power/userLoginLogs");
		// 列表信息
		mav.addObject("pageData", pageData);
		mav.addObject("userInfo", userInfo);
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);

		return mav;
	}

}
