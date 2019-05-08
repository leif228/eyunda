package com.hangyi.eyunda.domain.enumeric;

public enum ArvlftCode {
	arrive("到达", "自从", "卸货"), left("离开", "驶往", "装货");

	private String description;
	private String remark;
	private String updown;

	private ArvlftCode(String description, String remark, String updown) {
		this.description = description;
		this.remark = remark;
		this.updown = updown;
	}

	public String getDescription() {
		return description;
	}

	public String getRemark() {
		return remark;
	}

	public String getUpdown() {
		return updown;
	}
}
