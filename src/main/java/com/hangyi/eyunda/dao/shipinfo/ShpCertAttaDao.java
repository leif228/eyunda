package com.hangyi.eyunda.dao.shipinfo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.domain.shipinfo.ShpCertAtta;

@Repository
public class ShpCertAttaDao extends PageHibernateDao<ShpCertAtta, Long> {

	public List<ShpCertAtta> findByCertId(Long certId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("beDeleted", YesNoCode.no));
		criteria.add(Restrictions.eq("certId", certId));
		criteria.addOrder(Order.asc("no"));

		return super.find(criteria);
	}

	public Integer getImageNo(Long certId) {
		Page<ShpCertAtta> page = new Page<ShpCertAtta>();
		page.setPageSize(1);
		page.setPageNo(1);

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("certId", certId));
		criteria.addOrder(Order.desc("no"));

		super.findPageByCriteria(page, criteria);
		List<ShpCertAtta> list = (List<ShpCertAtta>) page.getResult();
		if (list.isEmpty())
			return 1;
		else {
			return list.get(0).getNo() + 1;
		}
	}

}
