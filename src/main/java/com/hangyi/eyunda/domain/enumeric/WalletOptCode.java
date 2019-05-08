package com.hangyi.eyunda.domain.enumeric;

public enum WalletOptCode {
	pay("支付"), 
	fetch("提现"), 
	delete("删除"), 
	confirmPay("确认付款"), 
	applyRefund("申请退款"), 
	refund("退款处理"), 
	masterfee("交易金额"), 
	brokerfee("代理人佣金"), 
	platfee("平台服务费");

	private String description;

	private WalletOptCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
