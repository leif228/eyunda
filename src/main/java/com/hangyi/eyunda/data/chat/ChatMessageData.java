package com.hangyi.eyunda.data.chat;

import com.hangyi.eyunda.data.BaseData;

public class ChatMessageData extends BaseData {

	private static final long serialVersionUID = -1L;
	
	private Long id = 0l;
	private String roomId = ""; // 聊天室ID
	private Long senderId = 0L; // 发送者ID
	private String senderName = ""; // 发送者姓名
	private String senderLogo = ""; // 发送者Logo
	private String content = ""; // 消息内容
	private String createTime = "";// 发送时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderLogo() {
		return senderLogo;
	}

	public void setSenderLogo(String senderLogo) {
		this.senderLogo = senderLogo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
