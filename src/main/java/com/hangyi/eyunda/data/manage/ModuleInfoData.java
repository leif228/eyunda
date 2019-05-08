package com.hangyi.eyunda.data.manage;

import com.hangyi.eyunda.data.BaseData;

public class ModuleInfoData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = null; // 模块ID
	private String moduleName = ""; // 模块名称
	private String moduleDesc = ""; // 模块说明
	private String moduleLayer = ""; // 层次号码
	private String moduleUrl = ""; // 入口地址
	private boolean theModule = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public String getModuleLayer() {
		return moduleLayer;
	}

	public void setModuleLayer(String moduleLayer) {
		this.moduleLayer = moduleLayer;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public boolean getTheModule() {
		return theModule;
	}

	public boolean isTheModule() {
		return theModule;
	}

	public void setTheModule(boolean theModule) {
		this.theModule = theModule;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
