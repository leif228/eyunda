package com.hangyi.eyunda.domain.enumeric;

public enum OrderStateCode {
	edit("未处理"),
	submit("已处理"),
	startsign("承运人已签"),
	endsign("托运人已签"),
	archive("已归档");

	private String description;

	private OrderStateCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
