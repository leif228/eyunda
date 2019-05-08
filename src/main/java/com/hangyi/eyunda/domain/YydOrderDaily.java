package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydOrderDaily")
public class YydOrderDaily extends BaseEntity {
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
	private Integer days = 0; // 租期天数
	@Column
	private Double price = 0.00D; // 租金率
	@Column
	private Double oilPrice = 0.00D; // 燃油费计算

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
	 * 取得租期天数
	 */
	public Integer getDays() {
		return days;
	}

	/**
	 * 设置租期天数
	 */
	public void setDays(Integer days) {
		this.days = days;
	}

	/**
	 * 取得租金率
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * 设置租金率
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * 取得燃油费计算
	 */
	public Double getOilPrice() {
		return oilPrice;
	}

	/**
	 * 设置燃油费计算
	 */
	public void setOilPrice(Double oilPrice) {
		this.oilPrice = oilPrice;
	}

}
