package com.hangyi.eyunda.service.manage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydLogInfoDao;
import com.hangyi.eyunda.dao.YydModuleInfoDao;
import com.hangyi.eyunda.data.manage.ModuleInfoData;
import com.hangyi.eyunda.domain.YydLogInfo;
import com.hangyi.eyunda.domain.YydModuleInfo;
import com.hangyi.eyunda.domain.YydRoleInfo;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ModuleService extends BaseService<YydModuleInfo, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydModuleInfoDao moduleDao;
	@Autowired
	YydLogInfoDao logDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageHibernateDao<YydModuleInfo, Long> getDao() {
		return (PageHibernateDao) moduleDao;
	}

	public ModuleInfoData getModuleData(YydModuleInfo yydModuleInfo) {
		ModuleInfoData moduleInfoData = null;
		if (yydModuleInfo != null) {
			moduleInfoData = new ModuleInfoData();
			CopyUtil.copyProperties(moduleInfoData, yydModuleInfo);
		}
		return moduleInfoData;
	}

	public ModuleInfoData getModuleData(Long id) {
		YydModuleInfo yydModuleInfo = moduleDao.get(id);
		ModuleInfoData moduleInfoData = this.getModuleData(yydModuleInfo);
		return moduleInfoData;
	}

	public ModuleInfoData getModuleDataByUrl(String url) {
		YydModuleInfo yydModuleInfo = moduleDao.getModuleByUrl(url);
		ModuleInfoData moduleInfoData = this.getModuleData(yydModuleInfo);
		return moduleInfoData;
	}

	/**
	 * 取得列表的模块数据对象
	 */
	public List<ModuleInfoData> getModuleDatas() {
		List<ModuleInfoData> moduleDatas = new ArrayList<ModuleInfoData>();
		List<YydModuleInfo> modules = moduleDao.getAll();
		for (YydModuleInfo module : modules) {
			ModuleInfoData moduleData = this.getModuleData(module);
			moduleDatas.add(moduleData);
		}
		return moduleDatas;
	}

	/**
	 * 保存或更新
	 */
	public boolean saveOrUpdate(ModuleInfoData moduleData) {
		try {
			YydModuleInfo module = moduleDao.get(moduleData.getId());
			if (module == null)
				module = new YydModuleInfo();// 添加

			// 修改属性
			module.setModuleName(moduleData.getModuleName());
			module.setModuleDesc(moduleData.getModuleDesc());
			module.setModuleLayer(moduleData.getModuleLayer());
			module.setModuleUrl(moduleData.getModuleUrl());

			// 保存或更新
			moduleDao.save(module);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 先删除关联关系，后删除实体
	 */
	public boolean deleteModule(Long id) {
		try {
			// 先删除关联关系
			YydModuleInfo module = moduleDao.get(id);

			for (Iterator<YydRoleInfo> it = module.getRoles().iterator(); it.hasNext();) {
				YydRoleInfo role = it.next();
				role.getModules().remove(module);
			}
			module.getRoles().clear();

			List<YydLogInfo> logs = logDao.findLogsAboutModule(id);
			for (YydLogInfo log : logs) {
				logDao.delete(log);
			}
			// logDao.deleteLogsAboutModule(id);

			// 后删除实体
			moduleDao.delete(module);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
