package com.hangyi.eyunda.domain.enumeric;

public enum RefundStatusCode {
	WAIT_SELLER_AGREE("退款协议等待卖家确认中", "退款申请"),

	SELLER_REFUSE_BUYER("卖家不同意协议，等待买家修改", "不用"),

	WAIT_BUYER_RETURN_GOODS("退款协议达成，等待买家退货", "不用"),

	WAIT_SELLER_CONFIRM_GOODS("等待卖家收货", "退款中"),

	REFUND_SUCCESS("退款成功", "已退款"),

	REFUND_CLOSED("退款关闭", "退款失败");

	private String description;
	private String remark;

	private RefundStatusCode(String description, String remark) {
		this.description = description;
		this.remark = remark;
	}

	public String getDescription() {
		return description;
	}

	public String getRemark() {
		return remark;
	}
}
