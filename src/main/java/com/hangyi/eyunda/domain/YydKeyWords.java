package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Table(name = "YydKeyWords")
public class YydKeyWords extends BaseEntity {
private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 20)
	private String channelCode = ""; // 一级类别代码
	@Column(nullable = false, length = 200)
	private String keyWords = ""; // 关键词
	@Column
	private Integer searchTimes = 0; // 搜索次数

	/**
	 * 取得一级类别代码
	 */
	public String getChannelCode() {
		return channelCode;
	}
	/**
	 * 设置一级类别代码
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * 取得关键词
	 */
	public String getKeyWords() {
		return keyWords;
	}
	/**
	 * 设置关键词
	 */
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	/**
	 * 取得搜索次数
	 */
	public Integer getSearchTimes() {
		return searchTimes;
	}
	/**
	 * 设置搜索次数
	 */
	public void setSearchTimes(Integer searchTimes) {
		this.searchTimes = searchTimes;
	}

}
