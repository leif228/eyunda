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
import com.hangyi.eyunda.dao.PubPayCityDao;
import com.hangyi.eyunda.domain.PubPayCity;
import com.hangyi.eyunda.service.BaseService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class PubPayCityService extends BaseService<PubPayCity, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PubPayCityDao pubPayCityDao;

	@Override
	public PageHibernateDao<PubPayCity, Long> getDao() {
		return (PageHibernateDao<PubPayCity, Long>) pubPayCityDao;
	}
	
	public List<PubPayCity> getPPCByPPN(String ppn){
		return pubPayCityDao.getPPCByPPN(ppn);
	}

}
