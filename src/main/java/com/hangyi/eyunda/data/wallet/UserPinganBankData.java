package com.hangyi.eyunda.data.wallet;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.BankCode2;

public class UserPinganBankData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String bindId = ""; // 平安银行绑定id
	private String bankName = "";
	private String cardNo = "";
	private String telephone = ""; // 银行预留手机号

	private BankCode2 bankCode = null;

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public BankCode2 getBankCode() {
		return bankCode;
	}

	public void setBankCode(BankCode2 bankCode) {
		this.bankCode = bankCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
