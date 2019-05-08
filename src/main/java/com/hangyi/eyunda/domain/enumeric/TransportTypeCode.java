package com.hangyi.eyunda.domain.enumeric;

//物流运输类型
public enum TransportTypeCode {
	POST("平邮"), EXPRESS("快递"), EMS("EMS");

	private String description;

	private TransportTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
