package com.hangyi.eyunda.data.manage.stat;

import java.util.List;

import com.hangyi.eyunda.data.BaseData;

public class MonthStatLoginData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String yearMonth = null; // 年月
	private List<StatLoginData> monthLoginDatas; // 按月分组

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public List<StatLoginData> getMonthLoginDatas() {
		return monthLoginDatas;
	}

	public void setMonthLoginDatas(List<StatLoginData> monthLoginDatas) {
		this.monthLoginDatas = monthLoginDatas;
	}

}
