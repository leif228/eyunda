package com.hangyi.eyunda.controller.manage.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.order.OrderCommonData;
import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.order.OrderCommonService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.MD5;

@Controller
@RequestMapping("/manage/orderCommon")
public class OrderCommonController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderCommonService orderCommonService;

	// 获取一组对象，以列表的形式返回
	@RequestMapping(value = "/orderCommon", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView orderCommon(Page<OrderCommonData> pageData, String keyWords, String startDate, String endDate)
			throws Exception {

		if (startDate != null && endDate != null && !"".equals(startDate) && !"".equals(endDate)
				&& CalendarUtil.parseYY_MM_DD(endDate).before(CalendarUtil.parseYY_MM_DD(startDate)))
			throw new Exception("错误！起始日期必须小于终止日期。");

		pageData = orderCommonService.findOrderPage(pageData, keyWords, startDate, endDate);

		ModelAndView mav = new ModelAndView("/manage/manage-orderCommon");

		// 列表信息
		mav.addObject("pageData", pageData);
		mav.addObject("keyWords", keyWords);
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);

		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.ORDER);
		mav.addObject("menuAct", MenuCode.ORDER_QUERY);

		return mav;
	}

	// 合同下载框
	@RequestMapping(value = "/showContainer", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showContainer(Long orderId, OrderTypeCode orderType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCommonData ocd = orderCommonService.getOrderData(orderId);
		if (ocd == null) {
			throw new Exception("查询合同失败！");
		}

		String paraStr = "uid=" + ocd.getBrokerId() + "&orderId=" + orderId + "&orderType=" + orderType;
		String checksum = MD5.toMD5(paraStr + Constants.SALT_VALUE);

		String urlShow = "http://" + Constants.DOMAIN_NAME + "/space/orderCommon/showOrder?" + paraStr;
		urlShow += "&checksum=" + checksum;

		String urlDownload = "http://" + Constants.DOMAIN_NAME + "/space/orderCommon/downloadOrder?" + paraStr;
		urlDownload += "&checksum=" + checksum;

		ModelAndView mav = new ModelAndView("/manage/template/container");

		mav.addObject("urlShow", urlShow);
		mav.addObject("urlDownload", urlDownload);

		mav.addObject("caller", "web");

		return mav;
	}
}
