package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydStatShip")
public class YydStatShip extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(name = "nian_yue", nullable = false, length = 6)
	private String yearMonth = ""; // 年月
	@Column
	private Integer upShips = 0; // 新上线车船
	@Column
	private Integer downShips = 0; // 新下线车船
	@Column
	private Integer sumWares = 0; // 总上线车船

	/**
	 * 取得年月
	 */
	public String getYearMonth() {
		return yearMonth;
	}

	/**
	 * 设置年月
	 */
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	/**
	 * 取得新上线车船
	 */
	public Integer getUpShips() {
		return upShips;
	}

	/**
	 * 设置新上线车船
	 */
	public void setUpShips(Integer upShips) {
		this.upShips = upShips;
	}

	/**
	 * 取得新下线车船
	 */
	public Integer getDownShips() {
		return downShips;
	}

	/**
	 * 设置新下线车船
	 */
	public void setDownShips(Integer downShips) {
		this.downShips = downShips;
	}

	/**
	 * 取得总上线车船
	 */
	public Integer getSumWares() {
		return sumWares;
	}

	/**
	 * 设置总上线车船
	 */
	public void setSumWares(Integer sumWares) {
		this.sumWares = sumWares;
	}

}
