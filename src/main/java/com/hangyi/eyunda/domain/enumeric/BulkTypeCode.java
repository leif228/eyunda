package com.hangyi.eyunda.domain.enumeric;

public enum BulkTypeCode {
	coal("煤炭"),

	metalmineral("矿石"),

	steel("钢铁"),

	metal("有色金属"),

	tile("建材"),

	wood("木材"),

	cement("水泥"),

	manure("化肥农药"),

	salt("盐"),

	food("粮食");

	private String description;

	public String getDescription() {
		return description;
	}

	public String getShortDesc() {
		return description;
	}

	private BulkTypeCode(String description) {
		this.description = description;
	}
}
