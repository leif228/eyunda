package com.hangyi.eyunda.domain.enumeric;

public enum AuditStatusCode {
	unaudit("未认证"), audit("已认证");

	private String description;

	private AuditStatusCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
