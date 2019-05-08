package com.hangyi.eyunda.controller.hyquan.back.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.hyquan.HyqAppData;
import com.hangyi.eyunda.service.hyquan.app.HyqAppService;

@Controller
@RequestMapping("/back/app")
public class HyqAppManageController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqAppService appService;

	@RequestMapping(value = "/appList", method = { RequestMethod.GET })
	public ModelAndView appList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/back/service/app");

		List<HyqAppData> appDatas = appService.getAllAppDatas();

		mav.addObject("appDatas", appDatas);

		return mav;
	}

	@RequestMapping(value = "/saveApp", method = { RequestMethod.POST })
	public void saveApp(HyqAppData appData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			appService.saveApp(appData);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "保存失败！");
		}
		JsonResponser.respondWithText(response, map);
	}

	@RequestMapping(value = "/deleteApp", method = { RequestMethod.GET })
	public void deleteApp(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			appService.deleteApp(id);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "删除成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "删除失败！");
		}
		JsonResponser.respondWithJson(response, map);
	}
}
