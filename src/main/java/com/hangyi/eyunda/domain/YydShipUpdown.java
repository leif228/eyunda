package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;

@Entity
@Table(name = "YydShipUpdown")
public class YydShipUpdown extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long arvlftId = 0L; // 到离ID
	@Enumerated(EnumType.ORDINAL)
	private CargoTypeCode cargoType = CargoTypeCode.container20e; // 货类
	@Column(nullable = false, length = 50)
	private String cargoName = ""; // 货名
	@Column
	private Double tonTeu = 0.00D; // 货量或箱量

	/**
	 * 取得到离ID
	 */
	public Long getArvlftId() {
		return arvlftId;
	}

	/**
	 * 设置到离ID
	 */
	public void setArvlftId(Long arvlftId) {
		this.arvlftId = arvlftId;
	}

	/**
	 * 取得货类
	 */
	public CargoTypeCode getCargoType() {
		return cargoType;
	}

	/**
	 * 设置货类
	 */
	public void setCargoType(CargoTypeCode cargoType) {
		this.cargoType = cargoType;
	}

	/**
	 * 取得货名
	 */
	public String getCargoName() {
		return cargoName;
	}

	/**
	 * 设置货名
	 */
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public Double getTonTeu() {
		return tonTeu;
	}

	public void setTonTeu(Double tonTeu) {
		this.tonTeu = tonTeu;
	}

}
