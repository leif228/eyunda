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

import com.hangyi.eyunda.domain.YydRecordShipCall;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydRecordShipCallDao extends PageHibernateDao<YydRecordShipCall, Long> {

	public List<Map<String, Object>> getStatShipCallList(Calendar month) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.ge("recordTime", month));
		criteria.add(Restrictions.lt("recordTime", CalendarUtil.addMonths(month, 1)));

		ProjectionList prolist = Projections.projectionList();
		prolist.add(Projections.alias(Projections.groupProperty("roleCode"), "roleCode")); // 按角色分组
		prolist.add(Projections.alias(Projections.rowCount(), "callNum")); // 访问次数
		prolist.add(Projections.alias(Projections.countDistinct("userId"), "calledUserNum"));// 访问人数
		prolist.add(Projections.alias(Projections.countDistinct("shipId"), "calledShipNum"));// 访问船舶数

		criteria.setProjection(prolist);

		@SuppressWarnings("unchecked")
		List<Object> statsShip = criteria.list();

		// 将结果集中的数据取出来，放进 List 集合中
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		if (!statsShip.isEmpty()) {
			for (Object obj : statsShip) {
				Object[] arr = (Object[]) obj;
				if (arr != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("roleCode", (UserPrivilegeCode) arr[0]);
					map.put("callNum", Integer.parseInt(arr[1].toString()));
					map.put("calledUserNum", Integer.parseInt(arr[2].toString()));
					map.put("calledShipNum", Integer.parseInt(arr[3].toString()));
					result.add(map);
				}
			}
		}
		return result;
	}

	@SuppressWarnings({ "static-access" })
	public YydRecordShipCall findFirstShipCall() {
		Page<YydRecordShipCall> page = new Page<YydRecordShipCall>();
		page.setPageSize(1);
		page.setPageNo(1);
		page.setOrder(page.ASC);
		page.setOrderBy("id");

		super.findPage(page);
		List<YydRecordShipCall> yydShipCallInfos = page.getResult();

		if (!yydShipCallInfos.isEmpty())
			return yydShipCallInfos.get(0);
		else
			return null;
	}
}
