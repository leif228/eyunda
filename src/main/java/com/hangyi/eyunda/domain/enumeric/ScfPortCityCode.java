package com.hangyi.eyunda.domain.enumeric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScfPortCityCode {
	GUANGZHOU("11", "广州", ScfBigAreaCode.ZHUSANJIAO, "4401", "12"),

	SHENZHEN("12", "深圳", ScfBigAreaCode.ZHUSANJIAO, "4403", "68"),

	ZHUHAI("13", "珠海", ScfBigAreaCode.ZHUSANJIAO, "4404", null),

	FOSHAN("14", "佛山", ScfBigAreaCode.ZHUSANJIAO, "4406", "16"),

	DONGGUAN("15", "东莞", ScfBigAreaCode.ZHUSANJIAO, "4419", "11"),

	ZHONGSHAN("16", "中山", ScfBigAreaCode.ZHUSANJIAO, "4420", "11"),

	JIANGMEN("17", "江门", ScfBigAreaCode.ZHUSANJIAO, "4407", null),

	ZHAOQING("18", "肇庆", ScfBigAreaCode.ZHUSANJIAO, "4412", "14"),

	YUNFU("19", "云浮", ScfBigAreaCode.ZHUSANJIAO, "4453", "18"),

	HUIZHOU("20", "惠州", ScfBigAreaCode.ZHUSANJIAO, "4413", "15"),

	SANTOU("21", "汕头", ScfBigAreaCode.ZHUSANJIAO, "4405", null),

	QINGUAN("22", "清远", ScfBigAreaCode.ZHUSANJIAO, "4418", "14"),

	ZHANJIANG("23", "湛江", ScfBigAreaCode.ZHUSANJIAO, "4408", null),

	YANGJIANG("27", "阳江", ScfBigAreaCode.ZHUSANJIAO, "4408", null),

	FANFUJIAN("24", "福建", ScfBigAreaCode.ZHUSANJIAO, "35", null),

	FANGUANGXI("25", "广西", ScfBigAreaCode.ZHUSANJIAO, "45", null),

	FANHAINAN("26", "海南", ScfBigAreaCode.ZHUSANJIAO, "46", null),

	SHANGHAI("31", "上海", ScfBigAreaCode.CHANGSANJIAO, "31", null),

	JIANGSU("32", "江苏", ScfBigAreaCode.CHANGSANJIAO, "32", null),

	ANHUI("33", "安徽", ScfBigAreaCode.CHANGSANJIAO, "34", null),

	JIANGXI("34", "江西", ScfBigAreaCode.CHANGSANJIAO, "36", null),

	HUBEI("35", "湖北", ScfBigAreaCode.CHANGSANJIAO, "42", null),

	HUNAN("36", "湖南", ScfBigAreaCode.CHANGSANJIAO, "42", null),

	SICHUAN("37", "四川", ScfBigAreaCode.CHANGSANJIAO, "51", null),

	JINGHANG("41", "京杭运河", ScfBigAreaCode.CHANGSANJIAO, "", null),

	HUAIHE("42", "淮河", ScfBigAreaCode.CHANGSANJIAO, "", null),

	DONGBEI("61", "辽宁", ScfBigAreaCode.CHINACOAST, "21", null),

	HUABEI("62", "津冀", ScfBigAreaCode.CHINACOAST, "12,13", null),

	SHANDONG("63", "山东", ScfBigAreaCode.CHINACOAST, "37", null),

	SUZHEHU("64", "苏浙沪", ScfBigAreaCode.CHINACOAST, "31,32,33", null),

	FUJIAN("65", "福建", ScfBigAreaCode.CHINACOAST, "35", null),

	YUEGUI("66", "粤桂", ScfBigAreaCode.CHINACOAST, "44,45", null),

	TAIGANGAO("68", "香港", ScfBigAreaCode.ZHUSANJIAO, "", null),

	AOMEN("69", "澳门", ScfBigAreaCode.ZHUSANJIAO, "", null),

	ASIA("71", "亚洲", ScfBigAreaCode.WORLDOCEAN, "", null),

	AMERICA("72", "美洲", ScfBigAreaCode.WORLDOCEAN, "", null),

	EUROPE("73", "欧洲", ScfBigAreaCode.WORLDOCEAN, "", null),

	AFRICA("74", "非洲", ScfBigAreaCode.WORLDOCEAN, "", null),

	OCEANIA("75", "大洋洲", ScfBigAreaCode.WORLDOCEAN, "", null);

	private String code;
	private String description;
	private String fullName;
	private ScfBigAreaCode bigArea;
	private String areaCode;
	private String nextCityCode;

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", this.ordinal());
		map.put("name", this.name());
		map.put("code", this.code);
		map.put("description", this.description);
		map.put("fullName", this.fullName);
		map.put("areaCode", this.areaCode);
		map.put("nextCityCode", this.nextCityCode);

		map.put("bigArea", this.bigArea.name());

		return map;
	}

	public static ScfPortCityCode getByCode(String code) {
		for (ScfPortCityCode e : ScfPortCityCode.values())
			if (e.getCode().equals(code))
				return e;

		return null;
	}

	public static ScfPortCityCode getByName(String name) {
		for (ScfPortCityCode e : ScfPortCityCode.values())
			if (e.name().equals(name))
				return e;

		return null;
	}

	public static ScfPortCityCode getByDescription(String description) {
		for (ScfPortCityCode e : ScfPortCityCode.values())
			if (e.getDescription().equals(description))
				return e;

		return null;
	}

	public static List<ScfPortCityCode> getPortCities(ScfBigAreaCode bigArea) {
		List<ScfPortCityCode> results = new ArrayList<ScfPortCityCode>();
		for (ScfPortCityCode e : ScfPortCityCode.values()) {
			if (e.getBigArea() == bigArea) {
				results.add(e);
			}
		}
		return results;
	}

	public static boolean isDownCity(String prevCode, String nextCode) {
		ScfPortCityCode tCity = ScfPortCityCode.getByCode(prevCode);
		while (tCity != null && tCity.getNextCityCode() != null) {
			tCity = ScfPortCityCode.getByCode(tCity.getNextCityCode());
			if (tCity != null && tCity.getCode().equals(nextCode))
				return true;
		}
		return false;
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

	public ScfBigAreaCode getBigArea() {
		return bigArea;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getNextCityCode() {
		return nextCityCode;
	}

	private ScfPortCityCode(String code, String description, ScfBigAreaCode bigArea, String areaCode,
			String nextCityCode) {
		this.code = code;
		this.description = description;
		this.bigArea = bigArea;
		this.fullName = bigArea.getDescription() + "." + description;
		this.areaCode = areaCode;
		this.nextCityCode = nextCityCode;
	}
}
