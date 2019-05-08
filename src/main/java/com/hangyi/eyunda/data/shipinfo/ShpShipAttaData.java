package com.hangyi.eyunda.data.shipinfo;

import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.ImgTypeCode;

public class ShpShipAttaData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private Long shipId = 0L; // 船舶ID
	private Integer no = 0; // 顺序号
	private ImgTypeCode imgType = null; // 图片类型
	private String title = ""; // 图片标题
	private String url = ""; // 路径
	private String createTime = ""; // 上传时间
	private Long size = 0L; // 文件大小

	private MultipartFile imgFile = null;// 船舶图片

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShipId() {
		return shipId;
	}

	public void setShipId(Long shipId) {
		this.shipId = shipId;
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

	public MultipartFile getImgFile() {
		return imgFile;
	}

	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
