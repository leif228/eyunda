package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydShipPort;

@Repository
public class YydShipPortDao extends PageHibernateDao<YydShipPort, Long> {

	public List<YydShipPort> getShipPorts(Long shipId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("shipId", shipId));

		return super.find(criteria);
	}

}
