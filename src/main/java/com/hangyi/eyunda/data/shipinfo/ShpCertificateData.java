package com.hangyi.eyunda.data.shipinfo;

import java.util.List;
import java.util.Map;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.AuditStatusCode;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

public class ShpCertificateData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private Long shipId = 0L; // 船舶ID
	private String shipName = ""; // 船舶名称
	private String mmsi = ""; // MMSI编号
	private CertTypeCode certType = null; // 证书类型
	private Map<String, Object> mapCertType = null; // 证书类型
	private String certNo = ""; // 证书编号
	private String issueDate = ""; // 发证日期
	private String maturityDate = ""; // 到期日期
	private String remindDate = ""; // 提醒日期
	private String warnCentent = ""; // 到期提醒内容
	private String remark = ""; // 备注
	private String createTime = ""; // 建立时间
	private YesNoCode opened = YesNoCode.no; // 公开性
	private AuditStatusCode status = AuditStatusCode.unaudit; // 认证状态
	private YesNoCode beDeleted = YesNoCode.no; // 删除标志

	private List<ShpCertAttaData> certAttaDatas = null;

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

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public CertTypeCode getCertType() {
		return certType;
	}

	public void setCertType(CertTypeCode certType) {
		this.certType = certType;
		this.mapCertType = certType.getMap();
	}

	public Map<String, Object> getMapCertType() {
		return mapCertType;
	}

	public void setMapCertType(Map<String, Object> mapCertType) {
		this.mapCertType = mapCertType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(String remindDate) {
		this.remindDate = remindDate;
	}

	public String getWarnCentent() {
		return warnCentent;
	}

	public void setWarnCentent(String warnCentent) {
		this.warnCentent = warnCentent;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
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

	public List<ShpCertAttaData> getCertAttaDatas() {
		return certAttaDatas;
	}

	public void setCertAttaDatas(List<ShpCertAttaData> certAttaDatas) {
		this.certAttaDatas = certAttaDatas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
