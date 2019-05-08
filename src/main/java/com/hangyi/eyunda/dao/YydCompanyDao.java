package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydCompany;

@Repository
public class YydCompanyDao extends PageHibernateDao<YydCompany, Long> {

	public List<YydCompany> getByUserId(Long userId) {
		String hql = "select distinct a from YydCompany a, YydUserDept b where a.id = b.compId and b.userId = "
				+ userId;
		hql += " order by b.createTime desc";

		List<YydCompany> yydCompanys = super.find(hql, new Object[] {});

		return yydCompanys;
	}

	public List<YydCompany> getByCompName(String compName) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("compName", compName));
		criteria.addOrder(Order.desc("createTime"));

		return super.find(criteria);
	}

}
