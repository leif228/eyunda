package com.hangyi.eyunda.dao.shipmove;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.domain.shipmove.MovShipArvlft;
import com.hangyi.eyunda.util.Constants;

@Repository
public class MovShipArvlftDao extends PageHibernateDao<MovShipArvlft, Long> {

	public Page<MovShipArvlft> getPage(int pageNo, int pageSize, String mmsi, String startDate, String endDate) {
		Page<MovShipArvlft> page = new Page<MovShipArvlft>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select a from MovShipArvlft a where a.mmsi = '" + mmsi + "'";

		if (startDate != null && !"".equals(startDate)) {
			hql += " and a.arvlftTime > '" + startDate + "'";
		}

		if (endDate != null && !"".equals(endDate)) {
			hql += " and a.arvlftTime < '" + endDate + "'";
		}

		hql += " order by a.arvlftTime desc";

		super.findPage(page, hql, new Object[] {});

		return page;
	}

	public MovShipArvlft getPrevOne(String mmsi, Long arvlftId) {
		Page<MovShipArvlft> page = new Page<MovShipArvlft>();
		page.setPageSize(1);
		page.setPageNo(1);

		String hql = "select a from MovShipArvlft a where a.mmsi = '" + mmsi + "'";
		if (arvlftId > 0)
			hql += " and a.id < " + arvlftId;

		hql += " order by a.arvlftTime desc";

		super.findPage(page, hql, new Object[] {});

		if (!page.getResult().isEmpty() && page.getResult().size() > 0)
			return page.getResult().get(0);
		else
			return null;
	}

}
