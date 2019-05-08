package com.hangyi.eyunda.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;

public class MakeDataConstructor {
	private static String dataPath = "/Users/xuguangzhong/temp/data/";

	private static String[] clazzNames = { "com.hangyi.eyunda.data.scfreight.ScfCargoData",
			"com.hangyi.eyunda.data.scfreight.ScfCargoPriceData",
			"com.hangyi.eyunda.data.scfreight.ScfFreightData",
			"com.hangyi.eyunda.data.scfreight.ScfFreightPriceData" };

	public static void main(String args[]) throws Exception {
		for (String clazzName : clazzNames) {
			Class<?> clazz = Class.forName(clazzName);
			Field[] field = clazz.getDeclaredFields();

			System.out.println("=============== " + clazz.getName() + " ===============");

			File dir = new File(dataPath);
			if (!dir.exists() || !dir.isDirectory())
				dir.mkdirs();

			File javaFile = new File(
					dataPath + clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1) + ".java");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(javaFile)));

			pw.println("public " + clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1) + "() { super(); }");
			pw.println("");

			pw.println("public " + clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1)
					+ "(Map<String, Object> params) {");
			pw.println("	super();");
			pw.println("	if (params != null && !params.isEmpty()) {");

			for (int i = 0; i < field.length; i++) {
				// 权限修饰符
				// int mo = field[i].getModifiers();
				// String priv = Modifier.toString(mo);
				// 属性类型
				Class<?> type = field[i].getType();

				if (type.getName().startsWith("java.lang.Long")) {
					pw.println("		this." + field[i].getName() + " = ((Double) params.get(\"" + field[i].getName()
							+ "\")).longValue();");
				} else if (type.getName().startsWith("java.lang.Integer")) {
					pw.println("		this." + field[i].getName() + " = ((Double) params.get(\"" + field[i].getName()
							+ "\")).intValue();");
				} else if (type.getName().startsWith("java.lang.Double")) {
					pw.println("		this." + field[i].getName() + " = (Double) params.get(\"" + field[i].getName()
							+ "\");");
				} else if (type.getName().startsWith("java.lang.String")) {
					pw.println("		this." + field[i].getName() + " = (String) params.get(\"" + field[i].getName()
							+ "\");");
				} else if (type.getName().startsWith("java.util.List")) {
					pw.println("		this." + field[i].getName() + " = new ArrayList<>();");
					pw.println("		List<Map<String, Object>> " + field[i].getName()
							+ "Map = (List<Map<String, Object>>) params.get(\"" + field[i].getName() + "\");");
					pw.println("		if(" + field[i].getName() + "Map != null && " + field[i].getName()
							+ "Map.size() > 0){");
					pw.println("			for (Map<String,Object> map : " + field[i].getName() + "Map){");
					pw.println(
							"				" + field[i].getName() + " data = new " + field[i].getName() + "(map);");
					pw.println("				this." + field[i].getName() + ".add(data);");
					pw.println("			}");
					pw.println("		}");
				} else if (type.getName().startsWith("com.hangyi.eyunda.data")) {
					pw.println("		this." + field[i].getName() + " = new " + type.getName()
							+ "((Map<String, Object>) params.get(\"" + field[i].getName() + "\"));");
				} else if (type.getName().startsWith("com.hangyi.eyunda")) {
					pw.println("		if (params.get(\"" + field[i].getName() + "\") != null) this."
							+ field[i].getName() + " = " + type.getName() + ".valueOf((String) params.get(\""
							+ field[i].getName() + "\"));");
				}
			}

			pw.println("	}");
			pw.println("}");

			pw.flush();
			pw.close();
		}
	}

}