package com.hangyi.eyunda.controller.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hangyi.eyunda.data.manage.HolidayData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.manage.HolidayService;

@Controller
@RequestMapping("/manage/power")
public class HolidayController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HolidayService yydHolidayService;
	
	@RequestMapping(value = "/holidayList", method = { RequestMethod.GET })
	public ModelAndView getHolidayList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/manage/manage-holiday");
		
		List<HolidayData> holidayDatas = yydHolidayService.findHoliday();
		
		// 假日列表信息
		mav.addObject("holidayDatas", holidayDatas);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.POWER);
		mav.addObject("menuAct", MenuCode.POWER_HOLIDAY);
		
		return mav;
	}
	
	@RequestMapping(value = "/saveHoliday", method = { RequestMethod.POST })
	public void saveHoliday(HolidayData holiday, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try{
			yydHolidayService.saveHoliday(holiday);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存节假日数据成功！");
		}catch(Exception e){
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "保存节假日数据失败！");
		}
		
		JsonResponser.respondWithJson(response, map);
	}
	
	@RequestMapping(value = "/deleteHoliday", method = { RequestMethod.POST })
	public void deleteHoliday(HolidayData holiday, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try{
			yydHolidayService.deleteHoliday(holiday);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "删除节假日数据成功！");
		}catch(Exception e){
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "删除节假日数据失败！");
		}
		
		JsonResponser.respondWithJson(response, map);
	}
}
