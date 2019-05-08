package com.hangyi.eyunda.domain.enumeric;

public enum HyqSysTypeCode {
	cms("信息服务"),

	app("应用服务"),

	estore("电子商务"),

	proxy("代理服务");

	private String description;

	private HyqSysTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
