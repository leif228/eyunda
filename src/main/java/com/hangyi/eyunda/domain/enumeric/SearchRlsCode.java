package com.hangyi.eyunda.domain.enumeric;

public enum SearchRlsCode {
	shipsearch("船盘"),
	cargosearch("货盘");

	private String description;

	private SearchRlsCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
