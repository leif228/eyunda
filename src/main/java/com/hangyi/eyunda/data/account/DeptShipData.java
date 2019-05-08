package com.hangyi.eyunda.data.account;

import java.util.ArrayList;
import java.util.List;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.ship.ShipData;

public class DeptShipData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long deptId = 0L; // ID

	private DepartmentData departmentData = null; // 部门
	private List<ShipData> shipDatas = new ArrayList<ShipData>(); // 成员集合

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public DepartmentData getDepartmentData() {
		return departmentData;
	}

	public void setDepartmentData(DepartmentData departmentData) {
		this.departmentData = departmentData;
	}

	public List<ShipData> getShipDatas() {
		return shipDatas;
	}

	public void setShipDatas(List<ShipData> shipDatas) {
		this.shipDatas = shipDatas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
