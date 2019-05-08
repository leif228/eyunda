package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.PubPayCnapsBank;

@Repository
public class PubPayCnapsBankDao extends PageHibernateDao<PubPayCnapsBank, Long> {

	public List<PubPayCnapsBank> searchOpenBank(String keyword, String bankCodeNo, String areaCode) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("bankClsCode", bankCodeNo));
		criteria.add(Restrictions.eq("cityCode", areaCode));
		if(!"".equals(keyword))
			criteria.add(Restrictions.like("bankName", keyword, MatchMode.ANYWHERE));
			
		@SuppressWarnings("unchecked")
		List<PubPayCnapsBank> userInfos = criteria.list();

		return userInfos;
	}

}
