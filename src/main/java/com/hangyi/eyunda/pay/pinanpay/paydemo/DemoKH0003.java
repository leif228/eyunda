package com.hangyi.eyunda.pay.pinanpay.paydemo;

import com.sdb.payclient.bean.exception.CsiiException;

/**
 * 模拟（KH0003）每日对账单查询
 * @author: zhuning090
 */
public class DemoKH0003 {	
	public static void main(String[] args) throws CsiiException {
		com.sdb.payclient.core.PayclientInterfaceUtil  util = new com.sdb.payclient.core.PayclientInterfaceUtil();
		com.ecc.emp.data.KeyedCollection input = new com.ecc.emp.data.KeyedCollection("input");
		com.ecc.emp.data.KeyedCollection output = new com.ecc.emp.data.KeyedCollection("output");
		
		input.put("masterId","2000311146");  //商户号，注意生产环境上要替换成商户自己的生产商户号
	    input.put("date","20140509");  //对账日期，格式：YYYYMMDD
	    
	    try {
	    	output = util.execute(input,"KH0003"); //执行发送（KH0003）每日对账单查询请求，并返回结果对象
			String errorCode = (String)output.getDataValue("errorCode");
			String errorMsg = (String)output.getDataValue("errorMsg");
			
			System.out.println("---每日对账单查询结果详细信息---"+output);
			
			if((errorCode == null || errorCode.equals(""))&& (errorMsg == null || errorMsg.equals(""))){
				System.out.println("---总金额---"+output.getDataValue("sumAmount"));
				System.out.println("---总笔数---"+output.getDataValue("sumCount"));
				com.ecc.emp.data.IndexedCollection orderList = (com.ecc.emp.data.IndexedCollection) output.getDataElement("iOrderListDetail");
				for(int i=0;i<orderList.size();i++){
					com.ecc.emp.data.KeyedCollection orderDetail = (com.ecc.emp.data.KeyedCollection) orderList.getElementAt(i);
					System.out.print("---订单状态---"+orderDetail.getDataValue("status"));
				    System.out.print("---支付完成时间---"+orderDetail.getDataValue("date"));
				    System.out.print("---手续费金额---"+orderDetail.getDataValue("charge"));
				    System.out.print("---商户号---"+orderDetail.getDataValue("masterId"));
				    System.out.print("---订单号---"+orderDetail.getDataValue("orderId"));
				    System.out.print("---币种---"+orderDetail.getDataValue("currency"));
				    System.out.print("---订单金额---"+orderDetail.getDataValue("amount"));
				    System.out.print("---下单时间---"+orderDetail.getDataValue("paydate"));
				    System.out.print("---商品描述---"+orderDetail.getDataValue("objectName"));
				    System.out.print("---订单有效期---"+orderDetail.getDataValue("validtime"));
				    System.out.print("---备注---"+orderDetail.getDataValue("remark"));
				    System.out.print("---本金清算标志---"+orderDetail.getDataValue("settleflg"));  //1已清算，0待清算
				    System.out.print("---本金清算时间---"+orderDetail.getDataValue("settletime"));
				    System.out.print("---手续费清算标志---"+orderDetail.getDataValue("chargeflg"));  //1已清算，0待清算
				    System.out.println("---手续费清算时间---"+orderDetail.getDataValue("chargetime"));
				}
			}else{
				System.out.println("---错误码---"+output.getDataValue("errorCode"));
				System.out.println("---错误说明---"+output.getDataValue("errorMsg"));
			}
	    }catch (Exception e) {
			e.printStackTrace();
		 }
	}
}
