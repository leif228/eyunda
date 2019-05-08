package com.hangyi.eyunda.controller.mobile;

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

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.DepartmentData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.ship.ShipCooordData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.service.account.DepartmentService;
import com.hangyi.eyunda.service.account.PrivilegeService;
import com.hangyi.eyunda.service.map.ShipCooordService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.service.sqlite.SqliteDataService;

@Controller
@RequestMapping("/mobile/monitor")
public class MblShipMonitorController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private UserService userService;
	@Autowired
	private MyShipService shipService;
	@Autowired
	private ShipCooordService shipCooordService;
	@Autowired
	private SqliteDataService sqliteDataService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PrivilegeService privilegeService;

	// 获取当前用户对象，含用户所在公司列表及当前公司
	private UserData getLoginUserData(HttpServletRequest request) throws Exception {
		String sessionId = ServletRequestUtils.getStringParameter(request, MblLoginController.MBL_SESSION_ID, "");
		OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
		if (ou == null)
			throw new Exception("session已丢失请重新登录！");

		UserData userData = userService.getById(ou.getId());
		if (userData == null)
			throw new Exception("未登录不能进行此项操作！");

		String strCompId = ServletRequestUtils.getStringParameter(request, MblLoginController.CURR_COMP_ID, null);
		if (strCompId == null)
			strCompId = "0"; // throw new Exception("你还不属于任何公司，请先注册公司并加入!");
		Long compId = Long.parseLong(strCompId);

		CompanyData currCompanyData = userService.getCompanyData(compId);
		List<CompanyData> companyDatas = userService.getCompanyDatas(userData.getId());
		userData.setCurrCompanyData(currCompanyData);
		userData.setCompanyDatas(companyDatas);

		return userData;
	}

	@RequestMapping(value = "/myAllShip", method = RequestMethod.GET)
	public void myAllShip(Page<ShipData> pageData, Long deptId, String keyWords, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);
			String contentMd5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			Map<String, Object> content = new HashMap<String, Object>();

			// 船舶组别列表
			List<DepartmentData> departmentDatas = null;
			if (deptId == null)
				deptId = -1L;

			if (userService.isRole(userData, UserPrivilegeCode.handler)) {
				departmentDatas = departmentService.getDepartmentDatas(userData.getCurrCompanyData().getId(),
						UserPrivilegeCode.master);
				content.put("flag", false);
				pageData = shipService.getShipPage(userData.getCurrCompanyData().getId(), keyWords,
						pageData.getPageNo(), pageData.getPageSize(), "", deptId, 0L);
			} else if (userService.isRole(userData, UserPrivilegeCode.master)) {
				content.put("flag", false);
				pageData = shipService.getShipPage(userData.getCurrCompanyData().getId(), keyWords,
						pageData.getPageNo(), pageData.getPageSize(), "", 0L, userData.getId());
			} else if (userService.isRole(userData, UserPrivilegeCode.sailor)) {
				content.put("flag", false);
				String mmsi = privilegeService.getShipMmsiForSailor(userData.getId(),
						userData.getCurrCompanyData().getId());
				pageData = shipService.getShipPage(userData.getCurrCompanyData().getId(), keyWords,
						pageData.getPageNo(), pageData.getPageSize(), mmsi, 0L, 0L);
			} else {
				content.put("flag", true);
				throw new Exception("非船东、船员及业务员不能进行此项操作！");
			}

			content.put("userData", userData);
			content.put("departmentDatas", departmentDatas);
			content.put("deptId", deptId);
			content.put("pageData", pageData);
			content.put("keyWords", keyWords);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMd5);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	// 时时更新当前用户船舶的分布
	@RequestMapping(value = "/shipCooordRefresh", method = { RequestMethod.GET, RequestMethod.POST })
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
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("shipCooordDatas", shipCooordDatas);
			if (!shipCooordDatas.isEmpty()) {
				map.put(JsonResponser.CONTENT, content);
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

	// 读取船舶最后一个（最新的）坐标
	@RequestMapping(value = "/latestPosition", method = RequestMethod.GET)
	public void latestPosition(Long shipId, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.handler)
					&& !userService.isRole(userData, UserPrivilegeCode.sailor)
					&& !userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东、船员及业务员不能进行此项操作！");

			ShipData shipData = shipService.getShipData(shipId);

			List<ShipCooordData> shipPositions = shipCooordService.getCurrShipDistCooords(shipData.getMmsi());

			if (shipPositions != null && shipPositions.size() > 0) {
				ShipCooordData shipPosition = shipPositions.get(0);
				shipPosition.setShipName(shipData.getShipName());

				Map<String, Object> content = new HashMap<String, Object>();
				content.put("shipPosition", shipPosition);

				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
				map.put(JsonResponser.CONTENT, content);
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/getPic", method = RequestMethod.GET)
	public void getPic(String mmsi, String posTime, String cameraNo, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.handler)
					&& !userService.isRole(userData, UserPrivilegeCode.sailor)
					&& !userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东、船员及业务员不能进行此项操作！");

			Long shipId = 1179L;

			String shipImageUrl = sqliteDataService.getMonitorImage(shipId, posTime, cameraNo);
			Map<String, Object> content = new HashMap<String, Object>();
			if (!"".equals(shipImageUrl) && shipImageUrl != null) {
				content.put("shipPic", "/test" + shipImageUrl);
			} else {
				// 图片不存在如何处理？
				content.put("shipPic", "");
			}
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功");
			map.put(JsonResponser.CONTENT, content);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/shipInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipInfo(String shipName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ModelAndView mav = new ModelAndView("/new/portal-shipInfo-mobile");

		UserData userData = this.getLoginUserData(request);

		if (!userService.isRole(userData, UserPrivilegeCode.handler)
				&& !userService.isRole(userData, UserPrivilegeCode.sailor)
				&& !userService.isRole(userData, UserPrivilegeCode.master))
			throw new Exception("非船东、船员及业务员不能进行此项操作！");

		ShipData myShipData = shipService.getShipDataByShipName(shipName);
		mav.addObject("myShipData", myShipData);

		return mav;
	}

}
