package com.hangyi.eyunda.data.ship;

import com.hangyi.eyunda.data.BaseData;

public class ShipCooordData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String mmsi = ""; // MMSI编号
	private String shipName = ""; // 船名
	private String posTime = ""; // 时间
	private Double longitude = 0.00D; // 百度经度
	private Double latitude = 0.00D; // 百度纬度
	private Double speed = 0.00D; // 速度
	private Double course = 0.00D; // 航向
	private Double lng = 0.00D; // 经度
	private Double lat = 0.00D; // 纬度

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getPosTime() {
		return posTime;
	}

	public void setPosTime(String posTime) {
		this.posTime = posTime;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getCourse() {
		return course;
	}

	public void setCourse(Double course) {
		this.course = course;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
