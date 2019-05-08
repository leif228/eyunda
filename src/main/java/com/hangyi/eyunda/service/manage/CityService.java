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

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydCityDao;
import com.hangyi.eyunda.data.ship.CityData;
import com.hangyi.eyunda.domain.YydCity;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class CityService extends BaseService<YydCity, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydCityDao cityDao;

	@Override
	public PageHibernateDao<YydCity, Long> getDao() {
		return (PageHibernateDao<YydCity, Long>) cityDao;
	}

	public List<CityData> getCityDatas() {
		List<CityData> cityDatas = new ArrayList<CityData>();
		try {
			Page<YydCity> page = cityDao.findPage();
			if (page != null && !page.getResult().isEmpty()) {
				List<YydCity> citys = (List<YydCity>) page.getResult();
				for (YydCity city : citys) {
					CityData cityData = new CityData();
					CopyUtil.copyProperties(cityData, city);
					cityDatas.add(cityData);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return cityDatas;
	}

}
