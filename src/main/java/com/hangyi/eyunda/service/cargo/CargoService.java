package com.hangyi.eyunda.service.cargo;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.cargo.CargoData;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.domain.YydCargoInfo;
import com.hangyi.eyunda.domain.enumeric.CargoBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ImgTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.PortService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MultipartUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class CargoService extends BaseService<YydCargoInfo, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydCargoInfoDao cargoDao;
	@Autowired
	private PortService portService;
	@Autowired
	private UserService userService;

	@Override
	public PageHibernateDao<YydCargoInfo, Long> getDao() {
		return (PageHibernateDao<YydCargoInfo, Long>) cargoDao;
	}

	public boolean valid(UserData currUserData, CargoData cargoData) {
		// 用户未登录,不能操作
		if (currUserData == null)
			return false;

		// 未找到货物不能操作
		if (cargoData == null)
			return false;

		if (cargoData.getPublisherId().equals(currUserData.getId()))
			return true;

		return false;
	}

	public Page<CargoData> getMySelfCargos(UserData userData, Page<CargoData> pageCargo, String keyWords,
			String startDate, String endDate) {
		List<CargoData> cargoDatas = new ArrayList<CargoData>();

		Page<YydCargoInfo> page = cargoDao.findByPublisherId(userData.getId(), pageCargo.getPageNo(),
				pageCargo.getPageSize(), keyWords, startDate, endDate);

		for (YydCargoInfo cargo : (List<YydCargoInfo>) page.getResult()) {
			CargoData cargoData = this.getCargoData(cargo);
			cargoDatas.add(cargoData);
		}

		CopyUtil.copyProperties(pageCargo, page);
		pageCargo.setResult(cargoDatas);

		return pageCargo;
	}

	public Long saveOrUpdate(UserData currUserData, CargoData cargoData) {
		try {
			YydCargoInfo cargoInfo = cargoDao.get(cargoData.getId());

			if (cargoInfo == null) {
				cargoInfo = new YydCargoInfo();
			} else {
				if (!this.valid(currUserData, cargoData)) {
					throw new Exception("警告！你不能共享、修改或删除别人发布的货盘！");
				}
			}

			CopyUtil.copyProperties(cargoInfo, cargoData, new String[] { "cargoImage" });
			cargoInfo.setCreateTime(CalendarUtil.now());

			if (cargoData.getCargoImage() != null && !"".equals(cargoData.getCargoImage()))
				cargoInfo.setCargoImage(cargoData.getCargoImage());

			// 如果上传了货物图片
			MultipartFile cargoImage = cargoData.getCargoImageFile();
			if (cargoImage != null && !cargoImage.isEmpty()) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getCargoDir(cargoData.getPublisherId());
				String prefix = "Cargo";
				String url = MultipartUtil.uploadFile(cargoImage, realPath, relativePath, prefix);
				// 修改数据库中文件路径
				cargoInfo.setCargoImage(url);
			}

			cargoDao.save(cargoInfo);

			cargoData.setId(cargoInfo.getId());

			return cargoInfo.getId();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0L;
		}
	}

	public boolean deleteCargo(UserData userData, Long cargoId) {
		try {
			YydCargoInfo cargoInfo = cargoDao.get(cargoId);
			CargoData cargoData = this.getCargoData(cargoInfo);

			if (!this.valid(userData, cargoData))
				throw new Exception("错误！该货物你无权操作。");

			if (cargoInfo != null) {
				// 如果存在旧文件删除之
				String realPath = Constants.SHARE_DIR;
				String oldPathFile = cargoInfo.getCargoImage();
				File oldF = new File(realPath + oldPathFile);
				if (oldF.exists())
					oldF.delete();

				// 删除货物信息
				cargoDao.delete(cargoInfo);
				return true;
			} else
				return false;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public CargoData getCargoData(Long id) {
		YydCargoInfo cargo = cargoDao.get(id);
		if (cargo != null)
			return this.getCargoData(cargo);
		else
			return null;
	}

	public CargoData getCargoData(YydCargoInfo cargo) {
		if (cargo != null) {
			CargoData cargoData = new CargoData();
			CopyUtil.copyProperties(cargoData, cargo);

			// 如果不存在货物logo文件
			String realPath = Constants.SHARE_DIR;
			String logoPathFile = cargoData.getCargoImage();
			if (logoPathFile == null || "".equals(logoPathFile) || !new File(realPath + logoPathFile).exists()) {
				cargoData.setCargoImage("/default/cargo/" + cargoData.getCargoType().toString() + ".jpg");
			}

			UserData publisher = userService.getUserData(cargo.getPublisherId());
			cargoData.setPublisher(publisher);

			PortData startPortData = portService.getPortData(cargo.getStartPortNo());
			cargoData.setStartPortData(startPortData);
			PortData endPortData = portService.getPortData(cargo.getEndPortNo());
			cargoData.setEndPortData(endPortData);

			cargoData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(cargo.getCreateTime()));

			if (cargoData.getCargoType().getCargoBigType() == CargoBigTypeCode.container) {
				List<CargoTypeCode> ctcs = CargoTypeCode.getCodesByCargoBigType(CargoBigTypeCode.container);

				String[] cargoNames = cargoData.getCargoNames().split(",");
				String[] tonTeus = cargoData.getTonTeus().split(",");
				String[] prices = cargoData.getPrices().split(",");

				Map<Integer, String> mapCargoNames = new TreeMap<Integer, String>();
				Map<Integer, Integer> mapTonTeus = new TreeMap<Integer, Integer>();
				Map<Integer, Double> mapPrices = new TreeMap<Integer, Double>();

				int n = 0;
				for (CargoTypeCode ctc : ctcs) {
					mapCargoNames.put(ctc.ordinal(), cargoNames[n]);
					mapTonTeus.put(ctc.ordinal(), Integer.parseInt(tonTeus[n]));
					mapPrices.put(ctc.ordinal(), Double.parseDouble(prices[n]));
					n++;
				}

				cargoData.setMapCargoNames(mapCargoNames);
				cargoData.setMapTonTeus(mapTonTeus);
				cargoData.setMapPrices(mapPrices);
			} else {
				CargoTypeCode ctc = cargoData.getCargoType();

				String cargoNames = cargoData.getCargoNames();
				String tonTeus = cargoData.getTonTeus();
				String prices = cargoData.getPrices();

				Map<Integer, String> mapCargoNames = new TreeMap<Integer, String>();
				Map<Integer, Integer> mapTonTeus = new TreeMap<Integer, Integer>();
				Map<Integer, Double> mapPrices = new TreeMap<Integer, Double>();

				mapCargoNames.put(ctc.ordinal(), cargoNames);
				mapTonTeus.put(ctc.ordinal(), Integer.parseInt(tonTeus));
				mapPrices.put(ctc.ordinal(), Double.parseDouble(prices));

				cargoData.setMapCargoNames(mapCargoNames);
				cargoData.setMapTonTeus(mapTonTeus);
				cargoData.setMapPrices(mapPrices);
			}

			return cargoData;
		}
		return null;
	}

	// 检查上传的图片格式和图片大小
	public boolean checkUploadFile(CargoData cargoData) {
		boolean flag = true;
		try {
			flag = this.checkUploadFile(cargoData.getCargoImageFile());
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return flag;
	}

	// 检查上传文件的格式和大小
	private boolean checkUploadFile(MultipartFile multipartFile) {
		try {
			// 如果上传了文件且格式和大小正确就返回true
			if (multipartFile != null && !multipartFile.isEmpty()) {
				String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
				if (ImgTypeCode.contains(ext) && multipartFile.getSize() <= Long.parseLong(Constants.IconSize)) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		// 如果没有上传文件直接返回true，因为可以不用上传该文件
		return true;
	}

	public boolean publishMyCargo(UserData userData, Long cargoId) {
		try {
			YydCargoInfo cargoInfo = cargoDao.get(cargoId);
			CargoData cargoData = this.getCargoData(cargoInfo);

			if (!this.valid(userData, cargoData))
				throw new Exception("错误！该货物你无权操作。");

			if (cargoData.getStatus() == ReleaseStatusCode.unpublish) {
				cargoInfo.setStatus(ReleaseStatusCode.publish);
				cargoInfo.setCreateTime(Calendar.getInstance());
				cargoDao.save(cargoInfo);
				return true;
			} else {
				throw new Exception("错误！已经发布过了，无需再发布。");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean unpublishMyCargo(UserData userData, Long cargoId) {
		try {
			YydCargoInfo cargoInfo = cargoDao.get(cargoId);
			CargoData cargoData = this.getCargoData(cargoInfo);

			if (!this.valid(userData, cargoData))
				throw new Exception("错误！该货物你无权操作。");

			if (cargoData.getStatus() == ReleaseStatusCode.publish) {
				cargoInfo.setStatus(ReleaseStatusCode.unpublish);
				cargoInfo.setCreateTime(Calendar.getInstance());
				cargoDao.save(cargoInfo);
				return true;
			} else {
				throw new Exception("错误！已经取消发布，无需再取消。");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Page<CargoData> findCargoPage(Page<CargoData> pageData, String keyWords, String startDate, String endDate) {
		Page<YydCargoInfo> page = cargoDao.findCargoPage(pageData.getPageSize(), pageData.getPageNo(), keyWords,
				startDate, endDate);

		List<CargoData> cargoDatas = new ArrayList<CargoData>();
		for (YydCargoInfo cargoInfo : (List<YydCargoInfo>) page.getResult()) {
			cargoDatas.add(this.getCargoData(cargoInfo));
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(cargoDatas);

		return pageData;
	}

	public boolean deleteCargos(UserData userData) {
		Page<CargoData> pageCargo = new Page<CargoData>();
		pageCargo.setPageNo(1);
		pageCargo.setPageSize(Constants.ALL_SIZE);

		boolean b = true;
		Page<CargoData> cargoPage = this.getMySelfCargos(userData, pageCargo, null, null, null);
		if (!cargoPage.getResult().isEmpty()) {
			for (CargoData cargoData : cargoPage.getResult()) {
				b = this.deleteCargo(userData, cargoData.getId());
				if (!b) {
					return b;
				}
			}
		}
		return b;
	}

}
