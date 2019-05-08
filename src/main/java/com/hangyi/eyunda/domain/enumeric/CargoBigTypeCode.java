package com.hangyi.eyunda.domain.enumeric;

import java.util.List;

public enum CargoBigTypeCode {
	container("集装箱"),

	bulk("散杂货"),

	danger("危险品");

	private String description;
	private List<CargoTypeCode> cargoTypes;

	public String getDescription() {
		return description;
	}

	public List<CargoTypeCode> getCargoTypes() {
		if (this.cargoTypes == null)
			this.cargoTypes = CargoTypeCode.getCodesByCargoBigType(this);
		return cargoTypes;
	}

	public void setCargoTypes(List<CargoTypeCode> cargoTypes) {
		this.cargoTypes = cargoTypes;
	}

	private CargoBigTypeCode(String description) {
		this.description = description;
	}
}
