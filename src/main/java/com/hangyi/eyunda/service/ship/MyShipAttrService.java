package com.hangyi.eyunda.service.ship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydShipAttrDao;
import com.hangyi.eyunda.data.ship.ShipAttrData;
import com.hangyi.eyunda.domain.YydShipAttr;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class MyShipAttrService extends BaseService<YydShipAttr, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydShipAttrDao shipAttrDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageHibernateDao<YydShipAttr, Long> getDao() {
		return (PageHibernateDao) shipAttrDao;
	}

	// 取出船舶对应某一动态属性的取值
	public ShipAttrData getShipAttrValue(String shipCode, String attrNameCode) {
		YydShipAttr shipAttr = shipAttrDao.getShipAttrValue(shipCode, attrNameCode);

		if (shipAttr != null) {
			ShipAttrData shipAttrData = new ShipAttrData();
			CopyUtil.copyProperties(shipAttrData, shipAttr);

			return shipAttrData;
		} else
			return null;
	}

}
