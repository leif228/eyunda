package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.HyqUserAccount;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.DESHelper;

@Repository
public class HyqUserAccountDao extends PageHibernateDao<HyqUserAccount, Long> {

	public HyqUserAccount getAccountByUserId(Long userId, PayStyleCode payStyle) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("payStyle", payStyle));

		List<HyqUserAccount> userAccounts = super.find(criteria);

		if (!userAccounts.isEmpty())
			return userAccounts.get(0);
		else
			return null;
	}

	public List<HyqUserAccount> getAccountsLikeLoginName(String loginName, PayStyleCode payStyle) {
		String hql = "select a from HyqUserAccount a, ScfUser b where a.userId = b.id and a.payStyle = "
				+ payStyle.ordinal() + " and (b.loginName = '" + loginName + "' or b.email = '" + loginName
				+ "' or b.mobile = '" + DESHelper.DoDES(loginName, Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE) + "')";

		List<HyqUserAccount> list = super.find(hql);

		return list;
	}

}
