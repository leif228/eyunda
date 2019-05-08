package com.hangyi.eyunda.weixing.util;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.hangyi.eyunda.weixing.pojo.AccessToken;
import com.hangyi.eyunda.weixing.pojo.Button;
import com.hangyi.eyunda.weixing.pojo.CommonButton;
import com.hangyi.eyunda.weixing.pojo.ComplexButton;
import com.hangyi.eyunda.weixing.pojo.Menu;
import com.hangyi.eyunda.weixing.pojo.ViewButton;
import com.hangyi.eyunda.util.Base64;
import com.hangyi.eyunda.util.Constants;
/**
 * 菜单管理器类
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	// 第三方用户唯一凭证
	static String appId = Constants.APPID;
	// 第三方用户唯一凭证密钥
	static String appSecret = Constants.APPSECRET;
	static String siteBase = "http://www.eyd98.com";

	public static void main(String[] args) {
		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			String m = new Gson().toJson(getMenu());
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());

			// 判断菜单创建结果
			if (0 == result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		String redirect = "";
		String btn31Callback="";
		String btn32Callback="";
		try {
			redirect = URLEncoder.encode(siteBase + "/scfreight/wx/user/bind","UTF-8");
			btn31Callback = "cargo";
			btn32Callback = "ship";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ViewButton btn11 = new ViewButton();
		btn11.setName("船舶管理");
		btn11.setType("view");  
		//btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+redirect+"&response_type=code&scope=snsapi_base&state="+btn11Callback+"#wechat_redirect ");
		btn11.setUrl(siteBase + "/wx/shipgl.html");

		ViewButton btn12 = new ViewButton();
		btn12.setName("船舶动态");
		btn12.setType("view");  
		btn12.setUrl(siteBase + "/wx/shipdt.html");

		ViewButton btn13 = new ViewButton();
		btn13.setName("船舶监控");
		btn13.setType("view");  
		btn13.setUrl(siteBase + "/wx/shipjk.html");

		ViewButton btn14 = new ViewButton();
		btn14.setName("货物发布");
		btn14.setType("view");  
		btn14.setUrl("http://www.eyd98.com/html/ship.html");
		
		ViewButton btn15 = new ViewButton();
		btn15.setName("合同签定");
		btn15.setType("view");  
		btn15.setUrl(siteBase + "/wx/order.html");

		ViewButton btn31 = new ViewButton();
		btn31.setName("我要找船");
		btn31.setType("view");  
		btn31.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+redirect+"&response_type=code&scope=snsapi_base&state="+btn31Callback+"#wechat_redirect");

		ViewButton btn32 = new ViewButton();
		btn32.setName("我要找货");
		btn32.setType("view");  
		//btn32.setUrl("http://www.eyd98.com/html/ship.html");
		btn32.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+redirect+"&response_type=code&scope=snsapi_base&state="+btn32Callback+"#wechat_redirect");
		
		ViewButton btn33 = new ViewButton();
		btn33.setName("APP下载");
		btn33.setType("view");  
		btn33.setUrl(siteBase + "/wx/appdown");

		ViewButton btn21 = new ViewButton();
		btn21.setName("个人设置");
		btn21.setType("view");  
		btn21.setUrl(siteBase + "/wx/sz.html");

		CommonButton btn22 = new CommonButton();
		btn22.setName("动态角色");
		btn22.setType("click");
		btn22.setKey("22");

		ViewButton btn23 = new ViewButton();
		btn23.setName("聊天方式");
		btn23.setType("view");
		btn23.setUrl(siteBase + "/wx/lt.html");
		
		ViewButton btn24 = new ViewButton();  
		btn24.setName("手机端说明书");  
		btn24.setType("view");  
		btn24.setUrl(siteBase + "/wx/appdoc_mobile");

		ViewButton btn25 = new ViewButton();  
		btn25.setName("网页端说明书");  
		btn25.setType("view");  
		btn25.setUrl(siteBase + "/wx/appdoc_web");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("功能介绍");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14, btn15 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("我要");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("帮助");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25  });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}