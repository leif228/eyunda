package com.hangyi.eyunda.controller.manage.operator;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.enumeric.UserStatusCode;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.cargo.CargoService;
import com.hangyi.eyunda.service.chat.ChatRoomService;
import com.hangyi.eyunda.service.portal.login.UserService;

@Controller
@RequestMapping("/manage/member")
public class UserController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserService userService;
	@Autowired
	private CargoService cargoService;
	@Autowired
	private ChatRoomService chatRoomService;

	@RequestMapping(value = "/user", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView findUser(Page<UserData> pageData, String userInfo, UserStatusCode status) {

		pageData = userService.backFindUserDatas(userInfo, status, pageData.getPageNo(), pageData.getPageSize());

		if (userInfo == null) {
			userInfo = "";
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-user");

		// 列表信息
		mav.addObject("status", status);
		mav.addObject("statuss", UserStatusCode.values());
		mav.addObject("pageData", pageData);
		mav.addObject("userInfo", userInfo);
		// 角色对话框中合同数据列表
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.MEMBER);
		mav.addObject("menuAct", MenuCode.MEMBER_USER);
		return mav;
	}

	// 获取一个对象
	@RequestMapping(value = "/user/{id}", method = { RequestMethod.GET })
	public void getUser(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 处理业务逻辑，为了可以rpc，返回数据必须是序列化的
			UserData user = userService.getById(id);

			if (user != null) {
				map.put("user", user);
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

	@RequestMapping(value = "/user/delete", method = { RequestMethod.DELETE })
	public ModelAndView deleteUser(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 查找用户
		UserData userData = userService.getById(id);
		if (userData == null)
			throw new Exception("用户信息不存在，无法删除！");

		// 删除拥有的货物
		boolean bCargo = cargoService.deleteCargos(userData);
		if (!bCargo)
			throw new Exception("删除用户的货物信息时出错！");

		// 聊天室删除或加删除标记
		boolean bChat = chatRoomService.deleteRooms(userData.getId());
		if (!bChat)
			throw new Exception("删除用户的聊天信息时出错！");

		// 删除用户
		boolean bUser = userService.deleteUser(id);
		if (!bUser)
			throw new Exception("删除用户信息时出错！");

		ModelAndView mav = new ModelAndView("redirect:/manage/member/user");
		return mav;
	}

	@RequestMapping(value = "/user/activity", method = { RequestMethod.GET })
	public void activeUser(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 激活
			boolean result = userService.activity(id);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "激活成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "已激活！");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作异常");
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
		return;
	}

	@RequestMapping(value = "/user/unActivity", method = { RequestMethod.GET })
	public void unActiveUser(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 处理业务逻辑，为了可以rpc，返回数据必须是序列化的
			boolean result = userService.unActivity(id);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取消激活成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "未激活！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作异常！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}
}
