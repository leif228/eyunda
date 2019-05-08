package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.AttrTypeCode;

@Entity
@Table(name = "YydTypeAttrName")
public class YydTypeAttrName extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 50)
	private String attrNameCode = ""; // 属性名编码
	@Column(nullable = false, length = 100)
	private String attrName = ""; // 属性名描述
	@Enumerated(EnumType.ORDINAL)
	private AttrTypeCode attrType = null; // 属性类型

	/**
	 * 取得属性名编码
	 */
	public String getAttrNameCode() {
		return attrNameCode;
	}

	/**
	 * 设置属性名编码
	 */
	public void setAttrNameCode(String attrNameCode) {
		this.attrNameCode = attrNameCode;
	}

	/**
	 * 取得属性名描述
	 */
	public String getAttrName() {
		return attrName;
	}

	/**
	 * 设置属性名描述
	 */
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	/**
	 * 取得属性类型
	 */
	public AttrTypeCode getAttrType() {
		return attrType;
	}

	/**
	 * 设置属性类型
	 */
	public void setAttrType(AttrTypeCode attrType) {
		this.attrType = attrType;
	}

}
