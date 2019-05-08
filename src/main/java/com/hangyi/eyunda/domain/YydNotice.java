package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.NoticeTopCode;
import com.hangyi.eyunda.domain.enumeric.NtcColumnCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

@Entity
@Table(name = "YydNotice")
public class YydNotice extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Enumerated(EnumType.ORDINAL)
	private NtcColumnCode ntcColumn = null; // 栏目
	@Column
	private Long pointNum = 0L; // 点击数
	@Column(nullable = false, length = 200)
	private String title = ""; // 标题
	@Column(nullable = true, length = 200)
	private String source = ""; // 来源
	@Column(nullable = true, length = 4000)
	private String content = ""; // 内容
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间
	@Column
	private Calendar publishTime = Calendar.getInstance(); // 发布时间
	@Enumerated(EnumType.ORDINAL)
	private ReleaseStatusCode releaseStatus = null; // 发布状态
	@Enumerated(EnumType.ORDINAL)
	private NoticeTopCode top = NoticeTopCode.no; // 是否置顶

	/**
	 * 取得栏目
	 */
	public NtcColumnCode getNtcColumn() {
		return ntcColumn;
	}

	/**
	 * 设置栏目
	 */
	public void setNtcColumn(NtcColumnCode ntcColumn) {
		this.ntcColumn = ntcColumn;
	}

	/**
	 * 取得点击数
	 */
	public Long getPointNum() {
		return pointNum;
	}

	/**
	 * 设置点击数
	 */
	public void setPointNum(Long pointNum) {
		this.pointNum = pointNum;
	}

	/**
	 * 取得标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 取得来源
	 */
	public String getSource() {
		return source;
	}

	/**
	 * 设置来源
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * 取得内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 取得建立时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置建立时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得发布时间
	 */
	public Calendar getPublishTime() {
		return publishTime;
	}

	/**
	 * 设置发布时间
	 */
	public void setPublishTime(Calendar publishTime) {
		this.publishTime = publishTime;
	}

	/**
	 * 取得发布状态
	 */
	public ReleaseStatusCode getReleaseStatus() {
		return releaseStatus;
	}

	/**
	 * 设置发布状态
	 */
	public void setReleaseStatus(ReleaseStatusCode releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public NoticeTopCode getTop() {
		return top;
	}

	public void setTop(NoticeTopCode top) {
		this.top = top;
	}

}
