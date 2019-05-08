package com.hangyi.eyunda.domain.enumeric;

public enum LoginSourceCode {
	web("网页"),
	mobile("手机");

	private String description;

	private LoginSourceCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
