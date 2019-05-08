package com.hangyi.eyunda.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "YydChatRoomStatus", indexes = { @Index(name = "idx_roomId", columnList = "roomId", unique = false) })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydChatRoomStatus extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long roomId = 0L; // 聊天室ID
	@Column
	private Long userId = 0L; // 聊天室成员ID

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
