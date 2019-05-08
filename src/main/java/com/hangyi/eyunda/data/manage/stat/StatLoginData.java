package com.hangyi.eyunda.data.manage.stat;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;

public class StatLoginData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String yearMonth = null; // 年月
	private Integer loginNum = 0; // 次数
	private Integer loginUserNum = 0; // 人数
	private Integer timeSpan = 0; // 使用时长
	private String timeSpanDesc = ""; // 使用时长描述
	private UserPrivilegeCode role = null; // 角色
	private String roleCode = ""; // 角色码
	private String roleDesc = ""; // 角色描述

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public UserPrivilegeCode getRole() {
		return role;
	}

	public void setRole(UserPrivilegeCode role) {
		this.role = role;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public Integer getLoginUserNum() {
		return loginUserNum;
	}

	public void setLoginUserNum(Integer loginUserNum) {
		this.loginUserNum = loginUserNum;
	}

	public Integer getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(Integer timeSpan) {
		this.timeSpan = timeSpan;
	}

	public String getTimeSpanDesc() {
		return timeSpanDesc;
	}

	public void setTimeSpanDesc(String timeSpanDesc) {
		this.timeSpanDesc = timeSpanDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
