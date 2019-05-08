package com.hangyi.eyunda.domain.enumeric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum HyqUnitTypeCode {
	enterprise("企业单位"),
	
	institution("事业单位"),
	
	vate("民营企业"),
	
	individual("个体工商户"),

	mass("社团组织"),

	party("党政团体");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (HyqUnitTypeCode code : HyqUnitTypeCode.values()) {
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

	private HyqUnitTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
