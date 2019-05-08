package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydWalletSettle")
public class YydWalletSettle extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间
	@Column
	private Long userId = 0L; // 用户ID
	@Column
	private Long walletId = 0L; // 钱包交易ID
	@Column(nullable = false, length = 20)
	private String subject = ""; // 标题
	@Column
	private Double money = 0.00D; // 交易金额，可正可负
	@Column
	private Double usableMoney = 0.00D; // 可用余额
	@Column
	private Double fetchableMoney = -1.00D; // 可取余额，大于等于0，负数表示是今天交易记录

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getUsableMoney() {
		return usableMoney;
	}

	public void setUsableMoney(Double usableMoney) {
		this.usableMoney = usableMoney;
	}

	public Double getFetchableMoney() {
		return fetchableMoney;
	}

	public void setFetchableMoney(Double fetchableMoney) {
		this.fetchableMoney = fetchableMoney;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
