package com.hangyi.eyunda.controller.hyquan.back.member;

import java.util.HashMap;
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
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.hyquan.HyqCompanyCertificateData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.hyquan.HyqCompanyCertificateService;

@Controller
@RequestMapping("/back/member/compCert")
public class HyqCompanyCertificateController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqCompanyCertificateService companyCertificateService;

	// 获取一组对象，以列表的形式返回
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView compCertList(Page<HyqCompanyCertificateData> pageData, String keyWords,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/back/member/compCert");

		// 分页列表
		pageData = companyCertificateService.getCompCertPageData(pageData, keyWords);
		mav.addObject("pageData", pageData);

		// 菜单信息
		//mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		//mav.addObject("menuOpen", MenuCode.MEMBER);
		//mav.addObject("menuAct", MenuCode.MEMBER_COMPCERT);

		return mav;
	}

	// 获取一个对象
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public void addCompCert(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 港口城市分组列表
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得数据成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "取得数据失败！");
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

	// 获取一个对象
	@RequestMapping(value = "/edit", method = { RequestMethod.GET })
	public void editCompCert(Long compCertId, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqCompanyCertificateData compCertData = companyCertificateService.getCompCertData(compCertId);
			if (compCertData == null)
				compCertData = new HyqCompanyCertificateData();

			map.put("compCertData", compCertData);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得数据成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "取得数据失败！");
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public void saveCompCert(HyqCompanyCertificateData compCertData, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			companyCertificateService.saveCompCert(compCertData);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
		return;
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.DELETE })
	public void deleteCompCert(Long compCertId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			companyCertificateService.deleteCompCert(compCertId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

}
