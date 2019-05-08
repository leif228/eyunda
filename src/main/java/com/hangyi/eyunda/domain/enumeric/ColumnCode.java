package com.hangyi.eyunda.domain.enumeric;

public enum ColumnCode {
	GSJJ("1", "公司简介", "公司", 1, 0, ColumnPageCode.alink, ColumnPageCode.index),

	YWFW("2", "业务范围", "业务", 2, 0, ColumnPageCode.images, ColumnPageCode.page),

	CBXX("3", "船舶信息", "船舶", 2, 1, ColumnPageCode.images, ColumnPageCode.ship),

	HWXX("4", "货物信息", "货物", 2, 1, ColumnPageCode.images, ColumnPageCode.cargo),

	CGAL("5", "成功案例", "案例", 2, 1, ColumnPageCode.tables, ColumnPageCode.page),

	WXZL("6", "文献资料", "文献", 2, 1, ColumnPageCode.tables, ColumnPageCode.article),

	HTTW("7", "合同条文", "合同", 2, 0, ColumnPageCode.tables, ColumnPageCode.article),

	ZZRY("8", "资质荣誉", "资质", 2, 0, ColumnPageCode.images, ColumnPageCode.bigImage),

	LXWM("9", "联系我们", "联系", 1, 0, ColumnPageCode.alink, ColumnPageCode.callus);

	private String code;
	private String description;
	private String shortName;
	private int layer = 1;// 1.单页,2.列表加详情
	private int pager = 0;// 0.不分页,1.分页.只针对layer=2的情况
	private ColumnPageCode listPage = ColumnPageCode.images;
	private ColumnPageCode contentPage = ColumnPageCode.page;

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getShortName() {
		return shortName;
	}

	public int getLayer() {
		return layer;
	}

	public int getPager() {
		return pager;
	}

	public ColumnPageCode getListPage() {
		return listPage;
	}

	public ColumnPageCode getContentPage() {
		return contentPage;
	}

	private ColumnCode(String code, String description, String shortName, int layer, int pager, ColumnPageCode listPage,
			ColumnPageCode contentPage) {
		this.code = code;
		this.description = description;
		this.shortName = shortName;
		this.layer = layer;
		this.pager = pager;
		this.listPage = listPage;
		this.contentPage = contentPage;
	}

}
