package com.hangyi.eyunda.tools;


public class PCALatLng {
	private String provinceName = ""; // 省份名称
	private String cityName = ""; // 地市名称
	private String areaName = ""; // 区县名称
	
	private String longitude = ""; // 经度
	private String latitude = ""; // 纬度
	
	
	public PCALatLng(String provinceName, String cityName, String areaName,
			String longitude, String latitude) {
		super();
		this.provinceName = provinceName;
		this.cityName = cityName;
		this.areaName = areaName;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public PCALatLng() {
		// TODO Auto-generated constructor stub
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "PCALatLng [provinceName=" + provinceName + ", cityName="
				+ cityName + ", areaName=" + areaName + ", longitude="
				+ longitude + ", latitude=" + latitude + "]";
	}
	
	
}
