package com.hangyi.eyunda.service.ship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydShipMonitorPlantDao;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.domain.YydShipMonitorPlant;
import com.hangyi.eyunda.domain.enumeric.ShipMonitorPlantCode;
import com.hangyi.eyunda.service.BaseService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ShipMonitorPlantService extends BaseService<YydShipMonitorPlant, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydShipMonitorPlantDao plantDao;

	@Autowired
	private MyShipService shipService;

	@Override
	public PageHibernateDao<YydShipMonitorPlant, Long> getDao() {
		return (PageHibernateDao<YydShipMonitorPlant, Long>) plantDao;
	}

	public boolean savePlant(Long shipId, ShipMonitorPlantCode plantCode) {
		try {
			ShipData shipData = shipService.getShipData(shipId);
			if (shipData != null) {
				YydShipMonitorPlant ysp = plantDao.getPlantByMmsi(shipData.getMmsi());
				if (ysp == null)
					ysp = new YydShipMonitorPlant();

				ysp.setPlantCodeCode(plantCode);
				ysp.setMmsi(shipData.getMmsi());

				plantDao.save(ysp);
			}
			return true;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}

	public ShipMonitorPlantCode getPlant(String mmsi) {
		YydShipMonitorPlant ysp = plantDao.getPlantByMmsi(mmsi);
		if (ysp != null)
			return ysp.getPlantCode();
		else
			return ShipMonitorPlantCode.baochuanwang;
	}

}
