package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.ApplyStatusCode;

@Entity
@Table(name = "HyqFriend")
public class HyqFriend extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long userId = 0L; // 用户ID
	@Column
	private Long tagId = 0L; // 标签ID
	@Column
	private Long friendId = 0L; // 好友ID
	@Enumerated(EnumType.ORDINAL)
	private ApplyStatusCode status = ApplyStatusCode.apply; // 状态
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public ApplyStatusCode getStatus() {
		return status;
	}

	public void setStatus(ApplyStatusCode status) {
		this.status = status;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
