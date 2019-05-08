package com.hangyi.eyunda.controller.mobile;

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
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.DepartmentData;
import com.hangyi.eyunda.data.account.DeptShipData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.service.account.DepartmentService;
import com.hangyi.eyunda.service.account.PrivilegeService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.util.Constants;

@Controller
@RequestMapping("/mobile/contact")
public class MblContactController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private UserService userService;
	@Autowired
	private MyShipService shipService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PrivilegeService privilegeService;

	// 获取当前用户对象，含用户所在公司列表及当前公司
	private UserData getLoginUserData(HttpServletRequest request) throws Exception {
		String sessionId = ServletRequestUtils.getStringParameter(request, MblLoginController.MBL_SESSION_ID, "");
		OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
		if (ou == null)
			throw new Exception("session已丢失请重新登录！");

		UserData userData = userService.getById(ou.getId());
		if (userData == null)
			throw new Exception("未登录不能进行此项操作！");

		String strCompId = ServletRequestUtils.getStringParameter(request, MblLoginController.CURR_COMP_ID, null);
		if (strCompId == null)
			strCompId = "0"; // throw new Exception("你还不属于任何公司，请先注册公司并加入!");
		Long compId = Long.parseLong(strCompId);

		CompanyData currCompanyData = userService.getCompanyData(compId);
		List<CompanyData> companyDatas = userService.getCompanyDatas(userData.getId());
		userData.setCurrCompanyData(currCompanyData);
		userData.setCompanyDatas(companyDatas);

		return userData;
	}

	/**
	 * 2.企业用户审核应检查资质、证书及是否绑定对公帐户， 审核通过后，成为企业的超级管理员，可建立部门，添加移除个人用户，
	 * 个人用户角色随部门类型走，其在企业中依据角色行事，可加入多个部门， 角色分为：企业管理员、业务员、船员、船东、货主、法务、档案员等；
	 * 企业超级管理员不可被移除，也不可被添加到其他公司或其他部门， 纯船东不可见船东、货主类部门及成员，？ 纯货主不可见船东、货主类部门及成员；？
	 */
	@RequestMapping(value = "/myContact", method = { RequestMethod.GET })
	public void myContact(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			// 部门列表
			List<DepartmentData> departmentDatas = departmentService
					.getDepartmentDatas(userData.getCurrCompanyData().getId());

			// 非管理员或业务员，不让其见到船员、船东与货主
			if (!userService.isRole(userData, UserPrivilegeCode.manager)
					&& !userService.isRole(userData, UserPrivilegeCode.handler)) {
				List<DepartmentData> dds = new ArrayList<DepartmentData>();
				for (DepartmentData d : departmentDatas) {
					if (d.getDeptType() == UserPrivilegeCode.master || d.getDeptType() == UserPrivilegeCode.owner
							|| d.getDeptType() == UserPrivilegeCode.sailor) {
						dds.add(d);
					}
				}
				departmentDatas.removeAll(dds);
			}

			// 部门中塞入联系人列表
			for (DepartmentData d : departmentDatas)
				d.setUserDatas(departmentService.getDeptUserData(d.getId()));

			// 只有管理员才可维护联系人
			boolean flag = false;
			if (userService.isRole(userData, UserPrivilegeCode.manager))
				flag = true;

			// 输出参数
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("departmentDatas", departmentDatas);
			content.put("flag", flag);

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

	// 船员上报初始化
	@RequestMapping(value = "/myContact/sailorReportInit", method = { RequestMethod.GET })
	public void sailorReportInit(Long deptId, Long userId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.manager))
				throw new Exception("非管理员不能进行此项操作!");

			DepartmentData departmentData = departmentService.getDepartmentData(deptId);
			if (departmentData == null)
				throw new Exception("错误！部门信息已经找不到!");

			if (departmentData.getDeptType() != UserPrivilegeCode.sailor)
				throw new Exception("错误！非船员部门成员，不能设置动态上报船舶!");

			// 已经指定的船舶mmsi
			String mmsi = privilegeService.getShipMmsiForSailor(userId, userData.getCurrCompanyData().getId());

			UserData sailor = userService.getById(userId);

			// 部门列表联系人列表
			List<DepartmentData> departmentDatas = departmentService
					.getDepartmentDatas(userData.getCurrCompanyData().getId());

			// 部门船舶map
			List<DeptShipData> deptShipDatas = new ArrayList<DeptShipData>();
			for (DepartmentData d : departmentDatas) {
				if (d.getDeptType() == UserPrivilegeCode.master) {
					Page<ShipData> pageShipData = shipService.getSimpleShipPage(userData.getCurrCompanyData().getId(),
							"", 1, Constants.ALL_SIZE, "", d.getId(), 0L);

					DeptShipData dsd = new DeptShipData();
					dsd.setDeptId(d.getId());
					dsd.setDepartmentData(d);
					dsd.setShipDatas(pageShipData.getResult());

					deptShipDatas.add(dsd);
				}
			}

			// 输出数据
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("deptId", deptId);
			content.put("userId", userId);
			content.put("trueName", sailor.getTrueName());
			content.put("deptShipDatas", deptShipDatas);
			content.put("mmsi", mmsi);

			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "船员上报初始化成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 船员上报设置
	@RequestMapping(value = "/myContact/sailorReportSave", method = { RequestMethod.GET, RequestMethod.POST })
	public void sailorReportSave(Long deptId, Long userId, String mmsi, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.manager))
				throw new Exception("非管理员不能进行此项操作!");

			departmentService.saveSailorReportPrivilege(userData.getCurrCompanyData().getId(), userId, mmsi);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "设置船员动态上报船舶成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 删除联系人，必须是业务员操作，不允许删除管理员
	@RequestMapping(value = "/myContact/deleteContact", method = { RequestMethod.GET })
	public void deleteContact(Long deptId, Long userId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.manager))
				throw new Exception("非管理员不能进行此项操作!");

			departmentService.deleteContact(userData.getCurrCompanyData().getId(), deptId, userId);

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
	public void addContact(Long deptId, Long userId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.manager))
				throw new Exception("非管理员不能进行此项操作!");

			departmentService.addContact(userData.getCurrCompanyData().getId(), deptId, userId);

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
	@RequestMapping(value = "/myContact/find", method = { RequestMethod.GET })
	public void find(String keyWords, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.manager))
				throw new Exception("非管理员不能进行此项操作!");

			List<UserData> contacts = userService.frontFindUsers(keyWords);

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

	// 增加部门
	@RequestMapping(value = "/department/addDepartment", method = { RequestMethod.GET })
	public void addDepartment(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.manager))
				throw new Exception("非管理员不能进行此项操作!");

			// 部门列表
			List<DepartmentData> departmentDatas = departmentService
					.getDepartmentDatas(userData.getCurrCompanyData().getId());

			Map<String, Object> content = new HashMap<String, Object>();
			content.put("departmentDatas", departmentDatas);

			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "进入部门管理对话框成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);

		return;
	}

	// 保存部门
	@RequestMapping(value = "/department/saveDepartment", method = { RequestMethod.POST })
	public void saveDepartment(Long deptId, String deptName, UserPrivilegeCode deptType, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.manager))
				throw new Exception("非管理员不能进行此项操作!");

			deptId = departmentService.saveDepartment(userData.getCurrCompanyData().getId(), deptId, deptName,
					deptType);

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
	public void deleteDepartment(Long deptId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			if (!userService.isRole(userData, UserPrivilegeCode.manager))
				throw new Exception("非管理员不能进行此项操作!");

			// 删除组别
			departmentService.deleteDepartment(userData.getCurrCompanyData().getId(), deptId);

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
