package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydType")
public class YydType extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 20)
	private String typeCode = ""; // 类别编码
	@Column(nullable = false, length = 20)
	private String typeName = ""; // 类别名称

	/**
	 * 取得类别编码
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * 设置类别编码
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * 取得类别名称
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * 设置类别名称
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
