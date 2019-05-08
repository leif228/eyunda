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
import com.hangyi.eyunda.data.hyquan.HyqFriendData;
import com.hangyi.eyunda.data.hyquan.HyqFriendTagData;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.domain.enumeric.ApplyStatusCode;
import com.hangyi.eyunda.service.hyquan.HyqFriendService;

@Controller
@RequestMapping("/hyquan/friend")
public class HyqFriendController extends HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqFriendService friendService;

	@RequestMapping(value = "/getFriendList", method = { RequestMethod.GET })
	public void getFriendList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			// 好友按标签分组列表，也是标签列表
			List<HyqFriendTagData> friendTagDatas = friendService.getFriendTagDatas(userData.getId());

			// 输出参数
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("userData", userData);
			content.put("friendTagDatas", friendTagDatas);

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

	@RequestMapping(value = "/getNewFriendList", method = { RequestMethod.GET })
	public void getNewFriendList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			// 申请加为好友的列表
			List<HyqFriendData> applyDatas = friendService.getMyFriendDatas(userData.getId(), ApplyStatusCode.apply);
			// 拒绝加为好友的列表
			List<HyqFriendData> rejectDatas = friendService.getMyFriendDatas(userData.getId(), ApplyStatusCode.reject);
			// 好友按标签分组列表，也是标签列表
			List<HyqFriendTagData> friendTagDatas = friendService.getFriendTagDatas(userData.getId());

			// 输出参数
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("userData", userData);
			content.put("applyDatas", applyDatas);
			content.put("rejectDatas", rejectDatas);
			content.put("friendTagDatas", friendTagDatas);

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

	@RequestMapping(value = "/removeFriend", method = { RequestMethod.GET })
	public void removeFriend(Long friendId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			friendService.removeFriend(userData.getId(), friendId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "删除好友关系成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/applyFriend", method = { RequestMethod.POST })
	public void applyFriend(Long tagId, Long friendId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			friendService.applyFriend(userData.getId(), tagId, friendId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "申请添加好友成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
	}

	@RequestMapping(value = "/approveFriend", method = { RequestMethod.POST })
	public void approveFriend(Long tagId, Long friendId, ApplyStatusCode approveCode, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			friendService.approveFriend(userData.getId(), tagId, friendId, approveCode);
			HyqUserData friend = userService.getById(friendId);

			String message = "处理成功！你已" + approveCode.getRemark() + friend.getTrueName() + friend.getMobile() + "为好友。";
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, message);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
	}

	@RequestMapping(value = "/saveTag", method = { RequestMethod.POST })
	public void saveTag(Long tagId, String tag, Integer no, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			tagId = friendService.saveTag(userData.getId(), tagId, tag, no);

			Map<String, Object> content = new HashMap<String, Object>();

			if (tagId > 0) {
				content.put("tagId", tagId);
				map.put(JsonResponser.CONTENT, content);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "保存标签成功！");
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

	@RequestMapping(value = "/deleteTag", method = { RequestMethod.GET })
	public void deleteTag(Long tagId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);

			friendService.deleteTag(userData.getId(), tagId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "删除标签成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

}
