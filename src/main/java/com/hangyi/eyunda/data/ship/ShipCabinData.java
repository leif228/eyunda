package com.hangyi.eyunda.data.ship;

import java.util.ArrayList;
import java.util.List;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;
import com.hangyi.eyunda.util.CalendarUtil;

public class ShipCabinData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private Long shipId = 0L; // 船舶Id
	private ShipData shipData = null; // 船舶

	private Long publisherId = 0L; // 发布人ID
	private UserData publisher = null; // 发布人只能是业务员

	private Long brokerId = 0L; // 代理人ID
	private UserData broker = null; // 代理人

	private Long masterId = 0L; // 船东ID
	private UserData master = null; // 船东人

	private WaresBigTypeCode waresBigType = null; // 船盘大类
	private WaresTypeCode waresType = null; // 船盘分类
	private CargoTypeCode cargoType = null; // 接货类

	private Integer containerCount = 0; // 载箱量TEU
	private String startDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 受载日期

	private String ports = ""; // 接货港
	private List<UpDownPortData> upDownPortDatas = null; // 接货港

	private String prices = ""; // 运费报价
	private List<SailLineData> sailLineDatas = null; // 航次报价表
	private SailLineData currSailLineData = null; // 当前要显示的航次报价

	private Double oilPrice = 0.00D; // 燃油费计算
	private Double demurrage = 0.00D; // 滞期费率
	private Integer paySteps = 0; // 支付分期
	private String orderDesc = ""; // 特约条款
	private String description = ""; // 船盘描述
	private ReleaseStatusCode status = ReleaseStatusCode.unpublish; // 发布状态
	private String createTime = CalendarUtil.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.now()); // 修改时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
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

	public UserData getPublisher() {
		return publisher;
	}

	public void setPublisher(UserData publisher) {
		this.publisher = publisher;
	}

	public WaresBigTypeCode getWaresBigType() {
		return waresBigType;
	}

	public void setWaresBigType(WaresBigTypeCode waresBigType) {
		this.waresBigType = waresBigType;
	}

	public WaresTypeCode getWaresType() {
		return waresType;
	}

	public void setWaresType(WaresTypeCode waresType) {
		this.waresType = waresType;
	}

	public CargoTypeCode getCargoType() {
		return cargoType;
	}

	public void setCargoType(CargoTypeCode cargoType) {
		this.cargoType = cargoType;
	}

	public Integer getContainerCount() {
		return containerCount;
	}

	public void setContainerCount(Integer containerCount) {
		this.containerCount = containerCount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getPorts() {
		return ports;
	}

	public void setPorts(String ports) {
		this.ports = ports;
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public Double getOilPrice() {
		return oilPrice;
	}

	public void setOilPrice(Double oilPrice) {
		this.oilPrice = oilPrice;
	}

	public Double getDemurrage() {
		return demurrage;
	}

	public void setDemurrage(Double demurrage) {
		this.demurrage = demurrage;
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

	public String getDescription() {
		this.description = this.getWaresBigType().getDescription() + " - " + this.getWaresType().getDescription();
		if (this.getCargoType() != CargoTypeCode.container20e)
			this.description += " - " + this.getCargoType().getShortName();
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ReleaseStatusCode getStatus() {
		return status;
	}

	public void setStatus(ReleaseStatusCode status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<UpDownPortData> getUpDownPortDatas() {
		return upDownPortDatas;
	}

	public List<UpDownPortData> getGotoUpDownPortDatas() {
		List<UpDownPortData> udpds = new ArrayList<UpDownPortData>();
		for (UpDownPortData upDownPortData : upDownPortDatas) {
			if (upDownPortData.getGotoThisPort()) {
				udpds.add(upDownPortData);
			}
		}
		return udpds;
	}

	public void setUpDownPortDatas(List<UpDownPortData> upDownPortDatas) {
		this.upDownPortDatas = upDownPortDatas;
	}

	public List<SailLineData> getSailLineDatas() {
		return sailLineDatas;
	}

	public List<SailLineData> getGotoSailLineDatas() {
		List<SailLineData> slds = new ArrayList<SailLineData>();
		for (SailLineData sailLineData : sailLineDatas) {
			if (sailLineData.getGotoThisLine()) {
				slds.add(sailLineData);
			}
		}
		return slds;
	}

	public void setSailLineDatas(List<SailLineData> sailLineDatas) {
		this.sailLineDatas = sailLineDatas;
	}

	public SailLineData getCurrSailLineData() {
		return currSailLineData;
	}

	public void setCurrSailLineData(SailLineData currSailLineData) {
		this.currSailLineData = currSailLineData;
	}

	public void setCurrSailLineData(String selPortNos) {
		if (sailLineDatas == null)
			return;

		// 设置指定的航线为当前航线
		if (selPortNos != null && !"".equals(selPortNos)) {
			for (SailLineData line : sailLineDatas) {
				if (line.getSailLineNo().equals(selPortNos)) {
					this.setCurrSailLineData(line);
					break;
				}
			}
		}

		// 若不存在指定的航线，随机设置当前航线
		if (this.getCurrSailLineData() == null) {
			List<SailLineData> ls = new ArrayList<SailLineData>();
			for (SailLineData line : sailLineDatas) {
				if (line.getGotoThisLine()) {
					ls.add(line);
				}
			}
			if (!ls.isEmpty()) {
				int x = (int) (Math.random() * ls.size());
				this.setCurrSailLineData(ls.get(x));
			}
		}
	}

	public void sortUpDownPortDatas(String selPortNos) {
		if (selPortNos == null || "".equals(selPortNos))
			return;
		if (upDownPortDatas == null)
			return;

		List<UpDownPortData> turns = new ArrayList<UpDownPortData>();
		// 要查的排第一
		for (UpDownPortData udpd : upDownPortDatas) {
			if (udpd.getStartPortNo().equals(selPortNos)) {
				turns.add(udpd);
				break;
			}
		}
		// 其余的排后面
		for (UpDownPortData udpd : upDownPortDatas) {
			if (!udpd.getStartPortNo().equals(selPortNos)) {
				turns.add(udpd);
			}
		}
		this.setUpDownPortDatas(turns);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}