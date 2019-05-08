package com.hangyi.eyunda.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESHelper {
	public final static int ENCRYPT_MODE = 0;
	public final static int DECRYPT_MODE = 1;

	public static void main(String[] args) {

		try {
			// 明文
			String str = "13912345678";
			// 密钥(客户拥有)
			String key = "eyunda1234567890eyunda";
			String desc = DESHelper.DoDES(str, key, ENCRYPT_MODE);
			System.out.println("密文：" + desc);
			// 解密
			str = DESHelper.DoDES(desc, key, DECRYPT_MODE);
			System.out.println("明文：" + str);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * 
	 * @method DoDES
	 * @param data
	 *            需要加密解密的数据
	 * @param key
	 *            密钥 必须8位字节
	 * @param flag
	 *            加密解密标志 0：加密 ，1：解密
	 * @return
	 * @throws
	 * @since v1.0
	 */
	public static String DoDES(String data, String key, int flag) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance("DES");

			if (flag == ENCRYPT_MODE) {
				BASE64Encoder base64encoder = new BASE64Encoder();
				cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
				return base64encoder.encode(cipher.doFinal(data.getBytes("UTF-8")));
			} else {
				BASE64Decoder base64decoder = new BASE64Decoder();
				byte[] encodeByte = base64decoder.decodeBuffer(data);
				cipher.init(Cipher.DECRYPT_MODE, securekey, random);
				byte[] decoder = cipher.doFinal(encodeByte);
				return new String(decoder, "UTF-8");
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return null;
	}
}