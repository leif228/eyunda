package com.hangyi.eyunda.controller.portal.login;

import java.util.Calendar;
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

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.portal.login.UserService.ResultType;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.MD5;
import com.sun.syndication.io.impl.Base64;

@Controller
@RequestMapping("/portal/login")
public class ChangePasswordController {// 统一登录
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String CAPTCHA = "captcha";
	@Autowired
	private UserService userService;

	/** 到达重置密码的页面 */
	@RequestMapping(value = "/changePasswd", method = RequestMethod.GET)
	public ModelAndView changePasswd(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String encodeCurrentTime = ServletRequestUtils.getStringParameter(request, "currentTime","");
			String encodeUserId = ServletRequestUtils.getStringParameter(request, "userId", "");
			String encodeCreateTime = ServletRequestUtils.getStringParameter(request, "createTime","");
			String encodeLoginName = ServletRequestUtils.getStringParameter(request, "loginName", "");
			String encodeEmail = ServletRequestUtils.getStringParameter(request, "email","");
			String encodeCheckSum = ServletRequestUtils.getStringParameter(request, "checkSum", "");
			
			String decodeCurrentTime = Base64.decode(encodeCurrentTime);
			String decodeUserId= Base64.decode(encodeUserId);
			String decodeCreateTime = Base64.decode(encodeCreateTime);
			String decodeLoginName = Base64.decode(encodeLoginName);
			String decodeEmail = Base64.decode(encodeEmail);
			String decodeCheckSum = Base64.decode(encodeCheckSum);
			String vaildCheckSum=MD5.toMD5(decodeCurrentTime+"&"+decodeUserId+"&"+decodeCreateTime+"&"+decodeLoginName+"&"+decodeEmail);
			ModelAndView mav=null;
			//校验参数
			if(!vaildCheckSum.equals(decodeCheckSum)){
				 mav = new ModelAndView("/portal/portal-find-password-error");
				 mav.addObject("message", "连接错误,是否是您的邮箱填写错误,请重新生成连接!");
				 return mav;
			 }
			
			//校验是否过期
			if((CalendarUtil.parseYYYY_MM_DD_HH_MM_SS(decodeCurrentTime).getTime().getTime()+60*60*1000) < Calendar.getInstance().getTime().getTime()){
				 mav = new ModelAndView("/portal/portal-find-password-error");
				 mav.addObject("message", "您的连接已过期,请重新生成连接!");
				 return mav;
			 }
			
			mav=new ModelAndView("/portal/portal-reset-password");
			mav.addObject("decodeEmail", decodeEmail);
			mav.addObject("decodeUserId", decodeUserId);
			return mav;
	}
	
	/** 重置密码*/
	@RequestMapping(value = "/changePasswd", method = RequestMethod.POST)
	public void resetPasswd(long id, String password, String password2, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ResultType result = userService.resetPasswd(id, password, password2);
			if (result == ResultType.SUCCESS) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "重置密码成功!"); 
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "重置密码失败!");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "重置密码失败!");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

}
