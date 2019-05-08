package com.hangyi.eyunda.domain.enumeric;

public enum ShpRightsCode {
	managedShip("船舶证书管理权"), 
	
	favoriteShip("公开证书查看权"),
	
	seeCertRights("船舶证书授权");

	private String description;

	private ShpRightsCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
