package com.hangyi.eyunda.domain.enumeric;

public enum ShipMonitorPlantCode {
	baochuanwang("AIS", "http://www.myships.com/myships/10026"), 
	shipmanagerplant("船管平台", "http://192.168.1.199:8080/eyundaService/shipMonitor/currRouteDatas"),
	sailormobile("船员手机", "http://www.eyd98.com");

	private String description;
	private String currUrl;

	private ShipMonitorPlantCode(String description, String currUrl) {
		this.description = description;
		this.currUrl = currUrl;
	}

	public String getDescription() {
		return description;
	}

	public String getCurrUrl() {
		return currUrl;
	}
}
