package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.EvalTypeCode;

@Entity
@Table(name = "YydOrderEvaluate")
public class YydOrderEvaluate extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long orderId = 0L; // 合同ID
	@Column
	private Long shipId = 0L; // 船舶ID
	@Column
	private Long userId = 0L; // 评价用户ID
	@Enumerated(EnumType.ORDINAL)
	private EvalTypeCode evalType = EvalTypeCode.ok; // 评价类型
	@Column(nullable = false, length = 500)
	private String evalContent = ""; // 评价内容
	@Column
	private Calendar createTime = Calendar.getInstance(); // 评价时间

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

	/**
	 * 取得评价用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置评价用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 取得评价类型
	 */
	public EvalTypeCode getEvalType() {
		return evalType;
	}

	/**
	 * 设置评价类型
	 */
	public void setEvalType(EvalTypeCode evalType) {
		this.evalType = evalType;
	}

	
	public String getEvalContent() {
		return evalContent;
	}

	public void setEvalContent(String evalContent) {
		this.evalContent = evalContent;
	}

	/**
	 * 取得评价时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置评价时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

}
