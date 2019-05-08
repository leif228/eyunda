package com.hangyi.eyunda.service.wallet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.YydUserInfoDao;
import com.hangyi.eyunda.dao.YydWalletDao;
import com.hangyi.eyunda.data.account.AccountData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.wallet.UserBankData;
import com.hangyi.eyunda.domain.PubPayCnapsBank;
import com.hangyi.eyunda.domain.YydUserInfo;
import com.hangyi.eyunda.domain.YydWallet;
import com.hangyi.eyunda.domain.enumeric.ApplyReplyCode;
import com.hangyi.eyunda.domain.enumeric.BankCode;
import com.hangyi.eyunda.domain.enumeric.FeeItemCode;
import com.hangyi.eyunda.domain.enumeric.PayStatusCode;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.SettleStyleCode;
import com.hangyi.eyunda.domain.enumeric.UserTypeCode;
import com.hangyi.eyunda.domain.enumeric.WalletSettleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.PaySequenceService;
import com.hangyi.eyunda.service.account.AccountService;
import com.hangyi.eyunda.service.account.CompanyService;
import com.hangyi.eyunda.service.pubpay.PubPayCnapsBankService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.DESHelper;
import com.hangyi.eyunda.util.MD5;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class WalletFetchService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydUserInfoDao userDao;
	@Autowired
	private YydWalletDao walletDao;

	@Autowired
	private UserBankCardService walletBankService;
	@Autowired
	private WalletSettleService walletSettleService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private PubPayCnapsBankService pubPayCnapsBankService;
	@Autowired
	private PaySequenceService paySequenceService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private WalletFillPayService walletFillPayService;

	// 3 取消绑定银行卡
	public boolean unbindCard(UserData userData, Long bankCardId) throws Exception {
		UserBankData bankData = walletBankService.getBindBankData(bankCardId);

		// 用户钱包账号
		AccountData ad = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
		String accountNo = ad != null ? ad.getAccountNo() : "";

		if (bankData != null) {
			try {
				pinganApi6065(userData, bankData, accountNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("解除绑定银行卡账户姓名：" + bankData.getAccountName());
			logger.info("解除绑定银行卡号：" + bankData.getCardNo());
			logger.info("解除绑定银行名称：" + bankData.getBankCode().getDescription());

			ad.setMobile("");
			accountService.saveAccount(ad);

			return walletBankService.localUnBindCard(bankCardId);
		} else
			return false;

	}

	// 会员子账户开立Api6000
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String pinganApi6000(UserData userData) throws Exception {
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6000"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
		parmaKeyDict.put("FuncFlag", "1"); // 功能标志1：开户
		parmaKeyDict.put("ThirdCustId", userData.getId().toString()); // 交易网会员代码
		parmaKeyDict.put("CustProperty", "00"); // 会员属性
		parmaKeyDict.put("Email", userData.getEmail()); // 邮箱

		Map retKeyDict = walletFillPayService.sendMessage2BankRespMap(parmaKeyDict, userData.getId().toString());

		return (String) retKeyDict.get("CustAcctId");// 银行返回的会员子账户
	}

	// 申请短信动态码Api6082
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean pinganApi6082(UserData userData, String tranType, String paymentNo, Double totalFee,
			HashMap<String, String> rspMap) throws Exception {

		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6082"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号

		parmaKeyDict.put("ThirdCustId", userData.getId().toString()); // 交易网会员代码
		parmaKeyDict.put("CustAcctId", userData.getAccountData().getAccountNo()); // 子账户账号
		parmaKeyDict.put("TranType", tranType); // 交易类型 1：提现 2：支付
		parmaKeyDict.put("TranAmount", totalFee.toString()); // 交易金额

		Map retKeyDict = walletFillPayService.sendMessage2BankRespMap(parmaKeyDict, paymentNo);

		rspMap.put("serialNo", (String) retKeyDict.get("SerialNo"));
		rspMap.put("revMobilePhone", (String) retKeyDict.get("RevMobilePhone"));

		return true;
	}

	// 会员提现（支持手续费）Api6085
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean pinganApi6085(UserData userData, String paymentNo, Double totalFee, Double handFee, String serialNo,
			String MessageCode) throws Exception {

		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6085"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号

		parmaKeyDict.put("CustAcctId", userData.getAccountData().getAccountNo()); // 子账户账号
		parmaKeyDict.put("ThirdCustId", userData.getId().toString()); // 交易网会员代码
		parmaKeyDict.put("CustName", userData.getAccountData().getAccounter()); // 子账户名称
		parmaKeyDict.put("OutAcctId", userData.getUserBankData().getCardNo()); // 提现账号
		parmaKeyDict.put("OutAcctIdName", userData.getUserBankData().getAccountName()); // 提现账户名称
		parmaKeyDict.put("CcyCode", "RMB"); // 币种
		parmaKeyDict.put("TranAmount", totalFee.toString()); // 申请提现金额
		parmaKeyDict.put("HandFee", handFee.toString()); // 提现手续费 收取给平台的。
		parmaKeyDict.put("SerialNo", serialNo); // 短信指令号
		parmaKeyDict.put("MessageCode", MessageCode); // 短信验证码

		return walletFillPayService.sendMessage2Bank(parmaKeyDict, paymentNo);
	}

	// 会员解绑提现账户Api6065
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean pinganApi6065(UserData userData, UserBankData bankData, String accountNo) throws Exception {

		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6065"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号

		parmaKeyDict.put("FuncFlag", "1"); // 功能标志 1：解绑
		parmaKeyDict.put("CustAcctId", accountNo); // 子账户账号
		parmaKeyDict.put("AcctId", bankData.getCardNo()); // 会员账号
		parmaKeyDict.put("ThirdCustId", userData.getId().toString()); // 交易网会员代码

		return walletFillPayService.sendMessage2Bank(parmaKeyDict, userData.getId().toString());
	}

	// 4.1 获取绑定银行卡列表(分页)
	@SuppressWarnings("rawtypes")
	public Page getBindCards(UserData userData, int pageNo) {
		try {
			// 调用银行接口，取得绑定银行卡列表
			Page page = walletBankService.getBankDatas(userData.getId(), pageNo);

			return page;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return null;
	}

	/**
	 * 4.2 获取绑定银行卡列表，不包含钱包
	 * 
	 * @param userData
	 * @return
	 */
	public List<UserBankData> getBindCards(UserData userData) {
		return this.getBindCards(userData, false);
	}

	/**
	 * 4.3 获取绑定银行卡列表和钱包
	 * 
	 * @param userData
	 * @param flag
	 *            true:包含钱包
	 * @return
	 */
	public List<UserBankData> getBindCards(UserData userData, boolean flag) {
		List<UserBankData> bankDatas = new ArrayList<UserBankData>();
		try {
			AccountData accountData = null;
			if (flag) {
				accountData = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
				if (accountData != null) {
					UserBankData userBankData = new UserBankData();
					userBankData.setUserId(userData.getId());
					userBankData.setAccountName(accountData.getAccounter());
					userBankData.setBankCode(BankCode.EYUNDA);
					userBankData.setCardNo(accountData.getAccountNo());
					bankDatas.add(userBankData);
				}
			}
			bankDatas.addAll(walletBankService.getBankDatas(userData.getId()));
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return bankDatas;
	}

	// 6 提现
	private YydWallet fetch(UserData userData, double fetchMoney, Long walletId) throws Exception {
		// 预保存提现记录
		YydWallet yydWallet = walletDao.get(walletId);
		if (yydWallet == null) {
			yydWallet = new YydWallet();

			yydWallet.setSettleStyle(SettleStyleCode.fetch);
			yydWallet.setPaymentNo(paySequenceService.getPaymentNo());
			yydWallet.setFeeItem(FeeItemCode.outaccount);
			yydWallet.setOrderId(0L);

			yydWallet.setSellerId(userData.getId());

			yydWallet.setBrokerId(0L);

			yydWallet.setBuyerId(userData.getId());

			yydWallet.setSubject(SettleStyleCode.fetch.getDescription() + " ¥:" + fetchMoney + "元");
			yydWallet.setBody(CalendarUtil.toYYYY_MM_DD_HH_MM(CalendarUtil.now()) + " 从钱包"
					+ SettleStyleCode.fetch.getDescription() + " ¥:" + fetchMoney + "元到"
					+ userData.getUserBankData().getBankCode().getDescription() + "("
					+ userData.getUserBankData().getAccountName() + ":" + userData.getUserBankData().getCardNo() + ")");
			yydWallet.setTotalFee(fetchMoney);

			yydWallet.setPaymentStatus(PayStatusCode.WAIT_PAYMENT);
			yydWallet.setGmtPayment(CalendarUtil.now());

			yydWallet.setSuretyDays(0);
			yydWallet.setGmtSurety(CalendarUtil.now());

			yydWallet.setRefundStatus(ApplyReplyCode.noapply);
			yydWallet.setGmtRefund(CalendarUtil.now());

			walletDao.save(yydWallet);
		}

		return yydWallet;

	}

	public Map<String, String> pinganValidatePW(UserData userData, AccountData ad, String orig,
			HttpServletRequest request) throws Exception {
		Map<String, String> content = new HashMap<String, String>();

		content.put("orderid", paySequenceService.getLogNo()); // 请求流水号
		content.put("P2PCode", Constants.QYDM); // 第三方平台ID
		content.put("P2PType", "2"); // 第三方平台类型 2：非代扣
		content.put("thirdCustId", userData.getId().toString()); // 交易网会员代码
		content.put("custAccId", ad.getAccountNo()); // 子账户账号
		content.put("name", ad.getAccounter()); // 会员名称
		content.put("idType", "1"); // 会员证件类型
		content.put("idNo", ad.getIdCode()); // 会员证件号码
		content.put("mobile", ad.getMobile()); // 会员手机号
		content.put("type", "V"); // 交易类型
		content.put("orig", orig); // 签名源数据串

		String validatepwNotify = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + request.getServletPath() + "/pingan/validatepwNotify";
		String validatepwReturn = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + request.getServletPath() + "/pingan/validatepwReturn";

		content.put("notifyUrl", validatepwNotify); // 交易结果通知第三方url
		content.put("returnurl", validatepwReturn); // 回调第三方url

		return content;
	}

	// 设置收款银行卡
	public boolean setRcvCard(UserData userData, Long cardId) {
		try {
			UserBankData userBankData = walletBankService.findBankCard(userData.getId());
			if (userBankData != null) {
				if (userBankData.getId().longValue() == cardId) {
					walletBankService.setRcvCard(cardId, YesNoCode.yes);
					return true;
				} else {
					walletBankService.setRcvCard(userBankData.getId(), YesNoCode.no);
					walletBankService.setRcvCard(cardId, YesNoCode.yes);
					return true;
				}
			} else {
				UserBankData bankData = walletBankService.getBindBankData(cardId);
				if (bankData != null) {
					walletBankService.setRcvCard(cardId, YesNoCode.yes);
					return true;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	public boolean prebindCard(UserData userData, String idCode, String accountName, String cardNo, String mobilePhone,
			String bankType, String sbType, String bankCode, String openBankId, int type) throws Exception {

		// 用户钱包账号
		AccountData ad = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);

		// 处理老用户没有平安子帐号
		if (ad == null) {
			// 调接口创建平安子账户
			String custAcctId = this.pinganApi6000(userData);
			// 存账户表
			ad = new AccountData();
			ad.setUserId(userData.getId());
			ad.setPayStyle(PayStyleCode.pinganpay);
			ad.setAccountNo(custAcctId);
			ad.setAccounter(userData.getTrueName());

			accountService.saveAccount(ad);
		}
		if (type == 1) {
			return pinganApi6066(userData, ad.getAccountNo(), idCode, accountName, cardNo, mobilePhone, bankType,
					sbType, bankCode, openBankId);
		} else if (type == 2) {
			return pinganApi6055(userData, ad.getAccountNo(), idCode, accountName, cardNo, mobilePhone, bankType,
					sbType, bankCode, openBankId);
		}
		return false;

	}

	// 会员绑定提现账户-小额鉴权Api6055
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean pinganApi6055(UserData userData, String accountNo, String idCode, String accountName, String cardNo,
			String mobilePhone, String bankType, String sbType, String bankCode, String openBankId) throws Exception {

		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6055"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号

		parmaKeyDict.put("CustAcctId", accountNo); // 子账户账号
		parmaKeyDict.put("ThirdCustId", userData.getId().toString()); // 交易网会员代码
		parmaKeyDict.put("CustName", accountName); // 会员名称
		parmaKeyDict.put("IdType", "52"); // 会员证件类型 1身份证，52组织机构代码证
		parmaKeyDict.put("IdCode", idCode); // 会员证件号码
		parmaKeyDict.put("AcctId", cardNo); // 会员账号
		if ("pinganBank".equals(bankType)) {
			parmaKeyDict.put("BankType", "1"); // 银行类型 1：本行 2：他行
			parmaKeyDict.put("BankName", "平安银行"); // 开户行名称

		} else if ("otherBank".equals(bankType)) {
			parmaKeyDict.put("BankType", "2"); // 银行类型 1：本行 2：他行
			if ("small5".equals(sbType)) {
				parmaKeyDict.put("BankName", BankCode.valueOf(bankCode).getDescription()); // 开户行名称
				parmaKeyDict.put("SBankCode", BankCode.valueOf(bankCode).getCode()); // 超级网银行号
			} else if ("big5".equals(sbType)) {
				PubPayCnapsBank ppcb = pubPayCnapsBankService.get(Long.valueOf(openBankId));
				parmaKeyDict.put("BankName", ppcb != null ? ppcb.getBankName() : ""); // 开户行名称
				parmaKeyDict.put("BankCode", ppcb != null ? ppcb.getBankCode() : ""); // 大小额行号
			}

		}
		parmaKeyDict.put("MobilePhone", mobilePhone); // 手机号

		return walletFillPayService.sendMessage2Bank(parmaKeyDict, userData.getId().toString());
	}

	// 会员绑定提现账户-银联验证Api6066
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean pinganApi6066(UserData userData, String accountNo, String idCode, String accountName, String cardNo,
			String mobilePhone, String bankType, String sbType, String bankCode, String openBankId) throws Exception {

		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6066"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号

		parmaKeyDict.put("CustAcctId", accountNo); // 子账户账号
		parmaKeyDict.put("ThirdCustId", userData.getId().toString()); // 交易网会员代码
		parmaKeyDict.put("CustName", accountName); // 会员名称
		parmaKeyDict.put("IdType", "1"); // 会员证件类型 1身份证
		parmaKeyDict.put("IdCode", idCode); // 会员证件号码
		parmaKeyDict.put("AcctId", cardNo); // 会员账号
		if ("pinganBank".equals(bankType)) {
			parmaKeyDict.put("BankType", "1"); // 银行类型 1：本行 2：他行
			parmaKeyDict.put("BankName", "平安银行"); // 开户行名称

		} else if ("otherBank".equals(bankType)) {
			parmaKeyDict.put("BankType", "2"); // 银行类型 1：本行 2：他行
			if ("small5".equals(sbType)) {
				parmaKeyDict.put("BankName", BankCode.valueOf(bankCode).getDescription()); // 开户行名称
				parmaKeyDict.put("SBankCode", BankCode.valueOf(bankCode).getCode()); // 超级网银行号
			} else if ("big5".equals(sbType)) {
				PubPayCnapsBank ppcb = pubPayCnapsBankService.get(Long.valueOf(openBankId));
				parmaKeyDict.put("BankName", ppcb != null ? ppcb.getBankName() : ""); // 开户行名称
				parmaKeyDict.put("BankCode", ppcb != null ? ppcb.getBankCode() : ""); // 大小额行号
			}

		}
		parmaKeyDict.put("MobilePhone", mobilePhone); // 手机号

		return walletFillPayService.sendMessage2Bank(parmaKeyDict, userData.getId().toString());
	}

	public boolean bindCard(UserData userData, String messageCode, String cardNo, String bankCode, String accountName,
			String idCode, String mobilePhone, String paypwd, int type) throws Exception {
		// 修改企业用户公司名称
		if (userData.getUserType() == UserTypeCode.enterprise) {
			String oldName = userData.getTrueName();
			String newName = accountName;
			if (oldName != null && newName != null && !newName.equals(oldName)) {
				companyService.updateCompName(oldName, newName);
			}
		}

		// 用户钱包账号
		AccountData ad = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
		String accountNo = ad != null ? ad.getAccountNo() : "";
		boolean flag = false;
		if (type == 1) {
			flag = pinganApi6067(userData, accountNo, messageCode, cardNo);// 银联卡
		} else if (type == 2) {
			flag = pinganApi6064(userData, accountNo, messageCode, cardNo);// 对公账户
		}
		if (flag) {
			// 绑定成功则设置身份证、绑定手机
			ad.setIdCode(idCode);
			ad.setAccounter(accountName);
			ad.setMobile(mobilePhone);
			ad.setPayPassWord(YesNoCode.yes);

			accountService.saveAccount(ad);

			YydUserInfo yui = userDao.get(userData.getId());
			String encryptPaypwd = MD5.toMD5(paypwd + Constants.SALT_VALUE + userData.getId());
			yui.setPaypwd(encryptPaypwd);
			yui.setMobile(DESHelper.DoDES(mobilePhone, Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE));
			yui.setTrueName(accountName);
			userDao.save(yui);

			UserBankData bankData = new UserBankData();
			bankData.setAccountName(accountName);
			bankData.setBankCode(BankCode.valueOf(bankCode));
			bankData.setCardNo(cardNo);
			bankData.setUserId(userData.getId());

			return walletBankService.localBindCard(userData.getId(), bankData);
		} else
			return false;
	}

	// 小额验证Api6064
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean pinganApi6064(UserData userData, String accountNo, String messageCode, String cardNo)
			throws Exception {

		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6064"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号

		parmaKeyDict.put("CustAcctId", accountNo); // 子账户账号
		parmaKeyDict.put("AcctId", cardNo); // 会员账号
		parmaKeyDict.put("ThirdCustId", userData.getId().toString()); // 交易网会员代码
		parmaKeyDict.put("CcyCode", "RMB"); // 币种
		parmaKeyDict.put("TranAmount", messageCode); // 鉴权金额

		return walletFillPayService.sendMessage2Bank(parmaKeyDict, userData.getId().toString());
	}

	// 验证短信验证码Api6067
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean pinganApi6067(UserData userData, String accountNo, String messageCode, String cardNo)
			throws Exception {

		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6067"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号

		parmaKeyDict.put("CustAcctId", accountNo); // 子账户账号
		parmaKeyDict.put("AcctId", cardNo); // 会员账号
		parmaKeyDict.put("ThirdCustId", userData.getId().toString()); // 交易网会员代码
		parmaKeyDict.put("MessageCode", messageCode); // 短信验证码

		return walletFillPayService.sendMessage2Bank(parmaKeyDict, userData.getId().toString());
	}

	public Map<String, String> pinganSetpw(UserData userData, AccountData ad, String type, HttpServletRequest request)
			throws Exception {
		Map<String, String> params = new HashMap<String, String>();

		/* 生成随机数:当前精确到秒的时间再加6位的数字随机序列 */
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String rdNum = df.format(new Date());
		String thirdLogNo = rdNum + ad.getId();

		/* 报文参数赋值 */
		params.put("orderid", thirdLogNo); // 请求流水号
		params.put("P2PCode", Constants.QYDM); // 第三方平台ID
		params.put("P2PType", "2"); // 第三方平台类型 2：非代扣
		params.put("thirdCustId", userData.getId().toString()); // 交易网会员代码
		params.put("custAccId", ad.getAccountNo()); // 子账户账号
		params.put("name", ad.getAccounter()); // 会员名称
		params.put("idType", "1"); // 会员证件类型
		params.put("idNo", ad.getIdCode()); // 会员证件号码
		params.put("mobile", ad.getMobile()); // 会员手机号
		if ("S".equals(type)) {
			params.put("type", "S"); // 交易类型

			String setpwNotify = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + request.getServletPath() + "/pingan/setpwNotify";
			String setpwReturn = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + request.getServletPath() + "/pingan/setpwReturn";

			params.put("notifyUrl", setpwNotify); // 交易结果通知第三方url
			params.put("returnurl", setpwReturn); // 回调第三方url
		} else if ("C".equals(type)) {
			params.put("type", "C"); // 交易类型
		} else if ("R".equals(type)) {
			params.put("type", "R"); // 交易类型
		} else if ("M".equals(type)) {
			params.put("type", "M"); // 交易类型
		}

		return params;
	}

	public String surefetch(UserData userData, String paymentNo, String serialNo, String messageCode) throws Exception {
		YydWallet yw = walletDao.findByPaymentNo(paymentNo);
		if (yw != null) {
			// 可提现额度必须大于提现额度必须大于手续费方可提现
			Double fetchableMoney = walletSettleService.getFetchableMoney(userData.getId());
			Double fetchingMoney = yw.getTotalFee();
			Double handFee = 4.00D;
			if (fetchingMoney > 10000.00D)
				handFee = 8.00D;
			if (userData.getUserBankData() != null && userData.getUserBankData().getBankCode() == BankCode.SPABANK)
				handFee = 0.00D;
			if (fetchableMoney < (fetchingMoney + handFee))
				throw new Exception("可提现额度不足不可提现！");

			if (pinganApi6085(userData, yw.getPaymentNo(), fetchingMoney, handFee, serialNo, messageCode)) {
				// 提现成功平台处理
				String cNo = userData.getUserBankData().getCardNo();
				String lastNo = cNo.substring(cNo.length() - 4, cNo.length());
				yw.setBankCardNo4(lastNo);
				yw.setPlantBankName(userData.getUserBankData().getBankCode().getDescription());
				yw.setPaymentStatus(PayStatusCode.TRADE_FINISHED);
				yw.setGmtPayment(Calendar.getInstance());
				walletDao.save(yw);

				// 钱包帐务添加取现记录
				walletSettleService.saveWalletSettle(userData.getId(), yw.getId(), WalletSettleCode.fetch,
						(-1) * fetchingMoney);

				if (handFee > 0.00D) {
					// 钱包帐务添加提现手续费记录
					walletSettleService.saveWalletSettle(userData.getId(), yw.getId(), WalletSettleCode.handfee,
							(-1) * handFee);
					// 钱包帐务添加平台的提现手续费记录
					walletSettleService.saveWalletSettle(0L, yw.getId(), WalletSettleCode.handfee, handFee);
				}

				return yw.getBody() + "--- 提现成功!";
			} else {
				// 提现失败平台处理
				return yw.getBody() + "--- 提现失败!";
			}
		} else
			throw new Exception("找不到提现记录!");
	}

	public HashMap<String, String> preFetch(UserData userData, double fetchMoney, Long walletId) throws Exception {
		YydWallet yw = this.fetch(userData, fetchMoney, walletId);

		HashMap<String, String> rspMap = new HashMap<String, String>();
		try {
			if (this.pinganApi6082(userData, "1", yw.getPaymentNo(), yw.getTotalFee(), rspMap)) {
				rspMap.put("paymentNo", yw.getPaymentNo());
				rspMap.put("fetchBody", yw.getBody());

				return rspMap;
			} else
				return rspMap;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public HashMap<String, String> preSyncMobile(UserData userData) throws Exception {
		AccountData ad = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
		if (ad != null) {
			List<UserBankData> ubds = walletBankService.getBankDatas(userData.getId());
			if (!ubds.isEmpty()) {
				HashMap<String, String> rspMap = new HashMap<String, String>();

				this.pinganApi6083(userData, ad, ubds.get(0), rspMap);
				rspMap.put("mobile", ad.getMobile());
				return rspMap;
			} else
				throw new Exception("找不到用户绑定的银行卡!");

		} else
			throw new Exception("找不到用户平安子账户!");
	}

	// 修改手机号
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean pinganApi6083(UserData userData, AccountData ad, UserBankData ubd, HashMap<String, String> rspMap)
			throws Exception {

		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		/**
		 * 第一部分：生成发送银行的请求的报文的实例
		 * 
		 */

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6083"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号

		parmaKeyDict.put("CustAcctId", ad.getAccountNo()); // 子账户账号
		parmaKeyDict.put("ThirdCustId", userData.getId().toString()); // 交易网会员代码

		parmaKeyDict.put("ModifiedType", "2"); // 修改方式
		parmaKeyDict.put("NewMobilePhone", ad.getMobile()); // 新手机号码
		parmaKeyDict.put("AcctId", ubd.getCardNo()); // 银行卡号

		Map retKeyDict = walletFillPayService.sendMessage2BankRespMap(parmaKeyDict, userData.getId().toString());

		rspMap.put("serialNo", (String) retKeyDict.get("SerialNo"));
		rspMap.put("revMobilePhone", (String) retKeyDict.get("RevMobilePhone"));

		return true;
	}

	public boolean sureSyncMobile(UserData userData, String serialNo, String syncMobileNo) throws Exception {

		AccountData ad = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
		if (ad != null) {
			this.pinganApi6084(userData, ad, serialNo, syncMobileNo);

			YydUserInfo yydUserInfo = userDao.get(userData.getId());
			yydUserInfo.setMobile(DESHelper.DoDES(ad.getMobile(), Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE));

			userDao.save(yydUserInfo);
			return true;
		} else
			throw new Exception("找不到用户平安子账户!");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean pinganApi6084(UserData userData, AccountData ad, String serialNo, String syncMobileNo)
			throws Exception {

		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		/**
		 * 第一部分：生成发送银行的请求的报文的实例
		 * 
		 */

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6084"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号

		parmaKeyDict.put("CustAcctId", ad.getAccountNo()); // 子账户账号
		parmaKeyDict.put("ThirdCustId", userData.getId().toString()); // 交易网会员代码

		parmaKeyDict.put("ModifiedType", "2"); // 修改方式
		parmaKeyDict.put("SerialNo", serialNo); // 短信指令号
		parmaKeyDict.put("MessageCode", syncMobileNo); // 短信验证码

		return walletFillPayService.sendMessage2Bank(parmaKeyDict, userData.getId().toString());

	}
}
