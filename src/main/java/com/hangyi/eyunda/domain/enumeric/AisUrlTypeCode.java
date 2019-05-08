package com.hangyi.eyunda.domain.enumeric;

public enum AisUrlTypeCode {
	
	ComKey("http://api.myships.com/DataApiServer/getComKey", "加密种子"),
	ShipId("http://api.myships.com/DataApiServer/getShipId", "船舶查询"),
	ShipBasicInfo("http://api.myships.com/DataApiServer/getShipBasicInformation", "船舶基本信息"),
	ShipLatest("http://api.myships.com/DataApiServer/getShipLatest", "最新船位"),
	ShipHistorTrack("http://api.myships.com/DataApiServer/getShipHistorTrack", "历史轨迹");
	
	private String url;
	private String description;
	
	private AisUrlTypeCode(String url, String descriprption){
		this.url = url;
		this.description = descriprption;
	}

	public String getUrl(){
		return url;
	}
	
	public String getDescription(){
		return description;
	}
	
}
