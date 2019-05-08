package com.hangyi.eyunda.domain.enumeric;

public enum ColumnPageCode {
	alink("链接地址", ""),

	images("图片列表", "/column/images"),

	images2("图片列表", "/column/images2"),

	tables("文字列表", "/column/tables"),

	bigImage("大图及说明", "/column/bigImage"),

	article("文字内容", "/column/article"),

	page("图文详情", "/column/page"),

	ship("船舶详情", "/column/ship-info"),

	cargo("货物详情", "/column/cargo-info"),

	index("首页", "/column/index"),

	callus("联系我们", "/column/callus");

	private String description;
	private String pageUrl;

	public String getDescription() {
		return description;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	private ColumnPageCode(String description, String pageUrl) {
		this.description = description;
		this.pageUrl = pageUrl;
	}
}
