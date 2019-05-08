package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "YydUserAccount")
public class YydUserAccount extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long userId = 0L; // 用户ID
	@Enumerated(EnumType.ORDINAL)
	private PayStyleCode payStyle = null; // 支付方式
	@Column(nullable = true, length = 100)
	private String accounter = ""; // 开户人
	@Column(nullable = true, length = 100)
	private String accountNo = ""; // 账号
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode payPassWord = YesNoCode.no; // 设置支付密码
	@Column(length = 100)
	private String idCode = ""; // 身份证号码
	@Column(length = 100)
	private String mobile = ""; // 平安绑定用手机

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
	 * 取得开户人
	 */
	public String getAccounter() {
		return accounter;
	}

	/**
	 * 设置开户人
	 */
	public void setAccounter(String accounter) {
		this.accounter = accounter;
	}

	/**
	 * 取得支付方式
	 */
	public PayStyleCode getPayStyle() {
		return payStyle;
	}

	/**
	 * 设置支付方式
	 */
	public void setPayStyle(PayStyleCode payStyle) {
		this.payStyle = payStyle;
	}

	/**
	 * 取得账号
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * 设置账号
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public YesNoCode getPayPassWord() {
		return payPassWord;
	}
	
	public void setPayPassWord(YesNoCode payPassWord) {
		this.payPassWord = payPassWord;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
