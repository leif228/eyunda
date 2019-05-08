package com.hangyi.eyunda.domain.enumeric;

public enum BuyOilStatusCode {
	edit("未支付"),
	payment("已支付"),
	confirmpay("已确认付款"),
	refundapply("退款申请"),
	refund("已退款"),
	filloil("交易完成");

	private String description;

	private BuyOilStatusCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
