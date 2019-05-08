package com.hangyi.eyunda.data.ship;

import java.util.Map;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.domain.enumeric.CargoBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;

public class SailLineData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L;

	private WaresBigTypeCode waresBigType = null; // 船盘大类
	private WaresTypeCode waresType = null; // 船盘分类
	private CargoTypeCode cargoType = null; // 货类

	private String startPortNo = ""; // 装货港
	private PortData startPortData = null; // 装货港
	private String endPortNo = ""; // 卸货港
	private PortData endPortData = null; // 卸货港
	private String sailLineNo = ""; // 航线号

	private Integer distance = 0; // 航程
	private Integer weight = 0; // 载重量
	private Map<Integer, Double> mapPrices = null;// 集装箱报价

	private boolean gotoThisLine = false; // 是否经营该航线
	private String description = ""; // 运价描述
	private String remark = ""; // 运价保存

	public SailLineData() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSailLineNo() {
		this.sailLineNo = this.startPortNo + "-" + this.endPortNo;
		return sailLineNo;
	}

	public void setSailLineNo(String sailLineNo) {
		this.sailLineNo = sailLineNo;
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

	public String getStartPortNo() {
		return startPortNo;
	}

	public void setStartPortNo(String startPortNo) {
		this.startPortNo = startPortNo;
	}

	public String getEndPortNo() {
		return endPortNo;
	}

	public void setEndPortNo(String endPortNo) {
		this.endPortNo = endPortNo;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public PortData getStartPortData() {
		return startPortData;
	}

	public void setStartPortData(PortData startPortData) {
		this.startPortData = startPortData;
	}

	public PortData getEndPortData() {
		return endPortData;
	}

	public void setEndPortData(PortData endPortData) {
		this.endPortData = endPortData;
	}

	public Map<Integer, Double> getMapPrices() {
		return mapPrices;
	}

	public void setMapPrices(Map<Integer, Double> mapPrices) {
		this.mapPrices = mapPrices;
	}

	public boolean getGotoThisLine() {
		return gotoThisLine;
	}

	public boolean isGotoThisLine() {
		return gotoThisLine;
	}

	public void setGotoThisLine(boolean gotoThisLine) {
		this.gotoThisLine = gotoThisLine;
	}

	public String makeDescription() {
		String unit = "";
		if (this.getCargoType().getCargoBigType() == CargoBigTypeCode.container)
			unit = "元/个";
		else
			unit = "元/吨";

		String r = "";
		if (mapPrices != null && !mapPrices.isEmpty()) {
			for (Integer ct : mapPrices.keySet()) {
				r += CargoTypeCode.valueOf(ct).getShortName() + ":" + mapPrices.get(ct) + unit + ",";
			}
			r = r.substring(0, r.length() - 1);
		}
		return r;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String makeRemark() {
		String r = "";
		if (mapPrices != null && !mapPrices.isEmpty()) {
			for (Integer ct : mapPrices.keySet()) {
				r += CargoTypeCode.valueOf(ct).name() + "^" + mapPrices.get(ct) + ",";
			}
			r = r.substring(0, r.length() - 1);
		}
		return r;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
