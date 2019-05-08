package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.BulkTypeCode;

@Entity
@Table(name = "YydOrderNoship")
public class YydOrderNoship extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long orderId = 0L; // 合同ID
	@Column(nullable = true, length = 8)
	private String startPortNo = ""; // 装货港
	@Column(nullable = true, length = 8)
	private String endPortNo = ""; // 卸货港
	@Enumerated(EnumType.ORDINAL)
	private BulkTypeCode cargoType = null; // 货类
	@Column(nullable = false, length = 50)
	private String cargoName = ""; // 货名
	@Column
	private Double ton = 0.00D; // 重量
	@Column
	private Double worth = 0.00D; // 价值
	@Column
	private Double price = 0.00D; // 运价(元/吨)
	@Column
	private Calendar startDate = Calendar.getInstance(); // 开始运输日期
	@Column
	private Calendar endDate = Calendar.getInstance(); // 结束运输日期
	@Column(nullable = false, length = 200)
	private String calcDesc = ""; // 运费计算说明
	@Column(nullable = false, length = 200)
	private String payDesc = ""; // 支付结算说明
	@Column(nullable = false, length = 200)
	private String cargoDesc = ""; // 货物交接说明

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
	public BulkTypeCode getCargoType() {
		return cargoType;
	}

	/**
	 * 设置货类
	 */
	public void setCargoType(BulkTypeCode cargoType) {
		this.cargoType = cargoType;
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
	 * 取得运价(元/吨)
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * 设置运价(元/吨)
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * 取得开始运输日期
	 */
	public Calendar getStartDate() {
		return startDate;
	}

	/**
	 * 设置开始运输日期
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	/**
	 * 取得结束运输日期
	 */
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * 设置结束运输日期
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public String getCalcDesc() {
		return calcDesc;
	}

	public void setCalcDesc(String calcDesc) {
		this.calcDesc = calcDesc;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

	public String getCargoDesc() {
		return cargoDesc;
	}

	public void setCargoDesc(String cargoDesc) {
		this.cargoDesc = cargoDesc;
	}

}
