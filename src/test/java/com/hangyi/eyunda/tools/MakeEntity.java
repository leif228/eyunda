package com.hangyi.eyunda.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MakeEntity {
	private static boolean Debug = true;

	/*** 这部分需要手动定义 ***/
	private static String packageName = "com.hangyi.eyunda.domain";
	private static String txtPath = "/Users/xuguangzhong/temp/txt/";
	private static String filePath = "/Users/xuguangzhong/temp/domain/";

	private String tableName = null;
	private String primaryKey = null;
	private String fieldNames = null;
	private String fieldDescs = null;
	private String fieldTypes = null;
	private String notNullDefs = null;
	private String remarks = null;

	/**
	 * 装载属性文件
	 */
	private void loadProperties(File propertyFile) {
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(propertyFile), "UTF-8");
			Properties properties = new Properties();
			properties.load(read);

			tableName = properties.getProperty("tableName");
			primaryKey = properties.getProperty("primaryKey");
			fieldNames = properties.getProperty("fieldNames");
			fieldDescs = properties.getProperty("fieldDescs");
			fieldTypes = properties.getProperty("fieldTypes");
			notNullDefs = properties.getProperty("notNullDefs");
			remarks = properties.getProperty("remarks");

			System.out.println("tableName = " + tableName);
			System.out.println("primaryKey = " + primaryKey);
			System.out.println("fieldNames = " + fieldNames);
			System.out.println("fieldDescs = " + fieldDescs);
			System.out.println("fieldTypes = " + fieldTypes);
			System.out.println("notNullDefs = " + notNullDefs);
			System.out.println("remarks = " + remarks);

			read.close();
		} catch (IOException e) {
			if (Debug) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 分解字符串
	 */
	private List<String> separate(String str, String sep) {
		List<String> list = new ArrayList<String>();
		try {
			if (str == null || str.trim().equals("")) {
				return list;
			}

			list = Arrays.asList(str.split(sep));

			/*
			 * StringTokenizer st = new StringTokenizer(str, sep); while
			 * (st.hasMoreTokens()) { list.add(st.nextToken()); }
			 */
		} catch (Exception e) {
			if (Debug) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * 首字母小写
	 */
	private String firstOneToLower(String str) {
		if (str == null || str.equals(""))
			return str;
		else
			return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	private void make() {
		try {
			File dir = new File(filePath);
			if (!dir.exists() || !dir.isDirectory())
				dir.mkdirs();

			File javaFile = new File(filePath + tableName + ".java");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(javaFile)));

			List<String> listFieldName = this.separate(fieldNames, ",");
			List<String> listFieldDesc = this.separate(fieldDescs, ",");
			List<String> listFieldType = this.separate(fieldTypes, ",");
			List<String> listNotNullDef = this.separate(notNullDefs, ",");
			List<String> listRemark = this.separate(remarks, ";");

			pw.println("package " + packageName + ";");
			pw.println("");
			pw.println("import java.util.Calendar;");
			pw.println("");
			pw.println("import javax.persistence.Column;");
			pw.println("import javax.persistence.Entity;");
			pw.println("import javax.persistence.EnumType;");
			pw.println("import javax.persistence.Enumerated;");
			pw.println("import javax.persistence.Table;");
			pw.println("");
			for (int i = 0; i < listFieldName.size(); i++) {
				String fieldType = (String) listFieldType.get(i);
				if (!fieldType.startsWith("String") && !fieldType.startsWith("Boolean")
						&& !fieldType.startsWith("Integer") && !fieldType.startsWith("Long")
						&& !fieldType.startsWith("Double") && !fieldType.startsWith("Calendar")) {
					if (listRemark != null && !listRemark.isEmpty() && i < listRemark.size()) {
						String remark = (String) listRemark.get(i);
						pw.println(this.makeEnum(fieldType, remark));
					}
				}
			}
			pw.println("");

			pw.println("@Entity");
			pw.println("@Table(name = \"" + tableName + "\")");
			pw.println("public class " + tableName + " extends BaseEntity {");
			pw.println("private static final long serialVersionUID = -1L;");
			pw.println("");

			for (int i = 0; i < listFieldName.size(); i++) {
				String fieldName = (String) listFieldName.get(i);
				String fieldDesc = (String) listFieldDesc.get(i);
				String fieldType = (String) listFieldType.get(i);
				String notNullDef = (String) listNotNullDef.get(i);

				if (fieldName.equals("Id"))
					continue;

				if (fieldType.indexOf("String") == 0) {
					int p = fieldType.indexOf("(");
					int q = fieldType.indexOf(")");
					String strLength = fieldType.substring(p + 1, q);
					pw.println("	@Column(nullable = " + ((notNullDef.equals("1")) ? "false" : "true") + ", length = "
							+ strLength + ")");
					pw.println("	private String " + firstOneToLower(fieldName) + " = \"\"; // " + fieldDesc + "");
				} else if (fieldType.indexOf("Boolean") == 0) {
					pw.println("	@Column");
					pw.println("	private Boolean " + firstOneToLower(fieldName) + " = true; // " + fieldDesc + "");
				} else if (fieldType.indexOf("Integer") == 0) {
					pw.println("	@Column");
					pw.println("	private Integer " + firstOneToLower(fieldName) + " = 0; // " + fieldDesc + "");
				} else if (fieldType.indexOf("Long") == 0) {
					pw.println("	@Column");
					pw.println("	private Long " + firstOneToLower(fieldName) + " = 0L; // " + fieldDesc + "");
				} else if (fieldType.indexOf("Double") == 0) {
					pw.println("	@Column");
					pw.println("	private Double " + firstOneToLower(fieldName) + " = 0.00D; // " + fieldDesc + "");
				} else if (fieldType.indexOf("Calendar") == 0) {
					pw.println("	@Column");
					pw.println("	private Calendar " + firstOneToLower(fieldName) + " = Calendar.getInstance(); // "
							+ fieldDesc + "");
				} else {
					pw.println("	@Enumerated(EnumType.ORDINAL)");
					pw.println("	private " + fieldType + " " + firstOneToLower(fieldName) + " = null; //" + fieldDesc
							+ "");
				}
			}
			pw.println("");
			for (int i = 0; i < listFieldName.size(); i++) {
				String fieldName = (String) listFieldName.get(i);
				String fieldDesc = (String) listFieldDesc.get(i);
				String fieldType = (String) listFieldType.get(i);

				if (fieldType.indexOf("String") == 0)
					fieldType = "String";

				if (fieldName.equals("Id"))
					continue;

				pw.println("	/**");
				pw.println("	 * 取得" + fieldDesc + "");
				pw.println("	 */");
				pw.println("	public " + fieldType + " get" + fieldName + "() {");
				pw.println("		return " + firstOneToLower(fieldName) + ";");
				pw.println("	}");
				pw.println("	/**");
				pw.println("	 * 设置" + fieldDesc + "");
				pw.println("	 */");
				pw.println(
						"	public void set" + fieldName + "(" + fieldType + " " + firstOneToLower(fieldName) + ") {");
				pw.println("		this." + firstOneToLower(fieldName) + " = " + firstOneToLower(fieldName) + ";");
				pw.println("	}");
				pw.println("");
			}
			pw.println("}");

			pw.flush();
			pw.close();
		} catch (IOException ioe) {
			if (Debug) {
				ioe.printStackTrace();
			}
		}
	}

	private String makeEnum(String enumTypeName, String enumItems) {
		try {
			File dir = new File(filePath + "/enumeric");
			if (!dir.exists() || !dir.isDirectory())
				dir.mkdirs();

			File javaFile = new File(filePath + "/enumeric/" + enumTypeName + ".java");
			PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(javaFile)));

			List<String> listItem = this.separate(enumItems, ",");

			pw2.println("package com.hangyi.eyunda.domain.enumeric;");

			pw2.println("");
			pw2.println("import java.util.ArrayList;");
			pw2.println("import java.util.HashMap;");
			pw2.println("import java.util.List;");
			pw2.println("import java.util.Map;");

			pw2.println("");
			pw2.println("public enum " + enumTypeName + " {");

			for (int i = 0; i < listItem.size(); i++) {
				String[] items = listItem.get(i).split(":");
				if (i < listItem.size() - 1)
					pw2.println("	" + items[0] + "(\"" + items[1] + "\"),");
				else
					pw2.println("	" + items[0] + "(\"" + items[1] + "\");");
			}

			pw2.println("");
			pw2.println("	private String description;");

			pw2.println("");
			pw2.println("	public static List<Map<String, Object>> getMaps() {");
			pw2.println("		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();");
			pw2.println("		for (" + enumTypeName + " e : " + enumTypeName + ".values()) {");
			pw2.println("			maps.add(e.getMap());");
			pw2.println("		}");
			pw2.println("		return maps;");
			pw2.println("	}");
			pw2.println("");
			pw2.println("	public Map<String, Object> getMap() {");
			pw2.println("		Map<String, Object> map = new HashMap<String, Object>();");
			pw2.println("		map.put(\"no\", this.ordinal());");
			pw2.println("		map.put(\"name\", this.name());");
			pw2.println("		map.put(\"description\", this.description);");
			pw2.println("		return map;");
			pw2.println("	}");

			pw2.println("	private " + enumTypeName + "(String description) {");
			pw2.println("		this.description = description;");
			pw2.println("	}");
			pw2.println("");
			pw2.println("	public String getDescription() {");
			pw2.println("		return description;");
			pw2.println("	}");
			pw2.println("}");

			pw2.flush();
			pw2.close();

			return "import com.hangyi.eyunda.domain.enumeric." + enumTypeName + ";";
		} catch (IOException ioe) {
			if (Debug) {
				ioe.printStackTrace();
			}
			return null;
		}
	}

	public static void main(String[] args) {
		MakeEntity maker = new MakeEntity();
		try {
			System.out.println("Generate file start!");

			File dir = new File(txtPath);
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				maker.loadProperties(files[i]);
				maker.make();
			}

			System.out.println("Generate file end!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}