package com.hangyi.eyunda.data.ship;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;

public class ShipPortData extends BaseData {
	private static final long serialVersionUID = -1L;

	private ScfPortCityCode portCity = null;
	private PortData cargoPortData = null;

	public String getCityName() {
		if (portCity != null)
			return portCity.getDescription();
		else if (cargoPortData != null)
			return cargoPortData.getFullName();
		else
			return "";
	}

	public ScfPortCityCode getPortCity() {
		return portCity;
	}

	public void setPortCity(ScfPortCityCode portCity) {
		this.portCity = portCity;
	}

	public PortData getCargoPortData() {
		return cargoPortData;
	}

	public void setCargoPortData(PortData cargoPortData) {
		this.cargoPortData = cargoPortData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
