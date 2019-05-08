package com.hangyi.eyunda.controller;

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

import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.domain.enumeric.ScfBigAreaCode;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.service.ship.PortService;

@Controller
@RequestMapping("/port")
public class CommonPortController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PortService portService;

	@RequestMapping(value = "/getAllPorts", method = { RequestMethod.GET })
	public void getAllPorts(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<PortData> portDatas = portService.getPortDatas();

			map.put("portDatas", portDatas);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/getCityCodePorts", method = { RequestMethod.GET })
	public void getCityCodePorts(String portCityCode, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			/*if (portCityCode == null || portCityCode == "")
				portCityCode = ScfPortCityCode.GUANGZHOU.getCode();*/

			List<PortData> portDatas = portService.getPortDatasByPortCityCode(portCityCode);

			map.put("portDatas", portDatas);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功！");

			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "提交失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	@RequestMapping(value = "/addNewPort", method = { RequestMethod.GET })
	public void addNewPort(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ScfBigAreaCode[] bigAreas = ScfBigAreaCode.values();

			map.put("bigAreas", this.getMapBigAreas(bigAreas));

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得数据成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "取得数据失败！");
		}
		JsonResponser.respondWithJson(response, map);

		return;
	}

	@RequestMapping(value = "/saveNewPort", method = { RequestMethod.GET })
	public void saveNewPort(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String portCityCode = ServletRequestUtils.getStringParameter(request, "portCityCode", "");
			String portNo = ServletRequestUtils.getStringParameter(request, "portNo", "");
			String portName = ServletRequestUtils.getStringParameter(request, "portName", "未命名港");

			boolean result = portService.save(portCityCode, portNo, portName);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "提交成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "提交失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "提交失败！");
		}
		JsonResponser.respondWithJson(response, map);
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

}
