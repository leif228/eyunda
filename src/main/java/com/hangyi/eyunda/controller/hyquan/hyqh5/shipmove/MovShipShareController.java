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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.controller.hyquan.HyqBaseController;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.data.shipmove.MovUserShipData;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpShipInfoService;
import com.hangyi.eyunda.service.hyquan.shipmove.MovUserShipService;

@Controller
@RequestMapping("/hyqh5/shipmove")
public class MovShipShareController extends HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MovUserShipService userShipService;
	@Autowired
	private ShpShipInfoService shipInfoService;
	@Autowired
	private HyqUserService userService;

	@RequestMapping(value = "/shipShareList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipShareList(String mmsi, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		List<MovUserShipData> userShipDatas = new ArrayList<MovUserShipData>();

		boolean canModify = userShipService.canModify(userData.getId(), mmsi);
		if (canModify)
			userShipDatas = userShipService.getDatasByShip(mmsi);
		else {
			MovUserShipData userShipData = userShipService.getUserShipData(userData.getId(), mmsi);
			userShipDatas.add(userShipData);
		}

		ShpShipInfoData shipInfoData = shipInfoService.getShipInfoData(mmsi, ShpCertSysCode.hyq);

		ModelAndView mav = new ModelAndView("/hyqh5/shipmove/shipShareList");

		mav.addObject("userData", userData);
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("userShipDatas", userShipDatas);
		mav.addObject("canModify", canModify);

		return mav;
	}

	@RequestMapping(value = "/shipShare", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipShare(Long usId, String mmsi, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		boolean canModify = userShipService.canModify(userData.getId(), mmsi);

		ShpShipInfoData shipInfoData = shipInfoService.getShipInfoData(mmsi, ShpCertSysCode.hyq);

		MovUserShipData userShipData = userShipService.getUserShipData(usId);
		if (userShipData == null)
			userShipData = new MovUserShipData();

		ModelAndView mav = new ModelAndView("/hyqh5/shipmove/shipShare");

		mav.addObject("userData", userData);
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("userShipData", userShipData);
		mav.addObject("rightss", ShpRightsCode.values());
		mav.addObject("canModify", canModify);

		return mav;
	}

	@RequestMapping(value = "/deleteShipShare", method = { RequestMethod.GET })
	public void deleteShipShare(Long usId, String mmsi, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			if (!userShipService.canModify(userData.getId(), mmsi))
				throw new Exception("错误！你没有该般舶动态的管理权。");

			MovUserShipData userShipData = userShipService.getUserShipData(usId);
			if (userShipData != null) {
				if (userShipData.getUserId().equals(userData.getId())
						&& userShipService.aloneModifier(userData.getId(), mmsi))
					throw new Exception("错误！你是该船舶的唯一管理者，不能删除。");
				// 删除用户船舶
				boolean b = userShipService.deleteUserShip(usId);
				if (!b)
					throw new Exception("错误！删除用户船舶信息时出错。");
			} else {
				throw new Exception("错误！用户船舶权限记录已经不存在。");
			}

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "删除成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/saveShipShare", method = { RequestMethod.POST })
	public void saveShipShare(Long usId, String loginName, String mmsi, ShpRightsCode rights,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			if (!userShipService.canModify(userData.getId(), mmsi))
				throw new Exception("错误！你没有该般舶动态的管理权。");

			MovUserShipData userShipData = userShipService.getUserShipData(usId);
			if (userShipData != null) {
				if (userShipData.getUserId().equals(userData.getId())
						&& userShipService.aloneModifier(userData.getId(), mmsi))
					throw new Exception("错误！你是该船舶的唯一管理者，不能修改。");

				userShipData.setRights(rights);
			} else {
				HyqUserData ud = userService.getByLoginName(loginName);
				if (ud == null)
					throw new Exception("错误！你输入的用户不存在，请重新输入用户登录名或手机号码。");

				userShipData = userShipService.getUserShipData(ud.getId(), mmsi);
				if (userShipData == null)
					userShipData = new MovUserShipData();

				userShipData.setUserId(ud.getId());
				userShipData.setMmsi(mmsi);
				userShipData.setRights(rights);
			}

			userShipService.saveUserShip(userShipData);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/removeShipShare", method = { RequestMethod.GET })
	public void removeShipShare(String mmsi, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			MovUserShipData userShipData = userShipService.getUserShipData(userData.getId(), mmsi);
			if (userShipData != null) {
				if (userShipData.getUserId().equals(userData.getId())
						&& userShipService.aloneModifier(userData.getId(), mmsi))
					throw new Exception("错误！你是该船舶的唯一管理者，不能删除。");
				// 删除用户船舶
				boolean b = userShipService.deleteUserShip(userShipData.getId());
				if (!b)
					throw new Exception("错误！删除用户船舶信息时出错。");
			} else {
				throw new Exception("错误！用户船舶权限记录已经不存在。");
			}

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "删除成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

}
