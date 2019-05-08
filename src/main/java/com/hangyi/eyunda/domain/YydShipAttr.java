package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydShipAttr")
public class YydShipAttr extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 50)
	private String shipCode = ""; // 船舶编码
	@Column(nullable = false, length = 50)
	private String attrNameCode = ""; // 属性名编码
	@Column(nullable = false, length = 50)
	private String attrValue = ""; // 属性值

	/**
	 * 取得船舶编码
	 */
	public String getShipCode() {
		return shipCode;
	}

	/**
	 * 设置船舶编码
	 */
	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
	}

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

}
