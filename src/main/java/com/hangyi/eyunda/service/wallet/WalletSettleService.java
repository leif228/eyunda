package com.hangyi.eyunda.service.wallet;

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

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydWalletSettleDao;
import com.hangyi.eyunda.data.account.AccountData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.manage.HolidayData;
import com.hangyi.eyunda.data.wallet.CustAcctData;
import com.hangyi.eyunda.data.wallet.WalletSettleData;
import com.hangyi.eyunda.domain.YydWalletSettle;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.WalletSettleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.EnumConst.RecentPeriodCode;
import com.hangyi.eyunda.service.account.AccountService;
import com.hangyi.eyunda.service.manage.HolidayService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.NumberFormatUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class WalletSettleService extends BaseService<YydWalletSettle, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydWalletSettleDao walletSettleDao;
	@Autowired
	private WalletFillPayService pinganPayService;
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private HolidayService holidayService;

	@Override
	public PageHibernateDao<YydWalletSettle, Long> getDao() {
		return (PageHibernateDao<YydWalletSettle, Long>) walletSettleDao;
	}

	public Double getUsableMoney(Long userId) {
		YydWalletSettle ws = walletSettleDao.getTodayLastOne(userId);
		if (ws != null)
			return ws.getUsableMoney();
		else
			return 0.00D;
	}

	public Double getFetchableMoney(Long userId) {
		return walletSettleDao.getFetchableMoney(userId);
	}

	public WalletSettleData getTodayLastOne(Long userId) {
		YydWalletSettle ws = walletSettleDao.getTodayLastOne(userId);
		return this.getWalletSettleData(ws);
	}

	public WalletSettleData getWalletSettleData(YydWalletSettle yydWalletSettle) {
		if (yydWalletSettle != null) {
			WalletSettleData data = new WalletSettleData();
			CopyUtil.copyProperties(data, yydWalletSettle);

			data.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(yydWalletSettle.getCreateTime()));

			String fdt[] = CalendarUtil.toHumanFormat(yydWalletSettle.getCreateTime()).split(" ");
			data.setPayDate(fdt[0]);
			data.setPayTime(fdt[1]);

			return data;
		}
		return null;
	}

	public void saveWalletSettle(Long userId, Long walletId, WalletSettleCode wsCode, Double money) {
		YydWalletSettle todayLastWs = walletSettleDao.getTodayLastOne(userId);
		if (todayLastWs == null)
			todayLastWs = new YydWalletSettle();
		YydWalletSettle ws = new YydWalletSettle();
		ws.setCreateTime(Calendar.getInstance());
		ws.setUserId(userId);
		ws.setWalletId(walletId);
		ws.setSubject(wsCode.getDescription());
		ws.setMoney(NumberFormatUtil.format(money));
		ws.setUsableMoney(NumberFormatUtil.format(todayLastWs.getUsableMoney() + money));
		ws.setFetchableMoney(-1.00D);
		walletSettleDao.save(ws);
	}

	public int updateFetchableMoney() {
		int n = -1;
		Calendar date = CalendarUtil.now();
		HolidayData holidayData = holidayService.getHoliday(date);

		if (date.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && date.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
				&& holidayData == null) {
			String hql = "update YydWalletSettle set fetchableMoney = usableMoney where fetchableMoney = -1.00";
			n = walletSettleDao.batchExecute(hql);
		}
		return n;
	}

	// 获取用户见证宝钱包
	private CustAcctData getUserCustAcctData(Long userId) throws Exception {
		CustAcctData custAcctData = null;

		if (userId == 0L) {
			List<CustAcctData> custAcctDatas = pinganPayService.api6010("", "3", "1");
			if (custAcctDatas != null) {
				for (CustAcctData cad : custAcctDatas) {
					if ("3".equals(cad.getCustType())) {
						custAcctData = cad;
					}
				}
			}
		} else {
			AccountData accountData = accountService.getAccountByUserId(userId, PayStyleCode.pinganpay);
			List<CustAcctData> custAcctDatas = pinganPayService.api6010(accountData.getAccountNo(), "2", "1");
			if (!custAcctDatas.isEmpty())
				custAcctData = custAcctDatas.get(0);
		}

		return custAcctData;
	}

	// 用户账务分页列表
	public Page<WalletSettleData> getSettleByUserId(Long userId, int pageNo, int pageSize, String startTime,
			String endTime) throws Exception {

		Page<YydWalletSettle> page = walletSettleDao.findByUserId(userId, pageNo, pageSize, startTime, endTime);

		List<WalletSettleData> settleDatas = new ArrayList<WalletSettleData>();
		for (YydWalletSettle walletSettle : (List<YydWalletSettle>) page.getResult()) {
			WalletSettleData settleData = this.getWalletSettleData(walletSettle);
			settleDatas.add(settleData);
		}
		Page<WalletSettleData> pg = new Page<WalletSettleData>(page.getPageNo(), page.getPageSize(), page.getOrderBy(),
				page.getOrder(), settleDatas, page.getTotalCount());

		return pg;
	}

	// 用户账务分页列表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<WalletSettleData> walletSettleQuery(String userInfo, RecentPeriodCode termCode, int pageNo,
			int pageSize) throws Exception {

		Long userId = 0L;
		if (userInfo != null && !"".equals(userInfo)) {
			UserData user = userService.getByLoginName(userInfo);
			if (user == null) {
				throw new Exception("找不到用户：" + userInfo);
			} else {
				userId = user.getId();
			}
		}

		Page page = walletSettleDao.findUserByTerm(userId, termCode, pageNo, pageSize);

		List<WalletSettleData> settleDatas = new ArrayList<WalletSettleData>();
		for (Long uid : (List<Long>) page.getResult()) {
			WalletSettleData settleData = this.getTodayLastOne(uid);
			// 本地可用余额
			Double usableMoney = NumberFormatUtil.format(settleData.getUsableMoney());
			// 本地可取余额
			Double fetchableMoney = NumberFormatUtil.format(this.getFetchableMoney(uid));

			CustAcctData acctData = this.getUserCustAcctData(uid);
			Double totalBalance = 0.00D;
			Double totalTranOutAmount = 0.00D;
			if (acctData != null) {
				// 见证宝可用余额
				totalBalance = NumberFormatUtil.format(acctData.getTotalBalance());
				// 见证宝可取余额
				totalTranOutAmount = NumberFormatUtil.format(acctData.getTotalTranOutAmount());
			}

			if (totalBalance.doubleValue() != usableMoney.doubleValue()
					|| totalTranOutAmount.doubleValue() != fetchableMoney.doubleValue())
				settleData.setCheckOpt(YesNoCode.yes);

			settleData.setUsableMoney(usableMoney);
			settleData.setFetchableMoney(fetchableMoney);
			settleData.setJzbTotalBalance(totalBalance);
			settleData.setJzbTotalTranOutAmount(totalTranOutAmount);

			settleDatas.add(settleData);
		}
		page.setResult(settleDatas);

		return page;
	}

	// 一个用户的账务分页列表
	public List<WalletSettleData> walletSettleQuery(Long userId, RecentPeriodCode termCode) throws Exception {
		if (termCode == null)
			termCode = RecentPeriodCode.THREE_DAYS;

		List<YydWalletSettle> page = walletSettleDao.findSettleByUserId(userId, termCode);

		List<WalletSettleData> settleDatas = new ArrayList<WalletSettleData>();
		for (YydWalletSettle yydSettle : page) {
			WalletSettleData settleData = this.getWalletSettleData(yydSettle);
			settleDatas.add(settleData);
		}

		return settleDatas;
	}

}
