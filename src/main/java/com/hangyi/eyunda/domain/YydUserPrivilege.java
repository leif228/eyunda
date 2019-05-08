package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;

@Entity
@Table(name = "YydUserPrivilege")
public class YydUserPrivilege extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long userId = 0L; // 用户ID
	@Column
	private Long compId = 0L; // 公司ID
	@Enumerated(EnumType.ORDINAL)
	private UserPrivilegeCode privilege = null; // 用户特权
	@Column(nullable = true, length = 200)
	private String mmsis = ""; // 上报船舶
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间

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
	 * 取得公司ID
	 */
	public Long getCompId() {
		return compId;
	}

	/**
	 * 设置公司ID
	 */
	public void setCompId(Long compId) {
		this.compId = compId;
	}

	/**
	 * 取得用户特权
	 */
	public UserPrivilegeCode getPrivilege() {
		return privilege;
	}

	/**
	 * 设置用户特权
	 */
	public void setPrivilege(UserPrivilegeCode privilege) {
		this.privilege = privilege;
	}

	/**
	 * 取得上报船舶
	 */
	public String getMmsis() {
		return mmsis;
	}

	/**
	 * 设置上报船舶
	 */
	public void setMmsis(String mmsis) {
		this.mmsis = mmsis;
	}

	/**
	 * 取得建立时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置建立时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

}
