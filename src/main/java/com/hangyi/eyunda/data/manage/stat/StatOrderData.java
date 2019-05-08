package com.hangyi.eyunda.data.manage.stat;

import com.hangyi.eyunda.data.BaseData;

public class StatOrderData extends BaseData {

	private static final long serialVersionUID = 1L;

	private String yearMonth = ""; // 年月
	private Integer sumOrderCount = 0; // 合同数
	private Double sumTransFee = 0.00D; // 交易金额
	private Double sumBrokerFee = 0.00D; // 代人理佣金
	private Double sumPlatFee = 0.00D; // 平台服务费

	private int ym = 0;

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Integer getSumOrderCount() {
		return sumOrderCount;
	}

	public void setSumOrderCount(Integer sumOrderCount) {
		this.sumOrderCount = sumOrderCount;
	}

	public Double getSumTransFee() {
		return sumTransFee;
	}

	public void setSumTransFee(Double sumTransFee) {
		this.sumTransFee = sumTransFee;
	}

	public Double getSumBrokerFee() {
		return sumBrokerFee;
	}

	public void setSumBrokerFee(Double sumBrokerFee) {
		this.sumBrokerFee = sumBrokerFee;
	}

	public Double getSumPlatFee() {
		return sumPlatFee;
	}

	public void setSumPlatFee(Double sumPlatFee) {
		this.sumPlatFee = sumPlatFee;
	}

	public int getYm() {
		return ym;
	}

	public void setYm(int ym) {
		this.ym = ym;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
