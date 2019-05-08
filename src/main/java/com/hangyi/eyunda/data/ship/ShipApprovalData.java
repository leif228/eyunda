package com.hangyi.eyunda.data.ship;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.EvalTypeCode;

public class ShipApprovalData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long orderId = 0L; // 合同ID
	private Long shipId = 0L; // 船舶ID
	private Long userId = 0L; // 评价用户ID
	private EvalTypeCode evalType = EvalTypeCode.ok; // 评价类型
	private String evalContent = ""; // 评价内容
	private String createTime = ""; // 评价时间

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getShipId() {
		return shipId;
	}

	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public EvalTypeCode getEvalType() {
		return evalType;
	}

	public void setEvalType(EvalTypeCode evalType) {
		this.evalType = evalType;
	}

	public String getEvalContent() {
		return evalContent;
	}

	public void setEvalContent(String evalContent) {
		this.evalContent = evalContent;
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
