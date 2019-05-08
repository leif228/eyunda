package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.HyqUserDept;
import com.hangyi.eyunda.domain.enumeric.UserStatusCode;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.DESHelper;

@Repository
public class HyqUserDeptDao extends PageHibernateDao<HyqUserDept, Long> {

	public List<HyqUserDept> getByDeptId(Long deptId) {

		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.addOrder(Order.asc("createTime"));

		return super.find(criteria);
	}

	public HyqUserDept getByCompIdDeptIdUserId(Long compId, Long deptId, Long userId) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("compId", compId));
		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.add(Restrictions.eq("userId", userId));

		List<HyqUserDept> yydUserDepts = super.find(criteria);
		if (!yydUserDepts.isEmpty())
			return yydUserDepts.get(0);
		else
			return null;
	}

	public List<HyqUserDept> getByCompIdUserId(Long compId, Long userId) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("compId", compId));
		criteria.add(Restrictions.eq("userId", userId));

		List<HyqUserDept> yydUserDepts = super.find(criteria);
		return yydUserDepts;
	}

	public List<HyqUserDept> getByCompIdKeyWords(Long compId, String keyWords, UserStatusCode status) {
		Page<HyqUserDept> page = new Page<HyqUserDept>();
		page.setPageSize(10);
		page.setPageNo(1);

		String hql = "select b from HyqUserInfo a, HyqUserDept b where a.id = b.userId and b.compId = " + compId;

		if (keyWords != null && !"".equals(keyWords))
			hql += " and (a.loginName like '%" + keyWords + "%' or a.trueName like '%" + keyWords
					+ "%' or a.nickName like '%" + keyWords + "%' or a.email = '" + keyWords + "' or mobile = '"
					+ DESHelper.DoDES(keyWords, Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE) + "')";

		if (status != null)
			hql += " and a.status = " + status.ordinal();

		hql += " order by a.createTime desc";

		super.findPage(page, hql, new Object[] {});

		return page.getResult();
	}

}
