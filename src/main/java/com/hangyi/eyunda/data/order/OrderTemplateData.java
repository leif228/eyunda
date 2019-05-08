package com.hangyi.eyunda.data.order;

import java.util.Calendar;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

public class OrderTemplateData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L;
	private OrderTypeCode orderType = OrderTypeCode.voyage_gaxjzx_container20e; // 合同类别编码
	private String title = ""; // 模板标题
	private String orderContent = ""; // 特约条款
	private Long operatorId; // 代理人BrokerId,0表示公有
	private UserData operator; // 代理人Broker
	private Calendar createTime = Calendar.getInstance(); // 建立时间
	private ReleaseStatusCode releaseStatus = null; // 发布状态
	private Calendar releaseTime = Calendar.getInstance(); // 发布时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	 * 取得发表者
	 */
	public UserData getOperator() {
		return operator;
	}

	/**
	 * 设置发表者
	 */
	public void setOperator(UserData operator) {
		this.operator = operator;
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
