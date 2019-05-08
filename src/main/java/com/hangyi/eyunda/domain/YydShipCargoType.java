package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;

@Entity
@Table(name = "YydShipCargoType")
public class YydShipCargoType extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long shipId = 0L; // 船舶ID
	@Enumerated(EnumType.ORDINAL)
	private CargoTypeCode cargoType = null; // 货类编码

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
	 * 取得货类编码
	 */
	public CargoTypeCode getCargoType() {
		return cargoType;
	}

	/**
	 * 设置货类编码
	 */
	public void setCargoType(CargoTypeCode cargoType) {
		this.cargoType = cargoType;
	}

}
