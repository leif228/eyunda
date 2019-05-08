package com.hangyi.eyunda.service.threads;

import java.util.List;
import java.util.concurrent.Callable;

import com.hangyi.eyunda.data.ship.ShipCooordData;
import com.hangyi.eyunda.domain.enumeric.ShipMonitorPlantCode;
import com.hangyi.eyunda.service.map.ShipCooordService;

public class MyCallable implements Callable<List<ShipCooordData>> {
	private ShipCooordService shipCooordService;
	private String mmsi;
	private String startTime;
	private String endTime;
	private ShipMonitorPlantCode platCode;
	
	public MyCallable(ShipCooordService shipCooordService, String mmsi, String startTime, String endTime, ShipMonitorPlantCode platCode) {
		this.shipCooordService = shipCooordService;
		this.mmsi = mmsi;
		this.startTime = startTime;
		this.endTime = endTime;
		this.platCode = platCode;
	}

	public List<ShipCooordData> call() {
		List<ShipCooordData> result = shipCooordService.getShipCooords(mmsi, startTime, endTime, platCode);
		return result;
	}

}
