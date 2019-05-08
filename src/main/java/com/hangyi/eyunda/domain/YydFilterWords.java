package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydFilterWords")
public class YydFilterWords extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = true, length = 16000)
	private String filterWords = ""; // 说明

	public String getFilterWords() {
		return filterWords;
	}

	public void setFilterWords(String filterWords) {
		this.filterWords = filterWords;
	}

}
