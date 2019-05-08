package com.hangyi.eyunda.data.manage;

import com.hangyi.eyunda.data.BaseData;

public class HolidayData extends BaseData {
	private static final long serialVersionUID = -1L;
	
	private Long id = null; // 节假日ID
	private String holidayName = ""; // 节假日名称
	private String startDate = ""; // 节假日开始时间
	private String endDate = ""; // 节假日结束时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
