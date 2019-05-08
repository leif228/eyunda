package com.hangyi.eyunda.controller.hyquan;

import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.service.redis.RedisService;
import com.hangyi.eyunda.util.CookieUtil;
import com.hangyi.eyunda.util.MatrixToImageWriter;

@Controller
@RequestMapping("/hyquan/login")
public class HyqQcodeLoginController {// 统一登录
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqUserService userService;
	@Autowired
	private RedisService redisService;

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;

	@RequestMapping(value = "/getQcodeImage", method = RequestMethod.GET)
	public void getQcodeImage(String qcode, HttpServletRequest request, HttpServletResponse response) {
		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode("LOGIN|" + qcode, BarcodeFormat.QR_CODE, 800, 800, hints);
			MatrixToImageWriter.writeToStream(bitMatrix, "jpg", response.getOutputStream());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	// 手机扫码后用于上传userId
	@RequestMapping(value = "/scanQcodeAfter", method = RequestMethod.POST)
	public void scanQcodeAfter(String qcode, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");

		OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
		if (ou == null)
			throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");

		if (!qcode.equals("")) {
			redisService.setValueToQueue(MessageConstants.QCODE_QUEUE, qcode, ou.getId().toString());

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
		} else {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "扫描失败，请刷新二维码后重新扫描");
		}

		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/verifyQcodeLogin", method = RequestMethod.GET)
	public void verifyQcodeLogin(String qcode, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = null;
			String v = redisService.getValueFromQueue(MessageConstants.QCODE_QUEUE, qcode);
			if (v != null && !"".equals(v)) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "登录成功");
				map.put(JsonResponser.CONTENT, userData);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "无用户登录");
			}

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/autoQcodeLogin", method = RequestMethod.GET)
	public ModelAndView autoQcodeLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String qcode = ServletRequestUtils.getStringParameter(request, "qcode", "");
		if (qcode.equals("")) {
			throw new Exception("未授权操作");
		}

		HyqUserData userData = null;
		String v = redisService.getValueFromQueue(MessageConstants.QCODE_QUEUE, qcode);
		if (v != null && !"".equals(v))
			userData = userService.getById(Long.parseLong(v));

		if (userData != null) {
			// 写Cookie
			String userId = Long.toString(userData.getId());
			String loginToken = userService.getLoginToken(userData);
			String uck = userId + "," + loginToken;
			int maxAge = 1 * 24 * 60 * 60;

			CookieUtil.setCookie(request, response, HyqLoginController.USER_COOKIE, uck, maxAge);

			redisService.delFieldFromQueue(MessageConstants.QCODE_QUEUE, qcode);

			ModelAndView mv = new ModelAndView("redirect:/index.jsp");
			return mv;
		} else {
			throw new Exception("未授权操作");
		}
	}

}
