package com.hangyi.eyunda.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

public class CookieUtil {

	/** 获取客户端IP地址，此方法用在proxy环境中 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-Ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		if (StringUtil.isNotBlank(ip)) {
			String[] ips = StringUtil.splitc(ip, ',');
			if (ips != null) {
				for (String tmpip : ips) {
					if (StringUtil.isBlank(tmpip))
						continue;
					tmpip = tmpip.trim();
					if (isIPAddr(tmpip))
						return tmpip;
				}
			}
		}

		return ip;
	}

	/** 判断是否为搜索引擎 */
	public static boolean isRobot(HttpServletRequest request) {
		String ua = request.getHeader("user-agent");
		if (StringUtil.isBlank(ua))
			return false;
		return (ua != null && (ua.indexOf("Baiduspider") != -1 || ua.indexOf("Googlebot") != -1
				|| ua.indexOf("sogou") != -1 || ua.indexOf("sina") != -1 || ua.indexOf("iaskspider") != -1
				|| ua.indexOf("ia_archiver") != -1 || ua.indexOf("Sosospider") != -1 || ua.indexOf("YoudaoBot") != -1
				|| ua.indexOf("yahoo") != -1 || ua.indexOf("yodao") != -1 || ua.indexOf("MSNBot") != -1
				|| ua.indexOf("spider") != -1 || ua.indexOf("Twiceler") != -1 || ua.indexOf("Sosoimagespider") != -1
				|| ua.indexOf("naver.com/robots") != -1 || ua.indexOf("Nutch") != -1 || ua.indexOf("spider") != -1));
	}

	/** 获取COOKIE */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;
		for (Cookie ck : cookies) {
			if (StringUtil.equalsIgnoreCase(name.split(""), ck.getName().split("")))
				return ck;
		}
		return null;
	}

	/** 获取COOKIE值 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;
		for (Cookie ck : cookies) {
			if (StringUtil.equalsIgnoreCase(name.split(""), ck.getName().split(""))) {
				String value = ck.getValue();
				if (value == null || "".equals(value))
					return null;
				else
					return value;
			}
		}
		return null;
	}

	/** 设置COOKIE */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
			int maxAge) {
		setCookie(request, response, name, value, maxAge, true);
	}

	/** 设置COOKIE */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
			int maxAge, boolean all_sub_domain) {
		Cookie cookie = new Cookie(name, value);
		// cookie.setHttpOnly(true);
		cookie.setMaxAge(maxAge);
		if (all_sub_domain) {
			String serverName = request.getServerName();
			String domain = getDomainOfServerName(serverName);
			if (domain != null && domain.indexOf('.') != -1) {
				cookie.setDomain('.' + domain);
			}
		}
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/** 刪除COOKIE */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		setCookie(request, response, name, "", 0, true);
	}

	/** 刪除COOKIE */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name,
			boolean all_sub_domain) {
		setCookie(request, response, name, "", 0, all_sub_domain);
	}

	/**
	 * 获取用户访问URL中的根域名 例如: www.dlog.cn -> dlog.cn
	 */
	public static String getDomainOfServerName(String host) {
		if (isIPAddr(host))
			return null;
		String[] names = StringUtil.splitc(host, '.');
		int len = names.length;
		if (len == 1)
			return null;
		if (len == 3) {
			return makeup(names[len - 2], names[len - 1]);
		}
		if (len > 3) {
			String dp = names[len - 2];
			if (dp.equalsIgnoreCase("com") || dp.equalsIgnoreCase("gov") || dp.equalsIgnoreCase("net")
					|| dp.equalsIgnoreCase("edu") || dp.equalsIgnoreCase("org"))
				return makeup(names[len - 3], names[len - 2], names[len - 1]);
			else
				return makeup(names[len - 2], names[len - 1]);
		}
		return host;
	}

	/** 判断字符串是否是一个IP地址 */
	public static boolean isIPAddr(String addr) {
		if (StringUtil.isEmpty(addr))
			return false;
		String[] ips = StringUtil.splitc(addr, '.');
		if (ips.length != 4)
			return false;
		try {
			int ipa = Integer.parseInt(ips[0]);
			int ipb = Integer.parseInt(ips[1]);
			int ipc = Integer.parseInt(ips[2]);
			int ipd = Integer.parseInt(ips[3]);
			return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0 && ipc <= 255 && ipd >= 0 && ipd <= 255;
		} catch (Exception e) {
		}
		return false;
	}

	private static String makeup(String... ps) {
		StringBuilder s = new StringBuilder();
		for (int idx = 0; idx < ps.length; idx++) {
			if (idx > 0)
				s.append('.');
			s.append(ps[idx]);
		}
		return s.toString();
	}

}
