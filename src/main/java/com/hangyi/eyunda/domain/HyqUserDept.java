package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HyqUserDept")
public class HyqUserDept extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long userId = 0L; // 用户ID
	@Column
	private Long compId = 0L; // 公司ID
	@Column
	private Long deptId = 0L; // 部门ID
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
	 * 取得部门ID
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * 设置部门ID
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
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
