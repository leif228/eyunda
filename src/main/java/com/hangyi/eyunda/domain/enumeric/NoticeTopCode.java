package com.hangyi.eyunda.domain.enumeric;

public enum NoticeTopCode {
	no("否"),
	yes("是");
	
	private String description;

	private NoticeTopCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
