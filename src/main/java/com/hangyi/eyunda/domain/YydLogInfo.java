package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydLogInfo")
public class YydLogInfo extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long adminId = 0L; // 管理员ID
	@Column
	private Long moduleId = 0L; // 模块ID

	@Column(nullable = false, length = 23)
	private String ipAddr = ""; // IP地址
	@Column
	private Calendar optionTime = Calendar.getInstance(); // 操作时间

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * 取得IP地址
	 */
	public String getIpAddr() {
		return ipAddr;
	}

	/**
	 * 设置IP地址
	 */
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	/**
	 * 取得操作时间
	 */
	public Calendar getOptionTime() {
		return optionTime;
	}

	/**
	 * 设置操作时间
	 */
	public void setOptionTime(Calendar optionTime) {
		this.optionTime = optionTime;
	}

}
