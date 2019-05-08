package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;

@Entity
@Table(name = "YydStatShipCall")
public class YydStatShipCall extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(name = "nian_yue", nullable = false, length = 7)
	private String yearMonth = ""; // 年月
	@Enumerated(EnumType.ORDINAL)
	private UserPrivilegeCode roleCode = UserPrivilegeCode.manager; // 角色
	@Column
	private Integer callNum = 0; // 次数
	@Column
	private Integer calledUserNum = 0; // 人数
	@Column
	private Integer calledShipNum = 0; // 车船数

	/**
	 * 取得年月
	 */
	public String getYearMonth() {
		return yearMonth;
	}

	/**
	 * 设置年月
	 */
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
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
	 * 取得次数
	 */
	public Integer getCallNum() {
		return callNum;
	}

	/**
	 * 设置次数
	 */
	public void setCallNum(Integer callNum) {
		this.callNum = callNum;
	}

	/**
	 * 取得人数
	 */
	public Integer getCalledUserNum() {
		return calledUserNum;
	}

	/**
	 * 设置人数
	 */
	public void setCalledUserNum(Integer calledUserNum) {
		this.calledUserNum = calledUserNum;
	}

	/**
	 * 取得车船数
	 */
	public Integer getCalledShipNum() {
		return calledShipNum;
	}

	/**
	 * 设置车船数
	 */
	public void setCalledShipNum(Integer calledShipNum) {
		this.calledShipNum = calledShipNum;
	}

}
