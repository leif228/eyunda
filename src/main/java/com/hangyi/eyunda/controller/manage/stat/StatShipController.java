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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.manage.stat.StatShipData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.manage.stat.StatShipService;

@Controller
@RequestMapping("/manage/stat")
public class StatShipController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StatShipService statShipService;

	@RequestMapping(value = "/statShip", method = RequestMethod.GET)
	public ModelAndView statShip(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("/manage/manage-stat-ship");

		// 登录统计列表
		List<StatShipData> statShipDatas = statShipService.getStatShipDatas();
		mav.addObject("statShipDatas", statShipDatas);

		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.STAT);
		mav.addObject("menuAct", MenuCode.STAT_SHIP);

		return mav;
	}

	// 获取一组对象
	@RequestMapping(value = "/statShipNumber", method = { RequestMethod.GET })
	public void statShipNumber(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<StatShipData> statShipDatas = statShipService.getStatShipDatas();

			if (statShipDatas != null) {
				map.put("statShipDatas", statShipDatas);
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
