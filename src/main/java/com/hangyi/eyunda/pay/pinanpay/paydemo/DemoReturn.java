package com.hangyi.eyunda.pay.pinanpay.paydemo;

import com.sdb.payclient.bean.exception.CsiiException;

/**
 * 模拟解包和验证银行返回前台通知数据
 * 
 * @author: zhuning090
 */
public class DemoReturn {
	public static void main(String[] args) throws CsiiException {
		com.sdb.payclient.core.PayclientInterfaceUtil util = new com.sdb.payclient.core.PayclientInterfaceUtil();
		com.ecc.emp.data.KeyedCollection output = new com.ecc.emp.data.KeyedCollection("output");

		String encoding = "GBK";

		// 模拟银行返回通知原始数据，实际页面接收程序应为：
		// String orig = request.getParameter("orig");
		String orig = "PGtDb2xsIGlkPSJvdXRwdXQiIGFwcGVuZD0iZmFsc2UiPjxmaWVsZCBpZD0ic3RhdHVzIiB2YWx1%0AZT0iMDEiLz48ZmllbGQgaWQ9ImRhdGUiIHZhbHVlPSIyMDE0MDUwOTA4NTUwMiIvPjxmaWVsZCBp%0AZD0iY2hhcmdlIiB2YWx1ZT0iMTAiLz48ZmllbGQgaWQ9Im1hc3RlcklkIiB2YWx1ZT0iMjAwMDMx%0AMTE0NiIvPjxmaWVsZCBpZD0ib3JkZXJJZCIgdmFsdWU9IjIwMDAzMTExNDYyMDE0MDUwOTI1OTk1%0AMTE0Ii8%2BPGZpZWxkIGlkPSJjdXJyZW5jeSIgdmFsdWU9IlJNQiIvPjxmaWVsZCBpZD0iYW1vdW50%0AIiB2YWx1ZT0iLjAxIi8%2BPGZpZWxkIGlkPSJwYXlkYXRlIiB2YWx1ZT0iMjAxNDA1MDkwODU1MzAi%0ALz48ZmllbGQgaWQ9InJlbWFyayIgdmFsdWU9IiIvPjxmaWVsZCBpZD0ib2JqZWN0TmFtZSIgdmFs%0AdWU9IktIcGF5Z2F0ZSIvPjxmaWVsZCBpZD0idmFsaWR0aW1lIiB2YWx1ZT0iMCIvPjwva0NvbGw%2B%0A";
		System.out.println("---银行返回前台通知原始数据---" + orig);

		// 模拟银行返回通知原始数据，实际页面接收程序应为：
		// String sign = request.getParameter("sign");
		String sign = "MjY5YzJlMDBhMzcyZTJkNWJjYjAxMzhmNGMxNmRkNDVjNjVjYTY3YzhiMjc1NTZhNTk0MTI0MzE5%0AN2Q1MWZkNWI5OTMxNzJhZTJiZDEyNDNmMjE3ZTk4MjU1N2E2YzAzOGI1YjI2YTQ0ZWU0M2EyNjUx%0AZTdmNjk2NDMzMDZhNTM5Y2NjMDM0YzJjZjJjZGE2ZjZlOTE1NTU3MzE1NzYxOGE4NGI1YTAwNTZi%0AODg4ZjVlMDdlMmNjODlmNzUyNzVmMGFmZDAzMWY4MDg3MjRjNjc0ZGE0MmRjNjYzNTM1YjM2MDFi%0ANDA4ZjllYWI4YjgxNDI4Y2E4NWM1NjMxMzA2ZA%3D%3D%0A";
		System.out.println("---银行返回前台通知签名数据---" + sign);
		try {
			orig = java.net.URLDecoder.decode(orig, encoding);
			sign = java.net.URLDecoder.decode(sign, encoding);
			System.out.println("---URL解码后的前台通知原始数据---" + orig);
			System.out.println("---URL解码后的前台通知签名数据---" + sign);

			orig = com.sdb.payclient.core.PayclientInterfaceUtil.Base64Decode(orig, encoding);
			sign = com.sdb.payclient.core.PayclientInterfaceUtil.Base64Decode(sign, encoding);
			System.out.println("---URL解码Base64Decode解码后的前台通知原始数据---" + orig);
			System.out.println("---URL解码Base64Decode解码后的前台通知签名数据---" + sign);

			boolean result = util.verifyData(sign, orig);
			System.out.println("---前台通知验签结果---" + result);
			if (result) {
				output = util.parseOrigData(orig);
				System.out.println("---订单详细信息---" + output);
				System.out.println("---订单状态---" + output.getDataValue("status"));
				System.out.println("---支付完成时间---" + output.getDataValue("date"));
				System.out.println("---手续费金额---" + output.getDataValue("charge"));
				System.out.println("---商户号---" + output.getDataValue("masterId"));
				System.out.println("---订单号---" + output.getDataValue("orderId"));
				System.out.println("---币种---" + output.getDataValue("currency"));
				System.out.println("---订单金额---" + output.getDataValue("amount"));
				System.out.println("---下单时间---" + output.getDataValue("paydate"));
				System.out.println("---商品描述---" + output.getDataValue("objectName"));
				System.out.println("---订单有效期---" + output.getDataValue("validtime"));
				System.out.println("---备注---" + output.getDataValue("remark"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
