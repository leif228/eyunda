package com.hangyi.eyunda.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EnumConst {

	public enum MobileMenuCode {
		MY_SHIP("01", "船舶登记", "icon-barcode", "/mobile/ship"),

		MY_SHIP_STATE("02", "动态上报", "icon-move", "/mobile/state"),

		MY_SHIP_MONITOR("03", "船舶监控", "icon-screenshot", "/mobile/monitor"),

		MY_CABIN("12", "船盘发布", "icon-th-large", "/mobile/cabin"),

		MY_CARGO("04", "货盘发布", "icon-th-large", "/mobile/cargo"),

		MY_ORDER("05", "合同", "icon-file", "/mobile/order"),
		
		// MY_BUYWARES("13", "购买订单", "icon-file", "/mobile/buywares"),

		MY_SETTLE("06", "钱包", "icon-pencil", "/mobile/settle"),

		MY_MESSAGE("07", "消息", "icon-envelope", "/mobile/message"),

		MY_CONTACT("08", "联系人", "icon-glass", "/mobile/contact"),

		MY_ACCOUNT("11", "设置", "icon-user", "/mobile/account");

		private String menuid;
		private String menuname;
		private String icon;
		private String url;

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

		private MobileMenuCode(String menuid, String menuname, String icon, String url) {
			this.menuid = menuid;
			this.menuname = menuname;
			this.icon = icon;
			this.url = url;
		}
	}

	public enum SpaceMenuCode {
		MY_SHIP("01", "船舶登记", "icon-barcode", "/space/ship/myShip"),

		MY_SHIP_STATE("02", "动态上报", "icon-move", "/space/state/myShip"),

		MY_SHIP_MONITOR("03", "船舶监控", "icon-screenshot", "/space/monitor/myAllShip/shipDistributoin"),

		MY_CABIN("04", "船盘发布", "icon-th-large", "/space/cabin/myCabin"),
		
		MY_CARGO("05", "货盘发布", "icon-th-large", "/space/cargo/cargoList"),

		MY_ORDER("06", "合同", "icon-file", "/space/orderCommon/orderList"),

		MY_WALLET("07", "钱包", "icon-pencil", "/space/wallet/myWallet"),

		MY_CONTACT("08", "联系人", "icon-glass", "/space/contact/myContact"),

		MY_RELEASE("09", "信息发布", "icon-flag", "/space/release/myRelease"),

		MY_ACCOUNT("10", "设置", "icon-user", "/space/account/myAccount");

		private String menuid;
		private String menuname;
		private String icon;
		private String url;

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

		private SpaceMenuCode(String menuid, String menuname, String icon, String url) {
			this.menuid = menuid;
			this.menuname = menuname;
			this.icon = icon;
			this.url = url;
		}
	}

	public enum MenuCode {
		POWER("", "01", "权限管理", "", ""),

		POWER_ADMIN("01", "0101", "管理员", "", "/manage/power/admin"),

		POWER_ROLE("01", "0102", "角色", "", "/manage/power/role"),

		POWER_MODULE("01", "0103", "模块", "", "/manage/power/module"),

		POWER_LOG("01", "0104", "用户日志", "", "/manage/power/userLoginLogs"),

		POWER_WORDS("01", "0105", "过滤词", "", "/manage/power/filterWords"),

		POWER_HOLIDAY("01", "0106", "节假日管理", "", "/manage/power/holidayList"),

		MEMBER("", "02", "会员管理", "", ""),

		MEMBER_OPERATOR("02", "0201", "实名认证", "", "/manage/member/operator"),

		MEMBER_USER("02", "0202", "会员", "", "/manage/member/user"),

		MEMBER_COMPCERT("02", "0203", "公司认证", "", "/manage/member/compCert/list"),

		MEMBER_HYQUSER("02", "0204", "航运圈会员", "", "/manage/member/hyqmember/list"),
		
		APP("", "04", "服务管理", "", ""),

		APP_APP("04", "0401", "服务", "", "/manage/app/appList"),

		SHIP("", "03", "船舶管理", "", ""),

		SHIP_TYPE("03", "0301", "船类", "", "/manage/ship/type"),

		SHIP_ATTRIBUTE("03", "0302", "船舶类别属性", "", "/manage/ship/attribute"),

		SHIP_PORT("03", "0303", "港口", "", "/manage/ship/port"),

		SHIP_QUERY("03", "0304", "船舶", "", "/manage/ship/ship"),

		SHIP_SAILLINE("03", "0305", "航线", "", "/manage/ship/sailLine"),

		SHIP_UPDOWNPORT("03", "0306", "装卸港", "", "/manage/ship/upDownPort"),

		SHIP_CABIN("03", "0307", "船盘", "", "/manage/ship/manageCabin"),

		SHIP_CARGO("03", "0308", "货盘", "", "/manage/ship/manageCargo"),
		
		ORDER("", "05", "合同管理", "", ""),

		ORDER_TEMPLATE("05", "0501", "合同模板", "", "/manage/order/orderTemplate"),

		ORDER_QUERY("05", "0502", "合同", "", "/manage/orderCommon/orderCommon"),

		ORDER_COMPLAIN("05", "0504", "投诉建议", "", "/manage/complain/complainInfo"),

		WALLET("", "06", "钱包管理", "", ""),

		WALLET_QUERY("06", "0601", "帐务查询", "", "/manage/wallet/walletQuery"),

		WALLET_CHECK("06", "0602", "会员对账", "", "/manage/wallet/getUsersSettle"),

		WALLET_PLAT("06", "0603", "平台服务费", "", "/manage/wallet/walletPlat"),
		
		STAT("", "07", "统计分析", "", ""),

		STAT_LOGIN("07", "0701", "登录统计", "", "/manage/stat/statLogin"),

		STAT_SHIPCALL("07", "0702", "船舶访问统计", "", "/manage/stat/statShipCall"),

		STAT_USER("07", "0703", "会员统计", "", "/manage/stat/statUser"),

		STAT_SHIP("07", "0704", "船舶统计", "", "/manage/stat/statShip"),

		STAT_ORDER("07", "0705", "合同统计", "", "/manage/stat/orderStatList"),
		
		NOTICE("", "08", "信息发布", "", ""),
		
		NOTICE_NOTICE("08", "0801", "信息发布", "", "/manage/notice/notice");

		public static Map<MenuCode, List<MenuCode>> getLayerMenuMap() {
			Map<MenuCode, List<MenuCode>> menuMap = new LinkedHashMap<MenuCode, List<MenuCode>>();

			menuMap.put(POWER, MenuCode.getChildren(POWER.getMenuid()));
			menuMap.put(MEMBER, MenuCode.getChildren(MEMBER.getMenuid()));
			menuMap.put(APP, MenuCode.getChildren(APP.getMenuid()));
			menuMap.put(SHIP, MenuCode.getChildren(SHIP.getMenuid()));
			menuMap.put(ORDER, MenuCode.getChildren(ORDER.getMenuid()));
			menuMap.put(WALLET, MenuCode.getChildren(WALLET.getMenuid()));
			menuMap.put(STAT, MenuCode.getChildren(STAT.getMenuid()));
			menuMap.put(NOTICE, MenuCode.getChildren(NOTICE.getMenuid()));

			return menuMap;
		}

		public static List<MenuCode> getChildren(String menupid) {
			List<MenuCode> list = new ArrayList<MenuCode>();

			for (MenuCode e : MenuCode.values())
				if (e.getMenupid().equals(menupid))
					list.add(e);

			return list;
		}

		public static List<MenuCode> getAllLeaf() {
			List<MenuCode> list = new ArrayList<MenuCode>();

			for (MenuCode e : MenuCode.values())
				if (!"".equals(e.getMenupid()))
					list.add(e);

			return list;
		}

		public static MenuCode getEnumByUrl(String url) {
			for (MenuCode e : MenuCode.values())
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

		private MenuCode(String menupid, String menuid, String menuname, String icon, String url) {
			this.menupid = menupid;
			this.menuid = menuid;
			this.menuname = menuname;
			this.icon = icon;
			this.url = url;
		}
	}

	public enum BackMenuCode {
		POWER("", "01", "权限管理", "", ""),

		POWER_ADMIN("01", "0101", "管理员", "", "/back/power/admin"),

		POWER_ROLE("01", "0102", "角色", "", "/back/power/role"),

		POWER_MODULE("01", "0103", "模块", "", "/back/power/module"),

		POWER_LOG("01", "0104", "用户日志", "", "/back/power/userLoginLogs"),

		POWER_WORDS("01", "0105", "过滤词", "", "/back/power/filterWords"),

		POWER_HOLIDAY("01", "0106", "节假日管理", "", "/back/power/holidayList"),

		MEMBER("", "02", "会员管理", "", ""),

		MEMBER_COMPCERT("02", "0203", "公司认证", "", "/back/member/compCert/list"),

		MEMBER_HYQUSER("02", "0204", "航运圈会员", "", "/back/member/hyqmember/list"),
		
		APP("", "04", "服务管理", "", ""),

		APP_APP("04", "0401", "服务", "", "/back/app/appList"),

		SHIP("", "03", "船舶管理", "", ""),

		SHIP_TYPE("03", "0301", "船类", "", "/back/ship/type"),

		SHIP_ATTRIBUTE("03", "0302", "船舶类别属性", "", "/back/ship/attribute"),

		SHIP_PORT("03", "0303", "港口", "", "/back/ship/port"),

		SHIP_QUERY("03", "0304", "船舶", "", "/back/ship/ship"),

		SHIP_SAILLINE("03", "0305", "航线", "", "/back/ship/sailLine"),

		SHIP_UPDOWNPORT("03", "0306", "装卸港", "", "/back/ship/upDownPort"),

		SHIP_CABIN("03", "0307", "船盘", "", "/back/ship/manageCabin"),

		SHIP_CARGO("03", "0308", "货盘", "", "/back/ship/manageCargo"),
		
		SHIP_CERTIFICATE("03", "0309", "证书管理", "", "/back/ship/certificate"),

		ORDER("", "05", "合同管理", "", ""),

		ORDER_TEMPLATE("05", "0501", "合同模板", "", "/back/order/orderTemplate"),

		ORDER_QUERY("05", "0502", "合同", "", "/back/orderCommon/orderCommon"),

		ORDER_COMPLAIN("05", "0504", "投诉建议", "", "/back/complain/complainInfo"),

		WALLET("", "06", "钱包管理", "", ""),

		WALLET_QUERY("06", "0601", "帐务查询", "", "/back/wallet/walletQuery"),

		WALLET_CHECK("06", "0602", "会员对账", "", "/back/wallet/getUsersSettle"),

		WALLET_PLAT("06", "0603", "平台服务费", "", "/back/wallet/walletPlat"),
		
		STAT("", "07", "统计分析", "", ""),

		STAT_LOGIN("07", "0701", "登录统计", "", "/back/stat/statLogin"),

		STAT_SHIPCALL("07", "0702", "船舶访问统计", "", "/back/stat/statShipCall"),

		STAT_USER("07", "0703", "会员统计", "", "/back/stat/statUser"),

		STAT_SHIP("07", "0704", "船舶统计", "", "/back/stat/statShip"),

		STAT_ORDER("07", "0705", "合同统计", "", "/back/stat/orderStatList"),
		
		NOTICE("", "08", "信息发布", "", ""),
		
		NOTICE_NOTICE("08", "0801", "信息发布", "", "/back/notice/notice");

		public static Map<BackMenuCode, List<BackMenuCode>> getLayerMenuMap() {
			Map<BackMenuCode, List<BackMenuCode>> menuMap = new LinkedHashMap<BackMenuCode, List<BackMenuCode>>();

			menuMap.put(POWER, BackMenuCode.getChildren(POWER.getMenuid()));
			menuMap.put(MEMBER, BackMenuCode.getChildren(MEMBER.getMenuid()));
			menuMap.put(APP, BackMenuCode.getChildren(APP.getMenuid()));
			menuMap.put(SHIP, BackMenuCode.getChildren(SHIP.getMenuid()));
			menuMap.put(ORDER, BackMenuCode.getChildren(ORDER.getMenuid()));
			menuMap.put(WALLET, BackMenuCode.getChildren(WALLET.getMenuid()));
			menuMap.put(STAT, BackMenuCode.getChildren(STAT.getMenuid()));
			menuMap.put(NOTICE, BackMenuCode.getChildren(NOTICE.getMenuid()));

			return menuMap;
		}

		public static List<BackMenuCode> getChildren(String menupid) {
			List<BackMenuCode> list = new ArrayList<BackMenuCode>();

			for (BackMenuCode e : BackMenuCode.values())
				if (e.getMenupid().equals(menupid))
					list.add(e);

			return list;
		}

		public static List<BackMenuCode> getAllLeaf() {
			List<BackMenuCode> list = new ArrayList<BackMenuCode>();

			for (BackMenuCode e : BackMenuCode.values())
				if (!"".equals(e.getMenupid()))
					list.add(e);

			return list;
		}

		public static BackMenuCode getEnumByUrl(String url) {
			for (BackMenuCode e : BackMenuCode.values())
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

		private BackMenuCode(String menupid, String menuid, String menuname, String icon, String url) {
			this.menupid = menupid;
			this.menuid = menuid;
			this.menuname = menuname;
			this.icon = icon;
			this.url = url;
		}
	}

	public enum RecentPeriodCode {
		THREE_DAYS("0", "三日内", 3),

		ONE_WEEK("1", "一周内", 7),

		HALF_MONTH("2", "半月内", 15),

		ONE_MONTH("3", "一个月内", 30),

		TWO_MONTH("4", "二个月内", 60),

		THREE_MONTH("5", "三个月内", 90),

		HALF_YEAR("6", "半年内", 180),

		ONE_YEAR("7", "一年内", 360);

		private String code;
		private String description;
		private Integer totalDays;

		public static RecentPeriodCode getByCode(String code) {
			for (RecentPeriodCode e : RecentPeriodCode.values())
				if (e.getCode().equals(code))
					return e;

			return null;
		}

		public static RecentPeriodCode getByTotalDays(Integer totalDays) {
			for (RecentPeriodCode e : RecentPeriodCode.values())
				if (e.getTotalDays().equals(totalDays))
					return e;

			return null;
		}

		public String getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}

		public Integer getTotalDays() {
			return totalDays;
		}

		private RecentPeriodCode(String code, String description, Integer totalDays) {
			this.code = code;
			this.description = description;
			this.totalDays = totalDays;
		}
	}

	public enum ShipSortCode {
		POINTER("1", "人气"),

		ORDER("2", "销量"),

		TIME("3", "最新"),

		PRICE("4", "报价"),

		TONS("5", "载货量");

		private String code;
		private String description;

		public static ShipSortCode getByCode(String code) {
			for (ShipSortCode e : ShipSortCode.values())
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

		private ShipSortCode(String code, String description) {
			this.code = code;
			this.description = description;
		}
	}

}
