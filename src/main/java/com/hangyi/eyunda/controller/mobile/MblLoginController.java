package com.hangyi.eyunda.controller.mobile;

import java.util.Calendar;
import java.util.Date;
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

import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.event.OnlineStatusCode;
import com.hangyi.eyunda.chat.mina.server.SessionManager;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.chat.redis.recorder.RedisCaptchaRecorder;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.YydArea;
import com.hangyi.eyunda.domain.YydCity;
import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;
import com.hangyi.eyunda.service.PCAreaService;
import com.hangyi.eyunda.service.manage.stat.RecordLoginService;
import com.hangyi.eyunda.service.portal.login.MailUtil;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.portal.login.UserService.ResultType;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CookieUtil;
import com.hangyi.eyunda.util.FileUtil;
import com.hangyi.eyunda.util.MD5;
import com.hangyi.eyunda.util.SessionUtil;
import com.sun.syndication.io.impl.Base64;

import jodd.util.StringUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Controller
@RequestMapping("/mobile/login")
public class MblLoginController {// 统一登录
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String MBL_SESSION_ID = "sessionId";
	public static final String CURR_COMP_ID = "currCompId";

	public static final String CAPTCHA = "captcha";
	public static final int PRE_TWENTY = 20;

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private RedisCaptchaRecorder redisCaptchaRecorder;
	@Autowired
	private RecordLoginService recordLoginService;
	@Autowired
	private UserService userService;
	@Autowired
	MyShipService myShipService;
	@Autowired
	private PCAreaService pCAreaService;
	@Autowired
	private JedisPool redisPool;

