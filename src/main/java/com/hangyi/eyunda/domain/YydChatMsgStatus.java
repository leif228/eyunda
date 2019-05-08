package com.hangyi.eyunda.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hangyi.eyunda.domain.enumeric.ReadStatusCode;

@Entity
@Table(name = "YydChatMsgStatus", indexes = { @Index(name = "idx_roomId", columnList = "roomId", unique = false) })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydChatMsgStatus extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long roomId = 0L; // 聊天室ID
	@Column
	private Long msgId = 0L; // 消息ID
	@Column
	private Long userId = 0L; // 聊天室成员ID
	@Enumerated(EnumType.ORDINAL)
	private ReadStatusCode readStatus = ReadStatusCode.noread; // 阅读状态

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ReadStatusCode getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(ReadStatusCode readStatus) {
		this.readStatus = readStatus;
	}

}
