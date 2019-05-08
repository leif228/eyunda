package com.hangyi.eyunda.data.manage.stat;

import com.hangyi.eyunda.data.BaseData;

public class StatUserData extends BaseData {

	private static final long serialVersionUID = -1L;

	private int ym = 0; // 年月
	private String yearMonth = ""; // 年月

	private Integer sumUsers = 0; // 总用户数
	private Integer sumBrokers = 0; // 总代理人数
	private Integer sumHandlers = 0; // 总业务员数
	private Integer sumSailors = 0; // 总船员数
	private Integer sumMasters = 0; // 总船东数
	private Integer sumOwners = 0; // 总货主数

	public int getYm() {
		return ym;
	}

	public void setYm(int ym) {
		this.ym = ym;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
