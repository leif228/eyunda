package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydUserDept;

@Repository
public class YydUserDeptDao extends PageHibernateDao<YydUserDept, Long> {

	public List<YydUserDept> getByDeptId(Long deptId) {

		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.addOrder(Order.asc("createTime"));

		return super.find(criteria);
	}

	public YydUserDept getByCompIdDeptIdUserId(Long compId, Long deptId, Long userId) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("compId", compId));
		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.add(Restrictions.eq("userId", userId));

		List<YydUserDept> yydUserDepts = super.find(criteria);
		if (!yydUserDepts.isEmpty())
			return yydUserDepts.get(0);
		else
			return null;
	}

}
