package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.HyqDepartment;

@Repository
public class HyqDepartmentDao extends PageHibernateDao<HyqDepartment, Long> {

	public List<HyqDepartment> getByCompId(Long compId) {

		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("compId", compId));
		criteria.addOrder(Order.asc("createTime"));

		return super.find(criteria);
	}

}
