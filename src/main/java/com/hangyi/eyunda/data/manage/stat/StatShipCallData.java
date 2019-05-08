package com.hangyi.eyunda.data.manage.stat;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;

public class StatShipCallData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String yearMonth = ""; // 年月
	private UserPrivilegeCode role = null; // 角色
	private String roleCode = ""; // 角色
	private String roleDesc = ""; // 角色描述
	private Integer callNum = 0; // 次数
	private Integer calledUserNum = 0; // 人数
	private Integer calledShipNum = 0; // 船舶数
	private int ym = 0;

	public int getYm() {
		return ym;
	}

	public void setYm(int ym) {
		this.ym = ym;
	}

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

	public Integer getCallNum() {
		return callNum;
	}

	public void setCallNum(Integer callNum) {
		this.callNum = callNum;
	}

	public Integer getCalledUserNum() {
		return calledUserNum;
	}

	public void setCalledUserNum(Integer calledUserNum) {
		this.calledUserNum = calledUserNum;
	}

	public Integer getCalledShipNum() {
		return calledShipNum;
	}

	public void setCalledShipNum(Integer calledShipNum) {
		this.calledShipNum = calledShipNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
