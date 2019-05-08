package com.hangyi.eyunda.util.sms;

import com.hangyi.eyunda.data.BaseData;

public class SMSContent extends BaseData {
	private static final long serialVersionUID = -1L;

	private String account;
	private String password;
	private String mobile;
	private String content;
	private String requestId;
	private String extno;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getExtno() {
		return extno;
	}

	public void setExtno(String extno) {
		this.extno = extno;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}