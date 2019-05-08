package com.hangyi.eyunda.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydNotice;
import com.hangyi.eyunda.domain.enumeric.NtcColumnCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

@Repository
public class YydNoticeDao extends PageHibernateDao<YydNotice, Long> {

	public Page<YydNotice> findNotices(int pageSize, int pageNo, NtcColumnCode... columnCodes) {
		Page<YydNotice> page = new Page<YydNotice>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(Page.DESC + "," + Page.DESC);
		page.setOrderBy("top,publishTime");

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("releaseStatus", ReleaseStatusCode.publish));

		if (columnCodes == null)
			criteria.add(Restrictions.eq("ntcColumn", NtcColumnCode.bulletin));
		else if (columnCodes.length == 1)
			criteria.add(Restrictions.eq("ntcColumn", columnCodes[0]));
		else if (columnCodes.length == 2)
			criteria.add(Restrictions.or(Restrictions.eq("ntcColumn", columnCodes[0]),
					Restrictions.eq("ntcColumn", columnCodes[1])));

		return findPageByCriteria(page, criteria);
	}

	public Page<YydNotice> findManageNotices(int pageSize, int pageNo, NtcColumnCode selectCode) {
		Page<YydNotice> page = new Page<YydNotice>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(Page.DESC + "," + Page.DESC);
		page.setOrderBy("top,publishTime");

		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("ntcColumn", selectCode));

		return findPageByCriteria(page, criteria);
	}
}
