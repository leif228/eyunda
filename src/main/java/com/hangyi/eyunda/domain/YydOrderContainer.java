package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YydOrderContainer")
public class YydOrderContainer extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long orderId = 0L; // 货物ID
	@Column(nullable = false, length = 50)
	private String cargoName = ""; // 规格
	@Column
	private Integer tonTeu = 0; // 箱量(个)
	@Column
	private Double price = 0.00D; // 运价(元/个)

	/**
	 * 取得合同ID
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * 设置合同ID
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * 取得规格
	 */
	public String getCargoName() {
		return cargoName;
	}

	/**
	 * 设置规格
	 */
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	/**
	 * 取得箱量(个)
	 */
	public Integer getTonTeu() {
		return tonTeu;
	}

	/**
	 * 设置箱量(个)
	 */
	public void setTonTeu(Integer tonTeu) {
		this.tonTeu = tonTeu;
	}

	/**
	 * 取得运价(元/个)
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * 设置运价(元/个)
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

}
