package com.hangyi.eyunda.service.ship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydShipHxdlDao;
import com.hangyi.eyunda.domain.YydShipHxdl;
import com.hangyi.eyunda.service.BaseService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ShipHxdlService extends BaseService<YydShipHxdl, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydShipHxdlDao shipHxdlDao;

	@Override
	public PageHibernateDao<YydShipHxdl, Long> getDao() {
		return (PageHibernateDao<YydShipHxdl, Long>) shipHxdlDao;
	}

}
