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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.account.OperatorData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.cargo.CargoData;
import com.hangyi.eyunda.data.manage.UpdateInfoData;
import com.hangyi.eyunda.data.notice.NoticeData;
import com.hangyi.eyunda.data.order.EvaluateData;
import com.hangyi.eyunda.data.ship.SailLineData;
import com.hangyi.eyunda.data.ship.ShipCabinData;
import com.hangyi.eyunda.data.ship.ShipCooordData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.data.ship.TypeData;
import com.hangyi.eyunda.data.ship.UpDownPortData;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ScfBigAreaCode;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;
import com.hangyi.eyunda.service.account.OperatorService;
import com.hangyi.eyunda.service.cargo.CargoService;
import com.hangyi.eyunda.service.notice.NoticeService;
import com.hangyi.eyunda.service.portal.home.EvaluateService;
import com.hangyi.eyunda.service.portal.home.HomeService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.MyCabinService;
import com.hangyi.eyunda.service.ship.MyShipArvlftService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.service.ship.SailLineService;
import com.hangyi.eyunda.service.ship.TypeService;
import com.hangyi.eyunda.service.ship.UpDownPortService;
import com.hangyi.eyunda.util.FileUtil;

@Controller
@RequestMapping("/mobile/home")
public class MblHomeController {// 统一登录
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final int PAGE_SIZE = 20;

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private UserService userService;
	@Autowired
	private HomeService homeService;
	@Autowired
	private EvaluateService evaluateService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private MyShipService myShipService;
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private CargoService cargoService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private MyCabinService cabinService;
	@Autowired
	private SailLineService sailLineService;
	@Autowired
	private UpDownPortService upDownPortService;
	@Autowired
	private MyShipArvlftService shipArvlftService;

	/**
	 * 代理人列表
	 */
	@RequestMapping(value = "/operatorList", method = { RequestMethod.GET, RequestMethod.POST })
	public void operatorList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> content = new HashMap<String, Object>();
			Integer pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			Page<OperatorData> pageData = operatorService.getOperatorDatas(PAGE_SIZE, pageNo);

