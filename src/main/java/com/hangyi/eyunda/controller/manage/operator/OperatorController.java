package com.hangyi.eyunda.controller.manage.operator;

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
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.account.OperatorData;
import com.hangyi.eyunda.domain.enumeric.ApplyStatusCode;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.account.OperatorService;

@Controller
@RequestMapping("/manage/member")
public class OperatorController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OperatorService operService;

	@RequestMapping(value = "/operator", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView operator(ApplyStatusCode status, HttpServletRequest request, HttpServletResponse response) {
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-operator-audit");

		// 获得角色,默认承运人
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		String operInfo = ServletRequestUtils.getStringParameter(request, "operInfo", "");

		Page<OperatorData> pageData = operService.getPageByOperInfos(pageNo, operInfo, status);

		// 列表信息
		mav.addObject("status", status);
		mav.addObject("statuss", ApplyStatusCode.values());
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageData", pageData);
		mav.addObject("operInfo", operInfo);

		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.MEMBER);
		mav.addObject("menuAct", MenuCode.MEMBER_OPERATOR);
		return mav;
	}

	// 删除
	@RequestMapping(value = "/operator/delete", method = { RequestMethod.DELETE })
	public ModelAndView deleteOperator(Long id, int pageNo, String operInfo, ApplyStatusCode status) {
		operService.deleteOperator(id);
		ModelAndView mav = new ModelAndView("redirect:/manage/member/operator?pageNo=" + pageNo + "&status="
				+ (status == null ? "" : status) + "&operInfo=" + operInfo);
		return mav;
	}

	// 取消审核
	@RequestMapping(value = "/operator/unaudit", method = { RequestMethod.POST })
	public ModelAndView unaudit(Long id, int pageNo, String operInfo, ApplyStatusCode status) {
		operService.unaudit(id);
		ModelAndView mav = new ModelAndView("redirect:/manage/member/operator?pageNo=" + pageNo + "&status="
				+ (status == null ? "" : status) + "&operInfo=" + operInfo);
		return mav;
	}

	// 审核
	@RequestMapping(value = "/operator/audit", method = { RequestMethod.POST })
	public ModelAndView audit(Long id, ApplyStatusCode status, String operInfo, int pageNo,
			ApplyStatusCode queryStatus) {
		operService.audit(id, status);
		ModelAndView mav = new ModelAndView("redirect:/manage/member/operator?pageNo=" + pageNo + "&status="
				+ (queryStatus == null ? "" : queryStatus) + "&operInfo=" + operInfo);
		return mav;
	}

	// 获取一个对象
	@RequestMapping(value = "/operator/{id}", method = { RequestMethod.GET })
	public void getOperator(@PathVariable("id") Long id, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 处理业务逻辑，为了可以rpc，返回数据必须是序列化的
			OperatorData operData = operService.getOperatorDataById(id);

			if (operData != null) {
				map.put("operData", operData);
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

	@RequestMapping(value = "/operator/showImage", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/manage/manage-ship-showImage");

		Long id = ServletRequestUtils.getLongParameter(request, "id", 0L);
		String currImageUrl = ServletRequestUtils.getStringParameter(request, "url", "");

		OperatorData operData = operService.getOperatorDataById(id);

		List<String> urls = operService.getImagesUrls(operData);

		if (currImageUrl != null && !"".equals(currImageUrl)) {
			mav.addObject("currImageUrl", currImageUrl);
		}

		if (urls != null) {
			mav.addObject("urls", urls);
			int currIndex = 0;
			for (int i = 0; i < urls.size(); i++) {
				if (currImageUrl.equals(urls.get(i))) {
					currIndex = i;
					break;
				}
			}
			mav.addObject("currIndex", currIndex);
		}

		return mav;
	}

}
