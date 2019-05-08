package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.ImgTypeCode;

@Entity
@Table(name = "YydShipAtta")
public class YydShipAtta extends BaseEntity {
private static final long serialVersionUID = -1L;

	@Column
	private Long shipId = 0L; // 船舶ID
	@Column
	private Integer no = 0; // 顺序号
	@Enumerated(EnumType.ORDINAL)
	private ImgTypeCode imgType = null; //图片类型
	@Column(nullable = true, length = 4096)
	private String title = ""; // 图片标题
	@Column(nullable = false, length = 200)
	private String url = ""; // 路径
	@Column
	private Calendar createTime = Calendar.getInstance(); // 上传时间
	@Column
	private Long size = 0L; // 文件大小

	/**
	 * 取得船舶ID
	 */
	public Long getShipId() {
		return shipId;
	}
	/**
	 * 设置船舶ID
	 */
	public void setShipId(Long shipId) {
		this.shipId = shipId;
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
	 * 取得图片类型
	 */
	public ImgTypeCode getImgType() {
		return imgType;
	}
	/**
	 * 设置图片类型
	 */
	public void setImgType(ImgTypeCode imgType) {
		this.imgType = imgType;
	}

	/**
	 * 取得图片标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置图片标题
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
