package com.hangyi.eyunda.domain.enumeric;

public enum RlsColumnCode {
	homepage("首页"),
	memspace("会员空间"),
	logoAdvert("Logo广告");

	private String description;

	private RlsColumnCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
