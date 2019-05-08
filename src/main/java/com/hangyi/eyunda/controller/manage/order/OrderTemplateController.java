package com.hangyi.eyunda.controller.manage.order;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.order.OrderTemplateData;
import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.order.OrderTemplateService;

@Controller
@RequestMapping("/manage/order")
public class OrderTemplateController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderTemplateService orderTemplateService;

	// 获取一组对象，以列表的形式返回
	@RequestMapping(value = "/orderTemplate", method = { RequestMethod.GET })
	public ModelAndView orderTemplate(HttpServletRequest request, HttpServletResponse response) {
		// 处理业务逻辑，为了可以rpc，返回数据必须是序列化的
		List<OrderTemplateData> OrderTemplateDatas = orderTemplateService.getOrderTemplateDatas();

		// 取得合同模板类型
		OrderTypeCode[] orderTypeCodes = OrderTypeCode.values();

		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-orderTemplate");
		// 列表信息
		mav.addObject("OrderTemplateDatas", OrderTemplateDatas);
		mav.addObject("orderTypeCodes", orderTypeCodes);
		// 角色对话框中合同数据列表
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.ORDER);
		mav.addObject("menuAct", MenuCode.ORDER_TEMPLATE);
		return mav;
	}

	// 得到一个合同模板信息
	@RequestMapping(value = "/orderTemplate/{id}", method = { RequestMethod.GET })
	public void getOrderTemplate(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			OrderTemplateData otd = orderTemplateService.getOrderTemplateData(id);
			if (otd != null) {
				map.put("orderTemplateData", otd);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取得数据成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "取得数据失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "取得数据失败！");
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 保存合同模板
	@RequestMapping(value = "/orderTemplate/save", method = { RequestMethod.GET, RequestMethod.POST })
	public void save(OrderTemplateData otd, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long id = ServletRequestUtils.getLongParameter(request, "orderTemplateId", 0L);
			otd.setId(id);
			boolean b = orderTemplateService.saveOrderTemplateData(otd);
			if (b) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "保存合同模板成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "保存合同模板失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "保存合同模板时出现错误！");
		}
		JsonResponser.respondWithText(response, map);
	}

	// 删除合同模板
	@RequestMapping(value = "/orderTemplate/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public void delete(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			OrderTemplateData otd = orderTemplateService.getOrderTemplateData(id);

			// 判断是否删除成功
			boolean b = orderTemplateService.deleteOrderTemplate(otd.getId());
			if (b) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "删除合同模板成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "删除合同模板失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "删除合同模板时出现错误！");
		}
		JsonResponser.respondWithText(response, map);
	}

}
