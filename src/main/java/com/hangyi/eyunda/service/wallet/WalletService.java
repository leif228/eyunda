package com.hangyi.eyunda.service.wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.YydWalletDao;
import com.hangyi.eyunda.dao.YydWalletKhsdDao;
import com.hangyi.eyunda.dao.YydWalletZjjzDao;
import com.hangyi.eyunda.data.account.AccountData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.wallet.UserBankData;
import com.hangyi.eyunda.data.wallet.WalletData;
import com.hangyi.eyunda.data.wallet.WalletKhsdData;
import com.hangyi.eyunda.data.wallet.WalletZjjzData;
import com.hangyi.eyunda.domain.YydWallet;
import com.hangyi.eyunda.domain.YydWalletKhsd;
import com.hangyi.eyunda.domain.enumeric.ApplyReplyCode;
import com.hangyi.eyunda.domain.enumeric.FeeItemCode;
import com.hangyi.eyunda.domain.enumeric.PayStatusCode;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.SettleStyleCode;
import com.hangyi.eyunda.domain.enumeric.WalletOptCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.account.AccountService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class WalletService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydWalletDao walletDao;
	@Autowired
	private YydWalletZjjzDao walletZjjzDao;
	@Autowired
	private YydWalletKhsdDao walletKhsdDao;

	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserBankCardService userBankCardService;
	@Autowired
	private WalletZjjzService walletZjjzService;
	@Autowired
	private WalletKhsdService walletKhsdService;

	// 查询资金托管到期而未确认付款的帐务记录
	public void updateBankCard() {
		List<YydWallet> wallets = walletDao.getAll();

		for (YydWallet wallet : wallets) {
			String paymentNo = wallet.getPaymentNo();

			String hql = "SELECT a FROM YydWalletKhsd a where a.paymentNo='" + paymentNo
					+ "' and (a.tranFunc='KHPAYMENT' or a.tranFunc='API007')";
			List<YydWalletKhsd> khsds = walletKhsdDao.find(hql);

			if (!khsds.isEmpty()) {
				YydWalletKhsd khsd = khsds.get(0);
				String jsonStr = khsd.getRecvParames();

				Gson gson = new Gson();
				Map<String, String> map = gson.fromJson(jsonStr, new TypeToken<Map<String, String>>() {
				}.getType());

				if (map != null && !map.isEmpty()) {
					String plantBankName = map.get("plantBankName");
					String accountNo = map.get("accountNo");

					if (plantBankName != null && !"".equals(plantBankName) && accountNo != null
							&& !"".equals(accountNo)) {
						wallet.setPlantBankName(plantBankName);
						wallet.setBankCardNo4(accountNo);

						walletDao.save(wallet);
					}
				}
			}
		}

		return;
	}

	// 查询资金托管到期而未确认付款的帐务记录
	public List<WalletData> getExpiredBillDatas() {
		List<WalletData> walletDatas = new ArrayList<WalletData>();

		List<YydWallet> wallets = walletDao.getExpiredBills();
		for (YydWallet wallet : wallets)
			walletDatas.add(this.getBillData(wallet));

		return walletDatas;
	}

	public Page<WalletData> findWalletByStyle(String userInfo, SettleStyleCode settleStyle, PayStatusCode paymentStatus,
			ApplyReplyCode refundStatus, String startDate, String endDate, int pageNo, int pageSize) throws Exception {
		Long userId = 0L;
		if (userInfo != null && !"".equals(userInfo)) {
			UserData user = userService.getByLoginName(userInfo);
			if (user == null) {
				throw new Exception("找不到用户：" + userInfo);
			} else {
				userId = user.getId();
			}
		}

		Page<YydWallet> page = walletDao.findWalletByStyle(userId, settleStyle, paymentStatus, refundStatus, startDate,
				endDate, pageNo, pageSize);

		List<WalletData> walletDatas = new ArrayList<WalletData>();
		for (YydWallet yw : (List<YydWallet>) page.getResult()) {
			WalletData walletData = this.getBillData(yw);
			walletDatas.add(walletData);
		}

		Page<WalletData> pageData = new Page<WalletData>();
		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(walletDatas);

		return pageData;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<WalletData> walletRefundQuery(ApplyReplyCode refundStatus, String userInfo, String startDate,
			String endDate, int pageNo, int pageSize) throws Exception {
		List<WalletData> walletDatas = new ArrayList<WalletData>();

		Long userId = 0L;
		if (userInfo != null && !"".equals(userInfo)) {
			UserData user = userService.getByLoginName(userInfo);
			if (user == null) {
				throw new Exception("找不到用户：" + userInfo);
			} else {
				userId = user.getId();
			}
		}

		Page page = walletDao.findByRefundStatus(refundStatus, userId, startDate, endDate, pageNo, pageSize);

		for (YydWallet yw : (List<YydWallet>) page.getResult()) {
			WalletData walletData = this.getBillData(yw);
			walletDatas.add(walletData);
		}

		page.setResult(walletDatas);

		return page;
	}

	public Page<WalletData> walletErrorQuery(YesNoCode errorStatus, String userInfo, String startDate, String endDate,
			int pageNo, int pageSize, String khsdOrZjjz) throws Exception {
		List<WalletData> walletDatas = new ArrayList<WalletData>();

		Long userId = 0L;
		if (userInfo != null && !"".equals(userInfo)) {
			UserData user = userService.getByLoginName(userInfo);
			if (user == null) {
				throw new Exception("找不到用户：" + userInfo);
			} else {
				userId = user.getId();
			}
		}

		Page<Long> page;
		if (khsdOrZjjz != null && "khsd".equals(khsdOrZjjz))
			page = walletDao.findByKhsdErrorStatus(errorStatus, userId, startDate, endDate, pageNo, pageSize);
		else
			page = walletDao.findByZjjzErrorStatus(errorStatus, userId, startDate, endDate, pageNo, pageSize);

		for (Long walletId : (List<Long>) page.getResult()) {
			WalletData walletData = this.getWalletDataInclZjjz(walletId);
			walletDatas.add(walletData);
		}

		Page<WalletData> p = new Page<WalletData>(page.getPageNo(), page.getPageSize(), null, null, null,
				page.getTotalCount());
		p.setResult(walletDatas);

		return p;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<WalletData> getWalletDatas(UserData userData, String startTime, String endTime, int pageNo,
			int pageSize) {
		List<WalletData> walletDatas = new ArrayList<WalletData>();

		Page page = walletDao.findByUserId(userData.getId(), startTime, endTime, pageNo, pageSize);

		for (YydWallet yw : (List<YydWallet>) page.getResult()) {
			WalletData walletData = this.getBillData(yw);

			if (walletData != null) {
				walletData.setOps(this.getOption(walletData, userData));
				walletData.setDescByLoginUser(userData);
			}

			walletDatas.add(walletData);
		}

		page.setResult(walletDatas);

		return page;
	}

	private Map<String, Boolean> getOption(WalletData walletData, UserData userData) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();

		for (WalletOptCode wo : WalletOptCode.values()) {
			map.put(wo.toString(), false);
		}

		if (walletData.getFeeItem() == FeeItemCode.bonus)
			return map;

		// 买家
		if (walletData.getBuyerId().equals(userData.getId())) {
			// 如果未提现可提现可删除
			if (walletData.getPaymentStatus() == PayStatusCode.WAIT_PAYMENT
					&& walletData.getSettleStyle() == SettleStyleCode.fetch) {
				map.put(WalletOptCode.fetch.toString(), true);
				map.put(WalletOptCode.delete.toString(), true);
			}
			// 如果未支付可支付可删除，
			if (walletData.getPaymentStatus() == PayStatusCode.WAIT_PAYMENT
					&& walletData.getSettleStyle() != SettleStyleCode.fetch
					&& walletData.getKhRefundFlag() != YesNoCode.yes) {
				map.put(WalletOptCode.pay.toString(), true);
				map.put(WalletOptCode.delete.toString(), true);
			}
			// 确认付款
			if (walletData.getPaymentStatus() == PayStatusCode.WAIT_CONFIRM) {
				map.put(WalletOptCode.confirmPay.toString(), true);
				// 如果已经支付且未申请退款
				if ((walletData.getRefundStatus() == ApplyReplyCode.noapply
						|| walletData.getRefundStatus() == ApplyReplyCode.noreply)) {
					map.put(WalletOptCode.applyRefund.toString(), true);
				}
			}
		}
		// 卖家
		if (walletData.getSellerId().equals(userData.getId())) {
			// 若果已经支付且申请退款卖家可以处理退款
			if (walletData.getRefundStatus() == ApplyReplyCode.apply
					&& walletData.getPaymentStatus() == PayStatusCode.WAIT_CONFIRM) {
				map.put(WalletOptCode.refund.toString(), true);
			}
		}
		// 打印账单按钮
		if (walletData.getPaymentStatus() == PayStatusCode.TRADE_FINISHED) {
			if (walletData.getSettleStyle() == SettleStyleCode.fill
					|| walletData.getSettleStyle() == SettleStyleCode.fetch) {
				map.put(WalletOptCode.masterfee.toString(), true);
			} else {
				if (walletData.getBuyerId().equals(userData.getId())) {
					map.put(WalletOptCode.masterfee.toString(), true);
				} else if (walletData.getBrokerId().equals(userData.getId())) {
					map.put(WalletOptCode.brokerfee.toString(), true);
				} else if (walletData.getSellerId().equals(userData.getId())) {
					map.put(WalletOptCode.masterfee.toString(), true);
					if (!walletData.getBrokerId().equals(0L))
						map.put(WalletOptCode.brokerfee.toString(), true);
					map.put(WalletOptCode.platfee.toString(), true);
				} else {
					;
				}
			}
		}

		return map;
	}

	public WalletData getWalletDataInclZjjz(Long walletId) {
		WalletData walletData = this.getBillData(walletDao.get(walletId));

		if (walletData != null) {
			List<WalletZjjzData> walletZjjzDatas = walletZjjzService.findByPaymentNo(walletData.getPaymentNo());
			walletData.setWalletZjjzDatas(walletZjjzDatas);

			YesNoCode es = YesNoCode.no;
			for (WalletZjjzData walletZjjzData : walletZjjzDatas) {
				if (!"000000".equals(walletZjjzData.getRspCode())) {
					es = YesNoCode.yes;
					break;
				}
			}
			walletData.setErrorStatus(es);

			List<WalletKhsdData> walletKhsdDatas = walletKhsdService.findByPaymentNo(walletData.getPaymentNo());
			walletData.setWalletKhsdDatas(walletKhsdDatas);

			YesNoCode khes = YesNoCode.no;
			for (WalletKhsdData walletKhsdData : walletKhsdDatas) {
				if (!"01".equals(walletKhsdData.getRspCode())) {
					khes = YesNoCode.yes;
					break;
				}
			}
			walletData.setKhErrorStatus(khes);
		}

		return walletData;
	}

	// 1 根据账务ID取得账务信息
	public WalletData getBillData(Long id) {
		YydWallet wallet = walletDao.get(id);
		return this.getBillData(wallet);
	}

	// 1 根据账务Entity取得账务信息
	public WalletData getBillData(YydWallet yw) {
		if (yw != null) {
			WalletData walletData = new WalletData();
			CopyUtil.copyProperties(walletData, yw);

			UserData sellerData = userService.getById(yw.getSellerId());
			if (sellerData != null) {
				AccountData ad = accountService.getAccountByUserId(yw.getSellerId(), PayStyleCode.pinganpay);
				UserBankData bd = userBankCardService.findBankCard(yw.getSellerId());
				sellerData.setAccountData(ad);
				sellerData.setUserBankData(bd);
				walletData.setSellerData(sellerData);
				if (ad != null) {
					walletData.setRcvCardNo(ad.getAccountNo());
					walletData.setRcvAccountName(ad.getAccounter());
				}
			}

			UserData brokerData = userService.getById(yw.getBrokerId());
			if (brokerData != null) {
				AccountData ad = accountService.getAccountByUserId(yw.getBrokerId(), PayStyleCode.pinganpay);
				UserBankData bd = userBankCardService.findBankCard(yw.getBrokerId());
				brokerData.setAccountData(ad);
				brokerData.setUserBankData(bd);
				walletData.setBrokerData(brokerData);
				if (ad != null) {
					walletData.setBrokerCardNo(ad.getAccountNo());
					walletData.setBrokerAccountName(ad.getAccounter());
				}
			}

			UserData buyerData = userService.getById(yw.getBuyerId());
			if (buyerData != null) {
				AccountData ad = accountService.getAccountByUserId(yw.getBuyerId(), PayStyleCode.pinganpay);
				UserBankData bd = userBankCardService.findBankCard(yw.getBuyerId());
				buyerData.setAccountData(ad);
				buyerData.setUserBankData(bd);
				walletData.setBuyerData(buyerData);
				if (ad != null) {
					walletData.setSndCardNo(ad.getAccountNo());
					walletData.setSndAccountName(ad.getAccounter());
				}
			}

			if (yw.getGmtPayment() != null)
				walletData.setGmtPayment(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(yw.getGmtPayment()));
			if (yw.getGmtSurety() != null)
				walletData.setGmtSurety(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(yw.getGmtSurety()));
			if (yw.getGmtRefund() != null)
				walletData.setGmtRefund(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(yw.getGmtRefund()));

			return walletData;
		}
		return null;
	}

	// 2 用户时间段账务分页查询
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page getBillDatas(Long userId, String startTime, String endTime, int pageNo) {
		List<WalletData> walletDatas = new ArrayList<WalletData>();

		Page page = walletDao.findByUserId(userId, startTime, endTime, pageNo, 10);
		for (YydWallet wallet : (List<YydWallet>) page.getResult())
			walletDatas.add(this.getBillData(wallet));

		page.setResult(walletDatas);

		return page;
	}

	/**
	 * @param paymentNo
	 *            eyunda电商平台交易号
	 * @return
	 */
	public WalletData getByPaymentNo(String paymentNo) {

		YydWallet yydWallet = walletDao.findByPaymentNo(paymentNo);

		return this.getBillData(yydWallet);
	}

	public WalletData getByAngel(Long receiverId, Long cabinId) {
		YydWallet yydWallet = walletDao.findByAngel(receiverId, cabinId);
		return this.getBillData(yydWallet);
	}

	public List<WalletData> findByOrderId(Long orderId, FeeItemCode feeItem) {
		List<WalletData> wds = new ArrayList<WalletData>();

		List<YydWallet> yydWallets = walletDao.findByOrderId(feeItem, orderId);

		for (YydWallet yydWallet : yydWallets) {
			WalletData wd = this.getBillData(yydWallet);
			wds.add(wd);
		}

		return wds;
	}

	public int updatePayStatus(String paymentNo, PayStatusCode paymentStatus, String body) {
		int n = walletDao.batchExecute("update YydWallet set paymentStatus = ?, body = ? where paymentNo = ?",
				new Object[] { paymentStatus, body, paymentNo });
		return n;
	}

	public int updatePaymentNo(Long walletId, String newPaymentNo) {
		int n = walletDao.batchExecute("update YydWallet set paymentNo = ? where id = ?",
				new Object[] { newPaymentNo, walletId });
		return n;
	}

	public boolean deleteWallet(Long walletId) {
		YydWallet wallet = walletDao.get(walletId);
		if (wallet != null) {
			walletZjjzDao.batchExecute("delete from YydWalletZjjz where paymentNo = ?", wallet.getPaymentNo());
			walletKhsdDao.batchExecute("delete from YydWalletKhsd where paymentNo = ?", wallet.getPaymentNo());

			walletDao.delete(wallet);
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<WalletData> walletPlatQuery(PayStatusCode statusCode, String startDate, String endDate, int pageNo,
			int pageSize) {

		List<WalletData> walletDatas = new ArrayList<WalletData>();

		Page page = walletDao.findByPayStatus(statusCode, startDate, endDate, pageNo, pageSize);

		for (YydWallet yw : (List<YydWallet>) page.getResult()) {
			WalletData walletData = this.getBillData(yw);
			walletDatas.add(walletData);
		}

		page.setResult(walletDatas);

		return page;
	}

	public List<WalletData> findByKhWaitStatus(YesNoCode waitStatus) {
		List<WalletData> walletDatas = new ArrayList<WalletData>();

		List<YydWallet> wallets = walletDao.findByKhWaitStatus(waitStatus);
		for (YydWallet wallet : wallets)
			walletDatas.add(this.getBillData(wallet));

		return walletDatas;
	}

}
