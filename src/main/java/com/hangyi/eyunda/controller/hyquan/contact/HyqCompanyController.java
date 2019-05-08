package com.hangyi.eyunda.controller.hyquan.contact;

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

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.controller.hyquan.HyqBaseController;
import com.hangyi.eyunda.data.hyquan.HyqCompanyData;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.service.hyquan.HyqCompanyService;

@Controller
@RequestMapping("/hyquan/company")
public class HyqCompanyController extends HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqCompanyService companyService;

	// 获取我的公司名称
	@RequestMapping(value = "/companyList", method = { RequestMethod.GET, RequestMethod.POST })
	public void companyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			Map<String, Object> content = new HashMap<String, Object>();

			String contentMd5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			// 公司列表
			List<HyqCompanyData> companyDatas = companyService.getCompanyDatas(userData.getId());

			content.put("userData", userData);
			content.put("companyDatas", companyDatas);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMd5);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 获取公司数据
	@RequestMapping(value = "/getCompany", method = { RequestMethod.GET })
	public void getCompany(Long compId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			@SuppressWarnings("unused")
			HyqUserData userData = this.getLoginUserData(request, response);

			Map<String, Object> content = new HashMap<String, Object>();

			HyqCompanyData compData = companyService.getCompanyData(compId);

			content.put("compData", compData);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 保存公司信息
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public void save(Long compId, String compName, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			boolean result = companyService.saveComp(userData, compId, compName);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "保存成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "保存失败，提交的数据内容或格式有误！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
	}

	// 删除公司信息
	@RequestMapping(value = "/delete", method = { RequestMethod.GET })
	public void delete(Long compId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			boolean result = companyService.deleteComp(userData, compId);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "删除成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "删除失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

}
