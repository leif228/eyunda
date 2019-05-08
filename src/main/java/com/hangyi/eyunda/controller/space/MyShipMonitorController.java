package com.hangyi.eyunda.controller.space;

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
import com.hangyi.eyunda.controller.portal.login.LoginController;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.DepartmentData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.ship.ShipCooordData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.service.EnumConst.SpaceMenuCode;
import com.hangyi.eyunda.service.account.DepartmentService;
import com.hangyi.eyunda.service.account.PrivilegeService;
import com.hangyi.eyunda.service.map.ShipCooordService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.service.sqlite.SqliteDataService;
import com.hangyi.eyunda.util.CookieUtil;

@Controller
@RequestMapping("/space/monitor")
public class MyShipMonitorController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private ShipCooordService shipCooordService;
	@Autowired
	private MyShipService shipService;
	@Autowired
	private SqliteDataService sqliteDataService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PrivilegeService privilegeService;

	// 获取当前用户对象，含用户所在公司列表及当前公司
	private UserData getLoginUserData(HttpServletRequest request) throws Exception {
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		if (userData == null)
			throw new Exception("未登录不能进行此项操作!");

		String strCompId = CookieUtil.getCookieValue(request, LoginController.CURR_COMP_ID);
		if (strCompId == null)
			strCompId = "0"; // throw new Exception("你还不属于任何公司，请先注册公司并加入!");
		Long compId = Long.parseLong(strCompId);

		CompanyData currCompanyData = userService.getCompanyData(compId);
		List<CompanyData> companyDatas = userService.getCompanyDatas(userData.getId());
		userData.setCurrCompanyData(currCompanyData);
		userData.setCompanyDatas(companyDatas);

		return userData;
	}

	// 获取当前用户当前角色下所有的船舶分布
	@RequestMapping(value = "/myAllShip/shipDistributoin", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipDistribution(String keyWords, Long deptId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserData userData = this.getLoginUserData(request);

		ModelAndView mav = new ModelAndView("/space/space-ship-monitor-distribution");

		// 船舶组别列表
		List<DepartmentData> departmentDatas = null;
		if (userService.isRole(userData, UserPrivilegeCode.handler))
			departmentDatas = departmentService.getDepartmentDatas(userData.getCurrCompanyData().getId(),
					UserPrivilegeCode.master);

		if (deptId == null)
			deptId = -1L;

		// 输出数据
		mav.addObject("userData", userData);
		mav.addObject("departmentDatas", departmentDatas);
		mav.addObject("deptId", deptId);
		mav.addObject("keyWords", keyWords);

		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_SHIP_MONITOR);

		return mav;
	}

	// 时时更新当前用户船舶的分布
	@RequestMapping(value = "/myAllShip/shipCooordRefresh", method = { RequestMethod.GET, RequestMethod.POST })
	public void shipCooordRefresh(String keyWords, Long deptId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			Map<String, String> mapMmsi = new HashMap<String, String>();
			if (userService.isRole(userData, UserPrivilegeCode.handler)) {
				mapMmsi = shipService.getCabinDescs(userData.getCurrCompanyData().getId(), keyWords, "", deptId, 0L);
			} else if (userService.isRole(userData, UserPrivilegeCode.master)) {
				mapMmsi = shipService.getCabinDescs(userData.getCurrCompanyData().getId(), keyWords, "", 0L,
						userData.getId());
			} else if (userService.isRole(userData, UserPrivilegeCode.sailor)) {
				String mmsi = privilegeService.getShipMmsiForSailor(userData.getId(),
						userData.getCurrCompanyData().getId());
				mapMmsi = shipService.getCabinDescs(userData.getCurrCompanyData().getId(), keyWords, mmsi, 0L, 0L);
			} else {
				throw new Exception("非船东、船员及业务员不能进行此项操作！");
			}

			String mmsis = "";
			for (String key : mapMmsi.keySet()) {
				mmsis += key + ",";
			}
			if (mmsis.length() > 0)
				mmsis = mmsis.substring(0, mmsis.length() - 1);

			List<ShipCooordData> shipCooordDatas = shipCooordService.getCurrShipDistCooords(mmsis);
			for (ShipCooordData shipCooordData : shipCooordDatas) {
				shipCooordData.setShipName(mapMmsi.get(shipCooordData.getMmsi()));
			}

			// shipMonitorService.gps2baidu(shipCooordDatas);

			if (!shipCooordDatas.isEmpty()) {
				map.put("shipCooordDatas", shipCooordDatas);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取得船舶位置坐标成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "取得船舶位置坐标失败！");
			}
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
			JsonResponser.respondWithText(response, map);
		}
		return;
	}

	@RequestMapping(value = "/shipInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipInfo(String shipName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("/new/portal-shipInfo-monitor");

		UserData userData = this.getLoginUserData(request);

		if (!userService.isRole(userData, UserPrivilegeCode.handler)
				&& !userService.isRole(userData, UserPrivilegeCode.sailor)
				&& !userService.isRole(userData, UserPrivilegeCode.master))
			throw new Exception("非船东、船员及业务员不能进行此项操作！");

		ShipData myShipData = shipService.getShipDataByShipName(shipName);

		mav.addObject("myShipData", myShipData);
		if (myShipData == null)
			throw new Exception("错误！指定的船舶不存在！");

		UserData contact = myShipData.getMaster();
		mav.addObject("contact", contact);

		return mav;
	}

	// 不再用！
	@RequestMapping(value = "/myAllShip", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView myAllShip(Page<ShipData> pageData, String keyWords, Long deptId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserData userData = this.getLoginUserData(request);

		ModelAndView mav = new ModelAndView("/space/space-ship-monitor-ships");
		// 船舶组别列表
		List<DepartmentData> departmentDatas = null;
		if (deptId == null)
			deptId = -1L;

		if (userService.isRole(userData, UserPrivilegeCode.handler)) {
			mav.addObject("flag", false);

			departmentDatas = departmentService.getDepartmentDatas(userData.getCurrCompanyData().getId(),
					UserPrivilegeCode.master);

			pageData = shipService.getShipPage(userData.getCurrCompanyData().getId(), keyWords, pageData.getPageNo(),
					pageData.getPageSize(), "", deptId, 0L);
		} else if (userService.isRole(userData, UserPrivilegeCode.master)) {
			mav.addObject("flag", false);
			pageData = shipService.getShipPage(userData.getCurrCompanyData().getId(), keyWords, pageData.getPageNo(),
					pageData.getPageSize(), "", 0L, userData.getId());
		} else if (userService.isRole(userData, UserPrivilegeCode.sailor)) {
			mav.addObject("flag", false);
			String mmsi = privilegeService.getShipMmsiForSailor(userData.getId(),
					userData.getCurrCompanyData().getId());
			pageData = shipService.getShipPage(userData.getCurrCompanyData().getId(), keyWords, pageData.getPageNo(),
					pageData.getPageSize(), mmsi, 0L, 0L);
		} else {
			mav.addObject("flag", true);
			mav.addObject("msg", "非船东、船员及业务员不能进行此项操作！");
		}

		// 用户信息
		mav.addObject("userData", userData);
		mav.addObject("departmentDatas", departmentDatas);
		mav.addObject("deptId", deptId);
		mav.addObject("pageData", pageData);
		mav.addObject("keyWords", keyWords);

		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_SHIP_MONITOR);

		return mav;
	}

	// 获取船舶监控图像
	@RequestMapping(value = "/getShipImage", method = { RequestMethod.GET, RequestMethod.POST })
	public void getShipImage(String mmsi, String posTime, String cameraNo, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long shipId = 1179L;

			String shipImageUrl = sqliteDataService.getMonitorImage(shipId, posTime, cameraNo);

			if (!"".equals(shipImageUrl) && shipImageUrl != null) {
				map.put("shipImageUrl", shipImageUrl);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取得图像成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "取得图像失败");
			}
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "取得图像异常");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

}
