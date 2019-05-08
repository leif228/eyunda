package com.hangyi.eyunda.data.ship;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;

public class UpDownPortData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L;

	private WaresBigTypeCode waresBigType = null; // 船盘大类
	private WaresTypeCode waresType = null; // 船盘分类
	private CargoTypeCode cargoType = null; // 货类

	private String startPortNo = ""; // 装卸港
	private PortData startPortData = null; // 装卸港

	private boolean gotoThisPort = false; // 是否在此港口装卸

	private Integer weight = 0; // 载重量

	public UpDownPortData() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WaresBigTypeCode getWaresBigType() {
		return waresBigType;
	}

	public void setWaresBigType(WaresBigTypeCode waresBigType) {
		this.waresBigType = waresBigType;
	}

	public WaresTypeCode getWaresType() {
		return waresType;
	}

	public void setWaresType(WaresTypeCode waresType) {
		this.waresType = waresType;
	}

	public CargoTypeCode getCargoType() {
		return cargoType;
	}

	public void setCargoType(CargoTypeCode cargoType) {
		this.cargoType = cargoType;
	}

	public String getStartPortNo() {
		return startPortNo;
	}

	public void setStartPortNo(String startPortNo) {
		this.startPortNo = startPortNo;
	}

	public PortData getStartPortData() {
		return startPortData;
	}

	public void setStartPortData(PortData startPortData) {
		this.startPortData = startPortData;
	}

	public boolean isGotoThisPort() {
		return gotoThisPort;
	}

	public boolean getGotoThisPort() {
		return gotoThisPort;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public void setGotoThisPort(boolean gotoThisPort) {
		this.gotoThisPort = gotoThisPort;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
