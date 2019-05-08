package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Table(name = "YydNoticeAttachment")
public class YydNoticeAttachment extends BaseEntity {
private static final long serialVersionUID = -1L;

	@Column
	private Long noticeId = 0L; // 公告ID
	@Column
	private Integer no = 0; // 顺序号
	@Column(nullable = true, length = 20)
	private String fileType = ""; // 文件类型
	@Column(nullable = true, length = 200)
	private String title = ""; // 附件标题
	@Column(nullable = false, length = 200)
	private String url = ""; // 附件路径
	@Column
	private Calendar createTime = Calendar.getInstance(); // 上传时间
	@Column
	private Long size = 0L; // 文件大小

	/**
	 * 取得公告ID
	 */
	public Long getNoticeId() {
		return noticeId;
	}
	/**
	 * 设置公告ID
	 */
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	/**
	 * 取得顺序号
	 */
	public Integer getNo() {
		return no;
	}
	/**
	 * 设置顺序号
	 */
	public void setNo(Integer no) {
		this.no = no;
	}

	/**
	 * 取得文件类型
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * 设置文件类型
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
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
	 * 取得附件路径
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置附件路径
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
