package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.ShipMonitorPlantCode;

@Entity
@Table(name = "YydShipMonitorPlant")
public class YydShipMonitorPlant extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 20)
	private String mmsi = ""; // 船舶mmsi
	@Column
	private ShipMonitorPlantCode plantCode = ShipMonitorPlantCode.baochuanwang; // 注册时间

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public ShipMonitorPlantCode getPlantCode() {
		return plantCode;
	}

	public void setPlantCodeCode(ShipMonitorPlantCode plantCode) {
		this.plantCode = plantCode;
	}

}
