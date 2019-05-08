package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.ColumnCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

@Entity
@Table(name = "YydCarrierIssue")
public class YydCarrierIssue extends BaseEntity {
private static final long serialVersionUID = -1L;

	@Column
	private Long carrierId = 0L; // 承运人ID
	@Enumerated(EnumType.ORDINAL)
	private ColumnCode columnCode = null; //栏目
	@Column
	private Integer no = 0; // 序号
	@Column(nullable = true, length = 200)
	private String title = ""; // 标题
	@Column(nullable = true, length = 200)
	private String source = ""; // 来源
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间
	@Column(nullable = true, length = 200)
	private String bigImage = ""; // 大图
	@Column(nullable = true, length = 200)
	private String smallImage = ""; // 小图
	@Column(nullable = true, length = 16000)
	private String content = ""; // 内容
	@Enumerated(EnumType.ORDINAL)
	private ReleaseStatusCode releaseStatus = null; //状态
	@Column
	private Calendar releaseTime = Calendar.getInstance(); // 发布时间
	@Column
	private Integer pointCount = 0; // 点击数

	/**
	 * 取得承运人ID
	 */
	public Long getCarrierId() {
		return carrierId;
	}
	/**
	 * 设置承运人ID
	 */
	public void setCarrierId(Long carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * 取得栏目
	 */
	public ColumnCode getColumnCode() {
		return columnCode;
	}
	/**
	 * 设置栏目
	 */
	public void setColumnCode(ColumnCode columnCode) {
		this.columnCode = columnCode;
	}

	/**
	 * 取得序号
	 */
	public Integer getNo() {
		return no;
	}
	/**
	 * 设置序号
	 */
	public void setNo(Integer no) {
		this.no = no;
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
	 * 取得大图
	 */
	public String getBigImage() {
		return bigImage;
	}
	/**
	 * 设置大图
	 */
	public void setBigImage(String bigImage) {
		this.bigImage = bigImage;
	}

	/**
	 * 取得小图
	 */
	public String getSmallImage() {
		return smallImage;
	}
	/**
	 * 设置小图
	 */
	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
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
	 * 取得状态
	 */
	public ReleaseStatusCode getReleaseStatus() {
		return releaseStatus;
	}
	/**
	 * 设置状态
	 */
	public void setReleaseStatus(ReleaseStatusCode releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	/**
	 * 取得发布时间
	 */
	public Calendar getReleaseTime() {
		return releaseTime;
	}
	/**
	 * 设置发布时间
	 */
	public void setReleaseTime(Calendar releaseTime) {
		this.releaseTime = releaseTime;
	}

	/**
	 * 取得点击数
	 */
	public Integer getPointCount() {
		return pointCount;
	}
	/**
	 * 设置点击数
	 */
	public void setPointCount(Integer pointCount) {
		this.pointCount = pointCount;
	}

}
