package com.hangyi.eyunda.data.wallet;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.account.UserData;

public class BonusData extends BaseData {

	private static final long serialVersionUID = -1L;
	private Long id=0L;
	private Double money = 0.00D;//红包金额
	private Double totalMoney = 0.00D;//总金额
	private String remark = ""; //说明
	// ##########
	private UserData senderData = null;//发送者
	private UserData receiverData = null;//接收者
	private String keyId="";//生成的唯一Id
	private String createTime = ""; // 建立时间
	private String receiveTime = ""; // 领取时间
	private String invalidTime = ""; // 失效时间
	private String candidateIds = ""; // 候选人Id列表（群发红包时使用）
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public UserData getSenderData() {
		return senderData;
	}

	public void setSenderData(UserData senderData) {
		this.senderData = senderData;
	}

	public UserData getReceiverData() {
		return receiverData;
	}

	public void setReceiverData(UserData receiverData) {
		this.receiverData = receiverData;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getCandidateIds() {
		return candidateIds;
	}

	public void setCandidateIds(String candidateIds) {
		this.candidateIds = candidateIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
