package com.hangyi.eyunda.controller.hyquan.hyqh5.shipinfo;

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
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.data.shipinfo.ShpCertificateData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.data.shipinfo.ShpUserShipData;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpFavoriteCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpCertRightsService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpCertificateService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpShipInfoService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpUserShipService;
import com.hangyi.eyunda.util.CalendarUtil;

@Controller
@RequestMapping("/hyqh5/shipinfo")
public class ShpShipShareController extends HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpUserShipService userShipService;
	@Autowired
	private ShpShipInfoService shipInfoService;
	@Autowired
	private ShpCertificateService certificateService;
	@Autowired
	private ShpCertRightsService certRightsService;
	@Autowired
	private HyqUserService userService;

	@RequestMapping(value = "/shipShareList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipShareList(Long shipId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		List<ShpUserShipData> userShipDatas = new ArrayList<ShpUserShipData>();

		boolean canModify = userShipService.canModify(userData.getId(), shipId);
		if (canModify)
			userShipDatas = userShipService.getDatasByShip(shipId);
		else {
			ShpUserShipData userShipData = userShipService.getUserShipData(userData.getId(), shipId);
			userShipDatas.add(userShipData);
		}

		ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(shipId);

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/shipShareList");

		mav.addObject("userData", userData);
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("userShipDatas", userShipDatas);
		mav.addObject("canModify", canModify);

		return mav;
	}
	//船舶下所有证书批量授权给一个用户
	@RequestMapping(value = "/saveShipShares", method = { RequestMethod.POST })
	public void saveShipShares(String loginName, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);
			
			long[] shipIds = ServletRequestUtils.getLongParameters(request, "shipId");
			
			HyqUserData ud = userService.getByLoginName(loginName);
			if (ud == null)
				throw new Exception("错误！你输入的用户不存在，请重新输入用户登录名或手机号码。");
			
			if(shipIds == null||shipIds.length==0)
				throw new Exception("没有选择要授权的船舶。");
			
			String[] sds = new String[shipIds.length];
			String[] eds = new String[shipIds.length];
			for (int i = 0; i < shipIds.length; i++) {
				sds[i] = ServletRequestUtils.getStringParameter(request, "startDate" + shipIds[i], "");
				eds[i] = ServletRequestUtils.getStringParameter(request, "endDate" + shipIds[i], "");
				
				if (!userShipService.canModify(userData.getId(), shipIds[i]))
					continue;   //throw new Exception("错误！你没有该般舶资料的修改权。");
				
				ShpUserShipData userShipData = userShipService.getFSUserShipData(ud.getId(), shipIds[i]);
				if (userShipData != null) {
					userShipData.setRights(ShpRightsCode.seeCertRights);
				} else {

					userShipData = userShipService.getUserShipData(ud.getId(), shipIds[i]);
					if (userShipData == null)
						userShipData = new ShpUserShipData();

					userShipData.setUserId(ud.getId());
					userShipData.setShipId(shipIds[i]);
					userShipData.setRights(ShpRightsCode.seeCertRights);
				}
				userShipService.saveUserShip(userShipData);
				
				long[] certIds = certificateService.getCertificateIds(shipIds[i]);
				
				certRightsService.saveCertRights(ShpCertSysCode.hyq, userShipData.getUserId(), userData.getId(), shipIds[i],
						certIds, sds[i], eds[i]);
				
			}

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}
	//船舶下所有证书批量授权给一个用户
	@RequestMapping(value = "/shipShares", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipShares(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HyqUserData userData = this.getLoginUserData(request, response);
		
		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/shipShares");
		
		List<ShpShipInfoData> shipInfoDatas = userShipService.getMyManageShips(userData.getId());
		
		mav.addObject("userData", userData);
		mav.addObject("shipInfoDatas", shipInfoDatas);
		
		String toDay = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());
		String oneMonthAfter = CalendarUtil.toYYYY_MM_DD(CalendarUtil.addDays(CalendarUtil.now(), 360));
		
		mav.addObject("toDay", toDay);
		mav.addObject("oneMonthAfter", oneMonthAfter);
		
		return mav;
	}
	//船舶下所有证书转移给一个用户
	@RequestMapping(value = "/shipChangs", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipChangs(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HyqUserData userData = this.getLoginUserData(request, response);
		
		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/shipChangs");
		
		List<ShpShipInfoData> shipInfoDatas = userShipService.getMyManageShips(userData.getId());
		
		mav.addObject("userData", userData);
		mav.addObject("shipInfoDatas", shipInfoDatas);
		
		return mav;
	}

	@RequestMapping(value = "/shipShare", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipShare(Long usId, Long shipId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		boolean canModify = userShipService.canModify(userData.getId(), shipId);

		ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(shipId);
		List<ShpCertificateData> certificateDatas = certificateService.getCertificateDatas(shipId);

		ShpUserShipData userShipData = userShipService.getUserShipData(usId);
		if (userShipData == null)
			userShipData = new ShpUserShipData();
		
		//查找我的船舶下给那些用户授权过
		List<HyqUserData> userDatas = new ArrayList<HyqUserData>();
		if(usId == 0l)
			userDatas = userShipService.findUsersByMyShips(userData.getId());

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/shipShare");

		mav.addObject("userData", userData);
		mav.addObject("userDatas", userDatas);
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("userShipData", userShipData);
		mav.addObject("certificateDatas", certificateDatas);
		mav.addObject("canModify", canModify);

		String toDay = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());
		String oneMonthAfter = CalendarUtil.toYYYY_MM_DD(CalendarUtil.addDays(CalendarUtil.now(), 30));

		mav.addObject("toDay", toDay);
		mav.addObject("oneMonthAfter", oneMonthAfter);

		return mav;
	}

	@RequestMapping(value = "/deleteShipShare", method = { RequestMethod.GET })
	public void deleteShipShare(Long usId, Long shipId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			if (!userShipService.canModify(userData.getId(), shipId))
				throw new Exception("错误！你没有该般舶资料的修改权。");

			ShpUserShipData userShipData = userShipService.getUserShipData(usId);
			if (userShipData != null) {
				if (userShipData.getUserId().equals(userData.getId()))
					throw new Exception("错误！你是该船舶的建立者，不能自己删除自己。");
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
	public void saveShipShare(Long usId, String loginName, Long shipId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			if (!userShipService.canModify(userData.getId(), shipId))
				throw new Exception("错误！你没有该般舶资料的修改权。");

			ShpUserShipData userShipData = userShipService.getUserShipData(usId);

			if (userShipData != null) {
				if (userShipData.getUserId().equals(userData.getId()))
					throw new Exception("错误！你是该船舶的建立者，不能自己修改自己的权限");

				userShipData.setRights(ShpRightsCode.seeCertRights);
			} else {
				HyqUserData ud = userService.getByLoginName(loginName);
				if (ud == null)
					throw new Exception("错误！你输入的用户不存在，请重新输入用户登录名或手机号码。");

				userShipData = userShipService.getUserShipData(ud.getId(), shipId);
				if (userShipData == null)
					userShipData = new ShpUserShipData();

				userShipData.setUserId(ud.getId());
				userShipData.setShipId(shipId);
				userShipData.setRights(ShpRightsCode.seeCertRights);
			}
			userShipService.saveUserShip(userShipData);

			long[] certIds = ServletRequestUtils.getLongParameters(request, "certId");
			if (certIds != null && certIds.length > 0) {
				String[] sds = new String[certIds.length];
				String[] eds = new String[certIds.length];
				for (int i = 0; i < certIds.length; i++) {
					sds[i] = ServletRequestUtils.getStringParameter(request, "startDate" + certIds[i], "");
					eds[i] = ServletRequestUtils.getStringParameter(request, "endDate" + certIds[i], "");
				}

				certRightsService.saveCertRights(ShpCertSysCode.hyq, userShipData.getUserId(), userData.getId(), shipId,
						certIds, sds, eds);
			} else {
				certRightsService.saveCertRights(ShpCertSysCode.hyq, userShipData.getUserId(), userData.getId(), shipId,
						new long[0], new String[0], new String[0]);
			}

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/addShipShare", method = { RequestMethod.GET })
	public void addShipShare(Long shipId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			ShpFavoriteCode favoriteCode = userShipService.canAddFavorite(userData.getId(), shipId);

			if (favoriteCode == ShpFavoriteCode.canfavorite) {
				ShpUserShipData userShipData = new ShpUserShipData();

				userShipData.setUserId(userData.getId());
				userShipData.setShipId(shipId);
				userShipData.setRights(ShpRightsCode.favoriteShip);

				userShipService.saveUserShip(userShipData);

				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "收藏成功！");
			} else if (favoriteCode == ShpFavoriteCode.cantfavorite) {
				throw new Exception("错误！你已经收藏了该船舶，不需要再收藏。");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/removeShipShare", method = { RequestMethod.GET })
	public void removeShipShare(Long shipId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			ShpFavoriteCode favoriteCode = userShipService.canRemoveFavorite(userData.getId(), shipId);

			if (favoriteCode == ShpFavoriteCode.cantcancel) {
				throw new Exception("错误！你还没有收藏该船舶，不能够移除收藏。");
			} else if (favoriteCode == ShpFavoriteCode.cancancel) {
				ShpUserShipData userShipData = userShipService.getUserShipData(userData.getId(), shipId);
				boolean b = userShipService.deleteUserShip(userShipData.getId());
				if (!b)
					throw new Exception("错误！删除用户船舶信息时出错。");
				else
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "移除收藏成功！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

}
