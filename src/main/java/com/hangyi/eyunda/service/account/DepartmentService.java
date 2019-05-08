package com.hangyi.eyunda.service.account;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.YydDepartmentDao;
import com.hangyi.eyunda.dao.YydUserDeptDao;
import com.hangyi.eyunda.dao.YydUserPrivilegeDao;
import com.hangyi.eyunda.data.account.DepartmentData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.YydDepartment;
import com.hangyi.eyunda.domain.YydUserDept;
import com.hangyi.eyunda.domain.YydUserPrivilege;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.domain.enumeric.UserTypeCode;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class DepartmentService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydDepartmentDao departmentDao;
	@Autowired
	private YydUserDeptDao userDeptDao;
	@Autowired
	private YydUserPrivilegeDao userPrivilegeDao;

	@Autowired
	private UserService userService;

	public List<UserData> getDeptUserData(Long deptId) {
		List<UserData> userDatas = new ArrayList<UserData>();

		List<YydUserDept> userDepts = userDeptDao.getByDeptId(deptId);

		for (YydUserDept userDept : userDepts) {
			UserData userData = userService.getById(userDept.getUserId());
			userDatas.add(userData);
		}

		return userDatas;
	}

	public DepartmentData getDepartmentData(YydDepartment dept) {
		if (dept != null) {
			DepartmentData departmentData = new DepartmentData();
			CopyUtil.copyProperties(departmentData, dept);
			departmentData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(dept.getCreateTime()));

			/*
			 * List<UserData> userDatas = new ArrayList<UserData>();
			 * List<YydUserDept> userDepts =
			 * userDeptDao.getByDeptId(dept.getId()); for (YydUserDept userDept
			 * : userDepts) { UserData userData =
			 * userService.getById(userDept.getUserId());
			 * userDatas.add(userData); }
			 * departmentData.setUserDatas(userDatas);
			 */

			return departmentData;
		}
		return null;
	}

	public DepartmentData getDepartmentData(Long deptId) {
		return this.getDepartmentData(departmentDao.get(deptId));
	}

	public List<DepartmentData> getDepartmentDatas(Long compId) {
		List<DepartmentData> departmentDatas = new ArrayList<DepartmentData>();
		try {
			List<YydDepartment> depts = departmentDao.getByCompId(compId);
			for (YydDepartment dept : depts) {
				DepartmentData departmentData = this.getDepartmentData(dept);
				departmentDatas.add(departmentData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return departmentDatas;
	}

	public List<DepartmentData> getDepartmentDatas(Long compId, UserPrivilegeCode deptType) {
		List<DepartmentData> departmentDatas = new ArrayList<DepartmentData>();
		try {
			List<YydDepartment> depts = departmentDao.getByCompIdDeptType(compId, deptType);
			for (YydDepartment dept : depts) {
				DepartmentData departmentData = this.getDepartmentData(dept);
				departmentDatas.add(departmentData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return departmentDatas;
	}

	public void deleteContact(Long compId, Long deptId, Long userId) throws Exception {
		YydUserDept yydUserDept = userDeptDao.getByCompIdDeptIdUserId(compId, deptId, userId);
		// 如果是部门成员
		if (yydUserDept != null) {
			YydDepartment yd = departmentDao.get(deptId);
			List<YydDepartment> ds = departmentDao.getByCompIdUserIdDeptType(compId, userId, yd.getDeptType());
			boolean hasSimularDep = false;
			if (!ds.isEmpty() && ds.size() > 1)
				hasSimularDep = true;

			if (yd.getDeptType() == UserPrivilegeCode.manager) {// 如果是管理员
				UserData ud = userService.getById(userId);
				if (ud.getUserType() == UserTypeCode.person) {// 普通管理员
					if (!hasSimularDep) {
						YydUserPrivilege yup = userPrivilegeDao.getByCompIdUserIdPrivilege(compId, userId,
								UserPrivilegeCode.manager);
						if (yup != null)
							userPrivilegeDao.delete(yup);
					}
					userDeptDao.delete(yydUserDept);
				} else {
					throw new Exception("错误！指定用户是公司管理员，不能被删除。");
				}
			} else {// 如果不是管理员
				if (!hasSimularDep) {
					YydUserPrivilege yup = userPrivilegeDao.getByCompIdUserIdPrivilege(compId, userId,
							yd.getDeptType());
					if (yup != null)
						userPrivilegeDao.delete(yup);
				}
				userDeptDao.delete(yydUserDept);
			}
		} else {
			throw new Exception("错误！指定用户已经不是指定部门的成员。");
		}
	}

	public void addContact(Long compId, Long deptId, Long userId) throws Exception {
		/*
		 * UserData ud = userService.getById(userId); if (ud.getUserType() ==
		 * UserTypeCode.enterprise) throw new
		 * Exception("警告！只能添加个人用户，不允许添加企业用户。");
		 */

		YydUserDept yydUserDept = userDeptDao.getByCompIdDeptIdUserId(compId, deptId, userId);
		if (yydUserDept == null) {
			yydUserDept = new YydUserDept();

			yydUserDept.setCompId(compId);
			yydUserDept.setDeptId(deptId);
			yydUserDept.setUserId(userId);

			userDeptDao.save(yydUserDept);

			YydDepartment yydDepartment = departmentDao.get(deptId);
			UserPrivilegeCode privilege = yydDepartment.getDeptType();

			YydUserPrivilege yydUserPrivilege = userPrivilegeDao.getByCompIdUserIdPrivilege(compId, userId, privilege);
			if (yydUserPrivilege == null) {
				yydUserPrivilege = new YydUserPrivilege();

				yydUserPrivilege.setCompId(compId);
				yydUserPrivilege.setUserId(userId);
				yydUserPrivilege.setPrivilege(privilege);

				userPrivilegeDao.save(yydUserPrivilege);
			}
		} else {
			throw new Exception("错误！指定用户已经是指定部门的成员，不可重复。");
		}

	}

	public Long saveDepartment(Long compId, Long deptId, String deptName, UserPrivilegeCode deptType) throws Exception {
		YydDepartment yydDepartment = departmentDao.get(deptId);

		if (yydDepartment == null)
			yydDepartment = new YydDepartment();

		yydDepartment.setCompId(compId);
		yydDepartment.setDeptName(deptName);
		yydDepartment.setDeptType(deptType);

		departmentDao.save(yydDepartment);

		return yydDepartment.getId();
	}

	public void deleteDepartment(Long compId, Long deptId) throws Exception {
		YydDepartment yydDepartment = departmentDao.get(deptId);

		if (yydDepartment != null) {
			List<YydUserDept> yuds = userDeptDao.getByDeptId(deptId);
			if (yuds.isEmpty()) {
				departmentDao.delete(yydDepartment);
			} else {
				throw new Exception("错误！指定的部门还有成员，不可删除。");
			}
		} else {
			throw new Exception("错误！指定的部门没有找到。");
		}

		return;
	}

	public void saveSailorReportPrivilege(Long compId, Long userId, String mmsi) throws Exception {
		YydUserPrivilege yydUserPrivilege = userPrivilegeDao.getByCompIdUserIdPrivilege(compId, userId,
				UserPrivilegeCode.sailor);
		if (yydUserPrivilege != null) {
			yydUserPrivilege.setMmsis(mmsi);
			userPrivilegeDao.save(yydUserPrivilege);
		} else {
			throw new Exception("错误！设置船员动态上报船舶时，未找到权限记录。");
		}
	}

}
