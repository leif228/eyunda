package com.hangyi.eyunda.controller.space;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.order.OrderCommonData;
import com.hangyi.eyunda.data.order.OrderDailyData;
import com.hangyi.eyunda.data.order.OrderTemplateData;
import com.hangyi.eyunda.data.order.OrderVoyageData;
import com.hangyi.eyunda.data.ship.SailLineData;
import com.hangyi.eyunda.data.ship.ShipApprovalData;
import com.hangyi.eyunda.data.ship.ShipCabinData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.data.ship.UpDownPortData;
import com.hangyi.eyunda.domain.enumeric.CargoBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.EvalTypeCode;
import com.hangyi.eyunda.domain.enumeric.OrderStateCode;
import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.domain.enumeric.ScfBigAreaCode;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.service.EnumConst.SpaceMenuCode;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.order.OrderCommonService;
import com.hangyi.eyunda.service.order.OrderTemplateService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.MyCabinService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.service.ship.SailLineService;
import com.hangyi.eyunda.service.ship.UpDownPortService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CookieUtil;
import com.hangyi.eyunda.util.HtmlToPdfUtil;
import com.hangyi.eyunda.util.MD5;

@Controller
@RequestMapping("/space/orderCommon")
public class MyOrderCommonController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final int PAGE_SIZE = 10;

	@Autowired
	private OrderCommonService orderCommonService;
	@Autowired
	private OrderTemplateService orderTemplateService;
	@Autowired
	private UserService userService;
	@Autowired
	private MyShipService shipService;
	@Autowired
	private MyCabinService cabinService;
	@Autowired
	private SailLineService sailLineService;
	@Autowired
	private UpDownPortService upDownPortService;

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

	@RequestMapping(value = "/orderList", method = RequestMethod.GET)
	public ModelAndView orderList(Page<OrderCommonData> pageData, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/space/space-orderCommon");

		UserData userData = this.getLoginUserData(request);

		String startTime = ServletRequestUtils.getStringParameter(request, "startTime", "");
		String endTime = ServletRequestUtils.getStringParameter(request, "endTime", "");

		pageData = orderCommonService.getOrderDatas(userData, pageData, startTime, endTime);

		// 输出信息
		mav.addObject("userData", userData);

		if (userService.isRole(userData, UserPrivilegeCode.handler))
			mav.addObject("flag", false); // 不允许不下单就起草合同了
		else
			mav.addObject("flag", false);

		mav.addObject("startTime", startTime);
		mav.addObject("endTime", endTime);

		mav.addObject("pageData", pageData);

		mav.addObject("orderTypeCodes", OrderTypeCode.values());

		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_ORDER);

		return mav;
	}

	@RequestMapping(value = "/orderEdit", method = RequestMethod.GET)
	public ModelAndView orderEdit(Long orderId, Long cabinId, Long cargoId, String selPortNos,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;

		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData selfData = userService.getByCookie(uck);
		if (selfData == null) {
			mav = new ModelAndView("redirect:/portal/login/login");
			return mav;
		}

		UserData userData = this.getLoginUserData(request);

		if (orderId == null)
			orderId = 0L;

		ShipCabinData cabinData = null;

		OrderCommonData orderCommonData = orderCommonService.getOrderData(orderId);
		if (orderCommonData == null) {

			if (cargoId != null) {
				throw new Exception("信息！请联系货主针对你的船盘下订单。");
			} else if (cabinId != null) {
				cabinData = cabinService.getById(cabinId);
				if (cabinData == null) {
					throw new Exception("错误！船盘已经不存在，不能下订单。");
				}

				OrderTypeCode orderType = OrderTypeCode.getCodeByType(cabinData.getWaresBigType(),
						cabinData.getWaresType(), cabinData.getCargoType());

				switch (orderType.getWaresBigType()) {
				case voyage:// 航次运输合同
					OrderVoyageData orderVoyageData = new OrderVoyageData(orderType);
					orderCommonData = orderVoyageData;

					orderVoyageData.setOrderType(orderType);
					orderVoyageData.setUpDate(cabinData.getStartDate());
					orderVoyageData.setDownDate(cabinData.getStartDate());
					orderVoyageData.setDemurrage(cabinData.getDemurrage());

					cabinData.setCurrSailLineData(selPortNos);

					if (cabinData.getCurrSailLineData() != null) {
						orderVoyageData.setDistance(cabinData.getCurrSailLineData().getDistance());
						orderVoyageData.setWeight(cabinData.getCurrSailLineData().getWeight());

						orderVoyageData.setStartPortNo(cabinData.getCurrSailLineData().getStartPortNo());
						orderVoyageData.setStartPort(cabinData.getCurrSailLineData().getStartPortData());
						orderVoyageData.setEndPortNo(cabinData.getCurrSailLineData().getEndPortNo());
						orderVoyageData.setEndPort(cabinData.getCurrSailLineData().getEndPortData());

						String cargoNames = "";
						String tonTeus = "";
						String prices = "";

						Map<Integer, String> mapCargoNames = new TreeMap<Integer, String>();
						Map<Integer, Double> mapTonTeus = new TreeMap<Integer, Double>();
						Map<Integer, Double> mapPrices = cabinData.getCurrSailLineData().getMapPrices();
						if (orderType.getCargoType().getCargoBigType() == CargoBigTypeCode.container) {
							for (Integer key : mapPrices.keySet()) {
								CargoTypeCode ctc = CargoTypeCode.valueOf(key);

								cargoNames += ctc.getShortName() + ",";
								tonTeus += "0,";
								prices += Double.toString(mapPrices.get(key));

								mapCargoNames.put(key, ctc.getShortName());
								mapTonTeus.put(key, 0D);
							}
							if (cargoNames.length() > 0) {
								cargoNames = cargoNames.substring(0, cargoNames.length() - 1);
								tonTeus = tonTeus.substring(0, tonTeus.length() - 1);
								prices = prices.substring(0, prices.length() - 1);
							}
						} else {
							CargoTypeCode ctc = orderType.getCargoType();

							cargoNames = ctc.getShortName();
							tonTeus = "0.00";
							prices = Double.toString(cabinData.getCurrSailLineData().getMapPrices().get(ctc.ordinal()));

							mapCargoNames.put(ctc.ordinal(), ctc.getShortName());
							mapTonTeus.put(ctc.ordinal(), 0D);
						}
						orderVoyageData.setCargoNames(cargoNames);
						orderVoyageData.setTonTeus(tonTeus);
						orderVoyageData.setPrices(prices);

						orderVoyageData.setMapCargoNames(mapCargoNames);
						orderVoyageData.setMapTonTeus(mapTonTeus);
						orderVoyageData.setMapPrices(mapPrices);
					}

					break;
				case daily:// 期租租船合同
					OrderDailyData orderDailyData = new OrderDailyData(orderType);
					orderCommonData = orderDailyData;

					orderDailyData.setOrderType(orderType);

					orderDailyData.setStartDate(cabinData.getStartDate());
					orderDailyData.setEndDate(cabinData.getStartDate());
					orderDailyData.setRcvDate(cabinData.getStartDate());
					orderDailyData.setRetDate(cabinData.getStartDate());

					orderDailyData.setDays(0);
					orderDailyData.setPrice(Double.parseDouble(cabinData.getPrices()));
					orderDailyData.setOilPrice(cabinData.getOilPrice());

					break;
				default:
					throw new Exception("错误！非法的合同类型。");
				}
				orderCommonData.setContainerCount(cabinData.getContainerCount());
				orderCommonData.setPaySteps(cabinData.getPaySteps());
				orderCommonData.setOrderDesc(cabinData.getOrderDesc());// 缺省合同条款

				orderCommonData.setOwnerId(userData.getId());
				orderCommonData.setOwner(userData);
				orderCommonData.setMasterId(cabinData.getMasterId());
				orderCommonData.setMaster(cabinData.getMaster());
				orderCommonData.setBrokerId(cabinData.getBrokerId());
				orderCommonData.setBroker(cabinData.getBroker());
				orderCommonData.setHandlerId(cabinData.getPublisherId());
				orderCommonData.setHandler(cabinData.getPublisher());

				orderCommonData.setShipId(cabinData.getShipId());
				orderCommonData.setShipData(cabinData.getShipData());
			}
		} else {
			cabinData = cabinService.getByCabin(orderCommonData.getOrderType().getWaresBigType(),
					orderCommonData.getOrderType().getWaresType(), orderCommonData.getOrderType().getCargoType(),
					orderCommonData.getShipId(), orderCommonData.getMasterId());

			boolean isOwnerModify = false;
			if (orderCommonData.getState() == OrderStateCode.edit
					&& userData.getId().equals(orderCommonData.getOwnerId()))
				isOwnerModify = true;

			boolean isHandlerModify = false;
			if (orderCommonData.getState().ordinal() < OrderStateCode.endsign.ordinal()
					&& userData.getId().equals(orderCommonData.getHandlerId()))
				isHandlerModify = true;

			if (!isOwnerModify && !isHandlerModify)
				throw new Exception("警告！你无权修改，或该合同已确认不许修改。");
		}
		orderCommonData.setOrderDesc(orderCommonData.getOrderDesc().replace("<br />", "\n"));// 缺省合同条款

		switch (orderCommonData.getOrderType().getWaresBigType()) {
		case voyage:// 航次运输合同
			mav = new ModelAndView("/space/space-order-voyageEdit");

			List<SailLineData> sailLineDatas = null;
			if (cabinData != null)
				sailLineDatas = cabinData.getGotoSailLineDatas();
			else
				sailLineDatas = sailLineService.getSailLinesByType(orderCommonData.getOrderType().getWaresBigType(),
						orderCommonData.getOrderType().getWaresType(), orderCommonData.getOrderType().getCargoType());

			mav.addObject("sailLineDatas", sailLineDatas);
			break;
		case daily:// 期租租船合同
			mav = new ModelAndView("/space/space-order-dailyEdit");

			List<UpDownPortData> upDownPortDatas = null;
			if (cabinData != null)
				upDownPortDatas = cabinData.getGotoUpDownPortDatas();
			else
				upDownPortDatas = upDownPortService.getUpDownPortsByType(
						orderCommonData.getOrderType().getWaresBigType(), orderCommonData.getOrderType().getWaresType(),
						orderCommonData.getOrderType().getCargoType());

			mav.addObject("upDownPortDatas", upDownPortDatas);
			break;
		default:
			throw new Exception("错误！非法的合同类型。");
		}

		// 港口城市分组列表
		ScfBigAreaCode[] bigAreas = ScfBigAreaCode.values();
		for (ScfBigAreaCode bigArea : bigAreas)
			if (bigArea.getPortCities() == null)
				bigArea.setPortCities(ScfPortCityCode.getPortCities(bigArea));
		mav.addObject("bigAreas", bigAreas);

		// 输出数据
		mav.addObject("userData", userData);
		mav.addObject("orderCommonData", orderCommonData);

		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_ORDER);

		return mav;
	}

	// 关键字查询一页用户
	@RequestMapping(value = "/findUsers", method = { RequestMethod.GET, RequestMethod.POST })
	public void findUsers(String userinfoKeyWords, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);
			if (!userService.isRole(userData, UserPrivilegeCode.handler))
				throw new Exception("非业务员不能进行此项操作!");

			List<UserData> userDatas = userService.frontFindUsers(userinfoKeyWords);
			map.put("userDatas", userDatas);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "查询用户成功！");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.toString());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 获取一个用户对象
	@RequestMapping(value = "/getOneUser", method = { RequestMethod.GET, RequestMethod.POST })
	public void getOneUser(Long objUserId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);
			if (!userService.isRole(userData, UserPrivilegeCode.handler))
				throw new Exception("非业务员不能进行此项操作!");

			UserData objUser = userService.getById(objUserId);
			map.put("objUser", objUser);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "查询用户成功！");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.toString());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 关键字查询一页船舶
	@RequestMapping(value = "/findShips", method = { RequestMethod.GET })
	public void findShips(String shipKeyWords, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);
			if (!userService.isRole(userData, UserPrivilegeCode.handler))
				throw new Exception("非业务员不能进行此项操作!");

			List<ShipData> ships = new ArrayList<ShipData>();
			Page<ShipData> page = shipService.findByName(1, shipKeyWords, null);
			if (page != null && !page.getResult().isEmpty())
				ships = page.getResult();

			if (!ships.isEmpty()) {
				map.put("ships", ships);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取得数据成功!");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "未找到匹配成功的船舶!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "查找船舶时出错!");
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 获取一个船舶对象
	@RequestMapping(value = "/getOneShip", method = { RequestMethod.GET })
	public void getOneShip(Long shipId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);
			if (!userService.isRole(userData, UserPrivilegeCode.handler))
				throw new Exception("非业务员不能进行此项操作!");

			ShipData ship = shipService.getShipData(shipId);

			if (ship != null) {
				map.put("ship", ship);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取得数据成功!");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "取得数据失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "取得数据失败!");
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
		return;
	}

	@RequestMapping(value = "/getContacts", method = RequestMethod.GET)
	public void getContacts(Long orderId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> content = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			OrderCommonData orderCommonData = orderCommonService.getMyOrderData(userData, orderId);
			if (orderCommonData == null)
				throw new Exception("错误！你没有指定合同的查看权！");

			List<UserData> contacts = orderCommonService.getOrderContacts(userData.getId(), orderCommonData);

			// 信息
			content.put("contacts", contacts);

			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/orderSave", method = { RequestMethod.GET, RequestMethod.POST })
	public void orderSave(OrderTypeCode orderType, OrderVoyageData orderVoyageData, OrderDailyData orderDailyData,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			OrderCommonData orderCommonData;

			switch (orderType.getWaresBigType()) {
			case voyage:// 航次运输合同
				orderCommonData = orderVoyageData;
				orderVoyageData.setCargoType(orderType.getCargoType());

				String cargoNames = "";
				String tonTeus = "";
				String prices = "";
				if (orderType.getCargoType().getCargoBigType() == CargoBigTypeCode.container) {
					List<CargoTypeCode> ctcs = CargoTypeCode.getCodesByCargoBigType(CargoBigTypeCode.container);
					for (CargoTypeCode ctc : ctcs) {
						String cargoName = ServletRequestUtils.getStringParameter(request, "cargoName-" + ctc.ordinal(),
								"");
						Double tonTeu = ServletRequestUtils.getDoubleParameter(request, "tonTeu-" + ctc.ordinal(), 0D);
						Double price = ServletRequestUtils.getDoubleParameter(request, "price-" + ctc.ordinal(), 0D);
						cargoNames += cargoName + ",";
						tonTeus += tonTeu + ",";
						prices += price + ",";
					}
					cargoNames = cargoNames.substring(0, cargoNames.length() - 1);
					tonTeus = tonTeus.substring(0, tonTeus.length() - 1);
					prices = prices.substring(0, prices.length() - 1);

					orderVoyageData.setCargoNames(cargoNames);
					orderVoyageData.setTonTeus(tonTeus);
					orderVoyageData.setPrices(prices);
				}

				break;
			case daily:// 期租租船合同
				orderCommonData = orderDailyData;
				break;
			default:
				throw new Exception("错误！非法的合同类型。");
			}

			Long orderId = orderCommonService.orderSave(userData, orderCommonData);
			if (orderId > 0) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, orderType.getDescription() + "保存成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, orderType.getDescription() + "保存失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/orderDelete", method = RequestMethod.GET)
	public void orderDelete(Long orderId, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			orderCommonService.orderDelete(userData, orderId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "合同删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/orderSubmit", method = RequestMethod.GET)
	public void orderSubmit(Long orderId, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			orderCommonService.orderSubmit(userData, orderId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "合同提交成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/orderStartSign", method = RequestMethod.GET)
	public void orderStartSign(Long orderId, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			orderCommonService.orderStartSign(userData, orderId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "承运人合同签字成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/orderEndSign", method = RequestMethod.GET)
	public void orderEndSign(Long orderId, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			orderCommonService.orderEndSign(userData, orderId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "托运人合同签字成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/orderArchive", method = RequestMethod.GET)
	public void orderArchive(Long orderId, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			orderCommonService.orderArchive(userData, orderId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "合同归档成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/orderApprovalEdit", method = RequestMethod.GET)
	public void orderApprovalEdit(Long orderId, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			ShipApprovalData shipApprovalData = orderCommonService.getOrderApproval(userData, orderId);

			map.put("shipApprovalData", shipApprovalData);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "合同评价内容获取成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/orderApproval", method = RequestMethod.GET)
	public void orderApproval(Long orderId, EvalTypeCode evalType, String evalContent, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			orderCommonService.orderApproval(userData, orderId, evalType, evalContent);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "合同评价提交成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 合同下载框
	@RequestMapping(value = "/showContainer", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showContainer(Long orderId, OrderTypeCode orderType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserData userData = this.getLoginUserData(request);

		String paraStr = "uid=" + userData.getId() + "&orderId=" + orderId + "&orderType=" + orderType;
		String checksum = MD5.toMD5(paraStr + Constants.SALT_VALUE);

		String urlShow = "http://" + Constants.DOMAIN_NAME + "/space/orderCommon/showOrder?" + paraStr;
		urlShow += "&checksum=" + checksum;

		String urlDownload = "http://" + Constants.DOMAIN_NAME + "/space/orderCommon/downloadOrder?" + paraStr;
		urlDownload += "&checksum=" + checksum;

		ModelAndView mav = new ModelAndView("/manage/template/container");

		mav.addObject("urlShow", urlShow);
		mav.addObject("urlDownload", urlDownload);

		mav.addObject("caller", "web");

		return mav;
	}

	// 合同页面
	@RequestMapping(value = "/showOrder", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showOrder(Long uid, Long orderId, OrderTypeCode orderType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 开始堵漏洞，进行加密验证
		String checksum = ServletRequestUtils.getStringParameter(request, "checksum", "");
		// 合同页面地址
		String paraStr = "uid=" + uid + "&orderId=" + orderId + "&orderType=" + orderType;
		String validchecksum = MD5.toMD5(paraStr + Constants.SALT_VALUE);
		// 校验合法性
		if (!validchecksum.equals(checksum))
			throw new Exception("对不起，你没有查看该合同的权限!");
		// 结束堵漏洞，进行加密验证

		UserData userData = userService.getById(uid);

		OrderCommonData orderCommonData = null;
		if (userData != null)
			orderCommonData = orderCommonService.getMyOrderData(userData.getId(), orderId);// 查找有权限的

		if (orderCommonData == null) {
			switch (orderType.getWaresBigType()) {
			case voyage:// 航次运输合同
				orderCommonData = new OrderVoyageData(orderType);
				break;
			case daily:// 期租租船合同
				orderCommonData = new OrderDailyData(orderType);
				break;
			default:
				throw new Exception("错误！非法的合同类型。");
			}
			OrderTemplateData otd = orderTemplateService.getTemplateByOrderType(orderType);
			if (otd != null)
				orderCommonData.setOrderDesc(otd.getOrderContent());// 缺省合同条款

			if (userData != null) {
				orderCommonData.setOwnerId(userData.getId());
				orderCommonData.setOwner(userData);
				orderCommonData.setMasterId(userData.getId());
				orderCommonData.setMaster(userData);
				orderCommonData.setBrokerId(userData.getId());
				orderCommonData.setBroker(userData);
				orderCommonData.setHandlerId(userData.getId());
				orderCommonData.setHandler(userData);
			}

			ShipData myShipData = new ShipData();
			orderCommonData.setShipData(myShipData);
		}

		ModelAndView mav = new ModelAndView("/manage/template/template-" + orderType.getWaresBigType().toString());
		// 向页面传值
		mav.addObject("orderData", orderCommonData);

		return mav;
	}

	// 生成合同PDF
	@RequestMapping(value = "/downloadOrder", method = { RequestMethod.GET })
	public ModelAndView downloadOrder(Long uid, Long orderId, OrderTypeCode orderType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			// 开始堵漏洞，进行加密验证
			String checksum = ServletRequestUtils.getStringParameter(request, "checksum", "");
			// 合同页面地址
			String paraStr = "uid=" + uid + "&orderId=" + orderId + "&orderType=" + orderType;
			String validchecksum = MD5.toMD5(paraStr + Constants.SALT_VALUE);
			// 校验合法性
			if (!validchecksum.equals(checksum))
				throw new Exception("对不起，你没有查看该合同的权限!");
			// 结束堵漏洞，进行加密验证

			// 使用本地地址，避免死循环
			String urlStr = "http://" + Constants.LOCALE_NAME + "/space/orderCommon/showOrder?" + paraStr;
			urlStr += "&checksum=" + checksum;

			// 模板文件路径
			String realPath = Constants.SHARE_DIR;// 取得不同系统的根目录盘符

			// 模板文件名
			OrderCommonData orderCommonData = orderCommonService.getMyOrderData(uid, orderId);
			String relativePath = "";
			if (orderCommonData != null)// 合同下载文件
				relativePath = ShareDirService.getOrderDir(orderCommonData.getId()) + "/"
						+ orderCommonData.getPdfFileName();
			else// 合同模板下载文件
				relativePath = ShareDirService.getOrderDir(0L) + "/" + orderType.toString() + ".pdf";

			File pdfFile = HtmlToPdfUtil.exportPdfFile(urlStr, realPath + relativePath);
			if (pdfFile.exists()) {
				ModelAndView mav = new ModelAndView("redirect:/space/orderCommon/downloadPdf?url=" + relativePath);
				return mav;
			} else {
				throw new Exception("错误!生成合同文件时出现错误。");
			}
		} catch (Exception e) {
			throw new Exception("错误!生成合同文件时出现错误。");
		}
	}

	// 下载合同PDF文件，下载前检查权限
	@RequestMapping(value = "/downloadPdf", method = { RequestMethod.GET })
	public void downloadPdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FileInputStream fis = null;
		ServletOutputStream sos = null;
		try {
			String urlPath = ServletRequestUtils.getStringParameter(request, "url", "");
			if ("".equals(urlPath)) {
				return;
			}

			File file = new File(Constants.SHARE_DIR + urlPath);
			if (!file.exists()) {
				return;
			}

			// 设置下载文件的类型为任意类型
			response.setContentType("application/x-msdownload");
			// 添加下载文件的头信息。此信息在下载时会在下载面板上显示，比如：
			String fileName = file.getName();
			String downLoadName = new String(fileName.getBytes("GBK"), "iso8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + downLoadName + "\"");

			sos = response.getOutputStream();
			fis = new FileInputStream(file);
			IOUtils.copy(fis, sos);
		} catch (Exception e) {
			logger.error("DOWNLOAD EXCEPTION：", e.getMessage());
			return;
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (sos != null) {
				sos.close();
			}
		}
	}

}
