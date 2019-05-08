package com.hangyi.eyunda.pay.pinanpay.paydemo;

import com.sdb.payclient.bean.exception.CsiiException;

/**
 * 模拟（KH0005）单笔订单退款
 * @author: zhuning090
 */
public class DemoKH0005 {	
	public static void main(String[] args) throws CsiiException {
		com.sdb.payclient.core.PayclientInterfaceUtil  util = new com.sdb.payclient.core.PayclientInterfaceUtil();
		com.ecc.emp.data.KeyedCollection input = new com.ecc.emp.data.KeyedCollection("input");
		com.ecc.emp.data.KeyedCollection output = new com.ecc.emp.data.KeyedCollection("output");
		
		int orderid ;
		String orderids;
		java.util.Random r=new java.util.Random();
		while(true){
			orderid=r.nextInt(99999999);
			if (orderid<0)orderid=-orderid;
			orderids = String.valueOf(orderid);
			if(orderids.length()<8){
				continue;
			}
			if(orderids.length()>=8){
				orderids = orderids.substring(0,8);
				break;
			}
		}
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		java.util.Date date = calendar.getTime();
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat( "yyyyMMddHHmmss" );
		String timestamp = formatter.format( date );
		String datetamp = timestamp.substring(0, 8);
		
		input.put("masterId","2000311146");  //商户号，注意生产环境上要替换成商户自己的生产商户号
		input.put("refundNo","2000311146"+datetamp+orderids);  //退款单号，严格遵守格式：商户号+8位日期YYYYMMDD+8位流水
		input.put("orderId","20003111462014062337371947");  //原订单号，严格遵守格式：商户号+8位日期YYYYMMDD+8位流水
		input.put("currency","RMB");  //币种，目前只支持RMB
		input.put("refundAmt","0.01");  //退款金额，12整数，2小数，必须等于原订单金额
		input.put("remark","2000311146");  //备注字段（商户自定）
		input.put("objectName","测试退款");  //退款原因（商户自定）
		input.put("NOTIFYURL","http://localhost:8090/khPayment_merchant_demo1/receivedemo2.jsp");  //商户接收订单退款结果服务器异步通知的url，支付平台通知银行，则银行通知商户，否则不通知
		
	    try {
	    	output = util.execute(input,"KH0005"); //执行发送（KH0005）单笔订单退款请求，并返回结果对象
			String errorCode = (String)output.getDataValue("errorCode");
			String errorMsg = (String)output.getDataValue("errorMsg");
			
			System.out.println("---单笔订单退款结果详细信息---"+output);
			
			if((errorCode == null || errorCode.equals(""))&& (errorMsg == null || errorMsg.equals(""))){				
				System.out.println("---订单状态---"+output.getDataValue("status"));  //01：接收成功，退款是否成功请以异步通知结果为准；02：失败
				System.out.println("---商户号---"+output.getDataValue("masterId"));
				System.out.println("---退款单号---"+output.getDataValue("refundAmt"));
				System.out.println("---原订单号---"+output.getDataValue("orderId"));
				System.out.println("---币种---"+output.getDataValue("currency"));
				System.out.println("---退款金额---"+output.getDataValue("refundAmt"));
			}else{
				System.out.println("---错误码---"+output.getDataValue("errorCode"));
				System.out.println("---错误说明---"+output.getDataValue("errorMsg"));
			}

	    }catch (Exception e) {
			e.printStackTrace();
		}
	}
}
