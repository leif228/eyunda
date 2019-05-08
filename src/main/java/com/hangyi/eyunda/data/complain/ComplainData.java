package com.hangyi.eyunda.data.complain;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

public class ComplainData extends BaseData {

	private static final long serialVersionUID = -1L;

	private Long id = 0L; // id
	private Long userId = 0L; // 用户ID
	private UserData userData = null; // 用户
	private String content = ""; // 问题内容
	private String reply = ""; // 回复内容
	private String createTime = ""; // 创建时间
	private String replyTime = ""; // 回复时间
	private YesNoCode status = YesNoCode.no; // 是否回复
	private YesNoCode opinion = YesNoCode.yes; // 投诉或建议

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

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public YesNoCode getStatus() {
		return status;
	}

	public void setStatus(YesNoCode status) {
		this.status = status;
	}

	public YesNoCode getOpinion() {
		return opinion;
	}

	public void setOpinion(YesNoCode opinion) {
		this.opinion = opinion;
	}

}
