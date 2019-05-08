package com.hangyi.eyunda.data.wallet;

import java.util.List;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.util.CalendarUtil;

public class ZjjzPayData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String tranFlag = "";// 记账标志 必输 1：转出 2：转入
	private String tranFlagDesc = "";
	private String tranStatus = "";// 交易状态 必输 0：成功
	private String tranAmount = "";// 金额 必输
	private String tranDate = "";// 交易日期 必输
	private String tranTime = "";// 交易时间 必输
	private String dateTime = "";
	private String frontLogNo = "";// 前置流水号 必输
	private String keepType = "";// 记账类型 必输 1：会员支付 2：会员冻结 3：会员解冻 4：登记挂账 5：清分支付
									// 6：下单预支付 7：确认并付款 8：会员退款 9：支付到平台 14:清分冻结
	private String keepTypeDesc = "";
	private String inCustAcctId = "";// 转入子账户 可选
	private String outCustAcctId = "";// 转出子账户 可选
	private String note = "";// 备注 可选 返回交易订单号

	public ZjjzPayData() {
		super();
	}

	public ZjjzPayData(List<String> lst) {
		super();
		if (lst.size() > 0)
			this.setTranFlag(lst.get(0));
		if (lst.size() > 1)
			this.tranStatus = lst.get(1);
		if (lst.size() > 2)
			this.tranAmount = lst.get(2);
		if (lst.size() > 3)
			this.tranDate = lst.get(3);
		if (lst.size() > 4)
			this.tranTime = lst.get(4);

		if (this.tranDate.length() == 8 && this.tranTime.length() == 6)
			this.dateTime = CalendarUtil
					.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.parseYYYYMMDDHHMMSS(this.tranDate + this.tranTime));

		if (lst.size() > 5)
			this.frontLogNo = lst.get(5);
		if (lst.size() > 6)
			this.setKeepType(lst.get(6));
		if (lst.size() > 7)
			this.inCustAcctId = lst.get(7);
		if (lst.size() > 8)
			this.outCustAcctId = lst.get(8);
		if (lst.size() > 9)
			this.note = lst.get(9);
	}

	public String getTranFlag() {
		return tranFlag;
	}

	public void setTranFlag(String tranFlag) {
		this.tranFlag = tranFlag;
		this.tranFlagDesc = "1".equals(tranFlag) ? "转出" : "2".equals(tranFlag) ? "转入" : "";
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

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
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

	public String getFrontLogNo() {
		return frontLogNo;
	}

	public void setFrontLogNo(String frontLogNo) {
		this.frontLogNo = frontLogNo;
	}

	public String getKeepType() {
		return keepType;
	}

	public void setKeepType(String keepType) {
		this.keepType = keepType;
		this.keepTypeDesc = "1".equals(keepType) ? "会员支付"
				: "2".equals(keepType) ? "会员冻结"
						: "3".equals(keepType) ? "会员解冻"
								: "4".equals(keepType) ? "登记挂账"
										: "5".equals(keepType) ? "清分支付"
												: "6".equals(keepType) ? "下单预支付"
														: "7".equals(keepType) ? "确认并付款" : "8".equals(keepType) ? "会员退款"
																: "9".equals(keepType) ? "支付到平台"
																		: "14".equals(keepType) ? "清分冻结" : "";
	}

	public String getKeepTypeDesc() {
		return keepTypeDesc;
	}

	public void setKeepTypeDesc(String keepTypeDesc) {
		this.keepTypeDesc = keepTypeDesc;
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
		return "记账标志:" + tranFlag + ",交易状态:" + tranStatus + ",金额:" + tranAmount + ",交易日期:" + tranDate + ",交易时间:"
				+ tranTime + ",前置流水号:" + frontLogNo + ",记账类型:" + keepType + ",转入子账户:" + inCustAcctId + ",转出子账户:"
				+ outCustAcctId + ",备注:" + note;
	}
}
