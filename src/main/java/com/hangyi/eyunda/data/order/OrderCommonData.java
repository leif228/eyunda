package com.hangyi.eyunda.data.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.data.wallet.WalletData;
import com.hangyi.eyunda.domain.enumeric.OrderStateCode;
import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.util.CalendarUtil;

public class OrderCommonData extends BaseData {

	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private OrderTypeCode orderType = null; // 合同类别

	private Long shipId = 0L; // 船舶ID
	private ShipData shipData = null;

	private Integer containerCount = 0; // 载箱量
	private Integer weight = 0; // 载重量

	private Long ownerId = 0L; // 货主ID
	private UserData owner = null; // 货主
	private Long brokerId = 0L; // 代理人ID
	private UserData broker = null;// 代理人
	private Long masterId = 0L; // 船东ID
	private UserData master = null;// 船东
	private Long handlerId = 0L; // 业务员ID
	private UserData handler = null; // 业务员

	private List<WalletData> walletDatas = null; // 分期付款帐单列表

	private Double transFee = 0.00D; // 合同金额（元）
	private Double platFee = 0.00D; // 平台服务费
	private Double brokerFee = 0.00D; // 代理费
	private Double masterFee = 0.00D; // 承运人运费

	private Integer paySteps = 1; // 付款分期
	private String orderDesc = ""; // 特约条款

	private OrderStateCode state = OrderStateCode.edit; // 状态
	private YesNoCode approval = YesNoCode.no; // 是否评价
	private String createTime = CalendarUtil.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.now()); // 建立时间

	private String pdfFileName = ""; // 合同文件名

	// 当前用户对该合同可进行的操作
	private Map<String, Boolean> ops = new HashMap<String, Boolean>();

	private String getFirstParty() {
		switch (this.getOrderType().getWaresBigType()) {
		case voyage:// 航次运输合同
			return "托运人";
		case daily:// 期租租船合同
			return "承租人";
		default:
			return "";
		}
	}

	private String getSecondParty() {
		switch (this.getOrderType().getWaresBigType()) {
		case voyage:// 航次运输合同
			return "承运人";
		case daily:// 期租租船合同
			return "出租人";
		default:
			return "";
		}
	}

	public String toString() {
		String orderText = "";

		orderText += this.getShipData().getShipName() + this.getOrderType().getDescription() + "（" + this.id + "）"
				+ "\n";

		if (!this.owner.getId().equals(this.broker.getId()) && !this.master.getId().equals(this.broker.getId())) {
			// 三方合同
			orderText += this.getFirstParty() + "：" + this.owner.getTrueName() + "\n";
			orderText += this.getSecondParty() + "：" + this.master.getTrueName() + "\n";
			orderText += "代理人：" + this.broker.getTrueName() + "，业务员" + this.handler.getTrueName() + "\n";
		} else {
			// 两方合同
			orderText += this.getFirstParty() + "：" + this.owner.getTrueName() + "\n";

			if (this.master == null) {
				orderText += this.getSecondParty() + "：" + this.broker.getTrueName() + "\n";
			} else {
				orderText += this.getSecondParty() + "：" + this.master.getTrueName() + "\n";
			}
		}
		orderText += "合同金额：" + this.transFee + "元\n";
		orderText += "付款分期：" + this.paySteps + "期\n";

		return orderText;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderTypeCode getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderTypeCode orderType) {
		this.orderType = orderType;
	}

	public Long getShipId() {
		return shipId;
	}

	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}

	public ShipData getShipData() {
		return shipData;
	}

	public void setShipData(ShipData shipData) {
		this.shipData = shipData;
	}

	public Integer getContainerCount() {
		return containerCount;
	}

	public void setContainerCount(Integer containerCount) {
		this.containerCount = containerCount;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public UserData getOwner() {
		return owner;
	}

	public void setOwner(UserData owner) {
		this.owner = owner;
	}

	public Long getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(Long brokerId) {
		this.brokerId = brokerId;
	}

	public UserData getBroker() {
		return broker;
	}

	public void setBroker(UserData broker) {
		this.broker = broker;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public UserData getMaster() {
		return master;
	}

	public void setMaster(UserData master) {
		this.master = master;
	}

	public Long getHandlerId() {
		return handlerId;
	}

	public void setHandlerId(Long handlerId) {
		this.handlerId = handlerId;
	}

	public UserData getHandler() {
		return handler;
	}

	public void setHandler(UserData handler) {
		this.handler = handler;
	}

	public List<WalletData> getWalletDatas() {
		return walletDatas;
	}

	public void setWalletDatas(List<WalletData> walletDatas) {
		this.walletDatas = walletDatas;
	}

	public Double getTransFee() {
		return transFee;
	}

	public void setTransFee(Double transFee) {
		this.transFee = transFee;
	}

	public Double getPlatFee() {
		return platFee;
	}

	public void setPlatFee(Double platFee) {
		this.platFee = platFee;
	}

	public Double getBrokerFee() {
		return brokerFee;
	}

	public void setBrokerFee(Double brokerFee) {
		this.brokerFee = brokerFee;
	}

	public Double getMasterFee() {
		return masterFee;
	}

	public void setMasterFee(Double masterFee) {
		this.masterFee = masterFee;
	}

	public Integer getPaySteps() {
		return paySteps;
	}

	public void setPaySteps(Integer paySteps) {
		this.paySteps = paySteps;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public OrderStateCode getState() {
		return state;
	}

	public void setState(OrderStateCode state) {
		this.state = state;
	}

	public YesNoCode getApproval() {
		return approval;
	}

	public void setApproval(YesNoCode approval) {
		this.approval = approval;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPdfFileName() {
		return pdfFileName;
	}

	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}

	public Map<String, Boolean> getOps() {
		return ops;
	}

	public void setOps(Map<String, Boolean> ops) {
		this.ops = ops;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
