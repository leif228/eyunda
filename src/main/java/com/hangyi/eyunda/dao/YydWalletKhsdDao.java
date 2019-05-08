package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydWalletKhsd;

@Repository
public class YydWalletKhsdDao extends PageHibernateDao<YydWalletKhsd, Long> {

	public YydWalletKhsd getByPayTranFunc(String paymentNo, String tranFunc) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("paymentNo", paymentNo));
		criteria.add(Restrictions.eq("tranFunc", tranFunc));

		List<YydWalletKhsd> ywkh = super.find(criteria);

		if (!ywkh.isEmpty())
			return ywkh.get(0);
		else
			return null;
	}

	public List<YydWalletKhsd> findByPaymentNo(String paymentNo) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("paymentNo", paymentNo));

		List<YydWalletKhsd> ywkh = super.find(criteria);

		return ywkh;
	}

}
