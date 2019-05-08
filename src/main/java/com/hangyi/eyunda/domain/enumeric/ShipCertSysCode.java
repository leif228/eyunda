package com.hangyi.eyunda.domain.enumeric;

public enum ShipCertSysCode {
	//apply("申请", "申请"), approve("批准", "好友"), reject("驳回", "拒绝");
	hyq("航运圈"),qcy("请船易");

	private String description;

	private ShipCertSysCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}


}
