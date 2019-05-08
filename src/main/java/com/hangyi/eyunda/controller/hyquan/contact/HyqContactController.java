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
import com.hangyi.eyunda.service.hyquan.HyqDepartmentService;
import com.hangyi.eyunda.service.hyquan.HyqUserService;

@Controller
@RequestMapping("/hyquan/contact")
public class HyqContactController extends HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqUserService userService;
	@Autowired
	private HyqDepartmentService departmentService;
	@Autowired
	private HyqCompanyService companyService;

	@RequestMapping(value = "/myContact", method = { RequestMethod.GET })
	public void myContact(String keyWords, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			// 公司列表
			List<HyqCompanyData> companyDatas = companyService.getCompanyDatas(userData.getId());

			// 输出参数
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("userData", userData);
			content.put("companyDatas", companyDatas);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 删除联系人，必须是业务员操作，不允许删除管理员
	@RequestMapping(value = "/myContact/deleteContact", method = { RequestMethod.GET })
	public void deleteContact(Long compId, Long deptId, Long userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			if (!userService.canMdfCompany(userData.getId(), compId))
				throw new Exception("非公司管理员不能进行此项操作!");

			departmentService.removeContact(userData, compId, deptId, userId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "联系人删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 向指定的部门添加一个用户，并赋予该部门缺省的权限类型
	@RequestMapping(value = "/myContact/addContact", method = { RequestMethod.POST })
	public void addContact(Long compId, Long deptId, Long userId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			if (!userService.canMdfCompany(userData.getId(), compId))
				throw new Exception("非公司管理员不能进行此项操作!");

			departmentService.addContact(compId, deptId, userId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "联系人添加成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
	}

	// 查找匹配的前6个用户，用于添加联系人
	@SuppressWarnings("unused")
	@RequestMapping(value = "/myContact/find", method = { RequestMethod.GET })
	public void find(String keyWords, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			List<HyqUserData> contacts = userService.findSixUsers(keyWords);

			Map<String, Object> content = new HashMap<String, Object>();

			if (!contacts.isEmpty()) {
				content.put("contacts", contacts);
				map.put(JsonResponser.CONTENT, content);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取得数据成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "未找到匹配成功的用户！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "查找用户时出错！");
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 保存部门，新增或改名
	@RequestMapping(value = "/department/saveDepartment", method = { RequestMethod.POST })
	public void saveDepartment(Long compId, Long deptId, String deptName, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			if (!userService.canMdfCompany(userData.getId(), compId))
				throw new Exception("非公司管理员不能进行此项操作!");

			deptId = departmentService.saveDepartment(userData, compId, deptId, deptName);

			Map<String, Object> content = new HashMap<String, Object>();

			if (deptId > 0) {
				content.put("deptId", deptId);
				map.put(JsonResponser.CONTENT, content);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "保存成功！");
			} else {
				throw new Exception("提交失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

	// 删除部门
	@RequestMapping(value = "/department/deleteDepartment", method = { RequestMethod.GET })
	public void deleteDepartment(Long compId, Long deptId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			if (!userService.canMdfCompany(userData.getId(), compId))
				throw new Exception("非公司管理员不能进行此项操作!");

			// 删除组别
			departmentService.deleteDepartment(compId, deptId);

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
