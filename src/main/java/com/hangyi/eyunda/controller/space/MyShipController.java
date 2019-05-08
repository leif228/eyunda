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
import com.hangyi.eyunda.data.ship.AttrNameData;
import com.hangyi.eyunda.data.ship.ShipAttaData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.data.ship.TypeData;
import com.hangyi.eyunda.domain.enumeric.CargoBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ScfBigAreaCode;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.domain.enumeric.ShipMonitorPlantCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.domain.enumeric.WarrantTypeCode;
import com.hangyi.eyunda.service.EnumConst.RecentPeriodCode;
import com.hangyi.eyunda.service.EnumConst.SpaceMenuCode;
import com.hangyi.eyunda.service.account.DepartmentService;
import com.hangyi.eyunda.service.account.PrivilegeService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.AttributeService;
import com.hangyi.eyunda.service.ship.MyShipAttaService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.service.ship.ShipMonitorPlantService;
import com.hangyi.eyunda.service.ship.TypeService;
import com.hangyi.eyunda.util.CookieUtil;

@Controller
@RequestMapping("/space/ship")
public class MyShipController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final int PAGE_SIZE = 10;
	public static final int MAX_SIZE = 1000;

	@Autowired
	private UserService userService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private MyShipService myShipService;
	@Autowired
	private MyShipAttaService shipAttaService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private ShipMonitorPlantService plantService;
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

	// 我的船舶列表
	@RequestMapping(value = "/myShip", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView myShip(Page<ShipData> pageData, Long deptId, String keyWords, HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView("/space/space-ship");

		UserData userData = this.getLoginUserData(request);

		pageData.setPageSize(PAGE_SIZE);

		// 船舶组别列表
		List<DepartmentData> departmentDatas = null;
		if (deptId == null)
			deptId = -1L;

		if (userService.isRole(userData, UserPrivilegeCode.handler)) {
			departmentDatas = departmentService.getDepartmentDatas(userData.getCurrCompanyData().getId(),
					UserPrivilegeCode.master);
			pageData = myShipService.getShipPage(userData.getCurrCompanyData().getId(), keyWords, pageData.getPageNo(),
					pageData.getPageSize(), "", deptId, 0L);
		} else if (userService.isRole(userData, UserPrivilegeCode.master)) {
			pageData = myShipService.getShipPage(userData.getCurrCompanyData().getId(), keyWords, pageData.getPageNo(),
					pageData.getPageSize(), "", 0L, userData.getId());
		} else if (userService.isRole(userData, UserPrivilegeCode.sailor)) {
			String mmsi = privilegeService.getShipMmsiForSailor(userData.getId(),
					userData.getCurrCompanyData().getId());
			pageData = myShipService.getShipPage(userData.getCurrCompanyData().getId(), keyWords, pageData.getPageNo(),
					pageData.getPageSize(), mmsi, 0L, 0L);
		} else {
			mav.addObject("flag", true);
			mav.addObject("msg", "非船东、船员及业务员不能进行此项操作！");
		}

		// 输出数据
		if (userService.isRole(userData, UserPrivilegeCode.master))
			mav.addObject("canAdd", true);
		else
			mav.addObject("canAdd", false);

		mav.addObject("userData", userData);
		mav.addObject("departmentDatas", departmentDatas);
		mav.addObject("deptId", deptId);

		mav.addObject("pageData", pageData);
		mav.addObject("keyWords", keyWords);

		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_SHIP);

		return mav;
	}

	// 编辑或添加船舶信息
	@RequestMapping(value = "/myShip/edit", method = { RequestMethod.GET })
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserData userData = this.getLoginUserData(request);

		if (!userService.isRole(userData, UserPrivilegeCode.master))
			throw new Exception("非船东不能进行此项操作!");

		String tab = ServletRequestUtils.getStringParameter(request, "tab", "ship-baseinfo");
		Long myShipId = ServletRequestUtils.getLongParameter(request, "id", 0L);

		ModelAndView mav = new ModelAndView("/space/space-ship-edit");
		mav.addObject("tab", tab);

		mav.addObject("userData", userData);

		// 取出船舶信息
		ShipData myShipData = myShipService.getFullShipData(myShipId);

		if (myShipData == null)
			myShipData = new ShipData();
		else if (!myShipData.getMasterId().equals(userData.getId()))
			throw new Exception("非船东自己的船舶不能进行此项操作！");

		mav.addObject("myShipData", myShipData);

		// 船舶类别分组列表
		List<TypeData> uncleDatas = typeService.getUncleDatas();
		mav.addObject("uncleDatas", uncleDatas);

		// 港口城市分组列表
		ScfBigAreaCode[] bigAreas = new ScfBigAreaCode[1];
		bigAreas[0] = ScfBigAreaCode.ZHUSANJIAO;
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

		// 到达期限列表
		mav.addObject("recentPeriodCodes", RecentPeriodCode.values());

		// 委托类型下拉
		WarrantTypeCode[] warrantTypes = WarrantTypeCode.values();
		mav.addObject("warrantTypes", warrantTypes);

		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_SHIP);

		return mav;
	}

	// 保存船舶基本信息
	@RequestMapping(value = "/myShip/saveBaseInfo", method = { RequestMethod.POST })
	public void saveBaseInfo(ShipData myShipData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作!");

			myShipData.setMasterId(userData.getId());

			if (myShipService.checkUploadFile(myShipData)) {
				Long result = myShipService.saveOrUpdate(userData, myShipData);
				if (result > 0) {
					map.put("shipId", result);
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
					map.put(JsonResponser.MESSAGE, "提交成功！");
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "船名重复或MMSI重复,提交失败！");
				}
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "未上传图片或格式不正确或大小超过1M,请检查!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
		return;
	}

	// 保存船舶认证资料
	@RequestMapping(value = "/myShip/saveAudit", method = { RequestMethod.POST })
	public void saveAudit(ShipData myShipData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作!");

			if (myShipService.checkAuditFile(myShipData)) {
				Long result = myShipService.saveAudit(userData, myShipData);
				if (result > 0) {
					map.put("shipId", result);
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
					map.put(JsonResponser.MESSAGE, "提交成功！");
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "没有操作权限,提交失败！");
				}
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "未上传图片或格式不正确或大小超过1M,请检查!");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithText(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "提交失败！");
			JsonResponser.respondWithText(response, map);
		}
		return;
	}

	// 保存船舶类别信息
	@RequestMapping(value = "/myShip/saveSortInfo", method = { RequestMethod.POST })
	public void saveSortInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作!");

			Long id = ServletRequestUtils.getLongParameter(request, "id", 0L);
			ShipData myShipData = myShipService.getShipData(id);

			if (myShipData != null) {
				List<AttrNameData> attrNameDatas = attributeService
						.getTypeAttrNameDatas(myShipData.getTypeData().getTypeCode());
				Map<String, Object> mapParam = new HashMap<String, Object>();
				for (AttrNameData attrNameData : attrNameDatas) {
					switch (attrNameData.getAttrType()) {
					case charcode:
						String charcodeParam = ServletRequestUtils.getStringParameter(request,
								"a" + attrNameData.getAttrNameCode(), "");
						mapParam.put("a" + attrNameData.getAttrNameCode(), charcodeParam);
						break;
					case charstr:
						String charstrParam = ServletRequestUtils.getStringParameter(request,
								"a" + attrNameData.getAttrNameCode(), "");
						mapParam.put("a" + attrNameData.getAttrNameCode(), charstrParam);
						break;
					case intnum:
						Integer intnumParam = ServletRequestUtils.getIntParameter(request,
								"a" + attrNameData.getAttrNameCode(), 0);
						mapParam.put("a" + attrNameData.getAttrNameCode(), intnumParam);
						break;
					case dblnum:
						Double dblnumParam = ServletRequestUtils.getDoubleParameter(request,
								"a" + attrNameData.getAttrNameCode(), 0.00D);
						mapParam.put("a" + attrNameData.getAttrNameCode(), dblnumParam);
						break;
					case booltype:
						String booltypeParam = ServletRequestUtils.getStringParameter(request,
								"a" + attrNameData.getAttrNameCode(), "");
						mapParam.put("a" + attrNameData.getAttrNameCode(), booltypeParam);
						break;
					case datetype:
						String datetypeParam = ServletRequestUtils.getStringParameter(request,
								"a" + attrNameData.getAttrNameCode(), "");
						mapParam.put("a" + attrNameData.getAttrNameCode(), datetypeParam);
						break;
					}
				}

				boolean result = myShipService.saveSortInfos(userData, myShipData, attrNameDatas, mapParam);

				if (result) {
					map.put("shipId", myShipData.getId());
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
					map.put(JsonResponser.MESSAGE, "提交成功！");
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "没有操作权限,提交失败！");
				}
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "船舶信息不存在,请检查!");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithText(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "提交失败！");
			JsonResponser.respondWithText(response, map);
		}
	}

	// 保存船舶详细信息
	@RequestMapping(value = "/myShip/saveDetailInfo", method = { RequestMethod.POST })
	public void saveDetailInfo(ShipAttaData myShipAttaData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作!");

			boolean result = myShipService.saveMyShipDetail(userData, myShipAttaData);
			if (result) {
				map.put("shipId", myShipAttaData.getShipId());
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "提交成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "提交失败！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithText(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "提交失败！");
			JsonResponser.respondWithText(response, map);
		}
		return;
	}

	// 保存船舶接货信息
	@RequestMapping(value = "/myShip/saveDelivery", method = { RequestMethod.POST })
	public void saveDelivery(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作!");

			String[] cargoTypes = ServletRequestUtils.getStringParameters(request, "cargoType");
			String[] cityorports = ServletRequestUtils.getStringParameters(request, "portCityCode");
			long shipId = ServletRequestUtils.getLongParameter(request, "shipId", 0);

			boolean result = myShipService.saveDeliveryInfo(userData, shipId, cargoTypes, cityorports);
			if (result) {
				map.put("shipId", shipId);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "提交成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "提交失败！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithText(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "提交失败！");
			JsonResponser.respondWithText(response, map);
		}
		return;
	}

	// 船舶附件上移
	@RequestMapping(value = "/myShip/moveAttaUp", method = { RequestMethod.GET })
	public void moveAttaUp(Long attaId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作!");

			boolean result = myShipService.moveAttaUp(userData, attaId);
			if (result) {
				map.put("shipId", shipAttaService.getShipId(attaId));
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "提交成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "提交失败！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "提交失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	// 船舶附件下移
	@RequestMapping(value = "/myShip/moveAttaDown", method = { RequestMethod.GET })
	public void moveAttaDown(Long attaId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作!");

			boolean result = myShipService.moveAttaDown(userData, attaId);
			if (result) {
				map.put("shipId", shipAttaService.getShipId(attaId));
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "提交成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "提交失败！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "提交失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	// 船舶附件删除
	@RequestMapping(value = "/myShip/removeAtta", method = { RequestMethod.DELETE })
	public ModelAndView removeAtta(Long attaId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserData userData = this.getLoginUserData(request);

		if (!userService.isRole(userData, UserPrivilegeCode.master))
			throw new Exception("非船东不能进行此项操作!");

		ModelAndView mav = new ModelAndView("redirect:/space/ship/myShip/edit");
		mav.addObject("tab", "ship-detail");
		mav.addObject("id", shipAttaService.getShipId(attaId));

		myShipService.removeShipAtta(userData, attaId);

		return mav;
	}

	// 提交船舶
	@RequestMapping(value = "/myShip/commit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView commit(Long id, Long pageNo, Long deptId, String keyWords, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserData userData = this.getLoginUserData(request);

		if (!userService.isRole(userData, UserPrivilegeCode.master))
			throw new Exception("非船东不能进行此项操作!");

		myShipService.commitMyShip(userData, id);

		// 重定向到/space/ship/myShip
		ModelAndView mav = new ModelAndView("redirect:/space/ship/myShip");

		mav.addObject("pageNo", pageNo);
		mav.addObject("deptId", deptId);
		mav.addObject("keyWords", keyWords);

		return mav;
	}

	// 发布船舶
	@RequestMapping(value = "/myShip/publish", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView publish(Long id, Long pageNo, Long deptId, String keyWords, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserData userData = this.getLoginUserData(request);

		if (!userService.isRole(userData, UserPrivilegeCode.master))
			throw new Exception("非船东不能进行此项操作!");

		myShipService.publishMyShip(userData, id);

		// 重定向到/space/ship/myShip
		ModelAndView mav = new ModelAndView("redirect:/space/ship/myShip");

		mav.addObject("pageNo", pageNo);
		mav.addObject("deptId", deptId);
		mav.addObject("keyWords", keyWords);

		return mav;
	}

	// 取消发布的船舶
	@RequestMapping(value = "/myShip/unpublish", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView unpublish(Long id, Long pageNo, Long deptId, String keyWords, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserData userData = this.getLoginUserData(request);

		if (!userService.isRole(userData, UserPrivilegeCode.master))
			throw new Exception("非船东不能进行此项操作!");

		myShipService.unpublishMyShip(userData, id);

		// 重定向到/space/ship/myShip
		ModelAndView mav = new ModelAndView("redirect:/space/ship/myShip");

		mav.addObject("pageNo", pageNo);
		mav.addObject("deptId", deptId);
		mav.addObject("keyWords", keyWords);

		return mav;
	}

	// 删除船舶信息
	@RequestMapping(value = "/myShip/delete", method = { RequestMethod.DELETE })
	public ModelAndView delete(Long id, Long pageNo, Long deptId, String keyWords, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserData userData = this.getLoginUserData(request);

		if (!userService.isRole(userData, UserPrivilegeCode.master))
			throw new Exception("非船东不能进行此项操作!");

		boolean result = myShipService.deleteShip(userData, id);

		if (result) {
			// 重定向到/space/ship/myShip
			ModelAndView mav = new ModelAndView("redirect:/space/ship/myShip");

			mav.addObject("pageNo", pageNo);
			mav.addObject("deptId", deptId);
			mav.addObject("keyWords", keyWords);

			return mav;
		} else {
			throw new Exception("错误！你没有删除该船舶的权限，或者有合同关联不能删除。");
		}
	}

	// 设置数据来源平台
	@RequestMapping(value = "/myShip/setPlant", method = { RequestMethod.POST, RequestMethod.GET })
	public void setMonitorDataSourc(Long shipId, ShipMonitorPlantCode plantName, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作!");

			boolean result = plantService.savePlant(shipId, plantName);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "设置数据源成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "设置数据源失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "设置数据源出错！");
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 取得平台码
	@RequestMapping(value = "/myShip/getPlant", method = { RequestMethod.POST, RequestMethod.GET })
	public void setMonitorDataSource(Long shipId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShipData shipData = myShipService.getShipData(shipId);

			if (shipData != null) {
				Map<String, String> plantMap = new HashMap<String, String>();
				ShipMonitorPlantCode[] monitorPlants = ShipMonitorPlantCode.values();
				for (ShipMonitorPlantCode monitorPlant : monitorPlants) {
					plantMap.put(monitorPlant.toString(), monitorPlant.getDescription());
				}
				map.put("MDS", shipData.getShipPlant());
				map.put("plantCode", plantMap);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取得数据成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "未找到该样板合同条款！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "查找用户时出错！");
		}
		JsonResponser.respondWithJson(response, map);
	}

}
