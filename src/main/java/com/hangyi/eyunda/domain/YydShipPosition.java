package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydShipPosition")
public class YydShipPosition extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 512)
	private String cbZid = ""; // 船舶ID
	@Column(nullable = false, length = 512)
	private String daData = ""; // 原始数据
	@Column(nullable = true, length = 30)
	private String gpsTime = ""; // 发出时间

	@Column(nullable = true, length = 30)
	private String recTime = ""; // 接收时间
	@Column(nullable = true, length = 20)
	private String baiduLng = ""; // 转换后的经度
	@Column(nullable = true, length = 20)
	private String baiduLat = ""; // 转换后的纬度
	
	public String getCbZID() {
		return cbZid;
	}
	public void setCbZID(String cbZID) {
		this.cbZid = cbZID;
	}
	public String getDaData() {
		return daData;
	}
	public void setDaData(String daData) {
		this.daData = daData;
	}
	public String getGpsTime() {
		return gpsTime;
	}
	public void setGpsTime(String gpsTime) {
		this.gpsTime = gpsTime;
	}
	public String getRecTime() {
		return recTime;
	}
	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}
	public String getBaiduLng() {
		return baiduLng;
	}
	public void setBaiduLng(String baiduLng) {
		this.baiduLng = baiduLng;
	}
	public String getBaiduLat() {
		return baiduLat;
	}
	public void setBaiduLat(String baiduLat) {
		this.baiduLat = baiduLat;
	}

}
