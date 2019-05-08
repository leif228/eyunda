package com.hangyi.eyunda.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.data.manage.LogInfoData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.manage.LogService;

@Controller
@RequestMapping("/manage/power")
public class LogController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LogService logService;

	// 获取一组对象，以列表的形式返回
	@RequestMapping(value = "/log", method = { RequestMethod.GET })
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response) {
		// 取出输入参数并给出缺省值

		// 处理业务逻辑，为了可以rpc，返回数据必须是序列化的
		List<LogInfoData> logDatas = logService.getLogDatas();

		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-log");
		// 列表信息
		mav.addObject("logDatas", logDatas);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.POWER);
		mav.addObject("menuAct", MenuCode.POWER_LOG);

		return mav;
	}

	@RequestMapping(value = "/log/delete", method = { RequestMethod.DELETE })
	public ModelAndView delete(Long id, HttpServletRequest request, HttpServletResponse response) {
		logService.delete(id);
		ModelAndView mav = new ModelAndView("redirect:/manage/power/log");
		return mav;
	}

}
