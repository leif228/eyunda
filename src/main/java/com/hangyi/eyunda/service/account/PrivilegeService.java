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

import com.hangyi.eyunda.dao.YydUserPrivilegeDao;
import com.hangyi.eyunda.data.account.OperatorData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.account.UserPrivilegeData;
import com.hangyi.eyunda.domain.YydUserPrivilege;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.domain.enumeric.UserTypeCode;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class PrivilegeService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydUserPrivilegeDao userPrivilegeDao;

	@Autowired
	private UserService userService;
	@Autowired
	private OperatorService operatorService;

	public UserPrivilegeData getUserPrivilegeData(YydUserPrivilege yup) {
		if (yup != null) {
			UserPrivilegeData upd = new UserPrivilegeData();
			CopyUtil.copyProperties(upd, yup);
			upd.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(yup.getCreateTime()));

			return upd;
		}
		return null;
	}

	public UserPrivilegeData getUserPrivilegeData(Long upId) {
		return this.getUserPrivilegeData(userPrivilegeDao.get(upId));
	}

	public List<UserPrivilegeData> getUserPrivilegeDatas(Long userId, Long compId) {
		List<UserPrivilegeData> upds = new ArrayList<UserPrivilegeData>();
		try {
			List<YydUserPrivilege> yups = userPrivilegeDao.getByUserIdCompId(userId, compId);
			for (YydUserPrivilege yup : yups) {
				UserPrivilegeData upd = this.getUserPrivilegeData(yup);
				upds.add(upd);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return upds;
	}

	public UserData getSailorByCompIdMmsi(Long compId, String mmsi) {
		try {
			YydUserPrivilege yup = userPrivilegeDao.getSailorByCompIdMmsi(compId, mmsi);
			if (yup != null)
				return userService.getById(yup.getUserId());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public String getShipMmsiForSailor(Long userId, Long compId) {
		try {
			YydUserPrivilege yup = userPrivilegeDao.getByCompIdUserIdPrivilege(compId, userId,
					UserPrivilegeCode.sailor);

			if (yup != null && yup.getMmsis() != null && !"".equals(yup.getMmsis()))
				return yup.getMmsis();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "-1";
	}

	public String getShipMmsiForSailor(Long userId) {
		try {
			YydUserPrivilege yup = userPrivilegeDao.getByCompIdUserIdPrivilege(userId, UserPrivilegeCode.sailor);

			if (yup != null && yup.getMmsis() != null && !"".equals(yup.getMmsis()))
				return yup.getMmsis();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "-1";
	}

	// 检查登录用户在当前公司是否有某项特权
	public boolean hasPrivilege(Long userId, Long compId, UserPrivilegeCode privil) {
		boolean ret = false;
		try {
			List<UserPrivilegeData> upds = this.getUserPrivilegeDatas(userId, compId);
			for (UserPrivilegeData upd : upds) {
				if (upd.getPrivilege() == privil) {
					ret = true;
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ret;
	}

	public List<UserPrivilegeData> getUserPrivilegeDatas(Long compId, UserPrivilegeCode privilege) {
		List<UserPrivilegeData> upds = new ArrayList<UserPrivilegeData>();
		try {
			List<YydUserPrivilege> yups = userPrivilegeDao.getByCompIdPrivilege(compId, privilege);
			for (YydUserPrivilege yup : yups) {
				UserPrivilegeData upd = this.getUserPrivilegeData(yup);
				upds.add(upd);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return upds;
	}

	public UserData getCompanySuperAdmin(Long compId) {
		try {
			List<YydUserPrivilege> yups = userPrivilegeDao.getByCompIdPrivilege(compId, UserPrivilegeCode.manager);
			for (YydUserPrivilege yup : yups) {
				UserData ud = userService.getById(yup.getUserId());
				if (ud.getUserType() == UserTypeCode.enterprise) {
					return ud;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public boolean isBrokerCompany(Long compId) {
		try {
			UserData sad = this.getCompanySuperAdmin(compId);

			OperatorData od = operatorService.getOperatorDataByUserId(sad.getId());
			if (od != null) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

}
