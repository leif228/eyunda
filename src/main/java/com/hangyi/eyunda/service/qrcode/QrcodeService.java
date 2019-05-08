package com.hangyi.eyunda.service.qrcode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.hangyi.eyunda.util.MatrixToImageWriter;

/**
 * 二维码生成与解析
 * @author apple
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class QrcodeService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 生成二维码
	 * @param jsonStr 字符串
	 * @param filePath 生成文件地址
	 * @param fileName 生成的文件名称
	 * @param overwrite 是否覆盖
	 * @param width 二维码宽度
	 * @param height 二维码高度
	 * @param format 二维码格式
	 * @return 生成文件地址
	 */
	public String encode(String jsonStr, String filePath, String fileName, Boolean overwrite, int width, int height, String format) throws Exception{
		String url = "";
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = multiFormatWriter.encode(jsonStr, BarcodeFormat.QR_CODE, width, height, hints);
		File fDir = new File(filePath);
		if (!fDir.exists()) {
			fDir.mkdirs();
		}else{
			//FileUtil.emptyDirectory(fDir);
		}
		if(filePath.lastIndexOf("/") != filePath.length()-1){
			filePath = filePath + "/";
		}
		url = filePath + fileName;
		File qcodeFile = new File(url);
		if(qcodeFile.exists() && !overwrite){
			return url;
		}else{
			MatrixToImageWriter.writeToPath(bitMatrix, format, qcodeFile.toPath());
		}
		return url;
	}
	/**
	 * 解析二维码
	 * @param path 二维码文件保存地址
	 * @return 二维码内容
	 */
	public String decode(String path){
		String result = "";
		return result;
	}
}
