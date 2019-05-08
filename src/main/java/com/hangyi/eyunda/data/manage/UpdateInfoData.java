package com.hangyi.eyunda.data.manage;

import com.hangyi.eyunda.data.BaseData;

public class UpdateInfoData extends BaseData {
	private static final long serialVersionUID = -1L;

	private String version; // 当前版本
	private String url; // 升级地址
	private String  note = ""; // 升级说明

	

	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
