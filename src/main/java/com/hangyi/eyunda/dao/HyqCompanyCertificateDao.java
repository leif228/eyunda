package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.HyqCompanyCertificate;

@Repository
public class HyqCompanyCertificateDao extends PageHibernateDao<HyqCompanyCertificate, Long> {

	public Page<HyqCompanyCertificate> getCompCertPage(int pageNo, int pageSize, String keyWords) {
		Page<HyqCompanyCertificate> page = new Page<HyqCompanyCertificate>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);

		Criteria criteria = this.getSession().createCriteria(entityClass);

		if (keyWords != null && !"".equals(keyWords))
			criteria.add(Restrictions.like("compName", keyWords, MatchMode.ANYWHERE));

		criteria.addOrder(Order.desc("createTime"));

		return super.findPageByCriteria(page, criteria);
	}

	public HyqCompanyCertificate getByCompName(String compName) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("compName", compName));

		List<HyqCompanyCertificate> certificates = super.find(criteria);

		if (!certificates.isEmpty())
			return certificates.get(0);
		else
			return null;
	}

}
