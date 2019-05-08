package com.hangyi.eyunda.dao.shipmove;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.domain.shipmove.MovShipUpdown;

@Repository
public class MovShipUpdownDao extends PageHibernateDao<MovShipUpdown, Long> {

	public List<MovShipUpdown> findByArvlftId(Long arvlftId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("arvlftId", arvlftId));
		criteria.addOrder(Order.asc("cargoType"));

		return super.find(criteria);
	}

}
