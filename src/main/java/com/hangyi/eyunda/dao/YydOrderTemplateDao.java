package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydOrderTemplate;
import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;

@Repository
public class YydOrderTemplateDao extends PageHibernateDao<YydOrderTemplate, Long> {

	public List<YydOrderTemplate> getYydOrderTemplates() {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.addOrder(Order.asc("orderType"));

		List<YydOrderTemplate> YydOrderTemplates = super.find(criteria);

		return YydOrderTemplates;
	}

	public YydOrderTemplate getTemplateByOrderType(OrderTypeCode orderType) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("orderType", orderType));

		List<YydOrderTemplate> YydOrderTemplates = super.find(criteria);

		if (!YydOrderTemplates.isEmpty())
			return YydOrderTemplates.get(0);
		else
			return null;
	}

}
