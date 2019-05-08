package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.HyqUnitTypeCode;
import com.hangyi.eyunda.domain.enumeric.RegionCode;

@Entity
@Table(name = "HyqDeveloper")
public class HyqDeveloper extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Enumerated(EnumType.ORDINAL)
	private HyqUnitTypeCode unitType = null; // 单位类型
	@Column(nullable = false, length = 100)
	private String unitName = ""; // 企业名称
	@Column(nullable = false, length = 20)
	private String unitNo = ""; // 注册号
	@Column(nullable = false, length = 200)
	private String address = ""; // 地址
	@Column(nullable = false, length = 20)
	private String organCode = ""; // 组织机构代码
	@Column(nullable = false, length = 100)
	private String license = ""; // 营业执照
	@Enumerated(EnumType.ORDINAL)
	private RegionCode lawyerRegion = null; // 法人归属地
	@Column(nullable = false, length = 20)
	private String lawyer = ""; // 法人
	@Column(nullable = false, length = 18)
	private String lawyerIdCard = ""; // 法人身份证
	@Enumerated(EnumType.ORDINAL)
	private RegionCode bossRegion = null; // 实控人归属地
	@Column(nullable = false, length = 20)
	private String boss = ""; // 实控人
	@Column(nullable = false, length = 18)
	private String bossIdCard = ""; // 实控人身份证
	@Column(nullable = false, length = 20)
	private String contacts = ""; // 联系人
	@Column(nullable = false, length = 11)
	private String contactsTel = ""; // 联系人电话

	/**
	 * 取得单位类型
	 */
	public HyqUnitTypeCode getUnitType() {
		return unitType;
	}

	/**
	 * 设置单位类型
	 */
	public void setUnitType(HyqUnitTypeCode unitType) {
		this.unitType = unitType;
	}

	/**
	 * 取得企业名称
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * 设置企业名称
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/**
	 * 取得注册号
	 */
	public String getUnitNo() {
		return unitNo;
	}

	/**
	 * 设置注册号
	 */
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	/**
	 * 取得地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 取得组织机构代码
	 */
	public String getOrganCode() {
		return organCode;
	}

	/**
	 * 设置组织机构代码
	 */
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	/**
	 * 取得营业执照
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * 设置营业执照
	 */
	public void setLicense(String license) {
		this.license = license;
	}

	/**
	 * 取得法人归属地
	 */
	public RegionCode getLawyerRegion() {
		return lawyerRegion;
	}

	/**
	 * 设置法人归属地
	 */
	public void setLawyerRegion(RegionCode lawyerRegion) {
		this.lawyerRegion = lawyerRegion;
	}

	/**
	 * 取得法人
	 */
	public String getLawyer() {
		return lawyer;
	}

	/**
	 * 设置法人
	 */
	public void setLawyer(String lawyer) {
		this.lawyer = lawyer;
	}

	/**
	 * 取得法人身份证
	 */
	public String getLawyerIdCard() {
		return lawyerIdCard;
	}

	/**
	 * 设置法人身份证
	 */
	public void setLawyerIdCard(String lawyerIdCard) {
		this.lawyerIdCard = lawyerIdCard;
	}

	/**
	 * 取得实控人归属地
	 */
	public RegionCode getBossRegion() {
		return bossRegion;
	}

	/**
	 * 设置实控人归属地
	 */
	public void setBossRegion(RegionCode bossRegion) {
		this.bossRegion = bossRegion;
	}

	/**
	 * 取得实控人
	 */
	public String getBoss() {
		return boss;
	}

	/**
	 * 设置实控人
	 */
	public void setBoss(String boss) {
		this.boss = boss;
	}

	/**
	 * 取得实控人身份证
	 */
	public String getBossIdCard() {
		return bossIdCard;
	}

	/**
	 * 设置实控人身份证
	 */
	public void setBossIdCard(String bossIdCard) {
		this.bossIdCard = bossIdCard;
	}

	/**
	 * 取得联系人
	 */
	public String getContacts() {
		return contacts;
	}

	/**
	 * 设置联系人
	 */
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	/**
	 * 取得联系人电话
	 */
	public String getContactsTel() {
		return contactsTel;
	}

	/**
	 * 设置联系人电话
	 */
	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

}
