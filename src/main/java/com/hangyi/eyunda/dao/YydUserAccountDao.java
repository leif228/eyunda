package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydUserAccount;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.DESHelper;

@Repository
public class YydUserAccountDao extends PageHibernateDao<YydUserAccount, Long> {

	public String getAccountNoByIdCode(String idCode, String accounter) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("idCode", DESHelper.DoDES(idCode, Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE)));
		criteria.add(Restrictions.eq("accounter", accounter));
		criteria.add(Restrictions.eq("payStyle", PayStyleCode.pinganpay));

		List<YydUserAccount> userAccounts = super.find(criteria);

		if (!userAccounts.isEmpty())
			return DESHelper.DoDES(userAccounts.get(0).getAccountNo(), Constants.SALT_VALUE, DESHelper.DECRYPT_MODE);
		else
			return null;
	}

	public YydUserAccount getAccountByUserId(Long userId, PayStyleCode payStyle) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("payStyle", payStyle));

		List<YydUserAccount> userAccounts = super.find(criteria);

		if (!userAccounts.isEmpty())
			return userAccounts.get(0);
		else
			return null;
	}

	public List<YydUserAccount> getAccountsLikeLoginName(String loginName, PayStyleCode payStyle) {
		String hql = "select a from YydUserAccount a, YydUserInfo b where a.userId = b.id and a.payStyle = "
				+ payStyle.ordinal() + " and (b.loginName = '" + loginName + "' or b.email = '" + loginName
				+ "' or b.mobile = '" + DESHelper.DoDES(loginName, Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE) + "')";

		List<YydUserAccount> list = super.find(hql);

		return list;
	}

}
