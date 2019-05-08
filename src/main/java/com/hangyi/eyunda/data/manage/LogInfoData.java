package com.hangyi.eyunda.data.manage;

import com.hangyi.eyunda.data.BaseData;

public class LogInfoData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = null; // ID
	private Long adminId; // 管理员ID
	private String adminName; // 管理员
	private Long moduleId; // 模块ID
	private String moduleName; // 模块
	private String ipAddr = ""; // IP地址
	private String optionTime = ""; // 操作时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getOptionTime() {
		return optionTime;
	}

	public void setOptionTime(String optionTime) {
		this.optionTime = optionTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
