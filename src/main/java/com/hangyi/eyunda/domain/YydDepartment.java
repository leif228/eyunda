package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;

@Entity
@Table(name = "YydDepartment", indexes = { @Index(name = "idx_compId", columnList = "compId", unique = false) })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydDepartment extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long compId = 0L; // 公司ID
	@Column(nullable = false, length = 20)
	private String deptName = ""; // 部门名称
	@Enumerated(EnumType.ORDINAL)
	private UserPrivilegeCode deptType = null; // 部门类型
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间

	public Long getCompId() {
		return compId;
	}

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
	 * 取得部门类型
	 */
	public UserPrivilegeCode getDeptType() {
		return deptType;
	}

	/**
	 * 设置部门类型
	 */
	public void setDeptType(UserPrivilegeCode deptType) {
		this.deptType = deptType;
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
