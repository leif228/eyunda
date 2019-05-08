package com.hangyi.eyunda.domain.enumeric;

public enum ShipTypeCode {
	cargoShip("货船"),

	containerShip("集装箱船"),

	multiShip("多用途船"),

	tankShip("油船"),

	chemicalShip("化学品船"),

	liquidShip("液体化学品船");

	private String description;

	public String getDescription() {
		return description;
	}

	private ShipTypeCode(String description) {
		this.description = description;
	}
}
