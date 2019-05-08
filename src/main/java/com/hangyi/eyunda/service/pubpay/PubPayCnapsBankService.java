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
import com.hangyi.eyunda.dao.PubPayCnapsBankDao;
import com.hangyi.eyunda.domain.PubPayCity;
import com.hangyi.eyunda.domain.PubPayCnapsBank;
import com.hangyi.eyunda.domain.enumeric.BankCode;
import com.hangyi.eyunda.service.BaseService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class PubPayCnapsBankService extends BaseService<PubPayCnapsBank, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PubPayCnapsBankDao pubPayCnapsBankDao;
	@Autowired
	PubPayCityDao pubPayCityDao;

	@Override
	public PageHibernateDao<PubPayCnapsBank, Long> getDao() {
		return (PageHibernateDao<PubPayCnapsBank, Long>) pubPayCnapsBankDao;
	}

	public List<PubPayCnapsBank> searchOpenBank(String keyword, String bank, String acode) {
		BankCode bc = BankCode.valueOf(bank);
		String bankCodeNo = bc.getCode().substring(0, 3);
		
		return pubPayCnapsBankDao.searchOpenBank(keyword,bankCodeNo,acode);
		
	}

}
