package com.hangyi.eyunda.service.portal.home;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.YydOrderEvaluateDao;
import com.hangyi.eyunda.data.order.EvaluateData;
import com.hangyi.eyunda.domain.YydOrderEvaluate;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class EvaluateService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydOrderEvaluateDao evaluateDao;
	@Autowired
	UserService userService;

	public List<EvaluateData> getEvaluateDatas(Long shipId, int preTwenty) {

		List<EvaluateData> evaluateDatas = new ArrayList<EvaluateData>();
		List<YydOrderEvaluate> evaluates = evaluateDao.getByShipId(shipId, preTwenty);

		for (YydOrderEvaluate evaluate: evaluates) {
			EvaluateData evaluateData = new EvaluateData();
			CopyUtil.copyProperties(evaluateData, evaluate);
			
			evaluateData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(evaluate.getCreateTime()));
			
			evaluateData.setUserData(userService.getById(evaluate.getUserId()));
			
			evaluateDatas.add(evaluateData);
			
		}

		return evaluateDatas;
	}

}
