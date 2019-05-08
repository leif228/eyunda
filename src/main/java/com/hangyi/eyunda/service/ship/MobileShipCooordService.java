package com.hangyi.eyunda.service.ship;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.YydShipCooordDao;
import com.hangyi.eyunda.data.ship.ShipCooordData;
import com.hangyi.eyunda.domain2.YydShipCooord;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(value = "transactionManager2", propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class MobileShipCooordService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydShipCooordDao shipCooordDao;

	public ShipCooordData getShipCooordData(YydShipCooord shipCooord) throws Exception {
		ShipCooordData shipCooordData = new ShipCooordData();
		CopyUtil.copyProperties(shipCooordData, shipCooord);

		return shipCooordData;
	}

	public ShipCooordData findOneShipCooord(String mmsi) throws Exception {
		YydShipCooord shipCooord = shipCooordDao.findOneShipCooord(mmsi);
		ShipCooordData shipCooordData = this.getShipCooordData(shipCooord);

		return shipCooordData;
	}

	public List<ShipCooordData> findShipCooords(String includeMmsis) throws Exception {
		List<YydShipCooord> shipCooords = shipCooordDao.findShipCooords(includeMmsis);

		List<ShipCooordData> shipCooordDatas = new ArrayList<ShipCooordData>();
		for (YydShipCooord shipCooord : shipCooords) {
			ShipCooordData shipCooordData = this.getShipCooordData(shipCooord);
			if (shipCooordData != null)
				shipCooordDatas.add(shipCooordData);
		}

		return shipCooordDatas;
	}

	public List<ShipCooordData> findShipSailLine(String mmsi, String startTime, String endTime) throws Exception {
		List<YydShipCooord> shipCooords = shipCooordDao.findShipSailLine(mmsi, startTime, endTime);

		List<ShipCooordData> shipCooordDatas = new ArrayList<ShipCooordData>();
		for (YydShipCooord shipCooord : shipCooords) {
			ShipCooordData shipCooordData = this.getShipCooordData(shipCooord);
			if (shipCooordData != null)
				shipCooordDatas.add(shipCooordData);
		}

		return shipCooordDatas;
	}

	public ShipCooordData saveShipCooord(ShipCooordData shipCooordData) throws Exception {
		YydShipCooord shipCooord = shipCooordDao.findOneShipCooordByPosTime(shipCooordData.getMmsi(),
				shipCooordData.getPosTime());
		if (shipCooord == null)
			shipCooord = new YydShipCooord();

		CopyUtil.copyProperties(shipCooord, shipCooordData, new String[] { "id" });

		shipCooordDao.save(shipCooord);

		shipCooordData.setId(shipCooord.getId());

		return shipCooordData;
	}

	public boolean saveShipCooords(String mmsi, String shipName, List<ShipCooordData> shipCooordDatas)
			throws Exception {
		for (ShipCooordData shipCooordData : shipCooordDatas) {
			shipCooordData.setMmsi(mmsi);
			shipCooordData.setShipName(shipName);

			this.saveShipCooord(shipCooordData);
		}
		return true;
	}

}
