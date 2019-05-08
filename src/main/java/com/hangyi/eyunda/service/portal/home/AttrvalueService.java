package com.hangyi.eyunda.service.portal.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydTypeAttrValueDao;
import com.hangyi.eyunda.data.ship.AttrValueData;
import com.hangyi.eyunda.domain.YydTypeAttrValue;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@SuppressWarnings("rawtypes")
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class AttrvalueService extends BaseService{
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydTypeAttrValueDao typeAttrValueDao;
	
	@Override
	public PageHibernateDao<YydTypeAttrValue, Long> getDao() {
		return (PageHibernateDao<YydTypeAttrValue, Long>) typeAttrValueDao;
	}
	
	public AttrValueData getAttrValueData(String attrValueCode){
		YydTypeAttrValue typeAttrValue=typeAttrValueDao.getByCode(attrValueCode);
		AttrValueData attrValueData=null;
		if(typeAttrValue!=null){
			attrValueData = new AttrValueData();
			CopyUtil.copyProperties(attrValueData, typeAttrValue);
		}
		
		return attrValueData;
	}
	
	
}
