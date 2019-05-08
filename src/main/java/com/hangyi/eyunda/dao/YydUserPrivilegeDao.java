package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydUserPrivilege;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;

@Repository
public class YydUserPrivilegeDao extends PageHibernateDao<YydUserPrivilege, Long> {

	public List<YydUserPrivilege> getByUserIdCompId(Long userId, Long compId) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("compId", compId));
		criteria.addOrder(Order.asc("privilege"));

		return super.find(criteria);
	}

	public List<YydUserPrivilege> getByCompIdPrivilege(Long compId, UserPrivilegeCode privilege) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("compId", compId));
		criteria.add(Restrictions.eq("privilege", privilege));
		criteria.addOrder(Order.asc("createTime"));

		return super.find(criteria);
	}

	public YydUserPrivilege getSailorByCompIdMmsi(Long compId, String mmsi) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("compId", compId));
		criteria.add(Restrictions.eq("mmsis", mmsi));

		List<YydUserPrivilege> yups = super.find(criteria);

		if (!yups.isEmpty())
			return yups.get(0);
		else
			return null;
	}

	public YydUserPrivilege getByCompIdUserIdPrivilege(Long compId, Long userId, UserPrivilegeCode privilege) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("compId", compId));
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("privilege", privilege));

		List<YydUserPrivilege> yups = super.find(criteria);

		if (!yups.isEmpty())
			return yups.get(0);
		else
			return null;
	}

	// 此方法有问题，仅在船员用自己的手机定位船舶位置时暂时使用，这种情况下一个船员只会在一个公司有动态上报权限
	public YydUserPrivilege getByCompIdUserIdPrivilege(Long userId, UserPrivilegeCode privilege) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("privilege", privilege));

		List<YydUserPrivilege> yups = super.find(criteria);

		if (!yups.isEmpty())
			return yups.get(0);
		else
			return null;
	}

}
