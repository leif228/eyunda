package com.hangyi.eyunda.service.ship;

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
import com.hangyi.eyunda.dao.YydPortCooordDao;
import com.hangyi.eyunda.data.ship.PortCooordData;
import com.hangyi.eyunda.domain.YydPortCooord;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class PortCooordService extends BaseService<YydPortCooord, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydPortCooordDao portCooordDao;

	@Override
	public PageHibernateDao<YydPortCooord, Long> getDao() {
		return (PageHibernateDao<YydPortCooord, Long>) portCooordDao;
	}

	public List<PortCooordData> getPortCooordDatas(String portNo) {
		List<PortCooordData> portCooordDatas = new ArrayList<PortCooordData>();
		try {
			List<YydPortCooord> portCooords = portCooordDao.getAllPortCooords(portNo);

			for (YydPortCooord portCooord : portCooords) {
				PortCooordData portCooordData = this.getPortCooordData(portCooord);
				portCooordDatas.add(portCooordData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return portCooordDatas;
	}

	private PortCooordData getPortCooordData(YydPortCooord portCooord) {
		try {
			PortCooordData portCooordData = null;
			if (portCooord != null) {
				portCooordData = new PortCooordData();
				CopyUtil.copyProperties(portCooordData, portCooord);
			}
			return portCooordData;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
