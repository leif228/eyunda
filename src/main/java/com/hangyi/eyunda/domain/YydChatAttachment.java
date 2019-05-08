package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.ImgTypeCode;

@Entity
@Table(name = "YydChatAttachment")
public class YydChatAttachment extends BaseEntity {
private static final long serialVersionUID = -1L;

	@Column
	private Long messageId = 0L; // 聊天ID
	@Enumerated(EnumType.ORDINAL)
	private ImgTypeCode imgType = null; //附件类型
	@Column(nullable = true, length = 500)
	private String title = ""; // 附件标题
	@Column(nullable = false, length = 200)
	private String url = ""; // 路径
	@Column
	private Calendar createTime = Calendar.getInstance(); // 上传时间
	@Column
	private Long size = 0L; // 文件大小

	/**
	 * 取得聊天ID
	 */
	public Long getMessageId() {
		return messageId;
	}
	/**
	 * 设置聊天ID
	 */
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	/**
	 * 取得附件类型
	 */
	public ImgTypeCode getImgType() {
		return imgType;
	}
	/**
	 * 设置附件类型
	 */
	public void setImgType(ImgTypeCode imgType) {
		this.imgType = imgType;
	}

	/**
	 * 取得附件标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置附件标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 取得路径
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置路径
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 取得上传时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}
	/**
	 * 设置上传时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得文件大小
	 */
	public Long getSize() {
		return size;
	}
	/**
	 * 设置文件大小
	 */
	public void setSize(Long size) {
		this.size = size;
	}

}
