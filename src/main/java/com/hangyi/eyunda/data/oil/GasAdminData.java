package com.hangyi.eyunda.data.oil;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.manage.AdminInfoData;

public class GasAdminData extends BaseData {

	private static final long serialVersionUID = 1L;

	private Long id = 0L;

	private Long adminId = 0L; // 操作员Id
	private String loginName = ""; // 操作员Id
	private AdminInfoData adminData = null; //

	private Long companyId = 0L; // 公司Id
	private GasCompanyData companyData = null; //

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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public AdminInfoData getAdminData() {
		return adminData;
	}

	public void setAdminData(AdminInfoData adminData) {
		this.adminData = adminData;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public GasCompanyData getCompanyData() {
		return companyData;
	}

	public void setCompanyData(GasCompanyData companyData) {
		this.companyData = companyData;
	}

}
