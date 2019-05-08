package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydUpDownPort;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;

@Repository
public class YydUpDownPortDao extends PageHibernateDao<YydUpDownPort, Long> {

	public YydUpDownPort getUpDownPort(WaresBigTypeCode waresBigType, WaresTypeCode waresType, CargoTypeCode cargoType,
			String startPortNo) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("waresBigType", waresBigType));
		criteria.add(Restrictions.eq("waresType", waresType));
		criteria.add(Restrictions.eq("cargoType", cargoType));

		criteria.add(Restrictions.eq("startPortNo", startPortNo));

		List<YydUpDownPort> upDownPorts = super.find(criteria);

		if (upDownPorts.isEmpty())
			return null;
		else
			return upDownPorts.get(0);
	}

	public List<YydUpDownPort> getUpDownPortsByType(WaresBigTypeCode waresBigType, WaresTypeCode waresType,
			CargoTypeCode cargoType) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("waresBigType", waresBigType));
		criteria.add(Restrictions.eq("waresType", waresType));
		criteria.add(Restrictions.eq("cargoType", cargoType));

		criteria.addOrder(Order.asc("startPortNo"));

		return super.find(criteria);
	}

	public Page<YydUpDownPort> getPageUpDownPortsByType(int pageNo, int pageSize, WaresBigTypeCode waresBigType,
			WaresTypeCode waresType, CargoTypeCode cargoType, List<String> lstPortNo) {
		Page<YydUpDownPort> page = new Page<YydUpDownPort>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("waresBigType", waresBigType));
		criteria.add(Restrictions.eq("waresType", waresType));
		criteria.add(Restrictions.eq("cargoType", cargoType));

		if (!lstPortNo.isEmpty())
			criteria.add(Restrictions.in("startPortNo", lstPortNo));

		criteria.addOrder(Order.asc("startPortNo"));

		super.findPageByCriteria(page, criteria);

		return page;
	}

}
