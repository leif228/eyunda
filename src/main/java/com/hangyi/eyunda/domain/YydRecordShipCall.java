package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;

@Entity
@Table(name = "YydRecordShipCall", indexes = { @Index(name = "idx_recordTime", columnList = "recordTime", unique = false) })
public class YydRecordShipCall extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long userId = 0L; // 用户
	@Column
	private Long shipId = 0L; // 船舶ID
	@Enumerated(EnumType.ORDINAL)
	private UserPrivilegeCode roleCode = UserPrivilegeCode.owner; // 角色
	@Column
	private Calendar recordTime = Calendar.getInstance(); // 访问时间

	/**
	 * 取得用户
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置用户
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 取得船舶ID
	 */
	public Long getShipId() {
		return shipId;
	}

	/**
	 * 设置船舶ID
	 */
	public void setShipId(Long shipId) {
		this.shipId = shipId;
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
	 * 取得访问时间
	 */
	public Calendar getRecordTime() {
		return recordTime;
	}

	/**
	 * 设置访问时间
	 */
	public void setRecordTime(Calendar recordTime) {
		this.recordTime = recordTime;
	}

}
