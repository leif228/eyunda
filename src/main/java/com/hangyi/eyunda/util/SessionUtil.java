package com.hangyi.eyunda.util;

import java.util.Date;
import java.util.Random;

public class SessionUtil {
	public static String getNewSessionId(Long userId, String OldSessionId) {
		if (OldSessionId == null || "".equals(OldSessionId))
			return "";

		String newSessionId = MD5.toMD5(userId + "_" + OldSessionId + "_" + (new Date().getTime()) + "_"
				+ Constants.SALT_VALUE);
		return newSessionId;
	}

	public static String getFirstSessionId(Long userId) {
		Random r = new Random();
		String newSessionId = MD5.toMD5(userId + "_" + r.nextLong() + "_" + (new Date().getTime()) + "_"
				+ Constants.SALT_VALUE);
//		String newSessionId = MD5.toMD5(userId + "_" + Constants.SALT_VALUE);
		
		return newSessionId;
	}

	public static String getBindingCode(Long userId, String loginName ) {
		String bindingCode = MD5.toMD5(userId + "_" + loginName + "_" + Constants.SALT_VALUE);
		return bindingCode;
	}

}
