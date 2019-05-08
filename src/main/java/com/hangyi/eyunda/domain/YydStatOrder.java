package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydStatOrder")
public class YydStatOrder extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(name = "nian_yue", nullable = false, length = 6)
	private String yearMonth = ""; // 年月
	@Column
	private Integer sumOrderCount = 0; // 合同数
	@Column
	private Double sumTransFee = 0.00D; // 交易金额
	@Column
	private Double sumBrokerFee = 0.00D; // 代理人佣金
	@Column
	private Double sumPlatFee = 0.00D; // 平台服务费

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

}
