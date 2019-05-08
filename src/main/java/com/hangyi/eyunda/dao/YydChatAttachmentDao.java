package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydChatAttachment;

@Repository
public class YydChatAttachmentDao extends PageHibernateDao<YydChatAttachment, Long> {
	@SuppressWarnings("unchecked")
	public List<YydChatAttachment> getById(Long messageId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("messageId", messageId));

		return criteria.list();
	}
}
