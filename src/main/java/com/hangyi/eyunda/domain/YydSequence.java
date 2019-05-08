package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydSequence")
public class YydSequence extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 32)
	private String sequenceName = ""; // 序列名
	@Column
	private Long sequenceNo = 0L; // 序列号

	/**
	 * 取得序列名
	 */
	public String getSequenceName() {
		return sequenceName;
	}

	/**
	 * 设置序列名
	 */
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	/**
	 * 取得序列号
	 */
	public Long getSequenceNo() {
		return sequenceNo;
	}

	/**
	 * 设置序列号
	 */
	public void setSequenceNo(Long sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

}
