package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.WrapTypeCode;

@Entity
@Table(name = "YydOrderWrap")
public class YydOrderWrap extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long orderId = 0L; // 合同ID
	@Column(nullable = true, length = 8)
	private String startPortNo = ""; // 装货港
	@Column(nullable = true, length = 8)
	private String endPortNo = ""; // 卸货港
	@Enumerated(EnumType.ORDINAL)
	private WrapTypeCode wrapType = null; // 货类
	@Column(nullable = false, length = 50)
	private String cargoName = ""; // 货名
	@Column(nullable = true, length = 50)
	private Integer amount = 0; // 数量
	@Column
	private Double ton = 0.00D; // 重量
	@Column
	private Double worth = 0.00D; // 价值
	@Column
	private Double price = 0.00D; // 运价(元/件)
	@Column
	private Double demurrage = 0.00D; // 滞期费率(元/吨.天)
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
	 * 取得货类
	 */
	public WrapTypeCode getWrapType() {
		return wrapType;
	}

	/**
	 * 设置货类
	 */
	public void setWrapType(WrapTypeCode wrapType) {
		this.wrapType = wrapType;
	}

	/**
	 * 取得货名
	 */
	public String getCargoName() {
		return cargoName;
	}

	/**
	 * 设置货名
	 */
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	/**
	 * 取得数量
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * 设置数量
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * 取得重量
	 */
	public Double getTon() {
		return ton;
	}

	/**
	 * 设置重量
	 */
	public void setTon(Double ton) {
		this.ton = ton;
	}

	/**
	 * 取得价值
	 */
	public Double getWorth() {
		return worth;
	}

	/**
	 * 设置价值
	 */
	public void setWorth(Double worth) {
		this.worth = worth;
	}

	/**
	 * 取得运价(元/件)
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * 设置运价(元/件)
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * 取得滞期费率(元/吨.天)
	 */
	public Double getDemurrage() {
		return demurrage;
	}

	/**
	 * 设置滞期费率(元/吨.天)
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
