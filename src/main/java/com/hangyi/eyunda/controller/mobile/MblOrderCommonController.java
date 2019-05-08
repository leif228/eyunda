package com.hangyi.eyunda.controller.mobile;

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

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.order.OrderCommonData;
import com.hangyi.eyunda.data.order.OrderDailyData;
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
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.order.OrderCommonService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.MyCabinService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.service.ship.SailLineService;
import com.hangyi.eyunda.service.ship.UpDownPortService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.HtmlToPdfUtil;
import com.hangyi.eyunda.util.MD5;

@Controller
@RequestMapping("/mobile/orderCommon")
public class MblOrderCommonController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final int PAGE_SIZE = 20;

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private OrderCommonService orderCommonService;
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

	@RequestMapping(value = "/orderList", method = RequestMethod.GET)
	public void orderList(Page<OrderCommonData> pageData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> content = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			String startTime = ServletRequestUtils.getStringParameter(request, "startTime", "");
			String endTime = ServletRequestUtils.getStringParameter(request, "endTime", "");

			pageData = orderCommonService.getOrderDatas(userData, pageData, startTime, endTime);

			// 输出信息
			content.put("userData", userData);

			if (userService.isRole(userData, UserPrivilegeCode.handler))
				content.put("flag", false); // 不允许不下单就起草合同了
			else
				content.put("flag", false);

			content.put("startTime", startTime);
			content.put("endTime", endTime);

			content.put("pageData", pageData);

			content.put("orderTypeCodes", OrderTypeCode.values());

			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/orderEdit", method = RequestMethod.GET)
	public void orderEdit(Long orderId, Long cabinId, String selPortNos, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> content = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (orderId == null)
				orderId = 0L;

			ShipCabinData cabinData = null;

			OrderCommonData orderCommonData = orderCommonService.getOrderData(orderId);
			if (orderCommonData == null) {

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
				List<SailLineData> sailLineDatas = null;
				if (cabinData != null)
					sailLineDatas = cabinData.getSailLineDatas();
				else
					sailLineDatas = sailLineService.getSailLinesByType(orderCommonData.getOrderType().getWaresBigType(),
							orderCommonData.getOrderType().getWaresType(),
							orderCommonData.getOrderType().getCargoType());

				content.put("sailLineDatas", sailLineDatas);
				break;
			case daily:// 期租租船合同
				List<UpDownPortData> upDownPortDatas = null;
				if (cabinData != null)
					upDownPortDatas = cabinData.getUpDownPortDatas();
				else
					upDownPortDatas = upDownPortService.getUpDownPortsByType(
							orderCommonData.getOrderType().getWaresBigType(),
							orderCommonData.getOrderType().getWaresType(),
							orderCommonData.getOrderType().getCargoType());

				content.put("upDownPortDatas", upDownPortDatas);
				break;
			default:
				throw new Exception("错误！非法的合同类型。");
			}

			// 港口城市分组列表
			ScfBigAreaCode[] bigAreas = ScfBigAreaCode.values();
			for (ScfBigAreaCode bigArea : bigAreas)
				if (bigArea.getPortCities() == null)
					bigArea.setPortCities(ScfPortCityCode.getPortCities(bigArea));
			content.put("bigAreas", bigAreas);

			// 输出数据
			content.put("userData", userData);
			content.put("orderCommonData", orderCommonData);

			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	// 关键字查询一页用户
	@RequestMapping(value = "/findUsers", method = { RequestMethod.GET, RequestMethod.POST })
	public void findUsers(String userinfoKeyWords, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> content = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);
			if (!userService.isRole(userData, UserPrivilegeCode.handler))
				throw new Exception("非业务员不能进行此项操作!");

			List<UserData> userDatas = userService.frontFindUsers(userinfoKeyWords);

			content.put("userDatas", userDatas);

			map.put(JsonResponser.CONTENT, content);
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
		Map<String, Object> content = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);
			if (!userService.isRole(userData, UserPrivilegeCode.handler))
				throw new Exception("非业务员不能进行此项操作!");

			UserData objUser = userService.getById(objUserId);
			content.put("objUser", objUser);

			map.put(JsonResponser.CONTENT, content);
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
		Map<String, Object> content = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);
			if (!userService.isRole(userData, UserPrivilegeCode.handler))
				throw new Exception("非业务员不能进行此项操作!");

			ShipData ship = shipService.getShipData(shipId);

			if (ship != null) {
				content.put("ship", ship);
				map.put(JsonResponser.CONTENT, content);
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
			case voyage:// 集装箱航次运输合同
				orderCommonData = orderVoyageData;

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
				} else {
					String cargoName = ServletRequestUtils.getStringParameter(request, "cargoName-0", "");
					Double tonTeu = ServletRequestUtils.getDoubleParameter(request, "tonTeu-0", 0D);
					Double price = ServletRequestUtils.getDoubleParameter(request, "price-0", 0D);
					cargoNames += cargoName + "";
					tonTeus += tonTeu + "";
					prices += price + "";
				}
				orderVoyageData.setCargoType(orderType.getCargoType());
				orderVoyageData.setCargoNames(cargoNames);
				orderVoyageData.setTonTeus(tonTeus);
				orderVoyageData.setPrices(prices);
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
		Map<String, Object> content = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			ShipApprovalData shipApprovalData = orderCommonService.getOrderApproval(userData, orderId);

			content.put("shipApprovalData", shipApprovalData);

			map.put(JsonResponser.CONTENT, content);
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

		return mav;
	}

	// 生成合同PDF
	@RequestMapping(value = "/downloadOrder", method = { RequestMethod.GET })
	public void downloadOrder(Long orderId, OrderTypeCode orderType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			UserData userData = this.getLoginUserData(request);

			String paraStr = "uid=" + userData.getId() + "&orderId=" + orderId + "&orderType=" + orderType;
			String checksum = MD5.toMD5(paraStr + Constants.SALT_VALUE);

			String urlShow = "http://" + Constants.LOCALE_NAME + "/space/orderCommon/showOrder?" + paraStr;
			urlShow += "&checksum=" + checksum;

			// 模板文件路径
			String realPath = Constants.SHARE_DIR;// 取得不同系统的根目录盘符

			// 模板文件名
			OrderCommonData orderCommonData = orderCommonService.getMyOrderData(userData, orderId);
			String relativePath = "";
			if (orderCommonData != null)// 合同下载文件
				relativePath = ShareDirService.getOrderDir(orderCommonData.getId()) + "/"
						+ orderCommonData.getPdfFileName();
			else
				// 合同模板下载文件
				relativePath = ShareDirService.getOrderDir(0L) + "/" + orderType.toString() + ".pdf";

			File pdfFile = HtmlToPdfUtil.exportPdfFile(urlShow, realPath + relativePath);
			if (pdfFile.exists()) {
				// 设置下载文件的类型为任意类型
				response.setContentType("application/x-msdownload");
				// 添加下载文件的头信息。此信息在下载时会在下载面板上显示，比如：
				String fileName = pdfFile.getName();
				String downLoadName = new String(fileName.getBytes("GBK"), "iso8859-1");
				response.setHeader("Content-Disposition", "attachment;filename=\"" + downLoadName + "\"");
				response.setHeader("Content_Length", String.valueOf(pdfFile.length()));
				ServletOutputStream sos = response.getOutputStream();
				FileInputStream fis = new FileInputStream(pdfFile);

				IOUtils.copy(fis, sos);

				fis.close();
				sos.close();
			} else {
				throw new Exception("错误!生成合同文件时出现错误。");
			}
		} catch (Exception e) {
			throw new Exception("错误!生成合同文件时出现错误。");
		}
	}

}
