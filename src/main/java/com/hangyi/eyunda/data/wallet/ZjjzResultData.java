package com.hangyi.eyunda.data.wallet;

import com.hangyi.eyunda.data.BaseData;

public class ZjjzResultData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String tranFlag = "";// 记账标志 TranFlag C(1) 必输 记账标志（1：登记挂账 2：支付 3：提现
									// 4：清分5:下单预支付 6：确认并付款 7：退款 8：支付到平台）
	private String tranStatus = "";// 交易状态 TranStatus C(1) 必输
									// （0：成功，1：失败，2：异常,3:冲正，5：待处理）
	private String tranAmount = "";// 交易金额 TranAmount 9(15) 必输
	private String tranDate = "";// 交易日期 TranDate C(8) 必输
	private String tranTime = "";// 交易时间 TranTime C(6) 必输
	private String inCustAcctId = "";// 转入子账户 InCustAcctId C(32) 可选
	private String outCustAcctId = "";// 转出子账户 OutCustAcctId C(32) 可选
	private String reserve = "";// 交易描述 Reserve C(120) 可选 当提现失败时，返回交易失败原因

	public String getTranFlag() {
		return tranFlag;
	}

	public void setTranFlag(String tranFlag) {
		this.tranFlag = tranFlag;
	}

	public String getTranStatus() {
		return tranStatus;
	}

	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public String getInCustAcctId() {
		return inCustAcctId;
	}

	public void setInCustAcctId(String inCustAcctId) {
		this.inCustAcctId = inCustAcctId;
	}

	public String getOutCustAcctId() {
		return outCustAcctId;
	}

	public void setOutCustAcctId(String outCustAcctId) {
		this.outCustAcctId = outCustAcctId;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
