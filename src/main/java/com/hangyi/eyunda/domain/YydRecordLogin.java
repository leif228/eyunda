package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;

@Entity
@Table(name = "YydRecordLogin", indexes = { @Index(name = "idx_loginTime", columnList = "loginTime", unique = false) })
public class YydRecordLogin extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long userId = 0L; // 用户ID
	@Enumerated(EnumType.ORDINAL)
	private LoginSourceCode loginSource = LoginSourceCode.mobile; // 登录来源
	@Column(nullable = true, length = 32)
	private String sessionId = "";
	@Column(nullable = true, length = 15)
	private String ipAddress = "127.0.0.1"; // 访问客户端IP地址
	@Enumerated(EnumType.ORDINAL)
	private UserPrivilegeCode roleCode = UserPrivilegeCode.owner; // 角色
	@Column
	private Calendar loginTime = Calendar.getInstance(); // 登陆时间
	@Column
	private Calendar logoutTime = Calendar.getInstance(); // 登出时间
	@Column
	private Integer timeSpan = 0; // 使用时长

	/**
	 * 取得用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 取得登录来源
	 */
	public LoginSourceCode getLoginSource() {
		return loginSource;
	}

	/**
	 * 设置登录来源
	 */
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

	/**
	 * 取得角色
	 */
	public UserPrivilegeCode getRoleCode() {
		return roleCode;
	}

	/**
	 * 设置角色
	 */
	public void setRoleCode(UserPrivilegeCode roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * 取得登陆时间
	 */
	public Calendar getLoginTime() {
		return loginTime;
	}

	/**
	 * 设置登陆时间
	 */
	public void setLoginTime(Calendar loginTime) {
		this.loginTime = loginTime;
		this.logoutTime = loginTime;
	}

	/**
	 * 取得登出时间
	 */
	public Calendar getLogoutTime() {
		return logoutTime;
	}

	/**
	 * 设置登出时间
	 */
	public void setLogoutTime(Calendar logoutTime) {
		this.logoutTime = logoutTime;
	}

	/**
	 * 取得使用时长
	 */
	public Integer getTimeSpan() {
		return timeSpan;
	}

	/**
	 * 设置使用时长
	 */
	public void setTimeSpan(Integer timeSpan) {
		this.timeSpan = timeSpan;
	}

}
