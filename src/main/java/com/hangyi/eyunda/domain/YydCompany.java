package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydCompany")
public class YydCompany extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 100)
	private String compName = ""; // 公司名称
	@Column(nullable = false, length = 100)
	private String shortName = ""; // 公司简称

	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间

	/**
	 * 取得公司名称
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * 设置公司名称
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * 取得公司简称
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * 设置公司简称
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * 取得建立时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置建立时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

}
