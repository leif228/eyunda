package com.hangyi.eyunda.controller.portal.home;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.hangyi.eyunda.data.account.OperatorData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.cargo.CargoData;
import com.hangyi.eyunda.data.notice.NoticeData;
import com.hangyi.eyunda.data.order.EvaluateData;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.data.ship.SailLineData;
import com.hangyi.eyunda.data.ship.ShipCabinData;
import com.hangyi.eyunda.data.ship.ShipCooordData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.data.ship.UpDownPortData;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ScfBigAreaCode;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.domain.enumeric.SearchRlsCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;
import com.hangyi.eyunda.service.account.OperatorService;
import com.hangyi.eyunda.service.cargo.CargoService;
import com.hangyi.eyunda.service.map.ShipCooordService;
import com.hangyi.eyunda.service.notice.NoticeService;
import com.hangyi.eyunda.service.portal.home.EvaluateService;
import com.hangyi.eyunda.service.portal.home.HomeService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.MyCabinService;
import com.hangyi.eyunda.service.ship.MyShipArvlftService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.service.ship.PortService;
import com.hangyi.eyunda.service.ship.SailLineService;
import com.hangyi.eyunda.service.ship.UpDownPortService;
// import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CookieUtil;

@Controller
@RequestMapping("/portal/home")
public class HomeController {// 统一登录
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final int PAGE_SIZE = 20;

