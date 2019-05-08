package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydTypeAttrName;

@Repository
public class YydTypeAttrNameDao extends PageHibernateDao<YydTypeAttrName, Long> {

	public List<YydTypeAttrName> getTypeAttrNames(String typeCode) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.like("attrNameCode", typeCode,
				MatchMode.START));

		@SuppressWarnings("unchecked")
		List<YydTypeAttrName> attrNames = criteria.list();

		return attrNames;
	}

	public YydTypeAttrName getByCode(String code) {
		return super.findUniqueBy("attrNameCode", code);
	}

	public String getSpcCode(String prtCode) throws Exception {
		for (int i = 1; i < 100; i++) {
			String c = "";
			if (i < 10)
				c = "0" + i;
			else
				c = "" + i;

			if (this.getByCode(prtCode + "_" + c) == null)
				return prtCode + "_" + c;
		}
		throw new Exception("错误！该类别属性名超过了99个。");
	}
}
