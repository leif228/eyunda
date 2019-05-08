package com.hangyi.eyunda.domain.enumeric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum RegionCode {
	china("中国大陆"),

	hongkong("中国香港"),

	macao("中国澳门"),

	taiwan("中国台湾"),

	foreign("中国境外");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (RegionCode code : RegionCode.values()) {
			codes.add(code.getMap());
		}
		return codes;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("description", this.description);
		return map;
	}

	private RegionCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
