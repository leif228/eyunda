package com.hangyi.eyunda.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileMD5Util {

	public static String getFileMD5(String filePath) throws Exception {
		File file = new File(filePath);
		return FileMD5Util.getFileMD5(file);
	}

	public static String getFileMD5(File file) throws Exception {
		if (!file.exists())
			throw new Exception("错误！文件不存在。");

		FileInputStream fis = new FileInputStream(file);

		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = fis.read(buffer, 0, 1024)) != -1) {
			md.update(buffer, 0, length);
		}
		BigInteger bigInt = new BigInteger(1, md.digest());

		fis.close();

		return bigInt.toString(16);
	}

	/*
	 * 获取文件扩展名
	 */
	public static String getFileExtName(String filename) throws Exception {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			} else {
				throw new Exception(filename + "文件无扩展名！");
			}
		} else {
			throw new Exception(filename + "文件不存在！");
		}
	}

	/*
	 * 获取不带扩展名的文件名
	 */
	public static String getFileNameNoExtName(String filename) throws Exception {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/*
	 * 获取文件目录名
	 */
	public static String getFileDirName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('/');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 文件重命名
	 * 
	 * @param path
	 *            文件目录
	 * @param oldname
	 *            原来的文件名
	 * @param newname
	 *            新文件名
	 * @throws Exception
	 */
	public void renameFile(String path, String oldname, String newname) throws Exception {
		if (!oldname.equals(newname)) {
			// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (!oldfile.exists()) {
				throw new Exception("重命名文件不存在！");
			}
			if (newfile.exists())
				// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				throw new Exception(path + "/" + newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		} else {
			throw new Exception("新文件名和旧文件名相同！");
		}
	}

	public static void main(String[] args) throws Exception {
		String filePath = "/Users/xuguangzhong/Downloads/Apache_OpenOffice_4.1.2_Mac.dmg";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date()) + " ---- The process is starting!");
		String fileMD5 = FileMD5Util.getFileMD5(filePath);
		System.out.println(sdf.format(new Date()) + " ---- The process has stopped!");

		System.out.println(fileMD5);
	}

}