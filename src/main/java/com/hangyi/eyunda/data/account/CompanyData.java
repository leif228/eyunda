package com.hangyi.eyunda.data.account;

import java.util.ArrayList;
import java.util.List;

import com.hangyi.eyunda.data.BaseData;

public class CompanyData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String compName = ""; // 公司名称
	private String shortName = ""; // 公司简称

	private String createTime = ""; // 建立时间

	private List<DepartmentData> departmentDatas = new ArrayList<DepartmentData>(); // 部门集合

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<DepartmentData> getDepartmentDatas() {
		return departmentDatas;
	}

	public void setDepartmentDatas(List<DepartmentData> departmentDatas) {
		this.departmentDatas = departmentDatas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
