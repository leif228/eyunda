package com.hangyi.eyunda.domain.enumeric;

public enum ApplyReplyCode {
	noapply("未申请退款", "不退款"),
	apply("申请退款", "申请退款"),
	reply("同意退款", "已退款"),
	noreply("拒绝退款", "退款失败");

	private String description;
	private String remark;

	private ApplyReplyCode(String description, String remark) {
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
