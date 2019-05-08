package com.hangyi.eyunda.pay.pinanpay.paydemo;

import com.sdb.payclient.bean.exception.CsiiException;

/**
 * 模拟（KH0006）订单退款查询
 * @author: zhuning090
 */
public class DemoKH0006 {	
	public static void main(String[] args) throws CsiiException {
		com.sdb.payclient.core.PayclientInterfaceUtil  util = new com.sdb.payclient.core.PayclientInterfaceUtil();
		com.ecc.emp.data.KeyedCollection input = new com.ecc.emp.data.KeyedCollection("input");
		com.ecc.emp.data.KeyedCollection output = new com.ecc.emp.data.KeyedCollection("output");
		
		input.put("masterId","2000311146");  //商户号，注意生产环境上要替换成商户自己的生产商户号
	    input.put("beginDate","20160501000000");  //查询开始时间（退款请求接收时间）YYYYMMDDHHMMSS
	    input.put("endDate","20160512235959");  //查询结束时间（退款请求接收时间）YYYYMMDDHHMMSS
	    
	    try {
	    	output = util.execute(input,"KH0006"); //执行发送（KH0006）订单退款查询请求，并返回结果对象
			String errorCode = (String)output.getDataValue("errorCode");
			String errorMsg = (String)output.getDataValue("errorMsg");
			
			System.out.println("---订单退款查询结果详细信息---"+output);
			
			if((errorCode == null || errorCode.equals(""))&& (errorMsg == null || errorMsg.equals(""))){
				System.out.println("---总金额---"+output.getDataValue("sumAmount"));
				System.out.println("---总笔数---"+output.getDataValue("sumCount"));
				com.ecc.emp.data.IndexedCollection orderList = (com.ecc.emp.data.IndexedCollection) output.getDataElement("iOrderListDetail");
				for(int i=0;i<orderList.size();i++){
					com.ecc.emp.data.KeyedCollection orderDetail = (com.ecc.emp.data.KeyedCollection) orderList.getElementAt(i);
				    System.out.print("---商户号---"+orderDetail.getDataValue("masterId"));
				    System.out.print("---退款单号---"+orderDetail.getDataValue("refundNo"));
				    System.out.print("---原订单号---"+orderDetail.getDataValue("orderId"));
				    System.out.print("---退款请求接收时间---"+orderDetail.getDataValue("refundTime"));
				    System.out.print("---币种---"+orderDetail.getDataValue("currency"));
				    System.out.print("---退款金额---"+orderDetail.getDataValue("refundAmt"));
					System.out.print("---退款状态---"+orderDetail.getDataValue("refundStatus"));  //01成功；02失败；00处理中
				    System.out.print("---退款本金扣收标志---"+orderDetail.getDataValue("settleflg"));  //0：未扣款；1：已扣款；2：扣款中
				    System.out.print("---退款失败本金退还标志---"+orderDetail.getDataValue("refundFlag"));  //0：未退款；1：已退款；2：处理中
				    System.out.print("---错误码---"+orderDetail.getDataValue("errorCode"));  //1已清算，0待清算
				    System.out.println("---错误说明---"+orderDetail.getDataValue("errorMsg"));
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
