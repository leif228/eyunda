package com.hangyi.eyunda.domain.enumeric;

public enum HyqIspTypeCode {
	cargoAgent("货运代理"),

	shipProxy("船船代理"),

	shipManager("船船管理"),

	insurance("保险"),

	finance("金融"),

	oil("油料供应"),

	shipBroker("船舶经纪"),

	port("港口码头"),

	storage("仓储场站");

	private String description;

	private HyqIspTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
