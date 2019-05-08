package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydShipAttr;

@Repository
public class YydShipAttrDao extends PageHibernateDao<YydShipAttr, Long> {

	public int batchDelete(String shipCode) {
		String hql = "delete from YydShipAttr where shipCode = ?";
		int n = super.batchExecute(hql, new Object[] { shipCode });
		return n;
	}

	// 取出船舶对应某一动态属性的取值
	public YydShipAttr getShipAttrValue(String shipCode, String attrNameCode) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("shipCode", shipCode));
		criteria.add(Restrictions.eq("attrNameCode", attrNameCode));

		@SuppressWarnings("unchecked")
		List<YydShipAttr> list = criteria.list();

		if (!list.isEmpty())
			return list.get(0);
		else
			return null;
	}
}
