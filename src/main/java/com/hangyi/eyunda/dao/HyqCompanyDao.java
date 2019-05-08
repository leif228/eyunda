package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.HyqCompany;

@Repository
public class HyqCompanyDao extends PageHibernateDao<HyqCompany, Long> {

	public List<HyqCompany> getByUserId(Long userId) {
		String hql = "select distinct a from HyqCompany a, HyqUserDept b where a.id = b.compId and b.userId = "
				+ userId;
		hql += " order by b.createTime desc";

		List<HyqCompany> companys = super.find(hql, new Object[] {});

		return companys;
	}

	public HyqCompany getByCompName(String compName) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("compName", compName));
		criteria.addOrder(Order.desc("createTime"));

		List<HyqCompany> companys = super.find(criteria);

		if (!companys.isEmpty())
			return companys.get(0);
		else
			return null;
	}

}
