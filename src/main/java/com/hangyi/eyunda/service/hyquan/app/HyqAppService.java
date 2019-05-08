package com.hangyi.eyunda.service.hyquan.app;

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

import com.hangyi.eyunda.dao.HyqAppDao;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.data.hyquan.HyqAppData;
import com.hangyi.eyunda.domain.HyqApp;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MultipartUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class HyqAppService extends BaseService<HyqApp, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	HyqAppDao hyqAppDao;

	@Override
	public PageHibernateDao<HyqApp, Long> getDao() {
		return (PageHibernateDao<HyqApp, Long>) hyqAppDao;
	}

	public HyqAppData getAppData(HyqApp app) {
		if (app != null) {
			HyqAppData appData = new HyqAppData();
			CopyUtil.copyProperties(appData, app);
			appData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(app.getCreateTime()));

			return appData;
		}
		return null;
	}

	public HyqAppData getAppData(Long appId) {
		HyqApp app = hyqAppDao.get(appId);
		if (app == null)
			return null;
		else
			return this.getAppData(app);
	}

	public List<HyqAppData> getAllAppDatas() {
		List<HyqAppData> appDatas = new ArrayList<HyqAppData>();
		try {
			List<HyqApp> apps = hyqAppDao.getAll();
			for (HyqApp app : apps) {
				HyqAppData appData = this.getAppData(app);
				appDatas.add(appData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return appDatas;
	}

	public boolean saveApp(HyqAppData appData) {
		try {
			HyqApp app = hyqAppDao.get(appData.getId());

			if (app == null) {
				app = new HyqApp();
			}

			CopyUtil.copyProperties(app, appData, new String[] { "id", "appIcon", "ispId", "createTime" });

			// 如果上传了Logo
			MultipartFile mpf = appData.getAppIconFile();
			if (mpf != null && !mpf.isEmpty()) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getManageDir();
				String prefix = "App";
				String url = MultipartUtil.uploadFile(mpf, realPath, relativePath, prefix);
				// 修改数据库中文件路径
				app.setAppIcon(url);
			}
			hyqAppDao.save(app);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteApp(Long id) {
		try {
			HyqApp app = hyqAppDao.get(id);

			hyqAppDao.delete(app);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
