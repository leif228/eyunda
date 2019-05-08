package com.hangyi.eyunda.data.hyquan;

import java.util.Map;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

public class HyqMessageData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String mobile = ""; // 手机
	private String content = ""; // 发送内容
	private String createTime = ""; // 创建时间
	private YesNoCode status = null; // 是否成功

	public HyqMessageData() {
		super();
	}

	public HyqMessageData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.id = ((Double) params.get("id")).longValue();
			this.mobile = (String) params.get("mobile");
			this.content = (String) params.get("content");
			this.createTime = (String) params.get("createTime");
			if (params.get("status") != null)
				this.status = YesNoCode.valueOf((String) params.get("status"));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public YesNoCode getStatus() {
		return status;
	}

	public void setStatus(YesNoCode status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
