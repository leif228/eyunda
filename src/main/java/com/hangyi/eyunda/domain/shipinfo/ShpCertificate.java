package com.hangyi.eyunda.domain.shipinfo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.BaseEntity;
import com.hangyi.eyunda.domain.enumeric.AuditStatusCode;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "ShpCertificate")
public class ShpCertificate extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long shipId = 0L; // 船舶ID
	@Enumerated(EnumType.ORDINAL)
	private CertTypeCode certType = null; // 证书类型
	@Column(nullable = false, length = 20)
	private String certNo = ""; // 证书编号
	@Column
	private Calendar issueDate = Calendar.getInstance(); // 发证日期
	@Column
	private Calendar maturityDate = Calendar.getInstance(); // 到期日期
	@Column
	private Calendar remindDate = Calendar.getInstance(); // 提醒日期
	@Column(nullable = false, length = 200)
	private String remark = ""; // 备注
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode opened = YesNoCode.no; // 公开性
	@Enumerated(EnumType.ORDINAL)
	private AuditStatusCode status = AuditStatusCode.unaudit; // 认证状态
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode beDeleted = YesNoCode.no; // 删除标志

	public Long getShipId() {
		return shipId;
	}

	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}

	public CertTypeCode getCertType() {
		return certType;
	}

	public void setCertType(CertTypeCode certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public Calendar getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Calendar issueDate) {
		this.issueDate = issueDate;
	}

	public Calendar getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Calendar maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Calendar getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(Calendar remindDate) {
		this.remindDate = remindDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public YesNoCode getOpened() {
		return opened;
	}

	public void setOpened(YesNoCode opened) {
		this.opened = opened;
	}

	public AuditStatusCode getStatus() {
		return status;
	}

	public void setStatus(AuditStatusCode status) {
		this.status = status;
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
