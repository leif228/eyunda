package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.HyqUserBankCard;
import com.hangyi.eyunda.domain.enumeric.BankCode;

@Repository
public class HyqUserBankCardDao extends PageHibernateDao<HyqUserBankCard, Long> {

	public Page<HyqUserBankCard> findBankDatas(Long userId, int pageNo) {
		Page<HyqUserBankCard> page = new Page<HyqUserBankCard>();
		page.setPageNo(pageNo);
		page.setOrder(Page.DESC + "," + Page.DESC);
		page.setOrderBy("isRcvCard,bindTime");

		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("userId", userId));

		return findPageByCriteria(page, criteria);
	}

	public List<HyqUserBankCard> findBankDatas(Long userId) {
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("userId", userId));

		// 缺省收款帐户排第一
		criteria.addOrder(Order.desc("isRcvCard"));

		List<HyqUserBankCard> bankCards = super.find(criteria);

		return bankCards;
	}

	public HyqUserBankCard findBy(Long userId, BankCode bankCode, String accountName, String cardNo) {
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("bankCode", bankCode));
		criteria.add(Restrictions.eq("accountName", accountName));
		criteria.add(Restrictions.eq("cardNo", cardNo));

		List<HyqUserBankCard> bankCards = super.find(criteria);
		if (!bankCards.isEmpty())
			return bankCards.get(0);
		else
			return null;
	}

	public HyqUserBankCard findRcvBank(Long userId) {
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("userId", userId));

		List<HyqUserBankCard> bankCards = super.find(criteria);
		if (!bankCards.isEmpty())
			return bankCards.get(0);
		else
			return null;
	}

}
