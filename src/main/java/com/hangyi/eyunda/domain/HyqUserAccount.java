package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "HyqUserAccount")
public class HyqUserAccount extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long userId = 0L; // 用户ID
	@Enumerated(EnumType.ORDINAL)
	private PayStyleCode payStyle = null; // 支付方式
	@Column(nullable = true, length = 100)
	private String accounter = ""; // 开户人
	@Column(nullable = true, length = 50)
	private String accountNo = ""; // 账号
	@Column(nullable = true, length = 100)
	private String idCode = ""; // 身份证号
	@Column(nullable = true, length = 100)
	private String mobile = ""; // 手机号
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode payPassWord = YesNoCode.no; // 设置支付密码

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

	/**
	 * 取得身份证号
	 */
	public String getIdCode() {
		return idCode;
	}

	/**
	 * 设置身份证号
	 */
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	/**
	 * 取得手机号
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public YesNoCode getPayPassWord() {
		return payPassWord;
	}

	public void setPayPassWord(YesNoCode payPassWord) {
		this.payPassWord = payPassWord;
	}

}
