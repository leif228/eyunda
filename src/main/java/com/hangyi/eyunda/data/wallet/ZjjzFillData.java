package com.hangyi.eyunda.data.wallet;

import java.util.List;

import com.hangyi.eyunda.data.BaseData;

public class ZjjzFillData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String tranType = "";// 入账类型 必输 02：会员充值 03：资金挂账
	private String thirdCustId = "";// 交易网会员代码 必输
	private String custAcctId = "";// 子账户账号 必输
	private String tranAmount = "";// 入金金额 必输
	private String inAcctId = "";// 入金账号 必输
	private String inAcctIdName = "";// 入金账户名称 必输
	private String ccyCode = "";// 币种 必输
	private String acctDate = "";// 会计日期 必输 即银行主机记账日期
	private String bankName = "";// 银行名称 必输 付款账户银行名称
	private String note = "";// 转账备注 必输
	private String frontLogNo = "";// 前置流水号 必输

	public ZjjzFillData() {
		super();
	}

	public ZjjzFillData(List<String> lst) {
		super();
		if (lst.size() > 0)
			this.tranType = lst.get(0);
		if (lst.size() > 1)
			this.thirdCustId = lst.get(1);
		if (lst.size() > 2)
			this.custAcctId = lst.get(2);
		if (lst.size() > 3)
			this.tranAmount = lst.get(3);
		if (lst.size() > 4)
			this.inAcctId = lst.get(4);
		if (lst.size() > 5)
			this.inAcctIdName = lst.get(5);
		if (lst.size() > 6)
			this.ccyCode = lst.get(6);
		if (lst.size() > 7)
			this.acctDate = lst.get(7);
		if (lst.size() > 8)
			this.bankName = lst.get(8);
		if (lst.size() > 9)
			this.note = lst.get(9);
		if (lst.size() > 10)
			this.frontLogNo = lst.get(10);
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getThirdCustId() {
		return thirdCustId;
	}

	public void setThirdCustId(String thirdCustId) {
		this.thirdCustId = thirdCustId;
	}

	public String getCustAcctId() {
		return custAcctId;
	}

	public void setCustAcctId(String custAcctId) {
		this.custAcctId = custAcctId;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getInAcctId() {
		return inAcctId;
	}

	public void setInAcctId(String inAcctId) {
		this.inAcctId = inAcctId;
	}

	public String getInAcctIdName() {
		return inAcctIdName;
	}

	public void setInAcctIdName(String inAcctIdName) {
		this.inAcctIdName = inAcctIdName;
	}

	public String getCcyCode() {
		return ccyCode;
	}

	public void setCcyCode(String ccyCode) {
		this.ccyCode = ccyCode;
	}

	public String getAcctDate() {
		return acctDate;
	}

	public void setAcctDate(String acctDate) {
		this.acctDate = acctDate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFrontLogNo() {
		return frontLogNo;
	}

	public void setFrontLogNo(String frontLogNo) {
		this.frontLogNo = frontLogNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String toString() {
		return "入账类型:" + tranType + ",交易网会员代码:" + thirdCustId + ",子账户账号:" + custAcctId + ",入金金额:" + tranAmount
				+ ",入金账号:" + inAcctId + ",入金账户名称:" + inAcctIdName + ",币种:" + ccyCode + ",会计日期:" + acctDate + ",银行名称:"
				+ bankName + ",转账备注:" + note + ",前置流水号:" + frontLogNo;
	}
}
