package com.hangyi.eyunda.domain.enumeric;

public enum PriceTypeCode {
	discuss("议价"), invite("招标");

	private String description;

	private PriceTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
