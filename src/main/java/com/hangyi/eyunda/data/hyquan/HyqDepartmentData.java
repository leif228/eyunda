package com.hangyi.eyunda.data.hyquan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hangyi.eyunda.data.BaseData;

public class HyqDepartmentData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String deptName = ""; // 部门名称

	private String createTime = ""; // 建立时间

	private List<HyqUserData> userDatas = new ArrayList<HyqUserData>(); // 成员集合

	public HyqDepartmentData() {
		super();
	}

	public HyqDepartmentData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.id = ((Double) params.get("id")).longValue();
			this.deptName = (String) params.get("deptName");
			this.createTime = (String) params.get("createTime");
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> userDatasMap = (List<Map<String, Object>>) params.get("userDatas");
			if (userDatasMap != null && userDatasMap.size() > 0) {
				for (Map<String, Object> map : userDatasMap) {
					HyqUserData data = new HyqUserData(map);
					this.userDatas.add(data);
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<HyqUserData> getUserDatas() {
		return userDatas;
	}

	public void setUserDatas(List<HyqUserData> userDatas) {
		this.userDatas = userDatas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
