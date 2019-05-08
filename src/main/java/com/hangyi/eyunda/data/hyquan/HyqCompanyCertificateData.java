package com.hangyi.eyunda.data.hyquan;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

public class HyqCompanyCertificateData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = null; // ID
	private String compName = ""; // 公司名称
	private String createTime = ""; // 建立时间
	private String compLogo = ""; // 公司Logo
	private String compLicence = ""; // 营业执照
	private YesNoCode certify = YesNoCode.no; // 已认证

	private MultipartFile compLogoMpf = null;
	private MultipartFile compLicenceMpf = null;

	public HyqCompanyCertificateData() {
		super();
	}

	public HyqCompanyCertificateData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.id = ((Double) params.get("id")).longValue();
			this.compName = (String) params.get("compName");
			this.createTime = (String) params.get("createTime");
			this.compLogo = (String) params.get("compLogo");
			this.compLicence = (String) params.get("compLicence");
			if (params.get("certify") != null)
				this.certify = YesNoCode.valueOf((String) params.get("certify"));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCompLogo() {
		return compLogo;
	}

	public void setCompLogo(String compLogo) {
		this.compLogo = compLogo;
	}

	public String getCompLicence() {
		return compLicence;
	}

	public void setCompLicence(String compLicence) {
		this.compLicence = compLicence;
	}

	public MultipartFile getCompLogoMpf() {
		return compLogoMpf;
	}

	public void setCompLogoMpf(MultipartFile compLogoMpf) {
		this.compLogoMpf = compLogoMpf;
	}

	public MultipartFile getCompLicenceMpf() {
		return compLicenceMpf;
	}

	public void setCompLicenceMpf(MultipartFile compLicenceMpf) {
		this.compLicenceMpf = compLicenceMpf;
	}

	public YesNoCode getCertify() {
		return certify;
	}

	public void setCertify(YesNoCode certify) {
		this.certify = certify;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
