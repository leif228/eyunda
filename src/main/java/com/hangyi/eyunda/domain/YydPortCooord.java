package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydPortCooord")
public class YydPortCooord extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 8)
	private String portNo = ""; // 港口编码
	@Column
	private Double latitude = 0.00D; // 纬度
	@Column
	private Double longitude = 0.00D; // 经度

	/**
	 * 取得港口编码
	 */
	public String getPortNo() {
		return portNo;
	}

	/**
	 * 设置港口编码
	 */
	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}

	/**
	 * 取得纬度
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * 设置纬度
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * 取得经度
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * 设置经度
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
