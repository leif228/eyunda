package com.hangyi.eyunda.controller.hyquan.hyqh5.shipmove;

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
import com.hangyi.eyunda.controller.hyquan.HyqBaseController;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.data.shipmove.MovShipArvlftData;
import com.hangyi.eyunda.data.shipmove.MovShipUpdownData;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.MeasureCode;
import com.hangyi.eyunda.domain.enumeric.MoveStateCode;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpShipInfoService;
import com.hangyi.eyunda.service.hyquan.shipmove.MovShipArvlftService;
import com.hangyi.eyunda.service.hyquan.shipmove.MovUserShipService;
import com.hangyi.eyunda.util.CalendarUtil;

@Controller
@RequestMapping("/hyqh5/shipmove")
public class MovShipArvlftController extends HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	// private final static int UPDOWN_COUNT = 5;

	@Autowired
	private MovShipArvlftService shipArvlftService;
	@Autowired
	private ShpShipInfoService shipInfoService;
	@Autowired
	private MovUserShipService userShipService;

	@RequestMapping(value = "/arvlftList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView arvlftList(String mmsi, String startDate, String endDate, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (startDate == null || "".equals(startDate))
			startDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.addMonths(CalendarUtil.now(), -1));
		if (endDate == null || "".equals(endDate))
			endDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());

		HyqUserData userData = this.getLoginUserData(request, response);

		boolean canModify = userShipService.canModify(userData.getId(), mmsi);

		ShpShipInfoData shipInfoData = shipInfoService.getShipInfoData(mmsi, ShpCertSysCode.hyq);

		Page<MovShipArvlftData> pageData = new Page<MovShipArvlftData>();
		pageData = shipArvlftService.getPageDatas(pageData, mmsi, startDate, endDate);

		MovShipArvlftData lastArvlftData = shipArvlftService.getPrevOne(mmsi, 0L);

		ModelAndView mav = new ModelAndView("/hyqh5/shipmove/shipTimeLine");

		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);

		mav.addObject("userData", userData);
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("pageData", pageData);
		mav.addObject("lastArvlftData", lastArvlftData);
		mav.addObject("canModify", canModify);

		return mav;
	}

	@RequestMapping(value = "/arvlftPage", method = { RequestMethod.GET, RequestMethod.POST })
	public void arvlftPage(Page<MovShipArvlftData> pageData, String mmsi, String startDate, String endDate,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			@SuppressWarnings("unused")
			HyqUserData userData = this.getLoginUserData(request, response);

			pageData = shipArvlftService.getPageDatas(pageData, mmsi, startDate, endDate);

			map.put(JsonResponser.CONTENT, pageData);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得下一页成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/arvlftEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView arvlftEdit(Long arvlftId, String mmsi, CertTypeCode certType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		if (!userShipService.canModify(userData.getId(), mmsi))
			throw new Exception("错误！你没有该般舶动态的管理权。");

		ShpShipInfoData shipInfoData = shipInfoService.getShipInfoData(mmsi, ShpCertSysCode.hyq);

		MovShipArvlftData shipArvlftData = shipArvlftService.getShipArvlftData(arvlftId);
		if (shipArvlftData == null) {
			shipArvlftData = new MovShipArvlftData();
			shipArvlftData.setMoveState(MoveStateCode.arrive);
			shipArvlftData.setMmsi(mmsi);
		}

		MovShipArvlftData prevShipArvlftData = shipArvlftService.getPrevOne(mmsi, arvlftId);
		String prevArvlftTime = "2000-01-01 00:00";
		if (prevShipArvlftData != null)
			prevArvlftTime = prevShipArvlftData.getArvlftTime();
		String currentTime = CalendarUtil.toYYYY_MM_DD_HH_MM(CalendarUtil.now());

		ModelAndView mav = new ModelAndView("/hyqh5/shipmove/shipTimePoint");

		mav.addObject("userData", userData);
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("shipArvlftData", shipArvlftData);
		mav.addObject("moveStates", MoveStateCode.values());
		mav.addObject("cargoTypes", CargoTypeCode.values());
		mav.addObject("measures", MeasureCode.values());

		mav.addObject("prevArvlftTime", prevArvlftTime);
		mav.addObject("currentTime", currentTime);

		return mav;
	}

	@RequestMapping(value = "/arvlftDelete", method = { RequestMethod.GET })
	public void arvlftDelete(Long arvlftId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			MovShipArvlftData shipArvlftData = shipArvlftService.getShipArvlftData(arvlftId);
			if (shipArvlftData == null)
				throw new Exception("错误！船舶动态内容未找到。");

			if (!userShipService.canModify(userData.getId(), shipArvlftData.getMmsi()))
				throw new Exception("错误！你没有该般舶动态的管理权。");

			boolean b = shipArvlftService.deleteShipArvlft(arvlftId);
			if (b) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "删除成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "删除失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/arvlftSave", method = { RequestMethod.POST })
	public void arvlftSave(MovShipArvlftData shipArvlftData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			if (!userShipService.canModify(userData.getId(), shipArvlftData.getMmsi()))
				throw new Exception("错误！你没有该般舶动态的管理权。");

			if (shipArvlftData.getMoveState() == MoveStateCode.upload
					|| shipArvlftData.getMoveState() == MoveStateCode.download) {
				String[] cargoTypes = ServletRequestUtils.getStringParameters(request, "cargoType");
				String[] cargoNames = ServletRequestUtils.getStringParameters(request, "cargoName");
				int[] tonTeus = ServletRequestUtils.getIntParameters(request, "tonTeu");
				String[] measures = ServletRequestUtils.getStringParameters(request, "measure");

				List<MovShipUpdownData> shipUpdownDatas = new ArrayList<MovShipUpdownData>();
				for (int i = 0; i < cargoTypes.length; i++) {
					if (cargoTypes[i] != null && !"".equals(cargoTypes[i])) {
						MovShipUpdownData shipUpdownData = new MovShipUpdownData();
						shipUpdownData.setArvlftId(shipArvlftData.getId());
						shipUpdownData.setCargoType(CargoTypeCode.valueOf(cargoTypes[i]));
						shipUpdownData.setCargoName(cargoNames[i]);
						shipUpdownData.setTonTeu(tonTeus[i]);
						shipUpdownData.setMeasure(MeasureCode.valueOf(measures[i]));
						shipUpdownDatas.add(shipUpdownData);
					}
				}

				shipArvlftData.setShipUpdownDatas(shipUpdownDatas);
			}

			boolean b = shipArvlftService.saveShipArvlft(shipArvlftData);
			if (b) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "保存成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "保存失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

}
