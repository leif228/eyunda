package com.hangyi.eyunda.data.ship;

import com.hangyi.eyunda.data.BaseData;

public class AttrValueData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID
	private String attrValueCode = ""; // 属性值编码
	private String attrValue = ""; // 属性值描述

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttrValueCode() {
		return attrValueCode;
	}

	public void setAttrValueCode(String attrValueCode) {
		this.attrValueCode = attrValueCode;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
