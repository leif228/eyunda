package com.hangyi.eyunda.domain.enumeric;

public enum ShpCertSysCode {
	hyq("船运圈"),

	scf("请船易");

	private String description;

	private ShpCertSysCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
