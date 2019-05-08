package com.hangyi.eyunda.domain.enumeric;

public enum WaresBigTypeCode {
	voyage("航租"),

	daily("期租");

	private String description;

	public String getDescription() {
		return description;
	}

	private WaresBigTypeCode(String description) {
		this.description = description;
	}
}
