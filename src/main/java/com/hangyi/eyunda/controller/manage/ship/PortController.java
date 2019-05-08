package com.hangyi.eyunda.controller.manage.ship;

import java.util.ArrayList;
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
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.domain.enumeric.ScfBigAreaCode;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.ship.PortService;

@Controller
@RequestMapping("/manage/ship")
public class PortController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PortService portService;

	// 获取一组对象，以列表的形式返回
	@RequestMapping(value = "/port", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView port(Page<PortData> pageData, ScfPortCityCode portCity, String keyWords,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("/manage/manage-port");

		mav.addObject("selPortCity", portCity);
		mav.addObject("keyWords", keyWords);

		// 大区下拉列表
		ScfBigAreaCode[] bigAreas = ScfBigAreaCode.values();
		for (ScfBigAreaCode bigAreaCode : bigAreas)
			bigAreaCode.setPortCities(ScfPortCityCode.getPortCities(bigAreaCode));
		mav.addObject("bigAreas", bigAreas);

		// 港口分页列表
		pageData = portService.getPortPageData(pageData, portCity, keyWords);
		mav.addObject("pageData", pageData);

		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.SHIP);
		mav.addObject("menuAct", MenuCode.SHIP_PORT);

		return mav;
	}

	// 获取一个对象
	@RequestMapping(value = "/port/add", method = { RequestMethod.GET })
	public void add(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 港口城市分组列表
			ScfBigAreaCode[] bigAreas = ScfBigAreaCode.values();
			map.put("bigAreas", this.getMapBigAreas(bigAreas));

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得数据成功！");

			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "取得数据失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	// 获取一个对象
	@RequestMapping(value = "/port/edit", method = { RequestMethod.GET })
	public void edit(String portNo, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PortData portData = portService.getPortData(portNo);

			Map<String, Object> mapPort = new HashMap<String, Object>();
			mapPort.put("portNo", portData.getPortNo());
			mapPort.put("portName", portData.getPortName());

			Map<String, Object> mapPortCity = new HashMap<String, Object>();
			mapPortCity.put("code", portData.getPortCity().getCode());
			mapPortCity.put("description", portData.getPortCity().getDescription());

			mapPort.put("portCity", mapPortCity);

			map.put("portData", mapPort);
			map.put("portCooordDatas", portData.getPortCooordDatas());

			// 港口城市分组列表
			ScfBigAreaCode[] bigAreas = ScfBigAreaCode.values();
			map.put("bigAreas", this.getMapBigAreas(bigAreas));

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得数据成功！");

			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "取得数据失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	private List<Map<String, Object>> getMapBigAreas(ScfBigAreaCode[] bigAreas) {
		List<Map<String, Object>> mapBigAreas = new ArrayList<Map<String, Object>>();
		for (ScfBigAreaCode bigArea : bigAreas) {
			Map<String, Object> mapBigArea = new HashMap<String, Object>();

			mapBigArea.put("code", bigArea.getCode());
			mapBigArea.put("description", bigArea.getDescription());

			List<ScfPortCityCode> portCityCodes = ScfPortCityCode.getPortCities(bigArea);
			List<Map<String, Object>> mapPortCityCodes = new ArrayList<Map<String, Object>>();
			for (ScfPortCityCode portCityCode : portCityCodes) {
				mapPortCityCodes.add(this.getMapPortCity(portCityCode));
			}
			mapBigArea.put("portCities", mapPortCityCodes);

			mapBigAreas.add(mapBigArea);
		}

		return mapBigAreas;
	}

	private Map<String, Object> getMapPortCity(ScfPortCityCode portCityCode) {
		Map<String, Object> mapPortCityCode = new HashMap<String, Object>();

		mapPortCityCode.put("code", portCityCode.getCode());
		mapPortCityCode.put("description", portCityCode.getDescription());

		return mapPortCityCode;
	}

	@RequestMapping(value = "/port/save", method = { RequestMethod.GET, RequestMethod.POST })
	public void save(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String portCityCode = ServletRequestUtils.getStringParameter(request, "portCityCode", "");
			String portNo = ServletRequestUtils.getStringParameter(request, "portNo", "");
			String portName = ServletRequestUtils.getStringParameter(request, "portName", "未命名港");
			double latitude = ServletRequestUtils.getDoubleParameter(request, "latitude", 0.00D);
			double longitude = ServletRequestUtils.getDoubleParameter(request, "longitude", 0.00D);
			double latitude1 = ServletRequestUtils.getDoubleParameter(request, "latitude1", 0.00D);
			double longitude1 = ServletRequestUtils.getDoubleParameter(request, "longitude1", 0.00D);
			double latitude2 = ServletRequestUtils.getDoubleParameter(request, "latitude2", 0.00D);
			double longitude2 = ServletRequestUtils.getDoubleParameter(request, "longitude2", 0.00D);

			portService.save(portCityCode, portNo, portName, longitude, latitude, longitude1, latitude1, longitude2,
					latitude2);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

	@RequestMapping(value = "/port/delete", method = { RequestMethod.GET, RequestMethod.DELETE })
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String portNo = ServletRequestUtils.getStringParameter(request, "portNo", "");

			portService.deletePort(portNo);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

}
