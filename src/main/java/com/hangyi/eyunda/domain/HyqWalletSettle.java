package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HyqWalletSettle")
public class HyqWalletSettle extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间
	@Column
	private Long userId = 0L; // 用户ID
	@Column
	private Long walletId = 0L; // 钱包交易ID
	@Column(nullable = true, length = 20)
	private String subject = ""; // 标题
	@Column
	private Double money = 0.00D; // 交易金额
	@Column
	private Double usableMoney = 0.00D; // 可用余额
	@Column
	private Double fetchableMoney = 0.00D; // 可取余额

	/**
	 * 取得建立时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置建立时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

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
	 * 取得钱包交易ID
	 */
	public Long getWalletId() {
		return walletId;
	}

	/**
	 * 设置钱包交易ID
	 */
	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}

	/**
	 * 取得标题
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * 设置标题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 取得交易金额
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * 设置交易金额
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	/**
	 * 取得可用余额
	 */
	public Double getUsableMoney() {
		return usableMoney;
	}

	/**
	 * 设置可用余额
	 */
	public void setUsableMoney(Double usableMoney) {
		this.usableMoney = usableMoney;
	}

	/**
	 * 取得可取余额
	 */
	public Double getFetchableMoney() {
		return fetchableMoney;
	}

	/**
	 * 设置可取余额
	 */
	public void setFetchableMoney(Double fetchableMoney) {
		this.fetchableMoney = fetchableMoney;
	}

}
