package com.hangyi.eyunda.domain.enumeric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CertTypeCode {
	inspection("内河船舶检验证书簿"),
	airworthiness("内河船舶适航证书"),
	loadLine("内河船舶载重线证书"),
	tonnage("内河船舶吨位证书"),
	oilDirt("内河船舶防止油污证书"),
	trashDirt("内河船舶防止垃圾污染证书"),
	airDirt("内河船舶防止空气污染证书"),
	dangerous("内河船舶装运危险货物适装证书"),
	sailor("内河船舶船员舱室设备证书"),
	safetyman("船舶最低安全配员证书"),
	nationality("船舶国籍证书"),
	possession("船舶所有权登记证书"),
	operation("船舶营业运输证"),
	deviation("磁罗经自差表"),
	safeCheck("船舶安全检查记录簿"),
	insurance("船舶保险单"),
	hmOperation("港澳航线船舶营运证"),
	hmSailing("航行港澳船舶证明书"),
	hmHealth("交通工具卫生证书"),
	hmMouse("免予除鼠证书"),
	hmRegister("船舶登记备案证书"),
	hmCustoms("海关监管簿"),
	hmProvAudit("交通部港澳批文"),
	hmCityAudit("交通厅港澳批文");

	private String description;

	public static List<Map<String, Object>> getMaps() {
		List<Map<String, Object>> codes = new ArrayList<Map<String, Object>>();
		for (CertTypeCode code : CertTypeCode.values()) {
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

	public static List<CertTypeCode> getByKeywords(String keywords) {
		List<CertTypeCode> ctcs = new ArrayList<CertTypeCode>();
		for (CertTypeCode e : CertTypeCode.values()) {
			if (e.getDescription().indexOf(keywords) >= 0) {
				ctcs.add(e);
			}
		}
		return ctcs;
	}

	private CertTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
