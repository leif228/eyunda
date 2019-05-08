package com.hangyi.eyunda.pay.pinanpay.paydemo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.ecc.emp.data.KeyedCollection;
import com.hangyi.eyunda.pay.pinanpay.PapayHttpProtocolHandler;
import com.hangyi.eyunda.pay.pinanpay.StringUtil;
import com.sdb.payclient.core.PayclientInterfaceUtil;

/**
 * 模拟（API002）非协议支付
 * 
 * @author: zhuning090
 */
public class DemoAPI007 {
	public static void main(String[] args) throws Exception {
		PayclientInterfaceUtil util = new PayclientInterfaceUtil();
		com.ecc.emp.data.KeyedCollection input = new com.ecc.emp.data.KeyedCollection("input");
		com.ecc.emp.data.KeyedCollection output = new com.ecc.emp.data.KeyedCollection("output");

		input.put("masterId", "2000311146"); // 商户号，注意生产环境上要替换成商户自己的生产商户号
		input.put("customerId", "zhuning090");
		input.put("bindId", "20003111462015120810000011");
		input.put("amount", "0.3");
		input.put("orderId", "20003111462015121010000003");
		input.put("currency", "RMB");
		input.put("objectName", "龙潭虎穴");
		input.put("paydate", "20151210152200");
		input.put("validtime", "0");
		input.put("remark", "2000311146");
		input.put("verifyCode", "736428");
		input.put("dateTime", "20151210091807");
		input.put("NOTIFYURL", "https://testebank.sdb.com.cn:461/khpayment/receivedemo2.jsp");
		KeyedCollection signDataput = new KeyedCollection("signDataput");
		String orig = ""; // 原始数据
		String sign = ""; // 签名数据
		String encoding = "GBK";
		try {
			signDataput = util.getSignData(input); // 签名
			System.out.println("---签名结果---" + signDataput);

			orig = (String) signDataput.getDataValue("orig"); // 获取原始数据
			// orig =
			// "<kColl id=\"input\" append=\"false\"><field id=\"masterId\" value=\"2000311146\"/><field id=\"orderId\" value=\"20003111462014101510255207\"/><field id=\"currency\" value=\"RMB\"/><field id=\"amount\" value=\"1.00\"/><field id=\"paydate\" value=\"20141015102552\"/><field id=\"remark\" value=\"141015102552074212\"/><field id=\"objectName\" value=\"test\"/><field id=\"validtime\" value=\"3600\"/></kColl>";
			System.out.println("---原始数据---" + orig);
			sign = (String) signDataput.getDataValue("sign"); // 获取签名数据
			// sign =
			// "423f90b04165cb328bc14ea63ccdb07730bcea5223642e0efda572c3dfab48bd6a5afe6f9797ed871c1c3699768d006a26b463eb05efd2c278ddd8648d564b8a3ec0f4ba0a5b99d3e831aa24308a025f020d0387573339311953c33ba290cc1d1251aeeda9060d33890209aa3c1cb9f3edcd807d95e043b023c09b7e630d60b0";
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
		Map<String, String[]> sPara = new HashMap<String, String[]>();
		sPara.put("orig", new String[] { orig });
		sPara.put("sign", new String[] { sign });
		String PaSignPayData = StringUtil.mapArrayToString(sPara);
		System.out.println("上送数据：" + PaSignPayData);
		byte[] nameValuePair = null;
		try {
			nameValuePair = PaSignPayData.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("上送平安付协议支付数据失败！" + e.getMessage());
			throw new Exception("上送平台协议支付数据失败");
		}
		String strResult = null;
		try {
			strResult = new String(PapayHttpProtocolHandler.sendData(nameValuePair,
					"https://testebank.sdb.com.cn:461/khpayment/API007.do", null, null), "UTF-8");
			// strResult = new
			// String(PapayHttpProtocolHandler.sendData(nameValuePair,
			// "http://localhost:8080/khpayment/API007.do", null,
			// null),"UTF-8");
			System.out.println("返回数据：" + strResult);
			orig = (strResult.split("SDBPAYGATE=")[0]).split("=")[2];
			sign = (strResult.split("orig=")[0]).split("=")[1];
			System.out.println("---银行返回前台通知原始数据---" + orig);
			System.out.println("---银行返回前台通知签名数据---" + sign);

			orig = java.net.URLDecoder.decode(orig, encoding);
			sign = java.net.URLDecoder.decode(sign, encoding);
			System.out.println("---URL解码后的前台通知原始数据---" + orig);
			System.out.println("---URL解码后的前台通知签名数据---" + sign);

			orig = com.sdb.payclient.core.PayclientInterfaceUtil.Base64Decode(orig, encoding);
			sign = com.sdb.payclient.core.PayclientInterfaceUtil.Base64Decode(sign, encoding);
			// sign =
			// "5d4049e6e5e110c438e44a76f2f5f07d4d394a3c7dd7a7a3884d6228caeeb8f371e0d965bf9df7a80841120a08c72492371ddf54f7893b22c20293ad38de21dd4be1878649604d4fd7237351126c2e971437c3bbaec8ce9e7249e49daf8f36213208ba47277a2a2588b20387f771de9aaa7977857b37e93088dd9c5893c1bdd4";
			System.out.println("---URL解码Base64Decode解码后的前台通知原始数据---" + orig);
			System.out.println("---URL解码Base64Decode解码后的前台通知签名数据---" + sign);

			boolean result = util.verifyData(sign, orig);
			System.out.println("---前台通知验签结果---" + result);
		} catch (HttpException e) {
			System.out.println("上送平安付协议支付数据失败！" + e.getMessage());
			throw new Exception("上送平台协议支付数据签名失败");
		} catch (IOException e) {
			System.out.println("上送平安付协议支付数据失败！" + e.getMessage());
			throw new Exception("上送平台协议支付数据签名失败");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (strResult == null) {
			System.out.println("平安付协议支付返回数据为空！");
			throw new Exception("上送平台协议支付返回数据为空");
		} else {
		}
	}
}
