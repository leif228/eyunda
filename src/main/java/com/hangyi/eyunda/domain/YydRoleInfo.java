package com.hangyi.eyunda.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "YydRoleInfo")
public class YydRoleInfo extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 20)
	private String roleName = ""; // 角色名称
	@Column(nullable = true, length = 200)
	private String roleDesc = ""; // 角色描述

	/**
	 * 以下标注说明YydRoleInfo为被控方， 被控方与主控方的唯一区别是update时， 不会误删除关联对象，但delete时还会误删除关联对象，
	 * 因此delete操作时在逻辑层要专门处理。
	 */
	@ManyToMany(mappedBy = "roles", cascade = { CascadeType.ALL })
	private Set<YydAdminInfo> admins;

	public Set<YydAdminInfo> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<YydAdminInfo> admins) {
		this.admins = admins;
	}

	/**
	 * 以下标注说明YydRoleInfo为主控方， 主控方发生insert,update,delete时， 会自动维护关联表，也会误删除关联对象，
	 * 因此这些操作有时在逻辑层要专门处理。
	 */
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "yyd_role_module", joinColumns = { @JoinColumn(name = "roleId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "moduleId", nullable = false, updatable = false) })
	private Set<YydModuleInfo> modules;

	public Set<YydModuleInfo> getModules() {
		return modules;
	}

	public void setModules(Set<YydModuleInfo> modules) {
		this.modules = modules;
	}

	/**
	 * 取得角色名称
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 设置角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 取得角色描述
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * 设置角色描述
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

}
