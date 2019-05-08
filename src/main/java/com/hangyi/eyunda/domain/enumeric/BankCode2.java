package com.hangyi.eyunda.domain.enumeric;

public enum BankCode2 {
	ICBC("ICBC", "工商银行", "/img/bank2/icbc.png", "/img/bank2/icbc.jpg"),

	CEB("CEB", "光大银行", "/img/bank2/ceb.png", "/img/bank2/ceb.jpg"),

	HXB("HXB", "华夏银行", "/img/bank2/hxb.png", "/img/bank2/hxb.jpg"),

	CCB("CCB", "建设银行", "/img/bank2/ccb.png", "/img/bank2/ccb.jpg"),

	BOCOM("BOCOM", "交通银行", "/img/bank2/bocom.png", "/img/bank2/bocom.jpg"),

	CMBC("CMBC", "民生银行", "/img/bank2/cmbc.png", "/img/bank2/cmbc.jpg"),

	ABC("ABC", "农业银行", "/img/bank2/abc.png", "/img/bank2/abc.jpg"),

	PAB("PAB", "平安银行", "/img/bank2/pab.png", "/img/bank2/pab.jpg"),

	SPDB("SPDB", "浦发银行", "/img/bank2/spdb.png", "/img/bank2/spdb.jpg"),

	BOS("BOS", "上海银行", "/img/bank2/bos.png", "/img/bank2/bos.jpg"),

	CIB("CIB", "兴业银行", "/img/bank2/cib.png", "/img/bank2/cib.jpg"),

	BOC("BOC", "中国银行", "/img/bank2/boc.png", "/img/bank2/boc.jpg"),

	PSBC("PSBC", "中国邮政储蓄银行", "/img/bank2/psbc.png", "/img/bank2/psbc.jpg"),

	CNCB("CNCB", "中信银行", "/img/bank2/cncb.png", "/img/bank2/cncb.jpg"),

	GDRCU("GDRCU", "广东农村信用社", "/img/bank2/gdrcu.png", "/img/bank2/gdrcu.jpg"),

	HZCB("HZCB", "杭州银行", "/img/bank2/hzcb.png", "/img/bank2/hzcb.jpg"),

	JSBK("JSBK", "江苏银行", "/img/bank2/jsbk.png", "/img/bank2/jsbk.jpg"),

	SZRCU("SZRCU", "深圳农村商业银行", "/img/bank2/szrcu.png", "/img/bank2/szrcu.jpg"),

	CBHB("CBHB", "渤海银行", "/img/bank2/cbhb.png", "/img/bank2/cbhb.jpg");

	private String code;
	private String description;
	private String icon;
	private String iconlng;

	public static BankCode2 getByCode(String code) {
		for (BankCode2 e : BankCode2.values())
			if (e.getCode().equals(code))
				return e;

		return null;
	}

	public static BankCode2 getByName(String name) {
		for (BankCode2 e : BankCode2.values())
			if (name != null && name.indexOf(e.getDescription()) >= 0)
				return e;

		return null;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getIcon() {
		return icon;
	}

	public String getIconlng() {
		return iconlng;
	}

	private BankCode2(String code, String description, String icon, String iconlng) {
		this.code = code;
		this.description = description;
		this.icon = icon;
		this.iconlng = iconlng;
	}
}
