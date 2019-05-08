package com.hangyi.eyunda.domain.enumeric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MeasureCode {
	teu("个"),

	ton("吨"),

	stere("方");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (MeasureCode e : MeasureCode.values()) {
			maps.add(e.getMap());
		}
		return maps;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("description", this.description);
		return map;
	}

	private MeasureCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
