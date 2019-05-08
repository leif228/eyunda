package com.hangyi.eyunda.domain.enumeric;

public enum CargoStatusCode {
	nopublish("不共享"),
	publish("共享");

	private String description;

	private CargoStatusCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
