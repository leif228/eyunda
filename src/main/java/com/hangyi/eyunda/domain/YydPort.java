package com.hangyi.eyunda.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "YydPort", indexes = { @Index(name = "idx_portNo", columnList = "portNo", unique = false) })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydPort extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 8)
	private String portNo = ""; // 港口编码
	@Column(nullable = false, length = 50)
	private String portName = ""; // 港口名称
	@Column(nullable = false, length = 100)
	private String engPortName = ""; // 港口名称
	@Column(nullable = false, length = 50)
	private String fullPortName = ""; // 全称港口名称
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
	 * 取得港口名称
	 */
	public String getPortName() {
		return portName;
	}

	/**
	 * 设置港口名称
	 */
	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getEngPortName() {
		return engPortName;
	}

	public void setEngPortName(String engPortName) {
		this.engPortName = engPortName;
	}

	public String getFullPortName() {
		return fullPortName;
	}

	public void setFullPortName(String fullPortName) {
		this.fullPortName = fullPortName;
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
