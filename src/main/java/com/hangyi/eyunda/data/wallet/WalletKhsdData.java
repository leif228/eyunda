package com.hangyi.eyunda.data.wallet;

import java.util.Map;

import com.hangyi.eyunda.data.BaseData;

public class WalletKhsdData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String createTime = ""; // 建立时间
	private String paymentNo = ""; // 支付序列号：客户号10位＋日期8位YYYYMMDD＋序列号8位
	private String tranFunc = ""; // 功能号20位
	private String sendParames = ""; // 发送参数
	private String recvParames = ""; // 接收参数
	private String rspCode = ""; // 银行返回的应答码
	private String rspMsg = ""; // 银行返回的应答描述

	public WalletKhsdData() {
		super();
	}

	public WalletKhsdData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.createTime = (String) params.get("createTime");
			this.paymentNo = (String) params.get("paymentNo");
			this.tranFunc = (String) params.get("tranFunc");
			this.sendParames = (String) params.get("sendParames");
			this.recvParames = (String) params.get("recvParames");
			this.rspCode = (String) params.get("rspCode");
			this.rspMsg = (String) params.get("rspMsg");
		}
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getTranFunc() {
		return tranFunc;
	}

	public void setTranFunc(String tranFunc) {
		this.tranFunc = tranFunc;
	}

	public String getSendParames() {
		return sendParames;
	}

	public void setSendParames(String sendParames) {
		this.sendParames = sendParames;
	}

	public String getRecvParames() {
		return recvParames;
	}

	public void setRecvParames(String recvParames) {
		this.recvParames = recvParames;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspMsg() {
		return rspMsg;
	}

	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
