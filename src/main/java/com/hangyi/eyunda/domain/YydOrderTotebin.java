package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydOrderTotebin")
public class YydOrderTotebin extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long orderId = 0L; // 合同ID
	@Column(nullable = true, length = 8)
	private String startPortNo = ""; // 装货港
	@Column(nullable = true, length = 8)
	private String endPortNo = ""; // 卸货港
	@Column(nullable = false, length = 100)
	private String cargoNames = ""; // 规格
	@Column(nullable = false, length = 100)
	private String teus = ""; // 箱量(个)
	@Column(nullable = false, length = 100)
	private String prices = ""; // 运价(元/个)

	@Column
	private Double worth = 0.00D; // 价值(元)
	@Column
	private Double demurrage = 0.00D; // 滞期费率(元/天)

	@Column
	private Calendar upDate = Calendar.getInstance(); // 装货日期
	@Column
	private Calendar downDate = Calendar.getInstance(); // 卸货日期

	@Column
	private Integer upDay = 0; // 允许装货时间（天）
	@Column
	private Integer downDay = 0; // 允许卸货时间（天）
	@Column
	private Integer upHour = 0; // 允许装货时间（小时）
	@Column
	private Integer downHour = 0; // 允许卸货时间（小时）
	@Column(nullable = false, length = 200)
	private String cargoDesc = ""; // 货物细节说明
	@Column(nullable = false, length = 200)
	private String payDesc = ""; // 支付结算说明

	/**
	 * 取得合同ID
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * 设置合同ID
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * 取得装货港
	 */
	public String getStartPortNo() {
		return startPortNo;
	}

	/**
	 * 设置装货港
	 */
	public void setStartPortNo(String startPortNo) {
		this.startPortNo = startPortNo;
	}

	/**
	 * 取得卸货港
	 */
	public String getEndPortNo() {
		return endPortNo;
	}

	/**
	 * 设置卸货港
	 */
	public void setEndPortNo(String endPortNo) {
		this.endPortNo = endPortNo;
	}

	/**
	 * 取得规格
	 */
	public String getCargoNames() {
		return cargoNames;
	}

	/**
	 * 设置规格
	 */
	public void setCargoNames(String cargoNames) {
		this.cargoNames = cargoNames;
	}

	/**
	 * 取得箱量(个)
	 */
	public String getTeus() {
		return teus;
	}

	/**
	 * 设置箱量(个)
	 */
	public void setTeus(String teus) {
		this.teus = teus;
	}

	/**
	 * 取得运价(元/个)
	 */
	public String getPrices() {
		return prices;
	}

	/**
	 * 设置运价(元/个)
	 */
	public void setPrices(String prices) {
		this.prices = prices;
	}

	public Double getWorth() {
		return worth;
	}

	public void setWorth(Double worth) {
		this.worth = worth;
	}

	/**
	 * 取得滞期费率(元/天)
	 */
	public Double getDemurrage() {
		return demurrage;
	}

	/**
	 * 设置滞期费率(元/天)
	 */
	public void setDemurrage(Double demurrage) {
		this.demurrage = demurrage;
	}

	/**
	 * 取得装货日期
	 */
	public Calendar getUpDate() {
		return upDate;
	}

	/**
	 * 设置装货日期
	 */
	public void setUpDate(Calendar upDate) {
		this.upDate = upDate;
	}

	/**
	 * 取得卸货日期
	 */
	public Calendar getDownDate() {
		return downDate;
	}

	/**
	 * 设置卸货日期
	 */
	public void setDownDate(Calendar downDate) {
		this.downDate = downDate;
	}

	public Integer getUpDay() {
		return upDay;
	}

	public void setUpDay(Integer upDay) {
		this.upDay = upDay;
	}

	public Integer getDownDay() {
		return downDay;
	}

	public void setDownDay(Integer downDay) {
		this.downDay = downDay;
	}

	public Integer getUpHour() {
		return upHour;
	}

	public void setUpHour(Integer upHour) {
		this.upHour = upHour;
	}

	public Integer getDownHour() {
		return downHour;
	}

	public void setDownHour(Integer downHour) {
		this.downHour = downHour;
	}

	public String getCargoDesc() {
		return cargoDesc;
	}

	public void setCargoDesc(String cargoDesc) {
		this.cargoDesc = cargoDesc;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

}
