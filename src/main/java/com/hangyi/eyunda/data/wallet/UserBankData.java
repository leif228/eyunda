package com.hangyi.eyunda.data.wallet;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.BankCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

public class UserBankData extends BaseData {

	private static final long serialVersionUID = -1L;

	private Long id = 0L;
	private Long userId = 0L;
	private String bindId = ""; // 平安银行绑定id
	private String accountName = "";
	private String cardNo = "";
	private String telephone = ""; // 银行预留手机号
	private BankCode bankCode = BankCode.EYUNDA;
	private String bankName = "";
	private String bindTime = ""; // 绑定时间
	private YesNoCode isRcvCard = YesNoCode.no;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BankCode getBankCode() {
		return bankCode;
	}

	public void setBankCode(BankCode bankCode) {
		this.bankCode = bankCode;
	}

	public String getBindTime() {
		return bindTime;
	}

	public void setBindTime(String bindTime) {
		this.bindTime = bindTime;
	}

	public YesNoCode getIsRcvCard() {
		return isRcvCard;
	}

	public void setIsRcvCard(YesNoCode isRcvCard) {
		this.isRcvCard = isRcvCard;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
