package com.hangyi.eyunda.util;

/**
 * 操作系统相关
 * 
 * @author guoqiang
 * 
 */
public class OSUtil {

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static boolean isWindows() {
		boolean isWindows;
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("windows") != -1) {
			isWindows = true;
		} else {
			isWindows = false;
		}
		return isWindows;
	}

	public static void main(String[] args) {
		System.out.println(OSUtil.isWindows());
	}
}
