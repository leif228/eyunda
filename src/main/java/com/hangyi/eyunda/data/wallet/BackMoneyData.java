package com.hangyi.eyunda.data.wallet;

import java.util.List;

import com.hangyi.eyunda.data.BaseData;

public class BackMoneyData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String withDrawDate = "";// 退单日期YYYYMMDD
	private String thirdLogNo = "";// 市场流水号C(22)
	private String frontLogNo = "";// 银行流水号C(14)
	private String remark = "";// 原转账备注C(120)
	private String withDrawRemark = "";// 退单原因C(600)

	public BackMoneyData() {
		super();
	}

	public BackMoneyData(List<String> lst) {
		super();
		if (lst.size() > 0)
			this.withDrawDate = lst.get(0);
		if (lst.size() > 1)
			this.thirdLogNo = lst.get(1);
		if (lst.size() > 2)
			this.frontLogNo = lst.get(2);
		if (lst.size() > 3)
			this.remark = lst.get(3);
		if (lst.size() > 4)
			this.withDrawRemark = lst.get(4);
	}

	public String getWithDrawDate() {
		return withDrawDate;
	}

	public void setWithDrawDate(String withDrawDate) {
		this.withDrawDate = withDrawDate;
	}

	public String getThirdLogNo() {
		return thirdLogNo;
	}

	public void setThirdLogNo(String thirdLogNo) {
		this.thirdLogNo = thirdLogNo;
	}

	public String getFrontLogNo() {
		return frontLogNo;
	}

	public void setFrontLogNo(String frontLogNo) {
		this.frontLogNo = frontLogNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWithDrawRemark() {
		return withDrawRemark;
	}

	public void setWithDrawRemark(String withDrawRemark) {
		this.withDrawRemark = withDrawRemark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String toString() {
		return "退单日期:" + withDrawDate + ",市场流水号:" + thirdLogNo + ",银行流水号:" + frontLogNo + ",原转账备注:" + remark + ",退单原因:"
				+ withDrawRemark;
	}

}
