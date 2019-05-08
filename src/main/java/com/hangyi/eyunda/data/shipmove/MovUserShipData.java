package com.hangyi.eyunda.data.shipmove;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;

public class MovUserShipData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private Long userId = 0L; // 用户ID
	private String mmsi = ""; // 船舶mmsi
	private ShpRightsCode rights = ShpRightsCode.favoriteShip; // 权限
	private String createTime = ""; // 建立时间

	private ShpShipInfoData shipInfoData = null;
	private HyqUserData userData = null;

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

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
