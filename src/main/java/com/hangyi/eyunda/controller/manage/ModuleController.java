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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.manage.ModuleInfoData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.manage.ModuleService;

@Controller
@RequestMapping("/manage/power")
public class ModuleController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ModuleService moduleService;

	// 获取一组对象，以列表的形式返回
	@RequestMapping(value = "/module", method = { RequestMethod.GET })
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response) {
		// 取出输入参数并给出缺省值

		// 处理业务逻辑，为了可以rpc，返回数据必须是序列化的
		List<ModuleInfoData> moduleDatas = moduleService.getModuleDatas();

		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-module");
		// 列表信息
		mav.addObject("moduleDatas", moduleDatas);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.POWER);
		mav.addObject("menuAct", MenuCode.POWER_MODULE);

		return mav;
	}

	// 获取一个对象
	@RequestMapping(value = "/module/{id}", method = { RequestMethod.GET })
	public void getEntity(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ModuleInfoData moduleData = moduleService.getModuleData(id);

			if (moduleData != null) {
				map.put("entity", moduleData);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取得数据成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "取得数据失败！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "取得数据失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	@RequestMapping(value = "/module/delete", method = { RequestMethod.DELETE })
	public ModelAndView delete(Long id, HttpServletRequest request, HttpServletResponse response) {
		moduleService.deleteModule(id);
		ModelAndView mav = new ModelAndView("redirect:/manage/power/module");
		return mav;
	}

	@RequestMapping(value = "/module/save", method = { RequestMethod.POST })
	public ModelAndView save(ModuleInfoData module, HttpServletRequest request, HttpServletResponse response) {
		moduleService.saveOrUpdate(module);

		ModelAndView mav = new ModelAndView("redirect:/manage/power/module");
		return mav;
	}

}
