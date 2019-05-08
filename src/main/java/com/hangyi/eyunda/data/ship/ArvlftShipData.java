package com.hangyi.eyunda.data.ship;

import com.hangyi.eyunda.data.BaseData;

public class ArvlftShipData extends BaseData {
	private static final long serialVersionUID = -1L;

	private ShipData shipData = null;
	private ShipArvlftData arriveData = null;
	private ShipArvlftData leftData = null;

	public ShipData getShipData() {
		return shipData;
	}

	public void setShipData(ShipData shipData) {
		this.shipData = shipData;
	}

	public ShipArvlftData getArriveData() {
		return arriveData;
	}

	public void setArriveData(ShipArvlftData arriveData) {
		this.arriveData = arriveData;
	}

	public ShipArvlftData getLeftData() {
		return leftData;
	}

	public void setLeftData(ShipArvlftData leftData) {
		this.leftData = leftData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
