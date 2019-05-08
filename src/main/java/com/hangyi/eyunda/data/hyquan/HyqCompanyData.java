package com.hangyi.eyunda.data.hyquan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hangyi.eyunda.data.BaseData;

public class HyqCompanyData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String compName = ""; // 公司名称
	private String shortName = ""; // 公司简称

	private String createTime = ""; // 建立时间

	public Long managerId = 0L; // 公司管理员

	private List<HyqDepartmentData> departmentDatas = new ArrayList<HyqDepartmentData>(); // 部门集合

	public HyqCompanyData() {
		super();
	}

	public HyqCompanyData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.id = ((Double) params.get("id")).longValue();
			this.compName = (String) params.get("compName");
			this.shortName = (String) params.get("shortName");
			this.createTime = (String) params.get("createTime");
			this.managerId = ((Double) params.get("managerId")).longValue();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> departmentDatasMap = (List<Map<String, Object>>) params.get("departmentDatas");
			if (departmentDatasMap != null && departmentDatasMap.size() > 0) {
				for (Map<String, Object> map : departmentDatasMap) {
					HyqDepartmentData data = new HyqDepartmentData(map);
					this.departmentDatas.add(data);
				}
			}
		}
	}

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

	public List<HyqDepartmentData> getDepartmentDatas() {
		return departmentDatas;
	}

	public void setDepartmentDatas(List<HyqDepartmentData> departmentDatas) {
		this.departmentDatas = departmentDatas;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
