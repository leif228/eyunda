package com.hangyi.eyunda.domain.shipinfo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.BaseEntity;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;

@Entity
@Table(name = "ShpCertRights")
public class ShpCertRights extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Enumerated(EnumType.ORDINAL)
	private ShpCertSysCode certSys = ShpCertSysCode.hyq;
	@Column
	private Long userId = 0L; // 用户ID
	@Column
	private Long masterId = 0L; // 船东ID
	@Column
	private Long shipId = 0L; // 船舶ID
	@Column
	private Long certId = 0L; // 证书ID
	@Column
	private Calendar startDate = Calendar.getInstance(); // 开始日期
	@Column
	private Calendar endDate = Calendar.getInstance(); // 结束日期
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间

	public ShpCertSysCode getCertSys() {
		return certSys;
	}

	public void setCertSys(ShpCertSysCode certSys) {
		this.certSys = certSys;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public Long getShipId() {
		return shipId;
	}

	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}

	public Long getCertId() {
		return certId;
	}

	public void setCertId(Long certId) {
		this.certId = certId;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
