package com.hangyi.eyunda.service;

import com.hangyi.eyunda.util.DateUtils;

public class ShareDirService {
	public static final String FILE_SEPA_SIGN = "/";

	private static final String MANAGE_DIR = "/manage";
	private static final String USER_DIR = "/user";
	private static final String SHIP_DIR = "/ship";
	private static final String ORDER_DIR = "/order";
	private static final String CARGO_DIR = "/cargo";
	private static final String WARES_DIR = "/wares";
	private static final String COMPANY_DIR = "/company";
	private static final String CHAT_DIR = "/chat";
	private static final String NEWS_DIR = "/news";
	private static final String ADVERT_DIR = "/advert";
	private static final String BUG_DIR = "/bug";
	private static final String QCODE_DIR = "/qcode";

	public static String getManageDir() {
		return MANAGE_DIR;
	}

	public static String getUserDir(Long userId) {
		String uId = Long.toString(userId);

		return USER_DIR + getTypeDir(uId);
	}

	public static String getOrderDir(Long orderId) {
		String oId = Long.toString(orderId);

		return ORDER_DIR + getTypeDir(oId);
	}

	public static String getCargoDir(Long userId) {
		String uId = Long.toString(userId);

		return CARGO_DIR + getTypeDir(uId);
	}

	public static String getGasWaresDir(Long companyId) {
		String cId = Long.toString(companyId);

		return WARES_DIR + getTypeDir(cId);
	}

	public static String getGasCompanyDir(Long companyId) {
		String cId = Long.toString(companyId);

		return COMPANY_DIR + getTypeDir(cId);
	}

	public static String getShipDir(Long shipId) {
		String cId = Long.toString(shipId);

		return SHIP_DIR + getTypeDir(cId);
	}

	public static String getShipDir(String mmsi) {
		boolean b = true;

		if (mmsi == null || "".equals(mmsi))
			b = false;

		if (b) {
			return SHIP_DIR + getTypeDir(mmsi);
		} else {
			// 防止误删除根目录
			return FILE_SEPA_SIGN + "THIS_DIR_IS_NOT_EXIST";
		}
	}

	public static String getChatDir(String room) {
		if (room != null && !"".equals(room)) {
			return CHAT_DIR + getTypeDir(room);
		} else {
			// 防止误删除根目录
			return FILE_SEPA_SIGN + "THIS_DIR_IS_NOT_EXIST";
		}
	}

	public static String getNewsDir(String newsId) {
		if (newsId != null && !"".equals(newsId)) {
			return NEWS_DIR + FILE_SEPA_SIGN + newsId;
		} else {
			// 防止误删除根目录
			return FILE_SEPA_SIGN + "THIS_DIR_IS_NOT_EXIST";
		}
	}

	public static String getAdvertDir(String advertId) {
		if (advertId != null && !"".equals(advertId)) {
			return ADVERT_DIR + FILE_SEPA_SIGN + advertId;
		} else {
			// 防止误删除根目录
			return FILE_SEPA_SIGN + "THIS_DIR_IS_NOT_EXIST";
		}
	}

	public static String getFileName(String prefix, String ext) {
		if (prefix != null && !"".equals(prefix))
			if (prefix.endsWith("-"))
				prefix = prefix.substring(0, prefix.length() - 1);

		return prefix + "-" + getFileName(ext);
	}

	public static String getFileName(String ext) {
		String fileName = DateUtils.getTime("yyyyMMddHHmmss");

		int randomNum = (int) (Math.random() * 9000 + 1000);
		fileName += randomNum;

		if (ext != null && !"".equals(ext)) {
			if (ext.startsWith(".")) {
				ext = ext.substring(1);
			}
			fileName += "." + ext;
		}

		return fileName;
	}

	private static String getTypeDir(String typeCode) {// "123456"
														// -----"/12/34/56"
		String level = "";

		if (typeCode != null && !"".equals(typeCode)) {
			while (typeCode.length() > 0) {
				if (typeCode.length() > 2) {
					level += FILE_SEPA_SIGN + typeCode.substring(0, 2);
					typeCode = typeCode.substring(2, typeCode.length());
				} else {
					level += FILE_SEPA_SIGN + typeCode;
					typeCode = "";
				}
			}
		} else {
			// 防止误删除根目录
			level = FILE_SEPA_SIGN + "THIS_DIR_IS_NOT_EXIST";
		}

		return level;
	}

	public static String getBugDir() {
		return BUG_DIR;
	}

	public static String getQcodeDir(Long userId) {
		String uId = Long.toString(userId);
		return QCODE_DIR + getTypeDir(uId);
	}

	public static String getLoginQcodeDir(String time) {
		return QCODE_DIR + "/" + time;
	}
}
