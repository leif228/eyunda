package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydCity")
public class YydCity extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 4)
	private String cityNo = ""; // 地市编码
	@Column(nullable = false, length = 20)
	private String cityName = ""; // 地市名称
	@Column
	private Double latitude = 0.00D; // 纬度
	@Column
	private Double longitude = 0.00D; // 经度
	/**
	 * 取得地市编码
	 */
	public String getCityNo() {
		return cityNo;
	}

	/**
	 * 设置地市编码
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	/**
	 * 取得地市名称
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * 设置地市名称
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

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

}
