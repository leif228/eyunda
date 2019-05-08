package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PubPayCnapsBank")
public class PubPayCnapsBank extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 14)
	private String bankCode = ""; // 超级网银号
	@Column(nullable = false, length = 1)
	private String status = ""; // 状态:0无效,1有效
	@Column(nullable = false, length = 3)
	private String bankClsCode = ""; // 大小额联行号银行代码，例如工商银行是102
	@Column(nullable = false, length = 6)
	private String cityCode = ""; // 城市代码，对应着城市表的PubPayCity.areaCode
	@Column(nullable = false, length = 120)
	private String bankName = ""; // 银行支行名称

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBankClsCode() {
		return bankClsCode;
	}

	public void setBankClsCode(String bankClsCode) {
		this.bankClsCode = bankClsCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
