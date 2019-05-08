package com.hangyi.eyunda.service.manage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydAdminInfoDao;
import com.hangyi.eyunda.dao.YydRoleInfoDao;
import com.hangyi.eyunda.data.manage.AdminInfoData;
import com.hangyi.eyunda.data.manage.ModuleInfoData;
import com.hangyi.eyunda.data.manage.RoleInfoData;
import com.hangyi.eyunda.domain.YydAdminInfo;
import com.hangyi.eyunda.domain.YydModuleInfo;
import com.hangyi.eyunda.domain.YydRoleInfo;
import com.hangyi.eyunda.domain.enumeric.UserStatusCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.EnumConst.BackMenuCode;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MD5;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class AdminService extends BaseService<YydAdminInfo, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydAdminInfoDao adminDao;
	@Autowired
	private YydRoleInfoDao roleDao;

	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleService moduleService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageHibernateDao<YydAdminInfo, Long> getDao() {
		return (PageHibernateDao) adminDao;
	}

	public AdminInfoData getAdminData(YydAdminInfo yydAdminInfo) {
		AdminInfoData adminInfoData = null;
		if (yydAdminInfo != null) {
			adminInfoData = new AdminInfoData();
			CopyUtil.copyProperties(adminInfoData, yydAdminInfo);

			Map<Long, RoleInfoData> map = new HashMap<Long, RoleInfoData>();
			for (YydRoleInfo r : roleDao.getAll()) {
				RoleInfoData rd = new RoleInfoData();
				CopyUtil.copyProperties(rd, r);

				map.put(rd.getId(), rd);
			}

			for (YydRoleInfo yydRoleInfo : yydAdminInfo.getRoles()) {
				map.get(yydRoleInfo.getId()).setTheRole(true);
			}

			adminInfoData.setRoleDatas(new ArrayList<RoleInfoData>(map.values()));
		}

		return adminInfoData;
	}

	public AdminInfoData getAdminData(Long id) {
		YydAdminInfo yydAdminInfo = adminDao.get(id);
		AdminInfoData adminInfoData = this.getAdminData(yydAdminInfo);
		return adminInfoData;
	}

	public AdminInfoData getAdminDataByLoginName(String loginName) {
		YydAdminInfo yydAdminInfo = adminDao.getByLoginName(loginName);
		AdminInfoData adminInfoData = this.getAdminData(yydAdminInfo);
		return adminInfoData;
	}

	public List<AdminInfoData> getAdminDatas() {
		List<AdminInfoData> adminDatas = new ArrayList<AdminInfoData>();
		// 根据查询参数，用Dao查询数据库
		List<YydAdminInfo> yydAdminInfos = adminDao.getAll();
		// 取出列表循环处理，将Entity转换为Data
		for (YydAdminInfo admin : yydAdminInfos) {
			AdminInfoData adminData = new AdminInfoData();
			// 使用属性拷贝CopyUtil类拷贝同名属性，其他属性在这儿分别处理
			CopyUtil.copyProperties(adminData, admin);

			// 同理处理管理员的角色数据
			List<RoleInfoData> roleDatas = new ArrayList<RoleInfoData>();
			Set<YydRoleInfo> roles = admin.getRoles();
			// 对角色循环处理
			for (YydRoleInfo role : roles) {
				RoleInfoData roleData = new RoleInfoData();
				// 使用属性拷贝CopyUtil类拷贝同名属性，其他属性在这儿分别处理
				CopyUtil.copyProperties(roleData, role);
				// data数据加入集合中
				roleDatas.add(roleData);
			}
			// 设置data中的特殊属性
			adminData.setRoleDatas(roleDatas);
			// data数据加入集合中
			adminDatas.add(adminData);
		}
		// 返回集合数据对象
		return adminDatas;
	}

	/**
	 * 先删除关联关系，后删除主动实体
	 */
	public boolean deleteAdmin(Long id) {
		try {
			YydAdminInfo adminInfo = adminDao.get(id);
			for (Iterator<YydRoleInfo> it = adminInfo.getRoles().iterator(); it.hasNext();) {
				YydRoleInfo role = it.next();
				role.getAdmins().remove(adminInfo);
			}
			adminInfo.getRoles().clear();
			adminDao.delete(adminInfo);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 保存或更新
	 */
	public boolean saveOrUpdate(AdminInfoData adminData) {
		try {
			YydAdminInfo admin = adminDao.get(adminData.getId());
			if (admin == null)
				admin = new YydAdminInfo();// 添加

			// 修改属性
			admin.setLoginName(adminData.getLoginName());
			admin.setTrueName(adminData.getTrueName());
			admin.setNickName(adminData.getNickName());
			admin.setEmail(adminData.getEmail());
			admin.setMobile(adminData.getMobile());

			// 保存或更新
			adminDao.save(admin);

			// 密码加密存储
			admin.setPassword(MD5.toMD5(adminData.getPassword() + adminData.getId()));
			adminDao.save(admin);// 更新

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 先删除原有关联关系，再赋予新选择关联关系
	 */
	public boolean updateRoles(Long adminId, long[] roles) {
		try {
			// 先删除原有关联关系
			YydAdminInfo admin = adminDao.get(adminId);
			for (Iterator<YydRoleInfo> it = admin.getRoles().iterator(); it.hasNext();) {
				YydRoleInfo role = it.next();
				role.getAdmins().remove(admin);
			}
			admin.getRoles().clear();

			// 再赋予新选择关联关系
			if (roles != null && roles.length > 0) {
				for (Long id : roles) {
					YydRoleInfo role = roleDao.get(id);
					role.getAdmins().add(admin);
					admin.getRoles().add(role);
				}
			}

			// 保存管理员
			adminDao.save(admin);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public void initUserRoleModule() {
		List<YydAdminInfo> admins = this.getAll();
		if (admins.isEmpty()) {
			if (true) {
				YydAdminInfo admin = new YydAdminInfo();

				admin.setLoginName("superadmin");
				admin.setPassword("pass");
				admin.setTrueName("超级管理");
				admin.setNickName("超级管理");
				admin.setEmail("superadmin@eyd98.com");
				admin.setMobile("18912345678");
				admin.setUserLogo("/img/headshot.png");
				admin.setCreateTime(Calendar.getInstance());
				admin.setStatus(UserStatusCode.activity);

				this.save(admin);

				admin.setPassword(MD5.toMD5("pass" + admin.getId()));
				this.save(admin);
			}

			this.initModuleInfo();

			Map<BackMenuCode, List<BackMenuCode>> menus = BackMenuCode.getLayerMenuMap();

			int i = 0;
			for (BackMenuCode root : menus.keySet()) {
				YydAdminInfo admin = new YydAdminInfo();

				admin.setLoginName(root.toString().toLowerCase());
				admin.setPassword(root.toString().toLowerCase());
				admin.setTrueName(root.getMenuname());
				admin.setNickName(root.getMenuname());
				admin.setEmail(root.toString().toLowerCase() + "@eyd98.com");
				admin.setMobile("1890000000" + ++i);
				admin.setUserLogo("/img/headshot.png");
				admin.setCreateTime(Calendar.getInstance());
				admin.setStatus(UserStatusCode.activity);

				this.save(admin);

				admin.setPassword(MD5.toMD5(root.toString().toLowerCase() + admin.getId()));

				YydRoleInfo r = new YydRoleInfo();

				r.setRoleName(root.getMenuname());
				r.setRoleDesc(root.getMenuname());

				Set<YydAdminInfo> as = new HashSet<YydAdminInfo>();
				as.add(admin);
				r.setAdmins(as);

				Set<YydModuleInfo> ms = new HashSet<YydModuleInfo>();
				@SuppressWarnings("static-access")
				List<BackMenuCode> mcs = root.getChildren(root.getMenuid());
				for (BackMenuCode mc : mcs) {
					ModuleInfoData md = moduleService.getModuleDataByUrl(mc.getUrl());
					YydModuleInfo m = moduleService.get(md.getId());

					/*Set<YydRoleInfo> rrs = m.getRoles();
					if (rrs == null)
						rrs = new HashSet<YydRoleInfo>();
					rrs.add(r);
					m.setRoles(rrs);
					moduleService.save(m);*/

					ms.add(m);
				}
				r.setModules(ms);

				roleService.save(r);

				Set<YydRoleInfo> rs = new HashSet<YydRoleInfo>();
				rs.add(r);
				admin.setRoles(rs);

				this.save(admin);
			}
		}
	}

	private void initModuleInfo() {
		try {
			List<YydModuleInfo> modules = moduleService.getAll();
			if (modules.isEmpty()) {
				for (BackMenuCode menu : BackMenuCode.getAllLeaf()) {
					YydModuleInfo m = new YydModuleInfo();
					m.setModuleName(menu.getMenuname());
					m.setModuleDesc(menu.getMenuname());
					m.setModuleLayer(menu.getMenuid());
					m.setModuleUrl(menu.getUrl());
					moduleService.save(m);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
