package com.hangyi.eyunda.domain.enumeric;

public enum GiroTypeCode {
	inaccount("转入"),
	outaccount("转出"),
	dedunct("扣费"),
	confer("赠送");

	private String description;

	private GiroTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
