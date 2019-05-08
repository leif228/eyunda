package com.hangyi.eyunda.data.order;

import com.hangyi.eyunda.data.BaseData;

public class OrderSignData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID
	private Long orderId = 0L; // 合同ID
	private Long userId = 0L; // 签字用户ID
	private String trueName = ""; // 姓名
	private String unitName = ""; // 公司名称
	private String signature = ""; // 个性签名
	private String stamp = ""; // 图章
	private String signContent = ""; // 签字内容
	private String createTime = ""; // 签字时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public String getSignContent() {
		return signContent;
	}

	public void setSignContent(String signContent) {
		this.signContent = signContent;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
