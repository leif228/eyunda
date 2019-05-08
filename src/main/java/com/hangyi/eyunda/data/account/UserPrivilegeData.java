package com.hangyi.eyunda.data.account;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;

public class UserPrivilegeData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private Long userId = 0L; // 用户ID
	private Long compId = 0L; // 公司ID
	private UserPrivilegeCode privilege = null; // 用户特权
	private String mmsis = ""; // 上报船舶

	private String createTime = ""; // 建立时间

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

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public UserPrivilegeCode getPrivilege() {
		return privilege;
	}

	public void setPrivilege(UserPrivilegeCode privilege) {
		this.privilege = privilege;
	}

	public String getMmsis() {
		return mmsis;
	}

	public void setMmsis(String mmsis) {
		this.mmsis = mmsis;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
