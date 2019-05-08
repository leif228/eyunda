package com.hangyi.eyunda.data.manage;

import java.util.List;

import com.hangyi.eyunda.data.BaseData;

public class RoleInfoData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = null; // 角色ID
	private String roleName = ""; // 角色名称
	private String roleDesc = ""; // 角色描述
	private boolean theRole = false;

	private List<ModuleInfoData> moduleDatas = null;

	public List<ModuleInfoData> getModuleDatas() {
		return moduleDatas;
	}

	public void setModuleDatas(List<ModuleInfoData> moduleDatas) {
		this.moduleDatas = moduleDatas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public boolean isTheRole() {
		return theRole;
	}

	public void setTheRole(boolean theRole) {
		this.theRole = theRole;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
