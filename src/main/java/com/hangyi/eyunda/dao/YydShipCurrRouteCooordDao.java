package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydShipCurrRouteCooord;

@Repository
public class YydShipCurrRouteCooordDao extends PageHibernateDao<YydShipCurrRouteCooord, Long> {

	public YydShipCurrRouteCooord getByMmsi(String shipMmsi) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("mmsi", shipMmsi));

		List<YydShipCurrRouteCooord> currRouteCooords = super.find(criteria);

		if (!currRouteCooords.isEmpty())
			return currRouteCooords.get(0);

		return null;
	}

	public List<YydShipCurrRouteCooord> getByMmsis(String[] shipMmsis) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.in("mmsi", shipMmsis));

		List<YydShipCurrRouteCooord> currRouteCooords = super.find(criteria);

		return currRouteCooords;
	}

}
