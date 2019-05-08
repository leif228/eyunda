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
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.ship.AttrNameData;
import com.hangyi.eyunda.data.ship.TypeData;
import com.hangyi.eyunda.domain.enumeric.AttrTypeCode;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.ship.AttributeService;
import com.hangyi.eyunda.service.ship.TypeService;

@Controller
@RequestMapping("/manage/ship")
public class AttributeController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AttributeService attributeService;

	@Autowired
	private TypeService typeService;

	// 获取一组对象，以列表的形式返回
	@RequestMapping(value = "/attribute", method = { RequestMethod.GET })
	public ModelAndView getList(String typeCode, HttpServletRequest request, HttpServletResponse response) {
		if(typeCode == null || typeCode.length() != 4){
			typeCode = "0101";
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-attribute");
		// 取得属性列表
		List<AttrNameData> attrNameDatas = attributeService.getTypeAttrNameDatas(typeCode);
		// 一级下拉列表
		List<TypeData> uncleDatas = typeService.getUncleDatas();
		// 列表信息
		mav.addObject("attrNameDatas", attrNameDatas);
		mav.addObject("typeCode", typeCode);
		mav.addObject("uncleDatas", uncleDatas);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.SHIP);
		mav.addObject("menuAct", MenuCode.SHIP_ATTRIBUTE);

		return mav;
	}

	// 获取一个对象
	@RequestMapping(value = "/attribute/add", method = { RequestMethod.GET })
	public void add(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<TypeData> uncleDatas = typeService.getUncleDatas();
			
			map.put("uncleDatas", uncleDatas);

			List<Map<String, Object>> attrTypeCodes = new ArrayList<Map<String, Object>>();
			for (AttrTypeCode attr : AttrTypeCode.values()) {
				Map<String, Object> mapAttr = new HashMap<String, Object>();
				mapAttr.put("code", attr.toString());
				mapAttr.put("description", attr.getDescription());
				attrTypeCodes.add(mapAttr);
			}

			map.put("attrTypeCodes", attrTypeCodes);
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
	@RequestMapping(value = "/attribute/edit", method = { RequestMethod.GET })
	public void edit(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<TypeData> uncleDatas = typeService.getUncleDatas();
			map.put("uncleDatas", uncleDatas);

			List<Map<String, Object>> attrTypeCodes = new ArrayList<Map<String, Object>>();
			for (AttrTypeCode attr : AttrTypeCode.values()) {
				Map<String, Object> mapAttr = new HashMap<String, Object>();
				mapAttr.put("code", attr.toString());
				mapAttr.put("description", attr.getDescription());
				attrTypeCodes.add(mapAttr);
			}
			map.put("attrTypeCodes", attrTypeCodes);

			AttrNameData attrNameData = attributeService.getTypeAttrNameData(id);
			map.put("attrNameData", attrNameData);

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

	@RequestMapping(value = "/attribute/delete", method = { RequestMethod.DELETE })
	public ModelAndView deleteAttribute(Long id, HttpServletRequest request, HttpServletResponse response) {
		attributeService.deleteAttribute(id);
		ModelAndView mav = new ModelAndView("redirect:/manage/ship/attribute");
		return mav;
	}

	@RequestMapping(value = "/attribute/save", method = { RequestMethod.POST })
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		Long id = ServletRequestUtils.getLongParameter(request, "id", 0L);
		String attrName = ServletRequestUtils.getStringParameter(request, "attrName", "");
		String typeCode = ServletRequestUtils.getStringParameter(request, "typeCode", "");
		String attrTypeCode = ServletRequestUtils.getStringParameter(request, "attrTypeCode", "");
		String attrValues = ServletRequestUtils.getStringParameter(request, "attrValues", "");

		attributeService.saveAttribute(id, attrName, typeCode, attrTypeCode, attrValues);

		ModelAndView mav = new ModelAndView("redirect:/manage/ship/attribute");
		return mav;
	}
}
