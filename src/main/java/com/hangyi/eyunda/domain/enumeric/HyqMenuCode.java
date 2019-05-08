package com.hangyi.eyunda.domain.enumeric;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum HyqMenuCode {
	POWER("", "01", "权限管理", "", ""),

	POWER_ADMIN("01", "0101", "管理员", "", "/hyqmanage/power/admin"),

	POWER_ROLE("01", "0102", "角色", "", "/hyqmanage/power/role"),

	POWER_MODULE("01", "0103", "模块", "", "/hyqmanage/power/module"),

	POWER_LOG("01", "0104", "用户日志", "", "/hyqmanage/power/log"),

	POWER_WORDS("01", "0105", "过滤词", "", "/hyqmanage/power/filterWords"),

	POWER_HOLIDAY("01", "0106", "节假日管理", "", "/hyqmanage/power/holidayList"),

	MEMBER("", "02", "会员管理", "", ""),

	MEMBER_USER("02", "0201", "会员", "", "/hyqmanage/member/userList"),

	MEMBER_COMPCERT("02", "0202", "认证公司", "", "/hyqmanage/member/compCert/list"),

	SHIP("", "03", "服务管理", "", ""),

	SHIP_PORT("03", "0301", "系统开发者", "", "/hyqmanage/system/developer"),

	SHIP_RULE("03", "0302", "系统", "", "/hyqmanage/system/system"),

	SHIP_PRICE("03", "0303", "服务提供者", "", "/hyqmanage/server/merchant"),

	SHIP_ASSIGN("03", "0304", "服务", "", "/hyqmanage/server/server"),

	WALLET("", "06", "钱包管理", "", ""),

	WALLET_QUERY("06", "0601", "帐务查询", "", "/hyqmanage/wallet/walletQuery"),

	WALLET_CHECK("06", "0602", "会员对账", "", "/hyqmanage/wallet/getUsersSettle"),

	WALLET_PLAT("06", "0603", "平台服务费", "", "/hyqmanage/wallet/walletPlat"),

	NOTICE("", "08", "信息发布", "", ""),

	NOTICE_NOTICE("08", "0801", "信息发布", "", "/hyqmanage/notice/notice");

	public static Map<HyqMenuCode, List<HyqMenuCode>> getLayerMenuMap() {
		Map<HyqMenuCode, List<HyqMenuCode>> menuMap = new LinkedHashMap<HyqMenuCode, List<HyqMenuCode>>();

		menuMap.put(POWER, HyqMenuCode.getChildren(POWER.getMenuid()));
		menuMap.put(MEMBER, HyqMenuCode.getChildren(MEMBER.getMenuid()));
		menuMap.put(SHIP, HyqMenuCode.getChildren(SHIP.getMenuid()));
		menuMap.put(WALLET, HyqMenuCode.getChildren(WALLET.getMenuid()));
		menuMap.put(NOTICE, HyqMenuCode.getChildren(NOTICE.getMenuid()));

		return menuMap;
	}

	public static List<HyqMenuCode> getChildren(String menupid) {
		List<HyqMenuCode> list = new ArrayList<HyqMenuCode>();

		for (HyqMenuCode e : HyqMenuCode.values())
			if (e.getMenupid().equals(menupid))
				list.add(e);

		return list;
	}

	public static List<HyqMenuCode> getAllLeaf() {
		List<HyqMenuCode> list = new ArrayList<HyqMenuCode>();

		for (HyqMenuCode e : HyqMenuCode.values())
			if (!"".equals(e.getMenupid()))
				list.add(e);

		return list;
	}

	public static HyqMenuCode getEnumByUrl(String url) {
		for (HyqMenuCode e : HyqMenuCode.values())
			if (e.getUrl().equals(url))
				return e;

		return null;
	}

	private String menupid;
	private String menuid;
	private String menuname;
	private String icon;
	private String url;

	public String getMenupid() {
		return menupid;
	}

	public String getMenuid() {
		return menuid;
	}

	public String getMenuname() {
		return menuname;
	}

	public String getIcon() {
		return icon;
	}

	public String getUrl() {
		return url;
	}

	private HyqMenuCode(String menupid, String menuid, String menuname, String icon, String url) {
		this.menupid = menupid;
		this.menuid = menuid;
		this.menuname = menuname;
		this.icon = icon;
		this.url = url;
	}

}
