package com.hangyi.eyunda.pay.pinanpay.paydemo;

import com.sdb.payclient.bean.exception.*;

/**
 * 模拟解包和验证银行返回后台通知数据
 * @author: zhuning090
 */
public class DemoNotify {	
	public static void main(String[] args) throws CsiiException {
		com.sdb.payclient.core.PayclientInterfaceUtil  util = new com.sdb.payclient.core.PayclientInterfaceUtil();
		com.ecc.emp.data.KeyedCollection output = new com.ecc.emp.data.KeyedCollection("output");  
		
		String encoding = "GBK";
		
		//模拟银行返回后台通知原始数据，实际页面接收程序应为：
		//String orig = request.getParameter("orig");
		String orig="PGtDb2xsIGlkPSJvdXRwdXQiIGFwcGVuZD0iZmFsc2UiPjxmaWVsZCBpZD0ic3RhdHVzIiB2YWx1ZT0iMDEiLz48ZmllbGQgaWQ9ImRhdGUiIHZhbHVlPSIyMDE0MDUxMjExMzk0MyIvPjxmaWVsZCBpZD0iY2hhcmdlIiB2YWx1ZT0iMTAiLz48ZmllbGQgaWQ9Im1hc3RlcklkIiB2YWx1ZT0iMjAwMDMxMTE0NiIvPjxmaWVsZCBpZD0ib3JkZXJJZCIgdmFsdWU9IjIwMDAzMTExNDYyMDE0MDUxMjc3ODkxNzY5Ii8+PGZpZWxkIGlkPSJjdXJyZW5jeSIgdmFsdWU9IlJNQiIvPjxmaWVsZCBpZD0iYW1vdW50IiB2YWx1ZT0iLjAxIi8+PGZpZWxkIGlkPSJwYXlkYXRlIiB2YWx1ZT0iMjAxNDA1MTIxMTQwMTUiLz48ZmllbGQgaWQ9InJlbWFyayIgdmFsdWU9IiIvPjxmaWVsZCBpZD0ib2JqZWN0TmFtZSIgdmFsdWU9IktIcGF5Z2F0ZSIvPjxmaWVsZCBpZD0idmFsaWR0aW1lIiB2YWx1ZT0iMCIvPjwva0NvbGw+";
		System.out.println("---银行返回后台通知原始数据---"+orig); 
		
		//模拟银行返回后台通知原始数据，实际页面接收程序应为：
		//String sign = request.getParameter("sign");
		String sign="YjA4ZmU1NWM3MTA0Yzc2MmQ2ZjdmN2RkNmQ1NGUzNDQyNzE3OTFjZjlmYjlmZDE1NjZhZGQ0MTI1YmIzNTAxZDRhNTljOGY0NDU1NDkzMmVlMzAwYzg3MTEyNjBhMjk5Njg2OGY0OGU3ZGY1Y2I2OGFiZjdiNDI0NjkyYWI4ODAwZjgwNDM3NWVlNGYxNTFiYTJhMmIwYmJiOWE2OTIzZWNhMmY3YmIxOTRkNTY0NmQwMzQzMzQ0MmUxZjRiYzZhZmUzNGYyZWY2YWNmNTE2MDgxZDEzN2ZiYWM1YjA1MDc1ODVjNjhhNThlZGM3ODE3MDFmNzY4MjEzODQwYzQ0OA==";
		System.out.println("---银行返回后台通知签名数据---"+sign); 
		
		try {			
			orig = com.sdb.payclient.core.PayclientInterfaceUtil.Base64Decode(orig,encoding);
			sign = com.sdb.payclient.core.PayclientInterfaceUtil.Base64Decode(sign,encoding);
			System.out.println("---Base64Decode解码后的后台通知原始数据---"+orig); 
			System.out.println("---Base64Decode解码后的后台通知签名数据---"+sign);
			
			boolean result = util.verifyData(sign,orig);
			System.out.println("---后台通知验签结果---"+result);
			if(result){
				output = util.parseOrigData(orig);
				System.out.println("---订单详细信息---"+output);
				System.out.println("---订单状态---"+output.getDataValue("status"));
				System.out.println("---支付完成时间---"+output.getDataValue("date"));
				System.out.println("---手续费金额---"+output.getDataValue("charge"));
				System.out.println("---商户号---"+output.getDataValue("masterId"));
				System.out.println("---订单号---"+output.getDataValue("orderId"));
				System.out.println("---币种---"+output.getDataValue("currency"));
				System.out.println("---订单金额---"+output.getDataValue("amount"));
				System.out.println("---下单时间---"+output.getDataValue("paydate"));
				System.out.println("---商品描述---"+output.getDataValue("objectName"));
				System.out.println("---订单有效期---"+output.getDataValue("validtime"));
				System.out.println("---备注---"+output.getDataValue("remark"));
			}
		}catch(Exception e){
				e.printStackTrace();
		}
	}
}
