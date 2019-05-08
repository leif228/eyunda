package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "YydBidInfo")
public class YydBidInfo extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long inviteId = 0L; // 招标ID
	@Column
	private Long bidderId = 0L; // 竞标人ID
	@Column
	private Double price = 0.00D; // 出价
	@Column
	private Calendar createTime = Calendar.getInstance(); // 发布时间
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode payStatus = null; // 支付保证金
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode refundStatus = null; // 退款
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode succStatus = null; // 中标

	/**
	 * 取得招标ID
	 */
	public Long getInviteId() {
		return inviteId;
	}

	/**
	 * 设置招标ID
	 */
	public void setInviteId(Long inviteId) {
		this.inviteId = inviteId;
	}

	/**
	 * 取得竞标人ID
	 */
	public Long getBidderId() {
		return bidderId;
	}

	/**
	 * 设置竞标人ID
	 */
	public void setBidderId(Long bidderId) {
		this.bidderId = bidderId;
	}

	/**
	 * 取得出价
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * 设置出价
	 */
	public void setPrice(Double price) {
		this.price = price;
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

	/**
	 * 取得中标
	 */
	public YesNoCode getSuccStatus() {
		return succStatus;
	}

	/**
	 * 设置中标
	 */
	public void setSuccStatus(YesNoCode succStatus) {
		this.succStatus = succStatus;
	}

}
