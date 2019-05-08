package com.hangyi.eyunda.dao;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.HyqMessage;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.util.Constants;

@Repository
public class HyqMessageDao extends PageHibernateDao<HyqMessage, Long> {

	public Page<HyqMessage> getMessagePage(int pageNo, int pageSize, YesNoCode status) {
		Page<HyqMessage> page = new Page<HyqMessage>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select a from HyqMessage a where 1 = 1";

		if (status != null)
			hql += " and a.status = " + status.ordinal();

		hql += " order by a.createTime desc";

		super.findPage(page, hql, new Object[] {});

		return page;
	}

}
