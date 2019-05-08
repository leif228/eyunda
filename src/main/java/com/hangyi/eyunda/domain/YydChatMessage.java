package com.hangyi.eyunda.domain;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "YydChatMessage", indexes = { @Index(name = "idx_roomId", columnList = "roomId", unique = false) })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydChatMessage extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long roomId = 0L; // 聊天室ID
	@Column
	private Long senderId = 0L; // 发送者ID
	@Column(nullable = false, length = 4096)
	private String content = ""; // 消息内容
	@Column
	private Calendar createTime = Calendar.getInstance(); // 发送时间

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "msgId")
	private List<YydChatMsgStatus> statuss;

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public List<YydChatMsgStatus> getStatuss() {
		return statuss;
	}

	public void setStatuss(List<YydChatMsgStatus> statuss) {
		this.statuss = statuss;
	}

}
