package com.hangyi.eyunda.data.release;

import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.enumeric.ColumnCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

public class CarrierIssueData extends BaseData {

	private static final long serialVersionUID = -1L;

	private Long id = 0L;
	private UserData userData = null; // 承运人
	private Long carrierId = 0L; // 承运人ID
	private ColumnCode columnCode = ColumnCode.GSJJ; // 栏目
	private Integer no = 0; // 序号
	private String title = ""; // 标题
	private String source = ""; // 来源
	private String createTime = ""; // 建立时间
	private String bigImage = ""; // 大图
	private MultipartFile bImage = null; // 大图

	private String smallImage = ""; // 小图
	private MultipartFile sImage = null; // 大图

	private String typeImage = ""; // 默认类别图片

	private String content = ""; // 内容
	private ReleaseStatusCode releaseStatus = ReleaseStatusCode.unpublish; // 状态
	private String releaseTime = ""; // 发布时间
	private Integer pointCount = 0; // 点击数

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public Long getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(Long carrierId) {
		this.carrierId = carrierId;
	}

	public ColumnCode getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(ColumnCode columnCode) {
		this.columnCode = columnCode;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getBigImage() {
		return bigImage;
	}

	public void setBigImage(String bigImage) {
		this.bigImage = bigImage;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ReleaseStatusCode getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(ReleaseStatusCode releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Integer getPointCount() {
		return pointCount;
	}

	public void setPointCount(Integer pointCount) {
		this.pointCount = pointCount;
	}

	public MultipartFile getbImage() {
		return bImage;
	}

	public void setbImage(MultipartFile bImage) {
		this.bImage = bImage;
	}

	public MultipartFile getsImage() {
		return sImage;
	}

	public void setsImage(MultipartFile sImage) {
		this.sImage = sImage;
	}

	public String getTypeImage() {
		return typeImage;
	}

	public void setTypeImage(String typeImage) {
		this.typeImage = typeImage;
	}

}
