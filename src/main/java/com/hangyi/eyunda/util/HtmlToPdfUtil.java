package com.hangyi.eyunda.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.context.i18n.LocaleContextHolder;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.BaseFont;

public class HtmlToPdfUtil {

	public static void main(String[] args) throws Exception {
		String urlStr = "http://www.eyd98.com/mobile/ship/show?id=70";
		String outputFile = "e:/ship70.pdf";

		HtmlToPdfUtil.exportPdfFile(urlStr, outputFile);
	}

	// 导出pdf
	public static File exportPdfFile(String urlStr, String outputFile) {
		try {
			if (!FileUtil.existDirectory(outputFile))
				FileUtil.makeDirectory(outputFile);

			File retFile = new File(outputFile);
			// 合同一旦被修改或者被处理,其createTime一定被修改,outputFile名称也会改变,就一定不存在
			if (retFile.exists() && retFile.length() > 0) // 一定是未被修改或者被处理,且已经存在
				return retFile;// 直接返回即可

			OutputStream os = new FileOutputStream(retFile);

			// String str = getHtmlFile(urlStr);
			// System.out.println(str);

			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(urlStr);
			ITextFontResolver fontResolver = renderer.getFontResolver();
			if(OSUtil.isWindows()){
				// for win7
				fontResolver.addFont("C:/Windows/Fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}else{
				fontResolver.addFont("/usr/share/fonts/chinese/TrueType/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
			renderer.layout();

			renderer.createPDF(os);

			System.out.println("转换成功！");
			os.flush();
			os.close();
			return retFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 读取页面内容
	public static String getHtmlFile(String urlStr) {
		try {
			if (urlStr.indexOf("?") != -1) {
				urlStr = urlStr + "&locale=" + LocaleContextHolder.getLocale().toString();
			} else {
				urlStr = urlStr + "?locale=" + LocaleContextHolder.getLocale().toString();
			}

			URL url = new URL(urlStr);

			URLConnection uc = url.openConnection();
			InputStream is = uc.getInputStream();

			Tidy tidy = new Tidy();

			OutputStream os2 = new ByteArrayOutputStream();
			tidy.setXHTML(true); // 设定输出为xhtml(还可以输出为xml)
			// tidy.setCharEncoding(Configuration.UTF8); // 设定编码以正常转换中文
			tidy.setTidyMark(false); // 不设置它会在输出的文件中给加条meta信息
			tidy.setXmlPi(true); // 让它加上<?xml version="1.0"?>
			tidy.setIndentContent(true); // 缩进，可以省略，只是让格式看起来漂亮一些
			tidy.parse(is, os2);

			is.close();

			// 解决乱码 --将转换后的输出流重新读取改变编码
			String temp;
			StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(
					((ByteArrayOutputStream) os2).toByteArray()), "utf-8"));
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
			}

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}