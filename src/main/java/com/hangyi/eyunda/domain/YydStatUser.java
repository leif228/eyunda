package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydStatUser")
public class YydStatUser extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(name = "nian_yue", nullable = false, length = 6)
	private String yearMonth = ""; // 年月
	@Column
	private Integer sumUsers = 0; // 总用户数
	@Column
	private Integer sumBrokers = 0; // 总管理员数
	@Column
	private Integer sumHandlers = 0; // 总业务员数
	@Column
	private Integer sumSailors = 0; // 总船员数
	@Column
	private Integer sumMasters = 0; // 总船东数
	@Column
	private Integer sumOwners = 0; // 总货主数

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Integer getSumUsers() {
		return sumUsers;
	}

	public void setSumUsers(Integer sumUsers) {
		this.sumUsers = sumUsers;
	}

	public Integer getSumBrokers() {
		return sumBrokers;
	}

	public void setSumBrokers(Integer sumBrokers) {
		this.sumBrokers = sumBrokers;
	}

	public Integer getSumHandlers() {
		return sumHandlers;
	}

	public void setSumHandlers(Integer sumHandlers) {
		this.sumHandlers = sumHandlers;
	}

	public Integer getSumSailors() {
		return sumSailors;
	}

	public void setSumSailors(Integer sumSailors) {
		this.sumSailors = sumSailors;
	}

	public Integer getSumMasters() {
		return sumMasters;
	}

	public void setSumMasters(Integer sumMasters) {
		this.sumMasters = sumMasters;
	}

	public Integer getSumOwners() {
		return sumOwners;
	}

	public void setSumOwners(Integer sumOwners) {
		this.sumOwners = sumOwners;
	}

}
