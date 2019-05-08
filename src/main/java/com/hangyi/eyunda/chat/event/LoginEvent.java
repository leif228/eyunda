package com.hangyi.eyunda.chat.event;

import java.util.Map;

public class LoginEvent extends BaseEvent {
	private static final long serialVersionUID = -1L;

	public static final String USERID = "userId";
	public static final String LOGIN_NAME = "loginName";
	public static final String TRUE_NAME = "trueName";
	public static final String NICK_NAME = "nickName";
	public static final String SIM_CARD_NO = "simCardNo"; // 手机SIM卡号
	public static final String BINDING_CODE = "bindingCode"; // 手机绑定编码
	public static final String EMAIL = "email"; // 电子邮箱
	public static final String MOBILE = "mobile"; // 手机

	public static final String IP_ADDRESS = "ipAddress"; // IP地址

	public static final String ONLINE_STATUS = "onlineStatus";// 状态

	public LoginEvent(Map<String, String> source) {
		super(source);
	}

	public void setUserId(Long userId) {
		eventMap.put(USERID, Long.toString(userId));
	}

	public Long getUserId() {
		return Long.parseLong(eventMap.get(USERID));
	}

	public void setLoginName(String loginName) {
		eventMap.put(LOGIN_NAME, loginName);
	}

	public String getLoginName() {
		return (String) eventMap.get(LOGIN_NAME);
	}

	public void setTrueName(String trueName) {
		eventMap.put(TRUE_NAME, trueName);
	}

	public String getTrueName() {
		return (String) eventMap.get(TRUE_NAME);
	}

	public void setNickName(String nickName) {
		eventMap.put(NICK_NAME, nickName);
	}

	public String getNickName() {
		return (String) eventMap.get(NICK_NAME);
	}

	public String getSimCardNo() {
		return (String) eventMap.get(SIM_CARD_NO);
	}

	public void setSimCardNo(String simCardNo) {
		eventMap.put(SIM_CARD_NO, simCardNo);
	}

	public String getBindingCode() {
		return (String) eventMap.get(BINDING_CODE);
	}

	public void setBindingCode(String bindingCode) {
		eventMap.put(BINDING_CODE, bindingCode);
	}

	public String getEmail() {
		return (String) eventMap.get(EMAIL);
	}

	public void setEmail(String email) {
		eventMap.put(EMAIL, email);
	}

	public String getMobile() {
		return (String) eventMap.get(MOBILE);
	}

	public void setMobile(String mobile) {
		eventMap.put(MOBILE, mobile);
	}

	public OnlineStatusCode getOnlineStatus() {
		return OnlineStatusCode.valueOf((String) eventMap.get(ONLINE_STATUS));
	}

	public void setOnlineStatus(OnlineStatusCode onlineStatus) {
		eventMap.put(ONLINE_STATUS, onlineStatus.toString());
	}

	public String getIpAddress() {
		return (String) eventMap.get(IP_ADDRESS);
	}

	public void setIpAddress(String ipAddress) {
		eventMap.put(IP_ADDRESS, ipAddress);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
