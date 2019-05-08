package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

@Entity
@Table(name = "HyqSoftSys")
public class HyqSoftSys extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long devId = 0L; // 系统开发者ID
	@Column(nullable = false, length = 50)
	private String sysName = ""; // 系统名称
	@Column(nullable = false, length = 200)
	private String sysDesc = ""; // 系统介绍
	@Column(nullable = false, length = 200)
	private String sysIcon = ""; // 图标地址
	@Column(nullable = false, length = 200)
	private String sysUrl = ""; // 入口地址
	@Column
	private Calendar createTime = Calendar.getInstance(); // 发布时间
	@Enumerated(EnumType.ORDINAL)
	private ReleaseStatusCode status = null; // 状态

	/**
	 * 取得系统开发者ID
	 */
	public Long getDevId() {
		return devId;
	}

	/**
	 * 设置系统开发者ID
	 */
	public void setDevId(Long devId) {
		this.devId = devId;
	}

	/**
	 * 取得系统名称
	 */
	public String getSysName() {
		return sysName;
	}

	/**
	 * 设置系统名称
	 */
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	/**
	 * 取得系统介绍
	 */
	public String getSysDesc() {
		return sysDesc;
	}

	/**
	 * 设置系统介绍
	 */
	public void setSysDesc(String sysDesc) {
		this.sysDesc = sysDesc;
	}

	/**
	 * 取得图标地址
	 */
	public String getSysIcon() {
		return sysIcon;
	}

	/**
	 * 设置图标地址
	 */
	public void setSysIcon(String sysIcon) {
		this.sysIcon = sysIcon;
	}

	/**
	 * 取得入口地址
	 */
	public String getSysUrl() {
		return sysUrl;
	}

	/**
	 * 设置入口地址
	 */
	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}

	/**
	 * 取得发布时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置发布时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得状态
	 */
	public ReleaseStatusCode getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 */
	public void setStatus(ReleaseStatusCode status) {
		this.status = status;
	}

}
