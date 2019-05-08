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
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.domain.enumeric.ShipStatusCode;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.ship.MyShipService;

@Controller
@RequestMapping("/manage/ship")
public class ShipController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MyShipService shipService;

	// 获取一组对象，以列表的形式返回
	@RequestMapping(value = "/ship", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView findShip(HttpServletRequest request, HttpServletResponse response) {
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-ship");

		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		mav.addObject("pageNo", pageNo);

		String keyWords = ServletRequestUtils.getStringParameter(request, "keyWords", "");
		mav.addObject("keyWords", keyWords);

		String selectCode = ServletRequestUtils.getStringParameter(request, "statusCode", "");

		ShipStatusCode statusCode = null;
		try {
			statusCode = ShipStatusCode.valueOf(selectCode);
		} catch (Exception e) {
			statusCode = null;
		}
		mav.addObject("statusCode", statusCode);

		Page<ShipData> pageData = shipService.findByName(pageNo, keyWords, statusCode);
		mav.addObject("pageData", pageData);
		// 下拉列表信息
		ShipStatusCode[] shipStatusCodes = { ShipStatusCode.commit, ShipStatusCode.audit, ShipStatusCode.publish };
		mav.addObject("shipStatusCodes", shipStatusCodes);

		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.SHIP);
		mav.addObject("menuAct", MenuCode.SHIP_QUERY);
		return mav;
	}

	@RequestMapping(value = "/ship/audit", method = { RequestMethod.POST, RequestMethod.GET })
	public void audit(Long id, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean result = shipService.audit(id);
			if (result) {
				ShipData shipData = shipService.getShipData(id);
				map.put("releaseTime", shipData.getReleaseTime());
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "审核成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "该船舶没有提交，不能审核！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 取消审核
	@RequestMapping(value = "/ship/unAudit", method = { RequestMethod.POST, RequestMethod.GET })
	public void unAudit(Long id, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean result = shipService.unAudit(id);
			if (result) {
				ShipData shipData = shipService.getShipData(id);
				map.put("releaseTime", shipData.getReleaseTime());
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取消审核成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "非审核状态，不能取消审核！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/ship/publish", method = { RequestMethod.POST, RequestMethod.GET })
	public void publishShip(Long id, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean result = shipService.publishShip(id);
			if (result) {
				ShipData shipData = shipService.getShipData(id);
				map.put("releaseTime", shipData.getReleaseTime());
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "发布成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "非审核状态，不能发布！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/ship/unPublish", method = { RequestMethod.POST, RequestMethod.GET })
	public void unpublishShip(Long id, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean result = shipService.unpublishShip(id);
			if (result) {
				ShipData shipData = shipService.getShipData(id);
				map.put("releaseTime", shipData.getReleaseTime());
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取消发布成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "非发布状态，不能取消发布！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/ship/delete", method = { RequestMethod.GET })
	public void deleteShip(Long id, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShipData shipData = shipService.getShipData(id);
			boolean result = shipService.deleteShip(shipData.getMaster(), id);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "删除成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "该船舶不存在！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/ship/check", method = { RequestMethod.GET })
	public void findShip(Long id, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShipData shipData = shipService.getShipData(id);
			if (shipData != null) {
				map.put("shipData", shipData);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "获取船舶成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "该船舶不存在！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/ship/showImage", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/manage/manage-ship-showImage");

		Long id = ServletRequestUtils.getLongParameter(request, "id", 0L);
		String currImageUrl = ServletRequestUtils.getStringParameter(request, "url", "");

		ShipData shipData = shipService.getShipData(id);

		if (currImageUrl != null && !"".equals(currImageUrl)) {
			mav.addObject("currImageUrl", currImageUrl);
		}

		List<String> urls = new ArrayList<String>();
		if (shipData.getIdCardFront() != null && !"".equals(shipData.getIdCardFront()))
			urls.add(shipData.getIdCardFront());
		if (shipData.getIdCardBack() != null && !"".equals(shipData.getIdCardBack()))
			urls.add(shipData.getIdCardBack());
		if (shipData.getWarrant() != null && !"".equals(shipData.getWarrant()))
			urls.add(shipData.getWarrant());
		if (shipData.getCertificate() != null && !"".equals(shipData.getCertificate()))
			urls.add(shipData.getCertificate());

		mav.addObject("urls", urls);
		int currIndex = 0;
		for (int i = 0; i < urls.size(); i++) {
			if (currImageUrl.equals(urls.get(i))) {
				currIndex = i;
				break;
			}
		}
		mav.addObject("currIndex", currIndex);

		return mav;
	}

	@RequestMapping(value = "/ship/analyseCooords", method = { RequestMethod.GET })
	public ModelAndView analysePage(String mmsi, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("/manage/manage-ship-analyse");

		mav.addObject("mmsi", mmsi);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.SHIP);
		mav.addObject("menuAct", MenuCode.SHIP_QUERY);
		return mav;
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/ship/getAnalyseMessage", method = { RequestMethod.GET })
	public void getAnalyseMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = (List<Map<String, Object>>) request.getSession().getAttribute("list");

			if (list != null && !list.isEmpty()) {
				for (Map<String, Object> mapList : list) {
					if (mapList.containsKey("endMessage")) {
						request.getSession().invalidate();
					}
				}
				map.put("list", list);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "分析出现异常！");
		}
		JsonResponser.respondWithJson(response, map);
	}

}
