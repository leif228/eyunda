package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Table(name = "YydException")
public class YydException extends BaseEntity {
private static final long serialVersionUID = -1L;

	@Column
	private Integer exceptionNo = 0; // 错误编号
	@Column(nullable = false, length = 200)
	private String className = ""; // 类的名称
	@Column(nullable = false, length = 200)
	private String method = ""; // 方法名称
	@Column(nullable = true, length = 200)
	private String description = ""; // 错误描述
	@Column
	private Integer count = 0; // 出错次数

	/**
	 * 取得错误编号
	 */
	public Integer getExceptionNo() {
		return exceptionNo;
	}
	/**
	 * 设置错误编号
	 */
	public void setExceptionNo(Integer exceptionNo) {
		this.exceptionNo = exceptionNo;
	}

	/**
	 * 取得类的名称
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 设置类的名称
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * 取得方法名称
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 设置方法名称
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 取得错误描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置错误描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 取得出错次数
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置出错次数
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

}
