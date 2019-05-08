package com.hangyi.eyunda.data.hyquan;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

public class HyqAppData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String appName = ""; // 服务名称
	private String appDesc = ""; // 服务介绍
	private String appIcon = ""; // 图标地址
	private String appUrl = ""; // 入口地址

	private String createTime = ""; // 建立时间
	private ReleaseStatusCode status = null; // 状态

	private MultipartFile appIconFile = null;// 上传的图标图片

	public HyqAppData() {
		super();
	}

	public HyqAppData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.id = ((Double) params.get("id")).longValue();
			this.appName = (String) params.get("appName");
			this.appDesc = (String) params.get("appDesc");
			this.appIcon = (String) params.get("appIcon");
			this.appUrl = (String) params.get("appUrl");
			this.createTime = (String) params.get("createTime");

			if (params.get("status") != null)
				this.status = ReleaseStatusCode.valueOf((String) params.get("status"));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public ReleaseStatusCode getStatus() {
		return status;
	}

	public void setStatus(ReleaseStatusCode status) {
		this.status = status;
	}

	public MultipartFile getAppIconFile() {
		return appIconFile;
	}

	public void setAppIconFile(MultipartFile appIconFile) {
		this.appIconFile = appIconFile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
