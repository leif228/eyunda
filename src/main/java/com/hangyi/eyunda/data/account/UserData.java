package com.hangyi.eyunda.data.account;

import java.util.Calendar;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.chat.event.OnlineStatusCode;
import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.wallet.UserBankData;
import com.hangyi.eyunda.domain.enumeric.ApplyStatusCode;
import com.hangyi.eyunda.domain.enumeric.UserStatusCode;
import com.hangyi.eyunda.domain.enumeric.UserTypeCode;
import com.hangyi.eyunda.util.CalendarUtil;

public class UserData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID
	private String simCardNo = ""; // 手机SIM卡号
	private String bindingCode = ""; // 手机绑定编码

	private UserTypeCode userType = UserTypeCode.person; // 用户类型
	private String loginName = ""; // 登录名
	private String password = ""; // 登录密码
	private String paypwd = ""; // 支付密码
	private String trueName = ""; // 姓名
	private String nickName = ""; // 昵称
	private String email = ""; // 电子邮箱
	private String mobile = ""; // 手机

	private String postCode = ""; // 邮编
	private String areaCode = ""; // 地区编码
	private String address = ""; // 小地址
	private String unitAddr = ""; // 完整地址

	private String userLogo = ""; // 图标图片
	private String signature = ""; // 个性签名
	private String stamp = ""; // 图章
	private String createTime = CalendarUtil.toYYYY_MM_DD_HH_MM(Calendar.getInstance()); // 注册时间
	private UserStatusCode status = UserStatusCode.inactivity;

	private OnlineStatusCode onlineStatus = OnlineStatusCode.ofline;
	private String ships = "";// 子账号授权的船舶名称如：shipName,shipName

	private MultipartFile userLogoFile = null;// 上传的图标图片
	private MultipartFile signatureFile = null;// 上传的个性签名
	private MultipartFile stampFile = null;// 上传的图章

	private String sessionId = "";

	private ApplyStatusCode applyStatus = ApplyStatusCode.approve;

	private AccountData accountData = null;
	private UserBankData userBankData = null;
	
	private CompanyData currCompanyData = null;
	private List<CompanyData> companyDatas = null;

	public String getShips() {
		return ships;
	}

	public void setShips(String ships) {
		this.ships = ships;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSimCardNo() {
		return simCardNo;
	}

	public void setSimCardNo(String simCardNo) {
		this.simCardNo = simCardNo;
	}

	public String getBindingCode() {
		return bindingCode;
	}

	public UserTypeCode getUserType() {
		return userType;
	}

	public void setUserType(UserTypeCode userType) {
		this.userType = userType;
	}

	public void setBindingCode(String bindingCode) {
		this.bindingCode = bindingCode;
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

	public String getPaypwd() {
		return paypwd;
	}

	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
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

	public String getUnitAddr() {
		return unitAddr;
	}

	public void setUnitAddr(String unitAddr) {
		this.unitAddr = unitAddr;
	}

	public String getUserLogo() {
		return userLogo;
	}

	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public UserStatusCode getStatus() {
		return status;
	}

	public void setStatus(UserStatusCode userStatusCode) {
		this.status = userStatusCode;
	}

	public OnlineStatusCode getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(OnlineStatusCode onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public MultipartFile getUserLogoFile() {
		return userLogoFile;
	}

	public void setUserLogoFile(MultipartFile userLogoFile) {
		this.userLogoFile = userLogoFile;
	}

	public MultipartFile getSignatureFile() {
		return signatureFile;
	}

	public void setSignatureFile(MultipartFile signatureFile) {
		this.signatureFile = signatureFile;
	}

	public MultipartFile getStampFile() {
		return stampFile;
	}

	public void setStampFile(MultipartFile stampFile) {
		this.stampFile = stampFile;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public ApplyStatusCode getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(ApplyStatusCode applyStatus) {
		this.applyStatus = applyStatus;
	}

	public AccountData getAccountData() {
		return accountData;
	}

	public void setAccountData(AccountData accountData) {
		this.accountData = accountData;
	}

	public UserBankData getUserBankData() {
		return userBankData;
	}

	public void setUserBankData(UserBankData userBankData) {
		this.userBankData = userBankData;
	}

	public CompanyData getCurrCompanyData() {
		return currCompanyData;
	}

	public void setCurrCompanyData(CompanyData currCompanyData) {
		this.currCompanyData = currCompanyData;
	}

	public List<CompanyData> getCompanyDatas() {
		return companyDatas;
	}

	public void setCompanyDatas(List<CompanyData> companyDatas) {
		this.companyDatas = companyDatas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
