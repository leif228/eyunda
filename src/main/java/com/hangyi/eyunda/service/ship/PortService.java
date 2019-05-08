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

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydCargoInfoDao;
import com.hangyi.eyunda.dao.YydOrderCommonDao;
import com.hangyi.eyunda.dao.YydPortCooordDao;
import com.hangyi.eyunda.dao.YydPortDao;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.domain.YydPort;
import com.hangyi.eyunda.domain.YydPortCooord;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.Cn2Spell;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class PortService extends BaseService<YydPort, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PortCooordService portCooordService;
	@Autowired
	YydPortDao portDao;
	@Autowired
	YydPortCooordDao portCooordDao;
	@Autowired
	YydCargoInfoDao cargoDao;
	@Autowired
	YydOrderCommonDao orderDao;

	@Override
	public PageHibernateDao<YydPort, Long> getDao() {
		return (PageHibernateDao<YydPort, Long>) portDao;
	}

	public PortData findPortByCooord(Double longitude, Double latitude) {
		YydPort yydPort = portDao.findPortByCooord(longitude, latitude);
		return this.getPortData(yydPort);
	}

	public PortData getByFullPortName(String fullPortName) {
		YydPort yydPort = portDao.getByFullPortName(fullPortName);
		return this.getPortData(yydPort);
	}

	public PortData getPortData(String portNo) {
		YydPort yydPort = portDao.getByCode(portNo);
		return this.getPortData(yydPort);
	}

	public Page<PortData> getPortPageData(Page<PortData> pageData, ScfPortCityCode cityCode, String keyWords) {
		try {
			Page<YydPort> page = portDao.getPortPage(pageData.getPageNo(), pageData.getPageSize(), cityCode, keyWords);

			List<PortData> portDatas = new ArrayList<PortData>();
			for (YydPort port : page.getResult()) {
				PortData portData = this.getPortData(port);
				portDatas.add(portData);
			}

			CopyUtil.copyProperties(pageData, page);
			pageData.setResult(portDatas);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageData;
	}

	public List<PortData> getPortDatas() {
		List<PortData> portDatas = new ArrayList<PortData>();
		try {
			List<YydPort> ports = portDao.getAllPorts();

			for (YydPort port : ports) {
				PortData portData = this.getPortData(port);
				portDatas.add(portData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return portDatas;
	}

	public List<PortData> getPortDatasByPortCityCode(String portCityCode) {
		List<PortData> portDatas = new ArrayList<PortData>();
		try {
			List<YydPort> ports = portDao.getPortsByPortCityCode(portCityCode);

			if (!ports.isEmpty())
				for (YydPort port : ports) {
					PortData portData = this.getPortData(port);
					portDatas.add(portData);
				}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return portDatas;
	}

	public PortData getPortData(YydPort yydPort) {
		try {
			PortData portData = null;
			if (yydPort != null) {
				portData = new PortData();

				CopyUtil.copyProperties(portData, yydPort);

				String portCityCode = yydPort.getPortNo().substring(0, 2);
				ScfPortCityCode portCity = ScfPortCityCode.getByCode(portCityCode);
				portData.setPortCity(portCity);

				portData.setPortCooordDatas(portCooordService.getPortCooordDatas(yydPort.getPortNo()));
			}

			return portData;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	// 程序改完后，该方法删除
	public boolean save(String portCityCode, String portNo, String portName) throws Exception {
		return this.save(portCityCode, portNo, portName, 0D, 0D);
	}

	public boolean save(String portCityCode, String portNo, String portName, Double longitude, Double latitude)
			throws Exception {
		return this.save(portCityCode, portNo, portName, longitude, latitude, 0D, 0D, 0D, 0D);
	}

	public boolean save(String portCityCode, String portNo, String portName, Double longitude, Double latitude,
			Double longitude1, Double latitude1, Double longitude2, Double latitude2) throws Exception {
		if (portCityCode == null || "".equals(portCityCode))
			throw new Exception("错误！港口城市必须选择。");

		if (portName == null || "".equals(portName))
			throw new Exception("错误！港口名称必须输入选择。");

		YydPort port = null;
		if ("".equals(portNo)) { // 是添加
			if (portDao.findByPortName(portCityCode, portName) != null)
				throw new Exception("错误！该港口名称已存在。");

			port = new YydPort();

			String spcPortNo = portDao.getSpcPortNo(portCityCode);
			port.setPortNo(spcPortNo);
		} else {
			// 是修改
			port = portDao.getByCode(portNo);

			if (port == null)
				throw new Exception("错误！修改的港口没有找到。");

			if (!port.getPortNo().substring(0, 2).equals(portCityCode)) {
				if (!cargoDao.findBy("startPortNo", portNo).isEmpty())
					throw new Exception("错误！该港口信息已被使用。");
				if (!cargoDao.findBy("endPortNo", portNo).isEmpty())
					throw new Exception("错误！该港口信息已被使用。");

				if (!orderDao.findBy("startPortNo", portNo).isEmpty())
					throw new Exception("错误！该港口信息已被使用。");
				if (!orderDao.findBy("endPortNo", portNo).isEmpty())
					throw new Exception("错误！该港口信息已被使用。");

				String spcPortNo = portDao.getSpcPortNo(portCityCode);
				port.setPortNo(spcPortNo);
			}
			// 先删除港口全部坐标
			portCooordDao.batchExecute("delete from YydPortCooord where portNo = ?", portNo);
		}

		port.setPortName(portName);
		port.setEngPortName(Cn2Spell.converterToSpell(portName));
		port.setFullPortName(ScfPortCityCode.getByCode(portCityCode).getDescription() + "." + portName);
		port.setLongitude(longitude);
		port.setLatitude(latitude);

		portDao.save(port);

		if (!longitude.equals(0D) && !latitude.equals(0D)) {
			YydPortCooord portCooord = new YydPortCooord();
			portCooord.setPortNo(port.getPortNo());
			portCooord.setLongitude(longitude);
			portCooord.setLatitude(latitude);
			portCooordDao.save(portCooord);
		}

		if (!longitude1.equals(0D) && !latitude1.equals(0D)) {
			YydPortCooord portCooord1 = new YydPortCooord();
			portCooord1.setPortNo(port.getPortNo());
			portCooord1.setLongitude(longitude1);
			portCooord1.setLatitude(latitude1);
			portCooordDao.save(portCooord1);
		}

		if (!longitude2.equals(0D) && !latitude2.equals(0D)) {
			YydPortCooord portCooord2 = new YydPortCooord();
			portCooord2.setPortNo(port.getPortNo());
			portCooord2.setLongitude(longitude2);
			portCooord2.setLatitude(latitude2);
			portCooordDao.save(portCooord2);
		}

		return true;
	}

	public boolean deletePort(String portNo) throws Exception {
		YydPort port = portDao.getByCode(portNo);
		if (port != null) {
			portDao.delete(port);
			return true;
		}
		return false;
	}

}