	@Autowired
	private UserService userService;
	@Autowired
	private HomeService homeService;
	@Autowired
	private EvaluateService evaluateService;
	@Autowired
	private MyShipService myShipService;
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private MyCabinService cabinService;
	@Autowired
	private SailLineService sailLineService;
	@Autowired
	private UpDownPortService upDownPortService;
	@Autowired
	private CargoService cargoService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private MyShipArvlftService shipArvlftService;
	@Autowired
	private ShipCooordService shipCooordService;
	@Autowired
	private PortService portService;
	/**
	 * 首页
	 */
	@RequestMapping(value = "/shipHome", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipHome(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/new/portal-index2");
		// 取出登录用户
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		mav.addObject("userData", userData);

		mav.addObject("searchRls", SearchRlsCode.shipsearch);
		// 搜索下拉
		mav.addObject("searchRlsCodes", SearchRlsCode.values());
		// 经营区域
		ScfBigAreaCode[] bigAreaCodes = new ScfBigAreaCode[1];// ScfBigAreaCode.values();
		bigAreaCodes[0] = ScfBigAreaCode.ZHUSANJIAO;
		for (ScfBigAreaCode bigAreaCode : bigAreaCodes)
			bigAreaCode.setPortCities(ScfPortCityCode.getPortCities(bigAreaCode));
		mav.addObject("bigAreaCodes", bigAreaCodes);

		// 金牌代理人及其业务员列表
		// UserData goldData = userService.getById(Constants.HUIYANG_USERID);
		Page<OperatorData> pd = operatorService.getOperatorDatas(1, 1);
		OperatorData operatorData = null;
		if (!pd.getResult().isEmpty())
			operatorData = pd.getResult().get(0);
		mav.addObject("operatorData", operatorData);

		// 集装箱规格
		List<CargoTypeCode> containerCodes = new ArrayList<CargoTypeCode>();
		containerCodes.add(CargoTypeCode.container20e);
		containerCodes.add(CargoTypeCode.container20f);
		containerCodes.add(CargoTypeCode.container40e);
		containerCodes.add(CargoTypeCode.container40f);
		containerCodes.add(CargoTypeCode.container45e);
		containerCodes.add(CargoTypeCode.container45f);
		mav.addObject("containerCodes", containerCodes);

		// 分页的船盘
		Page<ShipCabinData> cabinPage = new Page<ShipCabinData>();
		cabinPage.setPageNo(1);
		cabinPage.setPageSize(5);
		cabinService.getCabinHomePage(cabinPage, null, null, null, null, "");
		mav.addObject("cabinPage", cabinPage);
		// 货盘列表
		Page<CargoData> cargoPage = homeService.getHomeCargo5(5, 1);
		mav.addObject("cargoPage", cargoPage);
		// 通知公告
		Page<NoticeData> noticePage = noticeService.getHomePageNotices(10, 1);
		mav.addObject("noticePage", noticePage);

		return mav;
	}

	/**
	 * 搜索
	 */
	@RequestMapping(value = "/keySearch", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView keySerach(SearchRlsCode searchRls, ScfPortCityCode portCity, String keyWords, Integer pageNo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		// 参数
		mav.addObject("portCity", portCity);
		mav.addObject("keyWords", keyWords);
		mav.addObject("pageNo", pageNo);
		// 代理人列表
		if (searchRls == SearchRlsCode.shipsearch)
			mav.setViewName("redirect:/portal/home/shipListx");
		else
			mav.setViewName("redirect:/portal/home/cargoListx");

		return mav;
	}

	/**
	 * 代理人列表
	 */
	@RequestMapping(value = "/userListx", method = { RequestMethod.GET })
	public ModelAndView userListx(Page<UserData> pageData, ScfPortCityCode portCity, String keyWords,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		pageData.setPageSize(3);

		ModelAndView mav = new ModelAndView("/new/portal-userList");
		// 取出登录用户
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		mav.addObject("userData", userData);

		// 参数
		mav.addObject("portCity", portCity);
		mav.addObject("keyWords", keyWords);
		mav.addObject("searchRls", SearchRlsCode.shipsearch);

		// 搜索下拉
		mav.addObject("searchRlsCodes", SearchRlsCode.values());
		// 经营区域
		ScfBigAreaCode[] bigAreaCodes = new ScfBigAreaCode[1];// ScfBigAreaCode.values();
		bigAreaCodes[0] = ScfBigAreaCode.ZHUSANJIAO;
		for (ScfBigAreaCode bigAreaCode : bigAreaCodes)
			bigAreaCode.setPortCities(ScfPortCityCode.getPortCities(bigAreaCode));
		mav.addObject("bigAreaCodes", bigAreaCodes);

		// 代理人列表
		Page<OperatorData> pageOperator = operatorService.getOperatorDatas(pageData.getPageSize(),
				pageData.getPageNo());
		mav.addObject("pageData", pageOperator);

		// 通知公告
		Page<NoticeData> noticePage = noticeService.getHomePageNotices(10, 1);
		mav.addObject("noticePage", noticePage);

		// 控制搜索框显示选项
		mav.addObject("homepage", "false");
		mav.addObject("currentMenuItem", 2);

		return mav;
	}

	/**
	 * 货物列表
	 */
	@RequestMapping(value = "/cargoListx", method = { RequestMethod.GET })
	public ModelAndView cargoListx(Page<CargoData> pageData, WaresTypeCode waresType, String keyWords,
			CargoTypeCode cargoType, HttpServletRequest request, HttpServletResponse response) throws Exception {
		pageData.setPageSize(PAGE_SIZE);

		ModelAndView mav = new ModelAndView("/new/portal-cargoList");

		// 取出登录用户
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		mav.addObject("userData", userData);

		// 参数
		mav.addObject("keyWords", keyWords);
		mav.addObject("searchRls", SearchRlsCode.cargosearch);

		// 搜索下拉
		mav.addObject("searchRlsCodes", SearchRlsCode.values());
		// 经营区域
		ScfBigAreaCode[] bigAreaCodes = new ScfBigAreaCode[1];// ScfBigAreaCode.values();
		bigAreaCodes[0] = ScfBigAreaCode.ZHUSANJIAO;
		for (ScfBigAreaCode bigAreaCode : bigAreaCodes)
			bigAreaCode.setPortCities(ScfPortCityCode.getPortCities(bigAreaCode));
		mav.addObject("bigAreaCodes", bigAreaCodes);
		// 船盘列表 pageData.getPageSize()
		pageData = homeService.getHomeCargoList(pageData.getPageSize(), pageData.getPageNo(), keyWords, null, null);
		mav.addObject("pageData", pageData);

		// 通知公告
		Page<NoticeData> noticePage = noticeService.getHomePageNotices(10, 1);
		mav.addObject("noticePage", noticePage);

		// 控制搜索框显示选项
		mav.addObject("homepage", "false");
		mav.addObject("currentMenuItem", 4);

		return mav;
	}

	/**
	 * 船舶详情
	 */
	@RequestMapping(value = "/shipInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipInfo(Long shipId, Long cabinId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 取出登录用户
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);

		ModelAndView mav = new ModelAndView("/new/portal-ship-info");
		mav.addObject("userData", userData);

		// 搜索下拉
		mav.addObject("searchRlsCodes", SearchRlsCode.values());
		// 默认船盘下拉被选中
		mav.addObject("searchRls", SearchRlsCode.shipsearch);
		// 经营区域
		ScfBigAreaCode[] bigAreaCodes = new ScfBigAreaCode[1];// ScfBigAreaCode.values();
		bigAreaCodes[0] = ScfBigAreaCode.ZHUSANJIAO;
		for (ScfBigAreaCode bigAreaCode : bigAreaCodes)
			bigAreaCode.setPortCities(ScfPortCityCode.getPortCities(bigAreaCode));
		mav.addObject("bigAreaCodes", bigAreaCodes);

		ShipData myShipData = myShipService.getFullShipData(shipId);
		if (myShipData == null)
			throw new Exception("错误！指定的船舶不存在！");

		if (userData != null) {
			// 保存船舶点击数
			homeService.saveShipPointCount(userData, myShipData);
		}
		mav.addObject("myShipData", myShipData);

		UserData contact = myShipData.getMaster();
		mav.addObject("contact", contact);

		// 当前位置及接货信息
		List<ShipCooordData> shipCooordDatas = shipCooordService.getCurrShipDistCooords(myShipData.getMmsi());
		ShipCooordData shipCooorData = shipCooordDatas.isEmpty() ? null : shipCooordDatas.get(0);
		mav.addObject("shipCooorData", shipCooorData);

		// 获得评论集合列表
		List<EvaluateData> evaluateDatas = evaluateService.getEvaluateDatas(shipId, PAGE_SIZE);
		mav.addObject("evaluateDatas", evaluateDatas);

		return mav;
	}

	/**
	 * 货物详情
	 */
	@RequestMapping(value = "/cargoInfo", method = { RequestMethod.GET })
	public ModelAndView cargoInfo(Long cargoId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("/new/portal-cargoInfo");
		// 取出登录用户
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);

		mav.addObject("userData", userData);

		// 搜索下拉
		mav.addObject("searchRlsCodes", SearchRlsCode.values());
		// 默认货盘栏目选中
		mav.addObject("searchRls", SearchRlsCode.cargosearch);
		// 经营区域
		ScfBigAreaCode[] bigAreaCodes = new ScfBigAreaCode[1];// ScfBigAreaCode.values();
		bigAreaCodes[0] = ScfBigAreaCode.ZHUSANJIAO;
		for (ScfBigAreaCode bigAreaCode : bigAreaCodes)
			bigAreaCode.setPortCities(ScfPortCityCode.getPortCities(bigAreaCode));
		mav.addObject("bigAreaCodes", bigAreaCodes);

		CargoData cargoData = cargoService.getCargoData(cargoId);
		// 货运信息
		mav.addObject("cargoData", cargoData);

		UserData contact = cargoData.getPublisher();
		mav.addObject("contact", contact);

		// 控制搜索框显示选项
		mav.addObject("homepage", "false");
		mav.addObject("currentMenuItem", 4);

		return mav;
	}

