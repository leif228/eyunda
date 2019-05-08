package com.hangyi.eyunda.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

public class MakeDAO {
	private static boolean Debug = true;

	/*** 这部分需要手动定义 ***/
	private static String entityPackageName = "com.hangyi.eyunda.domain";
	private static String daoPackageName = "com.hangyi.eyunda.dao";
	private static String txtPath = "/Users/xuguangzhong/temp/txt/";
	private static String filePath = "/Users/xuguangzhong/temp/dao/";

	private String tableName = null;
	private String primaryKey = null;
	private String fieldNames = null;
	private String fieldDescs = null;
	private String fieldTypes = null;
	private String notNullDefs = null;

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

			System.out.println("tableName = " + tableName);
			System.out.println("primaryKey = " + primaryKey);
			System.out.println("fieldNames = " + fieldNames);
			System.out.println("fieldDescs = " + fieldDescs);
			System.out.println("fieldTypes = " + fieldTypes);
			System.out.println("notNullDefs = " + notNullDefs);

			read.close();
		} catch (IOException e) {
			if (Debug) {
				e.printStackTrace();
			}
		}
	}

	private void make() {
		try {
			File dir = new File(filePath);
			if (!dir.exists() || !dir.isDirectory())
				dir.mkdirs();

			File javaFile = new File(filePath + tableName + "Dao.java");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(javaFile)));

			pw.println("package " + daoPackageName + ";");
			pw.println("");
			pw.println("import java.util.List;");
			pw.println("import org.springframework.stereotype.Repository;");
			pw.println("import " + entityPackageName + "." + tableName + ";");
			pw.println("");
			pw.println("@Repository");
			pw.println("public class " + tableName + "Dao extends PageHibernateDao<" + tableName + ", Long> {");
			pw.println("	");
			pw.println("}");

			pw.flush();
			pw.close();
		} catch (IOException ioe) {
			if (Debug) {
				ioe.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		MakeDAO maker = new MakeDAO();
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