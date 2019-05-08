package com.hangyi.eyunda.data.ship;

import com.hangyi.eyunda.data.BaseData;

public class AreaData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String areaNo = ""; // 区县编码

	private String areaName = ""; // 区县名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
