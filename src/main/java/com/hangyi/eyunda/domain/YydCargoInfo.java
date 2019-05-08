package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;

@Entity
@Table(name = "YydCargoInfo", indexes = { @Index(name = "idx_publisherId", columnList = "publisherId", unique = false),
		@Index(name = "idx_cargoType", columnList = "cargoType", unique = false) })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydCargoInfo extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = true, length = 200)
	private String cargoImage = ""; // 货物图片
	@Column(nullable = false, length = 8)
	private String startPortNo = ""; // 装货港
	@Column(nullable = false, length = 8)
	private String endPortNo = ""; // 卸货港
	@Enumerated(EnumType.ORDINAL)
	private CargoTypeCode cargoType = null; // 货类
	@Column(nullable = false, length = 100)
	private String cargoNames = ""; // 货名或规格
	@Column(nullable = false, length = 100)
	private String tonTeus = ""; // 重量或数量
	@Column(nullable = false, length = 100)
	private String prices = ""; // 运费报价
	@Column
	private Double transFee = 0.00D; // 运费（元）
	@Enumerated(EnumType.ORDINAL)
	private ReleaseStatusCode status = null; // 发布状态
	@Column
	private Long publisherId = 0L; // 发布人ID
	@Column
	private Calendar createTime = Calendar.getInstance(); // 发布时间

	/**
	 * 取得货物图片
	 */
	public String getCargoImage() {
		return cargoImage;
	}

	/**
	 * 设置货物图片
	 */
	public void setCargoImage(String cargoImage) {
		this.cargoImage = cargoImage;
	}

	/**
	 * 取得装货港
	 */
	public String getStartPortNo() {
		return startPortNo;
	}

	/**
	 * 设置装货港
	 */
	public void setStartPortNo(String startPortNo) {
		this.startPortNo = startPortNo;
	}

	/**
	 * 取得卸货港
	 */
	public String getEndPortNo() {
		return endPortNo;
	}

	/**
	 * 设置卸货港
	 */
	public void setEndPortNo(String endPortNo) {
		this.endPortNo = endPortNo;
	}

	/**
	 * 取得货类
	 */
	public CargoTypeCode getCargoType() {
		return cargoType;
	}

	/**
	 * 设置货类
	 */
	public void setCargoType(CargoTypeCode cargoType) {
		this.cargoType = cargoType;
	}

	/**
	 * 取得货名或规格
	 */
	public String getCargoNames() {
		return cargoNames;
	}

	/**
	 * 设置货名或规格
	 */
	public void setCargoNames(String cargoNames) {
		this.cargoNames = cargoNames;
	}

	/**
	 * 取得重量或数量
	 */
	public String getTonTeus() {
		return tonTeus;
	}

	/**
	 * 设置重量或数量
	 */
	public void setTonTeus(String tonTeus) {
		this.tonTeus = tonTeus;
	}

	/**
	 * 取得运费报价
	 */
	public String getPrices() {
		return prices;
	}

	/**
	 * 设置运费报价
	 */
	public void setPrices(String prices) {
		this.prices = prices;
	}

	public Double getTransFee() {
		return transFee;
	}

	public void setTransFee(Double transFee) {
		this.transFee = transFee;
	}

	/**
	 * 取得发布状态
	 */
	public ReleaseStatusCode getStatus() {
		return status;
	}

	/**
	 * 设置发布状态
	 */
	public void setStatus(ReleaseStatusCode status) {
		this.status = status;
	}

	/**
	 * 取得发布人ID
	 */
	public Long getPublisherId() {
		return publisherId;
	}

	/**
	 * 设置发布人ID
	 */
	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	/**
	 * 取得发布时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置发布时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

}
