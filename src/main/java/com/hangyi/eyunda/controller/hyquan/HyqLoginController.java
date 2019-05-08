package com.hangyi.eyunda.controller.hyquan;

import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.event.OnlineStatusCode;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.domain.enumeric.HyqCheckTypeCode;
import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;
import com.hangyi.eyunda.service.hyquan.HyqMessageService;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.service.hyquan.HyqUserService.ResultType;
import com.hangyi.eyunda.service.redis.RedisService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CookieUtil;
import com.hangyi.eyunda.util.SessionUtil;
import com.hangyi.eyunda.util.StringUtil;

@Controller
@RequestMapping("/hyquan/login")
public class HyqLoginController extends HyqBaseController {// 统一登录
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String MBL_SESSION_ID = "sessionId";
	public static final String USER_COOKIE = "uck";

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private HyqUserService userService;
	@Autowired
	private HyqMessageService messageService;

	@Autowired
	private RedisService redisService;

	/** 登录 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginShow(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/hyqh5/login");
		return mav;
	}

	/** 登录 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ResultType result = ResultType.FAILURE;

			// 手机登录时，输入simCardNo、loginName、password四个参数
			String simCardNo = ServletRequestUtils.getStringParameter(request, "simCardNo", "");// 手机卡编号
			String loginName = ServletRequestUtils.getStringParameter(request, "loginName", "");
			String password = ServletRequestUtils.getStringParameter(request, "password", "");

			// 登录验证
			result = userService.login(loginName, password);

			if (result == ResultType.SUCCESS) {// 登录成功
				HyqUserData userData = userService.getByLoginName(loginName);

				// 绑定simCardNo
				userService.updateSimCardNo(userData.getId(), simCardNo);
				userData.setSimCardNo(simCardNo);

				userData.setSessionId(SessionUtil.getFirstSessionId(userData.getId()));// 第一次产生sessionId
				userData.setOnlineStatus(OnlineStatusCode.online);// 在线状态

				// 返回绑定码
				userData.setBindingCode(SessionUtil.getBindingCode(userData.getId(), userData.getLoginName()));

				// redis中存入在线用户对象
				OnlineUser ou = new OnlineUser();
				ou.setId(userData.getId());
				ou.setLoginName(userData.getLoginName());
				ou.setTrueName(userData.getTrueName());
				ou.setNickName(userData.getNickName());
				ou.setEnterTime(new Date().getTime());
				ou.setLoginServerName(Constants.MESSAGE_SERVER_NAME);
				ou.setOntime(new Date().getTime());
				ou.setSessionId(userData.getSessionId());
				ou.setSimNo(simCardNo);
				onlineUserRecorder.addOnlineUser(ou, LoginSourceCode.mobile);

				// 写Cookie
				String userId = Long.toString(userData.getId());
				String loginToken = userService.getLoginToken(userData);
				String uck = userId + "," + loginToken;
				int maxAge = 1 * 24 * 60 * 60;

				CookieUtil.setCookie(request, response, HyqLoginController.USER_COOKIE, uck, maxAge);

				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, result.getDescription());
				map.put(JsonResponser.CONTENT, userData);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	/** 自动登录 */
	@RequestMapping(value = "/autoLogin", method = RequestMethod.POST)
	public void autoLogin(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ResultType result = ResultType.FAILURE;

			String simCardNo = ServletRequestUtils.getStringParameter(request, "simCardNo", "");
			String bindingCode = ServletRequestUtils.getStringParameter(request, "bindingCode", "");

			result = userService.autoLogin(simCardNo, bindingCode);

			if (result == ResultType.SUCCESS) {
				HyqUserData userData = userService.getBySimCardNo(simCardNo);

				userData.setSessionId(SessionUtil.getFirstSessionId(userData.getId()));// 第一次产生sessionId
				userData.setOnlineStatus(OnlineStatusCode.online);// 在线状态

				// 返回绑定码
				userData.setBindingCode(SessionUtil.getBindingCode(userData.getId(), userData.getLoginName()));

				// redis中存入在线用户对象
				OnlineUser ou = new OnlineUser();
				ou.setId(userData.getId());
				ou.setLoginName(userData.getLoginName());
				ou.setTrueName(userData.getTrueName());
				ou.setNickName(userData.getNickName());
				ou.setEnterTime(new Date().getTime());
				ou.setLoginServerName(Constants.MESSAGE_SERVER_NAME);
				ou.setOntime(new Date().getTime());
				ou.setSessionId(userData.getSessionId());
				ou.setSimNo(simCardNo);

				OnlineUser onlineUser = onlineUserRecorder.getOnlineUser(userData.getId());
				if (onlineUser == null)
					onlineUserRecorder.addOnlineUser(ou, LoginSourceCode.mobile);
				else
					userData.setSessionId(onlineUser.getSessionId());

				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, result.getDescription());
				map.put(JsonResponser.CONTENT, userData);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	/** 登出 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String sessionId = ServletRequestUtils.getStringParameter(request, HyqLoginController.MBL_SESSION_ID, "");
			OnlineUser ou1 = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou1 != null) {
				HyqUserData u1 = userService.getById(ou1.getId());
				if (u1 != null) {
					u1.setSimCardNo("");
					userService.update(u1);
				}
				onlineUserRecorder.removeOnlineUser(ou1, LoginSourceCode.mobile);
			} else {
				String uck = CookieUtil.getCookieValue(request, HyqLoginController.USER_COOKIE);
				HyqUserData u2 = userService.getByCookie(uck);
				if (u2 != null) {
					u2.setSimCardNo("");
					userService.update(u2);

					OnlineUser ou2 = onlineUserRecorder.getOnlineUser(u2.getId());
					if (ou2 != null) {
						onlineUserRecorder.removeOnlineUser(ou2, LoginSourceCode.mobile);
					}
				}
			}

			CookieUtil.deleteCookie(request, response, USER_COOKIE);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功登出");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	/** 注册或找回密码时获取手机校验码 */
	@RequestMapping(value = "/checkCode", method = { RequestMethod.GET, RequestMethod.POST })
	public void checkCode(String mobile, HyqCheckTypeCode checkType, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String checkCode = StringUtil.getRandomDigits(6);
			// 校验码存入redis
			redisService.setValueToQueue(MessageConstants.REGISTER_QUEUE, mobile, checkCode);

			switch (checkType) {
			case register:
				// 检查该手机是否已经注册
				if (userService.getByMobile(mobile) == null) {
					// 发送验证码到手机
					String welcome = "欢迎注册船运宝用户，船运宝是处理航运业务的好帮手！";
					boolean result = messageService.sendCheckCodeTo(mobile, welcome, checkCode);
					// boolean result = true;// 临时停用短信验证接口
					if (result) {
						map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
						map.put(JsonResponser.MESSAGE, "发送验证码到手机成功。");
					} else {
						map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
						map.put(JsonResponser.MESSAGE, "发送验证码到手机失败。");
					}
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "该手机已经注册，请使用找回密码功能。");
				}
				break;
			case findPassword:
				// 检查该手机是否已经注册
				if (userService.getByMobile(mobile) != null) {
					// 发送验证码到手机
					String welcome = "密码只接受英文字母及数字，长度不超过20位。";
					boolean result = messageService.sendCheckCodeTo(mobile, welcome, checkCode);
					// boolean result = true;// 临时停用短信验证接口
					if (result) {
						map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
						map.put(JsonResponser.MESSAGE, "发送验证码到手机成功。");
					} else {
						map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
						map.put(JsonResponser.MESSAGE, "发送验证码到手机失败。");
					}
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "该手机用户未找到，不能修改密码。");
				}
				break;
			}

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	/** 注册 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerShow(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/hyqh5/register");
		return mav;
	}

	/** 注册 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(HyqUserData userData, String checkCode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 项目发布前，停用短信接口,短信接口未开发防止攻击功能
			String v = redisService.getValueFromQueue(MessageConstants.REGISTER_QUEUE, userData.getMobile());
			if (checkCode == null || "".equals(checkCode))
				throw new Exception("未输入手机验证码，不可注册！");
			if (!checkCode.equals(v))
				throw new Exception("手机验证码输入错误，不可注册！");

			ResultType result = ResultType.FAILURE;
			result = userService.register(userData);

			if (result == ResultType.SUCCESS) {
				redisService.delFieldFromQueue(MessageConstants.REGISTER_QUEUE, userData.getMobile());

				map.put(JsonResponser.CONTENT, userData.getId());
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	/** 用户信息修改 */
	@RequestMapping(value = "/userUpdate", method = RequestMethod.POST)
	public void userUpdate(HyqUserData userData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ResultType result = ResultType.FAILURE;
			result = userService.update(userData);

			if (result == ResultType.SUCCESS) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	/** 找回密码 */
	@RequestMapping(value = "/findPassword", method = RequestMethod.GET)
	public ModelAndView findPasswordShow(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/hyqh5/findPassword");
		return mav;
	}

	/** 找回密码 */
	@RequestMapping(value = "/findPassword", method = RequestMethod.POST)
	public void findPassword(String mobile, String checkCode, String idCard6, String password, String password2,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 项目发布前，停用短信接口
			String v = redisService.getValueFromQueue(MessageConstants.REGISTER_QUEUE, mobile);
			if (checkCode == null || "".equals(checkCode))
				throw new Exception("未输入手机验证码，不可修改密码！");
			if (!checkCode.equals(v))
				throw new Exception("手机验证码输入错误，不可修改密码！");

			HyqUserData userData = userService.getByMobile(mobile);
			if (userData == null)
				throw new Exception("用户未找到，不可修改密码！");
//			if (!userData.getIdCardNo().substring(12).equals(idCard6))
//				throw new Exception("身份证号码输入错误，不可修改密码！");

			ResultType result = userService.resetPasswd(userData.getId(), password, password2);

			if (result == ResultType.SUCCESS) {
				redisService.delFieldFromQueue(MessageConstants.REGISTER_QUEUE, mobile);

				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/uploadLogo", method = { RequestMethod.POST })
	public void uploadLogo(MultipartFile logoMpf, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HyqUserData userData = this.getLoginUserData(request, response);
			if (userData == null)
				throw new Exception("警告！未登录不能上传用户头像。");

			String url = userService.uploadLogo(userData.getId(), logoMpf);

			if (url != null && !"".equals(url)) {
				map.put(JsonResponser.CONTENT, url);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

}
