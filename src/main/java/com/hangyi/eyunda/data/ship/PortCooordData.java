package com.hangyi.eyunda.data.ship;

import com.hangyi.eyunda.data.BaseData;

public class PortCooordData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Double latitude = 0.00D; // 纬度

	private Double longitude = 0.00D; // 经度

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
