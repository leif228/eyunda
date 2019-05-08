package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;

@Entity
@Table(name = "YydOrderVoyage")
public class YydOrderVoyage extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long orderId = 0L; // 合同ID
	@Column(nullable = false, length = 8)
	private String startPortNo = ""; // 装货港
	@Column(nullable = false, length = 8)
	private String endPortNo = ""; // 卸货港
	@Column
	private Integer distance = 0; // 航程
	@Column
	private Calendar upDate = Calendar.getInstance(); // 装货日期
	@Column
	private Calendar downDate = Calendar.getInstance(); // 卸货日期
	@Enumerated(EnumType.ORDINAL)
	private CargoTypeCode cargoType = null; // 货类
	@Column(nullable = false, length = 100)
	private String cargoNames = ""; // 货名或规格
	@Column(nullable = false, length = 100)
	private String tonTeus = ""; // 重量或数量
	@Column(nullable = false, length = 100)
	private String prices = ""; // 运价
	@Column
	private Double demurrage = 0.00D; // 滞期费率

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
	 * 取得航程
	 */
	public Integer getDistance() {
		return distance;
	}

	/**
	 * 设置航程
	 */
	public void setDistance(Integer distance) {
		this.distance = distance;
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

	/**
	 * 取得货类
	 */
	public CargoTypeCode getCargoType() {
		return cargoType;
	}

	/**
	 * 设置货类
	 */
	public void setCargoType(CargoTypeCode cargoType) {
		this.cargoType = cargoType;
	}

	/**
	 * 取得货名或规格
	 */
	public String getCargoNames() {
		return cargoNames;
	}

	/**
	 * 设置货名或规格
	 */
	public void setCargoNames(String cargoNames) {
		this.cargoNames = cargoNames;
	}

	/**
	 * 取得重量或数量
	 */
	public String getTonTeus() {
		return tonTeus;
	}

	/**
	 * 设置重量或数量
	 */
	public void setTonTeus(String tonTeus) {
		this.tonTeus = tonTeus;
	}

	/**
	 * 取得运价
	 */
	public String getPrices() {
		return prices;
	}

	/**
	 * 设置运价
	 */
	public void setPrices(String prices) {
		this.prices = prices;
	}

	/**
	 * 取得滞期费率
	 */
	public Double getDemurrage() {
		return demurrage;
	}

	/**
	 * 设置滞期费率
	 */
	public void setDemurrage(Double demurrage) {
		this.demurrage = demurrage;
	}

}
