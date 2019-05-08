package com.hangyi.eyunda.service.wallet;

import java.util.Date;
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

import com.hangyi.eyunda.chat.ChatService;
import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.event.MessageEvent;
import com.hangyi.eyunda.data.account.AccountData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.chat.ChatRoomData;
import com.hangyi.eyunda.data.ship.ShipCabinData;
import com.hangyi.eyunda.data.wallet.CustAcctData;
import com.hangyi.eyunda.data.wallet.WalletData;
import com.hangyi.eyunda.domain.enumeric.ApplyReplyCode;
import com.hangyi.eyunda.domain.enumeric.FeeItemCode;
import com.hangyi.eyunda.domain.enumeric.PayStatusCode;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.service.account.AccountService;
import com.hangyi.eyunda.service.chat.ChatRoomService;
import com.hangyi.eyunda.service.ship.MyCabinService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class BonusService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MyCabinService cabinService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private WalletFillPayService walletFillPayService;
	@Autowired
	private WalletService walletBillService;
	@Autowired
	private ChatService chatService;
	@Autowired
	private ChatRoomService chatRoomService;

	public Map<Long, Long> groupBonusSend(Long roomId, UserData sender, long[] receiverIds, Double money,
			String remark) {
		Map<Long, Long> mapRet = null;

		try {
			if (receiverIds.length == 0)
				throw new Exception("错误！未指定收取红包的人。");
			if (money <= 0)
				throw new Exception("错误！红包金额必须大于0。");

			Double totalMoney = money * receiverIds.length;

			AccountData payAccountData = accountService.getAccountByUserId(sender.getId(), PayStyleCode.pinganpay);
			if ((payAccountData == null) || ("".equals(payAccountData.getAccountNo())))
				throw new Exception("请先绑定银行卡，才能进行后续操作!");

			// 可用余额totalBalance,可提现余额TotalTranOutAmount
			Double totalBalance = 0D;
			// 会员账户钱包余额
			List<CustAcctData> cads = walletFillPayService.api6010(payAccountData.getAccountNo(), "2", "1");
			if (!cads.isEmpty()) {
				totalBalance = cads.get(0).getTotalBalance();
			}
			if (totalBalance < totalMoney)
				throw new Exception("错误！可用余额不足。");

			mapRet = new HashMap<Long, Long>();
			for (long receiverId : receiverIds) {
				// 自己不能给自己发红包
				if (!sender.getId().equals(receiverId)) {
					AccountData rcvAccountData = accountService.getAccountByUserId(receiverId, PayStyleCode.pinganpay);
					if ((rcvAccountData != null) && (!"".equals(rcvAccountData.getAccountNo()))) {
						// 产生钱包记录
						Long walletId = walletFillPayService.bonusWalletData(sender.getId(), receiverId, money, 0,
								remark);
						WalletData walletData = walletBillService.getBillData(walletId);
						// 进行资金托管
						try {
							if (walletData.getRcvCardNo() == null || "".equals(walletData.getRcvCardNo()))
								throw new Exception("错误！收款人" + walletData.getSellerData().getTrueName() + "未绑定银行卡。");

							boolean result = walletFillPayService.walletPay(walletData, "", "");
							if (result) {
								mapRet.put(receiverId, walletData.getId());
							}
						} catch (Exception e1) {
							logger.error(e1.getMessage());
						}
					}
				}
			}

			String uw = "";
			for (Long key : mapRet.keySet())
				uw += key + "-" + mapRet.get(key) + ";";
			if (!"".equals(uw))
				uw = uw.substring(0, uw.length() - 1);

			String content = "BONUS:" + uw + ":" + remark;
			// 发送红包消息
			long[] rcvIds = new long[mapRet.keySet().size()];
			int n = 0;
			for (Long key : mapRet.keySet())
				rcvIds[n++] = key;
			this.sendMsgToRoom(roomId, content, sender, rcvIds);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return mapRet;
	}

	public Map<Long, String> batchBonusSend(UserData sender, long[] cabinIds, Double money, String remark) {
		Map<Long, String> mapRet = new HashMap<Long, String>();

		try {
			if (cabinIds.length == 0)
				throw new Exception("错误！未选择发放红包的船盘信息。");
			if (money <= 0)
				throw new Exception("错误！红包金额必须大于0。");

			Double totalMoney = money * cabinIds.length;

			AccountData payAccountData = accountService.getAccountByUserId(sender.getId(), PayStyleCode.pinganpay);
			if ((payAccountData == null) || ("".equals(payAccountData.getAccountNo())))
				throw new Exception("请先绑定银行卡，才能进行后续操作!");

			// 可用余额totalBalance,可提现余额TotalTranOutAmount
			Double totalBalance = 0D;
			// 会员账户钱包余额
			List<CustAcctData> cads = walletFillPayService.api6010(payAccountData.getAccountNo(), "2", "1");
			if (!cads.isEmpty()) {
				totalBalance = cads.get(0).getTotalBalance();
			}
			if (totalBalance < totalMoney)
				throw new Exception("错误！可用余额不足。");

			for (long cabinId : cabinIds) {
				ShipCabinData cabinData = cabinService.getById(cabinId);
				// 产生钱包记录
				Long walletId = walletFillPayService.bonusWalletData(sender.getId(), cabinData.getPublisherId(), money,
						0, remark, cabinData.getId());
				WalletData walletData = walletBillService.getBillData(walletId);
				// 进行资金托管
				try {
					if (walletData.getRcvCardNo() == null || "".equals(walletData.getRcvCardNo()))
						throw new Exception("错误！收款人" + walletData.getSellerData().getTrueName() + "未绑定银行卡。");

					WalletData sentWalletData = walletBillService.getByAngel(cabinData.getPublisherId(),
							cabinData.getId());
					if (sentWalletData != null)
						throw new Exception("错误！本发放周期已经给收款人" + walletData.getSellerData().getTrueName() + "发过红包。");

					boolean result = walletFillPayService.walletPay(walletData, "", "");
					if (result) {
						String content = "BONUS:" + walletId + ":" + remark;
						UserData receiver = cabinData.getPublisher();
						// 发送红包消息
						this.sendMsg(content, sender, receiver);

						mapRet.put(cabinData.getId(), walletData.getBody() + "\n");
					} else {
						mapRet.put(cabinData.getId(),
								"错误！给收款人" + walletData.getSellerData().getTrueName() + "发送红包失败。" + "\n");
					}
				} catch (Exception e1) {
					logger.error(e1.getMessage());
					mapRet.put(cabinData.getId(), e1.getMessage() + "\n");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return mapRet;
	}

	public void singleBonusOpen(UserData receiver, WalletData walletData) throws Exception {
		if (walletData == null || walletData.getFeeItem() != FeeItemCode.bonus)
			throw new Exception("错误！该红包非法。");
		if (walletData.getPaymentStatus() == PayStatusCode.WAIT_PAYMENT)
			throw new Exception("信息！该红包金额未支付。");
		if (walletData.getPaymentStatus() == PayStatusCode.TRADE_FINISHED)
			throw new Exception("警告！该红包已经被拆开过了。");
		if (walletData.getPaymentStatus() == PayStatusCode.TRADE_CLOSED)
			throw new Exception("警告！该红包已被回收。");
		if (!walletData.getSellerId().equals(receiver.getId()))
			throw new Exception("非法操作！该红包不是你的。");

		AccountData payAccountData = accountService.getAccountByUserId(receiver.getId(), PayStyleCode.pinganpay);
		if ((payAccountData == null) || ("".equals(payAccountData.getAccountNo())))
			throw new Exception("请先绑定银行卡，才能进行后续操作!");

		if (walletData.getPaymentStatus() == PayStatusCode.WAIT_CONFIRM)
			walletFillPayService.confirmPay(walletData);
	}

	public void singleBonusRefund(UserData sender, WalletData walletData) throws Exception {
		if (walletData == null || walletData.getFeeItem() != FeeItemCode.bonus)
			throw new Exception("错误！该红包非法。");
		if (walletData.getPaymentStatus() == PayStatusCode.WAIT_PAYMENT)
			throw new Exception("信息！该红包金额未支付。");
		if (walletData.getPaymentStatus() == PayStatusCode.TRADE_FINISHED)
			throw new Exception("警告！该红包已经被拆开过了。");
		if (walletData.getPaymentStatus() == PayStatusCode.TRADE_CLOSED)
			throw new Exception("警告！该红包已被回收。");

		if (!walletData.getBuyerId().equals(sender.getId()))
			throw new Exception("非法操作！该红包不是你的。");

		if (walletData.getPaymentStatus() == PayStatusCode.WAIT_CONFIRM)
			walletFillPayService.doRefund(walletData, ApplyReplyCode.reply, null);
	}

	// 向群里发送消息
	private void sendMsgToRoom(Long roomId, String content, UserData senderData, long[] receiverIds) throws Exception {
		ChatRoomData chatRoomData = chatRoomService.getChatRoomData(roomId, senderData.getId());
		// 发送通知到手机
		Map<String, String> mapMsg = new HashMap<String, String>();

		mapMsg.put(MessageEvent.MESSAGE_TYPE, MessageConstants.MESSAGE_EVENT);
		mapMsg.put(MessageEvent.CONTENT, content);
		mapMsg.put(MessageEvent.DATE_TIME, Long.toString(new Date().getTime()));
		mapMsg.put(MessageEvent.ROOM_ID, Long.toString(chatRoomData.getId()));
		mapMsg.put(MessageEvent.FROM_USERID, Long.toString(senderData.getId()));
		mapMsg.put(MessageEvent.FROM_USERNAME, senderData.getNickName());

		MessageEvent event = new MessageEvent(mapMsg);

		chatService.sendMessage(event, receiverIds);
	}

	// 发送消息
	private void sendMsg(String content, UserData senderData, UserData receiverData) throws Exception {
		ChatRoomData chatRoomData = chatRoomService.getByTwoUserId(senderData.getId(), receiverData.getId());
		if (chatRoomData == null) {
			chatRoomData = new ChatRoomData();

			Long roomId = chatRoomService.saveChatRoom(chatRoomData,
					new Long[] { senderData.getId(), receiverData.getId() });
			chatRoomData = chatRoomService.getChatRoomData(roomId, receiverData.getId());
		}
		// 发送通知到手机
		Map<String, String> mapMsg = new HashMap<String, String>();

		mapMsg.put(MessageEvent.MESSAGE_TYPE, MessageConstants.MESSAGE_EVENT);
		mapMsg.put(MessageEvent.CONTENT, content);
		mapMsg.put(MessageEvent.DATE_TIME, Long.toString(new Date().getTime()));
		mapMsg.put(MessageEvent.ROOM_ID, Long.toString(chatRoomData.getId()));
		mapMsg.put(MessageEvent.FROM_USERID, Long.toString(senderData.getId()));
		mapMsg.put(MessageEvent.TO_USERID, Long.toString(receiverData.getId()));
		mapMsg.put(MessageEvent.FROM_USERNAME, senderData.getNickName());
		mapMsg.put(MessageEvent.TO_USERNAME, receiverData.getNickName());

		MessageEvent event = new MessageEvent(mapMsg);

		chatService.sendMessage(event);
	}

}
