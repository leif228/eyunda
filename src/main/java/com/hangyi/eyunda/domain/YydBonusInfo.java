package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "YydBonusInfo", indexes = { @Index(name = "idx_senderId", columnList = "senderId", unique = false),
		@Index(name = "idx_keyId", columnList = "keyId", unique = false) })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydBonusInfo extends BaseEntity {
	private static final long serialVersionUID = -1L;
	
	@Column
	private Long senderId = 0L; // 发送者ID
	@Column(nullable = true)
	private Long receiverId = 0L; // 接收者ID（如果指定接收者，先填入。需要抢的红包，后续拆红包时更新）
	@Column
	private Calendar createTime = Calendar.getInstance(); // 发布红包时间
	@Column
	private Calendar invalidTime = Calendar.getInstance(); // 红包失效时间，24小时红包自动失效，退还给发放者
	@Column(nullable = true)
	private Calendar receiveTime = null; // 拆红包时间，空表示红包未拆
	@Column
	private String keyId = ""; // 总红包ID（一次可发送多个红包，md5（sendId＋money＋time＋num）,每次红包唯一ID）
	@Column
	private Double money = 0.00D; // 红包金额（元）
	@Column
	private Double totalMoney = 0.00D; // 总金额
	@Column(nullable = true, length = 2000)
	private String remark = ""; // 备注信息
	@Column(nullable = true, length = 2000)
	private String candidateIds = ""; // 候选人Id列表（群发红包时使用）
	
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	public Calendar getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	public Calendar getInvalidTime() {
		return invalidTime;
	}
	public void setInvalidTime(Calendar invalidTime) {
		this.invalidTime = invalidTime;
	}
	public Calendar getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Calendar receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
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
	public String getCandidateIds() {
		return candidateIds;
	}
	public void setCandidateIds(String candidateIds) {
		this.candidateIds = candidateIds;
	}

}
