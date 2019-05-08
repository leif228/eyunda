package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hangyi.eyunda.domain.enumeric.UserStatusCode;
import com.hangyi.eyunda.domain.enumeric.UserTypeCode;

@Entity
@Table(name = "YydUserInfo", indexes = { @Index(name = "idx_simCardNo", columnList = "simCardNo", unique = false),
		@Index(name = "idx_loginName", columnList = "loginName", unique = true),
		@Index(name = "idx_email", columnList = "email", unique = false),
		@Index(name = "idx_mobile", columnList = "mobile", unique = false) })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydUserInfo extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Enumerated(EnumType.ORDINAL)
	private UserTypeCode userType = UserTypeCode.person; // 用户类型
	@Column(nullable = true, length = 20)
	private String simCardNo = ""; // 手机SIM卡号
	@Column(nullable = false, length = 20)
	private String loginName = ""; // 登录名
	@Column(nullable = false, length = 50)
	private String password = ""; // 登录密码
	@Column(nullable = true, length = 50)
	private String paypwd = ""; // 支付密码
	@Column(nullable = true, length = 200)
	private String trueName = ""; // 姓名
	@Column(nullable = true, length = 20)
	private String nickName = ""; // 昵称
	@Column(nullable = false, length = 100)
	private String email = ""; // 电子邮箱
	@Column(nullable = true, length = 100)
	private String mobile = ""; // 手机

	@Column(nullable = true, length = 6)
	private String postCode = ""; // 邮编
	@Column(nullable = true, length = 6)
	private String areaCode = ""; // 地区编码
	@Column(nullable = true, length = 200)
	private String address = ""; // 公司地址

	@Column(nullable = true, length = 200)
	private String userLogo = ""; // 图标图片
	@Column(nullable = true, length = 200)
	private String signature = ""; // 个性签名
	@Column(nullable = true, length = 200)
	private String stamp = ""; // 图章
	@Column
	private Calendar createTime = Calendar.getInstance(); // 注册时间
	@Enumerated(EnumType.ORDINAL)
	private UserStatusCode status = null; // 状态

	public UserTypeCode getUserType() {
		return userType;
	}

	public void setUserType(UserTypeCode userType) {
		this.userType = userType;
	}

	public String getSimCardNo() {
		return simCardNo;
	}

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
	 * 取得姓名
	 */
	public String getTrueName() {
		return trueName;
	}

	public String getPaypwd() {
		return paypwd;
	}

	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
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

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	 * 取得个性签名
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * 设置个性签名
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * 取得图章
	 */
	public String getStamp() {
		return stamp;
	}

	/**
	 * 设置图章
	 */
	public void setStamp(String stamp) {
		this.stamp = stamp;
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
