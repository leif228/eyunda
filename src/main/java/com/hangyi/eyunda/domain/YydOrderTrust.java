package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydOrderTrust")
public class YydOrderTrust extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long orderId = 0L; // 合同ID
	@Column
	private Calendar startDate = Calendar.getInstance(); // 租期开始日期
	@Column
	private Calendar endDate = Calendar.getInstance(); // 租期结束日期
	@Column
	private Calendar rcvDate = Calendar.getInstance(); // 交船日期
	@Column(nullable = false, length = 8)
	private String rcvPortNo = ""; // 交船码头
	@Column
	private Calendar retDate = Calendar.getInstance(); // 还船日期
	@Column(nullable = false, length = 8)
	private String retPortNo = ""; // 还船码头
	@Column
	private Double rentRate = 0.00D; // 租金率(元/月)
	@Column
	private Double preRent = 0.00D; // 预付租金(元)
	@Column
	private Double oilRate = 0.00D; // 油耗(公斤/小时)
	@Column
	private Double oilPrice = 0.00D; // 油价(元/吨)
	@Column(nullable = false, length = 200)
	private String shipDesc = ""; // 租船说明
	@Column(nullable = false, length = 200)
	private String oilDesc = ""; // 燃油费计算说明
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
	 * 取得租期开始日期
	 */
	public Calendar getStartDate() {
		return startDate;
	}

	/**
	 * 设置租期开始日期
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	/**
	 * 取得租期结束日期
	 */
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * 设置租期结束日期
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	/**
	 * 取得交船日期
	 */
	public Calendar getRcvDate() {
		return rcvDate;
	}

	/**
	 * 设置交船日期
	 */
	public void setRcvDate(Calendar rcvDate) {
		this.rcvDate = rcvDate;
	}

	/**
	 * 取得交船码头
	 */
	public String getRcvPortNo() {
		return rcvPortNo;
	}

	/**
	 * 设置交船码头
	 */
	public void setRcvPortNo(String rcvPortNo) {
		this.rcvPortNo = rcvPortNo;
	}

	/**
	 * 取得还船日期
	 */
	public Calendar getRetDate() {
		return retDate;
	}

	/**
	 * 设置还船日期
	 */
	public void setRetDate(Calendar retDate) {
		this.retDate = retDate;
	}

	/**
	 * 取得还船码头
	 */
	public String getRetPortNo() {
		return retPortNo;
	}

	/**
	 * 设置还船码头
	 */
	public void setRetPortNo(String retPortNo) {
		this.retPortNo = retPortNo;
	}

	/**
	 * 取得租金率(元/月)
	 */
	public Double getRentRate() {
		return rentRate;
	}

	/**
	 * 设置租金率(元/月)
	 */
	public void setRentRate(Double rentRate) {
		this.rentRate = rentRate;
	}

	/**
	 * 取得预付租金(元)
	 */
	public Double getPreRent() {
		return preRent;
	}

	/**
	 * 设置预付租金(元)
	 */
	public void setPreRent(Double preRent) {
		this.preRent = preRent;
	}

	/**
	 * 取得油耗(公斤/小时)
	 */
	public Double getOilRate() {
		return oilRate;
	}

	/**
	 * 设置油耗(公斤/小时)
	 */
	public void setOilRate(Double oilRate) {
		this.oilRate = oilRate;
	}

	/**
	 * 取得油价(元/吨)
	 */
	public Double getOilPrice() {
		return oilPrice;
	}

	/**
	 * 设置油价(元/吨)
	 */
	public void setOilPrice(Double oilPrice) {
		this.oilPrice = oilPrice;
	}

	public String getShipDesc() {
		return shipDesc;
	}

	public void setShipDesc(String shipDesc) {
		this.shipDesc = shipDesc;
	}

	public String getOilDesc() {
		return oilDesc;
	}

	public void setOilDesc(String oilDesc) {
		this.oilDesc = oilDesc;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

}
