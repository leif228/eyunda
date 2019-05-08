package com.hangyi.eyunda.data.ship;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.CargoBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.util.NumberFormatUtil;

public class ShipUpdownData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID
	private Long arvlftId = 0L; // 到离ID

	private CargoTypeCode cargoType = null; // 货类
	private String cargoName = ""; // 货名
	private Double tonTeu = 0.00D; // 货量或箱量

	private String cargoNameDesc = ""; // 货名描述
	private String tonTeuDesc = ""; // 货量描述

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArvlftId() {
		return arvlftId;
	}

	public void setArvlftId(Long arvlftId) {
		this.arvlftId = arvlftId;
	}

	public CargoTypeCode getCargoType() {
		return cargoType;
	}

	public void setCargoType(CargoTypeCode cargoType) {
		this.cargoType = cargoType;

		if (this.getCargoType().getCargoBigType() == CargoBigTypeCode.container) {
			this.setCargoNameDesc(CargoBigTypeCode.container.getDescription() + "." + this.getCargoName());
			this.setTonTeuDesc(NumberFormatUtil.toInt(this.getTonTeu()) + "个");
		} else {
			this.setCargoNameDesc(this.getCargoType().getCargoBigType().getDescription() + "." + this.getCargoName());
			this.setTonTeuDesc(NumberFormatUtil.format(this.getTonTeu()) + "吨");
		}
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;

		if (this.getCargoType().getCargoBigType() == CargoBigTypeCode.container) {
			this.setCargoNameDesc(CargoBigTypeCode.container.getDescription() + "." + this.getCargoName());
			this.setTonTeuDesc(NumberFormatUtil.toInt(this.getTonTeu()) + "个");
		} else {
			this.setCargoNameDesc(this.getCargoType().getCargoBigType().getDescription() + "." + this.getCargoName());
			this.setTonTeuDesc(NumberFormatUtil.format(this.getTonTeu()) + "吨");
		}
	}

	public Double getTonTeu() {
		return tonTeu;
	}

	public void setTonTeu(Double tonTeu) {
		this.tonTeu = tonTeu;

		if (this.getCargoType().getCargoBigType() == CargoBigTypeCode.container) {
			this.setCargoNameDesc(CargoBigTypeCode.container.getDescription() + "." + this.getCargoName());
			this.setTonTeuDesc(NumberFormatUtil.toInt(this.getTonTeu()) + "个");
		} else {
			this.setCargoNameDesc(this.getCargoType().getCargoBigType().getDescription() + "." + this.getCargoName());
			this.setTonTeuDesc(NumberFormatUtil.format(this.getTonTeu()) + "吨");
		}
	}

	public String getCargoNameDesc() {
		return cargoNameDesc;
	}

	public void setCargoNameDesc(String cargoNameDesc) {
		this.cargoNameDesc = cargoNameDesc;
	}

	public String getTonTeuDesc() {
		return tonTeuDesc;
	}

	public void setTonTeuDesc(String tonTeuDesc) {
		this.tonTeuDesc = tonTeuDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
