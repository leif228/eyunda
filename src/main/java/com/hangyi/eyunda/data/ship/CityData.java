package com.hangyi.eyunda.data.ship;

import com.hangyi.eyunda.data.BaseData;

public class CityData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String cityNo = ""; // 地市编码

	private String cityName = ""; // 地市名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
