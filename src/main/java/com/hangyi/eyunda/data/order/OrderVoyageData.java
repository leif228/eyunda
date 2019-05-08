package com.hangyi.eyunda.data.order;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.hangyi.eyunda.domain.enumeric.CargoBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.util.CalendarUtil;

public class OrderVoyageData extends OrderCommonData {

	private static final long serialVersionUID = 1L;

	private String startPortNo = ""; // 装货港编号
	private PortData startPort = null; // 装货港
	private String endPortNo = ""; // 卸货港编号
	private PortData endPort = null; // 卸货港

	private Integer distance = 0; // 航程
	private String upDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 装货日期
	private String downDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 卸货日期
	private CargoTypeCode cargoType = null; // 货类
	private String cargoNames = ""; // 货名或规格
	private String tonTeus = ""; // 重量或数量
	private String prices = ""; // 运价

	private Map<Integer, String> mapCargoNames = null;// 货名或规格
	private Map<Integer, Double> mapTonTeus = null;// 重量或数量
	private Map<Integer, Double> mapPrices = null;// 运价

	private Double demurrage = 0.00D; // 滞期费率

	public OrderVoyageData() {
	}

	public OrderVoyageData(OrderTypeCode orderType) {
		this.setOrderType(orderType);

		if (orderType.getCargoType().getCargoBigType() == CargoBigTypeCode.container) {
			List<CargoTypeCode> ctcs = CargoTypeCode.getCodesByCargoBigType(CargoBigTypeCode.container);

			Map<Integer, String> mapCargoNames = new TreeMap<Integer, String>();
			Map<Integer, Double> mapTonTeus = new TreeMap<Integer, Double>();
			Map<Integer, Double> mapPrices = new TreeMap<Integer, Double>();

			for (CargoTypeCode ctc : ctcs) {
				mapCargoNames.put(ctc.ordinal(), ctc.getShortName());
				mapTonTeus.put(ctc.ordinal(), 0D);
				mapPrices.put(ctc.ordinal(), 0D);
			}

			this.setMapCargoNames(mapCargoNames);
			this.setMapTonTeus(mapTonTeus);
			this.setMapPrices(mapPrices);
		} else {
			Map<Integer, String> mapCargoNames = new TreeMap<Integer, String>();
			Map<Integer, Double> mapTonTeus = new TreeMap<Integer, Double>();
			Map<Integer, Double> mapPrices = new TreeMap<Integer, Double>();

			mapCargoNames.put(0, orderType.getCargoType().getShortName());
			mapTonTeus.put(0, 0D);
			mapPrices.put(0, 0D);

			this.setMapCargoNames(mapCargoNames);
			this.setMapTonTeus(mapTonTeus);
			this.setMapPrices(mapPrices);
		}
	}

	public String getStartPortNo() {
		return startPortNo;
	}

	public void setStartPortNo(String startPortNo) {
		this.startPortNo = startPortNo;
	}

	public PortData getStartPort() {
		return startPort;
	}

	public void setStartPort(PortData startPort) {
		this.startPort = startPort;
	}

	public String getEndPortNo() {
		return endPortNo;
	}

	public void setEndPortNo(String endPortNo) {
		this.endPortNo = endPortNo;
	}

	public PortData getEndPort() {
		return endPort;
	}

	public void setEndPort(PortData endPort) {
		this.endPort = endPort;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public String getUpDate() {
		return upDate;
	}

	public void setUpDate(String upDate) {
		this.upDate = upDate;
	}

	public String getDownDate() {
		return downDate;
	}

	public void setDownDate(String downDate) {
		this.downDate = downDate;
	}

	public CargoTypeCode getCargoType() {
		return cargoType;
	}

	public void setCargoType(CargoTypeCode cargoType) {
		this.cargoType = cargoType;
	}

	public String getCargoNames() {
		return cargoNames;
	}

	public void setCargoNames(String cargoNames) {
		this.cargoNames = cargoNames;
	}

	public String getTonTeus() {
		return tonTeus;
	}

	public void setTonTeus(String tonTeus) {
		this.tonTeus = tonTeus;
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public Map<Integer, String> getMapCargoNames() {
		return mapCargoNames;
	}

	public void setMapCargoNames(Map<Integer, String> mapCargoNames) {
		this.mapCargoNames = mapCargoNames;
	}

	public Map<Integer, Double> getMapTonTeus() {
		return mapTonTeus;
	}

	public void setMapTonTeus(Map<Integer, Double> mapTonTeus) {
		this.mapTonTeus = mapTonTeus;
	}

	public Map<Integer, Double> getMapPrices() {
		return mapPrices;
	}

	public void setMapPrices(Map<Integer, Double> mapPrices) {
		this.mapPrices = mapPrices;
	}

	public Double getDemurrage() {
		return demurrage;
	}

	public void setDemurrage(Double demurrage) {
		this.demurrage = demurrage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
