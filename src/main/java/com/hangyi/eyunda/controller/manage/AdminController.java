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
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.manage.AdminInfoData;
import com.hangyi.eyunda.data.manage.RoleInfoData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.manage.AdminService;
import com.hangyi.eyunda.service.manage.RoleService;

@Controller
@RequestMapping("/manage/power")
public class AdminController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdminService adminService;
	@Autowired
	private RoleService roleService;

	// 获取一组对象，以列表的形式返回
	@RequestMapping(value = "/admin", method = { RequestMethod.GET })
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response) {
		// 取出输入参数并给出缺省值

		// 处理业务逻辑，为了可以rpc，返回数据必须是序列化的
		List<AdminInfoData> adminDatas = adminService.getAdminDatas();
		List<RoleInfoData> roleDatas = roleService.getRoleDatas();

		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-admin");
		// 列表信息
		mav.addObject("adminDatas", adminDatas);
		// 角色对话框中角色数据列表
		mav.addObject("roleDatas", roleDatas);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.POWER);
		mav.addObject("menuAct", MenuCode.POWER_ADMIN);

		return mav;
	}

	// 获取一个对象
	@RequestMapping(value = "/admin/{id}", method = { RequestMethod.GET })
	public void getEntity(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AdminInfoData adminData = adminService.getAdminData(id);

			if (adminData != null) {
				map.put("entity", adminData);
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

	@RequestMapping(value = "/admin/delete", method = { RequestMethod.DELETE })
	public ModelAndView delete(Long id, HttpServletRequest request, HttpServletResponse response) {
		adminService.deleteAdmin(id);
		ModelAndView mav = new ModelAndView("redirect:/manage/power/admin");
		return mav;
	}

	@RequestMapping(value = "/admin/save", method = { RequestMethod.POST })
	public ModelAndView save(AdminInfoData admin, HttpServletRequest request, HttpServletResponse response) {
		adminService.saveOrUpdate(admin);

		ModelAndView mav = new ModelAndView("redirect:/manage/power/admin");
		return mav;
	}

	@RequestMapping(value = "/admin/saveRole", method = { RequestMethod.POST })
	public ModelAndView saveRole(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		Long id = ServletRequestUtils.getLongParameter(request, "id");
		long[] roleIds = ServletRequestUtils.getLongParameters(request, "role");

		adminService.updateRoles(id, roleIds);

		ModelAndView mav = new ModelAndView("redirect:/manage/power/admin");
		return mav;
	}

}
