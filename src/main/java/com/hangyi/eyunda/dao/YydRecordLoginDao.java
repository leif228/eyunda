package com.hangyi.eyunda.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydRecordLogin;
import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydRecordLoginDao extends PageHibernateDao<YydRecordLogin, Long> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page findUserLoginLogs(Page page, String userInfo, String startDate, String endDate) {
		List<Map<String, Object>> mlist = new ArrayList<Map<String, Object>>();

		String sql = "select b.id id,a.user_logo userLogo,a.login_name loginName,a.true_name trueName,a.nick_name nickName,a.email email,b.ip_address ipAddress,b.login_time loginTime,b.logout_time logoutTime,b.time_span timeSpan from yyd_user_info a, yyd_record_login b where a.id=b.user_id";
		if (userInfo != null && !"".equals(userInfo)) {
			sql += " and (a.login_name like '%" + userInfo + "%' or a.true_name like '%" + userInfo
					+ "%' or a.nick_name like '%" + userInfo + "%' or a.email like '%" + userInfo + "%')";
		}
		if (startDate != null && !"".equals(startDate)) {
			sql += " and b.login_time>='" + startDate + "'";
		}
		if (endDate != null && !"".equals(endDate)) {
			String sEnd = CalendarUtil.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.getTheDayMidnight(CalendarUtil
					.parseYY_MM_DD(endDate)));
			sql += " and b.login_time<='" + sEnd + "'";
		}

		super.findPageBySQL(page, sql);
		
		for (Object o : page.getResult()) {
			Object[] os = (Object[]) o;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", os[0]);
			map.put("userLogo", os[1]);
			map.put("loginName", os[2]);
			map.put("trueName", os[3]);
			map.put("nickName", os[4]);
			map.put("email", os[5]);
			map.put("ipAddress", os[6]);
			map.put("loginTime", os[7]);
			map.put("logoutTime", os[8]);
			map.put("timeSpan", os[9]);

			mlist.add(map);
		}

		page.setResult(mlist);

		return page;
	}

	public YydRecordLogin getLastByUserId(Long userId, LoginSourceCode loginSource, String sessionId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("loginSource", loginSource));
		criteria.add(Restrictions.eq("sessionId", sessionId));

		Page<YydRecordLogin> page = new Page<YydRecordLogin>();
		page.setPageSize(5);
		page.setPageNo(1);
		page.setOrder(Page.DESC);
		page.setOrderBy("loginTime");

		super.findPageByCriteria(page, criteria);
		List<YydRecordLogin> yydRecordLogin = page.getResult();

		if (!yydRecordLogin.isEmpty())
			return yydRecordLogin.get(0);
		else
			return null;
	}

	public List<Map<String, Object>> getStatLoginList(Calendar month) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.ge("loginTime", month));
		criteria.add(Restrictions.lt("loginTime", CalendarUtil.addMonths(month, 1)));

		ProjectionList prolist = Projections.projectionList();

		prolist.add(Projections.alias(Projections.groupProperty("roleCode"), "roleCode")); // 按角色分组
		prolist.add(Projections.alias(Projections.rowCount(), "loginNum")); // 登录次数
		prolist.add(Projections.alias(Projections.countDistinct("userId"), "loginUserNum"));// 登录人数
		prolist.add(Projections.alias(Projections.sum("timeSpan"), "timeSpan"));// 登录时长

		criteria.setProjection(prolist);

		@SuppressWarnings("unchecked")
		List<Object> statsLogin = criteria.list();

		// 将结果集中的数据取出来，放进 List 集合中
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		if (!statsLogin.isEmpty()) {
			for (Object obj : statsLogin) {
				Object[] arr = (Object[]) obj;
				if (arr != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("roleCode", (UserPrivilegeCode) arr[0]);
					map.put("loginNum", Integer.parseInt(arr[1].toString()));
					map.put("loginUserNum", Integer.parseInt(arr[2].toString()));
					map.put("timeSpan", Integer.parseInt(arr[3].toString()));
					result.add(map);
				}
			}
		}
		return result;
	}

	public YydRecordLogin findFirstLogin() {
		Page<YydRecordLogin> page = new Page<YydRecordLogin>();
		page.setPageSize(1);
		page.setPageNo(1);
		page.setOrder(Page.ASC);
		page.setOrderBy("id");

		super.findPage(page);
		List<YydRecordLogin> yydRecordLogin = page.getResult();

		if (!yydRecordLogin.isEmpty())
			return yydRecordLogin.get(0);
		else
			return null;
	}
}
