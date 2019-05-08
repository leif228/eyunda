package com.hangyi.eyunda.domain.enumeric;
//二维码扫描结果类型
public enum QCodeResultCode {
	pay("付款"), 
	fetch("收款"),
	login("登录");
	
	private String description;

	private QCodeResultCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
