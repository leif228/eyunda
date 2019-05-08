package com.hangyi.eyunda.domain.enumeric;

public enum UserTypeCode {
	person("个人"),

	enterprise("企业");

	private String description;

	private UserTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
