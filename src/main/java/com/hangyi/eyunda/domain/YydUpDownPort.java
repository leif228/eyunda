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
@Table(name = "YydUpDownPort")
public class YydUpDownPort extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Enumerated(EnumType.ORDINAL)
	private WaresBigTypeCode waresBigType = null; // 船盘大类
	@Enumerated(EnumType.ORDINAL)
	private WaresTypeCode waresType = null; // 船盘分类
	@Enumerated(EnumType.ORDINAL)
	private CargoTypeCode cargoType = null; // 货类
	@Column(nullable = true, length = 8)
	private String startPortNo = ""; // 装卸港
	@Column
	private Integer weight = 0; // 缺省载重量

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
	 * 取得装卸港
	 */
	public String getStartPortNo() {
		return startPortNo;
	}

	/**
	 * 设置装卸港
	 */
	public void setStartPortNo(String startPortNo) {
		this.startPortNo = startPortNo;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}
