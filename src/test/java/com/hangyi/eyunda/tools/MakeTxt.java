package com.hangyi.eyunda.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeTxt {
	private static String readFile = "/Users/xuguangzhong/temp/tables.txt";
	private static String txtPath = "/Users/xuguangzhong/temp/txt/";

	private static String dbName = "ebusiness";

	private static String sTableName = "表名英文名";
	private static String sFieldName = "字段名";

	private static String tableName = "";
	private static String fieldName = "";
	private static String fieldDesc = "";
	private static String fieldType = "";
	private static String fieldLen = "";
	private static String notNullDef = "";
	private static String remark = "";
	private static int fieldIndex = 0;

	private static String fieldNames = "";
	private static String fieldDescs = "";
	private static String fieldTypes = "";
	private static String notNullDefs = "";
	private static String remarks = "";

	private static String s = null;

	public static void main(String args[]) throws FileNotFoundException, IOException, Exception {
		// 循环读取文件的内容
		InputStreamReader read = new InputStreamReader(new FileInputStream(readFile), "UTF-8");
		BufferedReader br = new BufferedReader(read);
		BufferedWriter bw = null;

		while (br.ready()) {
			s = br.readLine();
			// System.out.println("s=====" + s);
			String strRegex = "";
			String primaryKey = "";
			strRegex = "([a-z]|[A-Z]|_)+";
			// 检查该行是否有表名
			if (s.indexOf(sTableName) != -1) {
				// 取得新的表名
				tableName = getFirstRegexString(s, strRegex);

				// 首字母大写
				tableName = toUpperFirstWord(tableName);

				if (fieldIndex > 0) {
					bw.write("fieldNames = " + fieldNames.substring(0, fieldNames.lastIndexOf(",")));
					bw.newLine();
					bw.write("fieldDescs = " + fieldDescs.substring(0, fieldDescs.lastIndexOf(",")));
					bw.newLine();
					bw.write("fieldTypes = " + fieldTypes.substring(0, fieldTypes.lastIndexOf(",")));
					bw.newLine();
					bw.write("notNullDefs = " + notNullDefs.substring(0, notNullDefs.lastIndexOf(",")));
					bw.newLine();
					bw.write("remarks = " + remarks.substring(0, remarks.lastIndexOf(";")));
					bw.newLine();
				}

				initFieldValues();
				fieldIndex = 0;

				if (bw != null) {
					bw.close();
				}
				File dir = new File(txtPath);
				if (!dir.exists() || !dir.isDirectory())
					dir.mkdirs();

				bw = new BufferedWriter(new FileWriter(txtPath + tableName + ".txt"));

				bw.write("dbNames = " + dbName);
				bw.newLine();
				bw.write("tableName = " + tableName);
				bw.newLine();
			}
			// 检查该行是否有字段名
			else if (s.indexOf(sFieldName) != -1) {
				// 继续读取下一行
			}
			// 处理主键、外键
			else if (s.indexOf("PK：") != -1) {
				primaryKey = s.substring(s.indexOf("PK：") + 3, s.indexOf("FK")).trim();
				bw.write("primaryKey = " + primaryKey);
				bw.newLine();
			}
			// 处理索引
			else if (s.indexOf("Index") != -1) {
				// 继续读取下一行
			} else {
				// 匹配检查是否有字段是英文字符串，若有则一定是待构造的字段名称
				if (s.length() > 0 && isRegexString(s.substring(0, 1), strRegex) && isRegexString(s, strRegex)) {
					// 初始化变量
					initFieldValue();

					fieldIndex = fieldIndex + 1;
					fieldName = getFirstRegexString(s, strRegex);

					// 首字母大写
					fieldName = toUpperFirstWord(fieldName);
					// 构造字段名的英文字符串
					s = cutBackStr(s, strRegex);
					// 匹配任何空白，包括空格、制表、换页等
					strRegex = "\\s+";
					s = cutBackStr(s, strRegex);
					fieldDesc = cutFrontStr(s, strRegex);
					System.out.println(fieldName + ":" + fieldDesc);

					s = cutBackStr(s, strRegex);
					// 构造字段名的字段类型及长度字符串
					fieldType = cutFrontStr(s, strRegex);
					s = cutBackStr(s, strRegex);
					if (fieldType.indexOf("(") != -1) {
						int n1 = fieldType.indexOf("(");
						int n2 = fieldType.indexOf(")");

						fieldLen = fieldType.substring(n1 + 1, n2);
						fieldType = fieldType.substring(0, n1);
					}
					// 构造字段是否可为空
					notNullDef = cutFrontStr(s, strRegex);
					s = cutBackStr(s, strRegex);
					if (notNullDef.indexOf("N") != -1) {
						notNullDef = "1";
					} else {
						notNullDef = "0";
					}

					remark = "";
					if (!fieldType.startsWith("String") && !fieldType.startsWith("Boolean")
							&& !fieldType.startsWith("Integer") && !fieldType.startsWith("Long")
							&& !fieldType.startsWith("Double") && !fieldType.startsWith("Calendar")) {
						s = cutBackStr(s, strRegex);
						s = cutBackStr(s, strRegex);
						s = cutBackStr(s, strRegex);
						remark = s;
					}

					fieldNames += fieldName + ",";
					fieldDescs += fieldDesc + ",";
					fieldTypes += fieldType + ((!fieldLen.equals("")) ? "(" + fieldLen + ")" : "") + ",";
					notNullDefs += notNullDef + ",";
					remarks += remark + ";";
				}
			}
		}
		br.close();
		if (fieldIndex > 0) {
			bw.write("fieldNames = " + fieldNames.substring(0, fieldNames.lastIndexOf(",")));
			bw.newLine();
			bw.write("fieldDescs = " + fieldDescs.substring(0, fieldDescs.lastIndexOf(",")));
			bw.newLine();
			bw.write("fieldTypes = " + fieldTypes.substring(0, fieldTypes.lastIndexOf(",")));
			bw.newLine();
			bw.write("notNullDefs = " + notNullDefs.substring(0, notNullDefs.lastIndexOf(",")));
			bw.newLine();
			bw.write("remarks = " + remarks.substring(0, remarks.lastIndexOf(";")));
			bw.newLine();
		}

		initFieldValues();
		fieldIndex = 0;

		if (bw != null) {
			bw.close();
		}

	}

	private static void initFieldValues() {
		fieldNames = "";
		fieldDescs = "";
		fieldTypes = "";
		notNullDefs = "";
		remarks = "";
	}

	private static void initFieldValue() {
		fieldName = "";
		fieldDesc = "";
		fieldLen = "";
		fieldType = "";
		notNullDef = "";
		remark = "";
	}

	private static boolean isRegexString(String sSource, String sRegex) {
		boolean rVal = false;
		Matcher matcher = regexString(sSource, sRegex);
		while (matcher.find()) {
			rVal = true;
			break;
		}
		return rVal;
	}

	private static String getFirstRegexString(String sSource, String sRegex) {
		String rVal = "";
		Matcher matcher = regexString(sSource, sRegex);
		while (matcher.find()) {
			rVal = matcher.group();
			// 开始位置matcher.start()
			// 结束位置matcher.end()
			break;
		}
		return rVal;
	}

	private static String cutFrontStr(String sSource, String sRegex) {
		int istart = 0;
		Matcher matcher = regexString(sSource, sRegex);
		while (matcher.find()) {
			istart = matcher.start();
			// iend = matcher.end();
			break;
		}
		return sSource.substring(0, istart);
	}

	private static String cutBackStr(String sSource, String sRegex) {
		int iend = 0;
		Matcher matcher = regexString(sSource, sRegex);
		while (matcher.find()) {
			// istart = matcher.start();
			iend = matcher.end();
			break;
		}
		return sSource.substring(iend);
	}

	private static Matcher regexString(String s, String r) {
		Pattern p = Pattern.compile(r);
		Matcher m = p.matcher(s);
		return m;
	}

	private static String toUpperFirstWord(String word) {
		word = word.substring(0, 1).toUpperCase() + word.substring(1, word.length());
		return word;
	}
}