package com.hangyi.eyunda.data.manage.stat;

import com.hangyi.eyunda.data.BaseData;

public class StatShipData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String yearMonth = ""; // 年月
	private Integer upShips = 0; // 新上线船舶
	private Integer downShips = 0; // 新下线船舶
	private Integer sumWares = 0; // 总上线船舶
	private int ym = 0;

	public int getYm() {
		return ym;
	}

	public void setYm(int ym) {
		this.ym = ym;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Integer getUpShips() {
		return upShips;
	}

	public void setUpShips(Integer upShips) {
		this.upShips = upShips;
	}

	public Integer getDownShips() {
		return downShips;
	}

	public void setDownShips(Integer downShips) {
		this.downShips = downShips;
	}

	public Integer getSumWares() {
		return sumWares;
	}

	public void setSumWares(Integer sumWares) {
		this.sumWares = sumWares;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
