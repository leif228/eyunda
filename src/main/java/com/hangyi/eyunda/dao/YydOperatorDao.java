package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydOperator;
import com.hangyi.eyunda.domain.enumeric.ApplyStatusCode;
import com.hangyi.eyunda.domain.enumeric.UserStatusCode;

@Repository
public class YydOperatorDao extends PageHibernateDao<YydOperator, Long> {

	// 通过代理公司编码获得代理人页
	public Page<YydOperator> getOperatorPage(int pageSize, int pageNo, Long... ids) {
		Page<YydOperator> page = new Page<YydOperator>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);

		String hql = "select a from YydOperator a, YydUserInfo b where a.userId = b.id and a.status = ? and b.status = ?";

		if (ids != null && ids.length > 0)
			hql += " and a.userId = " + ids[0];

		super.findPage(page, hql, new Object[] { ApplyStatusCode.approve, UserStatusCode.activity });

		return page;
	}

	public YydOperator getOperByUserId(Long userId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("status", ApplyStatusCode.approve));

		List<YydOperator> yydOperators = super.find(criteria);

		if (!yydOperators.isEmpty())
			return yydOperators.get(0);
		else
			return null;
	}

	public YydOperator getSelfOperByUserId(Long userId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));

		List<YydOperator> yydOperators = super.find(criteria);

		if (!yydOperators.isEmpty())
			return yydOperators.get(0);
		else
			return null;
	}

	public Page<YydOperator> getByOperInfos(int pageNo, String operInfo, ApplyStatusCode status) {
		Page<YydOperator> page = new Page<YydOperator>();
		page.setPageNo(pageNo);

		String hql = "select a from YydOperator a, YydUserInfo b where a.userId = b.id ";

		if (status != null) {
			hql += "and a.status=" + status.ordinal() + " ";
		}

		if (operInfo != null && !"".equals(operInfo)) {
			hql += "and (b.loginName like '%" + operInfo + "%' " + "or b.nickName like '%" + operInfo + "%' "
					+ "or b.mobile like '%" + operInfo + "%' " + "or b.email like '%" + operInfo + "%')";
		}

		super.findPage(page, hql, new Object[] {});

		return page;
	}

}
