package com.hangyi.eyunda.controller.space;

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
import com.hangyi.eyunda.controller.portal.login.LoginController;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.DepartmentData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.data.ship.ArvlftShipData;
import com.hangyi.eyunda.data.ship.ShipArvlftData;
import com.hangyi.eyunda.data.ship.ShipCooordData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.data.ship.ShipUpdownData;
import com.hangyi.eyunda.domain.enumeric.ArvlftCode;
import com.hangyi.eyunda.domain.enumeric.CargoBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ScfBigAreaCode;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.service.EnumConst.SpaceMenuCode;
import com.hangyi.eyunda.service.account.DepartmentService;
import com.hangyi.eyunda.service.account.PrivilegeService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.MyShipArvlftService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.service.ship.PortService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CookieUtil;

@Controller
@RequestMapping("/space/state")
public class MyShipArvlftController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final int PAGE_SIZE = 20;

	@Autowired
	private UserService userService;
	@Autowired
	private MyShipArvlftService shipArvlftService;
	@Autowired
	private MyShipService shipService;
	@Autowired
	private PortService portService;
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

	// 我的船舶动态列表
	@RequestMapping(value = "/myShip", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView myShip(Page<ArvlftShipData> pageData, Long deptId, String keyWords, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserData userData = this.getLoginUserData(request);

		ModelAndView mav = new ModelAndView("/space/space-ship-state");

		// 船舶组别列表
		List<DepartmentData> departmentDatas = null;
		if (deptId == null)
			deptId = -1L;

		if (userService.isRole(userData, UserPrivilegeCode.handler)) {
			mav.addObject("flag", false);

			departmentDatas = departmentService.getDepartmentDatas(userData.getCurrCompanyData().getId(),
					UserPrivilegeCode.master);

			pageData = shipArvlftService.getArvlftShipPage(userData.getCurrCompanyData().getId(), keyWords,
					pageData.getPageNo(), pageData.getPageSize(), "", deptId, 0L);
		} else if (userService.isRole(userData, UserPrivilegeCode.master)) {
			mav.addObject("flag", false);
			pageData = shipArvlftService.getArvlftShipPage(userData.getCurrCompanyData().getId(), keyWords,
					pageData.getPageNo(), pageData.getPageSize(), "", 0L, userData.getId());
		} else if (userService.isRole(userData, UserPrivilegeCode.sailor)) {
			mav.addObject("flag", false);
			String mmsi = privilegeService.getShipMmsiForSailor(userData.getId(),
					userData.getCurrCompanyData().getId());
			pageData = shipArvlftService.getArvlftShipPage(userData.getCurrCompanyData().getId(), keyWords,
					pageData.getPageNo(), pageData.getPageSize(), mmsi, 0L, 0L);
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
		mav.addObject("menuAct", SpaceMenuCode.MY_SHIP_STATE);

		return mav;
	}

	// 我的船舶动态上报到离列表
	@RequestMapping(value = "/myShip/shipArvlft", method = RequestMethod.GET)
	public ModelAndView shipArvlft(String mmsi, String startTime, String endTime, Page<ArvlftShipData> pageData,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		pageData.setPageSize(PAGE_SIZE);

		UserData userData = this.getLoginUserData(request);

		if (!userService.isRole(userData, UserPrivilegeCode.handler)
				&& !userService.isRole(userData, UserPrivilegeCode.sailor)
				&& !userService.isRole(userData, UserPrivilegeCode.master))
			throw new Exception("非船东、船员及业务员不能进行此项操作！");

		ShipData shipData = shipService.getShipDataByMmsi(mmsi);
		if (shipData == null)
			throw new Exception("错误！指定的船舶信息未找到。");

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
		ModelAndView mav = new ModelAndView("/space/space-ship-state-arvlft");

		mav.addObject("userData", userData);

		mav.addObject("mmsi", mmsi);
		mav.addObject("shipName", shipData.getShipName());

		mav.addObject("startTime", startTime);
		mav.addObject("endTime", endTime);

		mav.addObject("pageData", pageData);
		mav.addObject("flag", flag);

		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_SHIP_STATE);

		return mav;
	}

	// 添加、编辑动态上报到离信息
	@RequestMapping(value = "/myShip/shipArvlft/editShipArvlft", method = RequestMethod.GET)
	public ModelAndView editShipArvlft(Long id, String mmsi, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserData userData = this.getLoginUserData(request);

		if (!userService.isRole(userData, UserPrivilegeCode.sailor))
			throw new Exception("非船员不能进行此项操作！");

		ModelAndView mav = new ModelAndView("/space/space-ship-state-arvlft-edit");

		// 用户信息
		mav.addObject("userData", userData);

		// 港口城市分组列表
		ScfBigAreaCode[] bigAreas = ScfBigAreaCode.values();
		for (ScfBigAreaCode bigArea : bigAreas)
			if (bigArea.getPortCities() == null)
				bigArea.setPortCities(ScfPortCityCode.getPortCities(bigArea));
		mav.addObject("bigAreas", bigAreas);

		// 修改的到离港信息
		ShipArvlftData shipArvlftData = shipArvlftService.getShipArvlftDataById(id);

		if (shipArvlftData == null) {
			// 添加
			shipArvlftData = new ShipArvlftData();
			ShipArvlftData lastShipArvlft = shipArvlftService.getLastShipArvlft(mmsi);
			if (lastShipArvlft != null) {
				// 离港
				if (lastShipArvlft.getArvlft() == ArvlftCode.arrive) {
					shipArvlftData.setArvlft(ArvlftCode.left);
					shipArvlftData.setPortNo(lastShipArvlft.getPortNo());
					shipArvlftData.setPortData(lastShipArvlft.getPortData());
				} else if (lastShipArvlft.getArvlft() == ArvlftCode.left) {
					// 到港
					shipArvlftData.setArvlft(ArvlftCode.arrive);
					shipArvlftData.setPortNo(lastShipArvlft.getGoPortNo());
					shipArvlftData.setPortData(lastShipArvlft.getGoPortData());
				}
			} else {
				// 到港
				shipArvlftData.setArvlft(ArvlftCode.arrive);
			}
		}

		mav.addObject("shipArvlftData", shipArvlftData);
		// 参数传递,以便和添加同步
		mav.addObject("mmsi", mmsi);

		// 修改的是到港信息
		if (shipArvlftData.getArvlft() == ArvlftCode.arrive) {
			// 到港港口信息
			PortData portData = shipArvlftData.getPortData();
			if (portData != null) {
				List<PortData> arvlftPortDatas = portService
						.getPortDatasByPortCityCode(portData.getPortCity().getCode());
				// 到港城市所对应港口列表
				mav.addObject("arvlftPortDatas", arvlftPortDatas);
			} else {
				List<PortData> arvlftPortDatas = portService
						.getPortDatasByPortCityCode(ScfPortCityCode.GUANGZHOU.getCode());
				// 到港城市所对应港口列表
				mav.addObject("arvlftPortDatas", arvlftPortDatas);
			}

			// 修改所对应的离港信息
			ShipArvlftData leftArvlftData = shipArvlftService.getNextArvlft(id);

			if (leftArvlftData == null)
				leftArvlftData = new ShipArvlftData();
			mav.addObject("leftArvlftData", leftArvlftData);
			// 将去港港口信息
			PortData goPortData = leftArvlftData.getGoPortData();
			if (goPortData != null) {
				List<PortData> goPortDatas = portService.getPortDatasByPortCityCode(goPortData.getPortCity().getCode());
				// 将去港口城市所对应港口列表
				mav.addObject("goPortDatas", goPortDatas);
			} else {
				// 将去港港口信息
				ScfPortCityCode goCity = bigAreas[0].getPortCities().get(0);
				List<PortData> goPortDatas = portService.getPortDatasByPortCityCode(goCity.getCode());
				// 将去港口城市所对应港口列表
				mav.addObject("goPortDatas", goPortDatas);
			}
		} else if (shipArvlftData.getArvlft() == ArvlftCode.left) {
			// 修改的是最后一条离港信息
			// 离港港口信息
			PortData portData = shipArvlftData.getPortData();
			if (portData != null) {
				List<PortData> arvlftPortDatas = portService
						.getPortDatasByPortCityCode(portData.getPortCity().getCode());
				// 离港城市所对应港口列表
				mav.addObject("arvlftPortDatas", arvlftPortDatas);
			} else {
				List<PortData> arvlftPortDatas = portService
						.getPortDatasByPortCityCode(ScfPortCityCode.GUANGZHOU.getCode());
				// 到港城市所对应港口列表
				mav.addObject("arvlftPortDatas", arvlftPortDatas);
			}

			ShipArvlftData previousArvlft = shipArvlftService.getPreviousArvlft(id);
			// 上一条到港信息
			mav.addObject("previousArvlft", previousArvlft);

			// 将去港港口信息
			PortData goPortData = shipArvlftData.getGoPortData();
			if (goPortData != null) {
				List<PortData> goPortDatas = portService.getPortDatasByPortCityCode(goPortData.getPortCity().getCode());
				// 将去港口城市所对应港口列表
				mav.addObject("goPortDatas", goPortDatas);
			} else {
				// 将去港港口信息
				ScfPortCityCode goCity = bigAreas[0].getPortCities().get(0);
				List<PortData> goPortDatas = portService.getPortDatasByPortCityCode(goCity.getCode());
				// 将去港口城市所对应港口列表
				mav.addObject("goPortDatas", goPortDatas);
			}

		}

		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_SHIP_STATE);

		return mav;
	}

	// 船舶动态(装货或者卸货列表)
	@RequestMapping(value = "/myShip/shipArvlft/listUpdown", method = RequestMethod.GET)
	public ModelAndView listUpdown(Long id, String mmsi, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UserData userData = this.getLoginUserData(request);

		if (!userService.isRole(userData, UserPrivilegeCode.sailor))
			throw new Exception("非船员不能进行此项操作！");

		ModelAndView mav = new ModelAndView("/space/space-ship-state-arvlft-updown");

		// 用户信息
		mav.addObject("userData", userData);

		// 到离港信息,含装卸货物列表
		ShipArvlftData updownArvlftData = shipArvlftService.getShipArvlftDataById(id);
		if (updownArvlftData == null)
			updownArvlftData = new ShipArvlftData();
		mav.addObject("updownArvlftData", updownArvlftData);

		mav.addObject("mmsi", mmsi);

		// 港口城市分组列表
		ScfBigAreaCode[] bigAreas = ScfBigAreaCode.values();
		for (ScfBigAreaCode bigArea : bigAreas)
			if (bigArea.getPortCities() == null)
				bigArea.setPortCities(ScfPortCityCode.getPortCities(bigArea));
		mav.addObject("bigAreas", bigAreas);

		// 货类分组列表
		CargoBigTypeCode[] cargoBigTypes = CargoBigTypeCode.values();
		for (CargoBigTypeCode cargoBigType : cargoBigTypes)
			if (cargoBigType.getCargoTypes() == null)
				cargoBigType.setCargoTypes(CargoTypeCode.getCodesByCargoBigType(cargoBigType));
		mav.addObject("cargoBigTypes", cargoBigTypes);

		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_SHIP_STATE);

		return mav;
	}

	// 保存船舶动态(装卸货物)
	@RequestMapping(value = "/myShip/shipArvlft/saveUpdown", method = RequestMethod.POST)
	public void saveUpdown(ShipArvlftData shipArvlftData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.sailor))
				throw new Exception("非船员不能进行此项操作！");

			String[] cargoTypes = ServletRequestUtils.getStringParameters(request, "cargoType");
			String[] cargoNames = ServletRequestUtils.getStringParameters(request, "cargoName");
			double[] tonTeus = ServletRequestUtils.getDoubleParameters(request, "tonTeu");
			Long mmsi = ServletRequestUtils.getLongParameter(request, "mmsi", 0L);

			List<ShipUpdownData> shipUpdownDatas = new ArrayList<ShipUpdownData>();

			if (cargoTypes != null && cargoTypes.length > 0) {
				for (int i = 0; i < cargoTypes.length; i++) {
					ShipUpdownData shipUpdownData = new ShipUpdownData();
					shipUpdownData.setCargoType(CargoTypeCode.valueOf(cargoTypes[i]));
					shipUpdownData.setCargoName(cargoNames[i]);
					shipUpdownData.setTonTeu(tonTeus[i]);

					shipUpdownDatas.add(shipUpdownData);
				}
			}
			shipArvlftData.setShipUpdownDatas(shipUpdownDatas);

			Long result = shipArvlftService.saveShipUpdown(shipArvlftData);

			if (result > 0) {
				map.put("mmsi", mmsi);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "提交成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "提交失败!");
			}
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

	// 保存我的船舶动态上报到离列表
	@RequestMapping(value = "/myShip/shipArvlft/saveShipArvlft", method = RequestMethod.POST)
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

			map.put("mmsi", mmsi);
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

	// 删除船舶最后一条上报的信息
	@RequestMapping(value = "/myShip/shipArvlft/delShipArvlft", method = { RequestMethod.DELETE })
	public ModelAndView delShipArvlft(String mmsi, Long arriveId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserData userData = this.getLoginUserData(request);

		if (!userService.isRole(userData, UserPrivilegeCode.sailor))
			throw new Exception("非船员不能进行此项操作！");

		ModelAndView mav = new ModelAndView("redirect:/space/state/myShip/shipArvlft");
		mav.addObject("mmsi", mmsi);

		shipArvlftService.delShipArvlft(arriveId);
		return mav;
	}

	// 导出船舶动态到excel
	@RequestMapping(value = "/myShip/shipArvlft/exportStateToExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public void exportStateToExcel(String mmsi, String startTime, String endTime, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.handler)
					&& !userService.isRole(userData, UserPrivilegeCode.sailor)
					&& !userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东、船员及业务员不能进行此项操作!");

			List<ArvlftShipData> arvlftDatas = (List<ArvlftShipData>) shipArvlftService
					.getShipArvlftDatas(mmsi, startTime, endTime, 1, Constants.ALL_SIZE).getResult();

			String urlPath = shipArvlftService.exportStateExcel(mmsi, startTime, endTime, arvlftDatas);

			if (!"".equals(urlPath)) {
				map.put("urlPath", urlPath);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "导出船舶动态EXCEL文件成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "没有要导出的数据！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "导出EXCEL文件异常！");
			JsonResponser.respondWithText(response, map);
		}
		return;
	}

	// 航次回放
	@RequestMapping(value = "/routePlay", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView routePlay(Long arvlftId, Long shipId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("/space/space-ship-monitor-historyRoutePlay");

		// 查看权限验证
		ShipData myShipData = shipService.getShipData(shipId);
		if (myShipData == null)
			return mav;
		mav.addObject("myShipData", myShipData);

		ShipArvlftData leftData = null;
		if (arvlftId == null)// 合同跟踪时没有arvlftId参数
			arvlftId = 0L;
		leftData = shipArvlftService.getShipArvlftDataById(arvlftId);
		if (leftData == null)
			leftData = shipArvlftService.getLastShipArvlft(myShipData.getMmsi());
		if (leftData == null)
			return mav;

		mav.addObject("shipArvlftData", leftData);

		List<ShipCooordData> shipCooordDatas = shipArvlftService.getShipCooordDatas(myShipData, leftData);
		// shipMonitorService.gps2baidu(shipCooordDatas);
		mav.addObject("shipCooordDatas", shipCooordDatas);

		// 用户信息
		// mav.addObject("userData", userData);
		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_SHIP_STATE);

		return mav;
	}

}
