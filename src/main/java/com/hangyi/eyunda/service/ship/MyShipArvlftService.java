package com.hangyi.eyunda.service.ship;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.YydShipArvlftDao;
import com.hangyi.eyunda.dao.YydShipDao;
import com.hangyi.eyunda.dao.YydShipUpdownDao;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.data.ship.ArvlftShipData;
import com.hangyi.eyunda.data.ship.ShipArvlftData;
import com.hangyi.eyunda.data.ship.ShipCooordData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.data.ship.ShipUpdownData;
import com.hangyi.eyunda.domain.YydShip;
import com.hangyi.eyunda.domain.YydShipArvlft;
import com.hangyi.eyunda.domain.YydShipUpdown;
import com.hangyi.eyunda.domain.enumeric.ArvlftCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.map.ShipCooordService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.FileUtil;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class MyShipArvlftService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydShipDao shipDao;
	@Autowired
	private YydShipArvlftDao shipArvlftDao;
	@Autowired
	private YydShipUpdownDao shipUpdownDao;

	@Autowired
	private PortService portService;
	@Autowired
	private MyShipUpDownService shipUpDownService;
	@Autowired
	private MyShipService shipService;
	@Autowired
	private ShipCooordService shipCooordService;

	public List<ShipCooordData> getShipCurrCooordDatas(ShipData shipData) throws Exception {
		Calendar et = CalendarUtil.now();
		Calendar st = CalendarUtil.addDays(et, -2);

		// 只取2天的数据
		List<ShipCooordData> das = shipCooordService
				.analyseCooorDatas(shipCooordService.getShipCooordDatas(shipData, st, et));

		return das;
	}

	public List<ShipCooordData> getShipCooordDatas(ShipData shipData, ShipArvlftData leftData) throws Exception {
		// 取AIS坐标数据
		YydShipArvlft previousLeft = shipArvlftDao.get(leftData.getId());
		if (previousLeft == null)
			throw new Exception("错误！指定的航次不存在！");

		YydShipArvlft previousArrive = shipArvlftDao.getPreviousArvlft(previousLeft);
		if (previousArrive == null)
			throw new Exception("错误！指定的航次不存在！");

		YydShipArvlft nextArrive = shipArvlftDao.getNextArvlft(previousLeft);
		YydShipArvlft nextLeft = shipArvlftDao.getNextArvlft(nextArrive);

		boolean isHistory = true;
		if (nextArrive == null || nextLeft == null)
			isHistory = false;

		Calendar st = previousArrive.getArvlftTime();
		Calendar et = isHistory ? nextLeft.getArvlftTime() : CalendarUtil.now();
		Calendar ett = CalendarUtil.addDays(st, +5);
		Calendar stt = CalendarUtil.addDays(et, -5);

		// 最多只取5天的数据
		List<ShipCooordData> das = new ArrayList<ShipCooordData>();

		// 不是最后一条
		if (isHistory) {
			// 取已有坐标数据
			List<ShipCooordData> scds = shipCooordService.getShipCooordDatas(shipData, leftData);
			if (!scds.isEmpty())
				return scds;

			if (CalendarUtil.compare(et, ett) > 0) {
				das = shipCooordService.analyseCooorDatas(shipCooordService.getShipCooordDatas(shipData, st, ett));
			} else {
				das = shipCooordService.analyseCooorDatas(shipCooordService.getShipCooordDatas(shipData, st, et));
			}
		} else { // 是最后一条
			if (CalendarUtil.compare(st, stt) < 0) {
				das = shipCooordService.analyseCooorDatas(shipCooordService.getShipCooordDatas(shipData, stt, et));
			} else {
				das = shipCooordService.analyseCooorDatas(shipCooordService.getShipCooordDatas(shipData, st, et));
			}
		}

		// 保存数据文件并更新数据库
		String url = shipCooordService.writeCooordDataFile(shipData.getMmsi(), previousLeft.getId(), das);
		previousLeft.setSailLineData(url);
		shipArvlftDao.save(previousLeft);

		leftData.setSailLineData(url);// 数据文件地址可能已更新

		return das;
	}

	public List<ShipArvlftData> getLast10ShipArvlft(String mmsi) {
		List<ShipArvlftData> shipArvlftDatas = new ArrayList<ShipArvlftData>();
		List<YydShipArvlft> shipArvlfts = shipArvlftDao.getLast10ShipArvlft(mmsi);

		for (int i = shipArvlfts.size() - 1; i >= 0; i--) {
			YydShipArvlft shipArvlft = shipArvlfts.get(i);
			ShipArvlftData shipArvlftData = this.getShipArvlftData(shipArvlft);
			shipArvlftDatas.add(shipArvlftData);
		}

		return shipArvlftDatas;
	}

	public ShipArvlftData getLastShipArvlft(String mmsi) {
		YydShipArvlft shipArvlft = shipArvlftDao.getLastShipArvlft(mmsi);
		if (shipArvlft != null)
			return this.getShipArvlftData(shipArvlft);
		else
			return null;
	}

	public Page<ArvlftShipData> getShipArvlftDatas(String mmsi, String startTime, String endTime, Integer pageNo,
			Integer pageSize) {
		// 我的船舶动态上报
		Page<YydShipArvlft> page = shipArvlftDao.getMyShipArvlfts(mmsi, startTime, endTime, pageNo, 2 * pageSize);
		List<ArvlftShipData> arvlftShipDatas = new ArrayList<ArvlftShipData>();
		List<YydShipArvlft> shipArvlfts = (List<YydShipArvlft>) page.getResult();
		for (int i = 0; i < shipArvlfts.size();) {
			ArvlftShipData arvlftShipData = new ArvlftShipData();
			if (i == 0) {
				YydShipArvlft shipArvlft = shipArvlfts.get(i);
				if (shipArvlft.getArvlft() == ArvlftCode.arrive) {
					arvlftShipData.setArriveData(this.getShipArvlftData(shipArvlft));
					arvlftShipData.setLeftData(this.getNextArvlft(shipArvlft.getId()));
					i++;
				} else if (shipArvlft.getArvlft() == ArvlftCode.left) {
					arvlftShipData.setLeftData(this.getShipArvlftData(shipArvlft));
					i++;
					if (i <= shipArvlfts.size() - 1) {
						arvlftShipData.setArriveData(this.getShipArvlftData(shipArvlfts.get(i)));
						i++;
					} else
						arvlftShipData.setArriveData(this.getPreviousArvlft(shipArvlft.getId()));
				}
			} else if (i == shipArvlfts.size() - 1) {
				YydShipArvlft shipArvlft = shipArvlfts.get(i);
				if (shipArvlft.getArvlft() == ArvlftCode.arrive) {
					arvlftShipData.setArriveData(this.getShipArvlftData(shipArvlft));
					int k = i - 1;
					if (k >= 0)
						arvlftShipData.setLeftData(this.getShipArvlftData(shipArvlfts.get(k)));
					else
						arvlftShipData.setLeftData(this.getNextArvlft(shipArvlft.getId()));

					i++;
				} else if (shipArvlft.getArvlft() == ArvlftCode.left) {
					// arvlftShipData.setLeftData(this.getShipArvlftData(shipArvlft));
					// arvlftShipData.setArriveData(this.getPreviousArvlft(shipArvlft.getId()));
					i++;
					continue;
				}
			} else {
				YydShipArvlft shipArvlft = shipArvlfts.get(i);
				if (shipArvlft.getArvlft() == ArvlftCode.arrive) {
					arvlftShipData.setArriveData(this.getShipArvlftData(shipArvlft));
					int k = i - 1;
					if (k >= 0) {
						arvlftShipData.setLeftData(this.getShipArvlftData(shipArvlfts.get(k)));
					} else
						arvlftShipData.setLeftData(this.getNextArvlft(shipArvlft.getId()));
					i++;
				} else if (shipArvlft.getArvlft() == ArvlftCode.left) {
					arvlftShipData.setLeftData(this.getShipArvlftData(shipArvlft));
					i++;
					if (i <= shipArvlfts.size() - 1) {
						arvlftShipData.setArriveData(this.getShipArvlftData(shipArvlfts.get(i)));
						i++;
					} else
						arvlftShipData.setArriveData(this.getPreviousArvlft(shipArvlft.getId()));
				}
			}
			arvlftShipDatas.add(arvlftShipData);
		}

		Page<ArvlftShipData> pageData = new Page<ArvlftShipData>();
		pageData.setPageNo(page.getPageNo());
		pageData.setPageSize(page.getPageSize() / 2);
		pageData.setTotalCount(page.getTotalCount() / 2);
		pageData.setResult(arvlftShipDatas);

		return pageData;
	}

	public ShipArvlftData getShipArvlftDataById(Long id) {
		YydShipArvlft shipArvlft = shipArvlftDao.get(id);
		ShipArvlftData arvlftData = this.getShipArvlftData(shipArvlft);
		return arvlftData;
	}

	public ShipArvlftData getShipArvlftData(YydShipArvlft shipArvlft) {
		if (null != shipArvlft) {
			ShipArvlftData shipArvlftData = new ShipArvlftData();
			CopyUtil.copyProperties(shipArvlftData, shipArvlft);

			PortData portData = null, goPortData = null;

			portData = portService.getPortData(shipArvlft.getPortNo());
			shipArvlftData.setPortData(portData);
			goPortData = portService.getPortData(shipArvlft.getGoPortNo());
			shipArvlftData.setGoPortData(goPortData);

			String temp = "";

			if (shipArvlft.getArvlft() == ArvlftCode.arrive) {
				if (portData != null)
					temp += CalendarUtil.toYYYY_MM_DD_HH_MM(shipArvlft.getArvlftTime()) + " "
							+ ArvlftCode.arrive.getDescription() + " " + portData.getFullName();
				else
					temp += CalendarUtil.toYYYY_MM_DD_HH_MM(shipArvlft.getArvlftTime()) + " "
							+ ArvlftCode.arrive.getDescription() + " 某某港";
			} else if (shipArvlft.getArvlft() == ArvlftCode.left) {
				YydShipArvlft prev1 = shipArvlftDao.getPreviousArvlft(shipArvlft);
				if (shipArvlft.getArvlftTime() != null && !"".equals(shipArvlft.getArvlftTime())) {
					temp += CalendarUtil.toYYYY_MM_DD_HH_MM(prev1.getArvlftTime()) + " "
							+ ArvlftCode.arrive.getDescription() + " " + portData.getFullName();
					temp += " " + CalendarUtil.toYYYY_MM_DD_HH_MM(shipArvlft.getArvlftTime()) + " "
							+ ArvlftCode.left.getDescription();
					if (goPortData != null)
						temp += " " + ArvlftCode.left.getRemark() + " " + goPortData.getFullName();
				} else {
					temp += CalendarUtil.toYYYY_MM_DD_HH_MM(prev1.getArvlftTime()) + " "
							+ ArvlftCode.arrive.getDescription() + " " + portData.getFullName();
					temp += " 在港停留";
				}
			}
			shipArvlftData.setArvlftDesc(temp);

			shipArvlftData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM(shipArvlft.getCreateTime()));
			if (shipArvlft.getArvlftTime() != null)
				shipArvlftData.setArvlftTime(CalendarUtil.toYYYY_MM_DD_HH_MM(shipArvlft.getArvlftTime()));

			shipArvlftData.setShipUpdownDatas(shipUpDownService.getShipUpdownDatas(shipArvlft.getId()));

			return shipArvlftData;
		} else {
			return null;
		}
	}

	public Long saveShipUpdown(ShipArvlftData shipArvlftData) {
		try {
			YydShipArvlft shipArvlft = shipArvlftDao.get(shipArvlftData.getId());
			if (shipArvlft != null) {
				// 先删除旧的装卸列表
				shipUpdownDao.batchExecute("delete from YydShipUpdown where arvlftId = ?", shipArvlft.getId());

				// 再添加新的装卸列表
				if (!shipArvlftData.getShipUpdownDatas().isEmpty()) {
					for (ShipUpdownData shipUpdownData : shipArvlftData.getShipUpdownDatas()) {
						YydShipUpdown shipUpdown = new YydShipUpdown();
						shipUpdown.setArvlftId(shipArvlft.getId());
						shipUpdown.setCargoType(shipUpdownData.getCargoType());
						shipUpdown.setCargoName(shipUpdownData.getCargoName());
						shipUpdown.setTonTeu(shipUpdownData.getTonTeu());
						shipUpdownDao.save(shipUpdown);
					}
				}
				return shipArvlft.getId();
			}
			return -1l;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return -1l;
		}
	}

	public void saveShipArvlft(Long id, String arriveTime, String leftTime, String portNo, String goPortNo,
			String remark, String mmsi) throws Exception {
		if (leftTime == null || "".equals(leftTime))
			leftTime = CalendarUtil
					.toYYYY_MM_DD_HH_MM(CalendarUtil.addMinutes(CalendarUtil.parseYYYY_MM_DD_HH_MM(arriveTime), 30));

		if (!"".equals(arriveTime) && !"".equals(leftTime)) {
			if (CalendarUtil.compare(CalendarUtil.parseYYYY_MM_DD_HH_MM(arriveTime),
					CalendarUtil.parseYYYY_MM_DD_HH_MM(leftTime)) >= 0)
				throw new Exception("错误！到港时间不能大于等于离港时间。");
		}

		YydShipArvlft previousArrive, previousLeft, currArrive, currLeft, nextArrive = null, nextLeft = null;

		currArrive = shipArvlftDao.get(id);
		if (currArrive == null) {
			previousLeft = shipArvlftDao.getLastShipArvlft(mmsi);
			previousArrive = shipArvlftDao.getPreviousArvlft(previousLeft);
			currArrive = new YydShipArvlft();
			currLeft = new YydShipArvlft();

			if (previousLeft != null && previousArrive != null) {
				if (CalendarUtil.compare(previousLeft.getArvlftTime(),
						CalendarUtil.parseYYYY_MM_DD_HH_MM(arriveTime)) >= 0)
					throw new Exception("错误！上一离港时间不能大于等于下一到港时间。");
			}
		} else {
			YydShipArvlft lastLeft = shipArvlftDao.getLastShipArvlft(mmsi);
			YydShipArvlft lastArrive = shipArvlftDao.getPreviousArvlft(lastLeft);
			if (currArrive.getId().equals(lastArrive.getId())) {
				previousLeft = shipArvlftDao.getPreviousArvlft(currArrive);
				previousArrive = shipArvlftDao.getPreviousArvlft(previousLeft);
				currLeft = lastLeft;

				if (previousLeft != null && previousArrive != null) {
					if (CalendarUtil.compare(previousLeft.getArvlftTime(),
							CalendarUtil.parseYYYY_MM_DD_HH_MM(arriveTime)) >= 0)
						throw new Exception("错误！上一离港时间不能大于等于下一到港时间。");
				}
			} else {
				previousLeft = shipArvlftDao.getPreviousArvlft(currArrive);
				previousArrive = shipArvlftDao.getPreviousArvlft(previousLeft);
				currLeft = shipArvlftDao.getNextArvlft(currArrive);
				nextArrive = shipArvlftDao.getNextArvlft(currLeft);
				nextLeft = shipArvlftDao.getNextArvlft(nextArrive);

				if (previousArrive != null && previousLeft != null) {
					if (CalendarUtil.compare(previousLeft.getArvlftTime(),
							CalendarUtil.parseYYYY_MM_DD_HH_MM(arriveTime)) >= 0)
						throw new Exception("错误！上一离港时间不能大于等于下一到港时间。");
				}
				if (nextArrive != null && nextLeft != null) {
					if (CalendarUtil.compare(nextArrive.getArvlftTime(),
							CalendarUtil.parseYYYY_MM_DD_HH_MM(leftTime)) <= 0)
						throw new Exception("错误！下一到港时间不能小于等于上一离港时间。");
				}
			}
		}

		String realPath = Constants.SHARE_DIR;
		boolean bEditArriveTime = false, bEditLeftTime = false;
		if (CalendarUtil.compare(currArrive.getArvlftTime(), CalendarUtil.parseYYYY_MM_DD_HH_MM(arriveTime)) != 0)
			bEditArriveTime = true;
		if (CalendarUtil.compare(currLeft.getArvlftTime(), CalendarUtil.parseYYYY_MM_DD_HH_MM(leftTime)) != 0)
			bEditLeftTime = true;

		// 到港
		currArrive.setArvlft(ArvlftCode.arrive);
		currArrive.setArvlftTime(CalendarUtil.parseYYYY_MM_DD_HH_MM(arriveTime));
		currArrive.setPortNo(portNo);
		currArrive.setMmsi(mmsi);
		currArrive.setStatus(YesNoCode.no);
		currArrive.setRemark(remark);
		shipArvlftDao.save(currArrive);

		// 离港
		currLeft.setArvlft(ArvlftCode.left);
		currLeft.setArvlftTime(CalendarUtil.parseYYYY_MM_DD_HH_MM(leftTime));
		currLeft.setPortNo(portNo);
		currLeft.setGoPortNo(goPortNo);
		currLeft.setMmsi(mmsi);

		if (nextArrive == null && nextLeft == null)
			currLeft.setStatus(YesNoCode.yes);

		// 当前航次的航程是从当前的到港时间到下一航次的离港时间，修改了前者，当前航次需重新获取坐标
		if (bEditArriveTime) {
			File f = new File(realPath + currLeft.getSailLineData());
			if (f.isFile() && f.exists())
				f.delete();
			currLeft.setSailLineData("");
		}

		shipArvlftDao.save(currLeft);

		// 更新上一离港
		if (previousLeft != null && previousArrive != null) {
			previousLeft.setGoPortNo(portNo);
			previousLeft.setStatus(YesNoCode.no);

			// 上一航次的航程是从上一个到港时间到当前的离港时间，修改了后者，上一航次需重新获取坐标
			if (bEditLeftTime) {
				File f = new File(realPath + previousLeft.getSailLineData());
				if (f.isFile() && f.exists())
					f.delete();
				previousLeft.setSailLineData("");
			}

			shipArvlftDao.save(previousLeft);
		}

		// 更新下一港口
		if (nextArrive != null && nextLeft != null) {
			nextArrive.setPortNo(goPortNo);
			shipArvlftDao.save(nextArrive);
			nextLeft.setPortNo(goPortNo);
			shipArvlftDao.save(nextLeft);
		}
	}

	public void delShipArvlft(Long arriveId) {
		YydShipArvlft beforeOneShipArvlft = null;// 前一条离港信息
		YydShipArvlft behindOneShipArvlft = null;// 后一条离港信息
		YydShipArvlft behindTwoShipArvlft = null;// 后面第二条到港信息

		YydShipArvlft willDelShipArvlft = shipArvlftDao.get(arriveId);

		if (willDelShipArvlft != null) {
			beforeOneShipArvlft = shipArvlftDao.getPreviousArvlft(willDelShipArvlft);
			behindOneShipArvlft = shipArvlftDao.getNextArvlft(willDelShipArvlft);
			if (behindOneShipArvlft != null)
				behindTwoShipArvlft = shipArvlftDao.getNextArvlft(behindOneShipArvlft);

			// 前一条离港信息存在
			if (beforeOneShipArvlft != null) {
				// 后一条离港信息存在
				if (behindOneShipArvlft != null) {
					// 后面第二条到港信息存在
					if (behindTwoShipArvlft != null) {
						// 下面第二条到港信息存在(用户需自行修改此时的卸货列表,防止卸货列表中含有已经删除的货物)
						// 改变前一条离港信息的将去港信息
						beforeOneShipArvlft.setGoPortNo(behindTwoShipArvlft.getPortNo());
						shipArvlftDao.save(beforeOneShipArvlft);

					} else {
						// 1.改变上面第一条离港信息的将去港信息(此时删除的是倒数第二条信息,需将上面一条离港信息的状态改为最后状态)
						beforeOneShipArvlft.setGoPortNo("");
						beforeOneShipArvlft.setStatus(YesNoCode.yes);
						shipArvlftDao.save(beforeOneShipArvlft);
					}
					// 删除后一条离港信息
					shipUpdownDao.delShipUpdowns(behindOneShipArvlft.getId());
					shipArvlftDao.delete(behindOneShipArvlft);
				} else {
					// 3.改变上一条离港信息的将去港信息(此时删除的是最后一条信息,需将上面一条离港信息的状态改为最后状态)
					beforeOneShipArvlft.setGoPortNo("");
					beforeOneShipArvlft.setStatus(YesNoCode.yes);
					shipArvlftDao.save(beforeOneShipArvlft);
				}
			} else {
				if (behindOneShipArvlft != null) {
					// 删除后一条离港信息
					shipUpdownDao.delShipUpdowns(behindOneShipArvlft.getId());
					shipArvlftDao.delete(behindOneShipArvlft);
				}
			}
			// 1.删除该条到港信息
			shipUpdownDao.delShipUpdowns(willDelShipArvlft.getId());
			shipArvlftDao.delete(willDelShipArvlft);
		}

	}

	public ShipArvlftData getPreviousArvlft(Long id) {
		YydShipArvlft prevShipArvlft = shipArvlftDao.getPreviousArvlft(shipArvlftDao.get(id));
		if (prevShipArvlft != null)
			return this.getShipArvlftData(prevShipArvlft);
		return null;
	}

	public ShipArvlftData getNextArvlft(Long id) {
		YydShipArvlft nextArvlft = shipArvlftDao.getNextArvlft(shipArvlftDao.get(id));
		if (nextArvlft != null)
			return this.getShipArvlftData(nextArvlft);
		return null;
	}

	public String exportStateExcel(String mmsi, String startTime, String endTime, List<ArvlftShipData> arvlftDatas) {
		if (!arvlftDatas.isEmpty()) {
			String realPath = Constants.SHARE_DIR;
			String relaPath = ShareDirService.getShipDir(mmsi);
			String fileName = mmsi + "-" + startTime + "-" + endTime + ".xls";
			File file = new File(realPath + relaPath + "/" + fileName);
			if (!file.exists())
				FileUtil.makeDirectory(file);

			createExcel(file, arvlftDatas);
			return relaPath + "/" + fileName;
		}
		return "";
	}

	private void createExcel(File file, List<ArvlftShipData> arvlftDatas) {
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			// 创建工作薄
			workbook = Workbook.createWorkbook(os);
			// 创建新的一页
			sheet = workbook.createSheet("船舶动态记录表", 2);
			// 创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
			WritableCellFormat wcfF = new WritableCellFormat();
			wcfF.setWrap(true);
			wcfF.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcfF.setAlignment(Alignment.LEFT);
			WritableCellFormat wcfF1 = new WritableCellFormat();
			wcfF1.setWrap(true);
			wcfF1.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcfF1.setAlignment(Alignment.CENTRE);

			sheet.setColumnView(0, 15);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 33);
			sheet.setColumnView(4, 33);
			sheet.setColumnView(5, 15);
			sheet.setColumnView(6, 30);

			sheet.addCell(new Label(0, 0, "港口", wcfF1));
			sheet.addCell(new Label(1, 0, "到港时间", wcfF1));
			sheet.addCell(new Label(2, 0, "卸货", wcfF1));
			sheet.addCell(new Label(3, 0, "离港时间", wcfF1));
			sheet.addCell(new Label(4, 0, "装货", wcfF1));
			sheet.addCell(new Label(5, 0, "下一港口", wcfF1));
			sheet.addCell(new Label(6, 0, "备注", wcfF1));

			for (int i = 0; i < arvlftDatas.size(); i++) {
				ArvlftShipData arvlftShipData = arvlftDatas.get(i);

				ShipArvlftData arriveData = arvlftShipData.getArriveData();
				ShipArvlftData leftData = arvlftShipData.getLeftData();

				sheet.addCell(new Label(0, i + 1, arriveData.getPortData().getFullName(), wcfF));
				sheet.addCell(new Label(1, i + 1, arriveData.getArvlftTime(), wcfF));

				List<ShipUpdownData> downDatas = arriveData.getShipUpdownDatas();
				String downCargoDes = "";
				for (int j = 0; j < downDatas.size(); j++) {
					ShipUpdownData downData = downDatas.get(j);
					downCargoDes += downData.getCargoNameDesc() + ":";
					downCargoDes += downData.getTonTeuDesc();
					if (j < downDatas.size() - 1)
						downCargoDes += "\n";
				}

				sheet.addCell(new Label(2, i + 1, downCargoDes, wcfF));
				sheet.addCell(new Label(3, i + 1, leftData.getArvlftTime(), wcfF));

				List<ShipUpdownData> upDatas = leftData.getShipUpdownDatas();
				String upCargoDes = "";
				for (int k = 0; k < upDatas.size(); k++) {
					ShipUpdownData upData = upDatas.get(k);
					upCargoDes += upData.getCargoNameDesc() + ":";
					upCargoDes += upData.getTonTeuDesc();
					if (k < upDatas.size() - 1)
						upCargoDes += "\n";
				}

				sheet.addCell(new Label(4, i + 1, upCargoDes, wcfF));
				sheet.addCell(new Label(5, i + 1,
						leftData.getGoPortData() == null ? "" : leftData.getGoPortData().getFullName(), wcfF));
				sheet.addCell(new Label(6, i + 1, arriveData.getRemark(), wcfF));
			}
			// 把创建的内容写入到输出流中，并关闭输出流
			workbook.write();
			workbook.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArvlftShipData getArvlftShipData(YydShip yydShip) {
		ArvlftShipData arvlftShipData = new ArvlftShipData();

		ShipData shipData = shipService.getShipData(yydShip);
		arvlftShipData.setShipData(shipData);

		List<YydShipArvlft> last2ShipArvlft = shipArvlftDao.getLast2ShipArvlft(shipData.getMmsi());
		if (!last2ShipArvlft.isEmpty()) {
			ShipArvlftData shipLeftData = this.getShipArvlftData(last2ShipArvlft.get(0));
			ShipArvlftData shipArriveData = this.getShipArvlftData(last2ShipArvlft.get(1));
			arvlftShipData.setLeftData(shipLeftData);
			arvlftShipData.setArriveData(shipArriveData);
		}

		return arvlftShipData;
	}

	public Page<ArvlftShipData> getArvlftShipPage(Long compId, String keyWords, int pageNo, int pageSize, String mmsi,
			Long deptId, Long masterId) {
		List<ArvlftShipData> arvlftShipDatas = new ArrayList<ArvlftShipData>();
		Page<ArvlftShipData> pageData = new Page<ArvlftShipData>();

		Page<YydShip> page = shipDao.getShipPage(compId, keyWords, pageNo, pageSize, mmsi, deptId, masterId);

		for (YydShip yydShip : (List<YydShip>) page.getResult()) {
			ArvlftShipData arvlftShipData = this.getArvlftShipData(yydShip);
			arvlftShipDatas.add(arvlftShipData);
		}
		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(arvlftShipDatas);
		return pageData;
	}

}
