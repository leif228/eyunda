package com.hangyi.eyunda.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 使用正则表达式删除HTML标签
 */
public class MyHtmlSpirit {

	/**
	 * 使用正则表达式不区分大小写模式替换 所有HTML和样式 及SCRIPT标签
	 */
	public static String delAllHTMLTag(String htmlStr) {
		if (htmlStr == null) {
			return null;
		}
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);// 不区分大小写模式替换
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 使用正则表达式不区分大小写模式替换及SCRIPT标签
	 */
	public static String delScriptTag(String htmlStr) {
		if (htmlStr == null) {
			return null;
		}
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);// 不区分大小写模式替换
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 测试实例
	 */
	public static void main(String[] args) {
		// 如果区分大小写，就是把AabcAaB中的a替换成G
		// 如果不去分大小写，就是把AabcAaB中的a和A都替换成G
		replaceString("AabcAaB", "a", "G");
		replaceStringP("AabcAaB", "a", "G");
		replaceString("<sCript type=\"text/javascript\">", "script", "*");
	}

	public static void replaceString(String source, String oldstring,
			String newstring) {
		System.out.println("原来的字符串：" + source);

		String result1 = source.replaceAll("(?i)" + oldstring, newstring); // 大小写不敏感
		System.out.println("不区分大小写的替换结果：" + result1);

		String result2 = source.replaceAll(oldstring, newstring);// 大小写敏感
		System.out.println("区分大小写的替换结果：" + result2);
	}

	// 使用正则表达式实现不区分大小写替换
	public static void replaceStringP(String source, String oldstring,
			String newstring) {
		Matcher m = Pattern.compile(oldstring, Pattern.CASE_INSENSITIVE)
				.matcher(source);
		String result = m.replaceAll(newstring);
		System.out.println("使用正则表达式不区分大小写的替换结果" + result);

		Matcher m1 = Pattern.compile(oldstring, Pattern.CANON_EQ).matcher(
				source);
		String result1 = m1.replaceAll(newstring);
		System.out.println("使用正则表达式区分大小写的替换结果" + result1);
	}

	/**
	 * 判断字符串的编码
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}
}
