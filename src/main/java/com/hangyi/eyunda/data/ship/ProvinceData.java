package com.hangyi.eyunda.data.ship;

import com.hangyi.eyunda.data.BaseData;

public class ProvinceData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String provinceNo = ""; // 省份编码

	private String provinceName = ""; // 省份名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvinceNo() {
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
