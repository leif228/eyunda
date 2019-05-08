package com.hangyi.eyunda.domain.shipmove;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.BaseEntity;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;

@Entity
@Table(name = "MovUserShip")
public class MovUserShip extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long userId = 0L; // 用户ID
	@Column(nullable = true, length = 20)
	private String mmsi = ""; // 船舶mmsi
	@Enumerated(EnumType.ORDINAL)
	private ShpRightsCode rights = ShpRightsCode.favoriteShip; // 权限
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间

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
