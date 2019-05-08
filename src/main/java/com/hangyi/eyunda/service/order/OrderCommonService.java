package com.hangyi.eyunda.service.order;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.YydOrderCommonDao;
import com.hangyi.eyunda.dao.YydOrderDailyDao;
import com.hangyi.eyunda.dao.YydOrderEvaluateDao;
import com.hangyi.eyunda.dao.YydOrderSignDao;
import com.hangyi.eyunda.dao.YydOrderVoyageDao;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.order.OrderCommonData;
import com.hangyi.eyunda.data.order.OrderDailyData;
import com.hangyi.eyunda.data.order.OrderSignData;
import com.hangyi.eyunda.data.order.OrderVoyageData;
import com.hangyi.eyunda.data.ship.ShipApprovalData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.data.wallet.WalletData;
import com.hangyi.eyunda.domain.YydOrderCommon;
import com.hangyi.eyunda.domain.YydOrderDaily;
import com.hangyi.eyunda.domain.YydOrderEvaluate;
import com.hangyi.eyunda.domain.YydOrderSign;
import com.hangyi.eyunda.domain.YydOrderVoyage;
import com.hangyi.eyunda.domain.enumeric.CargoBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.EvalTypeCode;
import com.hangyi.eyunda.domain.enumeric.FeeItemCode;
import com.hangyi.eyunda.domain.enumeric.OrderStateCode;
import com.hangyi.eyunda.domain.enumeric.PayStatusCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.account.AccountService;
import com.hangyi.eyunda.service.manage.FilterWordsService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.service.ship.PortService;
import com.hangyi.eyunda.service.wallet.WalletService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.ImageUtil;
import com.hangyi.eyunda.util.NumberFormatUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class OrderCommonService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final int SIGN_WAIT_TIME = 3600;// 签字等待时间1小时

	@Autowired
	private YydOrderCommonDao orderCommonDao;
	@Autowired
	private YydOrderVoyageDao orderVoyageDao;
	@Autowired
	private YydOrderDailyDao orderDailyDao;

	@Autowired
	private YydOrderSignDao orderSignDao;
	@Autowired
	private YydOrderEvaluateDao orderEvaluateDao;

	@Autowired
	private UserService userService;
	@SuppressWarnings("unused")
	@Autowired
	private AccountService accountService;
	@Autowired
	private MyShipService myShipService;
	@Autowired
	private WalletService walletService;
	@Autowired
	private SignService signService;
	@Autowired
	private PortService portService;
	@Autowired
	private FilterWordsService filterWordsService;
	@Autowired
	private OrderMessageService messageService;

	// 转换合同对象
	public OrderCommonData getOrderData(YydOrderCommon yydOrderCommon) {
		if (yydOrderCommon == null)
			return null;

		OrderCommonData orderCommonData;
		switch (yydOrderCommon.getOrderType().getWaresBigType()) {
		case voyage:// 航租合同
			orderCommonData = new OrderVoyageData(yydOrderCommon.getOrderType());
			OrderVoyageData orderVoyageData = (OrderVoyageData) orderCommonData;

			YydOrderVoyage yydOrderVoyage = orderVoyageDao.getByOrderId(yydOrderCommon.getId());
			CopyUtil.copyProperties(orderVoyageData, yydOrderVoyage, new String[] { "id" });

			if (orderVoyageData.getOrderType().getCargoType().getCargoBigType() == CargoBigTypeCode.container) {
				List<CargoTypeCode> ctcs = CargoTypeCode.getCodesByCargoBigType(CargoBigTypeCode.container);

				String[] cargoNames = orderVoyageData.getCargoNames().split(",");
				String[] tonTeus = orderVoyageData.getTonTeus().split(",");
				String[] prices = orderVoyageData.getPrices().split(",");

				Map<Integer, String> mapCargoNames = new TreeMap<Integer, String>();
				Map<Integer, Double> mapTonTeus = new TreeMap<Integer, Double>();
				Map<Integer, Double> mapPrices = new TreeMap<Integer, Double>();

				int n = 0;
				for (CargoTypeCode ctc : ctcs) {
					mapCargoNames.put(ctc.ordinal(), cargoNames[n]);
					mapTonTeus.put(ctc.ordinal(), Double.parseDouble(tonTeus[n]));
					mapPrices.put(ctc.ordinal(), Double.parseDouble(prices[n]));
					n++;
				}

				orderVoyageData.setMapCargoNames(mapCargoNames);
				orderVoyageData.setMapTonTeus(mapTonTeus);
				orderVoyageData.setMapPrices(mapPrices);
			} else {
				CargoTypeCode ctc = orderVoyageData.getOrderType().getCargoType();

				String cargoNames = orderVoyageData.getCargoNames();
				String tonTeus = orderVoyageData.getTonTeus();
				String prices = orderVoyageData.getPrices();

				Map<Integer, String> mapCargoNames = new TreeMap<Integer, String>();
				Map<Integer, Double> mapTonTeus = new TreeMap<Integer, Double>();
				Map<Integer, Double> mapPrices = new TreeMap<Integer, Double>();

				mapCargoNames.put(ctc.ordinal(), cargoNames);
				mapTonTeus.put(ctc.ordinal(), Double.parseDouble(tonTeus));
				mapPrices.put(ctc.ordinal(), Double.parseDouble(prices));

				orderVoyageData.setMapCargoNames(mapCargoNames);
				orderVoyageData.setMapTonTeus(mapTonTeus);
				orderVoyageData.setMapPrices(mapPrices);
			}

			orderVoyageData.setStartPort(portService.getPortData(yydOrderVoyage.getStartPortNo()));
			orderVoyageData.setEndPort(portService.getPortData(yydOrderVoyage.getEndPortNo()));

			orderVoyageData.setUpDate(CalendarUtil.toYYYY_MM_DD(yydOrderVoyage.getUpDate()));
			orderVoyageData.setDownDate(CalendarUtil.toYYYY_MM_DD(yydOrderVoyage.getDownDate()));

			break;
		case daily:// 期租租船合同
			orderCommonData = new OrderDailyData(yydOrderCommon.getOrderType());
			OrderDailyData orderDailyData = (OrderDailyData) orderCommonData;

			YydOrderDaily yydOrderDaily = orderDailyDao.getByOrderId(yydOrderCommon.getId());
			CopyUtil.copyProperties(orderDailyData, yydOrderDaily, new String[] { "id" });

			orderDailyData.setStartDate(CalendarUtil.toYYYY_MM_DD(yydOrderDaily.getStartDate()));
			orderDailyData.setEndDate(CalendarUtil.toYYYY_MM_DD(yydOrderDaily.getEndDate()));
			orderDailyData.setRcvDate(CalendarUtil.toYYYY_MM_DD(yydOrderDaily.getRcvDate()));
			orderDailyData.setRetDate(CalendarUtil.toYYYY_MM_DD(yydOrderDaily.getRetDate()));

			orderDailyData.setRcvPort(portService.getPortData(yydOrderDaily.getRcvPortNo()));
			orderDailyData.setRetPort(portService.getPortData(yydOrderDaily.getRetPortNo()));

			break;
		default:
			return null;
		}
		CopyUtil.copyProperties(orderCommonData, yydOrderCommon);

		orderCommonData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(yydOrderCommon.getCreateTime()));
		orderCommonData.setPdfFileName("Order" + yydOrderCommon.getId() + "-"
				+ CalendarUtil.toYYYYMMDDHHmmss(yydOrderCommon.getCreateTime()) + ".pdf");

		UserData ownerData = userService.getUserData(yydOrderCommon.getOwnerId());
		if (ownerData != null) {
			OrderSignData orderSignData = signService.getOrderSignData(ownerData.getId(), orderCommonData.getId());
			if (orderSignData != null) {
				ownerData.setSignature(orderSignData.getSignature());
				ownerData.setTrueName(orderSignData.getTrueName());
				ownerData.setCreateTime(orderSignData.getCreateTime());
			}
		}
		orderCommonData.setOwner(ownerData);

		UserData brokerData = userService.getUserData(yydOrderCommon.getBrokerId());
		orderCommonData.setBroker(brokerData);

		UserData handler = userService.getUserData(yydOrderCommon.getHandlerId());
		orderCommonData.setHandler(handler);

		UserData masterData = userService.getUserData(yydOrderCommon.getMasterId());
		if (masterData != null) {
			OrderSignData orderSignData = signService.getOrderSignData(masterData.getId(), orderCommonData.getId());
			if (orderSignData != null) {
				masterData.setSignature(orderSignData.getSignature());
				masterData.setTrueName(orderSignData.getTrueName());
				masterData.setCreateTime(orderSignData.getCreateTime());
			}
		}
		orderCommonData.setMaster(masterData);

		ShipData myShipData = myShipService.getShipData(yydOrderCommon.getShipId());
		orderCommonData.setShipData(myShipData);

		List<WalletData> walletDatas = walletService.findByOrderId(yydOrderCommon.getId(), FeeItemCode.prefee);
		orderCommonData.setWalletDatas(walletDatas);

		return orderCommonData;
	}

	// 查找一个合同
	public OrderCommonData getOrderData(Long id) {
		YydOrderCommon yydOrderCommon = orderCommonDao.get(id);
		if (yydOrderCommon != null) {
			return this.getOrderData(yydOrderCommon);
		}
		return null;
	}

	// 查找一个合同并判断查询者是否有权查看
	public OrderCommonData getMyOrderData(UserData userData, Long id) {
		YydOrderCommon yydOrderCommon = orderCommonDao.getMyOrder(userData.getId(), id);
		if (yydOrderCommon != null)
			return this.getOrderData(yydOrderCommon);
		else
			return null;
	}

	// 查找一个合同并判断是否合同相关人
	public OrderCommonData getMyOrderData(Long userId, Long id) {
		YydOrderCommon yydOrderCommon = orderCommonDao.getMyOrder(userId, id);
		if (yydOrderCommon != null)
			return this.getOrderData(yydOrderCommon);
		else
			return null;
	}

	public List<UserData> getOrderContacts(Long myUserId, OrderCommonData orderCommonData) throws Exception {
		List<UserData> contacts = new ArrayList<UserData>();

		if (!orderCommonData.getHandlerId().equals(myUserId))
			contacts.add(orderCommonData.getHandler());
		else if (orderCommonData.getOwnerId().equals(myUserId) && !orderCommonData.getMasterId().equals(myUserId))
			contacts.add(orderCommonData.getMaster());
		else if (orderCommonData.getMasterId().equals(myUserId) && !orderCommonData.getOwnerId().equals(myUserId))
			contacts.add(orderCommonData.getOwner());
		else if (!orderCommonData.getMasterId().equals(myUserId) && !orderCommonData.getOwnerId().equals(myUserId)) {
			contacts.add(orderCommonData.getOwner());
			contacts.add(orderCommonData.getMaster());
		} else {
			throw new Exception("错误！合同三方均是你自己！");
		}

		return contacts;
	}

	// 分页查询
	public Page<OrderCommonData> getOrderDatas(UserData userData, Page<OrderCommonData> pageData, String startTime,
			String endTime) {
		List<OrderCommonData> orderDatas = new ArrayList<OrderCommonData>();

		Page<YydOrderCommon> page = orderCommonDao.getByUserId(userData.getId(), startTime, endTime,
				pageData.getPageNo(), pageData.getPageSize());

		for (YydOrderCommon order : (List<YydOrderCommon>) page.getResult()) {
			OrderCommonData orderData = this.getOrderData(order);

			if (orderData != null)
				orderData.setOps(this.getOptions(orderData, userData));

			orderDatas.add(orderData);
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(orderDatas);

		return pageData;
	}

	// 后台管理分页查询
	public Page<OrderCommonData> findOrderPage(Page<OrderCommonData> pageData, String keyWords, String startDate,
			String endDate) {

		Page<YydOrderCommon> page = orderCommonDao.findOrderPage(pageData.getPageSize(), pageData.getPageNo(), keyWords,
				startDate, endDate);

		List<OrderCommonData> list = new ArrayList<OrderCommonData>();
		for (YydOrderCommon yoc : page.getResult()) {
			OrderCommonData ocd = this.getOrderData(yoc);
			list.add(ocd);
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(list);

		return pageData;
	}

	// 保存合同
	public Long orderSave(UserData userData, OrderCommonData orderData) throws Exception {

		/*
		 * if (accountService.getAccountByUserId(orderData.getBrokerId(),
		 * PayStyleCode.pinganpay) == null) throw new
		 * Exception("错误！代理人没有绑定银行卡，不能建立合同。"); if
		 * (accountService.getAccountByUserId(orderData.getMasterId(),
		 * PayStyleCode.pinganpay) == null) throw new
		 * Exception("错误！承运人没有绑定银行卡，不能建立合同。"); if
		 * (accountService.getAccountByUserId(orderData.getOwnerId(),
		 * PayStyleCode.pinganpay) == null) throw new
		 * Exception("错误！承运人没有绑定银行卡，不能建立合同。");
		 */

		if (orderData != null && !userData.getId().equals(orderData.getHandlerId())
				&& !userData.getId().equals(orderData.getOwnerId())) {
			throw new Exception("错误！没有该合同的修改权限，保存失败。");
		}
		if (orderData != null && orderData.getState().ordinal() >= OrderStateCode.endsign.ordinal()) {
			throw new Exception("错误！该合同已结签，不允许修改。");
		}

		// 查找或新建合同
		YydOrderCommon yydOrderCommon = orderCommonDao.get(orderData.getId());
		if (yydOrderCommon == null)
			yydOrderCommon = new YydOrderCommon();

		CopyUtil.copyProperties(yydOrderCommon, orderData, new String[] { "id" });

		yydOrderCommon.setState(OrderStateCode.edit);
		yydOrderCommon.setCreateTime(Calendar.getInstance());

		yydOrderCommon.setPlatFee(NumberFormatUtil.format(orderData.getTransFee() * Constants.PLAT_RATE / 100));
		if (!orderData.getBrokerId().equals(orderData.getMasterId())
				&& !orderData.getBrokerId().equals(orderData.getOwnerId())) {
			yydOrderCommon.setBrokerFee(NumberFormatUtil.format(orderData.getTransFee() * Constants.BROKER_RATE / 100));
			yydOrderCommon.setMasterFee(orderData.getTransFee() - orderData.getPlatFee() - orderData.getBrokerFee());
		} else {
			yydOrderCommon.setBrokerFee(0.00D);
			yydOrderCommon.setMasterFee(orderData.getTransFee() - orderData.getPlatFee());
		}
		yydOrderCommon.setOrderDesc(yydOrderCommon.getOrderDesc().replace("\n", "<br />"));

		orderCommonDao.save(yydOrderCommon);
		orderData.setId(yydOrderCommon.getId());

		switch (orderData.getOrderType().getWaresBigType()) {
		case voyage:// 航次运输合同
			OrderVoyageData orderVoyageData = (OrderVoyageData) orderData;

			YydOrderVoyage yydOrderVoyage = orderVoyageDao.getByOrderId(orderVoyageData.getId());
			if (yydOrderVoyage == null)
				yydOrderVoyage = new YydOrderVoyage();

			CopyUtil.copyProperties(yydOrderVoyage, orderVoyageData, new String[] { "id" });

			yydOrderVoyage.setOrderId(orderVoyageData.getId());

			yydOrderVoyage.setUpDate(CalendarUtil.parseYYYY_MM_DD(orderVoyageData.getUpDate()));
			yydOrderVoyage.setDownDate(CalendarUtil.parseYYYY_MM_DD(orderVoyageData.getDownDate()));

			orderVoyageDao.save(yydOrderVoyage);
			break;
		case daily:// 期租租船合同
			OrderDailyData orderDailyData = (OrderDailyData) orderData;

			YydOrderDaily yydOrderDaily = orderDailyDao.getByOrderId(orderDailyData.getId());
			if (yydOrderDaily == null)
				yydOrderDaily = new YydOrderDaily();

			CopyUtil.copyProperties(yydOrderDaily, orderDailyData, new String[] { "id" });

			yydOrderDaily.setOrderId(orderDailyData.getId());

			yydOrderDaily.setStartDate(CalendarUtil.parseYYYY_MM_DD(orderDailyData.getStartDate()));
			yydOrderDaily.setEndDate(CalendarUtil.parseYYYY_MM_DD(orderDailyData.getEndDate()));
			yydOrderDaily.setRcvDate(CalendarUtil.parseYYYY_MM_DD(orderDailyData.getRcvDate()));
			yydOrderDaily.setRetDate(CalendarUtil.parseYYYY_MM_DD(orderDailyData.getRetDate()));

			orderDailyDao.save(yydOrderDaily);
			break;
		default:
			throw new Exception("错误！不支持的合同类型。");
		}

		return yydOrderCommon.getId();
	}

	// 删除合同
	public void orderDelete(UserData userData, Long orderId) throws Exception {
		OrderCommonData orderCommonData = this.getMyOrderData(userData, orderId);

		if (orderCommonData == null) {
			throw new Exception("错误！没有合同查看权，删除失败。");
		}
		if (!orderCommonData.getHandlerId().equals(userData.getId())
				&& !orderCommonData.getOwnerId().equals(userData.getId())) {
			throw new Exception("错误！没有合同删除权，删除失败。");
		}
		if (orderCommonData.getState().ordinal() >= OrderStateCode.endsign.ordinal()) {
			throw new Exception("错误！该合同已结签，不允许删除。");
		}

		// 删除签名
		List<YydOrderSign> yydOrderSigns = orderSignDao.findByOrder(orderCommonData.getId());
		for (YydOrderSign yydOrderSign : yydOrderSigns) {
			// 删除签名文件
			String realPath = Constants.SHARE_DIR;
			String attaPathFile = yydOrderSign.getSignature();
			File attaF = new File(realPath + attaPathFile);
			if (attaF.exists())
				attaF.delete();
			// 删除签名记录
			orderSignDao.delete(yydOrderSign);
		}

		// 删除合同记录
		switch (orderCommonData.getOrderType().getWaresBigType()) {
		case voyage:// 航次运输合同
			YydOrderVoyage yydOrderVoyage = orderVoyageDao.getByOrderId(orderCommonData.getId());
			orderVoyageDao.delete(yydOrderVoyage);
			break;
		case daily:// 期租租船合同
			YydOrderDaily yydOrderDaily = orderDailyDao.getByOrderId(orderCommonData.getId());
			orderDailyDao.delete(yydOrderDaily);
			break;
		default:
			throw new Exception("错误！不支持的合同类型。");
		}

		orderCommonDao.delete(orderId);
	}

	// 提交
	public void orderSubmit(UserData userData, Long orderId) throws Exception {
		OrderCommonData orderCommonData = this.getMyOrderData(userData, orderId);

		if (orderCommonData == null) {
			throw new Exception("错误！没有合同查看权，提交失败。");
		}
		if (!orderCommonData.getHandlerId().equals(userData.getId())) {
			throw new Exception("错误！非该合同的经纪人不能提交该合同。");
		}
		if (orderCommonData.getState() != OrderStateCode.edit) {
			throw new Exception("错误！合同状态不是edit，不允许提交。");
		}

		// 修改合同状态
		YydOrderCommon yydOrderCommon = orderCommonDao.get(orderId);
		yydOrderCommon.setState(OrderStateCode.submit);
		orderCommonDao.save(yydOrderCommon);

		// 发送通知
		String msg = userData.getTrueName() + OrderStateCode.submit.getDescription() + "合同！\n\n"
				+ orderCommonData.toString();

		messageService.sendOrderMsg(msg, userData, orderCommonData);
	}

	// 起签
	public void orderStartSign(UserData userData, Long orderId) throws Exception {
		OrderCommonData orderCommonData = this.getMyOrderData(userData, orderId);

		if (orderCommonData == null) {
			throw new Exception("错误！没有合同查看权，起签失败。");
		}
		if (!orderCommonData.getMasterId().equals(userData.getId())) {
			throw new Exception("错误！你无权起签该合同。");
		}
		if (orderCommonData.getState() != OrderStateCode.submit
				&& orderCommonData.getState() != OrderStateCode.startsign) {
			throw new Exception("错误！合同状态不正确，不允许起签。");
		}

		// 这儿要插入代理人签章图片到合同文本中
		// 保存上传的签名信息，获得文件名称
		String realPath = Constants.SHARE_DIR;
		String signatureImg = realPath + userData.getSignature();
		String stampImg = realPath + userData.getStamp();
		if (!userData.getStamp().equalsIgnoreCase("")) {
			ImageUtil.resize(signatureImg, 480, 480, false);
			ImageUtil.resize(stampImg, 480, 480, false);
			ImageUtil.pressImage(signatureImg, stampImg, 0, 0, 0.2f);
		}

		// 插入签字记录
		YydOrderSign yydSign = orderSignDao.getByUserOrder(userData.getId(), orderId);
		if (yydSign == null)
			yydSign = new YydOrderSign();
		yydSign.setOrderId(orderId);
		yydSign.setUserId(userData.getId());
		yydSign.setSignature(userData.getSignature());
		yydSign.setTrueName(userData.getTrueName());
		yydSign.setStamp(userData.getStamp());
		yydSign.setSignContent("同意全部合同条款");
		yydSign.setCreateTime(Calendar.getInstance());
		orderSignDao.save(yydSign);

		// 修改合同状态
		YydOrderCommon yydOrderCommon = orderCommonDao.get(orderId);
		yydOrderCommon.setState(OrderStateCode.startsign);
		orderCommonDao.save(yydOrderCommon);

		// 发送通知
		String msg = userData.getTrueName() + OrderStateCode.startsign.getDescription() + "合同！\n\n"
				+ orderCommonData.toString();
		messageService.sendOrderMsg(msg, userData, orderCommonData);
	}

	// 确签
	public void orderEndSign(UserData userData, Long orderId) throws Exception {
		OrderCommonData orderCommonData = this.getMyOrderData(userData, orderId);

		if (orderCommonData == null) {
			throw new Exception("错误！没有合同查看权，确签失败。");
		}
		if (!orderCommonData.getOwnerId().equals(userData.getId())) {
			throw new Exception("错误！你无权确签该合同。");
		}
		if (orderCommonData.getState() != OrderStateCode.startsign) {
			throw new Exception("错误！合同状态不是startsign，不允许确签。");
		}
		// 乙方签字后，等待甲方签字一小时
		String signTime = orderCommonData.getMaster().getCreateTime();
		Calendar st = CalendarUtil.parseYYYY_MM_DD_HH_MM(signTime);
		Calendar et = CalendarUtil.now();
		int diff = CalendarUtil.secondDiff(et, st);
		if (diff > SIGN_WAIT_TIME) {
			throw new Exception("错误！合同签字时间已过，请联系乙方重签。");
		}

		String realPath = Constants.SHARE_DIR;
		String signatureImg = realPath + userData.getSignature();
		String stampImg = realPath + userData.getStamp();
		if (!userData.getStamp().equalsIgnoreCase("")) {
			ImageUtil.resize(signatureImg, 480, 480, false);
			ImageUtil.resize(stampImg, 480, 480, false);
			ImageUtil.pressImage(signatureImg, stampImg, 0, 0, 0.2f);
		}

		// 插入签字记录
		YydOrderSign yydSign = orderSignDao.getByUserOrder(userData.getId(), orderId);
		if (yydSign == null)
			yydSign = new YydOrderSign();
		yydSign.setOrderId(orderId);
		yydSign.setUserId(userData.getId());
		yydSign.setSignature(userData.getSignature());
		yydSign.setTrueName(userData.getTrueName());
		yydSign.setStamp(userData.getStamp());
		yydSign.setSignContent("同意全部合同条款");
		yydSign.setCreateTime(Calendar.getInstance());
		orderSignDao.save(yydSign);

		// 修改合同状态
		YydOrderCommon yydOrderCommon = orderCommonDao.get(orderId);
		yydOrderCommon.setState(OrderStateCode.endsign);
		orderCommonDao.save(yydOrderCommon);

		// 发送通知
		String msg = userData.getTrueName() + OrderStateCode.endsign.getDescription() + "合同！\n\n"
				+ orderCommonData.toString();
		messageService.sendOrderMsg(msg, userData, orderCommonData);
	}

	// 归档
	public void orderArchive(UserData userData, Long orderId) throws Exception {
		OrderCommonData orderCommonData = this.getMyOrderData(userData, orderId);

		if (orderCommonData == null) {
			throw new Exception("错误！没有合同查看权，归档失败。");
		}
		if (!userService.isRole(userData, UserPrivilegeCode.handler)) {
			throw new Exception("错误！你无权归档该合同。");
		}
		if (orderCommonData.getState() != OrderStateCode.endsign) {
			throw new Exception("错误！合同状态不是endsign，不允许归档。");
		}

		// 修改合同状态
		YydOrderCommon yydOrderCommon = orderCommonDao.get(orderId);
		yydOrderCommon.setState(OrderStateCode.archive);
		orderCommonDao.save(yydOrderCommon);

		// 发送通知
		String msg = userData.getTrueName() + OrderStateCode.archive.getDescription() + "合同！\n\n"
				+ orderCommonData.toString();
		messageService.sendOrderMsg(msg, userData, orderCommonData);
	}

	// 获取评价内容
	public ShipApprovalData getOrderApproval(UserData userData, Long orderId) throws Exception {
		OrderCommonData orderCommonData = this.getMyOrderData(userData, orderId);

		if (orderCommonData == null) {
			throw new Exception("错误！没有合同查看权，评价失败。");
		}
		if (!orderCommonData.getOwnerId().equals(userData.getId())) {
			throw new Exception("错误！你无权评价该合同。");
		}

		YydOrderEvaluate yydEvaluate = orderEvaluateDao.getByOrderId(orderId);
		if (yydEvaluate == null)
			yydEvaluate = new YydOrderEvaluate();

		yydEvaluate.setOrderId(orderId);
		yydEvaluate.setShipId(orderCommonData.getShipId());// 可为0
		yydEvaluate.setUserId(userData.getId());

		ShipApprovalData shipApprovalData = new ShipApprovalData();

		CopyUtil.copyProperties(shipApprovalData, yydEvaluate);
		shipApprovalData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(yydEvaluate.getCreateTime()));

		return shipApprovalData;
	}

	// 评价
	public void orderApproval(UserData userData, Long orderId, EvalTypeCode evalType, String evalContent)
			throws Exception {
		OrderCommonData orderCommonData = this.getMyOrderData(userData, orderId);

		if (orderCommonData == null) {
			throw new Exception("错误！没有合同查看权，评价失败。");
		}
		if (!orderCommonData.getOwnerId().equals(userData.getId())) {
			throw new Exception("错误！你无权评价该合同。");
		}

		YydOrderEvaluate yydEvaluate = orderEvaluateDao.getByOrderId(orderId);
		if (yydEvaluate == null)
			yydEvaluate = new YydOrderEvaluate();

		yydEvaluate.setOrderId(orderId);
		yydEvaluate.setShipId(orderCommonData.getShipId());// 可为0
		yydEvaluate.setUserId(userData.getId());
		yydEvaluate.setEvalType(evalType);
		yydEvaluate.setEvalContent(filterWordsService.removeFilterWords(evalContent));
		yydEvaluate.setCreateTime(Calendar.getInstance());

		orderEvaluateDao.save(yydEvaluate);

		// 修改合同状态
		YydOrderCommon yydOrderCommon = orderCommonDao.get(orderId);
		yydOrderCommon.setApproval(YesNoCode.yes);
		orderCommonDao.save(yydOrderCommon);

		// 发送通知
		String msg = userData.getTrueName() + "评价合同！\n\n" + orderCommonData.toString();
		messageService.sendOrderMsg(msg, userData, orderCommonData);
	}

	private Map<String, Boolean> getOptions(OrderCommonData orderCommonData, UserData userData) {
		Map<String, Boolean> ops = new HashMap<String, Boolean>();

		for (OrderStateCode e : OrderStateCode.values())
			ops.put(e.toString(), false);
		ops.put("approval", false);
		ops.put("payment", false);
		ops.put("monitor", true);

		switch (orderCommonData.getState()) {
		case edit:
			// 订单确认前下单人还可以修改
			if (userData.getId().equals(orderCommonData.getHandlerId())
					|| userData.getId().equals(orderCommonData.getOwnerId())) {
				ops.put(OrderStateCode.edit.toString(), true);
			}
			if (userData.getId().equals(orderCommonData.getHandlerId())) {
				ops.put(OrderStateCode.submit.toString(), true);
			}
			break;
		case submit:
			if (userData.getId().equals(orderCommonData.getHandlerId())) {
				ops.put(OrderStateCode.edit.toString(), true);
			}
			if (userData.getId().equals(orderCommonData.getMasterId())) {
				// 如果当前用户是承运人,则可起签
				ops.put(OrderStateCode.startsign.toString(), true);
			}
			break;
		case startsign:
			if (userData.getId().equals(orderCommonData.getHandlerId())) {
				ops.put(OrderStateCode.edit.toString(), true);
			}
			// 乙方签字后，等待甲方签字一小时
			String signTime = orderCommonData.getMaster().getCreateTime();
			Calendar st = CalendarUtil.parseYYYY_MM_DD_HH_MM(signTime);
			Calendar et = CalendarUtil.now();
			int diff = CalendarUtil.secondDiff(et, st);

			if (userData.getId().equals(orderCommonData.getMasterId()) && diff > SIGN_WAIT_TIME) {
				// 如果当前用户是承运人,则可起签
				ops.put(OrderStateCode.startsign.toString(), true);
			}
			if (userData.getId().equals(orderCommonData.getOwnerId()) && diff <= SIGN_WAIT_TIME) {
				// 如果当前用户是托运人,则可确签
				ops.put(OrderStateCode.endsign.toString(), true);
			}
			break;
		case endsign:
			if (userData.getId().equals(orderCommonData.getHandlerId())) {
				ops.put(OrderStateCode.archive.toString(), true);
			}
			if (userData.getId().equals(orderCommonData.getOwnerId())) {
				List<WalletData> wds = orderCommonData.getWalletDatas();

				if (wds.isEmpty())// 未支付过
					ops.put("payment", true);
				else {
					WalletData wd = wds.get(wds.size() - 1);
					if (wd.getPaymentStatus() == PayStatusCode.TRADE_FINISHED
							&& wds.size() < orderCommonData.getPaySteps()) {
						// 这儿考虑分期，上期确付了下期可支付
						ops.put("payment", true);
					}
				}
			}
			if (userData.getId().equals(orderCommonData.getOwnerId())) {
				ops.put("approval", true);
			}
			break;
		case archive:
			ops.put("monitor", false);// 归档后不许实时跟踪了
			break;
		}

		return ops;
	}

}
