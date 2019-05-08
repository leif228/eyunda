package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.HyqRecordLogin;
import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.DESHelper;

@Repository
public class HyqRecordLoginDao extends PageHibernateDao<HyqRecordLogin, Long> {

	public Page<Object> findUserLoginLogs(int pageNo, int pageSize, String userInfo, String startDate, String endDate) {
		Page<Object> page = new Page<Object>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select a, b from HyqUserInfo a, HyqRecordLogin b where a.id = b.userId";

		if (userInfo != null && !"".equals(userInfo))
			hql += " and (a.loginName like '%" + userInfo + "%' or a.trueName like '%" + userInfo
					+ "%' or a.nickName like '%" + userInfo + "%' or a.email = '" + userInfo + "' or mobile = '"
					+ DESHelper.DoDES(userInfo, Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE) + "')";

		if (startDate != null && !"".equals(startDate)) {
			String sStart = CalendarUtil
					.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.getTheDayZero(CalendarUtil.parseYYYY_MM_DD(startDate)));
			hql += " and b.loginTime >= '" + sStart + "'";
		}

		if (endDate != null && !"".equals(endDate)) {
			String sEnd = CalendarUtil
					.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.getTheDayMidnight(CalendarUtil.parseYYYY_MM_DD(endDate)));
			hql += " and b.loginTime <= '" + sEnd + "'";
		}

		hql += " order by b.loginTime desc";

		super.findPage(page, hql);
		return page;
	}

	public HyqRecordLogin getLastByUserId(Long userId, LoginSourceCode loginSource, String sessionId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("loginSource", loginSource));
		criteria.add(Restrictions.eq("sessionId", sessionId));

		List<HyqRecordLogin> records = super.find(criteria);

		if (!records.isEmpty())
			return records.get(0);
		else
			return null;
	}

}
