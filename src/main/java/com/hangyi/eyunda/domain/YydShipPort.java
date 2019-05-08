package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydShipPort")
public class YydShipPort extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long shipId = 0L; // 船舶ID
	@Column(nullable = false, length = 8)
	private String portNo = ""; // 接货港口

	/**
	 * 取得船舶ID
	 */
	public Long getShipId() {
		return shipId;
	}

	/**
	 * 设置船舶ID
	 */
	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}

	/**
	 * 取得接货港口
	 */
	public String getPortNo() {
		return portNo;
	}

	/**
	 * 设置接货港口
	 */
	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}

}
