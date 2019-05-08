package com.hangyi.eyunda.controller.manage.ship;

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
import com.hangyi.eyunda.data.ship.TypeData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.ship.TypeService;

@Controller
@RequestMapping("/manage/ship")
public class TypeController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TypeService typeService;

	// 获取一组对象，以列表的形式返回
	@RequestMapping(value = "/type", method = { RequestMethod.GET })
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response) {
		// 取出输入参数并给出缺省值

		// 处理业务逻辑，为了可以rpc，返回数据必须是序列化的
		List<TypeData> typeDatas = typeService.getTypeDatas();

		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-type");
		// 列表信息
		mav.addObject("typeDatas", typeDatas);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.SHIP);
		mav.addObject("menuAct", MenuCode.SHIP_TYPE);

		return mav;
	}

	// 获取一个对象
	@RequestMapping(value = "/type/add", method = { RequestMethod.GET })
	public void add(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<TypeData> uncleDatas = typeService.getUncleDatas();

			map.put("uncleDatas", uncleDatas);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得数据成功！");
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

	// 获取一个对象
	@RequestMapping(value = "/type/edit", method = { RequestMethod.GET })
	public void edit(String typeCode, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TypeData typeData = typeService.getTypeData(typeCode);
			List<TypeData> uncleDatas = typeService.getUncleDatas();

			map.put("typeData", typeData);
			map.put("uncleDatas", uncleDatas);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得数据成功！");
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

	@RequestMapping(value = "/type/save", method = { RequestMethod.POST })
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String prtTypeCode = ServletRequestUtils.getStringParameter(request, "prtTypeCode", "");
		String typeCode = ServletRequestUtils.getStringParameter(request, "typeCode", "");
		String typeName = ServletRequestUtils.getStringParameter(request, "typeName", "");

		typeService.save(prtTypeCode, typeCode, typeName);

		ModelAndView mav = new ModelAndView("redirect:/manage/ship/type");
		return mav;
	}

	@RequestMapping(value = "/type/delete", method = { RequestMethod.DELETE })
	public ModelAndView delete(String typeCode, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		typeService.deleteType(typeCode);
		ModelAndView mav = new ModelAndView("redirect:/manage/ship/type");
		return mav;
	}

}
