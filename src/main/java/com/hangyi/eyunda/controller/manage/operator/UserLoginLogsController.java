package com.hangyi.eyunda.controller.manage.operator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.manage.UserLoginLogData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.manage.stat.RecordLoginService;
import com.hangyi.eyunda.util.CalendarUtil;

@Controller
@RequestMapping("/manage/power")
public class UserLoginLogsController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RecordLoginService recordLoginService;

	@RequestMapping(value = "/userLoginLogs", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView userLoginLogs(Page<UserLoginLogData> pageData, String userInfo, String startDate, String endDate)
			throws Exception {
		if (userInfo == null)
			userInfo = "";
		if (startDate == null || "".equals(startDate))
			startDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());
		if (endDate == null || "".equals(endDate))
			endDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());

		recordLoginService.findUserLoginLogs(pageData, userInfo, startDate, endDate);

		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-userloginlogs");
		// 列表信息
		mav.addObject("pageData", pageData);
		mav.addObject("userInfo", userInfo);
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		// 角色对话框中合同数据列表
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.POWER);
		mav.addObject("menuAct", MenuCode.POWER_LOG);
		return mav;
	}

}
