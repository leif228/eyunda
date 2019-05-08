package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "HyqCompanyCertificate")
public class HyqCompanyCertificate extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 100)
	private String compName = ""; // 公司名称
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间
	@Column(nullable = true, length = 200)
	private String compLogo = ""; // 公司Logo
	@Column(nullable = true, length = 200)
	private String compLicence = ""; // 营业执照
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode certify = YesNoCode.no; // 已认证

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public String getCompLogo() {
		return compLogo;
	}

	public void setCompLogo(String compLogo) {
		this.compLogo = compLogo;
	}

	public String getCompLicence() {
		return compLicence;
	}

	public void setCompLicence(String compLicence) {
		this.compLicence = compLicence;
	}

	public YesNoCode getCertify() {
		return certify;
	}

	public void setCertify(YesNoCode certify) {
		this.certify = certify;
	}

}
