package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydOrderSign")
public class YydOrderSign extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long orderId = 0L; // 合同ID
	@Column
	private Long userId = 0L; // 签字用户ID
	@Column(nullable = true, length = 200)
	private String trueName = ""; // 姓名
	@Column(nullable = true, length = 200)
	private String unitName = ""; // 公司名称
	@Column(nullable = false, length = 200)
	private String signature = ""; // 个性签名
	@Column(nullable = false, length = 200)
	private String stamp = ""; // 图章
	@Column(nullable = false, length = 500)
	private String signContent = ""; // 签字内容
	@Column
	private Calendar createTime = Calendar.getInstance(); // 签字时间

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
	 * 取得签字用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置签字用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/**
	 * 取得个性签名
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * 设置个性签名
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * 取得图章
	 */
	public String getStamp() {
		return stamp;
	}

	/**
	 * 设置图章
	 */
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	/**
	 * 取得签字内容
	 */
	public String getSignContent() {
		return signContent;
	}

	/**
	 * 设置签字内容
	 */
	public void setSignContent(String signContent) {
		this.signContent = signContent;
	}

	/**
	 * 取得签字时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置签字时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

}
