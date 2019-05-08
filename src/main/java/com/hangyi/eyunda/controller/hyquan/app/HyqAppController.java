package com.hangyi.eyunda.controller.hyquan.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.controller.hyquan.HyqBaseController;
import com.hangyi.eyunda.data.hyquan.HyqAppData;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.service.hyquan.app.HyqAppService;

@Controller
@RequestMapping("/hyquan/app")
public class HyqAppController extends HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqAppService appService;

	@RequestMapping(value = "/appList", method = { RequestMethod.GET })
	public void appList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			List<HyqAppData> appDatas = appService.getAllAppDatas();

			// 输出参数
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("userData", userData);
			content.put("appDatas", appDatas);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/appView", method = { RequestMethod.GET })
	public ModelAndView appView(String sessionId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HyqUserData userData = this.getLoginUserData(request, response);

		List<HyqAppData> appDatas = appService.getAllAppDatas();

		// 输出参数
		ModelAndView mav = new ModelAndView("/hyqh5/appList");

		mav.addObject("sessionId", sessionId);
		mav.addObject("userData", userData);
		mav.addObject("appDatas", appDatas);

		return mav;
	}

}
