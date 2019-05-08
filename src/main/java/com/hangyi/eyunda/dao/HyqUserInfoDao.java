package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.HyqUserInfo;
import com.hangyi.eyunda.domain.enumeric.UserStatusCode;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.DESHelper;

@Repository
public class HyqUserInfoDao extends PageHibernateDao<HyqUserInfo, Long> {

	public HyqUserInfo getByLoginName(String loginName, Long... excludeIds) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.or(Restrictions.eq("loginName", loginName),
				Restrictions.eq("mobile", DESHelper.DoDES(loginName, Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE))));

		if (excludeIds.length > 0) {
			for (Long uid : excludeIds) {
				criteria.add(Restrictions.ne("id", uid));
			}
		}

		List<HyqUserInfo> users = super.find(criteria);

		if (!users.isEmpty())
			return users.get(0);
		else
			return null;
	}

	public HyqUserInfo getBySimCardNo(String simCardNo) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("simCardNo", simCardNo));

		List<HyqUserInfo> users = super.find(criteria);

		if (!users.isEmpty())
			return users.get(0);
		else
			return null;
	}

	public HyqUserInfo getByEmail(String email) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("email", email));

		List<HyqUserInfo> users = super.find(criteria);

		if (!users.isEmpty())
			return users.get(0);
		else
			return null;
	}

	public HyqUserInfo getByMobile(String mobile) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("mobile", DESHelper.DoDES(mobile, Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE)));

		List<HyqUserInfo> users = super.find(criteria);

		if (!users.isEmpty())
			return users.get(0);
		else
			return null;
	}

	public Page<HyqUserInfo> getUserPage(int pageNo, int pageSize, String keyWords, UserStatusCode status) {
		Page<HyqUserInfo> page = new Page<HyqUserInfo>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select a from HyqUserInfo a where a.loginName <> '' ";

		if (keyWords != null && !"".equals(keyWords))
			hql += " and (a.loginName like '%" + keyWords + "%' or a.trueName like '%" + keyWords
					+ "%' or a.nickName like '%" + keyWords + "%' or a.email = '" + keyWords + "' or mobile = '"
					+ DESHelper.DoDES(keyWords, Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE) + "')";

		if (status != null)
			hql += " and a.status = " + status.ordinal();

		hql += " order by a.createTime desc";

		super.findPage(page, hql, new Object[] {});

		return page;
	}

}
