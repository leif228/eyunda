package com.hangyi.eyunda.controller.portal.login;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.YydArea;
import com.hangyi.eyunda.domain.YydCity;
import com.hangyi.eyunda.service.EnumConst.SpaceMenuCode;
import com.hangyi.eyunda.service.PCAreaService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.portal.login.UserService.ResultType;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.util.CookieUtil;
import com.hangyi.eyunda.util.FileUtil;
import com.hangyi.eyunda.util.MatrixToImageWriter;
import com.hangyi.eyunda.util.StringUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Controller
@RequestMapping("/portal/login")
public class LoginController {// 统一登录
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String USER_COOKIE = "uck";
	public static final String CAPTCHA = "captcha";
	public static final String CURR_COMP_ID = "curr_comp_id";
	public static final int PRE_TWENTY = 20;

	@Autowired
	private UserService userService;
	@Autowired
	MyShipService myShipService;
	@Autowired
	private PCAreaService pCAreaService;
	@Autowired
	private JedisPool redisPool;

	/** 登录界面 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginView(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		String loginName = ServletRequestUtils.getStringParameter(request, "loginName", "");

		ModelAndView mv = new ModelAndView("/portal/portal-login");
		String uid = StringUtil.getRandomString(32);
		Jedis jedis = null;
		try {
			jedis = redisPool.getResource();
			jedis.hset(MessageConstants.QCODE_QUEUE, uid, "");

		} catch (JedisConnectionException jedisEx) {
			redisPool.returnBrokenResource(jedis);
		} finally {
			if (jedis != null)
				redisPool.returnResource(jedis);
		}
		String _csrf = StringUtil.getRandomString(12);
		httpSession.setAttribute("_csrf", _csrf);
		mv.addObject("_csrf", _csrf);
		mv.addObject("uid", uid);
		mv.addObject("loginName", loginName);
		return mv;
	}

	@RequestMapping(value = "/protocol", method = RequestMethod.GET)
	public ModelAndView protocol(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/portal/register-protocol");
		String urlPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "WEB-INF"
				+ File.separator + "static" + File.separator + "protocol" + File.separator + "eyunda-protocol.txt";
		File file = new File(urlPath);
		String htmlContent = "";
		if (file.exists()) {
			String content = FileUtil.readFromFile(file);
			htmlContent = StringUtil.formatHTML(content);
		}
		mv.addObject("content", htmlContent);
		return mv;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginAction(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession)
			throws Exception {
		ResultType result = ResultType.FAILURE;

		String loginName = ServletRequestUtils.getStringParameter(request, "loginName");
		String passwordRev = ServletRequestUtils.getStringParameter(request, "password");

		StringBuffer stringBuffer = new StringBuffer(passwordRev);
		String password = stringBuffer.reverse().toString();
		String captcha = ServletRequestUtils.getStringParameter(request, "captcha", "");
		captcha = captcha.toLowerCase();
		int longDay = ServletRequestUtils.getIntParameter(request, "longDay", -1);

		String oldCrsf = (String) httpSession.getAttribute("_csrf");
		String crsf = ServletRequestUtils.getStringParameter(request, "_csrf");
		if (!crsf.equalsIgnoreCase(oldCrsf)) {
			response.setContentType("text/html;charset=utf-8");
			response.setStatus(403);

			// 页面友好提示信息
			OutputStream oStream = response.getOutputStream();
			oStream.write("请不要重复提交请求，返回原始页面刷新后再次尝试！！！".getBytes("UTF-8"));

			ModelAndView mv = new ModelAndView("error.jsp");
			return mv;
		}

		String vaildCaptcha = CookieUtil.getCookieValue(request, CAPTCHA);

		if (!StringUtil.isEmpty(captcha) && captcha.equals(vaildCaptcha)) {
			result = userService.login(loginName, password);
		} else {
			result = ResultType.CAPTCHA_ERROR;
		}

		if (result == ResultType.SUCCESS) {
			// 写Cookie
			UserData userData = userService.getByLoginName(loginName);
			String userId = Long.toString(userData.getId());
			String loginToken = userService.getLoginToken(userData);
			String uck = userId + "," + loginToken;
			int maxAge = longDay * 24 * 60 * 60;

			CookieUtil.setCookie(request, response, USER_COOKIE, uck, maxAge);

			List<CompanyData> companyDatas = userService.getCompanyDatas(userData.getId());
			if (!companyDatas.isEmpty())
				CookieUtil.setCookie(request, response, CURR_COMP_ID, companyDatas.get(0).getId().toString(), maxAge);
			else
				CookieUtil.setCookie(request, response, CURR_COMP_ID, "", maxAge);

			CookieUtil.deleteCookie(request, response, CAPTCHA);

			httpSession.removeAttribute("_csrf");

			ModelAndView mv = new ModelAndView("redirect:/index.jsp");
			return mv;
		} else {
			throw new Exception(result.getDescription());
		}
	}

	/** 登出 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logoutAction(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("redirect:/index.jsp");
		try {
			CookieUtil.deleteCookie(request, response, USER_COOKIE);
			CookieUtil.deleteCookie(request, response, CURR_COMP_ID);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return mv;

	}

	@RequestMapping(value = "/getCitys", method = { RequestMethod.GET })
	public void getCitys(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String proCode = ServletRequestUtils.getStringParameter(request, "proCode", "");
			List<YydCity> citys = pCAreaService.getCitysByProCode(proCode);

			map.put("citys", citys);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "操作成功！");

			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithText(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作失败！");
			JsonResponser.respondWithText(response, map);
		}
		return;
	}

	@RequestMapping(value = "/getAreas", method = { RequestMethod.GET })
	public void getAreas(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String cityCode = ServletRequestUtils.getStringParameter(request, "cityCode", "");
			List<YydArea> areas = pCAreaService.getAreasByCityCode(cityCode);

			map.put("areas", areas);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "操作成功！");

			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithText(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作失败！");
			JsonResponser.respondWithText(response, map);
		}
		return;
	}

	/** 注册界面 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerView(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ModelAndView mv = new ModelAndView("/portal/portal-register");
		try {

			mv.addObject("allProvince", pCAreaService.getAllProvince());

		} catch (Exception e) {
			e.printStackTrace();
		}
		String _csrf = StringUtil.getRandomString(12);
		httpSession.setAttribute("_csrf", _csrf);
		mv.addObject("_csrf", _csrf);
		return mv;
	}

	/**
	 * 注册表单的提交(备用)
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void registerAction(UserData userData, String _csrf, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		logger.info("服务端正在注册......");
		Map<String, Object> map = new HashMap<String, Object>();

		String oldCrsf = (String) httpSession.getAttribute("_csrf");
		if (!_csrf.equalsIgnoreCase(oldCrsf)) {
			response.setContentType("text/html;charset=utf-8");
			response.setStatus(403);

			// 页面友好提示信息
			OutputStream oStream;
			try {
				oStream = response.getOutputStream();
				oStream.write("请不要重复提交请求，返回原始页面刷新后再次尝试！！！".getBytes("UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			return;
		} else {
			httpSession.removeAttribute("_csrf");
		}

		try {
			String captcha = ServletRequestUtils.getStringParameter(request, "captcha", "").trim();
			captcha = captcha.toLowerCase();
			String vaildCaptcha = CookieUtil.getCookieValue(request, CAPTCHA);

			ResultType result = ResultType.FAILURE;
			if (!StringUtil.isEmpty(captcha) && captcha.equals(vaildCaptcha)) {
				result = userService.register(userData);
			} else {
				result = ResultType.CAPTCHA_ERROR;
			}

			if (result == ResultType.SUCCESS) {
				CookieUtil.deleteCookie(request, response, CAPTCHA);

				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, userData.getLoginName());
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public ModelAndView info(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/space/space-info");

		String uck = CookieUtil.getCookieValue(request, USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		if (userData == null)
			mav.setViewName("redirect:/portal/login/login");

		// 用户信息
		mav.addObject("userData", userData);

		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_SHIP);

		return mav;
	}

	@RequestMapping(value = "/verifyQcodeLogin", method = RequestMethod.GET)
	public void verifyQcodeLogin(String uid, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = userService.getUserFromRedis(uid);
			if (userData != null) {
				// 处理用户自动登录？
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
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/getQcode", method = RequestMethod.GET)
	public void getQcode(String uid, HttpServletRequest request, HttpServletResponse response) {
		Jedis jedis = null;
		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

			jedis = redisPool.getResource();
			jedis.hset(MessageConstants.QCODE_QUEUE, uid, "");
			Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode("LOGIN|" + uid, BarcodeFormat.QR_CODE, 800, 800, hints);
			MatrixToImageWriter.writeToStream(bitMatrix, "jpg", response.getOutputStream());

		} catch (JedisConnectionException jedisException) {
			redisPool.returnBrokenResource(jedis);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// 返还到连接池
			if (jedis != null) {
				redisPool.returnResource(jedis);
			}
		}
	}

	@RequestMapping(value = "/createQcodeString", method = RequestMethod.GET)
	public void createQcodeString(String uid, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Jedis jedis = null;
		try {
			jedis = redisPool.getResource();
			if (!uid.equals("")) {
				jedis.hdel(MessageConstants.QCODE_QUEUE, uid);
			}
			String newUid = StringUtil.getRandomString(32);
			jedis.hset(MessageConstants.QCODE_QUEUE, newUid, "");
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "二维码生成成功");
			map.put("code", newUid);
		} catch (JedisConnectionException jedisException) {
			// 释放redis对象
			redisPool.returnBrokenResource(jedis);
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, jedisException.getMessage());
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		} finally {
			// 返还到连接池
			if (jedis != null) {
				redisPool.returnResource(jedis);
			}
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	/** 邀请注册界面 */
	@RequestMapping(value = "/inviteRegister", method = RequestMethod.GET)
	public ModelAndView inviteRegisterView(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/portal/portal-register-invite");
		try {
			Long inviteUid = ServletRequestUtils.getLongParameter(request, "u", 0L);
			mv.addObject("bid", inviteUid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 邀请注册实现，邀请注册后还需要添加到企业
	 */
	@RequestMapping(value = "/inviteRegister", method = RequestMethod.POST)
	public void inviteRegisterAction(UserData userData, HttpServletRequest request, HttpServletResponse response) {
		logger.info("服务端正在注册......");
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String captcha = ServletRequestUtils.getStringParameter(request, "captcha", "").trim();

			captcha = captcha.toLowerCase();
			String vaildCaptcha = CookieUtil.getCookieValue(request, CAPTCHA);

			ResultType result = ResultType.FAILURE;
			if (!StringUtil.isEmpty(captcha) && captcha.equals(vaildCaptcha)) {
				result = userService.register(userData);
			} else {
				result = ResultType.CAPTCHA_ERROR;
			}

			if (result == ResultType.SUCCESS) {
				CookieUtil.deleteCookie(request, response, CAPTCHA);

				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, userData.getLoginName());
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
	}

	/** 邀请注册成功后下载界面 */
	@RequestMapping(value = "/inviteRegisterDownload", method = RequestMethod.GET)
	public ModelAndView inviteRegisterDownload(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/portal/portal-register-download");
		try {
			String loginName = ServletRequestUtils.getStringParameter(request, "loginName", "");
			UserData u = userService.getByLoginName(loginName);

			mv.addObject("u", u);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/autoLogin", method = RequestMethod.GET)
	public ModelAndView autoLoginAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String code = ServletRequestUtils.getStringParameter(request, "code", "");
		if (code.equals("")) {
			throw new Exception("未授权操作");
		}
		UserData userData = userService.getUserFromRedis(code);
		if (userData != null) {
			// 写Cookie
			String userId = Long.toString(userData.getId());
			String loginToken = userService.getLoginToken(userData);
			String uck = userId + "," + loginToken;
			int maxAge = 1 * 24 * 60 * 60;

			CookieUtil.setCookie(request, response, USER_COOKIE, uck, maxAge);

			CookieUtil.deleteCookie(request, response, CAPTCHA);
			Jedis jedis = null;
			try {
				jedis = redisPool.getResource();
				jedis.hdel(MessageConstants.QCODE_QUEUE, code);
			} catch (JedisConnectionException jedisException) {
				// 释放redis对象
				redisPool.returnBrokenResource(jedis);
				throw jedisException;
			} catch (Exception e) {
				throw e;
			} finally {
				// 返还到连接池
				if (jedis != null) {
					redisPool.returnResource(jedis);
				}
			}

			ModelAndView mv = new ModelAndView("redirect:/index.jsp");
			return mv;
		} else {
			throw new Exception("未授权操作");
		}
	}

}
