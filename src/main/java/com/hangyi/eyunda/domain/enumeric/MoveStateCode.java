package com.hangyi.eyunda.domain.enumeric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MoveStateCode {
	arrive("到港"),

	left("离港"),

	upload("装货"),

	download("卸货"),

	stop("停工"),

	stay("滞期"),

	sail("在航"),

	heaveto("停航");

	private String description;

	public String getDescription() {
		return description;
	}

	private MoveStateCode(String description) {
		this.description = description;
	}

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (MoveStateCode e : MoveStateCode.values()) {
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

}
