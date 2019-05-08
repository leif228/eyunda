package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydUserBankCard;
import com.hangyi.eyunda.domain.enumeric.BankCode;

@Repository
public class YydUserBankCardDao extends PageHibernateDao<YydUserBankCard, Long> {

	@SuppressWarnings({ "rawtypes" })
	public Page findBankDatas(Long userId, int pageNo) {
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setOrder(Page.DESC + "," + Page.DESC);
		page.setOrderBy("isRcvCard,bindTime");
		
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("userId", userId));
				
		return findPageByCriteria(page, criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<YydUserBankCard> findBankDatas(Long userId) {
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("userId", userId));
		
		// 缺省收款帐户排第一
		criteria.addOrder(Order.desc("isRcvCard"));

		List<YydUserBankCard> bankCards = criteria.list();
		
		return bankCards;
	}

	@SuppressWarnings("unchecked")
	public YydUserBankCard findBy(Long userId, BankCode bankCode, String accountName, String cardNo) {
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("bankCode", bankCode));
		criteria.add(Restrictions.eq("accountName", accountName));
		criteria.add(Restrictions.eq("cardNo", cardNo));

		List<YydUserBankCard> bankCards = criteria.list();
		if (!bankCards.isEmpty())
			return bankCards.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public YydUserBankCard findRcvBank(Long userId) {
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("userId", userId));

		List<YydUserBankCard> bankCards = criteria.list();
		if (!bankCards.isEmpty())
			return bankCards.get(0);
		else
			return null;
	}

}
