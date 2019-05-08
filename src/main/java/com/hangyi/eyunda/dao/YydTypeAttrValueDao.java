package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydTypeAttrValue;

@Repository
public class YydTypeAttrValueDao extends
		PageHibernateDao<YydTypeAttrValue, Long> {

	public List<YydTypeAttrValue> getAttrValuesBy(String attrNameCode) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.like("attrValueCode", attrNameCode,
				MatchMode.START));

		@SuppressWarnings("unchecked")
		List<YydTypeAttrValue> attrValues = criteria.list();

		return attrValues;
	}

	public YydTypeAttrValue getByCode(String code) {
		return super.findUniqueBy("attrValueCode", code);
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
		throw new Exception("错误！该属性名下属性值超过了99个。");
	}

}
