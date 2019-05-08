package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HyqDepartment")
public class HyqDepartment extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long compId = 0L; // 公司ID
	@Column(nullable = false, length = 20)
	private String deptName = ""; // 部门名称
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间

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
	 * 取得部门名称
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * 设置部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
