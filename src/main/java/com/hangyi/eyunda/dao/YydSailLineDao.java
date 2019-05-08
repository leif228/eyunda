package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydSailLine;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;

@Repository
public class YydSailLineDao extends PageHibernateDao<YydSailLine, Long> {

	public YydSailLine getSailLine(WaresBigTypeCode waresBigType, WaresTypeCode waresType, CargoTypeCode cargoType,
			String startPortNo, String endPortNo) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("waresBigType", waresBigType));
		criteria.add(Restrictions.eq("waresType", waresType));
		criteria.add(Restrictions.eq("cargoType", cargoType));

		criteria.add(Restrictions.eq("startPortNo", startPortNo));
		criteria.add(Restrictions.eq("endPortNo", endPortNo));

		List<YydSailLine> yydSailLines = super.find(criteria);

		if (yydSailLines.isEmpty())
			return null;
		else
			return yydSailLines.get(0);
	}

	public List<YydSailLine> getSailLinesByType(WaresBigTypeCode waresBigType, WaresTypeCode waresType,
			CargoTypeCode cargoType) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("waresBigType", waresBigType));
		criteria.add(Restrictions.eq("waresType", waresType));
		criteria.add(Restrictions.eq("cargoType", cargoType));

		criteria.addOrder(Order.asc("startPortNo"));
		criteria.addOrder(Order.asc("endPortNo"));

		return super.find(criteria);
	}

	public Page<YydSailLine> getPageSailLinesByType(int pageNo, int pageSize, WaresBigTypeCode waresBigType,
			WaresTypeCode waresType, CargoTypeCode cargoType, List<String> lstPortNo) {
		Page<YydSailLine> page = new Page<YydSailLine>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("waresBigType", waresBigType));
		criteria.add(Restrictions.eq("waresType", waresType));
		criteria.add(Restrictions.eq("cargoType", cargoType));

		if (!lstPortNo.isEmpty())
			criteria.add(Restrictions.or(Restrictions.in("startPortNo", lstPortNo),
					Restrictions.in("endPortNo", lstPortNo)));

		criteria.addOrder(Order.asc("startPortNo"));
		criteria.addOrder(Order.asc("endPortNo"));

		super.findPageByCriteria(page, criteria);

		return page;
	}

}
