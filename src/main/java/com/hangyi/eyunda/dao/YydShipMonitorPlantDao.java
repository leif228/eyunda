package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydShipMonitorPlant;

@Repository
public class YydShipMonitorPlantDao extends PageHibernateDao<YydShipMonitorPlant, Long> {

	public YydShipMonitorPlant getPlantByMmsi(String mmsi) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("mmsi", mmsi));

		List<YydShipMonitorPlant> monitorPlant = super.find(criteria);

		if (!monitorPlant.isEmpty())
			return monitorPlant.get(0);
		else
			return null;
	}

}
