package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydWalletZjjz;

@Repository
public class YydWalletZjjzDao extends PageHibernateDao<YydWalletZjjz, Long> {

	public YydWalletZjjz getByLogNo(String logNo) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("logNo", logNo));

		List<YydWalletZjjz> ywzs = super.find(criteria);

		if (!ywzs.isEmpty())
			return ywzs.get(0);
		else
			return null;
	}

	public List<YydWalletZjjz> findFetchZjjz(String startDate, String endDate) {
		String hql = "select y from YydWalletZjjz y, YydWallet z where y.paymentNo = z.paymentNo and y.tranFunc='6085'";

		if (startDate != null && !"".equals(startDate))
			hql += " and z.gmtPayment > '" + startDate + "'";
		if (endDate != null && !"".equals(endDate))
			hql += " and z.gmtPayment < '" + endDate + "'";

		List<YydWalletZjjz> ywzs = super.find(hql, new Object[] {});

		return ywzs;
	}

	public YydWalletZjjz getByPayTranFunc(String paymentNo, String tranFunc, String funcFlag) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("paymentNo", paymentNo));
		criteria.add(Restrictions.eq("tranFunc", tranFunc));
		criteria.add(Restrictions.eq("funcFlag", funcFlag));

		List<YydWalletZjjz> ywzs = super.find(criteria);

		if (!ywzs.isEmpty())
			return ywzs.get(0);
		else
			return null;
	}

	public List<YydWalletZjjz> findByPaymentNo(String paymentNo) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("paymentNo", paymentNo));

		List<YydWalletZjjz> ywzs = super.find(criteria);

		return ywzs;
	}

}
