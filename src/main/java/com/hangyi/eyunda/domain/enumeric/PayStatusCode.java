package com.hangyi.eyunda.domain.enumeric;

public enum PayStatusCode {
	WAIT_PAYMENT("未支付", "未提现", "未充值"),

	WAIT_CONFIRM("资金托管", "", ""),

	TRADE_FINISHED("确认付款", "提现完成", "充值完成"),

	TRADE_CLOSED("支付失败", "提现退单", "充值失败");
	
	private String description;
	private String remark;
	private String remark2;

	private PayStatusCode(String description, String remark, String remark2) {
		this.description = description;
		this.remark = remark;
		this.remark2 = remark2;
	}

	public String getDescription() {
		return description;
	}

	public String getRemark() {
		return remark;
	}

	public String getRemark2() {
		return remark2;
	}
}
