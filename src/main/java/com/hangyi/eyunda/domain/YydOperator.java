package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.ApplyStatusCode;

@Entity
@Table(name = "YydOperator")
public class YydOperator extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long userId = 0L; // 用户ID
	@Column(nullable = true, length = 20)
	private String legalPerson = ""; // 法人代表
	@Column(nullable = true, length = 200)
	private String idCardFront = ""; // 身份证正面
	@Column(nullable = true, length = 200)
	private String idCardBack = ""; // 身份证反面
	@Column(nullable = true, length = 200)
	private String busiLicence = ""; // 营业执照
	@Column(nullable = true, length = 200)
	private String taxLicence = ""; // 税务登记证
	@Enumerated(EnumType.ORDINAL)
	private ApplyStatusCode status = ApplyStatusCode.apply; // 认证状态
	@Column
	private Calendar applyTime = Calendar.getInstance(); // 认证时间

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

}
