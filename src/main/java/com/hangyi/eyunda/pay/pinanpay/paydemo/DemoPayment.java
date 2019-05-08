package com.hangyi.eyunda.pay.pinanpay.paydemo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ecc.emp.data.KeyedCollection;
import com.sdb.payclient.bean.exception.CsiiException;
import com.sdb.payclient.core.PayclientInterfaceUtil;

/**
 * 模拟组装要上送的订单数据
 * 
 * @author: zhuning090
 */
public class DemoPayment {
	public static void main(String[] args) throws CsiiException {
		PayclientInterfaceUtil util = new PayclientInterfaceUtil(); // 建立客户端实例
		KeyedCollection signDataput = new KeyedCollection("signDataput");
		String orig = ""; // 原始数据
		String sign = ""; // 签名数据
		String encoding = "GBK";
		try {
			signDataput = util.getSignData(getInputOrig()); // 签名
			System.out.println("---签名结果---" + signDataput);

			orig = (String) signDataput.getDataValue("orig"); // 获取原始数据
			System.out.println("---原始数据---" + orig);
			sign = (String) signDataput.getDataValue("sign"); // 获取签名数据
			System.out.println("---签名数据---" + sign);

			orig = PayclientInterfaceUtil.Base64Encode(orig, encoding); // 原始数据先做Base64Encode转码
			System.out.println("---Base64Encode转码后原始数据---" + orig);
			sign = PayclientInterfaceUtil.Base64Encode(sign, encoding); // 签名数据先做Base64Encode转码
			System.out.println("---Base64Encode转码后签名数据---" + sign);

			orig = java.net.URLEncoder.encode(orig, encoding); // Base64Encode转码后原始数据,再做URL转码
			System.out.println("---Base64Encode转码URL转码后原始数据---" + orig);
			sign = java.net.URLEncoder.encode(sign, encoding); // Base64Encode转码后签名数据,再做URL转码
			System.out.println("---Base64Encode转码URL转码后签名数据---" + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 生成包含原始订单信息的KeyedCollection
	private static KeyedCollection getInputOrig() {
		int count = 2; // 商品数量
		double price = 2.00; // 商品单价

		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = formatter.format(date); // 时间
		String datetamp = timestamp.substring(0, 8); // 日期

		KeyedCollection inputOrig = new KeyedCollection("inputOrig");

		inputOrig.put("masterId", "2000311146"); // 商户号，注意生产环境上要替换成商户自己的生产商户号
		inputOrig.put("orderId", "2000311146" + datetamp + getOrderId()); // 订单号，严格遵守格式：商户号+8位日期YYYYMMDD+8位流水
		inputOrig.put("currency", "RMB"); // 币种，目前只支持RMB
		inputOrig.put("amount", count * price); // 订单金额，12整数，2小数
		inputOrig.put("paydate", timestamp); // 下单时间，YYYYMMDDHHMMSS
		inputOrig.put("objectName", "KHpaygate"); // 订单款项描述（商户自定）
		inputOrig.put("validtime", "0"); // 订单有效期(秒)，0不生效
		inputOrig.put("remark", "2000311146"); // 备注字段（商户自定）
		System.out.println("---原始订单信息---" + inputOrig);

		return inputOrig;
	}

	// 生成8位随机数
	private static String getOrderId() {
		String orderId;
		java.util.Random r = new java.util.Random();
		while (true) {
			int i = r.nextInt(99999999);
			if (i < 0)
				i = -i;
			orderId = String.valueOf(i);
			System.out.println("---生成随机数---" + orderId);
			if (orderId.length() < 8) {
				System.out.println("---位数不够8位---" + orderId);
				continue;
			}
			if (orderId.length() >= 8) {
				orderId = orderId.substring(0, 8);
				System.out.println("---生成8位流水---" + orderId);
				break;
			}
		}
		return orderId;
	}
}
