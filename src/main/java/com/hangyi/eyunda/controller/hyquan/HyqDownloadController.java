package com.hangyi.eyunda.controller.hyquan;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hangyi.eyunda.util.Constants;

@Controller
@RequestMapping("/hyquan/download")
public class HyqDownloadController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/imageDownload", method = { RequestMethod.GET })
	public void imageDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String urlPath = ServletRequestUtils.getStringParameter(request, "url", "");
			if ("".equals(urlPath)) {
				return;
			}

			File file = new File(Constants.SHARE_DIR + urlPath);
			if (!file.exists()) {
				return;
			}

			response.setHeader("Content_Length", String.valueOf(file.length()));
			response.setContentType("image/jpeg");

			ServletOutputStream sos = response.getOutputStream();
			FileInputStream fis = new FileInputStream(file);

			IOUtils.copy(fis, sos);

			fis.close();
			sos.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/fileDownload", method = { RequestMethod.GET })
	public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String urlPath = ServletRequestUtils.getStringParameter(request, "url", "");
			if ("".equals(urlPath)) {
				return;
			}

			File file = new File(Constants.SHARE_DIR + urlPath);
			if (!file.exists()) {
				return;
			}

			// 设置下载文件的类型为任意类型
			response.setContentType("application/x-msdownload");
			// 添加下载文件的头信息。此信息在下载时会在下载面板上显示，比如：
			String fileName = file.getName();
			String downLoadName = new String(fileName.getBytes("GBK"), "iso8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + downLoadName + "\"");
			response.setHeader("Content_Length", String.valueOf(file.length()));

			ServletOutputStream sos = response.getOutputStream();
			FileInputStream fis = new FileInputStream(file);

			IOUtils.copy(fis, sos);

			fis.close();
			sos.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

}
