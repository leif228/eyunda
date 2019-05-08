package com.hangyi.eyunda.domain.enumeric;

public enum WalletSettleCode {
	fill("充值"),

	fetch("提现"),

	pay("担保支付"),

	refund("退款"),

	backfetch("提现退单"),

	masterfee("合同收款"),

	brokerfee("代理人佣金"),

	platfee("平台服务费"),

	handfee("取现手续费"),

	receivefee("直接收款"),

	bonus("红包");

	private String description;

	private WalletSettleCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
