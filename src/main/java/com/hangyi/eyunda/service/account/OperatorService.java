package com.hangyi.eyunda.service.account;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.event.OnlineStatusCode;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydOperatorDao;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.OperatorData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.account.UserPrivilegeData;
import com.hangyi.eyunda.domain.YydOperator;
import com.hangyi.eyunda.domain.enumeric.ApplyStatusCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MultipartUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class OperatorService extends BaseService<YydOperator, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydOperatorDao operatorDao;

	@Autowired
	private CompanyService companyService;
	@Autowired
	private PrivilegeService privilegeService;
	@Autowired
	private UserService userService;

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;

	@Override
	public PageHibernateDao<YydOperator, Long> getDao() {
		return (PageHibernateDao<YydOperator, Long>) operatorDao;
	}

	public OperatorData getOperatorDataById(Long id) {
		YydOperator operator = operatorDao.get(id);
		if (operator != null) {
			OperatorData operatorData = this.getOperatorData(operator);
			return operatorData;
		}
		return null;
	}

	public boolean deleteOperator(Long id) {
		try {
			YydOperator operator = operatorDao.get(id);
			if (operator.getStatus() == ApplyStatusCode.apply) {
				operatorDao.delete(operator);
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public OperatorData getOperatorDataByUserId(Long userId) {
		YydOperator yydOperator = operatorDao.getOperByUserId(userId);
		if (yydOperator != null) {
			return this.getOperatorData(yydOperator);
		}
		return null;
	}

	public OperatorData getSelfOperatorDataByUserId(Long userId) {
		YydOperator yydOperator = operatorDao.getSelfOperByUserId(userId);
		if (yydOperator != null) {
			return this.getOperatorData(yydOperator);
		}
		return null;
	}

	public OperatorData getOperatorData(YydOperator yydOperator) {
		if (yydOperator != null) {
			OperatorData operatorData = new OperatorData();
			CopyUtil.copyProperties(operatorData, yydOperator);

			UserData userData = userService.getById(yydOperator.getUserId());
			OnlineUser ou = onlineUserRecorder.getOnlineUser(userData.getId());
			if (ou != null && ou.isPresence())
				userData.setOnlineStatus(ou.getOnlineStatus());
			else
				userData.setOnlineStatus(OnlineStatusCode.ofline);

			operatorData.setUserData(userData);

			// 代理人下业务员
			List<UserData> userDatas = new ArrayList<UserData>();

			List<CompanyData> cds = companyService.getCompanyDatas(yydOperator.getUserId());
			if (!cds.isEmpty() && cds.size() == 1) {
				CompanyData cd = cds.get(0);

				List<UserPrivilegeData> upds = privilegeService.getUserPrivilegeDatas(cd.getId(),
						UserPrivilegeCode.handler);
				for (UserPrivilegeData upd : upds) {
					UserData handlerUserData = userService.getUserData(upd.getUserId());
					if (handlerUserData != null) {
						OnlineUser onlineUser = onlineUserRecorder.getOnlineUser(handlerUserData.getId());

						if (onlineUser != null && onlineUser.isPresence())
							handlerUserData.setOnlineStatus(onlineUser.getOnlineStatus());
						else
							handlerUserData.setOnlineStatus(OnlineStatusCode.ofline);

						userDatas.add(handlerUserData);
					}
				}
			}
			operatorData.setHandlerDatas(userDatas);

			return operatorData;
		}
		return null;
	}

	public boolean update(OperatorData operatorData) {
		try {
			YydOperator operator = operatorDao.get(operatorData.getId());
			if (operator == null)
				operator = new YydOperator();

			operator.setUserId(operatorData.getUserId());
			operator.setLegalPerson(operatorData.getLegalPerson());
			operator.setStatus(ApplyStatusCode.apply);

			// 如果上传了idCardFrontFile
			MultipartFile idCardFrontFile = operatorData.getIdCardFrontFile();
			if (idCardFrontFile != null && !idCardFrontFile.isEmpty()) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getUserDir(operatorData.getUserId());
				String prefix = "IdCardF";
				String url = MultipartUtil.uploadFile(idCardFrontFile, realPath, relativePath, prefix);
				// 修改数据库中文件路径
				operator.setIdCardFront(url);
			}

			// 如果上传了idCardFrontFile
			MultipartFile idCardBackFile = operatorData.getIdCardBackFile();
			if (idCardBackFile != null && !idCardBackFile.isEmpty()) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getUserDir(operatorData.getUserId());
				String prefix = "IdCardB";
				String url = MultipartUtil.uploadFile(idCardBackFile, realPath, relativePath, prefix);
				// 如果存在旧文件删除之
				String oldPathFile = operator.getIdCardBack();
				File oldF = new File(realPath + oldPathFile);
				if (oldF.exists())
					oldF.delete();
				// 修改数据库中文件路径
				operator.setIdCardBack(url);
			}

			// 如果上传了busiLicenceFile
			MultipartFile busiLicenceFile = operatorData.getBusiLicenceFile();
			if (busiLicenceFile != null && !busiLicenceFile.isEmpty()) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getUserDir(operatorData.getUserId());
				String prefix = "LicenceB";
				String url = MultipartUtil.uploadFile(busiLicenceFile, realPath, relativePath, prefix);
				// 如果存在旧文件删除之
				String oldPathFile = operator.getBusiLicence();
				File oldF = new File(realPath + oldPathFile);
				if (oldF.exists())
					oldF.delete();
				// 修改数据库中文件路径
				operator.setBusiLicence(url);
			}

			// 如果上传了Logo
			MultipartFile taxLicenceFile = operatorData.getTaxLicenceFile();
			if (taxLicenceFile != null && !taxLicenceFile.isEmpty()) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getUserDir(operatorData.getUserId());
				String prefix = "LicenceT";
				String url = MultipartUtil.uploadFile(taxLicenceFile, realPath, relativePath, prefix);
				// 如果存在旧文件删除之
				String oldPathFile = operator.getTaxLicence();
				File oldF = new File(realPath + oldPathFile);
				if (oldF.exists())
					oldF.delete();
				// 修改数据库中文件路径
				operator.setTaxLicence(url);
			}

			operatorDao.save(operator);

			operatorData.setId(operator.getId());

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	// 获得代理人页
	public Page<OperatorData> getOperatorDatas(int pageSize, int pageNo, Long... ids) throws Exception {
		// 通过代理公司编码获得代理人页
		Page<YydOperator> page = operatorDao.getOperatorPage(pageSize, pageNo, ids);

		Page<OperatorData> pageData = new Page<OperatorData>();
		List<OperatorData> operatorDatas = new ArrayList<OperatorData>();

		for (YydOperator operator : page.getResult()) {
			OperatorData operatorData = this.getOperatorData(operator);
			operatorDatas.add(operatorData);
		}
		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(operatorDatas);

		return pageData;
	}

	public boolean audit(Long id, ApplyStatusCode status) {
		try {
			YydOperator operator = operatorDao.get(id);

			if (operator != null) {
				operator.setStatus(status);
				operatorDao.save(operator);
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean unaudit(Long id) {
		try {
			YydOperator operator = operatorDao.get(id);

			if (operator != null) {
				operator.setStatus(ApplyStatusCode.apply);
				operatorDao.save(operator);
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Page<OperatorData> getPageByOperInfos(int pageNo, String operInfo, ApplyStatusCode status) {
		Page<YydOperator> page = operatorDao.getByOperInfos(pageNo, operInfo, status);

		Page<OperatorData> pageData = new Page<OperatorData>();

		List<OperatorData> operatorDatas = new ArrayList<OperatorData>();

		for (YydOperator operator : page.getResult()) {
			OperatorData operatorData = this.getOperatorData(operator);
			operatorDatas.add(operatorData);
		}
		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(operatorDatas);

		return pageData;
	}

	public List<String> getImagesUrls(OperatorData operData) {
		try {
			if (operData != null) {
				List<String> imageUrls = new ArrayList<String>();

				String idCardFront = operData.getIdCardFront();
				imageUrls.add(idCardFront);

				String idCardBack = operData.getIdCardBack();
				imageUrls.add(idCardBack);

				String busiLicence = operData.getBusiLicence();
				imageUrls.add(busiLicence);

				String taxLicence = operData.getTaxLicence();
				imageUrls.add(taxLicence);

				return imageUrls;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

}
