package com.hangyi.eyunda.domain.shipinfo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.BaseEntity;
import com.hangyi.eyunda.domain.enumeric.ImgTypeCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "ShpCertAtta")
public class ShpCertAtta extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long certId = 0L; // 证书ID
	@Column
	private Integer no = 0; // 顺序号
	@Enumerated(EnumType.ORDINAL)
	private ImgTypeCode imgType = null; // 图片类型
	@Column(nullable = true, length = 1024)
	private String title = ""; // 图片标题
	@Column(nullable = false, length = 200)
	private String url = ""; // 路径
	@Column(length = 200)
	private String showImgUrl = ""; // 大图展示用缩略图路径
	@Column(length = 200)
	private String smallImgUrl = ""; // 缩略图路径
	@Column
	private Calendar createTime = Calendar.getInstance(); // 上传时间
	@Column
	private Long size = 0L; // 文件大小
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode beDeleted = YesNoCode.no; // 删除标志

	/**
	 * 取得证书ID
	 */
	public Long getCertId() {
		return certId;
	}

	/**
	 * 设置证书ID
	 */
	public void setCertId(Long certId) {
		this.certId = certId;
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

	public String getShowImgUrl() {
		return showImgUrl;
	}

	public void setShowImgUrl(String showImgUrl) {
		this.showImgUrl = showImgUrl;
	}

	public String getSmallImgUrl() {
		return smallImgUrl;
	}

	public void setSmallImgUrl(String smallImgUrl) {
		this.smallImgUrl = smallImgUrl;
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

	public YesNoCode getBeDeleted() {
		return beDeleted;
	}

	public void setBeDeleted(YesNoCode beDeleted) {
		this.beDeleted = beDeleted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
