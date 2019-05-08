package com.hangyi.eyunda.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "YydModuleInfo")
public class YydModuleInfo extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 20)
	private String moduleName = ""; // 模块名称
	@Column(nullable = true, length = 200)
	private String moduleDesc = ""; // 模块说明
	@Column(nullable = false, length = 20)
	private String moduleLayer = ""; // 层次号码
	@Column(nullable = true, length = 200)
	private String moduleUrl = ""; // 入口地址

	/**
	 * 以下标注说明YydModuleInfo为被控方， 被控方与主控方的唯一区别是update时，
	 * 不会误删除关联对象，但delete时还会误删除关联对象， 因此delete操作时在逻辑层要专门处理。
	 */
	@ManyToMany(mappedBy = "modules", cascade = { CascadeType.ALL })
	private Set<YydRoleInfo> roles;

	public Set<YydRoleInfo> getRoles() {
		return roles;
	}

	public void setRoles(Set<YydRoleInfo> roles) {
		this.roles = roles;
	}

	/**
	 * 取得模块名称
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * 设置模块名称
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * 取得模块说明
	 */
	public String getModuleDesc() {
		return moduleDesc;
	}

	/**
	 * 设置模块说明
	 */
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	/**
	 * 取得层次号码
	 */
	public String getModuleLayer() {
		return moduleLayer;
	}

	/**
	 * 设置层次号码
	 */
	public void setModuleLayer(String moduleLayer) {
		this.moduleLayer = moduleLayer;
	}

	/**
	 * 取得入口地址
	 */
	public String getModuleUrl() {
		return moduleUrl;
	}

	/**
	 * 设置入口地址
	 */
	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

}
