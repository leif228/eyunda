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
import com.hangyi.eyunda.dao.YydShipUpdownDao;
import com.hangyi.eyunda.data.ship.ShipUpdownData;
import com.hangyi.eyunda.domain.YydShipUpdown;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class MyShipUpDownService extends BaseService<YydShipUpdown, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydShipUpdownDao shipUpdownDao;

	@Override
	public PageHibernateDao<YydShipUpdown, Long> getDao() {
		return (PageHibernateDao<YydShipUpdown, Long>) shipUpdownDao;
	}

	public List<ShipUpdownData> getShipUpdownDatas(Long arvlftId) {
		List<ShipUpdownData> shipUpdownDatas = new ArrayList<ShipUpdownData>();

		List<YydShipUpdown> shipUpdowns = shipUpdownDao.getShipUpdowns(arvlftId);

		for (YydShipUpdown shipUpdown : shipUpdowns)
			shipUpdownDatas.add(this.getShipUpdownData(shipUpdown));

		return shipUpdownDatas;
	}

	public ShipUpdownData getShipUpdownData(YydShipUpdown shipUpdown) {
		ShipUpdownData shipUpdownData = new ShipUpdownData();

		CopyUtil.copyProperties(shipUpdownData, shipUpdown);

		return shipUpdownData;
	}

}
