package com.hangyi.eyunda.util.sms;

import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.MD5Util;

public class SMSSender {
	public static String requestUrl = "http://www.17int.cn/xxsmsweb/smsapi/send.json";
	public static String encoding = "UTF-8";

	public static SMSResponse send(SMSContent smsDetail) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(requestUrl);

		SMSResponse res = null;
		try {
			String jsonContent = smsDetail.getJsonString();

			httppost.addHeader("Content-type", "application/json; charset=utf-8");
			httppost.setHeader("Accept", "application/json");
			httppost.setEntity(new StringEntity(jsonContent, Charset.forName(encoding)));
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String respContent = EntityUtils.toString(entity, encoding);

					Gson gson = new Gson();
					Map<String, Object> map = gson.fromJson(respContent, new TypeToken<Map<String, Object>>() {
					}.getType());

					res = new SMSResponse(map);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	public static void main(String[] args) {
		// 行业帐号：gzhxhy 密码：abc123

		SMSContent smsDetail = new SMSContent();

		smsDetail.setAccount(Constants.SMS_ACCOUNT);
		smsDetail.setPassword(MD5Util.encrypt(Constants.SMS_PASSWORD).toUpperCase());
		smsDetail.setMobile("15800030361");
		smsDetail.setContent("【易运达】你的验证码是222222, 20分钟有效。");
		smsDetail.setExtno("");// max length for 3, by default treats as empty
		smsDetail.setRequestId(String.valueOf(System.nanoTime()));

		System.out.println(smsDetail.getJsonString());
		SMSResponse res = SMSSender.send(smsDetail);
		System.out.println(res.getJsonString());
	}

}
