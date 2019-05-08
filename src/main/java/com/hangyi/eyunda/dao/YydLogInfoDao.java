package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydLogInfo;

@Repository
public class YydLogInfoDao extends PageHibernateDao<YydLogInfo, Long> {
	public static final int PREV_THOUSAND = 1000;
	public static final int FIRST_PAGENO = 1;

	/** 取最新的1000条日志 */
	public Page<YydLogInfo> getPrevThousandLogs() {
		Page<YydLogInfo> page = new Page<YydLogInfo>();
		page.setPageSize(PREV_THOUSAND);
		page.setPageNo(FIRST_PAGENO);
		page.setOrderBy("optionTime");
		page.setOrder(Page.DESC);

		return super.getAll(page);
	}

	public List<YydLogInfo> findLogsAboutModule(Long moduleId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("moduleId", moduleId));

		List<YydLogInfo> yydLogInfos = super.find(criteria);

		return yydLogInfos;
	}

	public int deleteLogsAboutModule(Long moduleId) {
		String hql = "delete from YydLogInfo where moduleId = ? ";

		int n = super.batchExecute(hql, new Object[] { moduleId });

		return n;
	}

}
