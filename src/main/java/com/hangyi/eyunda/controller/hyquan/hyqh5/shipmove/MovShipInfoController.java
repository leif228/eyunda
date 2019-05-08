package com.hangyi.eyunda.controller.hyquan.hyqh5.shipmove;

import java.util.HashMap;
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
import com.hangyi.eyunda.controller.hyquan.HyqBaseController;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.data.shipmove.MovUserShipData;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpShipInfoService;
import com.hangyi.eyunda.service.hyquan.shipmove.MovUserShipService;

@Controller
@RequestMapping("/hyqh5/shipmove")
public class MovShipInfoController extends HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MovUserShipService userShipService;
	@Autowired
	private ShpShipInfoService shipInfoService;

	@RequestMapping(value = "/shipList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipList(String keywords, Page<MovUserShipData> pageData, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		pageData = userShipService.getPageByUserId(userData.getId(), keywords, pageData);

		ModelAndView mav = new ModelAndView("/hyqh5/shipmove/shipList");

		mav.addObject("userData", userData);
		mav.addObject("pageData", pageData);
		mav.addObject("keywords", keywords);

		return mav;
	}

	@RequestMapping(value = "/shipPage", method = { RequestMethod.GET, RequestMethod.POST })
	public void shipPage(String keywords, Page<MovUserShipData> pageData, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			pageData = userShipService.getPageByUserId(userData.getId(), keywords, pageData);

			map.put(JsonResponser.CONTENT, pageData);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得下一页成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/findShip", method = { RequestMethod.GET, RequestMethod.POST })
	public void findShip(String mmsi, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			ShpShipInfoData sd = shipInfoService.getShipInfoDataByMmsiOrShipName(mmsi, ShpCertSysCode.hyq);
			if (sd != null) {
				MovUserShipData usd = userShipService.getUserShipData(userData.getId(), sd.getMmsi());
				if (usd == null) {
					MovUserShipData susd = userShipService.getModifyByMmsi(sd.getMmsi());
					boolean hasManager = (susd == null) ? false : true;

					MovUserShipData userShipData = new MovUserShipData();
					userShipData.setUserId(userData.getId());
					userShipData.setMmsi(sd.getMmsi());
					userShipData.setRights(hasManager ? ShpRightsCode.favoriteShip : ShpRightsCode.managedShip);// 这儿第一人有管理权ShpRightsCode.onlyOpen
					userShipService.saveUserShip(userShipData);
				} else {
					throw new Exception("警告！你已经添加过该船舶。");
				}
			} else {
				throw new Exception("对不起！你输入的船舶找不到，请先到船舶资料管理模块新建此船。");
			}

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "添加成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	/*
	 * @RequestMapping(value = "/shipTimeLine", method = { RequestMethod.GET,
	 * RequestMethod.POST }) public ModelAndView shipTimeLine(String mmsi,
	 * HttpServletRequest request, HttpServletResponse response) throws Exception {
	 * 
	 * HyqUserData userData = this.getLoginUserData(request, response);
	 * 
	 * ShpShipInfoData shipInfoData = shipInfoService.getShipInfoData(mmsi); if
	 * (shipInfoData == null) shipInfoData = new ShpShipInfoData();
	 * 
	 * ModelAndView mav = new ModelAndView("/hyqh5/shipmove/shipTimeLine");
	 * 
	 * mav.addObject("userData", userData); mav.addObject("shipInfoData",
	 * shipInfoData);
	 * 
	 * return mav; }
	 * 
	 * @RequestMapping(value = "/shipTimePoint", method = { RequestMethod.GET,
	 * RequestMethod.POST }) public ModelAndView shipTimePoint(String mmsi,
	 * HttpServletRequest request, HttpServletResponse response) throws Exception {
	 * 
	 * HyqUserData userData = this.getLoginUserData(request, response);
	 * 
	 * ShpShipInfoData shipInfoData = shipInfoService.getShipInfoData(mmsi); if
	 * (shipInfoData == null) shipInfoData = new ShpShipInfoData();
	 * 
	 * ModelAndView mav = new ModelAndView("/hyqh5/shipmove/shipTimePoint");
	 * 
	 * mav.addObject("userData", userData); mav.addObject("shipInfoData",
	 * shipInfoData);
	 * 
	 * mav.addObject("timePoints", TimeLineCode.values());
	 * mav.addObject("cargoTypes", CargoTypeCode.values());
	 * mav.addObject("measures", MeasureCode.values());
	 * 
	 * return mav; }
	 */

}
