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

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.shipmove.MovShipArvlftDao;
import com.hangyi.eyunda.data.shipmove.MovShipArvlftData;
import com.hangyi.eyunda.data.shipmove.MovShipUpdownData;
import com.hangyi.eyunda.domain.shipmove.MovShipArvlft;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class MovShipArvlftService extends BaseService<MovShipArvlft, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MovShipArvlftDao shipArvlftDao;
	@Autowired
	private MovShipUpdownService shipUpdownService;

	@Override
	public PageHibernateDao<MovShipArvlft, Long> getDao() {
		return (PageHibernateDao<MovShipArvlft, Long>) shipArvlftDao;
	}

	public MovShipArvlftData getShipArvlftData(MovShipArvlft shipArvlft) {
		if (shipArvlft != null) {
			MovShipArvlftData shipArvlftData = new MovShipArvlftData();
			CopyUtil.copyProperties(shipArvlftData, shipArvlft, new String[] { "arvlftTime", "createTime" });
			shipArvlftData.setArvlftTime(CalendarUtil.toYYYY_MM_DD_HH_MM(shipArvlft.getArvlftTime()));
			shipArvlftData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(shipArvlft.getCreateTime()));

			List<MovShipUpdownData> shipUpdownDatas = shipUpdownService.getShipUpdownDatas(shipArvlft.getId());
			shipArvlftData.setShipUpdownDatas(shipUpdownDatas);

			return shipArvlftData;
		}
		return null;
	}

	public MovShipArvlftData getShipArvlftData(Long arvlftId) {
		MovShipArvlft shipArvlft = shipArvlftDao.get(arvlftId);
		if (shipArvlft == null)
			return null;
		else
			return this.getShipArvlftData(shipArvlft);
	}

	public MovShipArvlftData getPrevOne(String mmsi, Long arvlftId) {
		return this.getShipArvlftData(shipArvlftDao.getPrevOne(mmsi, arvlftId));
	}

	public Page<MovShipArvlftData> getPageDatas(Page<MovShipArvlftData> pageData, String mmsi, String startDate,
			String endDate) {
		List<MovShipArvlftData> datas = new ArrayList<MovShipArvlftData>();

		Page<MovShipArvlft> page = shipArvlftDao.getPage(pageData.getPageNo(), pageData.getPageSize(), mmsi, startDate,
				endDate);

		for (MovShipArvlft shipArvlft : page.getResult()) {
			MovShipArvlftData data = this.getShipArvlftData(shipArvlft);
			datas.add(data);
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(datas);

		return pageData;
	}

	public boolean saveShipArvlft(MovShipArvlftData shipArvlftData) {
		try {
			MovShipArvlft shipArvlft = shipArvlftDao.get(shipArvlftData.getId());

			if (shipArvlft == null) {
				shipArvlft = new MovShipArvlft();
			}

			CopyUtil.copyProperties(shipArvlft, shipArvlftData, new String[] { "arvlftTime", "createTime" });
			shipArvlft.setArvlftTime(CalendarUtil.parseYYYY_MM_DD_HH_MM(shipArvlftData.getArvlftTime()));

			shipArvlftDao.save(shipArvlft);

			List<MovShipUpdownData> oldDatas = shipUpdownService.getShipUpdownDatas(shipArvlft.getId());
			for (MovShipUpdownData oldData : oldDatas) {
				shipUpdownService.deleteShipUpdown(oldData.getId());
			}

			List<MovShipUpdownData> datas = shipArvlftData.getShipUpdownDatas();
			if (datas != null) {
				for (MovShipUpdownData data : datas) {
					data.setArvlftId(shipArvlft.getId());
					shipUpdownService.saveShipUpdown(data);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteShipArvlft(Long id) {
		try {
			MovShipArvlft shipArvlft = shipArvlftDao.get(id);

			if (shipArvlft != null) {
				List<MovShipUpdownData> oldDatas = shipUpdownService.getShipUpdownDatas(shipArvlft.getId());
				if (oldDatas != null) {
					for (MovShipUpdownData oldData : oldDatas) {
						shipUpdownService.deleteShipUpdown(oldData.getId());
					}
				}
				shipArvlftDao.delete(shipArvlft);
				return true;
			}
			throw new Exception("错误！指定的证书记录已经不存在。");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
