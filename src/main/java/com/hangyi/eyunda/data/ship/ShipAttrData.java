package com.hangyi.eyunda.data.ship;

import com.hangyi.eyunda.data.BaseData;

public class ShipAttrData extends BaseData {
	private static final long serialVersionUID = -1L;

	private String shipCode = ""; // 船舶编码
	private String attrNameCode = ""; // 属性值编码
	private String attrValue = "";

	public String getAttrNameCode() {
		return attrNameCode;
	}

	public void setAttrNameCode(String attrNameCode) {
		this.attrNameCode = attrNameCode;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getShipCode() {
		return shipCode;
	}

	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
