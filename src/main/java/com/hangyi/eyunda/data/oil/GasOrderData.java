package com.hangyi.eyunda.data.oil;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.manage.AdminInfoData;
import com.hangyi.eyunda.domain.enumeric.BuyOilStatusCode;

public class GasOrderData extends BaseData {

	private static final long serialVersionUID = 1L;

	private Long id = 0L;
	private Long companyId = 0L; // 卖家公司Id
	private GasCompanyData company = null;

	private Long waresId = 0L; // 商品Id
	private GasWaresData gasWaresData = null;

	private Long carrierId = 0L; // 买家承运人Id
	private UserData carrier = null;

	private String description = ""; // 订单描述
	private String orderTime = ""; // 下单时间
	private Double saleCount = 0.00D; // 购买数量
	private Double price = 0.00D; // 交易价格
	private Double tradeMoney = 0.00D; // 交易金额
	private BuyOilStatusCode status = BuyOilStatusCode.edit; // 状态

	private Long adminId = 0L; // 操作员Id
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

	public GasCompanyData getCompany() {
		return company;
	}

	public void setCompany(GasCompanyData companyData) {
		this.company = companyData;
	}

	public Long getWaresId() {
		return waresId;
	}

	public void setWaresId(Long waresId) {
		this.waresId = waresId;
	}

	public GasWaresData getGasWaresData() {
		return gasWaresData;
	}

	public void setGasWaresData(GasWaresData gasWaresData) {
		this.gasWaresData = gasWaresData;
	}

	public Long getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(Long carrierId) {
		this.carrierId = carrierId;
	}

	public UserData getCarrier() {
		return carrier;
	}

	public void setCarrier(UserData carrier) {
		this.carrier = carrier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public Double getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(Double saleCount) {
		this.saleCount = saleCount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public BuyOilStatusCode getStatus() {
		return status;
	}

	public void setStatus(BuyOilStatusCode status) {
		this.status = status;
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
