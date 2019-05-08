package com.hangyi.eyunda.data.hyquan;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;

public class HyqRecordLoginData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = null; // ID
	private Long userId = 0L;
	private LoginSourceCode loginSource = LoginSourceCode.mobile; // 登录来源
	private String sessionId = "";
	private String userLogo = ""; // 图标图片
	private String loginName = ""; // 登录名
	private String trueName = ""; // 姓名
	private String nickName = ""; // 昵称
	private String email = ""; // 电子邮箱
	private String mobile = ""; // 手机
	private String loginTime = ""; // 登陆时间
	private String logoutTime = ""; // 登出时间
	private String timeSpan = ""; // 使用时长
	private String ipAddress = ""; // 登录客户端IP地址

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LoginSourceCode getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(LoginSourceCode loginSource) {
		this.loginSource = loginSource;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserLogo() {
		return userLogo;
	}

	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(String timeSpan) {
		this.timeSpan = timeSpan;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
