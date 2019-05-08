package com.hangyi.eyunda.domain.enumeric;

public enum HyqCheckTypeCode {
	register("注册"), findPassword("找回密码");

	private String description;

	private HyqCheckTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
