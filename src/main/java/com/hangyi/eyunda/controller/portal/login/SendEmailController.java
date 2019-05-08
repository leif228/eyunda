package com.hangyi.eyunda.controller.portal.login;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.service.portal.login.MailUtil;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CookieUtil;
import com.hangyi.eyunda.util.FileUtil;
import com.hangyi.eyunda.util.MD5;
import com.hangyi.eyunda.util.OSUtil;
import com.sun.syndication.io.impl.Base64;

@Controller
@RequestMapping("/portal/login")
public class SendEmailController {// 统一登录
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String CAPTCHA = "captcha";
	@Autowired
	private UserService userService;

	/** 发送找回密码邮件 */
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public void sendEmail(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String email = ServletRequestUtils.getStringParameter(request, "email", "");
			String captcha = ServletRequestUtils.getStringParameter(request, "captcha", "");
			String _csrf = ServletRequestUtils.getStringParameter(request, "_csrf", "");
			captcha = captcha.toLowerCase();

			String vaildCaptcha = CookieUtil.getCookieValue(request, CAPTCHA);
			String oldCrsf = (String) httpSession.getAttribute("_csrf");
			if(!_csrf.equalsIgnoreCase(oldCrsf)){
				response.setContentType("text/html;charset=utf-8");
	            response.setStatus(403);
	 
	            //页面友好提示信息
	            OutputStream oStream;
				try {
					oStream = response.getOutputStream();
					oStream.write("请不要重复提交请求，返回原始页面刷新后再次尝试！！！".getBytes("UTF-8"));
				} catch (IOException e) {
					e.printStackTrace();
				}
	            return;
			}else{
				httpSession.removeAttribute("_csrf");
			}
			// 校验验证码是否正确以及该用户输入的邮箱是否正确
			if (StringUtil.isNotBlank(captcha) && captcha.equals(vaildCaptcha)) {
				if (userService.getByEmail(email) != null) {
					UserData userData = userService.getByEmail(email);
					String currentTime = CalendarUtil.toYYYY_MM_DD_HH_MM_SS(Calendar.getInstance());
					String currentTime1 = CalendarUtil.toYYYY_MM_DD(Calendar.getInstance());
					String userId = Long.toString(userData.getId());
					String createTime = userData.getCreateTime();
					String loginName = userData.getLoginName();
					email = userData.getEmail();
					String checkSum = MD5.toMD5(currentTime + "&" + userId + "&" + createTime + "&" + loginName + "&" + email);
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
					String beforeReplaceEmailFile = "";
					if (OSUtil.isWindows()) {
						beforeReplaceEmailFile = FileUtil.readFromFile(request.getSession().getServletContext().getRealPath("/")
								+ "\\WEB-INF\\static\\email\\emailTemplate.html");
					} else {
						beforeReplaceEmailFile = FileUtil.readFromFile(request.getSession().getServletContext().getRealPath("/")
								+ "/WEB-INF/static/email/emailTemplate.html");
					}
					String afterReplaceEmailFile = beforeReplaceEmailFile
							.replaceAll("\\$\\{userTrueName\\}", userData.getTrueName()).replaceAll("\\$\\{url\\}", encodeUrl)
							.replaceAll("\\$\\{commitTime\\}", currentTime)
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
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "验证码异常！");
			}

			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "发送邮件异常,请重新发送！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

}
