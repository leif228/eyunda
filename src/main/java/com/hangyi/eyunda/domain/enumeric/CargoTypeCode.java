package com.hangyi.eyunda.domain.enumeric;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum CargoTypeCode {
	container20e("集装箱.20’吉箱", CargoBigTypeCode.container),

	container20f("集装箱.20’重箱", CargoBigTypeCode.container),

	container40e("集装箱.40’吉箱", CargoBigTypeCode.container),

	container40f("集装箱.40’重箱", CargoBigTypeCode.container),

	container45e("集装箱.45’吉箱", CargoBigTypeCode.container),

	container45f("集装箱.45’重箱", CargoBigTypeCode.container),

	coal("散杂货.煤炭", CargoBigTypeCode.bulk),

	metalmineral("散杂货.矿石", CargoBigTypeCode.bulk),

	steel("散杂货.钢铁", CargoBigTypeCode.bulk),

	metal("散杂货.有色金属", CargoBigTypeCode.bulk),

	tile("散杂货.建材", CargoBigTypeCode.bulk),

	wood("散杂货.木材", CargoBigTypeCode.bulk),

	cement("散杂货.水泥", CargoBigTypeCode.bulk),

	manure("散杂货.化肥农药", CargoBigTypeCode.bulk),

	salt("散杂货.盐", CargoBigTypeCode.bulk),

	food("散杂货.粮食", CargoBigTypeCode.bulk),

	machinery("散杂货.机械电器", CargoBigTypeCode.bulk),

	chemicals("散杂货.化工", CargoBigTypeCode.bulk),

	industry("散杂货.轻工医药", CargoBigTypeCode.bulk),

	industrial("散杂货.日用品", CargoBigTypeCode.bulk),

	farming("散杂货.农林牧渔", CargoBigTypeCode.bulk),

	cotton("散杂货.棉花", CargoBigTypeCode.bulk),

	explosives("危险品.爆炸品", CargoBigTypeCode.danger),

	liquefied("危险品.液化气体", CargoBigTypeCode.danger),

	flammable("危险品.易燃液体", CargoBigTypeCode.danger),

	flammablesolids("危险品.易燃固体", CargoBigTypeCode.danger),

	oxidizing("危险品.氧化剂", CargoBigTypeCode.danger),

	poisons("危险品.毒害品", CargoBigTypeCode.danger),

	radioactive("危险品.放射性物品", CargoBigTypeCode.danger),

	corrosives("危险品.腐蚀品", CargoBigTypeCode.danger),

	miscellaneous("危险品.杂类", CargoBigTypeCode.danger);

	private String description;
	private CargoBigTypeCode cargoBigType;

	public static CargoTypeCode valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}

	public static List<CargoTypeCode> getCodesByCargoBigType(CargoBigTypeCode cbtc) {
		Map<Integer, CargoTypeCode> map = new TreeMap<Integer, CargoTypeCode>();
		for (CargoTypeCode e : CargoTypeCode.values()) {
			if (e.getCargoBigType() == cbtc) {
				map.put(e.ordinal(), e);
			}
		}

		List<CargoTypeCode> results = new ArrayList<CargoTypeCode>();
		for (Integer key : map.keySet()) {
			results.add(map.get(key));
		}

		return results;
	}

	public static CargoTypeCode getByDescription(String description) {
		for (CargoTypeCode e : CargoTypeCode.values()) {
			if (e.getDescription() == description) {
				return e;
			}
		}
		return null;
	}

	public String getDescription() {
		return description;
	}

	public static CargoTypeCode getByShortName(String shortName) {
		for (CargoTypeCode e : CargoTypeCode.values()) {
			if (e.getShortName().equals(shortName)) {
				return e;
			}
		}
		return null;
	}

	public String getShortName() {
		switch (this) {
		case container20e:
			return "20’E";
		case container20f:
			return "20’F";
		case container40e:
			return "40’E";
		case container40f:
			return "40’F";
		case container45e:
			return "45’E";
		case container45f:
			return "45’F";
		default:
			return description.substring(description.indexOf(".") + 1);
		}
	}

	public String getShortDesc() {
		return description.substring(description.indexOf(".") + 1);
	}

	public CargoBigTypeCode getCargoBigType() {
		return cargoBigType;
	}

	private CargoTypeCode(String description, CargoBigTypeCode cargoBigType) {
		this.description = description;
		this.cargoBigType = cargoBigType;
	}
}
