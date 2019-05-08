package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "YydInviteInfo")
public class YydInviteInfo extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long cargoId = 0L; // 货物ID
	@Column
	private Long inviterId = 0L; // 招标人ID
	@Column
	private Calendar startDate = Calendar.getInstance(); // 招标开始日期
	@Column
	private Calendar endDate = Calendar.getInstance(); // 招标截止日期
	@Column
	private Double maxPrice = 0.00D; // 最高限价
	@Column
	private Double minPrice = 0.00D; // 最低限价
	@Column
	private Double demurrage = 0.00D; // 滞期费率
	@Column
	private Integer suretyDays = 0; // 资金托管天数
	@Column(nullable = true, length = 4000)
	private String orderContent = ""; // 特约条款
	@Column
	private Calendar createTime = Calendar.getInstance(); // 发布时间
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode payStatus = null; // 支付保证金
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode refundStatus = null; // 退款

	/**
	 * 取得货物ID
	 */
	public Long getCargoId() {
		return cargoId;
	}

	/**
	 * 设置货物ID
	 */
	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	/**
	 * 取得招标人ID
	 */
	public Long getInviterId() {
		return inviterId;
	}

	/**
	 * 设置招标人ID
	 */
	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}

	/**
	 * 取得招标开始日期
	 */
	public Calendar getStartDate() {
		return startDate;
	}

	/**
	 * 设置招标开始日期
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	/**
	 * 取得招标截止日期
	 */
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * 设置招标截止日期
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	/**
	 * 取得最高限价
	 */
	public Double getMaxPrice() {
		return maxPrice;
	}

	/**
	 * 设置最高限价
	 */
	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	/**
	 * 取得最低限价
	 */
	public Double getMinPrice() {
		return minPrice;
	}

	/**
	 * 设置最低限价
	 */
	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
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
	 * 取得资金托管天数
	 */
	public Integer getSuretyDays() {
		return suretyDays;
	}

	/**
	 * 设置资金托管天数
	 */
	public void setSuretyDays(Integer suretyDays) {
		this.suretyDays = suretyDays;
	}

	/**
	 * 取得特约条款
	 */
	public String getOrderContent() {
		return orderContent;
	}

	/**
	 * 设置特约条款
	 */
	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}

	/**
	 * 取得发布时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置发布时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得支付保证金
	 */
	public YesNoCode getPayStatus() {
		return payStatus;
	}

	/**
	 * 设置支付保证金
	 */
	public void setPayStatus(YesNoCode payStatus) {
		this.payStatus = payStatus;
	}

	/**
	 * 取得退款
	 */
	public YesNoCode getRefundStatus() {
		return refundStatus;
	}

	/**
	 * 设置退款
	 */
	public void setRefundStatus(YesNoCode refundStatus) {
		this.refundStatus = refundStatus;
	}

}
