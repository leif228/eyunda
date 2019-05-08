package com.hangyi.eyunda.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.service.ShareDirService;

public class MultipartUtil {

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
	
	/** 保存文件返回原、大展示图、小图相对路径(格式：原图相对路径 + ":" + 大展示图 + ":" +小图相对路径)  */
	public static String uploadBShSImg(MultipartFile mpf, String realPath, String relativePath, String prefix)
			throws Exception {
		if (mpf == null)
			return null;
		
		String ext = mpf.getOriginalFilename();
		InputStream is = mpf.getInputStream();
		
		String newFileName = getFileName(FilenameUtils.getExtension(ext));
		
		if (!ImageUtil.isImage(newFileName)) {
			return null;
		}
		
		String pathfileB = "";
		String pathfileSh = "";
		String pathfileS = "";
		if (prefix == null || "".equals(prefix)) {
			pathfileB = relativePath + ShareDirService.FILE_SEPA_SIGN + "B" +newFileName;
			pathfileSh = relativePath + ShareDirService.FILE_SEPA_SIGN + "Sh" +newFileName;
			pathfileS = relativePath + ShareDirService.FILE_SEPA_SIGN + "S" +newFileName;
		} else {
			pathfileB = relativePath + ShareDirService.FILE_SEPA_SIGN + prefix + "-" + "B" +newFileName;
			pathfileSh = relativePath + ShareDirService.FILE_SEPA_SIGN + prefix + "-" + "Sh" +newFileName;
			pathfileS = relativePath + ShareDirService.FILE_SEPA_SIGN + prefix + "-" + "S" +newFileName;
			
		}
		File newFileB = new File(realPath + pathfileB);
		File newFileSh = new File(realPath + pathfileSh);
		File newFileS = new File(realPath + pathfileS);
		
		FileUtils.copyInputStreamToFile(is, newFileB);
		FileUtils.copyFile(newFileB, newFileSh);
		FileUtils.copyFile(newFileB, newFileS);
		
		ImageUtil.resize(realPath + pathfileSh, 800, 1000, false);
		ImageUtil.resize(realPath + pathfileS, 80, 100, false);
		
		return pathfileB + ":" + pathfileSh + ":" + pathfileS;
	}
	
	/** 保存文件返回原、小图相对路径(格式：原图相对路径 + ":" +小图相对路径)  */
	public static String uploadBSImg(MultipartFile mpf, String realPath, String relativePath, String prefix)
			throws Exception {
		if (mpf == null)
			return null;
		
		String ext = mpf.getOriginalFilename();
		InputStream is = mpf.getInputStream();
		
		String newFileName = getFileName(FilenameUtils.getExtension(ext));
		
		if (!ImageUtil.isImage(newFileName)) {
			return null;
		}

		String pathfileB = "";
		String pathfileS = "";
		if (prefix == null || "".equals(prefix)) {
			pathfileB = relativePath + ShareDirService.FILE_SEPA_SIGN + "B" +newFileName;
			pathfileS = relativePath + ShareDirService.FILE_SEPA_SIGN + "S" +newFileName;
		} else {
			pathfileB = relativePath + ShareDirService.FILE_SEPA_SIGN + prefix + "-" + "B" +newFileName;
			pathfileS = relativePath + ShareDirService.FILE_SEPA_SIGN + prefix + "-" + "S" +newFileName;
			
		}
		File newFileB = new File(realPath + pathfileB);
		File newFileS = new File(realPath + pathfileS);
		
		FileUtils.copyInputStreamToFile(is, newFileB);
		FileUtils.copyFile(newFileB, newFileS);
		
		ImageUtil.resize(realPath + pathfileS, 80, 100, false);
		
		return pathfileB + ":" + pathfileS;
	}

	/** 保存文件返回相对路径 */
	public static String uploadFile(MultipartFile mpf, String realPath, String relativePath, String prefix)
			throws Exception {
		if (mpf == null)
			return null;

		String ext = mpf.getOriginalFilename();
		InputStream is = mpf.getInputStream();

		String url = MultipartUtil.uploadFile(is, realPath, relativePath, prefix, ext);

		File file = new File(realPath + url);
		String fileMD5Id = FileMD5Util.getFileMD5(file);
		String fileExt = FileMD5Util.getFileExtName(url);
		String fileDir = FileMD5Util.getFileDirName(url);
		String newUrl = fileDir + "/" + fileMD5Id + "." + fileExt;
		File newFile = new File(realPath + newUrl);

		// 确保文件名与fileMD5Id一致
		if (!newFile.exists())
			file.renameTo(newFile);
		else
			file.delete();

		return newUrl;
	}

	/** 保存文件返回相对路径 */
	public static String uploadFile(InputStream is, String realPath, String relativePath, String prefix, String ext)
			throws IOException {

		String newFileName = getFileName(FilenameUtils.getExtension(ext));

		String pathfile = "";
		if (prefix == null || "".equals(prefix))
			pathfile = relativePath + ShareDirService.FILE_SEPA_SIGN + newFileName;
		else
			pathfile = relativePath + ShareDirService.FILE_SEPA_SIGN + prefix + "-" + newFileName;
		File newFile = new File(realPath + pathfile);
		FileUtils.copyInputStreamToFile(is, newFile);
		if (ImageUtil.isImage(newFileName)) {
			// 读取文件大小，超过3m转换成小文件
			/*if (newFile.length() > 3000000) {
				ImageUtil.resize(realPath + pathfile, 400, 600, false);
			}*/
		}
		return pathfile;
	}

	/** 保存多个文件返回相对路径数组 */
	public static String[] uploadMultiFile(MultipartFile[] mpfs, String realPath, String relativePath, String prefix)
			throws Exception {
		if (mpfs == null || mpfs.length == 0)
			return null;

		String[] pathArray = new String[mpfs.length];

		for (int i = 0; i < mpfs.length; i++) {
			MultipartFile mpf = mpfs[i];

			pathArray[i] = uploadFile(mpf, realPath, relativePath, prefix);
		}
		return pathArray;
	}
}