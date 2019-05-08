package com.hangyi.eyunda.controller.manage.ship;

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
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.ship.ShipCabinData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.domain.YydCabinInfo;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.MyCabinService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.service.wallet.BonusService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;

@Controller
@RequestMapping("/manage/ship")
public class CabinController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MyCabinService myCabinService;
	@Autowired
	private UserService userService;
	@Autowired
	private BonusService bonusService;
	@Autowired
	private MyShipService myShipService;

	// 船盘列表
	@RequestMapping(value = "/manageCabin", method = { RequestMethod.GET })
	public ModelAndView manageCabin(Page<ShipCabinData> pageData, String keyWords, String startDate, String endDate)
			throws Exception {

		if (startDate != null && endDate != null && !"".equals(startDate) && !"".equals(endDate)
				&& CalendarUtil.parseYY_MM_DD(endDate).before(CalendarUtil.parseYY_MM_DD(startDate)))
			throw new Exception("错误！起始日期必须小于终止日期。");

		myCabinService.getCabinPageForManager(pageData, keyWords, startDate, endDate);

		ModelAndView mav = new ModelAndView("/manage/manage-cabin");

		// 集装箱规格
		List<CargoTypeCode> containerCodes = new ArrayList<CargoTypeCode>();
		containerCodes.add(CargoTypeCode.container20e);
		containerCodes.add(CargoTypeCode.container20f);
		containerCodes.add(CargoTypeCode.container40e);
		containerCodes.add(CargoTypeCode.container40f);
		containerCodes.add(CargoTypeCode.container45e);
		containerCodes.add(CargoTypeCode.container45f);
		mav.addObject("containerCodes", containerCodes);
		// 列表信息
		mav.addObject("pageData", pageData);
		mav.addObject("keyWords", keyWords);
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.SHIP);
		mav.addObject("menuAct", MenuCode.SHIP_CABIN);

		return mav;
	}

	// 删除指定的船盘
	@RequestMapping(value = "/manageCabin/deleteCabin", method = { RequestMethod.DELETE })
	public ModelAndView deleteCabin(Long id, String keyWords, int pageNo) {
		if (keyWords == null)
			keyWords = "";
		ModelAndView mav = new ModelAndView("redirect:/manage/ship/manageCabin");

		ShipCabinData shipCabinData = myCabinService.getById(id);
		myCabinService.deleteCabin(shipCabinData.getId());

		mav.addObject("pageNo", pageNo);
		mav.addObject("keyWords", keyWords);
		return mav;
	}

	// 发红包对话框
	@RequestMapping(value = "/publishers", method = { RequestMethod.GET, RequestMethod.POST })
	public void publishers(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			long[] choiceCabinIds = ServletRequestUtils.getLongParameters(request, "choiceCabinIds");

			Map<Long, String> mapReceiver = new HashMap<Long, String>();
			for (Long shipCabinId : choiceCabinIds) {
				YydCabinInfo yydCabinInfo = myCabinService.get(shipCabinId);
				ShipCabinData shipCabinData = myCabinService.getCabinData(yydCabinInfo);

				ShipData shipData = myShipService.getShipData(shipCabinData.getShipId());
				String str = shipCabinData.getPublisher().getTrueName() + "（" + shipData.getShipName() + "）";
				mapReceiver.put(shipCabinId, str);
			}

			map.put("mapReceiver", mapReceiver);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "红包发送成功");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 发红包
	@RequestMapping(value = "/batchBonusSend", method = { RequestMethod.GET, RequestMethod.POST })
	public void batchBonusSend(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData sender = userService.getById(Long.parseLong(Constants.ANGEL_ID));

			long[] cabinIds = ServletRequestUtils.getLongParameters(request, "cabinId");
			Double money = ServletRequestUtils.getDoubleParameter(request, "money", 0.0D);
			String remark = ServletRequestUtils.getStringParameter(request, "remark");

			Map<Long, String> mapRet = bonusService.batchBonusSend(sender, cabinIds, money, remark);
			String title = "";
			String content = "";
			String tips = "F";
			if (mapRet.isEmpty()) {
				title = "红包发送失败，没有一个红包被发送。";
			} else {
				title = "红包发送成功列表如下：" + "\n";
				tips = "T";
				for (String s : mapRet.values()) {
					content += s;
				}
			}

			map.put("title", title);
			map.put("tips", tips);
			map.put("content", content);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "红包发送成功");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

}
