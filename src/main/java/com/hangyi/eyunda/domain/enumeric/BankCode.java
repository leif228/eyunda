package com.hangyi.eyunda.domain.enumeric;

public enum BankCode {
	EYUNDA("EYUNDA", "平安银行见证宝", "/img/bank2/eyunda.png", "/img/bank2/eyunda.jpg"),

	ICBCB2C("102100099996", "中国工商银行", "/img/bank2/icbc.png", "/img/bank2/icbc.jpg"),

	ABC("103100000026", "中国农业银行", "/img/bank2/abc.png", "/img/bank2/abc.jpg"),

	BOCB2C("104100000004", "中国银行", "/img/bank2/boc.png", "/img/bank2/boc.jpg"),

	CCB("105100000017", "中国建设银行", "/img/bank2/ccb.png", "/img/bank2/ccb.jpg"),

	COMM("301290000007", "交通银行", "/img/bank2/bocom.png", "/img/bank2/bocom.jpg"),

	CMB("308584000013", "招商银行", "/img/bank2/zsyh.png", "/img/bank2/zsyh.jpg"),

	CITIC("302100011000", "中信银行", "/img/bank2/cncb.png", "/img/bank2/cncb.jpg"),

	SPABANK("307584007998", "平安银行", "/img/bank2/pab.png", "/img/bank2/pab.jpg"),

	CIB("309391000011", "兴业银行", "/img/bank2/cib.png", "/img/bank2/cib.jpg"),

	SPDB("310290000013", "浦发银行", "/img/bank2/spdb.png", "/img/bank2/spdb.jpg"),

	CEBBANK("303100000006", "中国光大银行", "/img/bank2/ceb.png", "/img/bank2/ceb.jpg"),

	CMBC("305100000013", "中国民生银行", "/img/bank2/cmbc.png", "/img/bank2/cmbc.jpg"),

	POSTGC("403100000004", "中国邮政储蓄银行", "/img/bank2/psbc.png", "/img/bank2/psbc.jpg"),

	BJBANK("313100000013", "北京银行", "/img/bank2/bjyh.png", "/img/bank2/bjyh.jpg"),

	SHBANK("325290000012", "上海银行", "/img/bank2/bos.png", "/img/bank2/bos.jpg"),

	HXB("304100040000", "华夏银行", "/img/bank2/hxb.png", "/img/bank2/hxb.jpg"),

	GDB("306581000003", "广发银行", "/img/bank2/gfyh.png", "/img/bank2/gfyh.jpg");

	private String code;
	private String description;
	private String iconShort;
	private String icon;

	public static BankCode getByCode(String code) {
		for (BankCode e : BankCode.values())
			if (e.getCode().equals(code))
				return e;

		return null;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getIconShort() {
		return iconShort;
	}

	public String getIcon() {
		return icon;
	}

	private BankCode(String code, String description, String iconShort, String icon) {
		this.code = code;
		this.description = description;
		this.iconShort = iconShort;
		this.icon = icon;
	}
}
