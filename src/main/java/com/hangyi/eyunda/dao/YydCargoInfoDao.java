package com.hangyi.eyunda.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydCargoInfo;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.StringUtil;

@Repository
public class YydCargoInfoDao extends PageHibernateDao<YydCargoInfo, Long> {

	public Page<YydCargoInfo> findCargoPage(int pageSize, int pageNo, String keyWords, String startDate,
			String endDate) {
		Page<YydCargoInfo> page = new Page<YydCargoInfo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);

		String hql = "select y from YydCargoInfo y where y.status = " + ReleaseStatusCode.publish.ordinal();

		if (keyWords != null && !"".equals(keyWords)) {
			// 判断是否为货号
			if (StringUtil.isNumeric(keyWords)) {
				hql += " and (y.cargoNames like '%" + keyWords + "%'";
				hql += " or y.id = " + keyWords + ")";
			} else {
				hql += " and y.cargoNames like '%" + keyWords + "%'";
			}
		}

		if (startDate != null && !"".equals(startDate)) {
			hql += " and y.createTime > '" + startDate + "'";
		}

		if (endDate != null && !"".equals(endDate)) {
			String sEnd = CalendarUtil
					.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.getTheDayMidnight(CalendarUtil.parseYY_MM_DD(endDate)));
			hql += " and y.createTime<='" + sEnd + "'";
		}

		hql += " order by y.createTime desc";

		super.findPage(page, hql);

		return page;
	}

	// 货主的货物列表（私有）
	public Page<YydCargoInfo> findByPublisherId(Long publisherId, int pageNo, int pageSize, String keyWords,
			String startDate, String endDate) {
		Page<YydCargoInfo> page = new Page<YydCargoInfo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);

		String hql = "select y from YydCargoInfo y where y.publisherId = " + publisherId;

		if (keyWords != null && !"".equals(keyWords)) {
			// 判断是否为货号
			if (StringUtil.isNumeric(keyWords)) {
				hql += " and (y.cargoNames like '%" + keyWords + "%'";
				hql += " or y.id = " + keyWords + ")";
			} else {
				hql += " and y.cargoNames like '%" + keyWords + "%'";
			}
		}

		if (startDate != null && !"".equals(startDate)) {
			hql += " and y.createTime > '" + startDate + "'";
		}

		if (endDate != null && !"".equals(endDate)) {
			String sEnd = CalendarUtil
					.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.getTheDayMidnight(CalendarUtil.parseYY_MM_DD(endDate)));
			hql += " and y.createTime<='" + sEnd + "'";
		}

		hql += " order by y.createTime desc";

		super.findPage(page, hql);

		return page;
	}

	// 查询代理人公司发布的货盘
	public List<YydCargoInfo> getCargosByCompId(Long compId) {
		Page<YydCargoInfo> page = new Page<YydCargoInfo>();
		page.setPageSize(Constants.ALL_SIZE);
		page.setPageNo(1);

		String hql = "select distinct d from YydCompany a, YydDepartment b, YydUserDept c, YydCargoInfo d where a.id = "
				+ compId + " and a.id = b.compId and b.id = c.deptId and c.userId = d.publisherId and d.status = "
				+ ReleaseStatusCode.publish.ordinal() + " order by d.createTime desc";

		super.findPage(page, hql, new Object[] {});

		return page.getResult();
	}

}
