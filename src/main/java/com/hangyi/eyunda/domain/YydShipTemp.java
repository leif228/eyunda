package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydShipTemp")
public class YydShipTemp extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 20)
	private String country = ""; // 国家
	@Column(nullable = false, length = 20)
	private String shipType = ""; // 船类
	@Column(nullable = false, length = 50)
	private String shipName = ""; // 船名
	@Column(nullable = true, length = 50)
	private String engName = ""; // 拼音船名
	@Column(nullable = false, length = 20)
	private String chsName = ""; // 中文船名
	@Column(nullable = false, length = 20)
	private String mmsi = ""; // MMSI编号
	@Column(nullable = false, length = 20)
	private String imo = ""; // IMO编号
	@Column(nullable = false, length = 20)
	private String callsign = ""; // 呼号
	@Column
	private Double length = 0.00D; // 船长
	@Column
	private Double breadth = 0.00D; // 船宽
	@Column
	private Double draught = 0.00D; // 吃水深度
	@Column(nullable = true, length = 20)
	private String commTypeName = ""; // 报位类型

	/**
	 * 取得国家
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 设置国家
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 取得船类
	 */
	public String getShipType() {
		return shipType;
	}

	/**
	 * 设置船类
	 */
	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	/**
	 * 取得船名
	 */
	public String getShipName() {
		return shipName;
	}

	/**
	 * 设置船名
	 */
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	/**
	 * 取得中文船名
	 */
	public String getChsName() {
		return chsName;
	}

	/**
	 * 设置中文船名
	 */
	public void setChsName(String chsName) {
		this.chsName = chsName;
	}

	/**
	 * 取得MMSI编号
	 */
	public String getMmsi() {
		return mmsi;
	}

	/**
	 * 设置MMSI编号
	 */
	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	/**
	 * 取得IMO编号
	 */
	public String getImo() {
		return imo;
	}

	/**
	 * 设置IMO编号
	 */
	public void setImo(String imo) {
		this.imo = imo;
	}

	/**
	 * 取得呼号
	 */
	public String getCallsign() {
		return callsign;
	}

	/**
	 * 设置呼号
	 */
	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}

	/**
	 * 取得船长
	 */
	public Double getLength() {
		return length;
	}

	/**
	 * 设置船长
	 */
	public void setLength(Double length) {
		this.length = length;
	}

	/**
	 * 取得船宽
	 */
	public Double getBreadth() {
		return breadth;
	}

	/**
	 * 设置船宽
	 */
	public void setBreadth(Double breadth) {
		this.breadth = breadth;
	}

	/**
	 * 取得吃水深度
	 */
	public Double getDraught() {
		return draught;
	}

	/**
	 * 设置吃水深度
	 */
	public void setDraught(Double draught) {
		this.draught = draught;
	}

	/**
	 * 取得报位类型
	 */
	public String getCommTypeName() {
		return commTypeName;
	}

	/**
	 * 设置报位类型
	 */
	public void setCommTypeName(String commTypeName) {
		this.commTypeName = commTypeName;
	}

}
