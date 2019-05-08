package com.hangyi.eyunda.data.shipinfo;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.ImgTypeCode;

public class ShpCertAttaData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private Long certId = 0L; // 证书ID
	private Integer no = 0; // 顺序号
	private ImgTypeCode imgType = null; // 图片类型
	private String title = ""; // 图片标题
	private String url = ""; // 路径
	private String showImgUrl = ""; // 大图展示用缩略图路径
	private String smallImgUrl = ""; // 缩略图路径
	private String createTime = ""; // 上传时间
	private Long size = 0L; // 文件大小

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getCertId() {
		return certId;
	}

	public void setCertId(Long certId) {
		this.certId = certId;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public ImgTypeCode getImgType() {
		return imgType;
	}

	public void setImgType(ImgTypeCode imgType) {
		this.imgType = imgType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
