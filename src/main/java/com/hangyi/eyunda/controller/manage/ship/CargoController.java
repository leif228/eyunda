package com.hangyi.eyunda.controller.manage.ship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.cargo.CargoData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.cargo.CargoService;
import com.hangyi.eyunda.util.CalendarUtil;

@Controller
@RequestMapping("/manage/ship")
public class CargoController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CargoService cargoService;

	// 货物列表
	@RequestMapping(value = "/manageCargo", method = { RequestMethod.GET })
	public ModelAndView manageCargo(Page<CargoData> pageData, String keyWords, String startDate, String endDate)
			throws Exception {

		if (startDate != null && endDate != null && !"".equals(startDate) && !"".equals(endDate)
				&& CalendarUtil.parseYY_MM_DD(endDate).before(CalendarUtil.parseYY_MM_DD(startDate)))
			throw new Exception("错误！起始日期必须小于终止日期。");

		pageData = cargoService.findCargoPage(pageData, keyWords, startDate, endDate);

		ModelAndView mav = new ModelAndView("/manage/manage-cargo");

		// 列表信息
		mav.addObject("pageData", pageData);
		mav.addObject("keyWords", keyWords);
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.SHIP);
		mav.addObject("menuAct", MenuCode.SHIP_CARGO);
		return mav;
	}

	// 删除指定的货物
	@RequestMapping(value = "/manageCargo/deleteCargo", method = { RequestMethod.DELETE })
	public ModelAndView deleteCargo(Long id, String keyWords, int pageNo) {
		if (keyWords == null)
			keyWords = "";
		ModelAndView mav = new ModelAndView("redirect:/manage/ship/manageCargo");

		CargoData cargoData = cargoService.getCargoData(id);
		cargoService.deleteCargo(cargoData.getPublisher(), id);

		mav.addObject("pageNo", pageNo);
		mav.addObject("keyWords", keyWords);
		return mav;
	}

}
