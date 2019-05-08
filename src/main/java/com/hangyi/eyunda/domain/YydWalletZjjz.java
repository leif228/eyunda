package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydWalletZjjz")
public class YydWalletZjjz extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间
	@Column(nullable = false, length = 20)
	private String logNo = ""; // 接口调用序列号：日期14位YYYYMMDDhhmmss＋序列号6位
	@Column(nullable = false, length = 26)
	private String paymentNo = ""; // 支付序列号：客户号10位＋日期8位YYYYMMDD＋序列号8位
	@Column(nullable = false, length = 4)
	private String tranFunc = ""; // 功能号4位
	@Column(nullable = false, length = 8000)
	private String sendParames = ""; // 发送参数
	@Column(nullable = true, length = 8000)
	private String recvParames = ""; // 接收参数
	@Column(nullable = true, length = 6)
	private String rspCode = ""; // 银行返回的应答码
	@Column(nullable = true, length = 200)
	private String rspMsg = ""; // 银行返回的应答描述
	@Column(nullable = false, length = 10)
	private String funcFlag = "0"; // 功能标志

	public String getLogNo() {
		return logNo;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
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

	public String getFuncFlag() {
		return funcFlag;
	}

	public void setFuncFlag(String funcFlag) {
		this.funcFlag = funcFlag;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
