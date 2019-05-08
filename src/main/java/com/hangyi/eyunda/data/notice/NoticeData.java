package com.hangyi.eyunda.data.notice;

import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.NoticeTopCode;
import com.hangyi.eyunda.domain.enumeric.NtcColumnCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

public class NoticeData extends BaseData {
	private static final long serialVersionUID = -1L;
	
	private Long id = 0L; // ID
	private NtcColumnCode ntcColumn = null; // 栏目
	private Long pointNum = 0L; // 点击数
	private String title = ""; // 标题
	private String content = ""; // 内容
	private String createTime = ""; // 建立时间
	private String publishTime = ""; // 发布时间
	private ReleaseStatusCode releaseStatus = null; // 发布状态
	private String source = ""; // 来源
	private MultipartFile sourceImg = null; // 大图
	private NoticeTopCode top = NoticeTopCode.no; // 是否置顶
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public MultipartFile getSourceImg() {
		return sourceImg;
	}

	public void setSourceImg(MultipartFile sourceImg) {
		this.sourceImg = sourceImg;
	}

	public NtcColumnCode getNtcColumn() {
		return ntcColumn;
	}

	public void setNtcColumn(NtcColumnCode ntcColumn) {
		this.ntcColumn = ntcColumn;
	}

	public Long getPointNum() {
		return pointNum;
	}

	public void setPointNum(Long pointNum) {
		this.pointNum = pointNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public ReleaseStatusCode getReleaseStatus() {
		return releaseStatus;
	}

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
