package com.hangyi.eyunda.data.oil;

import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.manage.AdminInfoData;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.SaleTypeCode;

public class GasCompanyData extends BaseData {

	private static final long serialVersionUID = 1L;

	private Long userId = 0L; // 会员Id
	private Long id = 0L;
	private SaleTypeCode saleType = null; // 卖家类型
	private String companyName = ""; // 公司名称
	private String address = ""; // 公司地址
	private String contact = ""; // 联系人
	private String mobile = ""; // 联系电话

	private String bigImage = ""; // 大图片
	private MultipartFile bigImageFile = null;

	private String smallImage = ""; // 小图片
	private MultipartFile smallImageFile = null;

	private String accounter = ""; // 开户人
	private PayStyleCode payStyle = PayStyleCode.alipay; // 支付方式
	private String accountNo = ""; // 收款账号
	private Long adminId = 0L; // 操作员Id
	private AdminInfoData admin = null;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SaleTypeCode getSaleType() {
		return saleType;
	}

	public void setSaleType(SaleTypeCode saleType) {
		this.saleType = saleType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBigImage() {
		return bigImage;
	}

	public void setBigImage(String bigImage) {
		this.bigImage = bigImage;
	}

	public MultipartFile getBigImageFile() {
		return bigImageFile;
	}

	public void setBigImageFile(MultipartFile bigImageFile) {
		this.bigImageFile = bigImageFile;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public MultipartFile getSmallImageFile() {
		return smallImageFile;
	}

	public void setSmallImageFile(MultipartFile smallImageFile) {
		this.smallImageFile = smallImageFile;
	}

	public String getAccounter() {
		return accounter;
	}

	public void setAccounter(String accounter) {
		this.accounter = accounter;
	}

	public PayStyleCode getPayStyle() {
		return payStyle;
	}

	public void setPayStyle(PayStyleCode payStyle) {
		this.payStyle = payStyle;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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
