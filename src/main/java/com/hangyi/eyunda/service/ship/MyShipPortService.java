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

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydShipPortDao;
import com.hangyi.eyunda.data.ship.ShipPortData;
import com.hangyi.eyunda.domain.YydShipPort;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.service.BaseService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class MyShipPortService extends BaseService<YydShipPort, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydShipPortDao shipPortDao;
	@Autowired
	private PortService portService;

	@Override
	public PageHibernateDao<YydShipPort, Long> getDao() {
		return (PageHibernateDao<YydShipPort, Long>) shipPortDao;
	}

	public List<ShipPortData> getShipPortDatas(Long shipId) {
		List<ShipPortData> shipPortDatas = new ArrayList<ShipPortData>();

		// 船舶接货港口列表
		List<YydShipPort> shipPorts = shipPortDao.getShipPorts(shipId);
		for (YydShipPort shipPort : shipPorts)
			shipPortDatas.add(this.getShipPortData(shipPort));

		return shipPortDatas;
	}

	public ShipPortData getShipPortData(YydShipPort shipPort) {
		ShipPortData shipPortData = new ShipPortData();
		if (null != shipPort) {
			if (shipPort.getPortNo().trim().length() == 2)
				shipPortData.setPortCity(ScfPortCityCode.getByCode(shipPort.getPortNo()));
			else if (shipPort.getPortNo().trim().length() == 4)
				shipPortData.setCargoPortData(portService.getPortData(shipPort.getPortNo()));
			else
				;
		}

		return shipPortData;
	}

	public boolean saveShipPorts(Long shipId, String[] cityorports) {
		try {
			// 先删除旧的
			shipPortDao.batchExecute("delete from YydShipPort where shipId = ?", shipId);
			// 再保存新的
			for (String cityorport : cityorports) {
				if (!cityorport.equals("")) {
					YydShipPort shipPort = new YydShipPort();
					shipPort.setPortNo(cityorport);
					shipPort.setShipId(shipId);
					shipPortDao.save(shipPort);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
