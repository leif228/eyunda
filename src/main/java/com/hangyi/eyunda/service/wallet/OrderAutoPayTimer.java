package com.hangyi.eyunda.service.wallet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.hangyi.eyunda.data.wallet.WalletData;
import com.hangyi.eyunda.data.wallet.WalletZjjzData;
import com.hangyi.eyunda.data.wallet.ZjjzResultData;
import com.hangyi.eyunda.domain.enumeric.PayStatusCode;
import com.hangyi.eyunda.domain.enumeric.WalletSettleCode;
import com.hangyi.eyunda.util.CalendarUtil;

public class OrderAutoPayTimer extends QuartzJobBean {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private WalletService walletService;
	private WalletFillPayService walletFillPayService;
	private WalletSettleService walletSettleService;
	private WalletZjjzService walletZjjzService;

	public void setWalletService(WalletService walletService) {
		this.walletService = walletService;
	}

	public void setWalletFillPayService(WalletFillPayService walletFillPayService) {
		this.walletFillPayService = walletFillPayService;
	}

	public void setWalletSettleService(WalletSettleService walletSettleService) {
		this.walletSettleService = walletSettleService;
	}

	public void setWalletZjjzService(WalletZjjzService walletZjjzService) {
		this.walletZjjzService = walletZjjzService;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info(sdf.format(new Date()) + " ---- OrderAutoPayTimer is running!");
		try {
			/*
			 * // 查询跨行支付定单的处理状态 List<WalletData> wds =
			 * walletService.findByKhWaitStatus(YesNoCode.yes);// 查询处于支付等待中的订单
			 * for (WalletData wd : wds)
			 * walletFillPayService.KhOrderStatusQuery(wd);// 已到帐的清分或清分冻结
			 */

			// 提现退单处理
			String yesterday = CalendarUtil.toYYYY_MM_DD(CalendarUtil.addDays(CalendarUtil.now(), -7));
			String today = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());

			List<WalletZjjzData> wzds = walletZjjzService.findFetchZjjz(yesterday, today);

			for (WalletZjjzData wzd : wzds) {
				logger.info(wzd.getJsonString());
				ZjjzResultData zrd = null;
				try {
					zrd = walletFillPayService.api6014(wzd);
				} catch (Exception e1) {
					logger.error(e1.getMessage());
					continue;
				}
				logger.info(zrd.getJsonString());

				WalletData theWalletData = walletService.getByPaymentNo(wzd.getPaymentNo());

				if (theWalletData != null) {
					if (!"0".equals(zrd.getTranStatus())) {
						if (theWalletData.getPaymentStatus() == PayStatusCode.TRADE_FINISHED) {
							walletService.updatePayStatus(wzd.getPaymentNo(), PayStatusCode.TRADE_CLOSED,
									theWalletData.getBody() + "|" + zrd.getReserve()); // 钱包帐务添加提现退单记录
							walletSettleService.saveWalletSettle(theWalletData.getBuyerId(), theWalletData.getId(),
									WalletSettleCode.backfetch, theWalletData.getTotalFee());
						}
					}
				}
			}

			// 自动确认付款
			List<WalletData> wallets = walletService.getExpiredBillDatas();
			for (WalletData wallet : wallets) {
				try {
					boolean result = walletFillPayService.confirmPay(wallet);

					if (result) {
						logger.info("支付号：" + wallet.getPaymentNo() + "，定时器自动确认付款成功！");
					} else {
						logger.error("支付号：" + wallet.getPaymentNo() + "，定时器自动确认付款失败！");
					}
				} catch (Exception e) {
					logger.error("支付号：" + wallet.getPaymentNo() + e.getMessage() + "-->" + wallet.getJsonString());
					continue;
				}
			}

			// 更新昨天钱包交易帐务的可提现余额等于可用余额
			walletSettleService.updateFetchableMoney();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info(sdf.format(new Date()) + " ---- OrderAutoPayTimer is stopped!");
	}

}