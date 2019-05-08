package com.hangyi.eyunda.data.shipinfo;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;

public class ShpCertShowRecordData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private ShpCertSysCode certSys = ShpCertSysCode.hyq;
	private Long userId = 0L; // 用户ID，分享的为0L
	private Long shipId = 0L; // 船舶ID
	private Long certId = 0L; // 证书ID
	private String createTime = ""; // 查看时间

	private HyqUserData userData = null;
	private ShpShipInfoData shipInfoData = null;
	private ShpCertificateData certificateData = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public HyqUserData getUserData() {
		return userData;
	}

	public void setUserData(HyqUserData userData) {
		this.userData = userData;
	}

	public ShpShipInfoData getShipInfoData() {
		return shipInfoData;
	}

	public void setShipInfoData(ShpShipInfoData shipInfoData) {
		this.shipInfoData = shipInfoData;
	}

	public ShpCertificateData getCertificateData() {
		return certificateData;
	}

	public void setCertificateData(ShpCertificateData certificateData) {
		this.certificateData = certificateData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
