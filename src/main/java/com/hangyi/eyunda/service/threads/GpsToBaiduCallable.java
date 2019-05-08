package com.hangyi.eyunda.service.threads;

import java.util.List;
import java.util.concurrent.Callable;

import com.hangyi.eyunda.data.ship.ShipCooordData;
import com.hangyi.eyunda.service.map.ShipCooordService;

public class GpsToBaiduCallable implements Callable<List<ShipCooordData>> {
	private ShipCooordService shipCooordService;
	private List<ShipCooordData> datas;

	public GpsToBaiduCallable(ShipCooordService shipCooordService, List<ShipCooordData> datas) {
		this.shipCooordService = shipCooordService;
		this.datas = datas;
	}

	public List<ShipCooordData> call() {
		List<ShipCooordData> result = shipCooordService.twentyGps2baidu(datas);
		return result;
	}

}
