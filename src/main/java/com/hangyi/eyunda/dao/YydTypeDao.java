package com.hangyi.eyunda.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydType;

@Repository
public class YydTypeDao extends PageHibernateDao<YydType, Long> {

	@SuppressWarnings("static-access")
	public Page<YydType> findPage() {
		Page<YydType> page = new Page<YydType>();
		page.setPageSize(super.getTotalCount(super.createCriteria()));
		page.setPageNo(1);
		page.setOrderBy("id");
		page.setOrder(page.ASC);

		return super.getAll(page);
	}

	@SuppressWarnings("unchecked")
	public List<YydType> getChildren(String code) {
		List<YydType> ret = new ArrayList<YydType>();
		if (code == null || "".equals(code))
			return ret;
		if (!"00".equals(code.substring(2, 4)))
			return ret;

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.like("typeCode", code.substring(0, 2), MatchMode.START));
		criteria.add(Restrictions.ne("typeCode", code));

		List<YydType> children = criteria.list();

		return children;
	}

	@SuppressWarnings("unchecked")
	public List<YydType> getUncles() {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.like("typeCode", "00", MatchMode.END));

		List<YydType> ret = criteria.list();

		return ret;
	}

	public YydType getByCode(String code) {
		return super.findUniqueBy("typeCode", code);
	}

	public YydType getByName(String name) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("typeName", name));

		@SuppressWarnings("unchecked")
		List<YydType> children = criteria.list();

		if (!children.isEmpty())
			return children.get(0);
		else
			return null;
	}

	public String getSpcUncleCode() throws Exception {
		for (int i = 1; i < 100; i++) {
			String c = "";
			if (i < 10)
				c = "0" + i + "00";
			else
				c = i + "00";

			if (this.getByCode(c) == null)
				return c;
		}
		throw new Exception("错误！同级类别数超过了99个。");
	}

	public String getSpcChildCode(String prtCode) throws Exception {
		if (prtCode == null || "".equals(prtCode))
			return this.getSpcUncleCode();

		for (int i = 1; i < 100; i++) {
			String c = prtCode.substring(0, 2);
			if (i < 10)
				c += "0" + i;
			else
				c += i;

			if (this.getByCode(c) == null)
				return c;
		}
		throw new Exception("错误！同级类别数超过了99个。");
	}

}
