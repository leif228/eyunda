package com.hangyi.eyunda.data.hyquan;

import java.util.Map;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.ApplyStatusCode;

public class HyqFriendData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private Long userId = 0L; // 用户ID
	private Long tagId = 0L; // 标签ID
	private Long friendId = 0L; // 好友ID
	private ApplyStatusCode status = ApplyStatusCode.apply; // 状态
	private String createTime = ""; // 建立时间

	private HyqUserData userData = new HyqUserData(); // 我方
	private HyqUserData friendData = new HyqUserData(); // 对方
	
	private Long applyId = 0L; // 申请方用户ID

	public HyqFriendData() {
		super();
	}

	@SuppressWarnings("unchecked")
	public HyqFriendData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.id = ((Double) params.get("id")).longValue();
			this.userId = ((Double) params.get("userId")).longValue();
			this.tagId = ((Double) params.get("tagId")).longValue();
			this.friendId = ((Double) params.get("friendId")).longValue();
			this.status = ApplyStatusCode.valueOf((String) params.get("status"));
			this.createTime = (String) params.get("createTime");

			this.userData = new HyqUserData((Map<String, Object>) params.get("userData"));
			this.friendData = new HyqUserData((Map<String, Object>) params.get("friendData"));

			this.applyId = ((Double) params.get("applyId")).longValue();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public HyqUserData getUserData() {
		return userData;
	}

	public void setUserData(HyqUserData userData) {
		this.userData = userData;
	}

	public HyqUserData getFriendData() {
		return friendData;
	}

	public void setFriendData(HyqUserData friendData) {
		this.friendData = friendData;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
