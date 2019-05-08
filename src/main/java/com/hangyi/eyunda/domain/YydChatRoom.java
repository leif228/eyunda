package com.hangyi.eyunda.domain;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "YydChatRoom")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydChatRoom extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 100)
	private String roomSubject = ""; // 聊天室主题
	@Column(nullable = false, length = 200)
	private String roomLogo = ""; // 聊天室Logo
	@Column(nullable = false, length = 100)
	private String recentlyTitle = ""; // 最近聊天主题
	@Column
	private Calendar recentlyTime = Calendar.getInstance(); // 最近聊天时间
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "roomId")
	private List<YydChatRoomStatus> statuss;

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

	public Calendar getRecentlyTime() {
		return recentlyTime;
	}

	public void setRecentlyTime(Calendar recentlyTime) {
		this.recentlyTime = recentlyTime;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public List<YydChatRoomStatus> getStatuss() {
		return statuss;
	}

	public void setStatuss(List<YydChatRoomStatus> statuss) {
		this.statuss = statuss;
	}

}
