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
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.data.ship.AttrNameData;
import com.hangyi.eyunda.data.ship.ShipAttaData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.data.ship.ShipPortData;
import com.hangyi.eyunda.data.ship.TypeData;
import com.hangyi.eyunda.domain.enumeric.ArvlftCode;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ScfBigAreaCode;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.domain.enumeric.ShipMonitorPlantCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.service.account.DepartmentService;
import com.hangyi.eyunda.service.account.PrivilegeService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.AttributeService;
import com.hangyi.eyunda.service.ship.MyShipCargoTypeService;
import com.hangyi.eyunda.service.ship.MyShipPortService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.service.ship.PortService;
import com.hangyi.eyunda.service.ship.ShipMonitorPlantService;
import com.hangyi.eyunda.service.ship.TypeService;
import com.hangyi.eyunda.util.ArrayUtil;

@Controller
@RequestMapping("/mobile/ship")
public class MblShipController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final int PAGE_SIZE = 10;

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private UserService userService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private MyShipService shipService;
	@Autowired
	private PortService portService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private MyShipCargoTypeService shipCargoTypeService;
	@Autowired
	private MyShipPortService shipPortService;
	@Autowired
	private ShipMonitorPlantService plantService;
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

	/**
	 * 3.船管、动态、监控、船盘，业务员可分组见全部，船东可见自己有船舶经营权的，船员可见自己有报动态权的，
	 * 业务员可增加船东的船舶，船东可增加自己的船舶， 船员可增加船舶动态， 业务员可发布船东的船盘，船东可发布自己的船盘，但谁发布的联系谁；
	 */
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

			// 输出数据
			if (userService.isRole(userData, UserPrivilegeCode.master))
				content.put("canAdd", true);
			else
				content.put("canAdd", false);

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

	@RequestMapping(value = "/myShip/edit", method = { RequestMethod.GET })
	public void edit(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String tab = ServletRequestUtils.getStringParameter(request, "tab", "ship-baseinfo");
			Long myShipId = ServletRequestUtils.getLongParameter(request, "id", 0L);

			// 当前编辑分项
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("tab", tab);

			UserData userData = this.getLoginUserData(request);
			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");
			content.put("userData", userData);

			// 取出船舶信息
			ShipData shipData = shipService.getFullShipData(myShipId);
			if (shipData == null)
				shipData = new ShipData();
			else if (!shipData.getMasterId().equals(userData.getId()))
				throw new Exception("非船东自己的船舶不能进行此项操作！");
			content.put("shipData", shipData);

			// 船舶类别列表
			List<TypeData> uncleTypeDatas = typeService.getUncleDatas();
			content.put("uncleTypeDatas", uncleTypeDatas);

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

	// 保存基本信息
	@RequestMapping(value = "/myShip/saveBaseInfo", method = { RequestMethod.POST })
	public void saveBaseInfo(ShipData myShipData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			myShipData.setMasterId(userData.getId());

			Long result = shipService.saveOrUpdate(userData, myShipData);

			if (result > 0) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "没有操作权限或船名重复或MMSI重复,提交失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 保存船舶认证资料
	@RequestMapping(value = "/myShip/saveAudit", method = { RequestMethod.POST })
	public void saveAudit(ShipData myShipData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			Long result = shipService.saveAudit(userData, myShipData);

			if (result > 0) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "没有操作权限,提交失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myShip/saveDetailInfo", method = { RequestMethod.POST })
	public void saveDetailInfo(ShipAttaData myShipAttaData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			boolean result = shipService.saveMyShipDetail(userData, myShipAttaData);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myShip/removeAtta", method = { RequestMethod.GET })
	public void removeAtta(Long attaId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			boolean result = shipService.removeShipAtta(userData, attaId);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myShip/getCityCodePorts", method = { RequestMethod.GET })
	public void getCityCodePorts(String portCityCode, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<PortData> portDatas = portService.getPortDatasByPortCityCode(portCityCode);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, portDatas);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 提交给后台发布，暂时未用
	@RequestMapping(value = "/myShip/commit", method = { RequestMethod.GET, RequestMethod.POST })
	public void commitShip(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			boolean result = shipService.commitMyShip(userData, id);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "提交审核信息成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "提交审核信息失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);

	}

	// 发布
	@RequestMapping(value = "/myShip/publish", method = { RequestMethod.POST })
	public void publishShip(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			boolean result = shipService.publishMyShip(userData, id);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 取消发布
	@RequestMapping(value = "/myShip/unpublish", method = { RequestMethod.POST })
	public void unpublish(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			boolean result = shipService.unpublishMyShip(userData, id);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 删除
	@RequestMapping(value = "/delete", method = { RequestMethod.GET })
	public void deleteShip(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			boolean result = shipService.deleteShip(userData, id);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 获取一个对象，增加一个新港口
	@RequestMapping(value = "/myShip/addNewPort", method = { RequestMethod.GET })
	public void addNewPort(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			ScfBigAreaCode[] bigAreas = ScfBigAreaCode.values();

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功");
			map.put(JsonResponser.CONTENT, this.getMapBigAreas(bigAreas));
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	private List<Map<String, Object>> getMapBigAreas(ScfBigAreaCode[] bigAreas) {
		List<Map<String, Object>> mapBigAreas = new ArrayList<Map<String, Object>>();
		for (ScfBigAreaCode bigArea : bigAreas) {
			Map<String, Object> mapBigArea = new HashMap<String, Object>();

			mapBigArea.put("code", bigArea.getCode());
			mapBigArea.put("description", bigArea.getDescription());

			List<ScfPortCityCode> portCityCodes = ScfPortCityCode.getPortCities(bigArea);
			List<Map<String, Object>> mapPortCityCodes = new ArrayList<Map<String, Object>>();
			for (ScfPortCityCode portCityCode : portCityCodes) {
				mapPortCityCodes.add(this.getMapPortCity(portCityCode));
			}
			mapBigArea.put("portCities", mapPortCityCodes);

			mapBigAreas.add(mapBigArea);
		}

		return mapBigAreas;
	}

	private Map<String, Object> getMapPortCity(ScfPortCityCode portCityCode) {
		Map<String, Object> mapPortCityCode = new HashMap<String, Object>();

		mapPortCityCode.put("code", portCityCode.getCode());
		mapPortCityCode.put("description", portCityCode.getDescription());

		return mapPortCityCode;
	}

	@RequestMapping(value = "/myShip/saveNewPort", method = { RequestMethod.GET })
	public void saveNewPort(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			String portCityCode = ServletRequestUtils.getStringParameter(request, "portCityCode", "");
			String portNo = ServletRequestUtils.getStringParameter(request, "portNo", "");
			String portName = ServletRequestUtils.getStringParameter(request, "portName", "");

			boolean result = portService.save(portCityCode, portNo, portName);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myShip/saveTypeSort", method = { RequestMethod.POST })
	public void saveSortInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			Long id = ServletRequestUtils.getLongParameter(request, "id", 0L);
			ShipData myShipData = shipService.getShipData(id);

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

				shipService.saveSortInfos(userData, myShipData, attrNameDatas, mapParam);

				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 保存船舶报价信息
	@RequestMapping(value = "/myShip/saveDelivery", method = { RequestMethod.POST })
	public void saveDelivery(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			String[] cargoTypes = ServletRequestUtils.getStringParameter(request, "cargoType").split(", ");
			String[] cityorports = ServletRequestUtils.getStringParameter(request, "cityorport").split(", ");
			long shipId = ServletRequestUtils.getLongParameter(request, "shipId", 0);

			// 保存接货类别
			boolean result1 = shipCargoTypeService.saveCargoTypes(shipId, cargoTypes);
			// 保存接货港口
			boolean result2 = shipPortService.saveShipPorts(shipId, cityorports);

			if (result1 == true && result2 == true) {
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

	// 接货信息
	@RequestMapping(value = "/myShip/getShipDelivery", method = { RequestMethod.GET })
	public void getTypeAndPort(Long shipId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String contentMd5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

		Map<String, Object> content = new HashMap<String, Object>();

		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			// 接货类别
			List<CargoTypeCode> typList = shipCargoTypeService.getShipCargoTypes(shipId);
			// 接货港口
			List<ShipPortData> portList = shipPortService.getShipPortDatas(shipId);
			content.put("typList", typList);
			content.put("portList", portList);
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMd5);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "提交失败！");
		}
		JsonResponser.respondWithText(response, map);
	}

	// 设置数据来源平台
	@RequestMapping(value = "/myShip/setPlant", method = { RequestMethod.POST, RequestMethod.GET })
	public void setPlant(Long shipId, ShipMonitorPlantCode plantName, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			plantService.savePlant(shipId, plantName);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 取得数据来源平台
	@RequestMapping(value = "/myShip/getPlant", method = { RequestMethod.POST, RequestMethod.GET })
	public void getPlant(String mmsi, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> content = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			ShipMonitorPlantCode plantName = plantService.getPlant(mmsi);
			content.put("plantName", plantName);
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 获取当前船舶所在港口
	@RequestMapping(value = "/getCurrentPort", method = { RequestMethod.GET, RequestMethod.POST })
	public void myShip(String mmsi, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.master))
				throw new Exception("非船东不能进行此项操作！");

			String shipName = shipService.getMyShipName(mmsi);

			List<PortData> ports = portService.getPortDatas();
			List<PortData> currentPorts = ArrayUtil.randomTopic(ports, 1);

			Map<String, Object> content = new HashMap<String, Object>();
			content.put("shipName", shipName);
			content.put("portData", currentPorts.get(0));
			content.put("arvlftCode", ArvlftCode.arrive);

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
}
