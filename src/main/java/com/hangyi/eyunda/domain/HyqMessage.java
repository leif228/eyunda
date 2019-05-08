package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "HyqMessage")
public class HyqMessage extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 11)
	private String mobile = ""; // 手机
	@Column(nullable = false, length = 500)
	private String content = ""; // 发送内容
	@Column
	private Calendar createTime = Calendar.getInstance(); // 创建时间
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode status = null; // 是否成功

	/**
	 * 取得手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 取得发送内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置发送内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 取得创建时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得是否成功
	 */
	public YesNoCode getStatus() {
		return status;
	}

	/**
	 * 设置是否成功
	 */
	public void setStatus(YesNoCode status) {
		this.status = status;
	}

}
