package com.hangyi.eyunda.util.sms;

import java.util.Map;

import com.hangyi.eyunda.data.BaseData;

public class SMSResponse extends BaseData {
	private static final long serialVersionUID = -1L;

	private String requestId;// 客户请求ID
	private String status;// 请求状态
	private String batchId;// 系统生成的唯一批处理ID
	private String errorCode;// 错误代码

	public SMSResponse() {
	}

	public SMSResponse(Map<String, Object> map) {
		this.requestId = (String) map.get("requestId");
		this.status = (String) map.get("status");
		this.batchId = (String) map.get("batchId");
		this.errorCode = (String) map.get("errorCode");
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}