	/**
	 * 进入通知公告详情页
	 */
	@RequestMapping(value = "/noticeInfo", method = RequestMethod.GET)
	public ModelAndView noticeInfo(Long noticeId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = new ModelAndView("/new/portal-noticeInfo");

		// 取出登录用户
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);

		// 用户信息
		mv.addObject("userData", userData);

		// 搜索下拉
		mv.addObject("searchRlsCodes", SearchRlsCode.values());

		// 默认船舶下拉被选中
		mv.addObject("searchRls", SearchRlsCode.shipsearch);

		NoticeData noticeData = noticeService.getNoticeDataById(noticeId);

		mv.addObject("noticeData", noticeData);

		return mv;
	}

	// 船盘列表
	@RequestMapping(value = "/cabinHome", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView cabinHome(Page<ShipCabinData> pageData, WaresBigTypeCode waresBigType, WaresTypeCode waresType,
			CargoTypeCode cargoType, HttpServletRequest request) throws Exception {

		String selPortNos ="";
		String keyWords = "";
		ModelAndView mav = new ModelAndView("/new/portal-carbin-list");
		if (selPortNos != null && !"".equals(selPortNos)) {
			selPortNos = cleanStr(selPortNos);
		}
		// 取出登录用户
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		mav.addObject("userData", userData);

		// 通知公告
		Page<NoticeData> noticePage = noticeService.getHomePageNotices(10, 1);
		mav.addObject("noticePage", noticePage);

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
		mav.addObject("containerCodes", containerCodes);

		// 当前url
		String contextpath;
		contextpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getRequestURI();
		String url = contextpath + "?";

		Enumeration<String> keys = request.getParameterNames();
		Map<String, String> params = new HashMap<>();
		while (keys.hasMoreElements()) {
			String k = keys.nextElement();
			if (k.equalsIgnoreCase("waresType") || k.equalsIgnoreCase("cargoType")) {
				url += "&" + k + "=" + request.getParameter(k);
			} else {
				params.put(k, request.getParameter(k));
			}
		}
		String url_no_waresBigType = url;
		String url_no_selPortNos = url;
		if (waresBigType == null || waresBigType.equals("")) {
			selPortNos = "";
		} else {
			if (params.size() > 0) {
				for (Entry<String, String> entry : params.entrySet()) {
					String req = entry.getKey();
					String reqVal = entry.getValue();
					if (!req.equalsIgnoreCase("selPortNos")) {
						url_no_selPortNos += "&" + req + "=" + reqVal;
						if (!req.equalsIgnoreCase("waresBigType")) {
							url_no_waresBigType += "&" + req + "=" + reqVal.trim();
						}
					}
				}
			}
		}

		mav.addObject("url_no_waresBigType", url_no_waresBigType);
		mav.addObject("url_no_selPortNos", url_no_selPortNos);

		mav.addObject("waresBigType", waresBigType);
		mav.addObject("waresType", waresType);
		mav.addObject("cargoType", cargoType);
		mav.addObject("selPortNos", selPortNos);
		mav.addObject("keyWords", keyWords);
		mav.addObject("sailLineDatas", sailLineDatas);
		mav.addObject("upDownPortDates", upDownPortDates);
		mav.addObject("pageData", pageData);
		mav.addObject("title",
				(waresType == WaresTypeCode.nmszh) ? cargoType.getShortName() : waresType.getDescription());
		return mav;
	}

	/**
	 * 船舶详情
	 */
	@RequestMapping(value = "/cabinInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView cabinInfo(Long cabinId, String selPortNos, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (selPortNos != null && !"".equals(selPortNos)) {
			selPortNos = cleanStr(selPortNos);
		}
		// 取出登录用户
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);

		ModelAndView mav = new ModelAndView("/new/portal-carbin-info");
		mav.addObject("userData", userData);

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

	private String cleanStr(String str) {
		if (str.matches("^[0-9_]+$"))
			return str;
		return "";
	}
	
	@RequestMapping(value = "/port/getAll", method = { RequestMethod.GET})
	public void getAll(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<PortData> pds = portService.getPortDatas();
			List<AllPortData> all = new ArrayList<>();
			for (ScfPortCityCode s : ScfPortCityCode.values()){
				AllPortData apd = new AllPortData();
				apd.code = s.getCode();
				apd.name = s.getDescription();
				
				ArrayList<ShortPortData> spd = new ArrayList<>();
				for(PortData pd : pds){
					if(pd.getPortCity().equals(s)){
						ShortPortData tpd = new ShortPortData();
						tpd.code = pd.getPortNo();
						tpd.name = pd.getPortName();
						spd.add(tpd);
					}
				}
				apd.sub = spd;
				all.add(apd);
			}

			map.put("content", all);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "请求成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}
	class ShortPortData{
		public String name;
		public String code;
	}
	class AllPortData{
		public String name;
		public String code;
		public ArrayList<ShortPortData> sub;
	}
}
