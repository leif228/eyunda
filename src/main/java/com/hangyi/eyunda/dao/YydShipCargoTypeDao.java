package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydShipCargoType;

@Repository
public class YydShipCargoTypeDao extends PageHibernateDao<YydShipCargoType, Long> {
	
	@SuppressWarnings("unchecked")
	public List<YydShipCargoType> getShipCargoTypes(Long shipId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("shipId", shipId));

		return criteria.list();
	}
	
}
