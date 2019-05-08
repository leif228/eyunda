package com.hangyi.eyunda.service.manage;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydModuleInfoDao;
import com.hangyi.eyunda.dao.YydRoleInfoDao;
// import com.hangyi.eyunda.data.manage.ModuleComparator;
import com.hangyi.eyunda.data.manage.ModuleInfoData;
import com.hangyi.eyunda.data.manage.RoleInfoData;
import com.hangyi.eyunda.domain.YydAdminInfo;
import com.hangyi.eyunda.domain.YydModuleInfo;
import com.hangyi.eyunda.domain.YydRoleInfo;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class RoleService extends BaseService<YydRoleInfo, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydRoleInfoDao roleDao;
	@Autowired
	YydModuleInfoDao moduleDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageHibernateDao<YydRoleInfo, Long> getDao() {
		return (PageHibernateDao) roleDao;
	}

	public RoleInfoData getRoleData(YydRoleInfo yydRoleInfo) {
		RoleInfoData roleInfoData = null;
		if (yydRoleInfo != null) {
			roleInfoData = new RoleInfoData();
			CopyUtil.copyProperties(roleInfoData, yydRoleInfo);

			Map<String, ModuleInfoData> map = new TreeMap<String, ModuleInfoData>();
			List<YydModuleInfo> yydModuleInfos = moduleDao.getAll();
			for (YydModuleInfo m : yydModuleInfos) {
				ModuleInfoData md = new ModuleInfoData();
				CopyUtil.copyProperties(md, m);

				map.put(md.getModuleLayer(), md);
			}

			for (YydModuleInfo yydModuleInfo : yydRoleInfo.getModules()) {
				map.get(yydModuleInfo.getModuleLayer()).setTheModule(true);
			}

			List<ModuleInfoData> mds = new ArrayList<ModuleInfoData>(map.values());

			// ModuleComparator comparator = new ModuleComparator();
			// Collections.sort(mds, comparator);

			roleInfoData.setModuleDatas(mds);
		}

		return roleInfoData;
	}

	public RoleInfoData getRoleData(Long id) {
		YydRoleInfo yydRoleInfo = roleDao.get(id);
		RoleInfoData roleInfoData = this.getRoleData(yydRoleInfo);
		return roleInfoData;
	}

	/**
	 * 取得角色数据列表
	 */
	public List<RoleInfoData> getRoleDatas() {
		List<RoleInfoData> roleDatas = new ArrayList<RoleInfoData>();
		try {
			List<YydRoleInfo> yydRoleInfos = roleDao.getAll();
			for (YydRoleInfo yydRoleInfo : yydRoleInfos) {
				RoleInfoData roleData = this.getRoleData(yydRoleInfo);
				roleDatas.add(roleData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return roleDatas;
	}

	/**
	 * 保存或更新
	 */
	public boolean saveOrUpdate(RoleInfoData roleData) {
		try {
			YydRoleInfo role = roleDao.get(roleData.getId());
			if (role == null)
				role = new YydRoleInfo();// 添加

			// 修改属性
			role.setRoleName(roleData.getRoleName());
			role.setRoleDesc(roleData.getRoleDesc());

			// 保存或更新
			roleDao.save(role);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 先删除关联关系，后删除实体
	 */
	public boolean deleteRole(Long id) {
		try {
			YydRoleInfo role = roleDao.get(id);

			// 先删除关联关系
			for (Iterator<YydAdminInfo> it = role.getAdmins().iterator(); it.hasNext();) {
				YydAdminInfo admin = it.next();
				admin.getRoles().remove(role);
			}
			role.getAdmins().clear();

			for (Iterator<YydModuleInfo> it = role.getModules().iterator(); it.hasNext();) {
				YydModuleInfo module = it.next();
				module.getRoles().remove(role);
			}
			role.getModules().clear();

			// 后删除实体
			roleDao.delete(role);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 先删除原有关联关系，再赋予新选择关联关系
	 */
	public boolean updateModules(Long roleId, long[] modules) {
		try {
			// 先删除原有关联关系
			YydRoleInfo role = roleDao.get(roleId);
			for (Iterator<YydModuleInfo> it = role.getModules().iterator(); it.hasNext();) {
				YydModuleInfo module = it.next();
				module.getRoles().remove(role);
			}
			role.getModules().clear();

			// 再赋予新选择关联关系
			if (modules != null && modules.length > 0) {
				for (Long id : modules) {
					YydModuleInfo module = moduleDao.get(id);
					module.getRoles().add(role);
					role.getModules().add(module);
				}
			}

			// 保存角色
			roleDao.save(role);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
