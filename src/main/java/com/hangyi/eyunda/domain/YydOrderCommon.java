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

import com.hangyi.eyunda.domain.enumeric.OrderStateCode;
import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "YydOrderCommon", indexes = { @Index(name = "idx_shipId", columnList = "shipId", unique = false),
		@Index(name = "idx_ownerId", columnList = "ownerId", unique = false),
		@Index(name = "idx_brokerId", columnList = "brokerId", unique = false),
		@Index(name = "idx_masterId", columnList = "masterId", unique = false),
		@Index(name = "idx_handlerId", columnList = "handlerId", unique = false) })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydOrderCommon extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Enumerated(EnumType.ORDINAL)
	private OrderTypeCode orderType = OrderTypeCode.voyage_gaxjzx_container20e; // 合同类别

	@Column
	private Long shipId = 0L; // 船舶ID
	@Column
	private Integer containerCount = 0; // 载箱量
	@Column
	private Integer weight = 0; // 载重量

	@Column
	private Long ownerId = 0L; // 货主
	@Column
	private Long brokerId = 0L; // 代理人
	@Column
	private Long masterId = 0L; // 船东
	@Column
	private Long handlerId = 0L; // 业务员

	@Column
	private Double transFee = 0.00D; // 合同金额（元）
	@Column
	private Double platFee = 0.00D; // 平台服务费
	@Column
	private Double brokerFee = 0.00D; // 代理费
	@Column
	private Double masterFee = 0.00D; // 承运人运费

	@Column
	private Integer paySteps = 0; // 付款分期
	@Column(nullable = false, length = 4000)
	private String orderDesc = ""; // 特约条款

	@Enumerated(EnumType.ORDINAL)
	private OrderStateCode state = OrderStateCode.edit; // 状态
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode approval = YesNoCode.no; // 是否评价
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间

	public OrderTypeCode getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderTypeCode orderType) {
		this.orderType = orderType;
	}

	public Long getShipId() {
		return shipId;
	}

	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}

	public Integer getContainerCount() {
		return containerCount;
	}

	public void setContainerCount(Integer containerCount) {
		this.containerCount = containerCount;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
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

	public Long getHandlerId() {
		return handlerId;
	}

	public void setHandlerId(Long handlerId) {
		this.handlerId = handlerId;
	}

	public Double getTransFee() {
		return transFee;
	}

	public void setTransFee(Double transFee) {
		this.transFee = transFee;
	}

	public Double getPlatFee() {
		return platFee;
	}

	public void setPlatFee(Double platFee) {
		this.platFee = platFee;
	}

	public Double getBrokerFee() {
		return brokerFee;
	}

	public void setBrokerFee(Double brokerFee) {
		this.brokerFee = brokerFee;
	}

	public Double getMasterFee() {
		return masterFee;
	}

	public void setMasterFee(Double masterFee) {
		this.masterFee = masterFee;
	}

	public Integer getPaySteps() {
		return paySteps;
	}

	public void setPaySteps(Integer paySteps) {
		this.paySteps = paySteps;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public OrderStateCode getState() {
		return state;
	}

	public void setState(OrderStateCode state) {
		this.state = state;
	}

	public YesNoCode getApproval() {
		return approval;
	}

	public void setApproval(YesNoCode approval) {
		this.approval = approval;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

}
