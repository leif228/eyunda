package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydArea")
public class YydArea extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 6)
	private String areaNo = ""; // 区县编码
	@Column(nullable = false, length = 20)
	private String areaName = ""; // 区县名称
	@Column
	private Double latitude = 0.00D; // 纬度
	@Column
	private Double longitude = 0.00D; // 经度
	
	/**
	 * 取得区县编码
	 */
	public String getAreaNo() {
		return areaNo;
	}

	/**
	 * 设置区县编码
	 */
	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	/**
	 * 取得区县名称
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * 设置区县名称
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
