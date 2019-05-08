package com.hangyi.eyunda.data.hyquan;

import java.util.Map;

import com.hangyi.eyunda.chat.event.OnlineStatusCode;
import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.UserStatusCode;

public class HyqUserData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String simCardNo = ""; // SIM卡号
	private String loginName = ""; // 登录名
	private String password = ""; // 用户密码
	private String paypwd = ""; // 支付密码
	private String trueName = ""; // 姓名
	private String nickName = ""; // 昵称
	private String idCardNo = ""; // 身份证号
	private String email = ""; // 电子邮箱
	private String mobile = ""; // 手机
	private String userLogo = ""; // 图标图片
	private String createTime = ""; // 注册时间
	private UserStatusCode status = UserStatusCode.activity; // 状态

	private String bindingCode = ""; // 手机绑定编码

	private HyqAccountData accountData = null;// 见证宝帐户
	private HyqUserBankData userBankData = null;// 绑定的银行卡

	private OnlineStatusCode onlineStatus = OnlineStatusCode.ofline;
	private String sessionId = "";

	public HyqUserData() {
		super();
	}

	@SuppressWarnings("unchecked")
	public HyqUserData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.id = ((Double) params.get("id")).longValue();
			this.simCardNo = (String) params.get("simCardNo");
			this.loginName = (String) params.get("loginName");
			this.password = (String) params.get("password");
			this.paypwd = (String) params.get("paypwd");
			this.trueName = (String) params.get("trueName");
			this.nickName = (String) params.get("nickName");
			this.idCardNo = (String) params.get("idCardNo");
			this.email = (String) params.get("email");
			this.mobile = (String) params.get("mobile");
			this.userLogo = (String) params.get("userLogo");
			this.createTime = (String) params.get("createTime");
			if (params.get("status") != null)
				this.status = UserStatusCode.valueOf((String) params.get("status"));
			this.bindingCode = (String) params.get("bindingCode");

			this.accountData = new HyqAccountData((Map<String, Object>) params.get("accountData"));
			this.userBankData = new HyqUserBankData((Map<String, Object>) params.get("userBankData"));

			if (params.get("onlineStatus") != null)
				this.onlineStatus = OnlineStatusCode.valueOf((String) params.get("onlineStatus"));
			this.sessionId = (String) params.get("sessionId");
		}
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

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public UserStatusCode getStatus() {
		return status;
	}

	public void setStatus(UserStatusCode status) {
		this.status = status;
	}

	public HyqAccountData getAccountData() {
		return accountData;
	}

	public void setAccountData(HyqAccountData accountData) {
		this.accountData = accountData;
	}

	public HyqUserBankData getUserBankData() {
		return userBankData;
	}

	public void setUserBankData(HyqUserBankData userBankData) {
		this.userBankData = userBankData;
	}

	public String getBindingCode() {
		return bindingCode;
	}

	public void setBindingCode(String bindingCode) {
		this.bindingCode = bindingCode;
	}

	public OnlineStatusCode getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(OnlineStatusCode onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
