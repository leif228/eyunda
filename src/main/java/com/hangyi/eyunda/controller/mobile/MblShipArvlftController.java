package com.hangyi.eyunda.controller.mobile;

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

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.DepartmentData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.ship.ArvlftShipData;
import com.hangyi.eyunda.data.ship.ShipArvlftData;
import com.hangyi.eyunda.data.ship.ShipCooordData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.data.ship.ShipUpdownData;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.service.account.DepartmentService;
import com.hangyi.eyunda.service.account.PrivilegeService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.MyShipArvlftService;
import com.hangyi.eyunda.service.ship.MyShipService;

@Controller
@RequestMapping("/mobile/state")
public class MblShipArvlftController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final int PAGE_SIZE = 20;

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private UserService userService;
	@Autowired
	private MyShipArvlftService shipArvlftService;
	@Autowired
	private MyShipService shipService;
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

	// 我的船舶动态列表
	@RequestMapping(value = "/myShip", method = { RequestMethod.GET, RequestMethod.POST })
	public void myShip(Page<ArvlftShipData> pageData, Long deptId, String keyWords, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			String contentMd5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			Map<String, Object> content = new HashMap<String, Object>();

			List<DepartmentData> departmentDatas = null;
			if (deptId == null)
				deptId = -1L;

			if (userService.isRole(userData, UserPrivilegeCode.handler)) {
				content.put("flag", false);

				departmentDatas = departmentService.getDepartmentDatas(userData.getCurrCompanyData().getId(),
						UserPrivilegeCode.master);

				pageData = shipArvlftService.getArvlftShipPage(userData.getCurrCompanyData().getId(), keyWords,
						pageData.getPageNo(), pageData.getPageSize(), "", deptId, 0L);
			} else if (userService.isRole(userData, UserPrivilegeCode.master)) {
				content.put("flag", false);
				pageData = shipArvlftService.getArvlftShipPage(userData.getCurrCompanyData().getId(), keyWords,
						pageData.getPageNo(), pageData.getPageSize(), "", 0L, userData.getId());
			} else if (userService.isRole(userData, UserPrivilegeCode.sailor)) {
				content.put("flag", false);
				String mmsi = privilegeService.getShipMmsiForSailor(userData.getId(),
						userData.getCurrCompanyData().getId());
				pageData = shipArvlftService.getArvlftShipPage(userData.getCurrCompanyData().getId(), keyWords,
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
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 我的船舶动态上报到离列表
	@RequestMapping(value = "/shipApply/shipArvlft", method = RequestMethod.GET)
	public void shipArvlft(String mmsi, String startTime, String endTime, Page<ArvlftShipData> pageData,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pageData.setPageSize(PAGE_SIZE);

			String contentMd5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.handler)
					&& !userService.isRole(userData, UserPrivilegeCode.sailor)
					&& !userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东、船员及业务员不能进行此项操作!");

			ShipData shipData = shipService.getShipDataByMmsi(mmsi);

			// 船舶动态上报列表
			pageData = shipArvlftService.getShipArvlftDatas(mmsi, startTime, endTime, pageData.getPageNo(),
					pageData.getPageSize());
			for (ArvlftShipData asd : (List<ArvlftShipData>) pageData.getResult())
				asd.setShipData(shipData);

			// 如果当前用户是船员，则可上报或者修改
			boolean flag = false;
			if (userService.isRole(userData, UserPrivilegeCode.sailor)) {
				String privilmmsi = privilegeService.getShipMmsiForSailor(userData.getId(),
						userData.getCurrCompanyData().getId());
				if (privilmmsi != null && mmsi != null && privilmmsi.equals(mmsi))
					flag = true;
			}

			// 输出数据
			Map<String, Object> content = new HashMap<String, Object>();

			content.put("userData", userData);

			content.put("mmsi", mmsi);
			content.put("shipName", shipData.getShipName());

			content.put("startTime", startTime);
			content.put("endTime", endTime);

			content.put("pageData", pageData);
			content.put("flag", flag);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMd5);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);

	}

	// 给指定船舶添加一个动态上报到离信息
	@RequestMapping(value = "/myShip/shipApply/addShipArvlft", method = RequestMethod.GET)
	public void addShipApplyArvlft(String mmsi, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.sailor))
				throw new Exception("非船员不能进行此项操作！");

			Map<String, Object> content = new HashMap<String, Object>();

			// 参数传递
			content.put("mmsi", mmsi);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 保存装卸货物信息
	@RequestMapping(value = "/saveShipApply", method = RequestMethod.POST)
	public void saveShipApply(ShipArvlftData shipArvlftData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.sailor))
				throw new Exception("非船员不能进行此项操作！");

			String[] cargoTypes = ServletRequestUtils.getStringParameter(request, "cargoType", "").split(", ");
			String[] cargoNames = ServletRequestUtils.getStringParameter(request, "cargoName", "").split(", ");
			String[] tonTeuStr = ServletRequestUtils.getStringParameter(request, "tonTeu", "0").split(", ");

			List<ShipUpdownData> shipUpdownDatas = new ArrayList<ShipUpdownData>();

			if (cargoTypes != null && cargoTypes.length > 0) {
				if (!cargoTypes[0].equals("")) {
					for (int i = 0; i < cargoTypes.length; i++) {
						ShipUpdownData shipUpdownData = new ShipUpdownData();
						shipUpdownData.setCargoType(CargoTypeCode.valueOf(cargoTypes[i]));
						shipUpdownData.setCargoName(cargoNames[i]);
						shipUpdownData.setTonTeu(Double.valueOf(tonTeuStr[i]));

						shipUpdownDatas.add(shipUpdownData);
					}
				}
			}
			shipArvlftData.setShipUpdownDatas(shipUpdownDatas);

			Long result = shipArvlftService.saveShipUpdown(shipArvlftData);

			if (result > 0) {
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
			JsonResponser.respondWithJson(response, map);
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
		return;

	}

	// 删除船舶最后一条上报的信息
	@RequestMapping(value = "/myShip/shipApply/delShipArvlft", method = { RequestMethod.GET })
	public void delLastShipArvlft(Long arriveId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.sailor))
				throw new Exception("非船员不能进行此项操作！");

			shipArvlftService.delShipArvlft(arriveId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "删除动态成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 保存我的船舶动态上报到离列表（新）
	@RequestMapping(value = "/myShip/shipApply/saveShipArvlft", method = RequestMethod.POST)
	public void saveShipArvlft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.sailor))
				throw new Exception("非船员不能进行此项操作！");

			String mmsi = ServletRequestUtils.getStringParameter(request, "mmsi", "");
			Long id = ServletRequestUtils.getLongParameter(request, "id", 0L);
			String arriveTime = ServletRequestUtils.getStringParameter(request, "arriveTime", "");
			String leftTime = ServletRequestUtils.getStringParameter(request, "leftTime", "");
			String portNo = ServletRequestUtils.getStringParameter(request, "portNo", "");
			String goPortNo = ServletRequestUtils.getStringParameter(request, "goPortNo", "");
			String remark = ServletRequestUtils.getStringParameter(request, "remark", "");

			shipArvlftService.saveShipArvlft(id, arriveTime, leftTime, portNo, goPortNo, remark, mmsi);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "提交成功！");
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
			JsonResponser.respondWithJson(response, map);
		}
		return;

	}

	// 编辑动态上报到离信息
	@RequestMapping(value = "/myShip/shipApply/editShipArvlft", method = RequestMethod.GET)
	public void shipApplyArvlftEdit(Long id, String mmsi, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.sailor))
				throw new Exception("非船员不能进行此项操作！");

			Map<String, Object> content = new HashMap<String, Object>();
			// 修改的到离港信息
			ShipArvlftData arrive = shipArvlftService.getShipArvlftDataById(id);

			if (arrive == null) {
				// 添加
				arrive = new ShipArvlftData();
				ShipArvlftData lastShipArvlft = shipArvlftService.getLastShipArvlft(mmsi);
				content.put("addShipArvlft", lastShipArvlft);
			}
			content.put("arriveData", arrive);
			// 修改所对应的离港信息
			ShipArvlftData left = shipArvlftService.getNextArvlft(id);
			content.put("leftData", left);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 航次回放
	@RequestMapping(value = "/routePlay", method = { RequestMethod.GET, RequestMethod.POST })
	public void routePlay(Long arvlftId, Long shipId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查看权限验证
			ShipData myShipData = shipService.getShipData(shipId);
			if (myShipData == null)
				new Exception("错误！指定的船舶信息不存在。");

			ShipArvlftData leftData = null;
			if (arvlftId == null)// 合同跟踪时没有arvlftId参数
				arvlftId = 0L;
			leftData = shipArvlftService.getShipArvlftDataById(arvlftId);
			if (leftData == null)
				leftData = shipArvlftService.getLastShipArvlft(myShipData.getMmsi());
			if (leftData == null)
				new Exception("错误！指定的航次信息不存在。");

			List<ShipCooordData> shipCooordDatas = shipArvlftService.getShipCooordDatas(myShipData, leftData);
			// shipMonitorService.gps2baidu(shipCooordDatas);
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("shipPositions", shipCooordDatas);

			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

}
