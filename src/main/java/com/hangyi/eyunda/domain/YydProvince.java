package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydProvince")
public class YydProvince extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 2)
	private String provinceNo = ""; // 省份编码
	@Column(nullable = false, length = 20)
	private String provinceName = ""; // 省份名称
	@Column
	private Double latitude = 0.00D; // 纬度
	@Column
	private Double longitude = 0.00D; // 经度
	
	/**
	 * 取得省份编码
	 */
	public String getProvinceNo() {
		return provinceNo;
	}

	/**
	 * 设置省份编码
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	/**
	 * 取得省份名称
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * 设置省份名称
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
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
