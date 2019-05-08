package com.hangyi.eyunda.data.account;

import java.util.ArrayList;
import java.util.List;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;

public class DepartmentData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String deptName = ""; // 部门名称
	private UserPrivilegeCode deptType = null; // 部门类型

	private String createTime = ""; // 建立时间

	private List<UserData> userDatas = new ArrayList<UserData>(); // 成员集合

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 取得部门类型
	 */
	public UserPrivilegeCode getDeptType() {
		return deptType;
	}

	/**
	 * 设置部门类型
	 */
	public void setDeptType(UserPrivilegeCode deptType) {
		this.deptType = deptType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<UserData> getUserDatas() {
		return userDatas;
	}

	public void setUserDatas(List<UserData> userDatas) {
		this.userDatas = userDatas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
