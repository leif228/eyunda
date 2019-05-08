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
import com.hangyi.eyunda.dao.YydProvinceDao;
import com.hangyi.eyunda.data.ship.ProvinceData;
import com.hangyi.eyunda.domain.YydProvince;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ProvinceService extends BaseService<YydProvince, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydProvinceDao provinceDao;

	@Override
	public PageHibernateDao<YydProvince, Long> getDao() {
		return (PageHibernateDao<YydProvince, Long>) provinceDao;
	}

	public YydProvince getByCode(String code) {
		return provinceDao.getByCode(code);
	}

	public List<ProvinceData> getProvinceDatas() {
		List<ProvinceData> provinceDatas = new ArrayList<ProvinceData>();
		try {
			Page<YydProvince> page = provinceDao.findPage();
			if (page != null && !page.getResult().isEmpty()) {
				List<YydProvince> types = (List<YydProvince>) page.getResult();
				for (YydProvince type : types) {
					ProvinceData provinceData = new ProvinceData();
					CopyUtil.copyProperties(provinceData, type);
					provinceDatas.add(provinceData);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return provinceDatas;
	}

}
