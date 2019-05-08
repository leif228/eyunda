package com.hangyi.eyunda.service.ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydCabinInfoDao;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.ship.SailLineData;
import com.hangyi.eyunda.data.ship.ShipCabinData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.data.ship.UpDownPortData;
import com.hangyi.eyunda.domain.YydCabinInfo;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class MyCabinService extends BaseService<YydCabinInfo, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydCabinInfoDao cabinInfoDao;

	@Autowired
	private MyShipService shipService;
	@Autowired
	private UserService userService;
	@Autowired
	private PortService portService;

	@Override
	public PageHibernateDao<YydCabinInfo, Long> getDao() {
		return (PageHibernateDao<YydCabinInfo, Long>) cabinInfoDao;
	}

	public ShipCabinData getById(Long id) {
		YydCabinInfo cabinInfo = cabinInfoDao.get(id);
		return this.getCabinData(cabinInfo);
	}

	public ShipCabinData getByCabin(WaresBigTypeCode waresBigType, WaresTypeCode waresType, CargoTypeCode cargoType,
			Long shipId, Long masterId) {
		YydCabinInfo cabinInfo = cabinInfoDao.getByCabin(waresBigType, waresType, cargoType, shipId, masterId);
		return this.getCabinData(cabinInfo);
	}

	public ShipCabinData getByIdAndSelPortNos(Long id, String selPortNos) {
		ShipCabinData cabinData = this.getById(id);
		if (cabinData != null) {
			if (cabinData.getWaresBigType() == WaresBigTypeCode.voyage) {
				cabinData.setCurrSailLineData(selPortNos);
			} else if (cabinData.getWaresBigType() == WaresBigTypeCode.daily) {
				cabinData.sortUpDownPortDatas(selPortNos);
			}
		}
		return cabinData;
	}

	public String getCabinDesc(Long shipId) {
		return "";
	}

	public ShipCabinData getCabinData(YydCabinInfo cabinInfo, String... selPortNos) {
		ShipCabinData cabinData = null;
		if (cabinInfo != null) {
			cabinData = new ShipCabinData();

			CopyUtil.copyProperties(cabinData, cabinInfo);

			ShipData shipData = shipService.getShipData(cabinInfo.getShipId());
			cabinData.setShipData(shipData);

			UserData broker = userService.getUserData(cabinInfo.getBrokerId());
			cabinData.setBroker(broker);

			UserData master = userService.getUserData(cabinInfo.getMasterId());
			cabinData.setMaster(master);

			UserData publisher = userService.getUserData(cabinInfo.getPublisherId());
			cabinData.setPublisher(publisher);

			if (cabinInfo.getWaresBigType() == WaresBigTypeCode.daily) {
				List<UpDownPortData> upDownPortDatas = this.makeUpDownPortDatas(cabinInfo.getWaresBigType(),
						cabinInfo.getWaresType(), cabinInfo.getCargoType(), cabinInfo.getPorts(), selPortNos);
				cabinData.setUpDownPortDatas(upDownPortDatas);
			}

			if (cabinInfo.getWaresBigType() == WaresBigTypeCode.voyage) {
				List<SailLineData> sailLineDatas = this.makeSailLineDatas(cabinInfo.getWaresBigType(),
						cabinInfo.getWaresType(), cabinInfo.getCargoType(), cabinInfo.getPrices(), selPortNos);
				cabinData.setSailLineDatas(sailLineDatas);
			}

			String startDate = CalendarUtil.toYYYY_MM_DD(cabinInfo.getStartDate());
			cabinData.setStartDate(startDate);
			String createTime = CalendarUtil.toYYYY_MM_DD_HH_MM_SS(cabinInfo.getCreateTime());
			cabinData.setCreateTime(createTime);
		}
		return cabinData;
	}

	private List<UpDownPortData> makeUpDownPortDatas(WaresBigTypeCode waresBigType, WaresTypeCode waresType,
			CargoTypeCode cargoType, String ports, String... selPortNos) {

		List<UpDownPortData> upDownPortDatas = new ArrayList<UpDownPortData>();
		if (ports == null || "".equals(ports))
			return upDownPortDatas;

		String[] ls = ports.split(";");
		for (String l : ls) {
			String[] ss = l.split(":");
			if (ss != null && ss.length == 2) {
				String pn = ss[0];
				if (selPortNos != null && selPortNos.length > 0) {
					if (selPortNos[0] != null && !selPortNos[0].equals(pn)) {
						continue;
					}
				}
				UpDownPortData upDownPortData = new UpDownPortData();
				upDownPortData.setWaresBigType(waresBigType);
				upDownPortData.setWaresType(waresType);
				upDownPortData.setCargoType(cargoType);

				upDownPortData.setStartPortNo(pn);
				upDownPortData.setStartPortData(portService.getPortData(pn));

				Integer weight = Integer.parseInt(ss[1]);
				upDownPortData.setWeight(weight);

				if (weight > 0)
					upDownPortData.setGotoThisPort(true);
				else
					upDownPortData.setGotoThisPort(false);

				upDownPortDatas.add(upDownPortData);
			}
		}
		return upDownPortDatas;
	}

	private List<SailLineData> makeSailLineDatas(WaresBigTypeCode waresBigType, WaresTypeCode waresType,
			CargoTypeCode cargoType, String prices, String... selPortNos) {

		List<SailLineData> sailLineDatas = new ArrayList<SailLineData>();
		if (prices == null || "".equals(prices))
			return sailLineDatas;

		String[] ls = prices.split(";");
		for (String l : ls) {
			String[] ss = l.split(":");
			if (ss != null && ss.length == 4) {
				String ports = ss[0];
				if (selPortNos != null && selPortNos.length > 0) {
					if (selPortNos[0] != null && !selPortNos[0].equals(ports)) {
						continue;
					}
				}
				SailLineData sailLineData = new SailLineData();
				sailLineData.setWaresBigType(waresBigType);
				sailLineData.setWaresType(waresType);
				sailLineData.setCargoType(cargoType);

				String[] ps = ports.split("\\-");
				String startPortNo = ps[0];
				String endPortNo = ps[1];

				sailLineData.setStartPortNo(startPortNo);
				sailLineData.setStartPortData(portService.getPortData(startPortNo));
				sailLineData.setEndPortNo(endPortNo);
				sailLineData.setEndPortData(portService.getPortData(endPortNo));
				sailLineData.setSailLineNo(startPortNo + "-" + endPortNo);

				Integer distance = Integer.parseInt(ss[1]);
				Integer weight = Integer.parseInt(ss[2]);

				sailLineData.setDistance(distance);
				sailLineData.setWeight(weight);

				boolean gotoThisLine = false;
				Map<Integer, Double> mapPrices = new TreeMap<Integer, Double>();

				String cargoTypes = ss[3];
				String[] cts = cargoTypes.split(",");

				for (String ct : cts) {
					String[] tt = ct.split("\\^");

					String cargoTypeName = tt[0];
					String priceValue = tt[1];

					CargoTypeCode ccargoType = CargoTypeCode.valueOf(cargoTypeName);
					Double price = Double.parseDouble(priceValue);

					if (!gotoThisLine && price > 0)
						gotoThisLine = true;

					mapPrices.put(ccargoType.ordinal(), price);
				}
				sailLineData.setGotoThisLine(gotoThisLine);
				sailLineData.setMapPrices(mapPrices);

				sailLineData.setRemark(sailLineData.makeRemark());
				sailLineData.setDescription(sailLineData.makeDescription());

				sailLineDatas.add(sailLineData);
			}
		}

		return sailLineDatas;
	}

	private String randomFindPortNo(String ports) {
		if (ports == null || "".equals(ports))
			return null;

		String[] ls = ports.split(";");
		List<String> lst = new ArrayList<String>();

		for (String l : ls) {
			String[] ss = l.split(":");
			if (ss != null && ss.length == 2) {
				String pn = ss[0];
				Integer weight = Integer.parseInt(ss[1]);

				if (weight > 0)
					lst.add(pn);
			}
		}

		if (lst.size() > 0) {
			int x = (int) (Math.random() * lst.size());
			return lst.get(x);
		}

		return null;
	}

	private String randomFindPortNos(String prices) {
		if (prices == null || "".equals(prices))
			return null;

		String[] ls = prices.split(";");
		List<String> lst = new ArrayList<String>();

		for (String l : ls) {
			String[] ss = l.split(":");
			if (ss != null && ss.length == 4) {
				String ports = ss[0];

				String cargoTypes = ss[3];
				String[] cts = cargoTypes.split(",");
				for (String ct : cts) {
					String[] tt = ct.split("\\^");

					String priceValue = tt[1];
					Double price = Double.parseDouble(priceValue);

					if (price > 0) {
						lst.add(ports);
						break;
					}
				}
			}
		}

		if (lst.size() > 0) {
			int x = (int) (Math.random() * lst.size());
			return lst.get(x);
		}

		return null;
	}

	public Page<ShipCabinData> getCabinPageForManager(Page<ShipCabinData> pageData, String keyWords, String startDate,
			String endDate) {

		Page<YydCabinInfo> page = cabinInfoDao.getCabinPageForManager(pageData.getPageSize(), pageData.getPageNo(),
				keyWords, startDate, endDate);

		List<ShipCabinData> cabinDatas = new ArrayList<ShipCabinData>();
		for (YydCabinInfo cabinInfo : page.getResult()) {
			String selPortNos = "";
			if (cabinInfo.getWaresBigType() == WaresBigTypeCode.voyage) {
				selPortNos = this.randomFindPortNos(cabinInfo.getPrices());
			} else if (cabinInfo.getWaresBigType() == WaresBigTypeCode.daily) {
				selPortNos = this.randomFindPortNo(cabinInfo.getPorts());
			}

			ShipCabinData cabinData = this.getCabinData(cabinInfo, selPortNos);
			if (cabinData != null) {
				if (cabinData.getWaresBigType() == WaresBigTypeCode.voyage) {
					cabinData.setCurrSailLineData(selPortNos);
				} else if (cabinData.getWaresBigType() == WaresBigTypeCode.daily) {
					cabinData.sortUpDownPortDatas(selPortNos);
				}
				cabinDatas.add(cabinData);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(cabinDatas);

		return pageData;
	}

	// 新增或修改
	public Long saveOrUpdate(ShipCabinData cabinData, UserData userData) throws Exception {
		if (cabinData.getShipId().equals(0L))
			throw new Exception("错误！未选择船舶，不能发布船盘信息。");

		YydCabinInfo cabinInfo = this.get(cabinData.getId());

		if (cabinInfo == null) {
			cabinInfo = new YydCabinInfo();
		} else {
			if (!this.valid(cabinInfo.getId(), userData.getId())) {
				throw new Exception("警告！你不能共享、修改或删除别人发布的船盘！");
			}
		}

		CopyUtil.copyProperties(cabinInfo, cabinData);

		cabinInfo.setStartDate(CalendarUtil.parseYYYY_MM_DD(cabinData.getStartDate()));
		cabinInfo.setCreateTime(CalendarUtil.now());

		cabinInfoDao.save(cabinInfo);

		cabinData.setId(cabinInfo.getId());

		return cabinInfo.getId();
	}

	// 验证操作权限
	public boolean valid(Long cabinId, Long userId) throws Exception {
		YydCabinInfo cabinInfo = this.get(cabinId);
		if (cabinInfo == null)
			throw new Exception("错误！指定的船盘信息记录已经不存在！");

		if (!cabinInfo.getPublisherId().equals(userId))
			return false;

		return true;
	}

	// 发布
	public boolean publish(Long cabinId) {
		YydCabinInfo cabinInfo = this.get(cabinId);

		if (cabinInfo != null) {
			if (ReleaseStatusCode.unpublish == cabinInfo.getStatus()) {
				cabinInfo.setStatus(ReleaseStatusCode.publish);
				cabinInfoDao.save(cabinInfo);
				return true;
			}
		}

		return false;
	}

	// 取消发布
	public boolean unpublish(Long cabinId) {
		YydCabinInfo cabinInfo = this.get(cabinId);

		if (cabinInfo != null) {
			if (ReleaseStatusCode.publish == cabinInfo.getStatus()) {
				cabinInfo.setStatus(ReleaseStatusCode.unpublish);
				cabinInfoDao.save(cabinInfo);
				return true;
			}
		}

		return false;
	}

	// 删除
	public void deleteCabin(Long cabinId) {
		YydCabinInfo cabinInfo = this.get(cabinId);

		if (cabinInfo != null) {
			cabinInfoDao.delete(cabinInfo);
		}
	}

	public Page<ShipCabinData> getCabinPage(Page<ShipCabinData> pageData, UserData publisher, String keyWords,
			String startDate, String endDate) {

		Page<YydCabinInfo> pgCabin = cabinInfoDao.getCabinPage(publisher.getId(), pageData.getPageNo(),
				pageData.getPageSize(), keyWords, startDate, endDate);

		List<ShipCabinData> cabinDatas = new ArrayList<ShipCabinData>();
		for (YydCabinInfo yydCabinInfo : pgCabin.getResult()) {
			String selPortNos = "";
			if (yydCabinInfo.getWaresBigType() == WaresBigTypeCode.voyage) {
				selPortNos = this.randomFindPortNos(yydCabinInfo.getPrices());
			} else if (yydCabinInfo.getWaresBigType() == WaresBigTypeCode.daily) {
				selPortNos = this.randomFindPortNo(yydCabinInfo.getPorts());
			}

			ShipCabinData cabinData = this.getCabinData(yydCabinInfo, selPortNos);
			if (cabinData != null) {
				if (cabinData.getWaresBigType() == WaresBigTypeCode.voyage) {
					cabinData.setCurrSailLineData(selPortNos);
				} else if (cabinData.getWaresBigType() == WaresBigTypeCode.daily) {
					cabinData.sortUpDownPortDatas(selPortNos);
				}
				cabinDatas.add(cabinData);
			}
		}

		CopyUtil.copyProperties(pageData, pgCabin);
		pageData.setResult(cabinDatas);

		return pageData;
	}

	public Page<ShipCabinData> getCabinHomePage(Page<ShipCabinData> pageData, WaresBigTypeCode waresBigType,
			WaresTypeCode waresType, CargoTypeCode cargoType, String selPortNos, String keyWords) {

		Page<YydCabinInfo> pgCabin = cabinInfoDao.getCabinHomePage(pageData.getPageNo(), pageData.getPageSize(),
				waresBigType, waresType, cargoType, selPortNos, keyWords);

		List<ShipCabinData> cabinDatas = new ArrayList<ShipCabinData>();
		for (YydCabinInfo yydCabinInfo : pgCabin.getResult()) {
			String pns = selPortNos;
			if (yydCabinInfo.getWaresBigType() == WaresBigTypeCode.voyage) {
				if (pns == null || "".equals(pns)) {
					pns = this.randomFindPortNos(yydCabinInfo.getPrices());
				}
			} else if (yydCabinInfo.getWaresBigType() == WaresBigTypeCode.daily) {
				if (pns == null || "".equals(pns)) {
					pns = this.randomFindPortNo(yydCabinInfo.getPorts());
				}
			}

			ShipCabinData cabinData = this.getCabinData(yydCabinInfo, pns);
			if (cabinData != null) {
				if (cabinData.getWaresBigType() == WaresBigTypeCode.voyage) {
					cabinData.setCurrSailLineData(pns);
				} else if (cabinData.getWaresBigType() == WaresBigTypeCode.daily) {
					cabinData.sortUpDownPortDatas(pns);
				}
				cabinDatas.add(cabinData);
			}
		}

		CopyUtil.copyProperties(pageData, pgCabin);
		pageData.setResult(cabinDatas);

		return pageData;
	}

}
