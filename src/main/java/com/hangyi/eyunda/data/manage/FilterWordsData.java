package com.hangyi.eyunda.data.manage;

import com.hangyi.eyunda.data.BaseData;

public class FilterWordsData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = null; // ID
	private String filterWords = ""; // 过滤词

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilterWords() {
		return filterWords;
	}

	public void setFilterWords(String filterWords) {
		this.filterWords = filterWords;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
