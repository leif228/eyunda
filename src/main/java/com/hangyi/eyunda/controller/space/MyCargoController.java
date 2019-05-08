package com.hangyi.eyunda.controller.space;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.cargo.CargoData;
import com.hangyi.eyunda.domain.enumeric.CargoBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.CargoStatusCode;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ScfBigAreaCode;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.service.EnumConst.SpaceMenuCode;
import com.hangyi.eyunda.service.cargo.CargoService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CookieUtil;

@Controller
@RequestMapping("/space/cargo")
public class MyCargoController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private CargoService cargoService;

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

	@RequestMapping(value = "/cargoList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView cargoList(Page<CargoData> pageData, String keyWords, String startDate, String endDate,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("/space/space-cargo");

		UserData userData = this.getLoginUserData(request);
		mav.addObject("userData", userData);

		if (startDate != null && endDate != null && !"".equals(startDate) && !"".equals(endDate)
				&& CalendarUtil.parseYY_MM_DD(endDate).before(CalendarUtil.parseYY_MM_DD(startDate)))
			throw new Exception("错误！起始日期必须小于终止日期。");

		pageData = cargoService.getMySelfCargos(userData, pageData, keyWords, startDate, startDate);

		mav.addObject("pageData", pageData);
		mav.addObject("keyWords", keyWords);
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		// 货类列表
		List<CargoTypeCode> cargoTypes = new ArrayList<CargoTypeCode>();
		cargoTypes.add(CargoTypeCode.container20e);
		cargoTypes.add(CargoTypeCode.coal);
		cargoTypes.add(CargoTypeCode.metalmineral);
		cargoTypes.add(CargoTypeCode.steel);
		cargoTypes.add(CargoTypeCode.metal);
		cargoTypes.add(CargoTypeCode.tile);
		cargoTypes.add(CargoTypeCode.wood);
		cargoTypes.add(CargoTypeCode.cement);
		cargoTypes.add(CargoTypeCode.manure);
		cargoTypes.add(CargoTypeCode.salt);
		cargoTypes.add(CargoTypeCode.food);
		cargoTypes.add(CargoTypeCode.machinery);
		cargoTypes.add(CargoTypeCode.chemicals);
		cargoTypes.add(CargoTypeCode.industry);
		cargoTypes.add(CargoTypeCode.industrial);
		cargoTypes.add(CargoTypeCode.farming);
		cargoTypes.add(CargoTypeCode.cotton);
		mav.addObject("cargoTypes", cargoTypes);

		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_CARGO);

		return mav;
	}

	// 添加或修改一个货物
	@RequestMapping(value = "/cargoEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView cargoEdit(Long cargoId, CargoTypeCode cargoType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("/space/space-cargo-edit");

		UserData userData = this.getLoginUserData(request);
		mav.addObject("userData", userData);

		if (cargoId == null)
			cargoId = 0L;

		CargoData cargoData = cargoService.getCargoData(cargoId);
		if (cargoData == null) {
			if (cargoType == null)
				cargoType = CargoTypeCode.container20e;

			cargoData = new CargoData();

			cargoData.setCargoType(cargoType);
			cargoData.setPublisherId(userData.getId());
			cargoData.setPublisher(userData);

			String cargoNames = "";
			String tonTeus = "";
			String prices = "";

			Map<Integer, String> mapCargoNames = new TreeMap<Integer, String>();
			Map<Integer, Integer> mapTonTeus = new TreeMap<Integer, Integer>();
			Map<Integer, Double> mapPrices = new TreeMap<Integer, Double>();
			if (cargoType.getCargoBigType() == CargoBigTypeCode.container) {
				List<CargoTypeCode> ctcs = CargoTypeCode.getCodesByCargoBigType(CargoBigTypeCode.container);
				for (CargoTypeCode ctc : ctcs) {
					cargoNames += ctc.getShortName() + ",";
					tonTeus += "0,";
					prices += "0.00,";

					mapCargoNames.put(ctc.ordinal(), ctc.getShortName());
					mapTonTeus.put(ctc.ordinal(), 0);
					mapPrices.put(ctc.ordinal(), 0D);
				}
				if (cargoNames.length() > 0) {
					cargoNames = cargoNames.substring(0, cargoNames.length() - 1);
					tonTeus = tonTeus.substring(0, tonTeus.length() - 1);
					prices = prices.substring(0, prices.length() - 1);
				}
			} else {
				CargoTypeCode ctc = cargoType;

				cargoNames = ctc.getShortName();
				tonTeus = "0";
				prices = "0.00";

				mapCargoNames.put(ctc.ordinal(), ctc.getShortName());
				mapTonTeus.put(ctc.ordinal(), 0);
				mapPrices.put(ctc.ordinal(), 0D);

			}
			cargoData.setCargoNames(cargoNames);
			cargoData.setTonTeus(tonTeus);
			cargoData.setPrices(prices);

			cargoData.setMapCargoNames(mapCargoNames);
			cargoData.setMapTonTeus(mapTonTeus);
			cargoData.setMapPrices(mapPrices);

			cargoData.setCargoImage("/default/cargo/" + cargoType.toString() + ".jpg");
		} else {
			if (!cargoData.getPublisherId().equals(userData.getId()))
				throw new Exception("警告！你无权修改别人发布的货物信息。");
		}

		// 要修改的货运信息
		mav.addObject("cargoData", cargoData);

		// 货物公开性列表
		CargoStatusCode[] cargoStatuss = CargoStatusCode.values();
		mav.addObject("cargoStatuss", cargoStatuss);

		// 港口城市分组列表
		ScfBigAreaCode[] bigAreas = ScfBigAreaCode.values();
		for (ScfBigAreaCode bigArea : bigAreas)
			if (bigArea.getPortCities() == null)
				bigArea.setPortCities(ScfPortCityCode.getPortCities(bigArea));
		mav.addObject("bigAreas", bigAreas);

		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_CARGO);

		return mav;
	}

	// 保存一个我添加的货运信息或者修改一个我的货运信息后再进行保存
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public void save(CargoData cargoData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			cargoData.setPublisherId(userData.getId());

			if (cargoService.checkUploadFile(cargoData)) {
				String cargoNames = "";
				String tonTeus = "";
				String prices = "";
				if (cargoData.getCargoType().getCargoBigType() == CargoBigTypeCode.container) {
					List<CargoTypeCode> ctcs = CargoTypeCode.getCodesByCargoBigType(CargoBigTypeCode.container);
					for (CargoTypeCode ctc : ctcs) {
						String cargoName = ServletRequestUtils.getStringParameter(request, "cargoName-" + ctc.ordinal(),
								"");
						Integer tonTeu = ServletRequestUtils.getIntParameter(request, "tonTeu-" + ctc.ordinal(), 0);
						Double price = ServletRequestUtils.getDoubleParameter(request, "price-" + ctc.ordinal(), 0D);
						cargoNames += cargoName + ",";
						tonTeus += tonTeu + ",";
						prices += price + ",";
					}
					cargoNames = cargoNames.substring(0, cargoNames.length() - 1);
					tonTeus = tonTeus.substring(0, tonTeus.length() - 1);
					prices = prices.substring(0, prices.length() - 1);

					cargoData.setCargoNames(cargoNames);
					cargoData.setTonTeus(tonTeus);
					cargoData.setPrices(prices);
				}

				// 若是子帐号,使用其父帐号,否则使用自己的帐号
				Long cargoId = cargoService.saveOrUpdate(userData, cargoData);

				if (cargoId > 0) {
					int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);

					map.put("cargoId", cargoId);
					map.put("pageNo", pageNo);
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
					map.put(JsonResponser.MESSAGE, "保存货物成功！");
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "保存货物失败，提交的数据内容或格式有误！");
				}
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "保存货物失败，图片格式不正确或图片大小超过1M！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "保存货物异常！");
		}
		JsonResponser.respondWithText(response, map);
	}

	// 发布
	@RequestMapping(value = "/publish", method = { RequestMethod.GET, RequestMethod.POST })
	public void publish(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (cargoService.publishMyCargo(userData, id)) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "发布成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "发布失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

	// 取消发布
	@RequestMapping(value = "/unpublish", method = { RequestMethod.GET, RequestMethod.POST })
	public void unpublish(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.handler))
				throw new Exception("非业务员不能进行此项操作！");

			if (cargoService.unpublishMyCargo(userData, id)) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取消发布成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "取消发布失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

	// 删除
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public void delete(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (cargoService.deleteCargo(userData, id)) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "删除成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "删除失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

}
