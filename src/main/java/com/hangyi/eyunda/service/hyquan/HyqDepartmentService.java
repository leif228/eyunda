package com.hangyi.eyunda.service.hyquan;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.HyqDepartmentDao;
import com.hangyi.eyunda.dao.HyqUserDeptDao;
import com.hangyi.eyunda.data.hyquan.HyqDepartmentData;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.domain.HyqDepartment;
import com.hangyi.eyunda.domain.HyqUserDept;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class HyqDepartmentService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqDepartmentDao departmentDao;
	@Autowired
	private HyqUserDeptDao userDeptDao;

	@Autowired
	private HyqUserService userService;

	public List<HyqUserData> getDeptUserData(Long deptId) {
		List<HyqUserData> userDatas = new ArrayList<HyqUserData>();

		List<HyqUserDept> userDepts = userDeptDao.getByDeptId(deptId);

		for (HyqUserDept userDept : userDepts) {
			HyqUserData userData = userService.getById(userDept.getUserId());
			userDatas.add(userData);
		}

		return userDatas;
	}

	public HyqDepartmentData getDepartmentData(HyqDepartment dept) {
		if (dept != null) {
			HyqDepartmentData departmentData = new HyqDepartmentData();
			CopyUtil.copyProperties(departmentData, dept);
			departmentData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(dept.getCreateTime()));

			List<HyqUserData> userDatas = new ArrayList<HyqUserData>();
			List<HyqUserDept> userDepts = userDeptDao.getByDeptId(dept.getId());
			for (HyqUserDept userDept : userDepts) {
				HyqUserData userData = userService.getById(userDept.getUserId());
				userDatas.add(userData);
			}
			departmentData.setUserDatas(userDatas);

			return departmentData;
		}
		return null;
	}

	public HyqDepartmentData getDepartmentData(Long deptId) {
		return this.getDepartmentData(departmentDao.get(deptId));
	}

	public List<HyqDepartmentData> getDepartmentDatas(Long compId) {
		List<HyqDepartmentData> departmentDatas = new ArrayList<HyqDepartmentData>();
		try {
			List<HyqDepartment> depts = departmentDao.getByCompId(compId);
			for (HyqDepartment dept : depts) {
				HyqDepartmentData departmentData = this.getDepartmentData(dept);
				departmentDatas.add(departmentData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return departmentDatas;
	}

	public void removeContact(HyqUserData userData,Long compId, Long deptId, Long userId) throws Exception {
		if (userData.getId().equals(userId)) {
			List<HyqUserDept> yydUserDepts = userDeptDao.getByCompIdUserId(compId,
					userId);

			if (yydUserDepts.size() == 1) {
				throw new Exception("错误！你不能完全移除你自己!");
			}
		}

		HyqUserDept yydUserDept = userDeptDao.getByCompIdDeptIdUserId(compId, deptId,
				userId);
		userDeptDao.delete(yydUserDept);
	}

	public void addContact(Long compId, Long deptId, Long userId) throws Exception {
		HyqUserDept yydUserDept = userDeptDao.getByCompIdDeptIdUserId(compId, deptId, userId);
		if (yydUserDept == null) {
			yydUserDept = new HyqUserDept();

			yydUserDept.setCompId(compId);
			yydUserDept.setDeptId(deptId);
			yydUserDept.setUserId(userId);

			userDeptDao.save(yydUserDept);
		} else {
			throw new Exception("错误！指定用户已经是指定部门的成员，不可重复。");
		}

	}

	public Long saveDepartment(HyqUserData userData, Long compId, Long deptId, String deptName) throws Exception {
		HyqDepartment department = departmentDao.get(deptId);

		if (department == null)
			department = new HyqDepartment();

		department.setCompId(compId);
		department.setDeptName(deptName);

		departmentDao.save(department);

		return department.getId();
	}

	public void deleteDepartment(Long compId, Long deptId) throws Exception {
		HyqDepartment yydDepartment = departmentDao.get(deptId);

		if (yydDepartment != null) {
			List<HyqUserDept> yuds = userDeptDao.getByDeptId(deptId);
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

}
