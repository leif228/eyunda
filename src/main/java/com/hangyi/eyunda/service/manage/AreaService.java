package com.hangyi.eyunda.service.manage;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydAreaDao;
import com.hangyi.eyunda.data.ship.AreaData;
import com.hangyi.eyunda.domain.YydArea;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class AreaService extends BaseService<YydArea, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydAreaDao areaDao;

	@Override
	public PageHibernateDao<YydArea, Long> getDao() {
		return (PageHibernateDao<YydArea, Long>) areaDao;
	}

	public List<AreaData> getAreaDatas() {
		List<AreaData> areaDatas = new ArrayList<AreaData>();
		try {
			List<YydArea> areas = areaDao.getAll();
			for (YydArea area : areas) {
				AreaData areaData = new AreaData();
				CopyUtil.copyProperties(areaData, area);
				areaDatas.add(areaData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return areaDatas;
	}

}
