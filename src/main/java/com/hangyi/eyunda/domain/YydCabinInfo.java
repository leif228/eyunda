package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;

@Entity
@Table(name = "YydCabinInfo", indexes = { @Index(name = "idx_shipId", columnList = "shipId", unique = false),
		@Index(name = "idx_publisherId", columnList = "publisherId", unique = false) })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydCabinInfo extends BaseEntity {

	private static final long serialVersionUID = -1L;

	@Enumerated(EnumType.ORDINAL)
	private WaresBigTypeCode waresBigType = null; // 船盘大类
	@Enumerated(EnumType.ORDINAL)
	private WaresTypeCode waresType = null; // 船盘分类
	@Enumerated(EnumType.ORDINAL)
	private CargoTypeCode cargoType = null; // 接货类
	@Column
	private Long shipId = 0L; // 船舶ID
	@Column
	private Integer containerCount = 0; // 载箱量TEU
	@Column
	private Calendar startDate = Calendar.getInstance(); // 受载日期
	@Column(nullable = false, length = 8000)
	private String ports = ""; // 期租接货港及载重量
	@Column(nullable = false, length = 64000)
	private String prices = ""; // 航租航线报价及载重量
	@Column
	private Double oilPrice = 0.00D; // 燃油费计算
	@Column
	private Double demurrage = 0.00D; // 滞期费率
	@Column
	private Integer paySteps = 0; // 支付分期
	@Column(nullable = false, length = 4000)
	private String orderDesc = ""; // 特约条款
	@Enumerated(EnumType.ORDINAL)
	private ReleaseStatusCode status = null; // 发布状态
	@Column
	private Long publisherId = 0L; // 发布人ID
	@Column
	private Long brokerId = 0L; // 代理人ID
	@Column
	private Long masterId = 0L; // 船东ID
	@Column
	private Calendar createTime = Calendar.getInstance(); // 修改时间

	/**
	 * 取得船盘大类
	 */
	public WaresBigTypeCode getWaresBigType() {
		return waresBigType;
	}

	/**
	 * 设置船盘大类
	 */
	public void setWaresBigType(WaresBigTypeCode waresBigType) {
		this.waresBigType = waresBigType;
	}

	/**
	 * 取得船盘分类
	 */
	public WaresTypeCode getWaresType() {
		return waresType;
	}

	/**
	 * 设置船盘分类
	 */
	public void setWaresType(WaresTypeCode waresType) {
		this.waresType = waresType;
	}

	/**
	 * 取得船舶ID
	 */
	public Long getShipId() {
		return shipId;
	}

	/**
	 * 设置船舶ID
	 */
	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}

	public Integer getContainerCount() {
		return containerCount;
	}

	public void setContainerCount(Integer containerCount) {
		this.containerCount = containerCount;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	/**
	 * 取得接货港
	 */
	public String getPorts() {
		return ports;
	}

	/**
	 * 设置接货港
	 */
	public void setPorts(String ports) {
		this.ports = ports;
	}

	/**
	 * 取得接货类
	 */
	public CargoTypeCode getCargoType() {
		return cargoType;
	}

	/**
	 * 设置接货类
	 */
	public void setCargoType(CargoTypeCode cargoType) {
		this.cargoType = cargoType;
	}

	/**
	 * 取得运费报价
	 */
	public String getPrices() {
		return prices;
	}

	/**
	 * 设置运费报价
	 */
	public void setPrices(String prices) {
		this.prices = prices;
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

	/**
	 * 取得支付分期
	 */
	public Integer getPaySteps() {
		return paySteps;
	}

	/**
	 * 设置支付分期
	 */
	public void setPaySteps(Integer paySteps) {
		this.paySteps = paySteps;
	}

	/**
	 * 取得特约条款
	 */
	public String getOrderDesc() {
		return orderDesc;
	}

	/**
	 * 设置特约条款
	 */
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	/**
	 * 取得发布状态
	 */
	public ReleaseStatusCode getStatus() {
		return status;
	}

	/**
	 * 设置发布状态
	 */
	public void setStatus(ReleaseStatusCode status) {
		this.status = status;
	}

	/**
	 * 取得发布人ID
	 */
	public Long getPublisherId() {
		return publisherId;
	}

	/**
	 * 设置发布人ID
	 */
	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public Long getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(Long brokerId) {
		this.brokerId = brokerId;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

}
