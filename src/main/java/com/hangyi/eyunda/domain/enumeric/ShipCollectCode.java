package com.hangyi.eyunda.domain.enumeric;

public enum ShipCollectCode {

	mySelfShips("我的船舶"),

	collectShips("收藏船舶");

	private String description;

	ShipCollectCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
