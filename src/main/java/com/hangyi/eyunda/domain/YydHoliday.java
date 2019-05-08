package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydHoliday")
public class YydHoliday extends BaseEntity {
	private static final long serialVersionUID = -1L;
	
	@Column(nullable = false, length = 20)
	private String holidayName = ""; // 节假日名称
	@Column
	private Calendar startDate = Calendar.getInstance(); // 节假日开始时间
	@Column
	private Calendar endDate = Calendar.getInstance(); // 节假日结束时间
	
	/**
	 * 获取节假日名称
	 */
	public String getHolidayName() {
		return holidayName;
	}
	
	/**
	 * 设置节假日名称
	 */
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	
	/**
	 * 获取节假日开始时间
	 */
	public Calendar getStartDate() {
		return startDate;
	}
	
	/**
	 * 设置节假日开始时间
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	/**
	 * 获取节假日结束时间
	 */
	public Calendar getEndDate() {
		return endDate;
	}
	
	/**
	 * 设置节假日结束时间
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
}
