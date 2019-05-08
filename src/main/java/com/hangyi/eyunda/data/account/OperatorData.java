package com.hangyi.eyunda.data.account;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.ApplyStatusCode;

public class OperatorData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID
	private Long userId = 0L; // 用户ID
	private UserData userData = null;
	private String legalPerson = ""; // 法人代表
	private String idCardFront = ""; // 身份证正面
	private String idCardBack = ""; // 身份证反面
	private String busiLicence = ""; // 营业执照
	private String taxLicence = ""; // 税务登记证
	private ApplyStatusCode status = ApplyStatusCode.apply; // 状态
	private Calendar applyTime = Calendar.getInstance(); // 申请时间

	private MultipartFile idCardFrontFile = null;// 身份证正面
	private MultipartFile idCardBackFile = null;// 身份证反面
	private MultipartFile busiLicenceFile = null;// 营业执照
	private MultipartFile taxLicenceFile = null;// 税务登记证

	private List<UserData> handlerDatas = new ArrayList<UserData>(); //业务员

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getIdCardFront() {
		return idCardFront;
	}

	public void setIdCardFront(String idCardFront) {
		this.idCardFront = idCardFront;
	}

	public String getIdCardBack() {
		return idCardBack;
	}

	public void setIdCardBack(String idCardBack) {
		this.idCardBack = idCardBack;
	}

	public String getBusiLicence() {
		return busiLicence;
	}

	public void setBusiLicence(String busiLicence) {
		this.busiLicence = busiLicence;
	}

	public String getTaxLicence() {
		return taxLicence;
	}

	public void setTaxLicence(String taxLicence) {
		this.taxLicence = taxLicence;
	}

	public ApplyStatusCode getStatus() {
		return status;
	}

	public void setStatus(ApplyStatusCode status) {
		this.status = status;
	}

	public Calendar getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Calendar applyTime) {
		this.applyTime = applyTime;
	}

	public MultipartFile getIdCardFrontFile() {
		return idCardFrontFile;
	}

	public void setIdCardFrontFile(MultipartFile idCardFrontFile) {
		this.idCardFrontFile = idCardFrontFile;
	}

	public MultipartFile getIdCardBackFile() {
		return idCardBackFile;
	}

	public void setIdCardBackFile(MultipartFile idCardBackFile) {
		this.idCardBackFile = idCardBackFile;
	}

	public MultipartFile getBusiLicenceFile() {
		return busiLicenceFile;
	}

	public void setBusiLicenceFile(MultipartFile busiLicenceFile) {
		this.busiLicenceFile = busiLicenceFile;
	}

	public MultipartFile getTaxLicenceFile() {
		return taxLicenceFile;
	}

	public void setTaxLicenceFile(MultipartFile taxLicenceFile) {
		this.taxLicenceFile = taxLicenceFile;
	}

	public List<UserData> getHandlerDatas() {
		return handlerDatas;
	}

	public void setHandlerDatas(List<UserData> handlerDatas) {
		this.handlerDatas = handlerDatas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
