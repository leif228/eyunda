package com.hangyi.eyunda.data.shipinfo;

import java.util.List;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;

public class ShpUserShipData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private ShpCertSysCode certSys = ShpCertSysCode.hyq;
	private Long userId = 0L; // 用户ID
	private Long shipId = 0L; // 船舶ID
	private ShpRightsCode rights = null; // 权限
	private String createTime = ""; // 建立时间

	private ShpShipInfoData shipInfoData = null;
	private HyqUserData userData = null;
	private List<ShpCertRightsData> certRightsDatas = null;

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

	public ShpRightsCode getRights() {
		return rights;
	}

	public void setRights(ShpRightsCode rights) {
		this.rights = rights;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public ShpShipInfoData getShipInfoData() {
		return shipInfoData;
	}

	public void setShipInfoData(ShpShipInfoData shipInfoData) {
		this.shipInfoData = shipInfoData;
	}

	public HyqUserData getUserData() {
		return userData;
	}

	public void setUserData(HyqUserData userData) {
		this.userData = userData;
	}

	public List<ShpCertRightsData> getCertRightsDatas() {
		return certRightsDatas;
	}

	public void setCertRightsDatas(List<ShpCertRightsData> certRightsDatas) {
		this.certRightsDatas = certRightsDatas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
