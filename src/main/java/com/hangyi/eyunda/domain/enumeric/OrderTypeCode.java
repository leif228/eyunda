package com.hangyi.eyunda.domain.enumeric;

import java.util.Map;
import java.util.TreeMap;

public enum OrderTypeCode {
	voyage_gaxjzx_container20e("航租-港澳线集装箱", WaresBigTypeCode.voyage, WaresTypeCode.gaxjzx, CargoTypeCode.container20e),

	voyage_nmjzx_container20e("航租-内贸集装箱", WaresBigTypeCode.voyage, WaresTypeCode.nmjzx, CargoTypeCode.container20e),

	voyage_nmszh_coal("航租-内贸散杂货-煤炭", WaresBigTypeCode.voyage, WaresTypeCode.nmszh, CargoTypeCode.coal),

	voyage_nmszh_steel("航租-内贸散杂货-钢铁", WaresBigTypeCode.voyage, WaresTypeCode.nmszh, CargoTypeCode.steel),

	voyage_nmszh_tile("航租-内贸散杂货-建材", WaresBigTypeCode.voyage, WaresTypeCode.nmszh, CargoTypeCode.tile),

	voyage_nmszh_cement("航租-内贸散杂货-水泥", WaresBigTypeCode.voyage, WaresTypeCode.nmszh, CargoTypeCode.cement),

	daily_gaxjzx_container20e("期租-港澳线集装箱", WaresBigTypeCode.daily, WaresTypeCode.gaxjzx, CargoTypeCode.container20e),

	daily_nmjzx_container20e("期租-内贸集装箱", WaresBigTypeCode.daily, WaresTypeCode.nmjzx, CargoTypeCode.container20e),

	daily_nmszh_coal("期租-内贸散杂货-煤炭", WaresBigTypeCode.daily, WaresTypeCode.nmszh, CargoTypeCode.coal),

	daily_nmszh_steel("期租-内贸散杂货-钢铁", WaresBigTypeCode.daily, WaresTypeCode.nmszh, CargoTypeCode.steel),

	daily_nmszh_tile("期租-内贸散杂货-建材", WaresBigTypeCode.daily, WaresTypeCode.nmszh, CargoTypeCode.tile),

	daily_nmszh_cement("期租-内贸散杂货-水泥", WaresBigTypeCode.daily, WaresTypeCode.nmszh, CargoTypeCode.cement);

	private String description;
	private WaresBigTypeCode waresBigType;
	private WaresTypeCode waresType;
	private CargoTypeCode cargoType;

	private OrderTypeCode(String description, WaresBigTypeCode waresBigType, WaresTypeCode waresType,
			CargoTypeCode cargoType) {
		this.description = description;
		this.waresBigType = waresBigType;
		this.waresType = waresType;
		this.cargoType = cargoType;
	}

	public static OrderTypeCode[] getCodesByWaresBigType(WaresBigTypeCode wbtc) {
		Map<Integer, OrderTypeCode> map = new TreeMap<Integer, OrderTypeCode>();
		for (OrderTypeCode e : OrderTypeCode.values()) {
			if (e.getWaresBigType() == wbtc) {
				map.put(e.ordinal(), e);
			}
		}

		OrderTypeCode[] arrOrderType = new OrderTypeCode[map.size()];
		int n = 0;
		for (Integer key : map.keySet()) {
			arrOrderType[n++] = map.get(key);
		}

		return arrOrderType;
	}

	public static OrderTypeCode getCodeByType(WaresBigTypeCode wbt, WaresTypeCode wt, CargoTypeCode ct) {
		for (OrderTypeCode e : OrderTypeCode.values()) {
			if (e.getWaresBigType() == wbt && e.getWaresType() == wt && e.getCargoType() == ct) {
				return e;
			}
		}
		return voyage_gaxjzx_container20e;
	}

	public String getDescription() {
		return description;
	}

	public WaresBigTypeCode getWaresBigType() {
		return waresBigType;
	}

	public WaresTypeCode getWaresType() {
		return waresType;
	}

	public CargoTypeCode getCargoType() {
		return cargoType;
	}

}
