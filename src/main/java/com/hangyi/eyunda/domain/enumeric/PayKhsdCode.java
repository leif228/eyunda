package com.hangyi.eyunda.domain.enumeric;

public enum PayKhsdCode {
	paying("支付中..."),

	success("支付成功"),

	failure("支付失败");

	private String description;

	private PayKhsdCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
