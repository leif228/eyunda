package com.hangyi.eyunda.domain.shipinfo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.BaseEntity;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "ShpUserShip")
public class ShpUserShip extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Enumerated(EnumType.ORDINAL)
	private ShpCertSysCode certSys = ShpCertSysCode.hyq;
	@Column
	private Long userId = 0L; // 用户ID
	@Column
	private Long shipId = 0L; // 船舶ID
	@Enumerated(EnumType.ORDINAL)
	private ShpRightsCode rights = ShpRightsCode.favoriteShip; // 权限
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode beDeleted = YesNoCode.no; // 删除标志

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ShpCertSysCode getCertSys() {
		return certSys;
	}

	public void setCertSys(ShpCertSysCode certSys) {
		this.certSys = certSys;
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

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
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
