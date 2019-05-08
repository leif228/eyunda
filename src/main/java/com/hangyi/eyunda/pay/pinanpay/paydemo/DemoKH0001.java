package com.hangyi.eyunda.pay.pinanpay.paydemo;

import com.sdb.payclient.bean.exception.CsiiException;

/**
 * 模拟（KH0001）单笔订单状态查询
 * 
 * @author: zhuning090
 */
public class DemoKH0001 {
	public static void main(String[] args) throws CsiiException {
		com.sdb.payclient.core.PayclientInterfaceUtil util = new com.sdb.payclient.core.PayclientInterfaceUtil();
		com.ecc.emp.data.KeyedCollection input = new com.ecc.emp.data.KeyedCollection("input");
		com.ecc.emp.data.KeyedCollection output = new com.ecc.emp.data.KeyedCollection("output");

		input.put("masterId", "2000311146"); // 商户号，注意生产环境上要替换成商户自己的生产商户号
		input.put("orderId", "20003111462014050880763832"); // 订单号，严格遵守格式：商户号+8位日期YYYYMMDD+8位流水

		try {
			output = util.execute(input, "KH0001"); // 执行发送（KH0001）单笔订单状态查询请求，并返回结果对象
			String errorCode = (String) output.getDataValue("errorCode");
			String errorMsg = (String) output.getDataValue("errorMsg");

			System.out.println("---单笔订单状态查询结果详细信息---" + output);

			if ((errorCode == null || errorCode.equals("")) && (errorMsg == null || errorMsg.equals(""))) {
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
				System.out.println("---本金清算标志---" + output.getDataValue("settleflg")); // 1已清算，0待清算
				System.out.println("---本金清算时间---" + output.getDataValue("settletime"));
				System.out.println("---手续费清算标志---" + output.getDataValue("chargeflg")); // 1已清算，0待清算
				System.out.println("---手续费清算时间---" + output.getDataValue("chargetime"));
			} else {
				System.out.println("---错误码---" + output.getDataValue("errorCode"));
				System.out.println("---错误说明---" + output.getDataValue("errorMsg"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
