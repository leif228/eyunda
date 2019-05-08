package com.hangyi.eyunda.data.wallet;

import java.util.List;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.util.CalendarUtil;

public class ZjjzFetchData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String tranFlag = "";// 记账标志 必输 01:提现 02:清分
	private String tranFlagDesc = "";
	private String tranStatus = "";// 交易状态 必输 0：成功
	private String funcMsg = "";// 记账说明 可选
	private String thirdCustId = "";// 交易网会员代码 必输
	private String custAcctId = "";// 子账户 必输
	private String custName = "";// 子账户名称 必输
	private String tranAmount = "";// 交易金额 必输
	private String handFee = "";// 手续费 可选
	private String tranDate = "";// 交易日期 必输
	private String tranTime = "";// 交易时间 必输
	private String dateTime = "";
	private String frontLogNo = "";// 前置流水号 必输
	private String note = "";// 备注 可选

	public ZjjzFetchData() {
		super();
	}

	public ZjjzFetchData(List<String> lst) {
		super();
		if (lst.size() > 0)
			this.setTranFlag(lst.get(0));
		if (lst.size() > 1)
			this.tranStatus = lst.get(1);
		if (lst.size() > 2)
			this.funcMsg = lst.get(2);
		if (lst.size() > 3)
			this.thirdCustId = lst.get(3);
		if (lst.size() > 4)
			this.custAcctId = lst.get(4);
		if (lst.size() > 5)
			this.custName = lst.get(5);
		if (lst.size() > 6)
			this.tranAmount = lst.get(6);
		if (lst.size() > 7)
			this.handFee = lst.get(7);
		if (lst.size() > 8)
			this.tranDate = lst.get(8);
		if (lst.size() > 9)
			this.tranTime = lst.get(9);

		if (this.tranDate.length() == 8 && this.tranTime.length() == 6)
			this.dateTime = CalendarUtil
					.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.parseYYYYMMDDHHMMSS(this.tranDate + this.tranTime));

		if (lst.size() > 10)
			this.frontLogNo = lst.get(10);
		if (lst.size() > 11)
			this.note = lst.get(11);
	}

	public String getTranFlag() {
		return tranFlag;
	}

	public void setTranFlag(String tranFlag) {
		this.tranFlag = tranFlag;
		this.tranFlagDesc = "01".equals(tranFlag) ? "提现" : "02".equals(tranFlag) ? "清分" : "";
	}

	public String getTranFlagDesc() {
		return tranFlagDesc;
	}

	public void setTranFlagDesc(String tranFlagDesc) {
		this.tranFlagDesc = tranFlagDesc;
	}

	public String getTranStatus() {
		return tranStatus;
	}

	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}

	public String getFuncMsg() {
		return funcMsg;
	}

	public void setFuncMsg(String funcMsg) {
		this.funcMsg = funcMsg;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getHandFee() {
		return handFee;
	}

	public void setHandFee(String handFee) {
		this.handFee = handFee;
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

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getFrontLogNo() {
		return frontLogNo;
	}

	public void setFrontLogNo(String frontLogNo) {
		this.frontLogNo = frontLogNo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String toString() {
		return "记账标志:" + tranFlag + ",交易状态:" + tranStatus + ",记账说明:" + funcMsg + ",交易网会员代码:" + thirdCustId + ",子账户:"
				+ custAcctId + ",子账户名称:" + custName + ",交易金额:" + tranAmount + ",手续费:" + handFee + ",交易日期:" + tranDate
				+ ",交易时间:" + tranTime + ",前置流水号:" + frontLogNo + ",备注:" + note;
	}
}
