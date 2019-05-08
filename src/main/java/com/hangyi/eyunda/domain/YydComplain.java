package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "YydComplain")
public class YydComplain extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long userId = 0L; // 用户ID
	@Column(nullable = false, length = 2000)
	private String content = ""; // 问题内容
	@Column(nullable = true, length = 2000)
	private String reply = ""; // 回复内容
	@Column
	private Calendar createTime = Calendar.getInstance(); // 创建时间
	@Column
	private Calendar replyTime = Calendar.getInstance(); // 回复时间
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode status = YesNoCode.no; // 是否回复
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode opinion = YesNoCode.yes; // 投诉或建议

	/**
	 * 取得用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 取得问题内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置问题内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 取得回复内容
	 */
	public String getReply() {
		return reply;
	}

	/**
	 * 设置回复内容
	 */
	public void setReply(String reply) {
		this.reply = reply;
	}

	/**
	 * 取得创建时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得回复时间
	 */
	public Calendar getReplyTime() {
		return replyTime;
	}

	/**
	 * 设置回复时间
	 */
	public void setReplyTime(Calendar replyTime) {
		this.replyTime = replyTime;
	}

	/**
	 * 取得是否回复
	 */
	public YesNoCode getStatus() {
		return status;
	}

	/**
	 * 设置是否回复
	 */
	public void setStatus(YesNoCode status) {
		this.status = status;
	}

	/**
	 * 取得投诉或建议
	 */
	public YesNoCode getOpinion() {
		return opinion;
	}

	/**
	 * 设置投诉或建议
	 */
	public void setOpinion(YesNoCode opinion) {
		this.opinion = opinion;
	}

}
