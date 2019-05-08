package com.hangyi.eyunda.domain.enumeric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScfBigAreaCode {

	ZHUSANJIAO("2", "珠三角", "泛珠三角流域、南海周边及港澳台地区"),

	CHANGSANJIAO("4", "长三角", "长江流域、淮河流域及京杭大运河流域"),

	CHINACOAST("6", "沿海", "中国沿海港口城市"),

	WORLDOCEAN("7", "远洋", "亚洲、美洲、欧洲、非洲及大洋洲著名港口");

	private String code;
	private String description;
	private List<ScfPortCityCode> portCities;
	private String fullName;
	private String remark;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> bigAreas = new ArrayList<Map<String, Object>>();
		for (ScfBigAreaCode bigArea : ScfBigAreaCode.values()) {
			bigAreas.add(bigArea.getMap());
		}
		return bigAreas;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("code", this.code);
		map.put("description", this.description);
		map.put("fullName", this.fullName);
		map.put("remark", this.remark);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (this.portCities == null || this.portCities.isEmpty())
			this.portCities = ScfPortCityCode.getPortCities(this);

		for (ScfPortCityCode ct : this.portCities) {
			list.add(ct.getMap());
		}
		map.put("portCities", list);

		return map;
	}

	public static ScfBigAreaCode getByCode(String code) {
		for (ScfBigAreaCode e : ScfBigAreaCode.values())
			if (e.getCode().equals(code))
				return e;

		return null;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getFullName() {
		return fullName;
	}

	public String getRemark() {
		return remark;
	}

	public List<ScfPortCityCode> getPortCities() {
		return portCities;
	}

	public void setPortCities(List<ScfPortCityCode> portCities) {
		this.portCities = portCities;
	}

	private ScfBigAreaCode(String code, String description, String remark) {
		this.code = code;
		this.description = description;
		this.fullName = description;
		this.remark = remark;
	}
	
	public static void main(String[] args) {
		System.out.println(ScfBigAreaCode.getMaps());
	}
	
}
