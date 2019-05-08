package com.hangyi.eyunda.service.ship;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydOrderCommonDao;
import com.hangyi.eyunda.dao.YydOrderEvaluateDao;
import com.hangyi.eyunda.dao.YydShipAttaDao;
import com.hangyi.eyunda.dao.YydShipAttrDao;
import com.hangyi.eyunda.dao.YydShipDao;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.order.EvaluateData;
import com.hangyi.eyunda.data.ship.AttrNameData;
import com.hangyi.eyunda.data.ship.ShipArvlftData;
import com.hangyi.eyunda.data.ship.ShipAttaData;
import com.hangyi.eyunda.data.ship.ShipAttrData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.data.ship.TypeData;
import com.hangyi.eyunda.domain.YydOrderEvaluate;
import com.hangyi.eyunda.domain.YydShip;
import com.hangyi.eyunda.domain.YydShipAtta;
import com.hangyi.eyunda.domain.YydShipAttr;
import com.hangyi.eyunda.domain.enumeric.ImgTypeCode;
import com.hangyi.eyunda.domain.enumeric.ShipMonitorPlantCode;
import com.hangyi.eyunda.domain.enumeric.ShipStatusCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.manage.FilterWordsService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Cn2Spell;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.ImageUtil;
import com.hangyi.eyunda.util.MultipartUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class MyShipService extends BaseService<YydShip, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final int PAGE_SIZE = 20;

	@Autowired
	private YydOrderCommonDao orderCommonDao;
	@Autowired
	private YydShipDao shipDao;
	@Autowired
	private YydShipAttaDao shipAttaDao;
	@Autowired
	private YydShipAttrDao shipAttrDao;
	@Autowired
	private YydOrderEvaluateDao evaluateDao;

	@Autowired
	private MyCabinService cabinService;
	@Autowired
	private ShipMonitorPlantService plantService;
	@Autowired
	private UserService userService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private MyShipArvlftService shipArvlftService;
	@Autowired
	private MyShipAttrService shipAttrService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private FilterWordsService filterWordsService;
	@Autowired
	private MyShipCargoTypeService shipCargoTypeService;
	@Autowired
	private MyShipPortService shipPortService;

	@Override
	public PageHibernateDao<YydShip, Long> getDao() {
		return (PageHibernateDao<YydShip, Long>) shipDao;
	}

	public List<ShipData> getAllShipDatas() {
		List<YydShip> ships = shipDao.getAll();

		List<ShipData> shipDatas = new ArrayList<ShipData>();
		for (YydShip ship : ships)
			shipDatas.add(this.getShipData(ship));

		return shipDatas;
	}

	public Page<ShipData> getShipPage(Long compId, String keyWords, int pageNo, int pageSize, String mmsi, Long deptId,
			Long masterId) {
		List<ShipData> shipDatas = new ArrayList<ShipData>();
		Page<YydShip> page = shipDao.getShipPage(compId, keyWords, pageNo, pageSize, mmsi, deptId, masterId);
		for (YydShip yydShip : (List<YydShip>) page.getResult()) {
			ShipData shipData = this.getShipData(yydShip);
			if (shipData != null)
				shipDatas.add(shipData);
		}

		Page<ShipData> pageData = new Page<ShipData>();
		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(shipDatas);

		return pageData;
	}

	public Page<ShipData> getSimpleShipPage(Long compId, String keyWords, int pageNo, int pageSize, String mmsi, Long deptId,
			Long masterId) {
		List<ShipData> shipDatas = new ArrayList<ShipData>();
		Page<YydShip> page = shipDao.getShipPage(compId, keyWords, pageNo, pageSize, mmsi, deptId, masterId);
		for (YydShip yydShip : (List<YydShip>) page.getResult()) {
			ShipData shipData = new ShipData();
			shipData.setShipName(yydShip.getShipName());
			shipData.setMmsi(yydShip.getMmsi());
			shipDatas.add(shipData);
		}

		Page<ShipData> pageData = new Page<ShipData>();
		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(shipDatas);

		return pageData;
	}

	public Map<String, String> getCabinDescs(Long compId, String keyWords, String mmsi, Long deptId, Long masterId) {
		Page<YydShip> page = shipDao.getShipPage(compId, keyWords, 1, Constants.ALL_SIZE, mmsi, deptId, masterId);

		Map<String, String> map = new HashMap<String, String>();
		if (page != null && !page.getResult().isEmpty()) {
			for (YydShip ship : page.getResult()) {
				String cabinDesc = cabinService.getCabinDesc(ship.getId());
				map.put(ship.getMmsi(), ship.getId() + "|" + ship.getShipName() + "|" + cabinDesc);
			}
		}

		return map;
	}

	public ShipData getShipDataByShipName(String shipName) {
		YydShip ship = shipDao.getShipByShipName(shipName);
		if (ship != null) {
			ShipData shipData = this.getFullShipData(ship);
			return shipData;
		}
		return null;
	}

	public boolean audit(Long id) {
		try {
			YydShip ship = shipDao.get(id);

			if (ship.getShipStatus() == ShipStatusCode.commit) {
				ship.setShipStatus(ShipStatusCode.audit);
				ship.setReleaseTime(Calendar.getInstance());
				shipDao.save(ship);
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	public boolean unAudit(Long id) {
		try {
			YydShip ship = shipDao.get(id);

			if (ship.getShipStatus() == ShipStatusCode.audit) {
				ship.setShipStatus(ShipStatusCode.commit);
				ship.setReleaseTime(Calendar.getInstance());
				shipDao.save(ship);
				return true;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	public boolean publishShip(Long id) {
		try {
			YydShip ship = shipDao.get(id);
			// ShipData shipData = this.getShipData(ship);

			if (ship.getShipStatus() == ShipStatusCode.audit) {
				ship.setShipStatus(ShipStatusCode.publish);
				ship.setReleaseTime(Calendar.getInstance());
				shipDao.save(ship);
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean unpublishShip(Long id) {
		try {
			YydShip ship = shipDao.get(id);

			if (ship.getShipStatus() == ShipStatusCode.publish) {
				ship.setShipStatus(ShipStatusCode.audit);
				ship.setReleaseTime(Calendar.getInstance());
				shipDao.save(ship);
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	// 无权限检查，直接删除船舶及一切相关信息，很危险，其他程序调用前，要作权限检查
	private boolean deleteShip(Long shipId) {
		try {
			YydShip yydShip = shipDao.get(shipId);
			if (yydShip != null) {
				/*
				 * // 删除接货类别，一对多会随YydShip自动删除 List<YydShipCargoType>
				 * shipCargoTypes = shipCargoTypeDao.getShipCargoTypes(shipId);
				 * for (YydShipCargoType shipCargoType : shipCargoTypes)
				 * shipCargoTypeDao.delete(shipCargoType);
				 * 
				 * // 删除经营区域，一对多会随YydShip自动删除 List<YydShipPort> shipPorts =
				 * shipPortDao.getShipPorts(shipId); for (YydShipPort shipPort :
				 * shipPorts) shipPortDao.delete(shipPort);
				 */

				// 删除船舶类别属性
				List<YydShipAttr> shipAttrs = shipAttrDao.findBy("shipCode", yydShip.getShipCode());
				if (!shipAttrs.isEmpty())
					for (YydShipAttr shipAttr : shipAttrs)
						shipAttrDao.delete(shipAttr);

				// 删除船舶附件
				List<YydShipAtta> shipAttas = shipAttaDao.findBy("shipId", yydShip.getId());
				if (!shipAttas.isEmpty()) {
					for (YydShipAtta shipAtta : shipAttas) {
						// 如果存在附件文件删除之
						String realPath = Constants.SHARE_DIR;
						String attaPathFile = shipAtta.getUrl();
						File attaF = new File(realPath + attaPathFile);
						if (attaF.exists())
							attaF.delete();
						// 删除附件记录
						shipAttaDao.delete(shipAtta);
					}
				}

				// 如果存在船舶logo文件删除之
				String realPath = Constants.SHARE_DIR;
				String logoPathFile = yydShip.getShipLogo();
				File logoF = new File(realPath + logoPathFile);
				if (logoF.exists())
					logoF.delete();

				// 删除船舶记录
				shipDao.delete(yydShip);

				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Page<ShipData> findByName(int pageNo, String nameOrMmsi, ShipStatusCode statusCode) {

		Page<ShipData> pageData = new Page<ShipData>();
		Page<YydShip> page = shipDao.findByNameOrMmsi(pageNo, nameOrMmsi, statusCode);

		List<ShipData> shipDatas = new ArrayList<ShipData>();

		for (YydShip yydShip : page.getResult()) {
			ShipData shipData = this.getShipData(yydShip);
			shipDatas.add(shipData);
		}
		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(shipDatas);

		return pageData;
	}

	public ShipData getFullShipData(Long id) {
		YydShip ship = shipDao.get(id);
		if (ship != null) {
			return this.getFullShipData(ship);
		}
		return null;
	}

	public ShipData getShipData(Long id) {
		YydShip ship = shipDao.get(id);
		if (ship != null) {
			return this.getShipData(ship);
		}
		return null;
	}

	public String getMyShipName(String mmsi) {
		YydShip ship = shipDao.findByMmsi(mmsi);
		if (ship != null) {
			return ship.getShipName();
		}
		return "";
	}

	public ShipData getShipDataByMmsi(String mmsi) {
		YydShip ship = shipDao.findByMmsi(mmsi);
		if (ship != null) {
			return this.getShipData(ship);
		}
		return null;
	}

	public ShipData getFullShipData(YydShip ship) {
		if (ship != null) {
			ShipData myShipData = this.getShipData(ship);

			// 证书
			List<ShipAttaData> myShipAttaDatas = this.getMyShipAttaDatas(myShipData.getId());
			myShipData.setMyShipAttaDatas(myShipAttaDatas);
			// 船舶动态
			myShipData.setShipArvlftDatas(shipArvlftService.getLast10ShipArvlft(ship.getMmsi()));
			// 接货类别
			// myShipData.setShipCargoTypes(shipCargoTypeService.getShipCargoTypes(ship.getId()));
			// 运营区域
			// myShipData.setShipPortDatas(shipPortService.getShipPortDatas(ship.getId()));
			// 船舶属性
			List<AttrNameData> attrNameDatas = attributeService.getTypeAttrNameDatas(myShipData.getShipType());
			for (AttrNameData attrNameData : attrNameDatas) {
				// 取出船舶对应每一动态属性的取值并存入attrNameData
				ShipAttrData shipAttrData = shipAttrService.getShipAttrValue(myShipData.getShipCode(),
						attrNameData.getAttrNameCode());

				if (shipAttrData == null)
					shipAttrData = new ShipAttrData();

				attrNameData.setCurrAttrValue(shipAttrData);
			}
			myShipData.setAttrNameDatas(attrNameDatas);

			return myShipData;
		}
		return null;
	}

	public ShipData getShipData(YydShip ship) {
		if (ship != null) {
			ShipData myShipData = new ShipData();

			CopyUtil.copyProperties(myShipData, ship);

			// 如果不存在船舶logo文件
			String realPath = Constants.SHARE_DIR;
			String logoPathFile = myShipData.getShipLogo();
			if (logoPathFile == null || "".equals(logoPathFile) || !new File(realPath + logoPathFile).exists()) {
				myShipData.setShipLogo("/default/ship/" + myShipData.getShipType() + ".jpg");
			} else {
				int p = logoPathFile.lastIndexOf(".");
				if (p < 0)
					p = logoPathFile.length();

				String ext = "";
				if (p > 0)
					ext = logoPathFile.substring(p).toLowerCase();

				String lineRentLogo = logoPathFile.substring(0, p) + "-lineRent" + ext;
				String timeRentLogo = logoPathFile.substring(0, p) + "-timeRent" + ext;

				if (!new File(realPath + lineRentLogo).exists() || !new File(realPath + timeRentLogo).exists()) {
					String waterImg = "/default/ship/timeRent.jpg";
					try {
						ImageUtil.makeShipLogo(realPath + logoPathFile, realPath + lineRentLogo,
								realPath + timeRentLogo, realPath + waterImg);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (ship.getShipStatus() == ShipStatusCode.publish)
					myShipData.setShipLogo(timeRentLogo);
				else
					myShipData.setShipLogo(lineRentLogo);
			}

			ShipMonitorPlantCode plantName = plantService.getPlant(myShipData.getMmsi());
			myShipData.setShipPlant(plantName);

			if (ship.getMmsi() != null && !"".equals(ship.getMmsi())) {
				ShipArvlftData lftData = shipArvlftService.getLastShipArvlft(ship.getMmsi());
				if (lftData != null) {
					myShipData.setArvlftDesc(lftData.getArvlftDesc());
				}
			}

			if (myShipData.getShipStatus() != ShipStatusCode.publish)
				myShipData.setReleaseTime("");
			else
				myShipData.setReleaseTime(CalendarUtil.toYYYY_MM_DD(ship.getReleaseTime()));

			myShipData.setTypeData(typeService.getTypeData(ship.getShipType()));

			UserData masterData = userService.getUserData(ship.getMasterId());
			myShipData.setMaster(masterData);

			return myShipData;
		}
		return null;
	}

	public List<ShipAttaData> getMyShipAttaDatas(Long myShipId) {
		List<ShipAttaData> myShipAttaDatas = new ArrayList<ShipAttaData>();
		List<YydShipAtta> shipAttas = shipAttaDao.findByMyShipId(myShipId);
		if (!shipAttas.isEmpty()) {
			for (YydShipAtta shipAtta : shipAttas) {
				ShipAttaData myShipAttaData = new ShipAttaData();
				CopyUtil.copyProperties(myShipAttaData, shipAtta);
				myShipAttaData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(shipAtta.getCreateTime()));
				myShipAttaDatas.add(myShipAttaData);
			}
			return myShipAttaDatas;
		}
		return null;

	}

	private boolean valid(UserData currUserData, ShipData myShipData) {
		// 用户未登录,不能操作
		if (currUserData == null)
			return false;

		// 未找到船舶不能操作
		if (myShipData == null)
			return false;

		// 只允许编辑未发布的船舶
		if (myShipData.getShipStatus() == ShipStatusCode.publish)
			return false;

		// 非船东自己的船舶不能进行此项操作
		if (!myShipData.getMasterId().equals(currUserData.getId()))
			return false;

		return true;
	}

	private boolean uniqueMmsi(ShipData myShipData) {
		return true;
		/*
		 * if (shipDao.findByMmsi(myShipData.getMmsi()) != null) return false;
		 * else return true;
		 */
	}

	private boolean uniqueShipName(ShipData myShipData) {
		return true;
		/*
		 * if (shipDao.getShipByShipName(myShipData.getShipName()) != null)
		 * return false; else return true;
		 */
	}

	public Long saveOrUpdate(UserData userData, ShipData myShipData) {
		try {
			// 只允许代理人或承运人操作自己的未发布的船舶
			if (!this.valid(userData, myShipData))
				return -1L;

			YydShip myShip = shipDao.get(myShipData.getId());
			// 首次添加
			if (myShip == null) {
				if (!this.uniqueMmsi(myShipData))
					return -1L;
				if (!this.uniqueShipName(myShipData))
					return -1L;

				myShip = new YydShip();

				myShip.setShipType(myShipData.getShipType());
				myShip.setShipCode(shipDao.getSpcShipCode(myShipData.getShipType()));

				myShip.setShipStatus(ShipStatusCode.audit);
				myShip.setCreateTime(myShipData.getCreateTime());
			} else {
				if (!myShip.getMmsi().equals(myShipData.getMmsi())) {
					if (!this.uniqueMmsi(myShipData))
						return -1L;
				}
				if (!myShip.getShipName().equals(myShipData.getShipName())) {
					if (!this.uniqueShipName(myShipData))
						return -1L;
				}
				if (!myShip.getShipType().equals(myShipData.getShipType())) {
					// 修改类别
					myShip.setShipType(myShipData.getShipType());
					String oldShipCode = myShipData.getShipCode();
					String newShipCode = shipDao.getSpcShipCode(myShipData.getShipType());
					myShip.setShipCode(newShipCode);

					// 批删除船舶动态属性
					shipAttrDao.batchDelete(oldShipCode);
				}
			}
			myShip.setMasterId(myShipData.getMasterId());

			myShip.setShipName(myShipData.getShipName());
			myShip.setEnglishName(myShipData.getEnglishName());
			myShip.setMmsi(myShipData.getMmsi());

			myShip.setMouldedDepth(myShipData.getMouldedDepth());

			myShip.setImo(myShipData.getImo());
			myShip.setCallsign(myShipData.getCallsign());
			myShip.setLength(myShipData.getLength());
			myShip.setBreadth(myShipData.getBreadth());
			myShip.setDraught(myShipData.getDraught());
			myShip.setSumTons(myShipData.getSumTons());
			myShip.setCleanTons(myShipData.getCleanTons());
			myShip.setaTons(myShipData.getaTons());
			myShip.setbTons(myShipData.getbTons());
			myShip.setFullContainer(myShipData.getFullContainer());
			myShip.setHalfContainer(myShipData.getHalfContainer());
			myShip.setSpaceContainer(myShipData.getSpaceContainer());
			myShip.setShipMaster(myShipData.getShipMaster());

			myShip.setKeyWords(filterWordsService.removeFilterWords(myShipData.getKeyWords()));
			myShip.setShipTitle(filterWordsService.removeFilterWords(myShipData.getShipTitle()));

			// 如果上传了Logo
			MultipartFile shipLogoFile = myShipData.getShipLogoFile();
			String shipLogoUrl = saveFile(myShip, shipLogoFile, myShip.getShipLogo(), "Ship");
			myShip.setShipLogo(shipLogoUrl);

			if (shipLogoUrl != null && !"".equals(shipLogoUrl)) {
				String realPath = Constants.SHARE_DIR;

				File f = new File(realPath + shipLogoUrl);
				if (!f.exists())
					shipLogoUrl = "/default/ship/" + myShip.getShipType() + ".jpg";

				int p = shipLogoUrl.lastIndexOf(".");
				if (p < 0)
					p = shipLogoUrl.length();

				String ext = "";
				if (p > 0)
					ext = shipLogoUrl.substring(p).toLowerCase();

				String lineRentLogo = realPath + shipLogoUrl.substring(0, p) + "-lineRent" + ext;
				String timeRentLogo = realPath + shipLogoUrl.substring(0, p) + "-timeRent" + ext;
				String waterImg = realPath + "/default/ship/timeRent.jpg";

				ImageUtil.makeShipLogo(realPath + shipLogoUrl, lineRentLogo, timeRentLogo, waterImg);
			}

			shipDao.save(myShip);

			myShipData.setId(myShip.getId());

			return myShip.getId();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0L;
		}
	}

	public Long saveAudit(UserData selfData, ShipData myShipData) {
		try {
			// 只允许代理人或承运人操作自己的未发布的船舶
			if (!this.valid(selfData, myShipData))
				return -1L;

			YydShip myShip = shipDao.get(myShipData.getId());
			if (myShip == null)
				return -1L;

			myShip.setWarrantType(myShipData.getWarrantType());

			MultipartFile idCardFrontFile = myShipData.getIdCardFrontFile();
			String idCardFrontUrl = saveFile(myShip, idCardFrontFile, myShip.getIdCardFront(), "IdCardFront");
			if (idCardFrontUrl != null && !idCardFrontUrl.equals(myShip.getIdCardFront()))
				myShip.setShipStatus(ShipStatusCode.edit);
			myShip.setIdCardFront(idCardFrontUrl);

			MultipartFile idCardBackFile = myShipData.getIdCardBackFile();
			String idCardBackUrl = saveFile(myShip, idCardBackFile, myShip.getIdCardBack(), "IdCardBack");
			if (idCardBackUrl != null && !idCardBackUrl.equals(myShip.getIdCardBack()))
				myShip.setShipStatus(ShipStatusCode.edit);
			myShip.setIdCardBack(idCardBackUrl);

			MultipartFile certificateFile = myShipData.getCertificateFile();
			String certificateUrl = saveFile(myShip, certificateFile, myShip.getCertificate(), "Certificate");
			if (certificateUrl != null && !certificateUrl.equals(myShip.getCertificate()))
				myShip.setShipStatus(ShipStatusCode.edit);
			myShip.setCertificate(certificateUrl);

			MultipartFile warrantFile = myShipData.getWarrantFile();
			String warrantUrl = saveFile(myShip, warrantFile, myShip.getWarrant(), "Warrant");
			if (warrantUrl != null && !warrantUrl.equals(myShip.getWarrant()))
				myShip.setShipStatus(ShipStatusCode.edit);
			myShip.setWarrant(warrantUrl);

			shipDao.save(myShip);

			return myShip.getId();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0L;
		}
	}

	// 检查上传的LOGO图片格式和图片大小
	public boolean checkUploadFile(ShipData myShipData) {
		return this.checkUploadFile(myShipData.getShipLogoFile(), myShipData.getShipLogo());
	}

	// 检查上传的论证资料图片格式和图片大小
	public boolean checkAuditFile(ShipData myShipData) {
		return this.checkUploadFile(myShipData.getIdCardFrontFile(), myShipData.getIdCardFront())
				& this.checkUploadFile(myShipData.getIdCardBackFile(), myShipData.getIdCardBack())
				& this.checkUploadFile(myShipData.getCertificateFile(), myShipData.getCertificate())
				& this.checkUploadFile(myShipData.getWarrantFile(), myShipData.getWarrant());
	}

	private boolean checkUploadFile(MultipartFile multipartFile, String url) {
		try {
			if (multipartFile != null && !multipartFile.isEmpty()) {
				String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
				if (ImgTypeCode.contains(ext) && multipartFile.getSize() <= Long.parseLong(Constants.IconSize)) {
					// 如果上传了文件且文件格式和大小正确则返回true
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return false;
		}
	}

	// 保存文件
	private String saveFile(YydShip myShip, MultipartFile mpf, String oldPathFile, String prefix) throws Exception {
		if (mpf != null && !mpf.isEmpty()) {
			// 取得文件后缀
			String ext = FilenameUtils.getExtension(mpf.getOriginalFilename());
			// 判断文件格式
			if (ImgTypeCode.contains(ext)) {
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getUserDir(myShip.getMasterId())
						+ ShareDirService.getShipDir(myShip.getMmsi());

				String url = MultipartUtil.uploadFile(mpf, realPath, relativePath, prefix);
				// 返回新文件路径
				return url;
			}
		}
		return oldPathFile;
	}

	public boolean saveMyShipDetail(UserData selfData, ShipAttaData myShipAttaData) {
		try {
			ShipData myShipData = this.getShipData(myShipAttaData.getShipId());

			// 只允许代理人或承运人操作自己的未发布的船舶
			if (!this.valid(selfData, myShipData))
				return false;

			YydShipAtta myShipAtta = new YydShipAtta();

			myShipAtta.setShipId(myShipAttaData.getShipId());
			myShipAtta.setNo(shipAttaDao.getImageNo(myShipAttaData.getShipId()));
			myShipAtta.setTitle(filterWordsService.removeFilterWords(myShipAttaData.getTitle()));
			myShipAtta.setCreateTime(Calendar.getInstance());
			if (myShipAttaData.getUrl() != null && !"".equals(myShipAttaData.getUrl())) {
				myShipAtta.setImgType(myShipAttaData.getImgType());
				myShipAtta.setUrl(myShipAttaData.getUrl());
				myShipAtta.setSize(myShipAttaData.getSize());
			}
			// 如果上传了Image
			MultipartFile mpf = myShipAttaData.getShipImageFile();
			if (mpf != null && !mpf.isEmpty()) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getUserDir(myShipData.getMasterId())
						+ ShareDirService.getShipDir(myShipData.getMmsi());
				String prefix = "ShipAtta";
				String url = MultipartUtil.uploadFile(mpf, realPath, relativePath, prefix);

				File newF = new File(realPath + url);
				if (newF.exists()) {
					String ext = FilenameUtils.getExtension(mpf.getOriginalFilename());

					myShipAtta.setImgType(ImgTypeCode.valueOf(ext.toLowerCase()));
					myShipAtta.setUrl(url);
					myShipAtta.setSize(newF.length());
				}
			}

			shipAttaDao.save(myShipAtta);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean saveDeliveryInfo(UserData userData, Long shipId, String[] cargoTypes, String[] cityorports) {
		try {
			ShipData myShipData = this.getShipData(shipId);
			// 只允许代理人或承运人操作自己的未发布的船舶
			if (!this.valid(userData, myShipData))
				return false;

			// 保存接货类别
			boolean result1 = shipCargoTypeService.saveCargoTypes(shipId, cargoTypes);
			// 保存接货港口
			boolean result2 = shipPortService.saveShipPorts(shipId, cityorports);

			if (result1 == true && result2 == true)
				return true;
			else
				return false;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean moveAttaUp(UserData selfData, Long attaId) {
		try {
			YydShipAtta shipAtta = shipAttaDao.get(attaId);
			if (shipAtta != null) {
				ShipData myShipData = this.getShipData(shipAtta.getShipId());
				// 只允许代理人或承运人操作自己的未发布的船舶
				if (!this.valid(selfData, myShipData))
					return false;

				YydShipAtta prevAtta = null, nextAtta = null;
				List<YydShipAtta> shipAttas = shipAttaDao.findByMyShipId(shipAtta.getShipId());
				for (int i = 0; i < shipAttas.size(); i++) {
					YydShipAtta atta = shipAttas.get(i);
					if (shipAtta.getId().equals(atta.getId())) {
						nextAtta = atta;
						break;
					} else {
						prevAtta = atta;
					}
				}
				// 若让第一个前移,则不需处理
				if (prevAtta != null) {
					Integer tNo = prevAtta.getNo();

					prevAtta.setNo(nextAtta.getNo());
					shipAttaDao.save(prevAtta);

					nextAtta.setNo(tNo);
					shipAttaDao.save(nextAtta);
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean moveAttaDown(UserData selfData, Long attaId) {
		try {
			YydShipAtta shipAtta = shipAttaDao.get(attaId);
			if (shipAtta != null) {
				ShipData myShipData = this.getShipData(shipAtta.getShipId());
				// 只允许代理人或承运人操作自己的未发布的船舶
				if (!this.valid(selfData, myShipData))
					return false;

				YydShipAtta prevAtta = null, nextAtta = null;
				List<YydShipAtta> shipAttas = shipAttaDao.findByMyShipId(shipAtta.getShipId());
				for (int i = shipAttas.size() - 1; i >= 0; i--) {
					YydShipAtta atta = shipAttas.get(i);
					if (shipAtta.getId().equals(atta.getId())) {
						prevAtta = atta;
						break;
					} else {
						nextAtta = atta;
					}
				}
				// 若让最后一个后移,则不需处理
				if (nextAtta != null) {
					Integer tNo = prevAtta.getNo();

					prevAtta.setNo(nextAtta.getNo());
					shipAttaDao.save(prevAtta);

					nextAtta.setNo(tNo);
					shipAttaDao.save(nextAtta);
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean removeShipAtta(UserData selfData, Long attaId) {
		try {
			YydShipAtta myShipAtta = shipAttaDao.get(attaId);

			if (myShipAtta != null) {
				ShipData myShipData = this.getShipData(myShipAtta.getShipId());
				// 只允许代理人或承运人操作自己的未发布的船舶
				if (!this.valid(selfData, myShipData))
					return false;

				// 如果存在船舶图片文件删除之
				String realPath = Constants.SHARE_DIR;
				String shipPathFile = myShipAtta.getUrl();
				File attaF = new File(realPath + shipPathFile);
				if (attaF.exists())
					attaF.delete();

				// 删除船舶记录
				shipAttaDao.delete(myShipAtta);

				return true;
			}
			return false;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean commitMyShip(UserData selfData, Long id) {
		try {
			ShipData myShipData = this.getShipData(id);
			// 只允许代理人或承运人操作自己的未发布的船舶
			if (!this.valid(selfData, myShipData))
				return false;

			YydShip myShip = shipDao.get(id);

			if (myShip.getShipStatus() == ShipStatusCode.edit) {
				myShip.setShipStatus(ShipStatusCode.commit);
				myShip.setReleaseTime(Calendar.getInstance());
				shipDao.save(myShip);
			}

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean publishMyShip(UserData selfData, Long id) {
		try {
			YydShip myShip = shipDao.get(id);
			ShipData myShipData = this.getShipData(myShip);
			// 只允许代理人或承运人操作自己的未发布的船舶
			if (!this.valid(selfData, myShipData))
				return false;

			if (myShip.getShipStatus() == ShipStatusCode.audit) {
				myShip.setShipStatus(ShipStatusCode.publish);
				myShip.setReleaseTime(Calendar.getInstance());
				shipDao.save(myShip);
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean unpublishMyShip(UserData selfData, Long id) {
		try {
			YydShip myShip = shipDao.get(id);

			if (myShip.getShipStatus() == ShipStatusCode.publish) {
				myShip.setShipStatus(ShipStatusCode.audit);
				myShip.setReleaseTime(Calendar.getInstance());
				shipDao.save(myShip);
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteShip(UserData selfData, Long shipId) {
		try {
			ShipData myShipData = this.getShipData(shipId);
			// 只允许代理人或承运人操作未发布的船舶
			if (!this.valid(selfData, myShipData))
				return false;
			// 有合同关联，不允许删除
			if (!orderCommonDao.getByShipId(shipId).isEmpty())
				return false;

			return this.deleteShip(shipId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean saveSortInfos(UserData selfData, ShipData myShipData, List<AttrNameData> attrNameDatas,
			Map<String, Object> mapParam) {
		try {
			// 只允许代理人或承运人操作自己的未发布的船舶
			if (!this.valid(selfData, myShipData))
				return false;

			shipAttrService.batchExecute("delete from YydShipAttr where shipCode = ? ", myShipData.getShipCode());

			for (AttrNameData attrNameData : attrNameDatas) {
				YydShipAttr shipAttr = new YydShipAttr();

				shipAttr.setShipCode(myShipData.getShipCode());
				shipAttr.setAttrNameCode(attrNameData.getAttrNameCode());

				Object o = mapParam.get("a" + attrNameData.getAttrNameCode());

				switch (attrNameData.getAttrType()) {
				case charcode:
					shipAttr.setAttrValue((String) o);
					break;
				case charstr:
					shipAttr.setAttrValue((String) o);
					break;
				case intnum:
					shipAttr.setAttrValue(Integer.toString((Integer) o));
					break;
				case dblnum:
					shipAttr.setAttrValue(Double.toString((Double) o));
					break;
				case booltype:
					shipAttr.setAttrValue((String) o);
					break;
				case datetype:
					shipAttr.setAttrValue((String) o);
					break;
				}

				shipAttrService.save(shipAttr);
			}

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public List<EvaluateData> getEvaluaDatas(Long shipId) {
		List<YydOrderEvaluate> yydEvaluates = evaluateDao.getByShipId(shipId, PAGE_SIZE);
		List<EvaluateData> evaluaDatas = new ArrayList<EvaluateData>();
		if (!yydEvaluates.isEmpty() && yydEvaluates != null) {
			for (YydOrderEvaluate yydEvaluate : yydEvaluates) {
				EvaluateData evaluateData = new EvaluateData();
				CopyUtil.copyProperties(evaluateData, yydEvaluate);
				evaluateData.setUserData(userService.getById(yydEvaluate.getUserId()));
				evaluateData.setCreateTime(CalendarUtil.toYYYY_MM_DD(yydEvaluate.getCreateTime()));
				evaluaDatas.add(evaluateData);
			}
		}
		return evaluaDatas;
	}

	public boolean isRightFormat(MultipartFile shipExcelFile, File modelFile) {
		try {
			if (shipExcelFile == null || shipExcelFile.isEmpty())
				return false;

			Workbook rwb = Workbook.getWorkbook(shipExcelFile.getInputStream());
			Workbook modelrwb = Workbook.getWorkbook(modelFile);

			Cell[] modelrow = modelrwb.getSheets()[0].getRow(0);
			Cell[] row = rwb.getSheet(0).getRow(0);

			// 校验格式
			for (int j = 0; j < row.length; j++) {
				if (!modelrow[j].getContents().equals(row[j].getContents())) {
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}

	}

	private String getCellValue(Cell[] cells, int index) {
		int arrayLength = cells.length;
		if (index < arrayLength)
			return cells[index].getContents().trim();
		else
			return "";
	}

	public boolean importShipExcel(UserData userData, String urlFile) {
		try {
			File shipExcelFile = new File(urlFile);
			if (!shipExcelFile.exists())
				return false;

			jxl.Workbook rwb = Workbook.getWorkbook(shipExcelFile);
			Sheet rs = rwb.getSheet(0);

			for (int j = 1; j < rs.getRows(); j++) {
				Cell[] cells = rs.getRow(j);

				if (cells != null && cells.length > 1) {
					String mmsi = this.getCellValue(cells, 5);// MMSI编号
					String shipName = this.getCellValue(cells, 3); // 船舶名称
					if (shipName.equals("") || mmsi.equals(""))
						continue;

					// String masterName = this.getCellValue(cells, 2);// 船东姓名

					ShipData shipData = new ShipData();

					// 校验mmsi唯一性
					shipData.setMmsi(mmsi);
					if (!this.uniqueMmsi(shipData))
						continue;

					// 校验船名唯一性
					shipData.setShipName(shipName);
					if (!this.uniqueShipName(shipData))
						continue;

					String shipTypeName = "货船";
					TypeData typeData = typeService.getTypeDataByName(shipTypeName);

					String englishName = Cn2Spell.converterToSpell(shipName); // 船舶英文名称
					String shipLenth = this.getCellValue(cells, 6); // 船长
					String shipBreadth = this.getCellValue(cells, 7);// 船宽
					String shipMouldedDepth = this.getCellValue(cells, 8);// 型深
					String shipDraught = this.getCellValue(cells, 9);// 吃水深度
					String shipSumTons = this.getCellValue(cells, 10);// 总吨
					String shipCleanTons = this.getCellValue(cells, 11);// 净吨
					String shipFullContainer = this.getCellValue(cells, 12);// 载重(吨)
					String shipHalfContainer = this.getCellValue(cells, 13);// 载箱(TEU)

					Double length = ("".equals(shipLenth)) ? 0D : Double.parseDouble(shipLenth);
					Double breadth = ("".equals(shipBreadth)) ? 0D : Double.parseDouble(shipBreadth);
					Double mouldedDepth = ("".equals(shipMouldedDepth)) ? 0D : Double.parseDouble(shipMouldedDepth);
					Double draught = ("".equals(shipDraught)) ? 0D : Double.parseDouble(shipDraught);
					Integer sumTons = ("".equals(shipSumTons)) ? 0 : Integer.parseInt(shipSumTons);
					Integer cleanTons = ("".equals(shipCleanTons)) ? 0 : Integer.parseInt(shipCleanTons);
					Integer fullContainer = ("".equals(shipFullContainer)) ? 0 : Integer.parseInt(shipFullContainer);
					Integer halfContainer = ("".equals(shipHalfContainer)) ? 0 : Integer.parseInt(shipHalfContainer);

					shipData.setShipType(typeData.getTypeCode());
					shipData.setMasterId(userData.getId());
					shipData.setEnglishName(englishName);
					shipData.setLength(length);
					shipData.setBreadth(breadth);
					shipData.setMouldedDepth(mouldedDepth);
					shipData.setDraught(draught);
					shipData.setSumTons(sumTons);
					shipData.setCleanTons(cleanTons);
					shipData.setFullContainer(fullContainer);
					shipData.setHalfContainer(halfContainer);

					// 保存
					Long shipId = this.saveOrUpdate(userData, shipData);
					// 发布
					this.publishMyShip(userData, shipId);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	// 导出所有船舶登记列表中的船舶
	public boolean exportShipExcel(File file, List<ShipData> myShipDatas) {
		try {
			Workbook workbook = Workbook.getWorkbook(file);
			WritableWorkbook wb = Workbook.createWorkbook(file, workbook);
			WritableSheet sheet = wb.getSheet(0);

			for (int k = sheet.getRows() - 1; k > 0; k--) {
				sheet.removeRow(k);
			}

			for (int i = 0; i < myShipDatas.size(); i++) {
				sheet.addCell(new Label(0, i + 1, i + 1 + "")); // 序号

				sheet.addCell(new Label(1, i + 1, "")); // 组别需要改为部门
				sheet.addCell(new Label(2, i + 1, myShipDatas.get(i).getMaster().getTrueName())); // 船东
				sheet.addCell(new Label(3, i + 1, myShipDatas.get(i).getShipName())); // 船舶中文名
				sheet.addCell(new Label(4, i + 1, myShipDatas.get(i).getEnglishName())); // 船舶英文名
				sheet.addCell(new Label(5, i + 1, myShipDatas.get(i).getMmsi())); // 船舶MMSI
				sheet.addCell(new Label(6, i + 1, String.valueOf(myShipDatas.get(i).getLength()))); // 长
				sheet.addCell(new Label(7, i + 1, String.valueOf(myShipDatas.get(i).getBreadth()))); // 宽
				sheet.addCell(new Label(8, i + 1, String.valueOf(myShipDatas.get(i).getMouldedDepth()))); // 型深
				sheet.addCell(new Label(9, i + 1, String.valueOf(myShipDatas.get(i).getDraught()))); // 吃水深度
				sheet.addCell(new Label(10, i + 1, String.valueOf(myShipDatas.get(i).getSumTons()))); // 总吨
				sheet.addCell(new Label(11, i + 1, String.valueOf(myShipDatas.get(i).getCleanTons()))); // 净吨
				sheet.addCell(new Label(12, i + 1, String.valueOf(myShipDatas.get(i).getaTons()))); // 载重
				sheet.addCell(new Label(13, i + 1, String.valueOf(myShipDatas.get(i).getFullContainer()))); // 载箱
			}
			wb.write();
			wb.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}