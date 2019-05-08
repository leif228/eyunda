package com.hangyi.eyunda.data.manage.stat;

import java.util.List;

import com.hangyi.eyunda.data.BaseData;

public class MonthStatShipCallData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String yearMonth = null; // 年月
	private List<StatShipCallData> statShipCallDatas; // 按月分组

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public List<StatShipCallData> getStatShipCallDatas() {
		return statShipCallDatas;
	}

	public void setStatShipCallDatas(List<StatShipCallData> statShipCallDatas) {
		this.statShipCallDatas = statShipCallDatas;
	}

}
