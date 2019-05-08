package com.hangyi.eyunda.service.pubpay;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.PubPayNodeDao;
import com.hangyi.eyunda.domain.PubPayNode;
import com.hangyi.eyunda.service.BaseService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class PubPayNodeService extends BaseService<PubPayNode, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PubPayNodeDao pubPayNodeDao;

	@Override
	public PageHibernateDao<PubPayNode, Long> getDao() {
		return (PageHibernateDao<PubPayNode, Long>) pubPayNodeDao;
	}
	
	public List<PubPayNode> getAllPubPayNode(){
		return pubPayNodeDao.getAll();
	}

}
