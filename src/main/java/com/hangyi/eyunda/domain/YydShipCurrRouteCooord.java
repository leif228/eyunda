package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydShipCurrRouteCooord")
public class YydShipCurrRouteCooord extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(length = 20)
	private String mmsi = ""; // 船舶MMSI
	@Column(nullable = true, length = 200)
	private String currCooord = ""; // 当前坐标
	@Column(nullable = true, length = 200)
	private String currRouteFile = ""; // 当前航次轨迹的文件路径

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public String getCurrCooord() {
		return currCooord;
	}

	public void setCurrCooord(String currCooord) {
		this.currCooord = currCooord;
	}

	public String getCurrRouteFile() {
		return currRouteFile;
	}

	public void setCurrRouteFile(String currRouteFile) {
		this.currRouteFile = currRouteFile;
	}

}
