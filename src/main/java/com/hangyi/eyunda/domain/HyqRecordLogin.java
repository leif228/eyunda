package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;

@Entity
@Table(name = "HyqRecordLogin", indexes = { @Index(name = "idx_loginTime", columnList = "loginTime", unique = false) })
public class HyqRecordLogin extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long userId = 0L; // 用户ID
	@Enumerated(EnumType.ORDINAL)
	private LoginSourceCode loginSource = LoginSourceCode.mobile; // 登录来源
	@Column(nullable = true, length = 32)
	private String sessionId = "";
	@Column(nullable = true, length = 23)
	private String ipAddress = "127.0.0.1"; // 访问客户端IP地址
	@Column
	private Calendar loginTime = Calendar.getInstance(); // 登陆时间
	@Column
	private Calendar logoutTime = Calendar.getInstance(); // 登出时间
	@Column
	private Integer timeSpan = 0; // 使用时长

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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Calendar getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Calendar loginTime) {
		this.loginTime = loginTime;
	}

	public Calendar getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Calendar logoutTime) {
		this.logoutTime = logoutTime;
	}

	public Integer getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(Integer timeSpan) {
		this.timeSpan = timeSpan;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
