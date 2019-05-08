package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;

@Entity
@Table(name = "YydStatLogin")
public class YydStatLogin extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(name = "nian_yue", nullable = false, length = 6)
	private String yearMonth = ""; // 年月
	@Enumerated(EnumType.ORDINAL)
	private UserPrivilegeCode roleCode = UserPrivilegeCode.manager; // 角色
	@Column
	private Integer loginNum = 0; // 次数
	@Column
	private Integer loginUserNum = 0; // 人数
	@Column
	private Integer timeSpan = 0; // 使用时长

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
	public Integer getLoginNum() {
		return loginNum;
	}

	/**
	 * 设置次数
	 */
	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	/**
	 * 取得人数
	 */
	public Integer getLoginUserNum() {
		return loginUserNum;
	}

	/**
	 * 设置人数
	 */
	public void setLoginUserNum(Integer loginUserNum) {
		this.loginUserNum = loginUserNum;
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
