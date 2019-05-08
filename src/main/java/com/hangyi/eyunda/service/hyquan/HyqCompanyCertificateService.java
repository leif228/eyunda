package com.hangyi.eyunda.service.hyquan;

import java.io.File;
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

import com.hangyi.eyunda.dao.HyqCompanyCertificateDao;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.hyquan.HyqCompanyCertificateData;
import com.hangyi.eyunda.domain.HyqCompanyCertificate;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MultipartUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class HyqCompanyCertificateService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqCompanyCertificateDao compCertDao;

	public HyqCompanyCertificateData getCompCertData(HyqCompanyCertificate compCert) {
		if (compCert != null) {
			HyqCompanyCertificateData compCertData = new HyqCompanyCertificateData();
			CopyUtil.copyProperties(compCertData, compCert);
			compCertData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(compCert.getCreateTime()));

			return compCertData;
		}
		return null;
	}

	public HyqCompanyCertificateData getCompCertData(String compName) {
		HyqCompanyCertificate compCert = compCertDao.getByCompName(compName);
		if (compCert == null)
			return null;
		else
			return this.getCompCertData(compCert);
	}

	public HyqCompanyCertificateData getCompCertData(Long compCertId) {
		HyqCompanyCertificate compCert = compCertDao.get(compCertId);
		if (compCert == null)
			return null;
		else
			return this.getCompCertData(compCert);
	}

	public Page<HyqCompanyCertificateData> getCompCertPageData(Page<HyqCompanyCertificateData> pageData,
			String keyWords) {
		List<HyqCompanyCertificateData> compCertDatas = new ArrayList<HyqCompanyCertificateData>();

		Page<HyqCompanyCertificate> page = compCertDao.getCompCertPage(pageData.getPageNo(), pageData.getPageSize(),
				keyWords);

		if (!page.getResult().isEmpty()) {
			for (HyqCompanyCertificate compCert : page.getResult()) {
				HyqCompanyCertificateData compCertData = this.getCompCertData(compCert);
				compCertDatas.add(compCertData);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(compCertDatas);

		return pageData;
	}

	public boolean deleteCompCert(Long compCertId) {
		try {
			compCertDao.delete(compCertId);

			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	public boolean saveCompCert(HyqCompanyCertificateData compCertData) {
		try {
			HyqCompanyCertificate compCert = compCertDao.get(compCertData.getId());
			if (compCert == null) {
				compCert = new HyqCompanyCertificate();
				compCert.setId(0L);
			}

			CopyUtil.copyProperties(compCert, compCertData, new String[] { "createTime", "compLogo", "compLicence" });
			compCert.setCreateTime(Calendar.getInstance());

			compCertDao.save(compCert);

			// 拷贝新文件到指定位置
			String realPath = Constants.SHARE_DIR;
			String relativePath = ShareDirService.getGasCompanyDir(compCert.getId());

			if (!compCertData.getCompLogoMpf().isEmpty()) {
				// 删除旧文件
				if (compCert.getCompLogo() != null && !"".equals(compCert.getCompLogo())) {
					File oldF = new File(realPath + compCert.getCompLogo());
					if (oldF.exists()) {
						oldF.delete();
					}
				}
				// 上传新文件
				String prefix = "CompLogo";
				String url = MultipartUtil.uploadFile(compCertData.getCompLogoMpf(), realPath, relativePath, prefix);
				compCert.setCompLogo(url);
				compCertDao.save(compCert);
			}

			if (!compCertData.getCompLicenceMpf().isEmpty()) {
				// 上传新文件
				String prefix = "CompLicence";
				String url = MultipartUtil.uploadFile(compCertData.getCompLicenceMpf(), realPath, relativePath, prefix);
				compCert.setCompLicence(url);
				compCertDao.save(compCert);
			}

			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

}
