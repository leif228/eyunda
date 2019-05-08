package com.hangyi.eyunda.weixing.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.weixing.pojo.AccessToken;
import com.hangyi.eyunda.weixing.util.WeixinUtil;

@Service
public class AccessTokenService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static AccessToken accessToken = null;

	public static synchronized AccessToken getAccessToken() {
		return accessToken;
	}

	private static synchronized void setAccessToken(AccessToken accessToken) {
		AccessTokenService.accessToken = accessToken;
	}

	protected void doAccessToken() {
		AccessToken accessToken = WeixinUtil.getAccessToken(Constants.APPID,Constants.APPSECRET);
		
		if (accessToken != null) {
			setAccessToken(accessToken);
		} else {
			do {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
				
				accessToken = WeixinUtil.getAccessToken(Constants.APPID,Constants.APPSECRET);
			} while (accessToken == null);
			setAccessToken(accessToken);
		}
		logger.info("微信accessToken: " + accessToken.getToken());
	}
}
