package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydTypeAttrValue")
public class YydTypeAttrValue extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 50)
	private String attrValueCode = ""; // 属性值编码
	@Column(nullable = false, length = 100)
	private String attrValue = ""; // 属性值描述

	/**
	 * 取得属性值编码
	 */
	public String getAttrValueCode() {
		return attrValueCode;
	}

	/**
	 * 设置属性值编码
	 */
	public void setAttrValueCode(String attrValueCode) {
		this.attrValueCode = attrValueCode;
	}

	/**
	 * 取得属性值描述
	 */
	public String getAttrValue() {
		return attrValue;
	}

	/**
	 * 设置属性值描述
	 */
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

}
