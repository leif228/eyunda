package com.hangyi.eyunda.domain.enumeric;

public enum ContainerTypeCode {
	container20e("集装箱.20’E"),

	container20f("集装箱.20’F"),

	container40e("集装箱.40’E"),

	container40f("集装箱.40’F"),

	container45e("集装箱.45’E"),

	container45f("集装箱.45’F");

	private String description;

	public String getDescription() {
		return description;
	}

	public String getShortDesc() {
		return description.substring(description.indexOf(".") + 1);
	}

	private ContainerTypeCode(String description) {
		this.description = description;
	}
}
