package com.hangyi.eyunda.controller.manage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.hyquan.HyqUserService;

@Controller
@RequestMapping("/manage/member/hyqmember")
public class MemberManageController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	HyqUserService userService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView userList(Page<HyqUserData> pageData, String userInfo) {
		if (userInfo == null)
			userInfo = "";

		pageData = userService.getUserPageData(pageData, userInfo, null);

		ModelAndView mav = new ModelAndView("/manage/manage-hyqmember");

		mav.addObject("pageData", pageData);
		mav.addObject("userInfo", userInfo);

		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.MEMBER);
		mav.addObject("menuAct", MenuCode.MEMBER_HYQUSER);
		return mav;
	}

	// 获取一个对象
	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public void getUser(@PathVariable("id") Long id, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = userService.getById(id);

			if (userData != null) {
				map.put("user", userData);
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
		return;
	}

	// 容易影响数据一致性，一般不要使用
	@RequestMapping(value = "/delete", method = { RequestMethod.DELETE })
	public ModelAndView deleteUser(Long id) throws Exception {
		// 查找用户
		HyqUserData userData = userService.getById(id);
		if (userData == null)
			throw new Exception("用户信息不存在，无法删除！");

		// 删除用户
		boolean bUser = userService.deleteUser(id);
		if (!bUser)
			throw new Exception("删除用户信息时出错！");

		ModelAndView mav = new ModelAndView("redirect:/manage/member/hyqmember/list");
		return mav;
	}

	@RequestMapping(value = "/activity", method = { RequestMethod.GET })
	public void activeUser(Long id, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 激活
			boolean result = userService.activity(id);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "激活成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "激活失败！");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "激活失败");
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

	@RequestMapping(value = "/unActivity", method = { RequestMethod.GET })
	public void unActiveUser(Long id, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 取消激活
			boolean result = userService.unActivity(id);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取消激活成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "取消激活失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "取消激活失败！");
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

}
