package com.hangyi.eyunda.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.data.complain.ComplainData;
import com.hangyi.eyunda.domain.YydComplain;

@Repository
public class YydComplainDao extends PageHibernateDao<YydComplain, Long> {

	public Page<YydComplain> getList(Page<ComplainData> pageData) {
		
		pageData.setOrder(Page.DESC);
		pageData.setOrderBy("createTime");
		
		Criteria criteria = this.getSession().createCriteria(entityClass);
		
		return super.findPageByCriteria(pageData, criteria);
	}
	
	public Page<YydComplain> getByUserId(Page<ComplainData> pageData, Long userId) {
		
		pageData.setOrder(Page.DESC);
		pageData.setOrderBy("createTime");
		
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("userId", userId));
		
		return super.findPageByCriteria(pageData, criteria);
	}

	
}
