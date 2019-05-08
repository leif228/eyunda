package com.hangyi.eyunda.data.chat;

import com.hangyi.eyunda.data.BaseData;

public class ChatRoomData extends BaseData {

	private static final long serialVersionUID = -1L;

	private Long id = 0L;
	private String roomSubject = ""; // 聊天室主题
	private String roomLogo = ""; // 聊天室Logo
	private String recentlyTitle = ""; // 最近聊天主题
	private String recentlyTime = ""; // 最近聊天时间

	private Integer noReadCount = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoomSubject() {
		return roomSubject;
	}

	public void setRoomSubject(String roomSubject) {
		this.roomSubject = roomSubject;
	}

	public String getRoomLogo() {
		return roomLogo;
	}

	public void setRoomLogo(String roomLogo) {
		this.roomLogo = roomLogo;
	}

	public String getRecentlyTitle() {
		return recentlyTitle;
	}

	public void setRecentlyTitle(String recentlyTitle) {
		this.recentlyTitle = recentlyTitle;
	}

	public String getRecentlyTime() {
		return recentlyTime;
	}

	public void setRecentlyTime(String recentlyTime) {
		this.recentlyTime = recentlyTime;
	}

	public Integer getNoReadCount() {
		return noReadCount;
	}

	public void setNoReadCount(Integer noReadCount) {
		this.noReadCount = noReadCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
