package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydDepartment;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;

@Repository
public class YydDepartmentDao extends PageHibernateDao<YydDepartment, Long> {

	public List<YydDepartment> getByCompId(Long compId) {

		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("compId", compId));
		criteria.addOrder(Order.asc("deptType"));
		criteria.addOrder(Order.asc("createTime"));

		return super.find(criteria);
	}

	public List<YydDepartment> getByCompIdDeptType(Long compId, UserPrivilegeCode deptType) {

		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("compId", compId));
		criteria.add(Restrictions.eq("deptType", deptType));
		criteria.addOrder(Order.asc("createTime"));

		return super.find(criteria);
	}

	public List<YydDepartment> getByCompIdUserIdDeptType(Long compId, Long userId, UserPrivilegeCode deptType) {

		String hql = "select distinct b from YydCompany a, YydDepartment b, YydUserDept c where a.id = b.compId and b.id = c.deptId and a.id = "
				+ compId + " and b.deptType = " + deptType.ordinal() + " and c.userId = " + userId
				+ " order by b.createTime desc";

		List<YydDepartment> ds = super.find(hql);

		return ds;
	}

}
