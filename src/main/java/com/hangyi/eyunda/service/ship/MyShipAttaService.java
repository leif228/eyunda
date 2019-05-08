package com.hangyi.eyunda.service.ship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydShipAttaDao;
import com.hangyi.eyunda.domain.YydShipAtta;
import com.hangyi.eyunda.service.BaseService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class MyShipAttaService extends BaseService<YydShipAtta, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydShipAttaDao shipAttaDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageHibernateDao<YydShipAtta, Long> getDao() {
		return (PageHibernateDao) shipAttaDao;
	}

	public Long getShipId(Long attaId) throws Exception {
		YydShipAtta atta = shipAttaDao.get(attaId);
		if (atta != null) {
			return atta.getShipId();
		} else {
			throw new Exception("错误！船舶附件已经不存在。");
		}
	}
}
