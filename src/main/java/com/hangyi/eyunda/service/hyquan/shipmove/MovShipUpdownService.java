package com.hangyi.eyunda.service.hyquan.shipmove;

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
import com.hangyi.eyunda.dao.shipmove.MovShipUpdownDao;
import com.hangyi.eyunda.data.shipmove.MovShipUpdownData;
import com.hangyi.eyunda.domain.shipmove.MovShipUpdown;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class MovShipUpdownService extends BaseService<MovShipUpdown, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MovShipUpdownDao shipUpdownDao;

	@Override
	public PageHibernateDao<MovShipUpdown, Long> getDao() {
		return (PageHibernateDao<MovShipUpdown, Long>) shipUpdownDao;
	}

	public MovShipUpdownData getShipUpdownData(MovShipUpdown shipUpdown) {
		if (shipUpdown != null) {
			MovShipUpdownData shipUpdownData = new MovShipUpdownData();
			CopyUtil.copyProperties(shipUpdownData, shipUpdown, new String[] { "createTime" });
			shipUpdownData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(shipUpdown.getCreateTime()));

			return shipUpdownData;
		}
		return null;
	}

	public MovShipUpdownData getShipUpdownData(Long updownId) {
		MovShipUpdown shipUpdown = shipUpdownDao.get(updownId);
		if (shipUpdown == null)
			return null;
		else
			return this.getShipUpdownData(shipUpdown);
	}

	public List<MovShipUpdownData> getShipUpdownDatas(Long arvlftId) {
		List<MovShipUpdownData> datas = new ArrayList<MovShipUpdownData>();
		List<MovShipUpdown> shipUpdowns = shipUpdownDao.findByArvlftId(arvlftId);
		for (MovShipUpdown shipUpdown : shipUpdowns) {
			MovShipUpdownData data = this.getShipUpdownData(shipUpdown);
			datas.add(data);
		}
		return datas;
	}

	public boolean saveShipUpdown(MovShipUpdownData shipUpdownData) {
		try {
			MovShipUpdown shipUpdown = shipUpdownDao.get(shipUpdownData.getId());

			if (shipUpdown == null) {
				shipUpdown = new MovShipUpdown();
			}

			CopyUtil.copyProperties(shipUpdown, shipUpdownData, new String[] { "createTime" });

			shipUpdownDao.save(shipUpdown);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteShipUpdown(Long id) {
		try {
			MovShipUpdown shipUpdown = shipUpdownDao.get(id);

			if (shipUpdown != null) {
				shipUpdownDao.delete(shipUpdown);
				return true;
			}
			throw new Exception("错误！指定的装卸记录已经不存在。");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
