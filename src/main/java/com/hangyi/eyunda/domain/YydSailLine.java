package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;

@Entity
@Table(name = "YydSailLine")
public class YydSailLine extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Enumerated(EnumType.ORDINAL)
	private WaresBigTypeCode waresBigType = null; // 船盘大类
	@Enumerated(EnumType.ORDINAL)
	private WaresTypeCode waresType = null; // 船盘分类
	@Enumerated(EnumType.ORDINAL)
	private CargoTypeCode cargoType = null; // 货类
	@Column(nullable = true, length = 8)
	private String startPortNo = ""; // 装货港
	@Column(nullable = true, length = 8)
	private String endPortNo = ""; // 卸货港
	@Column
	private Integer distance = 0; // 航程
	@Column
	private Integer weight = 0; // 缺省载重量
	@Column(nullable = false, length = 200)
	private String description = ""; // 运价描述
	@Column(nullable = false, length = 200)
	private String remark = ""; // 运价保存

	/**
	 * 取得船盘大类
	 */
	public WaresBigTypeCode getWaresBigType() {
		return waresBigType;
	}

	/**
	 * 设置船盘大类
	 */
	public void setWaresBigType(WaresBigTypeCode waresBigType) {
		this.waresBigType = waresBigType;
	}

	/**
	 * 取得船盘分类
	 */
	public WaresTypeCode getWaresType() {
		return waresType;
	}

	/**
	 * 设置船盘分类
	 */
	public void setWaresType(WaresTypeCode waresType) {
		this.waresType = waresType;
	}

	/**
	 * 取得货类
	 */
	public CargoTypeCode getCargoType() {
		return cargoType;
	}

	/**
	 * 设置货类
	 */
	public void setCargoType(CargoTypeCode cargoType) {
		this.cargoType = cargoType;
	}

	/**
	 * 取得装货港
	 */
	public String getStartPortNo() {
		return startPortNo;
	}

	/**
	 * 设置装货港
	 */
	public void setStartPortNo(String startPortNo) {
		this.startPortNo = startPortNo;
	}

	/**
	 * 取得卸货港
	 */
	public String getEndPortNo() {
		return endPortNo;
	}

	/**
	 * 设置卸货港
	 */
	public void setEndPortNo(String endPortNo) {
		this.endPortNo = endPortNo;
	}

	/**
	 * 取得航程
	 */
	public Integer getDistance() {
		return distance;
	}

	/**
	 * 设置航程
	 */
	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
