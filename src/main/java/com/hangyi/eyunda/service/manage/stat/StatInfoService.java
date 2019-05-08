package com.hangyi.eyunda.service.manage.stat;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.YydOrderCommonDao;
import com.hangyi.eyunda.dao.YydRecordLoginDao;
import com.hangyi.eyunda.dao.YydRecordShipCallDao;
import com.hangyi.eyunda.dao.YydShipDao;
import com.hangyi.eyunda.dao.YydStatLoginDao;
import com.hangyi.eyunda.dao.YydStatOrderDao;
import com.hangyi.eyunda.dao.YydStatShipCallDao;
import com.hangyi.eyunda.dao.YydStatShipDao;
import com.hangyi.eyunda.dao.YydStatUserDao;
import com.hangyi.eyunda.dao.YydUserInfoDao;
import com.hangyi.eyunda.domain.YydOrderCommon;
import com.hangyi.eyunda.domain.YydRecordLogin;
import com.hangyi.eyunda.domain.YydRecordShipCall;
import com.hangyi.eyunda.domain.YydShip;
import com.hangyi.eyunda.domain.YydStatLogin;
import com.hangyi.eyunda.domain.YydStatOrder;
import com.hangyi.eyunda.domain.YydStatShip;
import com.hangyi.eyunda.domain.YydStatShipCall;
import com.hangyi.eyunda.domain.YydStatUser;
import com.hangyi.eyunda.domain.YydUserInfo;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.util.CalendarUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class StatInfoService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydRecordLoginDao recordLoginDao;
	@Autowired
	private YydStatLoginDao statLoginDao;
	@Autowired
	private YydRecordShipCallDao recordShipCallDao;
	@Autowired
	private YydStatShipCallDao statShipCallDao;
	@Autowired
	private YydUserInfoDao userDao;
	@Autowired
	private YydStatUserDao statUserDao;
	@Autowired
	private YydShipDao shipDao;
	@Autowired
	private YydStatShipDao statShipDao;
	@Autowired
	private YydOrderCommonDao orderCommonDao;
	@Autowired
	private YydStatOrderDao statOrderDao;

	/**
	 * 获取登录用户的信息，并设置
	 */
	public void statLogin() {
		YydRecordLogin firstLogin = recordLoginDao.findFirstLogin();

		if (firstLogin == null)
			return;

		Calendar month = CalendarUtil.parseYYYYMM(CalendarUtil.toYYYYMM(firstLogin.getLoginTime()));

		YydStatLogin lastEntity = statLoginDao.getLastEntity();
		if (lastEntity != null)
			month = CalendarUtil.parseYYYYMM(lastEntity.getYearMonth());

		Calendar now = Calendar.getInstance();

		while (CalendarUtil.compare(month, now) <= 0) {
			List<Map<String, Object>> statLoginList = recordLoginDao.getStatLoginList(month);

			for (Map<String, Object> statLogin : statLoginList) {

				YydStatLogin yydStatLogin = statLoginDao.findByYearMonthAndRole(CalendarUtil.toYYYYMM(month),
						(UserPrivilegeCode) statLogin.get("roleCode"));
				if (yydStatLogin == null)
					yydStatLogin = new YydStatLogin();

				yydStatLogin.setYearMonth(CalendarUtil.toYYYYMM(month)); // 统计年月
				yydStatLogin.setRoleCode((UserPrivilegeCode) statLogin.get("roleCode")); // 设置角色
				yydStatLogin.setLoginNum((Integer) statLogin.get("loginNum")); // 设置登录次数
				yydStatLogin.setTimeSpan((Integer) statLogin.get("timeSpan")); // 设置登录时长
				yydStatLogin.setLoginUserNum((Integer) statLogin.get("loginUserNum")); // 设置登录人数

				statLoginDao.save(yydStatLogin);
			}
			month = CalendarUtil.addMonths(month, 1);
		}
	}

	/**
	 * 获取被访问船舶的信息，并设置
	 */
	public void statShipCall() {
		YydRecordShipCall firstShipCall = recordShipCallDao.findFirstShipCall();
		if (firstShipCall == null)
			return;
		// 到现在截止（截止时间）
		Calendar now = Calendar.getInstance();
		// 第一条访问记录的时间
		Calendar month = CalendarUtil.parseYYYYMM(CalendarUtil.toYYYYMM(firstShipCall.getRecordTime()));
		// 最后一条统计船舶访问记录的时间
		YydStatShipCall lastEntity = statShipCallDao.getLastEntity();
		// 如果不是第一次统计设置最后统计记录时间为统计开始时间
		if (lastEntity != null)
			month = CalendarUtil.parseYYYYMM(lastEntity.getYearMonth());

		while (CalendarUtil.compare(month, now) <= 0) {
			List<Map<String, Object>> statShipCallList = recordShipCallDao.getStatShipCallList(month);

			for (Map<String, Object> statShipCall : statShipCallList) {
				String yearMonth = CalendarUtil.toYYYYMM(month);
				YydStatShipCall yydStatShipCall = statShipCallDao.getByYearMonth((yearMonth),
						(UserPrivilegeCode) statShipCall.get("roleCode"));
				if (yydStatShipCall == null)
					yydStatShipCall = new YydStatShipCall();

				yydStatShipCall.setYearMonth(yearMonth); // 统计年月
				yydStatShipCall.setRoleCode((UserPrivilegeCode) statShipCall.get("roleCode")); // 角色
				yydStatShipCall.setCallNum((Integer) statShipCall.get("callNum")); // 访问次数
				yydStatShipCall.setCalledUserNum((Integer) statShipCall.get("calledUserNum")); // 访问人数
				yydStatShipCall.setCalledShipNum((Integer) statShipCall.get("calledShipNum")); // 访问船舶数

				statShipCallDao.save(yydStatShipCall);
			}
			month = CalendarUtil.addMonths(month, 1);
		}
	}

	/**
	 * 获取各角色用户总量及新增量，并设置
	 */
	public void statUser() {
		YydUserInfo firstUser = userDao.findFirstUser();
		if (firstUser == null)
			return;
		// 到现在截止（截止时间）
		Calendar now = Calendar.getInstance();
		// 第一条用户注册记录的时间
		Calendar month = CalendarUtil.parseYYYYMM(CalendarUtil.toYYYYMM(firstUser.getCreateTime()));
		// 最后一条统计用户记录的时间
		YydStatUser lastEntity = statUserDao.getLastEntity();
		if (lastEntity != null)
			month = CalendarUtil.parseYYYYMM(lastEntity.getYearMonth());
		// 统计从有用户注册的那个月开始到当前月截止
		while (CalendarUtil.compare(month, now) <= 0) {

			// 只对没有统计过的月份及当前月进行统计
			YydStatUser statUserMonth = statUserDao.getByYearMonth(CalendarUtil.toYYYYMM(month));
			if (statUserMonth == null)
				statUserMonth = new YydStatUser();

			statUserMonth.setYearMonth(CalendarUtil.toYYYYMM(month));
			statUserMonth.setSumUsers(userDao.getStatSumUser(month));
			statUserMonth.setSumBrokers(userDao.statSumUsersOnPrivil(month, UserPrivilegeCode.manager));
			statUserMonth.setSumHandlers(userDao.statSumUsersOnPrivil(month, UserPrivilegeCode.handler));
			statUserMonth.setSumSailors(userDao.statSumUsersOnPrivil(month, UserPrivilegeCode.sailor));
			statUserMonth.setSumMasters(userDao.statSumUsersOnPrivil(month, UserPrivilegeCode.master));
			statUserMonth.setSumOwners(userDao.statSumUsersOnPrivil(month, UserPrivilegeCode.owner));

			statUserDao.save(statUserMonth);

			month = CalendarUtil.addMonths(month, 1);
		}
	}

	public void statShip() throws Exception {
		YydShip firstShip = shipDao.findFirstShip();
		if (firstShip == null)
			return;

		Calendar month = CalendarUtil.getOnYmdhms(CalendarUtil.getYear(firstShip.getCreateTime()),
				CalendarUtil.getMonth(firstShip.getCreateTime()), 1, 0, 0, 0);

		YydStatShip lastEntity = statShipDao.getLastEntity();
		if (lastEntity != null)
			month = CalendarUtil.getOnYmdhms(Integer.parseInt(lastEntity.getYearMonth().substring(0, 4)),
					Integer.parseInt(lastEntity.getYearMonth().substring(4, 6)), 1, 0, 0, 0);

		Calendar lastMonth = CalendarUtil.getOnYmdhms(CalendarUtil.getYear(Calendar.getInstance()),
				CalendarUtil.getMonth(Calendar.getInstance()), 1, 0, 0, 0);

		while (CalendarUtil.compare(month, lastMonth) <= 0) {
			// 统计到本月末总上线船舶数量
			int sumShip = shipDao.statShipOnMonth(month);
			// 统计本月新上线船舶数量
			int upShip = shipDao.statUpShipOnMonth(month);
			// 统计到上月末总上线船舶数量
			int preSumShip = shipDao.statShipOnMonth(CalendarUtil.addMonths(month, -1));

			YydStatShip yydStatShip = statShipDao.getByYyyyMm(month);
			if (yydStatShip == null)
				yydStatShip = new YydStatShip();

			yydStatShip.setYearMonth(CalendarUtil.toYYYYMM(month)); // 统计年月
			yydStatShip.setSumWares(sumShip); // 本月总上线船舶数量
			yydStatShip.setUpShips(upShip); // 本月新上线船舶数量
			yydStatShip.setDownShips(preSumShip + upShip - sumShip);// 本月总下线船舶数量

			statShipDao.save(yydStatShip);

			month = CalendarUtil.addMonths(month, 1);
		}
	}

	/**
	 * 获取成交合同的信息，并设置
	 */
	public void statOrder() {
		YydOrderCommon firstOrder = orderCommonDao.findFirstOrder();
		if (firstOrder == null)
			return;

		Calendar month = CalendarUtil.getOnYmdhms(CalendarUtil.getYear(firstOrder.getCreateTime()),
				CalendarUtil.getMonth(firstOrder.getCreateTime()), 1, 0, 0, 0);

		YydStatOrder lastEntity = statOrderDao.getLastEntity();
		if (lastEntity != null)
			month = CalendarUtil.getOnYmdhms(Integer.parseInt(lastEntity.getYearMonth().substring(0, 4)),
					Integer.parseInt(lastEntity.getYearMonth().substring(4, 6)), 1, 0, 0, 0);

		Calendar lastMonth = CalendarUtil.getOnYmdhms(CalendarUtil.getYear(Calendar.getInstance()),
				CalendarUtil.getMonth(Calendar.getInstance()), 1, 0, 0, 0);

		// month = CalendarUtil.addMonths(month, -1);
		// lastMonth = CalendarUtil.addMonths(lastMonth, 1);

		String strMonth = CalendarUtil.toYYYYMMDDHHmmss(month);
		String strLastMonth = CalendarUtil.toYYYYMMDDHHmmss(lastMonth);
		logger.info("strMonth = " + strMonth);
		logger.info("strLastMonth = " + strLastMonth);

		while (CalendarUtil.compare(month, lastMonth) <= 0) {
			List<Map<String, Object>> maps = orderCommonDao.statOrderOnMonth(month);
			for (Map<String, Object> map : maps) {

				YydStatOrder statOrder = statOrderDao.getByYyyyMm(CalendarUtil.toYYYYMM(month));
				if (statOrder == null)
					statOrder = new YydStatOrder();

				statOrder.setYearMonth(CalendarUtil.toYYYYMM(month)); // 统计年月

				statOrder.setSumOrderCount(((Long) map.get("sumOrderCount")).intValue());
				statOrder.setSumTransFee(((Double) map.get("sumTransFee")));
				statOrder.setSumBrokerFee((Double) map.get("sumBrokerFee"));
				statOrder.setSumPlatFee((Double) map.get("sumPlatFee"));

				statOrderDao.save(statOrder);
			}
			month = CalendarUtil.addMonths(month, 1);
		}
	}

}
