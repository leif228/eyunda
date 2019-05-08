package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.data.release.CarrierIssueData;
import com.hangyi.eyunda.domain.YydCarrierIssue;
import com.hangyi.eyunda.domain.enumeric.ColumnCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

@Repository
public class YydCarrierIssueDao extends PageHibernateDao<YydCarrierIssue, Long> {

	// 取得当前栏目的记录
	public Page<YydCarrierIssue> getPageByColumnCode(Page<CarrierIssueData> pageData, Long carrierId, ColumnCode selectCode) {
		pageData.setOrder(Page.ASC);
		pageData.setOrderBy("no");
		
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("carrierId", carrierId));
		criteria.add(Restrictions.eq("columnCode", selectCode));
		
		return super.findPageByCriteria(pageData, criteria);
	}

	// 取得当前栏目的最后一条记录
	public YydCarrierIssue getByColumnCode(ColumnCode columnCode, Long carrierId) {
		Page<YydCarrierIssue> page = new Page<YydCarrierIssue>();
		page.setOrder(Page.DESC);
		page.setOrderBy("no");
		page.setPageSize(1);
		
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("carrierId", carrierId));
		criteria.add(Restrictions.eq("columnCode", columnCode));
		
		super.findPageByCriteria(page, criteria);
		List<YydCarrierIssue> lastCarrierIssue = page.getResult();
		
		if(!lastCarrierIssue.isEmpty()){
			return lastCarrierIssue.get(0);
		}
		return null;
	}
	
	// 取得当前栏目已发布的记录
	public Page<YydCarrierIssue> getPageByColumnCode2(Page<CarrierIssueData> pageData, Long carrierId, ColumnCode selectCode) {
		pageData.setOrder(Page.ASC);
		pageData.setOrderBy("no");
		
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("carrierId", carrierId));
		criteria.add(Restrictions.eq("columnCode", selectCode));
		criteria.add(Restrictions.eq("releaseStatus", ReleaseStatusCode.publish));
		
		return super.findPageByCriteria(pageData, criteria);
	}

	// 取得当前栏目已发布的最后一条记录
	public YydCarrierIssue getByColumnCode2(ColumnCode columnCode, Long carrierId) {
		Page<YydCarrierIssue> page = new Page<YydCarrierIssue>();
		page.setOrder(Page.DESC);
		page.setOrderBy("no");
		page.setPageSize(1);
		
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("carrierId", carrierId));
		criteria.add(Restrictions.eq("columnCode", columnCode));
		criteria.add(Restrictions.eq("releaseStatus", ReleaseStatusCode.publish));
		
		super.findPageByCriteria(page, criteria);
		List<YydCarrierIssue> lastCarrierIssue = page.getResult();
		
		if(!lastCarrierIssue.isEmpty()){
			return lastCarrierIssue.get(0);
		}
		return null;
	}
	
	// 取得当前栏目的所有记录
	@SuppressWarnings("unchecked")
	public List<YydCarrierIssue> getAll(Long carrierId, ColumnCode columnCode) {
		
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("carrierId", carrierId));
		criteria.add(Restrictions.eq("columnCode", columnCode));
		
		List<YydCarrierIssue> carrierIssues = criteria.list();
		
		if(carrierIssues != null && !carrierIssues.isEmpty()){
			return carrierIssues;
		}
		return null;
	}
	
}
