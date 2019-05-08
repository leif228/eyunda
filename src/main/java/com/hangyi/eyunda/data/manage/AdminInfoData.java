package com.hangyi.eyunda.data.manage;

import java.util.Calendar;
import java.util.List;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.UserStatusCode;

public class AdminInfoData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = null; // ID
	private String loginName = ""; // 登录名
	private String password = ""; // 用户密码
	private String trueName = ""; // 姓名
	private String nickName = ""; // 昵称
	private String email = ""; // 电子邮箱
	private String mobile = ""; // 手机
	private String userLogo = ""; // 图标图片
	private Calendar createTime = Calendar.getInstance(); // 注册时间
	private UserStatusCode status = UserStatusCode.activity; // 状态

	private List<RoleInfoData> roleDatas = null;

	public List<RoleInfoData> getRoleDatas() {
		return roleDatas;
	}

	public void setRoleDatas(List<RoleInfoData> roleDatas) {
		this.roleDatas = roleDatas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserLogo() {
		return userLogo;
	}

	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public UserStatusCode getStatus() {
		return status;
	}

	public void setStatus(UserStatusCode status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
