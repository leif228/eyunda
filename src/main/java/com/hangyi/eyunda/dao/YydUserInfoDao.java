package com.hangyi.eyunda.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydUserInfo;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.domain.enumeric.UserStatusCode;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.DESHelper;

@Repository
public class YydUserInfoDao extends PageHibernateDao<YydUserInfo, Long> {

	public UserPrivilegeCode getUserRole(Long userId) {
		String hql = "select b.deptType from YydCompany a, YydDepartment b, YydUserDept c, YydUserInfo d"
				+ " where a.id = b.compId and b.id = c.deptId and c.userId = d.id" + " and d.id = " + userId
				+ " order by b.deptType asc";

		List<Object> os = super.find(hql);

		UserPrivilegeCode upc = UserPrivilegeCode.owner;
		if (os != null && !os.isEmpty())
			upc = (UserPrivilegeCode) os.get(0);

		return upc;
	}

	public Page<YydUserInfo> getPageData(Long compId, Long deptId, String keyWords, int pageNo, int pageSize) {
		Page<YydUserInfo> page = new Page<YydUserInfo>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select distinct d from YydCompany a, YydDepartment b, YydUserDept c, YydUserInfo d where c.compId = "
				+ compId + " and a.id = b.compId and b.id = c.deptId and c.userId = d.id";

		if (deptId != null && deptId > 0)
			hql += " and c.deptId = " + deptId;
		// 非管理员或业务员，不让其见到船员、船东与货主
		if (deptId != null && deptId < 0) {
			hql += " and b.deptType != " + UserPrivilegeCode.sailor.ordinal();
			hql += " and b.deptType != " + UserPrivilegeCode.master.ordinal();
			hql += " and b.deptType != " + UserPrivilegeCode.owner.ordinal();
		}

		if (keyWords != null && !"".equals(keyWords))
			hql += " and (d.loginName = '" + keyWords + "' or d.email = '" + keyWords + "' or d.mobile = '" + keyWords
					+ "' or d.trueName like '%" + keyWords + "%' or d.nickName like '%" + keyWords + "%')";

		hql += " order by c.createTime desc";

		super.findPage(page, hql, new Object[] {});

		return page;
	}

	public YydUserInfo getByLoginName(String loginName, Long... excludeIds) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.or(Restrictions.eq("loginName", loginName),
				Restrictions.eq("mobile", DESHelper.DoDES(loginName, Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE)),
				Restrictions.eq("email", loginName)));

		if (excludeIds.length > 0) {
			for (Long uid : excludeIds) {
				criteria.add(Restrictions.ne("id", uid));
			}
		}

		@SuppressWarnings("unchecked")
		List<YydUserInfo> userInfos = criteria.list();

		if (!userInfos.isEmpty())
			return userInfos.get(0);
		else
			return null;
	}

	public List<YydUserInfo> getBySimCardNo(String simCardNo) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("simCardNo", simCardNo));

		@SuppressWarnings("unchecked")
		List<YydUserInfo> userInfos = criteria.list();

		return userInfos;
	}

	public List<YydUserInfo> frontFindUsers(String userInfo) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.or(Restrictions.eq("loginName", userInfo), Restrictions.eq("email", userInfo),
				Restrictions.eq("mobile", DESHelper.DoDES(userInfo, Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE)),
				Restrictions.like("nickName", userInfo, MatchMode.ANYWHERE),
				Restrictions.like("trueName", userInfo, MatchMode.ANYWHERE)));

		Page<YydUserInfo> page = new Page<YydUserInfo>();
		page.setPageSize(6);
		page.setPageNo(1);
		page.setOrder(Page.DESC);
		page.setOrderBy("id");

		super.findPageByCriteria(page, criteria);

		return page.getResult();
	}

	public int getStatSumUser(Calendar month) {

		Criteria criteria = this.getSession().createCriteria(entityClass);

		Calendar nextMonth = CalendarUtil.addMonths(month, 1);
		criteria.add(Restrictions.lt("createTime", nextMonth));

		ProjectionList prolist = Projections.projectionList();
		prolist.add(Projections.alias(Projections.rowCount(), "sumUsers")); // 总用户数
		criteria.setProjection(prolist);

		Object statUser = (Object) criteria.uniqueResult();

		// 将结果集中的数据取出来，放进 Map集合中
		int sumUser = 0;
		if (statUser != null) {
			sumUser = Integer.parseInt(statUser.toString());
		}
		return sumUser;
	}

	public int statSumUsersOnPrivil(Calendar month, UserPrivilegeCode privilege) {
		Calendar nextMonth = CalendarUtil.addMonths(month, 1);

		// 统计总用户数
		String hql = "select count(d) sumUsers from YydCompany a, YydDepartment b, YydUserDept c, YydUserInfo d"
				+ " where a.id = b.compId and b.id = c.deptId and c.userId = d.id" + " and d.createTime < '"
				+ CalendarUtil.toYYYYMM(nextMonth) + "' and b.deptType = " + privilege.ordinal();

		Object statUser = (Object) super.findUnique(hql);

		// 将结果集中的数据取出来，放进 Map集合中
		int sumUsers = 0;
		if (statUser != null) {
			sumUsers = Integer.parseInt(statUser.toString());
		}
		return sumUsers;
	}

	public YydUserInfo findFirstUser() {
		Page<YydUserInfo> page = new Page<YydUserInfo>();
		page.setPageSize(1);
		page.setPageNo(1);
		page.setOrder(Page.DESC);
		page.setOrderBy("createTime");

		super.findPage(page);
		List<YydUserInfo> yydUserInfos = page.getResult();

		if (!yydUserInfos.isEmpty())
			return yydUserInfos.get(0);
		else
			return null;
	}

	public Page<YydUserInfo> backFindUserDatas(String userInfo, UserStatusCode status, int pageNo, int pageSize) {
		Page<YydUserInfo> page = new Page<YydUserInfo>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		page.setOrder(Page.DESC);
		page.setOrderBy("createTime");

		Criteria criteria = getSession().createCriteria(entityClass);

		if (userInfo != null && !"".equals(userInfo))
			criteria.add(Restrictions.or(Restrictions.eq("loginName", userInfo), Restrictions.eq("email", userInfo),
					Restrictions.eq("mobile", DESHelper.DoDES(userInfo, Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE)),
					Restrictions.like("nickName", userInfo, MatchMode.ANYWHERE),
					Restrictions.like("trueName", userInfo, MatchMode.ANYWHERE)));

		if (status != null)
			criteria.add(Restrictions.eq("status", status));

		return findPageByCriteria(page, criteria);
	}

}