	/**
	 * 1.注册用户分“企业”或“个人”， 仅企业用户可以申请成为代理人， 企业用户注册后，系统自动建立公司及企业综合管理部，并将该企业用户加入该部门，
	 * 企业用户类型不允许修改，绑卡也只允许绑定对公帐户；
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ResultType result = ResultType.FAILURE;

			String simCardNo = ServletRequestUtils.getStringParameter(request, "simCardNo", "");
			String loginName = ServletRequestUtils.getStringParameter(request, "loginName", "");
			String password = ServletRequestUtils.getStringParameter(request, "password", "");

			// 登录验证
			result = userService.login(loginName, password);

			if (result == ResultType.SUCCESS) {// 登录成功
				UserData userData = userService.getByLoginName(loginName);

				// 绑定simCardNo
				userService.updateSimCardNo(userData.getId(), simCardNo);
				userData.setSimCardNo(simCardNo);

				userData.setSessionId(SessionUtil.getFirstSessionId(userData.getId()));// 第一次产生sessionId
				userData.setOnlineStatus(OnlineStatusCode.online);// 在线状态

				// 返回绑定码
				userData.setBindingCode(SessionUtil.getBindingCode(userData.getId(), userData.getLoginName()));

				// 返回用户所在的公司列表并置当前公司
				List<CompanyData> companyDatas = userService.getCompanyDatas(userData.getId());
				if (!companyDatas.isEmpty())
					userData.setCurrCompanyData(companyDatas.get(0));
				userData.setCompanyDatas(companyDatas);

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
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
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
			Long existCompId = ServletRequestUtils.getLongParameter(request, "existCompId", 0L);
			
			result = userService.autoLogin(simCardNo, bindingCode);

			if (result == ResultType.SUCCESS) {
				UserData userData = userService.getBySimCardNo(simCardNo);

				userData.setSessionId(SessionUtil.getFirstSessionId(userData.getId()));// 第一次产生sessionId
				userData.setOnlineStatus(OnlineStatusCode.online);// 在线状态

				// 返回绑定码
				userData.setBindingCode(SessionUtil.getBindingCode(userData.getId(), userData.getLoginName()));

				// 返回用户所在的公司列表并置当前公司
				List<CompanyData> companyDatas = userService.getCompanyDatas(userData.getId());
				
				if (!companyDatas.isEmpty())
					userData.setCurrCompanyData(companyDatas.get(0));
				
				for(CompanyData companyData : companyDatas){
					if (companyData.getId().equals(existCompId)){
						userData.setCurrCompanyData(companyData);
					}
				}
				
				userData.setCompanyDatas(companyDatas);

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
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	/** 登出 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logoutAction(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");

			UserData userData = userService.getById(ou.getId());
			userData.setSimCardNo(null);
			userService.update(userData);

			recordLoginService.recordLogout(ou, LoginSourceCode.mobile);

			onlineUserRecorder.removeOnlineUser(ou, LoginSourceCode.mobile);
			SessionManager.removeSession(ou.getId());

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功登出");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/getregister", method = RequestMethod.GET)
	public void getregister(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功");
			map.put(JsonResponser.CONTENT, pCAreaService.getAllProvince());
			map.put(JsonResponser.CONTENTMD5, contentMD5);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/getCitys", method = { RequestMethod.GET })
	public void getCitys(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String proCode = ServletRequestUtils.getStringParameter(request, "proCode", "");
			List<YydCity> citys = pCAreaService.getCitysByProCode(proCode);

			map.put(JsonResponser.CONTENT, citys);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "操作成功！");

			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	@RequestMapping(value = "/getAreas", method = { RequestMethod.GET })
	public void getAreas(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String cityCode = ServletRequestUtils.getStringParameter(request, "cityCode", "");
			List<YydArea> areas = pCAreaService.getAreasByCityCode(cityCode);

			map.put(JsonResponser.CONTENT, areas);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "操作成功！");

			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	/**
	 * 注册
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void registerAction(UserData userData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
			String captcha = ServletRequestUtils.getStringParameter(request, "captcha", "");
			captcha = captcha.toLowerCase();

			String vaildCaptcha = redisCaptchaRecorder.getCaptcha(uuid);

			ResultType result = ResultType.FAILURE;
			if (!StringUtil.isEmpty(captcha) && captcha.equals(vaildCaptcha)) {
				result = userService.register(userData);
			} else {
				result = ResultType.CAPTCHA_ERROR;
			}

			if (result == ResultType.SUCCESS) {
				redisCaptchaRecorder.removeCaptcha(uuid);

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
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public void info(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");

			UserData userData = userService.getById(ou.getId());

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, userData);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/qcodeLogin", method = RequestMethod.POST)
	public void qcodeLogin(String qcode, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Jedis jedis = null;
		try {
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");

			if (!qcode.equals("")) {
				jedis = redisPool.getResource();
				if (jedis.hexists(MessageConstants.QCODE_QUEUE, qcode)) {
					jedis.hset(MessageConstants.QCODE_QUEUE, qcode, ou.getId().toString());
				}
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "获取信息成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "扫描失败，请刷新二维码后重新扫描");
			}

		} catch (JedisConnectionException jedisException) {
			// 释放redis对象
			redisPool.returnBrokenResource(jedis);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		} finally {
			if (jedis != null)
				redisPool.returnResource(jedis);
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	/** 发送找回密码邮件 */
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public void email(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String email = ServletRequestUtils.getStringParameter(request, "email");

			// 校验该用户输入的邮箱是否正确
			UserData userData = userService.getByEmail(email);
			if (userData != null) {
				String currentTime = CalendarUtil.toYYYY_MM_DD_HH_MM_SS(Calendar.getInstance());
				String currentTime1 = CalendarUtil.toYYYY_MM_DD(Calendar.getInstance());
				String userId = Long.toString(userData.getId());
				String createTime = userData.getCreateTime();
				String loginName = userData.getLoginName();
				email = userData.getEmail();
				String checkSum = MD5
						.toMD5(currentTime + "&" + userId + "&" + createTime + "&" + loginName + "&" + email);
				String encodeCurrentTime = Base64.encode(currentTime);
				String encodeUserId = Base64.encode(userId);
				String encodeCreateTime = Base64.encode(createTime);
				String encodeLoginName = Base64.encode(loginName);
				String encodeEmail = Base64.encode(email);
				String encodeCheckSum = Base64.encode(checkSum);
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
						+ path;
				String encodeUrl = basePath + "/portal/login/changePasswd?currentTime=" + encodeCurrentTime + "&userId="
						+ encodeUserId + "&createTime=" + encodeCreateTime + "&loginName=" + encodeLoginName + "&email="
						+ encodeEmail + "&checkSum=" + encodeCheckSum;

				String beforeReplaceEmailFile = FileUtil
						.readFromFile(request.getSession().getServletContext().getRealPath("/")
								+ "/WEB-INF/static/email/emailTemplate.html");

				String afterReplaceEmailFile = beforeReplaceEmailFile
						.replaceAll("\\$\\{userTrueName\\}", userData.getTrueName())
						.replaceAll("\\$\\{url\\}", encodeUrl).replaceAll("\\$\\{commitTime\\}", currentTime)
						.replaceAll("\\$\\{userLoginName\\}", userData.getLoginName())
						.replaceAll("\\$\\{commitTime1\\}", currentTime1);
				MailUtil.sendMail("易运达找回密码邮件", afterReplaceEmailFile, email);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "重置密码邮件已经发送，请登陆邮箱进行重置！");
				CookieUtil.deleteCookie(request, response, CAPTCHA);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "邮箱错误！");
			}

			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "发送邮件出现异常,请重新发送！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

}
