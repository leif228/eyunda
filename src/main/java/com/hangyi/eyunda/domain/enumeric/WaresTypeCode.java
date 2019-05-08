package com.hangyi.eyunda.domain.enumeric;

public enum WaresTypeCode {
	gaxjzx("港澳线集装箱"),

	nmjzx("内贸集装箱"),

	nmszh("内贸散杂货");

	private String description;

	public String getDescription() {
		return description;
	}

	private WaresTypeCode(String description) {
		this.description = description;
	}
}
