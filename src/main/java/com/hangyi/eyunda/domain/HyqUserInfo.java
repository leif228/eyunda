package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.UserStatusCode;

@Entity
@Table(name = "HyqUserInfo")
public class HyqUserInfo extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = true, length = 20)
	private String simCardNo = ""; // SIM卡号
	@Column(nullable = false, length = 20)
	private String loginName = ""; // 登录名
	@Column(nullable = false, length = 20)
	private String password = ""; // 用户密码
	@Column(nullable = true, length = 20)
	private String paypwd = ""; // 支付密码
	@Column(nullable = true, length = 20)
	private String trueName = ""; // 姓名
	@Column(nullable = true, length = 20)
	private String nickName = ""; // 昵称
	@Column(nullable = true, length = 40)
	private String idCardNo = ""; // 身份证号
	@Column(nullable = true, length = 100)
	private String email = ""; // 电子邮箱
	@Column(nullable = false, length = 40)
	private String mobile = ""; // 手机
	@Column(nullable = true, length = 200)
	private String userLogo = ""; // 图标图片
	@Column
	private Calendar createTime = Calendar.getInstance(); // 注册时间
	@Enumerated(EnumType.ORDINAL)
	private UserStatusCode status = null; // 状态

	/**
	 * 取得SIM卡号
	 */
	public String getSimCardNo() {
		return simCardNo;
	}

	/**
	 * 设置SIM卡号
	 */
	public void setSimCardNo(String simCardNo) {
		this.simCardNo = simCardNo;
	}

	/**
	 * 取得登录名
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 设置登录名
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 取得用户密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置用户密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 取得支付密码
	 */
	public String getPaypwd() {
		return paypwd;
	}

	/**
	 * 设置支付密码
	 */
	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
	}

	/**
	 * 取得姓名
	 */
	public String getTrueName() {
		return trueName;
	}

	/**
	 * 设置姓名
	 */
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	/**
	 * 取得昵称
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 设置昵称
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 取得身份证号
	 */
	public String getIdCardNo() {
		return idCardNo;
	}

	/**
	 * 设置身份证号
	 */
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	/**
	 * 取得电子邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置电子邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 取得手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 取得图标图片
	 */
	public String getUserLogo() {
		return userLogo;
	}

	/**
	 * 设置图标图片
	 */
	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}

	/**
	 * 取得注册时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置注册时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得状态
	 */
	public UserStatusCode getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 */
	public void setStatus(UserStatusCode status) {
		this.status = status;
	}

}
