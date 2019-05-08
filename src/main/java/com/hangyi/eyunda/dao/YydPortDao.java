package com.hangyi.eyunda.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydPort;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;

@Repository
public class YydPortDao extends PageHibernateDao<YydPort, Long> {

	public List<YydPort> findByKeyWords(String keyWords) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.like("fullPortName", keyWords, MatchMode.ANYWHERE));

		List<YydPort> ports = super.find(criteria);

		return ports;
	}

	public YydPort getByFullPortName(String fullPortName) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("fullPortName", fullPortName));

		List<YydPort> ports = super.find(criteria);

		if (!ports.isEmpty())
			return ports.get(0);
		else
			return null;
	}

	public YydPort getByCode(String portNo) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("portNo", portNo));

		List<YydPort> ports = super.find(criteria);

		if (!ports.isEmpty())
			return ports.get(0);
		else
			return null;
	}

	public YydPort findByPortName(String portCityCode, String portName) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.like("portNo", portCityCode, MatchMode.START));
		criteria.add(Restrictions.eq("portName", portName));

		@SuppressWarnings("unchecked")
		List<YydPort> cityPorts = criteria.list();

		if (!cityPorts.isEmpty())
			return cityPorts.get(0);
		else
			return null;
	}

	public YydPort findPortByCooord(Double longitude, Double latitude) {
		int step = 0;
		List<YydPort> list = new ArrayList<YydPort>();
		while (list.isEmpty() && step < 10) {
			step++;

			String hql = "select a from YydPort a, YydPortCooord b where a.portNo=b.portNo" + " and b.longitude>="
					+ (longitude - step * 0.01) + " and b.latitude>=" + (latitude - step * 0.01) + " and b.longitude<="
					+ (longitude + step * 0.01) + " and b.latitude<=" + (latitude + step * 0.01);
			list = super.find(hql, new Object[] {});
		}
		if (!list.isEmpty())
			return list.get(0);
		else
			return null;
	}

	// 带分页港口信息
	public Page<YydPort> getPortPage(int pageNo, int pageSize, ScfPortCityCode cityCode, String keyWords) {
		Page<YydPort> page = new Page<YydPort>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);

		Criteria criteria = getSession().createCriteria(entityClass);
		if (cityCode != null)
			criteria.add(Restrictions.like("portNo", cityCode.getCode(), MatchMode.START));

		if (keyWords != null && !"".equals(keyWords))
			criteria.add(Restrictions.like("fullPortName", keyWords, MatchMode.ANYWHERE));

		criteria.addOrder(Order.asc("engPortName"));

		return this.findPageByCriteria(page, criteria);
	}

	public List<YydPort> getAllPorts() {
		List<YydPort> allPorts = new ArrayList<YydPort>();

		ScfPortCityCode[] pcs = ScfPortCityCode.values();
		for (ScfPortCityCode pc : pcs) {
			List<YydPort> ports = this.getPortsByPortCityCode(pc.getCode());
			allPorts.addAll(ports);
		}

		return allPorts;
	}

	public String getSpcPortNo(String portCityCode) throws Exception {
		char[] hexadecimal = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				String portNo = portCityCode + hexadecimal[i] + hexadecimal[j];
				if (this.getByCode(portNo) == null)
					return portNo;
			}
		}
		throw new Exception("错误！未找到空闲的港口编码。");
	}

	public List<YydPort> getPortsByPortCityCode(String portCityCode) {

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.like("portNo", portCityCode, MatchMode.START));
		criteria.addOrder(Order.asc("engPortName"));

		@SuppressWarnings("unchecked")
		List<YydPort> cityPorts = criteria.list();

		return cityPorts;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page getPortsByPortCityCode(Page pageData, String portCityCode) {
		if (portCityCode != null && !"".equals(portCityCode)) {
			Criteria criteria = getSession().createCriteria(entityClass);
			criteria.add(Restrictions.like("portNo", portCityCode, MatchMode.START));
			criteria.addOrder(Order.asc("engPortName"));
			return findPageByCriteria(pageData, criteria);
		} else {
			super.findPage(pageData);
			return pageData;
		}
	}

}
