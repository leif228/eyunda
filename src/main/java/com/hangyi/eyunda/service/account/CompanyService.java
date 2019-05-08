package com.hangyi.eyunda.service.account;

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

import com.hangyi.eyunda.dao.YydCompanyDao;
import com.hangyi.eyunda.dao.YydDepartmentDao;
import com.hangyi.eyunda.dao.YydUserDeptDao;
import com.hangyi.eyunda.dao.YydUserPrivilegeDao;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.YydCompany;
import com.hangyi.eyunda.domain.YydDepartment;
import com.hangyi.eyunda.domain.YydUserDept;
import com.hangyi.eyunda.domain.YydUserPrivilege;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class CompanyService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydCompanyDao companyDao;
	@Autowired
	private YydDepartmentDao departmentDao;
	@Autowired
	private YydUserDeptDao userDeptDao;
	@Autowired
	private YydUserPrivilegeDao userPrivilegeDao;

	public CompanyData getCompanyData(YydCompany comp) {
		if (comp != null) {
			CompanyData companyData = new CompanyData();
			CopyUtil.copyProperties(companyData, comp);
			companyData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(comp.getCreateTime()));

			return companyData;
		}
		return null;
	}

	public CompanyData getCompanyData(Long compId) {
		YydCompany comp = companyDao.get(compId);
		if (comp == null)
			return new CompanyData();
		else
			return this.getCompanyData(comp);
	}

	public List<CompanyData> getCompanyDatas(Long userId) {
		List<CompanyData> companyDatas = new ArrayList<CompanyData>();
		try {
			List<YydCompany> comps = companyDao.getByUserId(userId);
			for (YydCompany comp : comps) {
				CompanyData companyData = this.getCompanyData(comp);
				companyDatas.add(companyData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return companyDatas;
	}

	// 注册企业用户后建立公司、部门、分配权限
	public void compRegister(UserData userData) throws Exception {
		List<YydCompany> ycs = companyDao.getByCompName(userData.getTrueName());
		if (!ycs.isEmpty())
			throw new Exception("错误！存在同名的企业。");

		YydCompany yc = new YydCompany();
		yc.setCompName(userData.getTrueName());
		yc.setShortName(userData.getTrueName());
		yc.setCreateTime(Calendar.getInstance());
		companyDao.save(yc);

		YydDepartment yd = new YydDepartment();
		yd.setCompId(yc.getId());
		yd.setDeptName("综合管理部");
		yd.setDeptType(UserPrivilegeCode.manager);
		yd.setCreateTime(Calendar.getInstance());
		departmentDao.save(yd);

		YydUserDept yud = new YydUserDept();
		yud.setUserId(userData.getId());
		yud.setCompId(yc.getId());
		yud.setDeptId(yd.getId());
		yud.setCreateTime(Calendar.getInstance());
		userDeptDao.save(yud);

		YydUserPrivilege yup = new YydUserPrivilege();
		yup.setUserId(userData.getId());
		yup.setCompId(yc.getId());
		yup.setPrivilege(UserPrivilegeCode.manager);
		yup.setMmsis("");
		yup.setCreateTime(Calendar.getInstance());
		userPrivilegeDao.save(yup);
	}

	// 注册企业用户修改名称后要更新公司名称
	public void updateCompName(String oldName, String newName) throws Exception {
		List<YydCompany> ycs = companyDao.getByCompName(newName);
		if (!ycs.isEmpty())
			throw new Exception("错误！存在同名的企业。");

		List<YydCompany> lstYydCompany = companyDao.getByCompName(oldName);
		if (lstYydCompany.isEmpty())
			throw new Exception("错误！要修改的企业名称找不到。");

		YydCompany yydCompany = lstYydCompany.get(0);
		yydCompany.setCompName(newName);
		yydCompany.setShortName(newName);
		companyDao.save(yydCompany);
	}

}
