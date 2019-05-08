package com.hangyi.eyunda.service.hyquan;

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

import com.hangyi.eyunda.dao.HyqCompanyDao;
import com.hangyi.eyunda.dao.HyqDepartmentDao;
import com.hangyi.eyunda.dao.HyqUserDeptDao;
import com.hangyi.eyunda.data.hyquan.HyqCompanyData;
import com.hangyi.eyunda.data.hyquan.HyqDepartmentData;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.domain.HyqCompany;
import com.hangyi.eyunda.domain.HyqDepartment;
import com.hangyi.eyunda.domain.HyqUserDept;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class HyqCompanyService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqCompanyDao companyDao;
	@Autowired
	private HyqDepartmentDao departmentDao;
	@Autowired
	private HyqUserDeptDao userDeptDao;
	@Autowired
	private HyqDepartmentService departmentService;

	public HyqCompanyData getCompanyData(HyqCompany comp) {
		if (comp != null) {
			HyqCompanyData companyData = new HyqCompanyData();
			CopyUtil.copyProperties(companyData, comp);
			companyData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(comp.getCreateTime()));

			List<HyqDepartmentData> depts = departmentService.getDepartmentDatas(comp.getId());
			companyData.setDepartmentDatas(depts);

			return companyData;
		}
		return null;
	}

	public HyqCompanyData getCompanyData(Long compId) {
		HyqCompany comp = companyDao.get(compId);
		if (comp == null)
			return new HyqCompanyData();
		else
			return this.getCompanyData(comp);
	}

	public List<HyqCompanyData> getCompanyDatas(Long userId) {
		List<HyqCompanyData> companyDatas = new ArrayList<HyqCompanyData>();
		try {
			List<HyqCompany> comps = companyDao.getByUserId(userId);
			for (HyqCompany comp : comps) {
				HyqCompanyData companyData = this.getCompanyData(comp);
				companyDatas.add(companyData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return companyDatas;
	}

	public boolean deleteComp(HyqUserData userData, Long compId) {
		try {
			HyqCompany company = companyDao.get(compId);

			if (company == null)
				throw new Exception("错误！指定的公司信息已经不存在!");

			if (!userData.getId().equals(company.getManagerId()))
				throw new Exception("错误！非管理员不能删除公司!");

			List<HyqUserDept> huds = userDeptDao.getByCompIdKeyWords(compId, null, null);

			for (HyqUserDept hud : huds) {
				if (!hud.getUserId().equals(userData.getId())) {
					throw new Exception("错误！除管理员外，公司成员未全部移除时，不能删除公司。");
				}
			}

			for (HyqUserDept hud : huds) {
				userDeptDao.delete(hud);
			}

			List<HyqDepartment> depts = departmentDao.getByCompId(compId);
			for (HyqDepartment dept : depts) {
				departmentDao.delete(dept);
			}

			companyDao.delete(compId);

			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	public boolean saveComp(HyqUserData userData, Long compId, String compName) {
		try {
			HyqCompany company = companyDao.get(compId);
			if (company == null) {
				HyqCompany c = companyDao.getByCompName(compName);
				if (c != null)
					throw new Exception("错误！存在同名的企业、组织机构或社会团体。");

				company = new HyqCompany();
				company.setCompName(compName);
				company.setShortName(compName);
				company.setCreateTime(Calendar.getInstance());
				company.setManagerId(userData.getId());
				companyDao.save(company);

				HyqDepartment dept = new HyqDepartment();
				dept.setCompId(company.getId());
				dept.setDeptName("管理部");
				dept.setCreateTime(Calendar.getInstance());
				departmentDao.save(dept);

				HyqUserDept yud = new HyqUserDept();
				yud.setUserId(userData.getId());
				yud.setCompId(company.getId());
				yud.setDeptId(dept.getId());
				yud.setCreateTime(Calendar.getInstance());
				userDeptDao.save(yud);
			} else {
				if (!userData.getId().equals(company.getManagerId()))
					throw new Exception("非管理员不能进行此项操作!");

				company.setCompName(compName);
				company.setShortName(compName);

				companyDao.save(company);
			}

			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

}
