package com.hangyi.eyunda.domain.enumeric;

public enum WrapTypeCode {
	machinery("机械电器"),

	chemicals("化工"),

	industry("轻工医药"),

	industrial("日用品"),

	farming("农林牧渔"),

	cotton("棉花");

	private String description;

	public String getDescription() {
		return description;
	}

	public String getShortDesc() {
		return description;
	}

	private WrapTypeCode(String description) {
		this.description = description;
	}
}
