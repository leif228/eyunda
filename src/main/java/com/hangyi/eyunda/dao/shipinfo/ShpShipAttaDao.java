package com.hangyi.eyunda.dao.shipinfo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.domain.shipinfo.ShpShipAtta;

@Repository
public class ShpShipAttaDao extends PageHibernateDao<ShpShipAtta, Long> {

	public List<ShpShipAtta> findByShipId(Long shipId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("beDeleted", YesNoCode.no));
		criteria.add(Restrictions.eq("shipId", shipId));
		criteria.addOrder(Order.asc("no"));

		return super.find(criteria);
	}

}
