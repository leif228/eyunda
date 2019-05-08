package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

@Entity
@Table(name = "YydOrderTemplate")
public class YydOrderTemplate extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Enumerated(EnumType.ORDINAL)
	private OrderTypeCode orderType = OrderTypeCode.voyage_gaxjzx_container20e; // 合同类别编码
	@Column(nullable = false, length = 50)
	private String title = ""; // 模板标题
	@Column(nullable = false, length = 4000)
	private String orderContent = ""; // 特约条款
	@Column(nullable = false, length = 32)
	private Long operatorId; // 代理人BrokerId,0表示公有
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间
	@Enumerated(EnumType.ORDINAL)
	private ReleaseStatusCode releaseStatus = null; // 发布状态
	@Column
	private Calendar releaseTime = Calendar.getInstance(); // 发布时间

	/**
	 * 取得合同类别编码
	 */
	public OrderTypeCode getOrderType() {
		return orderType;
	}

	/**
	 * 设置合同类别编码
	 */
	public void setOrderType(OrderTypeCode orderType) {
		this.orderType = orderType;
	}

	/**
	 * 取得模板标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置模板标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 取得合同模板特约条款
	 */
	public String getOrderContent() {
		return orderContent;
	}

	/**
	 * 设置合同模板特约条款
	 */
	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}

	/**
	 * 取得发表者ID
	 */
	public Long getOperatorId() {
		return operatorId;
	}

	/**
	 * 设置发表者ID
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * 取得建立时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置建立时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得发布状态
	 */
	public ReleaseStatusCode getReleaseStatus() {
		return releaseStatus;
	}

	/**
	 * 设置发布状态
	 */
	public void setReleaseStatus(ReleaseStatusCode releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	/**
	 * 取得发布时间
	 */
	public Calendar getReleaseTime() {
		return releaseTime;
	}

	/**
	 * 设置发布时间
	 */
	public void setReleaseTime(Calendar releaseTime) {
		this.releaseTime = releaseTime;
	}

}
