package com.hangyi.eyunda.data.wallet;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

public class WalletSettleData extends BaseData {

	private static final long serialVersionUID = -1L;

	private String createTime = ""; // 建立时间
	private Long userId = 0L; // 用户ID
	private Long walletId = 0L; // 钱包交易ID
	private String subject = ""; // 标题
	private Double money = 0.00D; // 交易金额，可正可负
	private Double usableMoney = 0.00D; // 可用余额
	private Double fetchableMoney = 0.00D; // 可取余额，大于等于0，负数表示是今天交易记录

	// ##########
	private UserData userData = null;
	private Double jzbTotalBalance = 0.00D; // 见证宝可用余额
	private Double jzbTotalTranOutAmount = 0.00D; // 见证宝可取余额
	private YesNoCode checkOpt = YesNoCode.no; // 帐务不平衡标志
	// ##########
	private String payDate = "";
	private String payTime = "";

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
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

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public Double getJzbTotalBalance() {
		return jzbTotalBalance;
	}

	public void setJzbTotalBalance(Double jzbTotalBalance) {
		this.jzbTotalBalance = jzbTotalBalance;
	}

	public Double getJzbTotalTranOutAmount() {
		return jzbTotalTranOutAmount;
	}

	public void setJzbTotalTranOutAmount(Double jzbTotalTranOutAmount) {
		this.jzbTotalTranOutAmount = jzbTotalTranOutAmount;
	}

	public YesNoCode getCheckOpt() {
		return checkOpt;
	}

	public void setCheckOpt(YesNoCode checkOpt) {
		this.checkOpt = checkOpt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
