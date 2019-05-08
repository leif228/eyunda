package com.hangyi.eyunda.data.oil;

import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.manage.AdminInfoData;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

public class GasWaresData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L;
	private Long companyId = 0L; // 能源公司
	private GasCompanyData companyData = null;

	private String waresLogo = ""; // 商品Logo
	private MultipartFile waresFile = null; // 商品Logo文件

	private String waresName = ""; // 商品名称
	private String subTitle = ""; // 商品描述
	private String description = ""; // 商品详情
	private Double stdPrice = 0.00D; // 市场价格
	private Double price = 0.00D; // 销售价格
	private String priceSignal = ""; // 价格标语
	private String createTime = ""; // 建立时间
	private ReleaseStatusCode status = ReleaseStatusCode.unpublish; // 商品状态
	private String sellTime = ""; // 上下架时间
	private Long adminId = 0L; // 发表者Id
	private AdminInfoData admin = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public GasCompanyData getCompanyData() {
		return companyData;
	}

	public void setCompanyData(GasCompanyData companyData) {
		this.companyData = companyData;
	}

	public String getWaresLogo() {
		return waresLogo;
	}

	public void setWaresLogo(String waresLogo) {
		this.waresLogo = waresLogo;
	}

	public MultipartFile getWaresFile() {
		return waresFile;
	}

	public void setWaresFile(MultipartFile waresFile) {
		this.waresFile = waresFile;
	}

	public String getWaresName() {
		return waresName;
	}

	public void setWaresName(String waresName) {
		this.waresName = waresName;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getStdPrice() {
		return stdPrice;
	}

	public void setStdPrice(Double stdPrice) {
		this.stdPrice = stdPrice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPriceSignal() {
		return priceSignal;
	}

	public void setPriceSignal(String priceSignal) {
		this.priceSignal = priceSignal;
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

	public String getSellTime() {
		return sellTime;
	}

	public void setSellTime(String sellTime) {
		this.sellTime = sellTime;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public AdminInfoData getAdmin() {
		return admin;
	}

	public void setAdmin(AdminInfoData admin) {
		this.admin = admin;
	}

}
