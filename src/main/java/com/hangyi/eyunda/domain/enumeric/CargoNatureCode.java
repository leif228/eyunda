package com.hangyi.eyunda.domain.enumeric;

public enum CargoNatureCode {
	ordinary("普通货物"), dangerous("危险品");

	private String description;

	private CargoNatureCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
