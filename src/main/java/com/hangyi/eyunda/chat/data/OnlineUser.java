package com.hangyi.eyunda.chat.data;

import java.util.Date;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangyi.eyunda.chat.event.OnlineStatusCode;
import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.util.Constants;

public class OnlineUser extends BaseData {
	private static final long serialVersionUID = 2052205826281663792L;

	// ID
	private Long id;
	// 登录名
	private String loginName;
	// 姓名
	private String trueName;
	// 昵称
	private String nickName;
	// simNo
	private String simNo;

	// 登录服务器标识
	private String loginServerName;
	// mina sessionId
	private String sessionId;
	// dwr sessionId
	private String dwrSessionId;
	// 报到时间
	private Long ontime;
	// 进入时间
	private Long enterTime;

	private OnlineStatusCode onlineStatus = OnlineStatusCode.online;

	public OnlineUser() {
	}

	public OnlineUser(String jsonStr) {
		try {
			Gson gson = new Gson();

			Map<String, Object> map = gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
			}.getType());

			this.id = ((Double) map.get("id")).longValue();
			this.loginName = (String) map.get("loginName");
			this.trueName = (String) map.get("trueName");
			this.nickName = (String) map.get("nickName");
			this.simNo = (String) map.get("simNo");
			this.loginServerName = (String) map.get("loginServerName");
			this.sessionId = (String) map.get("sessionId");
			this.dwrSessionId = (String) map.get("dwrSessionId");
			this.ontime = ((Double) map.get("ontime")).longValue();
			this.enterTime = ((Double) map.get("enterTime")).longValue();
			this.onlineStatus = OnlineStatusCode.valueOf(((String) map.get("onlineStatus")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isPresence() {
		Long currtime = new Date().getTime();
		Long leavetime = currtime - this.ontime;
		if (leavetime / 1000 / 60 < Constants.OFFLINE_TIME)
			return true;
		else
			return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTrueName() {
		if("".equals(this.trueName) || (null == this.trueName)){
			trueName = this.loginName;
		}
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLoginServerName() {
		return loginServerName;
	}

	public void setLoginServerName(String loginServerName) {
		this.loginServerName = loginServerName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getDwrSessionId() {
		return dwrSessionId;
	}

	public void setDwrSessionId(String dwrSessionId) {
		this.dwrSessionId = dwrSessionId;
	}

	public void setOntime(Long ontime) {
		this.ontime = ontime;
	}

	public void setEnterTime(Long enterTime) {
		this.enterTime = enterTime;
	}

	public long getOntime() {
		return ontime;
	}

	public void setOntime(long ontime) {
		this.ontime = ontime;
	}

	public long getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(long enterTime) {
		this.enterTime = enterTime;
	}

	public OnlineStatusCode getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(OnlineStatusCode onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