			content.put("operatorList", pageData.getResult());
			content.put("total", pageData.getTotalPages());
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "读取数据成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	/**
	 * 进入搜索页
	 */
	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public void search(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNo = 1;
		try {
			String keyWords = ServletRequestUtils.getStringParameter(request, "keyWords", "");

			// 获取选中栏目信息
			String searchRlsCode = ServletRequestUtils.getStringParameter(request, "searchRlsCode", "");

			Map<String, Object> content = new HashMap<String, Object>();
			if (searchRlsCode.endsWith("shipsearch")) {
				// 搜船盘
				Page<ShipCabinData> cabinPage = new Page<ShipCabinData>();
				cabinPage.setPageNo(1);
				cabinPage.setPageSize(PAGE_SIZE);
				cabinService.getCabinHomePage(cabinPage, null, null, null, null, keyWords);
				content.put("pageData", cabinPage);
			} else {
				// 搜货pageNo, pageSize, portCity, keyWords
				Page<CargoData> pageCargoData = homeService.getHomeCargoList(PAGE_SIZE, pageNo, keyWords, null, null);
				content.put("pageData", pageCargoData);
			}
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	/** 查找联系人 */
	@RequestMapping(value = "/findCargoContact", method = { RequestMethod.GET, RequestMethod.POST })
	public void findCargoContact(Long cargoId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try { // 页号
			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			CargoData cargoData = cargoService.getCargoData(cargoId);
			if (cargoData == null)
				throw new Exception("错误！指定的货物不存在！");

			UserData contact = cargoData.getPublisher();

			Map<String, Object> content = new HashMap<String, Object>();
			content.put("contact", contact);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	/** 查找联系人 */
	@RequestMapping(value = "/findShipContact", method = { RequestMethod.GET, RequestMethod.POST })
	public void findShipContact(Long shipId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try { // 页号
			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			ShipData shipData = myShipService.getFullShipData(shipId);
			if (shipData == null)
				throw new Exception("错误！指定的船舶不存在！");

			UserData contact = shipData.getMaster();

			Map<String, Object> content = new HashMap<String, Object>();
			content.put("contact", contact);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);
		} catch (

		Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	/** 查找船盘 */
	@RequestMapping(value = "/findShipCabin", method = { RequestMethod.GET, RequestMethod.POST })
	public void findShipCabin(Long cabinId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try { // 页号
			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			ShipCabinData shipCabinData = cabinService.getById(cabinId);
			if (shipCabinData == null)
				throw new Exception("错误！指定的船盘不存在！");

			Map<String, Object> content = new HashMap<String, Object>();
			content.put("shipCabinData", shipCabinData);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);
		} catch (

		Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	/**
	 * 船舶详情
	 */
	@RequestMapping(value = "/shipInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipInfo(Long shipId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");
		OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
		UserData userData = null;
		if (ou != null)
			userData = userService.getById(ou.getId());

		ModelAndView mav = new ModelAndView("/new/portal-shipInfo-mobile");
		mav.addObject("userData", userData);

		ShipData myShipData = myShipService.getFullShipData(shipId);
		if (myShipData == null)
			throw new Exception("错误！指定的船舶不存在！");

		// 没登录则不计点击数
		if (userData != null) {
			// 保存船舶点击数
			homeService.saveShipPointCount(userData, myShipData);
		}
		mav.addObject("myShipData", myShipData);

		UserData contact = myShipData.getMaster();
		mav.addObject("contact", contact);

		// 获得评论集合列表
		List<EvaluateData> evaluateDatas = evaluateService.getEvaluateDatas(shipId, PAGE_SIZE);
		mav.addObject("evaluateDatas", evaluateDatas);

		return mav;
	}

	/**
	 * 货物详情
	 */
	@RequestMapping(value = "/cargoInfo", method = RequestMethod.GET)
	public ModelAndView cargoInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/mobile/cargo-info");

		Long id = ServletRequestUtils.getLongParameter(request, "id", 0L);

		CargoData cargoData = cargoService.getCargoData(id);
		mav.addObject("cargoData", cargoData);

		UserData contact = cargoData.getPublisher();
		mav.addObject("contact", contact);

		return mav;
	}

	/** 升级 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET })
	public void update(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			Map<String, Object> content = new HashMap<String, Object>();
			// 读取update.txt信息，获取升级信息
			String staticPath = request.getSession().getServletContext().getRealPath("/");
			String filePath = "";
			if("".equals(id) || id == null){
				filePath = staticPath + "/WEB-INF/static/phone/update.json";
			}else{
				filePath = staticPath + "/WEB-INF/static/phone/update_"+id+".json";
			}
			String json = FileUtil.readFromFile(filePath);
			Gson gson = new Gson();
			UpdateInfoData res = gson.fromJson(json, UpdateInfoData.class);
			content.put("version", res.getVersion());// 软件当前版本

			content.put("url", res.getUrl());// 软件下载地址
			content.put("note", res.getNote());// 软件说明

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "读取数据成功");
			map.put(JsonResponser.CONTENT, content);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	/** 通知公告 */
	@RequestMapping(value = "/notice", method = { RequestMethod.GET })
	public void notice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");
			Map<String, Object> content = new HashMap<String, Object>();
			// 页号
			int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);

			Page<NoticeData> noticeDatas = noticeService.getHomePageNotices(10, pageNo);
			if (noticeDatas.getPageSize() > 0) {
				content.put("totalPages", noticeDatas.getPageSize());
				content.put("noticeDatas", noticeDatas.getResult());

				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
				map.put(JsonResponser.CONTENT, content);
				map.put(JsonResponser.CONTENTMD5, contentMD5);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "取得新闻失败！");
			}

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	/** 进入通知公告详情页 */
	@RequestMapping(value = "/noticeDetail", method = RequestMethod.GET)
	public ModelAndView noticeDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Long noticeId = ServletRequestUtils.getLongParameter(request, "noticeId", 0);

		ModelAndView mv = new ModelAndView("/mobile/noticeDetail");

		NoticeData noticeData = noticeService.getNoticeDataById(noticeId);
		if (noticeData == null)
			throw new Exception("找不到该条信息");

		mv.addObject("noticeData", noticeData);

		return mv;
	}

	/** 获取所有船类 **/
	@RequestMapping(value = "/getAllTypes/show", method = { RequestMethod.GET })
	public void getShipTypes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> content = new HashMap<String, Object>();

			// 船舶类别分组列表
			List<TypeData> uncleTypeDatas = typeService.getUncleDatas();
			content.put("uncleTypeDatas", uncleTypeDatas);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "读取数据成功");
			map.put(JsonResponser.CONTENT, content);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	// 船盘列表
	@RequestMapping(value = "/cabinHome", method = { RequestMethod.GET, RequestMethod.POST })
	public void cabinHome(Page<ShipCabinData> pageData, WaresBigTypeCode waresBigType, WaresTypeCode waresType,
			CargoTypeCode cargoType, String selPortNos, String keyWords, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> content = new HashMap<String, Object>();

			// 通知公告
			Page<NoticeData> noticePage = noticeService.getHomePageNotices(10, 1);
			content.put("noticePage", noticePage);

			if (waresType == null)
				waresType = WaresTypeCode.nmjzx;
			if (cargoType == null)
				cargoType = CargoTypeCode.container20e;

			// 航租航线
			List<SailLineData> sailLineDatas = null;
			if (waresBigType == WaresBigTypeCode.voyage)
				sailLineDatas = sailLineService.getSailLinesByType(waresBigType, waresType, cargoType);
			// 期租装卸港
			List<UpDownPortData> upDownPortDates = null;
			if (waresBigType == WaresBigTypeCode.daily)
				upDownPortDates = upDownPortService.getUpDownPortsByType(waresBigType, waresType, cargoType);

			// 分页的船盘
			cabinService.getCabinHomePage(pageData, waresBigType, waresType, cargoType, selPortNos, keyWords);

			// 集装箱规格
			List<CargoTypeCode> containerCodes = new ArrayList<CargoTypeCode>();
			containerCodes.add(CargoTypeCode.container20e);
			containerCodes.add(CargoTypeCode.container20f);
			containerCodes.add(CargoTypeCode.container40e);
			containerCodes.add(CargoTypeCode.container40f);
			containerCodes.add(CargoTypeCode.container45e);
			containerCodes.add(CargoTypeCode.container45f);
			content.put("containerCodes", containerCodes);

			content.put("waresBigType", waresBigType);
			content.put("waresType", waresType);
			content.put("cargoType", cargoType);
			content.put("selPortNos", selPortNos);
			content.put("keyWords", keyWords);
			content.put("sailLineDatas", sailLineDatas);
			content.put("upDownPortDates", upDownPortDates);
			content.put("pageData", pageData);
			content.put("title", "船盘");

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "读取数据成功");
			map.put(JsonResponser.CONTENT, content);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	/**
	 * 首页
	 */
	@RequestMapping(value = "/shipHome", method = { RequestMethod.GET, RequestMethod.POST })
	public void shipHome(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> content = new HashMap<String, Object>();
			// 经营区域
			ScfBigAreaCode[] bigAreaCodes = new ScfBigAreaCode[1];// ScfBigAreaCode.values();
			bigAreaCodes[0] = ScfBigAreaCode.ZHUSANJIAO;
			for (ScfBigAreaCode bigAreaCode : bigAreaCodes)
				bigAreaCode.setPortCities(ScfPortCityCode.getPortCities(bigAreaCode));

			// 金牌代理人及其业务员列表
			Page<OperatorData> pd = operatorService.getOperatorDatas(3, 1);
			content.put("operatorPage", pd);

			// 集装箱规格
			List<CargoTypeCode> containerCodes = new ArrayList<CargoTypeCode>();
			containerCodes.add(CargoTypeCode.container20e);
			containerCodes.add(CargoTypeCode.container20f);
			containerCodes.add(CargoTypeCode.container40e);
			containerCodes.add(CargoTypeCode.container40f);
			containerCodes.add(CargoTypeCode.container45e);
			containerCodes.add(CargoTypeCode.container45f);

			// 分页的船盘
			Page<ShipCabinData> cabinPage = new Page<ShipCabinData>();
			cabinPage.setPageNo(1);
			cabinPage.setPageSize(5);
			cabinService.getCabinHomePage(cabinPage, null, null, null, null, "");
			content.put("cabinPage", cabinPage);
			// 货盘列表
			Page<CargoData> cargoPage = homeService.getHomeCargo5(5, 1);
			content.put("cargoPage", cargoPage);
			// 通知公告
			Page<NoticeData> noticePage = noticeService.getHomePageNotices(10, 1);
			content.put("noticePage", noticePage);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "读取数据成功");
			map.put(JsonResponser.CONTENT, content);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// /**
	// * 船舶详情
	// */
	// @RequestMapping(value = "/cabinInfo", method = { RequestMethod.GET,
	// RequestMethod.POST })
	// public void cabinInfo(Long cabinId, String selPortNos, HttpServletRequest
	// request, HttpServletResponse response)
	// throws Exception {
	//
	// Map<String, Object> map = new HashMap<String, Object>();
	// try {
	// Map<String, Object> content = new HashMap<String, Object>();
	//
	// ShipCabinData shipCabinData = cabinService.getByIdAndSelPortNos(cabinId,
	// selPortNos);
	// Long shipId = shipCabinData.getShipData().getId();
	// ShipData myShipData = myShipService.getFullShipData(shipId);
	// if (myShipData == null)
	// throw new Exception("错误！指定的船舶不存在！");
	// // 集装箱规格
	// List<CargoTypeCode> containerCodes = new ArrayList<CargoTypeCode>();
	// containerCodes.add(CargoTypeCode.container20e);
	// containerCodes.add(CargoTypeCode.container20f);
	// containerCodes.add(CargoTypeCode.container40e);
	// containerCodes.add(CargoTypeCode.container40f);
	// containerCodes.add(CargoTypeCode.container45e);
	// containerCodes.add(CargoTypeCode.container45f);
	// content.put("containerCodes", containerCodes);
	// content.put("myShipData", myShipData);
	// content.put("cabinData", shipCabinData);
	// UserData contact = shipCabinData.getPublisher();
	// content.put("contact", contact);
	// content.put("selPortNos", selPortNos);
	// // 获得评论集合列表
	// List<EvaluateData> evaluateDatas =
	// evaluateService.getEvaluateDatas(shipId, PAGE_SIZE);
	// content.put("evaluateDatas", evaluateDatas);
	// // 船舶地图
	// ShipArvlftData leftData = null;
	// leftData = shipArvlftService.getLastShipArvlft(myShipData.getMmsi());
	//
	// content.put("shipArvlftData", leftData);
	// List<ShipCooordData> shipCooordDatas = new ArrayList<>();
	// if (leftData != null)
	// shipCooordDatas = shipArvlftService.getShipCooordDatas(myShipData,
	// leftData);
	// content.put("shipCooordDatas", shipCooordDatas);
	//
	// map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
	// map.put(JsonResponser.MESSAGE, "读取数据成功");
	// map.put(JsonResponser.CONTENT, content);
	//
	// } catch (Exception e) {
	// map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
	// map.put(JsonResponser.MESSAGE, e.getMessage());
	// }
	// JsonResponser.respondWithJson(response, map);
	// }
	/**
	 * 船舶详情
	 */
	@RequestMapping(value = "/cabinInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView cabinInfo(Long cabinId, String selPortNos, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("/mobile/portal-carbin-info");

		ShipCabinData shipCabinData = cabinService.getByIdAndSelPortNos(cabinId, selPortNos);
		Long shipId = shipCabinData.getShipData().getId();
		ShipData myShipData = shipCabinData.getShipData();
		if (myShipData == null)
			throw new Exception("错误！指定的船舶不存在！");
		// 集装箱规格
		List<CargoTypeCode> containerCodes = new ArrayList<CargoTypeCode>();
		containerCodes.add(CargoTypeCode.container20e);
		containerCodes.add(CargoTypeCode.container20f);
		containerCodes.add(CargoTypeCode.container40e);
		containerCodes.add(CargoTypeCode.container40f);
		containerCodes.add(CargoTypeCode.container45e);
		containerCodes.add(CargoTypeCode.container45f);
		mav.addObject("containerCodes", containerCodes);

		mav.addObject("myShipData", myShipData);
		mav.addObject("cabinData", shipCabinData);
		UserData contact = shipCabinData.getPublisher();
		mav.addObject("contact", contact);
		mav.addObject("selPortNos", selPortNos);
		// 获得评论集合列表
		List<EvaluateData> evaluateDatas = evaluateService.getEvaluateDatas(shipId, PAGE_SIZE);
		mav.addObject("evaluateDatas", evaluateDatas);
		// 船舶地图
		List<ShipCooordData> shipCooordDatas = shipArvlftService.getShipCurrCooordDatas(myShipData);
		mav.addObject("shipCooordDatas", shipCooordDatas);

		return mav;
	}

}
