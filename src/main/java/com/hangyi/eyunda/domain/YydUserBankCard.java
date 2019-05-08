package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.BankCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "YydUserBankCard")
public class YydUserBankCard extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long userId = 0L; // 用户ID
	@Column(nullable = false, length = 20)
	private String accountName = ""; // 帐户名
	@Column(nullable = false, length = 100)
	private String cardNo = ""; // 帐号
	@Enumerated(EnumType.ORDINAL)
	private BankCode bankCode = null; // 银行代码
	@Column
	private Calendar bindTime = Calendar.getInstance(); // 绑定时间
	@Column
	private YesNoCode isRcvCard = YesNoCode.no; // 绑定时间

	/**
	 * 取得用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 取得帐户名
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * 设置帐户名
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * 取得帐号
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * 设置帐号
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * 取得银行代码
	 */
	public BankCode getBankCode() {
		return bankCode;
	}

	/**
	 * 设置银行代码
	 */
	public void setBankCode(BankCode bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 取得绑定时间
	 */
	public Calendar getBindTime() {
		return bindTime;
	}

	/**
	 * 设置绑定时间
	 */
	public void setBindTime(Calendar bindTime) {
		this.bindTime = bindTime;
	}

	/**
	 * 取得绑定的收款卡
	 */
	public YesNoCode getIsRcvCard() {
		return isRcvCard;
	}

	/**
	 * 设置绑定的收款卡
	 */
	public void setIsRcvCard(YesNoCode isRcvCard) {
		this.isRcvCard = isRcvCard;
	}

}
