package com.hangyi.eyunda.controller.portal.login;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.patchca.color.ColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.service.Captcha;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.word.RandomWordFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.util.CookieUtil;

import jodd.util.StringUtil;

@Controller
public class CaptchaController {
	private static ConfigurableCaptchaService cs = null;
	private static ColorFactory cf = null;
	private static RandomWordFactory wf = null;
	private static Random r = new Random();
	private static CurvesRippleFilterFactory crff = null;
	private static MarbleRippleFilterFactory mrff = null;
	private static DoubleRippleFilterFactory drff = null;
	private static WobbleRippleFilterFactory wrff = null;
	private static DiffuseRippleFilterFactory dirff = null;

	@RequestMapping(value = "/captcha")
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		init();
		response.setContentType("image/png");
		response.setHeader("cache", "no-cache");
		wf.setMaxLength(5);
		wf.setMinLength(4);
		OutputStream os = response.getOutputStream();

		switch (r.nextInt(5)) {
		case 0:
			cs.setFilterFactory(crff);
			break;
		case 1:
			cs.setFilterFactory(mrff);
			break;
		case 2:
			cs.setFilterFactory(drff);
			break;
		case 3:
			cs.setFilterFactory(wrff);
			break;
		case 4:
			cs.setFilterFactory(dirff);
			break;
		}

		Captcha captcha = cs.getCaptcha();
		String cryptValue = captcha.getChallenge();

		CookieUtil.setCookie(request, response, LoginController.CAPTCHA, cryptValue, -1);

		ImageIO.write(captcha.getImage(), "gif", os);

		os.flush();
		os.close();
	}

	@RequestMapping(value = "/check", method = { RequestMethod.GET })
	public void check(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String captcha = ServletRequestUtils.getStringParameter(request, "captcha", "");
			captcha = captcha.toLowerCase();
			String vaildCaptcha = CookieUtil.getCookieValue(request, LoginController.CAPTCHA);

			if (StringUtil.isNotBlank(captcha) && captcha.equals(vaildCaptcha)) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	/**
	 * 初始化验证码
	 */
	private void init() {
		cs = new ConfigurableCaptchaService();
		cf = new SingleColorFactory(new Color(25, 60, 170));
		wf = new RandomWordFactory();
		crff = new CurvesRippleFilterFactory(cs.getColorFactory());
		drff = new DoubleRippleFilterFactory();
		wrff = new WobbleRippleFilterFactory();
		dirff = new DiffuseRippleFilterFactory();
		mrff = new MarbleRippleFilterFactory();
		cs.setWordFactory(wf);
		cs.setColorFactory(cf);
		cs.setWidth(120);
		cs.setHeight(50);
	}
}
