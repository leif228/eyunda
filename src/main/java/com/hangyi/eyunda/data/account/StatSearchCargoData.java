package com.hangyi.eyunda.data.account;

import com.hangyi.eyunda.data.BaseData;

public class StatSearchCargoData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID
	
	private String searchWords = ""; // 搜索词
	
	private Long count = 0L; // 次数

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSearchWords() {
		return searchWords;
	}

	public void setSearchWords(String searchWords) {
		this.searchWords = searchWords;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
