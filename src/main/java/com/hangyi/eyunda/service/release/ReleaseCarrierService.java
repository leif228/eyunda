package com.hangyi.eyunda.service.release;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import com.hangyi.eyunda.dao.YydCargoInfoDao;
import com.hangyi.eyunda.dao.YydCarrierIssueDao;
import com.hangyi.eyunda.dao.YydShipDao;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.cargo.CargoData;
import com.hangyi.eyunda.data.release.CarrierIssueData;
import com.hangyi.eyunda.domain.YydCargoInfo;
import com.hangyi.eyunda.domain.YydCarrierIssue;
import com.hangyi.eyunda.domain.YydShip;
import com.hangyi.eyunda.domain.enumeric.ColumnCode;
import com.hangyi.eyunda.domain.enumeric.ImgTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.cargo.CargoService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MultipartUtil;
import com.hangyi.eyunda.util.StringUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ReleaseCarrierService extends BaseService<YydCarrierIssue, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydCarrierIssueDao carrierIssueDao;
	@Autowired
	private YydCargoInfoDao cargoDao;
	@Autowired
	private YydShipDao shipDao;

	@Autowired
	private UserService userService;
	@Autowired
	private MyShipService shipService;
	@Autowired
	private CargoService cargoService;

	@Override
	public PageHibernateDao<YydCarrierIssue, Long> getDao() {
		return (PageHibernateDao<YydCarrierIssue, Long>) carrierIssueDao;
	}

	// 取一条记录
	public CarrierIssueData getCarrierIssueData(YydCarrierIssue carrierIssue) {
		if (carrierIssue != null) {
			CarrierIssueData carrierIssueData = new CarrierIssueData();
			CopyUtil.copyProperties(carrierIssueData, carrierIssue);

			UserData userData = userService.getById(carrierIssue.getCarrierId());
			carrierIssueData.setUserData(userData);

			String createTime = CalendarUtil.toYYYY_MM_DD(carrierIssue.getCreateTime());
			carrierIssueData.setCreateTime(createTime);

			String releaseTime = "";

			if (carrierIssue.getColumnCode() == ColumnCode.CBXX) {
				YydShip ship = shipService.get(carrierIssue.getNo().longValue());
				if (ship != null) {
					carrierIssueData.setTypeImage("shipImage/" + ship.getShipType());
				}
			}

			if (carrierIssue.getColumnCode() == ColumnCode.HWXX) {
				YydCargoInfo cargo = cargoService.get(carrierIssue.getNo().longValue());
				if (cargo != null)
					carrierIssueData.setTypeImage("cargoImage/" + cargo.getCargoType());
			}

			if (carrierIssue.getReleaseStatus() == ReleaseStatusCode.publish)
				releaseTime = CalendarUtil.toYYYY_MM_DD(carrierIssue.getReleaseTime());
			carrierIssueData.setReleaseTime(releaseTime);

			return carrierIssueData;
		}
		return null;
	}

	// 取一条记录
	public CarrierIssueData getCarrierIssueData(Long id) {
		YydCarrierIssue carrierIssue = carrierIssueDao.get(id);
		return this.getCarrierIssueData(carrierIssue);
	}

	// 取一条记录
	public CarrierIssueData getCarrierIssueData(ColumnCode columnCode, Long saId) {
		CarrierIssueData cid = this.getCarrierIssueData(carrierIssueDao.getByColumnCode2(columnCode, saId));
		if (cid != null)
			cid.setContent(StringUtil.formatHTML(cid.getContent()));
		return cid;
	}

	// 分页记录
	public Page<CarrierIssueData> getReleaseType2(Page<CarrierIssueData> pageData, Long saId, ColumnCode selectCode) {
		Page<YydCarrierIssue> page = carrierIssueDao.getPageByColumnCode2(pageData, saId, selectCode);

		List<CarrierIssueData> carrierIssueDatas = new ArrayList<CarrierIssueData>();
		for (YydCarrierIssue yydCarrierIssue : page.getResult()) {
			CarrierIssueData carrierIssueData = this.getCarrierIssueData(yydCarrierIssue);
			carrierIssueData.setContent(StringUtil.formatHTML(carrierIssueData.getContent()));
			carrierIssueDatas.add(carrierIssueData);
			// 点击数
			if (pageData.getPageSize() == 1) {
				int num = yydCarrierIssue.getPointCount();
				num++;
				yydCarrierIssue.setPointCount(num);
				carrierIssueDao.save(yydCarrierIssue);
			}
		}

		pageData.setResult(carrierIssueDatas);

		return pageData;
	}

	// 分页记录
	public Page<CarrierIssueData> getReleaseType(Page<CarrierIssueData> pageData, Long saId, ColumnCode selectCode) {
		if (selectCode == null)
			selectCode = ColumnCode.GSJJ;

		Page<YydCarrierIssue> page = carrierIssueDao.getPageByColumnCode(pageData, saId, selectCode);

		List<CarrierIssueData> carrierIssueDatas = new ArrayList<CarrierIssueData>();
		for (YydCarrierIssue yydCarrierIssue : page.getResult()) {
			CarrierIssueData carrierIssueData = this.getCarrierIssueData(yydCarrierIssue);
			carrierIssueDatas.add(carrierIssueData);
		}

		pageData.setResult(carrierIssueDatas);

		return pageData;
	}

	// 保存、修改
	public void saveOrUpdate(CarrierIssueData carrierIssueData) throws Exception {
		try {
			if (carrierIssueData.getContent().getBytes().length > 16000) {
				throw new Exception("错误！内容过长或文件过大！");
			}
			YydCarrierIssue carrierIssue = carrierIssueDao.get(carrierIssueData.getId());
			if (carrierIssue == null)
				carrierIssue = new YydCarrierIssue();

			carrierIssue.setCarrierId(carrierIssueData.getCarrierId());
			carrierIssue.setColumnCode(carrierIssueData.getColumnCode());
			carrierIssue.setTitle(carrierIssueData.getTitle());
			carrierIssue.setSource(carrierIssueData.getSource());
			carrierIssue.setContent(carrierIssueData.getContent());
			carrierIssue.setReleaseStatus(carrierIssueData.getReleaseStatus());

			// 大图
			MultipartFile bigFile = carrierIssueData.getbImage();
			if (bigFile != null && !bigFile.isEmpty()) {
				String ext = FilenameUtils.getExtension(bigFile.getOriginalFilename());
				if (ImgTypeCode.contains(ext)) {
					// 拷贝新文件到指定位置
					String realPath = Constants.SHARE_DIR;
					String relativePath = ShareDirService.getUserDir(carrierIssueData.getCarrierId());
					String prefix = "bigImage";
					String url = MultipartUtil.uploadFile(bigFile, realPath, relativePath, prefix);
					// 修改数据库中文件路径
					carrierIssue.setBigImage(url);
				} else {
					throw new Exception("错误！上传文件的类型不符合要求！");
				}
			}
			// 小图
			MultipartFile smallFile = carrierIssueData.getsImage();
			if (smallFile != null && !smallFile.isEmpty()) {
				String ext = FilenameUtils.getExtension(smallFile.getOriginalFilename());
				if (ImgTypeCode.contains(ext)) {
					// 拷贝新文件到指定位置
					String realPath = Constants.SHARE_DIR;
					String relativePath = ShareDirService.getUserDir(carrierIssueData.getCarrierId());
					String prefix = "smallImage";
					String url = MultipartUtil.uploadFile(smallFile, realPath, relativePath, prefix);
					// 如果存在旧文件删除之
					String oldPathFile = carrierIssue.getSmallImage();
					File oldF = new File(realPath + oldPathFile);
					if (oldF.exists())
						oldF.delete();
					// 修改数据库中文件路径
					carrierIssue.setSmallImage(url);
				} else {
					throw new Exception("错误！上传文件的类型不符合要求！");
				}
			}
			carrierIssueDao.save(carrierIssue);
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
	}

	// 发布
	public boolean publish(Long id, Long saId) {
		YydCarrierIssue carrierIssue = carrierIssueDao.get(id);
		if (carrierIssue != null && carrierIssue.getCarrierId().longValue() == saId) {
			if (carrierIssue.getReleaseStatus() == ReleaseStatusCode.unpublish) {
				carrierIssue.setReleaseStatus(ReleaseStatusCode.publish);
				carrierIssue.setReleaseTime(CalendarUtil.now());
				carrierIssueDao.save(carrierIssue);
				return true;
			}
		}
		return false;
	}

	// 取消发布
	public boolean unpublish(Long id, Long saId) {
		YydCarrierIssue carrierIssue = carrierIssueDao.get(id);
		if (carrierIssue != null && carrierIssue.getCarrierId().longValue() == saId) {
			if (carrierIssue.getReleaseStatus() == ReleaseStatusCode.publish) {
				carrierIssue.setReleaseStatus(ReleaseStatusCode.unpublish);
				carrierIssueDao.save(carrierIssue);
				return true;
			}
		}
		return false;
	}

	// 删除
	public void delete(Long id, Long saId) {
		YydCarrierIssue carrierIssue = carrierIssueDao.get(id);
		if (carrierIssue != null && carrierIssue.getCarrierId().longValue() == saId) {
			if (carrierIssue.getReleaseStatus() == ReleaseStatusCode.unpublish) {
				carrierIssueDao.delete(carrierIssue);
			}
		}
	}

	public boolean needInit(Long saId) throws Exception {
		try {
			YydCarrierIssue ci = carrierIssueDao.getByColumnCode(ColumnCode.GSJJ, saId);
			return ci == null;
		} catch (Exception e) {
			throw new Exception("错误！站点内容初始化时出现错误。");
		}
	}

	public void init(Long compId, Long saId) throws Exception {
		try {
			this.initGSJJ(saId);
			this.initLXWM(saId);
			this.initCBXX(compId, saId);
			this.initHWXX(compId, saId);
		} catch (Exception e) {
			throw new Exception("错误！站点内容初始化时出现错误。");
		}
	}

	// 初始化我的信息
	public void initGSJJ(Long saId) throws Exception {
		try {
			UserData ud = userService.getById(saId);

			YydCarrierIssue ci = carrierIssueDao.getByColumnCode(ColumnCode.GSJJ, saId);
			if (ci == null) {
				YydCarrierIssue yydCarrierIssue = new YydCarrierIssue();

				yydCarrierIssue.setCarrierId(saId);
				yydCarrierIssue.setColumnCode(ColumnCode.GSJJ);
				yydCarrierIssue.setBigImage(ud.getUserLogo());
				yydCarrierIssue.setSmallImage(ud.getUserLogo());
				yydCarrierIssue.setTitle(ud.getTrueName());
				yydCarrierIssue.setContent(
						ud.getTrueName() + "\n" + ud.getAddress() + "\n" + ud.getMobile() + "\n" + ud.getEmail());
				yydCarrierIssue.setReleaseStatus(ReleaseStatusCode.publish);

				carrierIssueDao.save(yydCarrierIssue);
			}
		} catch (Exception e) {
			throw new Exception("错误！站点内容初始化时出现错误。");
		}
	}

	// 初始化船舶信息
	public void initLXWM(Long saId) throws Exception {
		try {
			UserData ud = userService.getById(saId);

			YydCarrierIssue carrierIssue = carrierIssueDao.getByColumnCode(ColumnCode.LXWM, saId);
			if (carrierIssue == null) {
				YydCarrierIssue carrierIssueData = new YydCarrierIssue();

				carrierIssueData.setCarrierId(ud.getId());
				carrierIssueData.setColumnCode(ColumnCode.LXWM);
				carrierIssueData.setNo(0);
				carrierIssueData.setTitle(ud.getTrueName());
				carrierIssueData.setSource(ud.getTrueName());
				carrierIssueData.setCreateTime(Calendar.getInstance());

				carrierIssueData.setContent(
						ud.getTrueName() + "\n" + ud.getAddress() + "\n" + ud.getMobile() + "\n" + ud.getEmail());

				carrierIssueData.setReleaseStatus(ReleaseStatusCode.publish);
				carrierIssueData.setReleaseTime(Calendar.getInstance());
				carrierIssueData.setPointCount(0);

				carrierIssueDao.save(carrierIssueData);
			}
		} catch (Exception e) {
			throw new Exception("错误！站点内容初始化时出现错误。");
		}
	}

	// 初始化船舶信息
	public void initCBXX(Long compId, Long saId) throws Exception {
		try {
			YydCarrierIssue lastCarrierIssue = carrierIssueDao.getByColumnCode(ColumnCode.CBXX, saId);
			if (lastCarrierIssue == null) {
				List<YydShip> yydShips = shipDao.getShipsByCompId(compId);
				for (YydShip yydShip : yydShips) {
					YydCarrierIssue issue = new YydCarrierIssue();

					issue.setCarrierId(saId);
					issue.setNo(yydShip.getId().intValue());
					issue.setTitle(yydShip.getShipName());
					issue.setContent(yydShip.getShipTitle());

					if (yydShip.getShipLogo() != null && !"".equals(yydShip.getShipLogo())) {
						issue.setBigImage(yydShip.getShipLogo());
						issue.setSmallImage(yydShip.getShipLogo());
					} else {
						issue.setBigImage("");
						issue.setSmallImage("");
					}

					issue.setColumnCode(ColumnCode.CBXX);
					issue.setReleaseStatus(ReleaseStatusCode.publish);

					carrierIssueDao.save(issue);
				}
			}
		} catch (Exception e) {
			throw new Exception("错误！站点内容初始化时出现错误。");
		}
	}

	// 初始化货物
	public void initHWXX(Long compId, Long saId) throws Exception {
		try {
			YydCarrierIssue lastCarrierIssue = carrierIssueDao.getByColumnCode(ColumnCode.HWXX, saId);

			if (lastCarrierIssue == null) {
				List<YydCargoInfo> ycis = cargoDao.getCargosByCompId(compId);
				for (YydCargoInfo cargoInfo : ycis) {
					CargoData cargoData = cargoService.getCargoData(cargoInfo);

					YydCarrierIssue carrierIssue = new YydCarrierIssue();

					carrierIssue.setCarrierId(saId);
					carrierIssue.setNo(cargoData.getId().intValue());
					carrierIssue.setTitle(cargoData.getCargoType().getDescription());
					carrierIssue.setContent(cargoData.getCargoType().getDescription());// cargoData.getDescription()

					if (cargoData.getCargoImage() != null && !"".equals(cargoData.getCargoImage())) {
						carrierIssue.setBigImage(cargoData.getCargoImage());
						carrierIssue.setSmallImage(cargoData.getCargoImage());
					} else {
						carrierIssue.setBigImage("");
						carrierIssue.setSmallImage("");
					}

					carrierIssue.setColumnCode(ColumnCode.HWXX);
					carrierIssue.setReleaseStatus(ReleaseStatusCode.publish);

					carrierIssueDao.save(carrierIssue);
				}
			}
		} catch (Exception e) {
			throw new Exception("错误！站点内容初始化时出现错误。");
		}
	}

	// 取得当前栏目集合列表
	public List<CarrierIssueData> getByColumn(Long saId, ColumnCode selectCode) {
		List<YydCarrierIssue> carrierIssues = carrierIssueDao.getAll(saId, selectCode);

		List<CarrierIssueData> carrierIssueDatas = new ArrayList<CarrierIssueData>();

		if (carrierIssues != null) {
			for (YydCarrierIssue carrierIssue : carrierIssues) {
				CarrierIssueData carrierIssueData = this.getCarrierIssueData(carrierIssue);
				carrierIssueDatas.add(carrierIssueData);
			}
		}

		return carrierIssueDatas;
	}

	// 删除集合列表
	public void deleteList(List<CarrierIssueData> carrierIssueDatas) {
		if (carrierIssueDatas != null) {
			for (CarrierIssueData carrierIssueData : carrierIssueDatas) {
				carrierIssueDao.delete(carrierIssueData.getId());
			}
		}
	}

	// 刷新
	public void refresh(Long compId, Long saId) throws Exception {
		try {
			List<CarrierIssueData> cbIssues = this.getByColumn(saId, ColumnCode.CBXX);
			List<CarrierIssueData> hwIssues = this.getByColumn(saId, ColumnCode.HWXX);

			deleteList(cbIssues);
			deleteList(hwIssues);

			this.initCBXX(compId, saId);
			this.initHWXX(compId, saId);

		} catch (Exception e) {
			throw new Exception("错误！刷新出现异常。");
		}
	}

}
