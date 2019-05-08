package com.hangyi.eyunda.domain2;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "YydShipCooord", indexes = { @Index(name = "idx_mmsi", columnList = "mmsi", unique = false),
		@Index(name = "idx_posTime", columnList = "posTime", unique = false) })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydShipCooord extends BaseEntity2 {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 20)
	private String mmsi = ""; // MMSI编号
	@Column(nullable = false, length = 20)
	private String shipName = ""; // 船名
	@Column(nullable = false, length = 20)
	private String posTime = ""; // 时间2016-07-30 11:29:21
	@Column
	private Double longitude = 0.00D; // 百度经度
	@Column
	private Double latitude = 0.00D; // 百度纬度
	@Column
	private Double speed = 0.00D; // 速度
	@Column
	private Double course = 0.00D; // 航向
	@Column
	private Double lng = 0.00D; // 经度
	@Column
	private Double lat = 0.00D; // 纬度

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

}
