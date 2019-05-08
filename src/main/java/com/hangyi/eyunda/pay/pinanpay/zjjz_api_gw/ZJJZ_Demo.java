package com.hangyi.eyunda.pay.pinanpay.zjjz_api_gw;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class ZJJZ_Demo {
	public static void main(String[] args) {
		// TODO code application logic here

		String ServerIPAddress = "192.168.1.198";
		int ServerPort = 7072;

		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数
		HashMap retKeyDict = new HashMap();// 用于存放银行发送报文的参数

		/**
		 * 第一部分：生成发送银行的请求的报文的实例
		 * 
		 */

		/* 生成随机数:当前精确到秒的时间再加6位的数字随机序列 */
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String rdNum = df.format(new Date());
		Random random = new Random();
		int ird = random.nextInt(999999);
		String srd = String.format("%06d", ird);
		String thirdLogNo = rdNum + srd;

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6000"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", "3009"); // 企业代码
		parmaKeyDict.put("ThirdLogNo", thirdLogNo); // 请求流水号
		parmaKeyDict.put("SupAcctId", "11014890107001"); // 资金汇总账号
		parmaKeyDict.put("FuncFlag", "1"); // 功能标志1：开户
		parmaKeyDict.put("ThirdCustId", "pingan002"); // 交易网会员代码
		parmaKeyDict.put("CustProperty", "00"); // 会员属性
		parmaKeyDict.put("NickName", "1"); // 会员昵称
		parmaKeyDict.put("MobilePhone", "18617166125"); // 手机号码
		parmaKeyDict.put("Email", "hwx@163.com"); // 邮箱
		parmaKeyDict.put("Reserve", "保留域"); // 保留域

		/* 获取请求报文 */
		ZJJZ_API_GW msg = new ZJJZ_API_GW();
		String tranMessage = msg.getTranMessage(parmaKeyDict);// 调用函数生成报文

		/* 输出报文结果 */
		System.out.println("第一部分：生成发送银行的请求的报文的实例");
		System.out.println(tranMessage);
		System.out.println("-------------------------------");

		/**
		 * 第二部分：获取银行返回的报文的实例
		 * 
		 */
		/* 发送请求报文 */
		msg.SendTranMessage(tranMessage, ServerIPAddress, ServerPort, retKeyDict);

		/* 获取银行返回报文 */
		String recvMessage = (String) retKeyDict.get("RecvMessage");// 银行返回的报文
		/* 输出报文结果 */
		System.out.println("第二部分：获取银行返回的报文");
		System.out.println(recvMessage);
		System.out.println("-------------------------------");

		/**
		 * 第三部分：解析银行返回的报文的实例
		 * 
		 */
		// recvMessage="A0010301018556                0000000134000000       20150415144303674                 000000交易请求                                                                                            0                 0         0605001CMacmACmAcmaCmac20150415144303999999交易请求                                  1000000121    675                 85561&2&3&4&20150415&20150416&b1&";
		retKeyDict = msg.parsingTranMessageString(recvMessage);
		String rspCode = (String) retKeyDict.get("RspCode");// 银行返回的应答码
		String rspMsg = (String) retKeyDict.get("RspMsg");// 银行返回的应答描述
		String bodyMsg = (String) retKeyDict.get("BodyMsg");
		String custAcctId = (String) retKeyDict.get("CustAcctId");// 银行返回的会员子账户

		/* 输出报文结果 */
		System.out.println("第三部分：解析银行返回的报文");
		System.out.println("返回应答码：");
		System.out.println(rspCode);
		System.out.println("返回应答码描述：");
		System.out.println(rspMsg);
		System.out.println("返回报文体：");
		System.out.println(bodyMsg);
		System.out.println("返回会员子账户：");
		System.out.println(custAcctId);
		System.out.println("-------------------------------");
	}

}
