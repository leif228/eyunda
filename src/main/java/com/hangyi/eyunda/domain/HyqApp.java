package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

@Entity
@Table(name = "HyqApp")
public class HyqApp extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long ispId = 0L; // 服务提供者ID
	@Column(nullable = false, length = 50)
	private String appName = ""; // 服务名称
	@Column(nullable = false, length = 200)
	private String appDesc = ""; // 服务介绍
	@Column(nullable = false, length = 200)
	private String appIcon = ""; // 图标地址
	@Column(nullable = false, length = 200)
	private String appUrl = ""; // 入口地址
	@Column
	private Calendar createTime = Calendar.getInstance(); // 发布时间
	@Enumerated(EnumType.ORDINAL)
	private ReleaseStatusCode status = null; // 状态

	/**
	 * 取得服务提供者ID
	 */
	public Long getIspId() {
		return ispId;
	}

	/**
	 * 设置服务提供者ID
	 */
	public void setIspId(Long ispId) {
		this.ispId = ispId;
	}

	/**
	 * 取得服务名称
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * 设置服务名称
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * 取得服务介绍
	 */
	public String getAppDesc() {
		return appDesc;
	}

	/**
	 * 设置服务介绍
	 */
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	/**
	 * 取得图标地址
	 */
	public String getAppIcon() {
		return appIcon;
	}

	/**
	 * 设置图标地址
	 */
	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}

	/**
	 * 取得入口地址
	 */
	public String getAppUrl() {
		return appUrl;
	}

	/**
	 * 设置入口地址
	 */
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
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
