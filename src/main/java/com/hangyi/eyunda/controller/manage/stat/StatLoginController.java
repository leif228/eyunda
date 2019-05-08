package com.hangyi.eyunda.controller.manage.stat;

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
import com.hangyi.eyunda.data.manage.stat.MonthStatLoginData;
import com.hangyi.eyunda.data.manage.stat.StatLoginData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.manage.stat.StatLoginService;

@Controller
@RequestMapping("/manage/stat")
public class StatLoginController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StatLoginService statLoginService;

	@RequestMapping(value = "/statLogin", method = RequestMethod.GET)
	public ModelAndView statLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tab = ServletRequestUtils.getStringParameter(request, "tab", "login-view1");
		
		ModelAndView mav = new ModelAndView("/manage/manage-stat-login");
		
		//登录统计列表
		List<StatLoginData> statLoginDatas = statLoginService.getStatLoginDatas();
		mav.addObject("statLoginDatas", statLoginDatas);
		
		mav.addObject("tab", tab);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.STAT);
		mav.addObject("menuAct", MenuCode.STAT_LOGIN);

		return mav;
	}
	
	// 获取一组对象
	@RequestMapping(value = "/statLoginNumber", method = { RequestMethod.GET })
	public void statLoginNumber(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 处理业务逻辑，为了可以rpc，返回数据必须是序列化的
			List<MonthStatLoginData> monthStatLoginDatas = statLoginService.getMonthStatLoginDatas();

			if (monthStatLoginDatas != null) {
				map.put("monthStatLoginDatas", monthStatLoginDatas);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取得数据成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "取得数据失败！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "取得数据失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

}
