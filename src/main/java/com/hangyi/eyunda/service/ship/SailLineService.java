package com.hangyi.eyunda.service.ship;

import java.io.File;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.YydPortDao;
import com.hangyi.eyunda.dao.YydSailLineDao;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.data.ship.SailLineData;
import com.hangyi.eyunda.domain.YydPort;
import com.hangyi.eyunda.domain.YydSailLine;
import com.hangyi.eyunda.domain.enumeric.CargoBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.StringUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class SailLineService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydSailLineDao sailLineDao;
	@Autowired
	YydPortDao portDao;

	@Autowired
	private PortService portService;

	public List<SailLineData> getSailLinesByType(WaresBigTypeCode waresBigType, WaresTypeCode waresType,
			CargoTypeCode cargoType) {
		List<YydSailLine> yydSailLines = sailLineDao.getSailLinesByType(waresBigType, waresType, cargoType);

		List<SailLineData> sailLineDatas = new ArrayList<SailLineData>();
		for (YydSailLine yydSailLine : yydSailLines) {
			SailLineData sailLineData = this.getSailLineData(yydSailLine);
			sailLineDatas.add(sailLineData);
		}

		return sailLineDatas;
	}

	public Page<SailLineData> getPageSailLinesByType(Page<SailLineData> pageData, WaresBigTypeCode waresBigType,
			WaresTypeCode waresType, CargoTypeCode cargoType, String keyWords) {

		List<String> lstPortNo = new ArrayList<String>();
		if (keyWords != null && !"".equals(keyWords)) {
			List<YydPort> ps = portDao.findByKeyWords(keyWords);
			for (YydPort p : ps) {
				lstPortNo.add(p.getPortNo());
			}
		}

		Page<YydSailLine> page = sailLineDao.getPageSailLinesByType(pageData.getPageNo(), pageData.getPageSize(),
				waresBigType, waresType, cargoType, lstPortNo);

		List<SailLineData> sailLineDatas = new ArrayList<SailLineData>();
		for (YydSailLine yydSailLine : page.getResult()) {
			SailLineData sailLineData = this.getSailLineData(yydSailLine);
			sailLineDatas.add(sailLineData);
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(sailLineDatas);

		return pageData;
	}

	public SailLineData getById(Long id) {
		YydSailLine yydSailLine = sailLineDao.get(id);
		return this.getSailLineData(yydSailLine);
	}

	public SailLineData getSailLineData(YydSailLine yydSailLine) {
		SailLineData sailLineData = null;
		if (yydSailLine != null) {
			sailLineData = new SailLineData();

			CopyUtil.copyProperties(sailLineData, yydSailLine);

			PortData startPortData = portService.getPortData(yydSailLine.getStartPortNo());
			PortData endPortData = portService.getPortData(yydSailLine.getEndPortNo());
			sailLineData.setStartPortData(startPortData);
			sailLineData.setEndPortData(endPortData);

			Map<Integer, Double> mapPrices = this.makeMapPrices(yydSailLine.getRemark());
			sailLineData.setMapPrices(mapPrices);
		}
		return sailLineData;
	}

	private Map<Integer, Double> makeMapPrices(String cargoTypes) {
		Map<Integer, Double> mapPrices = new TreeMap<Integer, Double>();

		if (cargoTypes != null && !"".equals(cargoTypes)) {
			String[] cts = cargoTypes.split(",");
			if (cts.length > 0) {
				for (String ct : cts) {
					String[] tt = ct.split("\\^");
					if (tt.length > 0) {
						String cargoTypeName = tt[0];
						String priceValue = tt[1];
						CargoTypeCode ccargoType = CargoTypeCode.valueOf(cargoTypeName);
						Double price = Double.parseDouble(priceValue);

						mapPrices.put(ccargoType.ordinal(), price);
					}
				}
			}
		}
		return mapPrices;
	}

	// 新增或修改
	public Long saveOrUpdate(SailLineData sailLineData) throws Exception {
		YydSailLine yydSailLine = sailLineDao.get(sailLineData.getId());

		if (yydSailLine == null) {
			yydSailLine = new YydSailLine();
		}

		CopyUtil.copyProperties(yydSailLine, sailLineData);

		sailLineDao.save(yydSailLine);

		sailLineData.setId(yydSailLine.getId());

		return yydSailLine.getId();
	}

	// 删除
	public void deleteSailLine(Long id) {
		YydSailLine yydSailLine = sailLineDao.get(id);

		if (yydSailLine != null) {
			sailLineDao.delete(yydSailLine);
		}
	}

	public boolean isRightFormat(MultipartFile shipExcelFile, File modelFile) {
		try {
			if (shipExcelFile == null || shipExcelFile.isEmpty())
				return false;

			Workbook rwb = Workbook.getWorkbook(shipExcelFile.getInputStream());
			Workbook modelrwb = Workbook.getWorkbook(modelFile);

			Cell[] modelrow = modelrwb.getSheets()[0].getRow(0);
			Cell[] row = rwb.getSheet(0).getRow(0);

			// 校验格式
			for (int j = 0; j < row.length; j++) {
				if (!modelrow[j].getContents().equals(row[j].getContents())) {
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}

	}

	private String getCellValue(Cell[] cells, int index) {
		int arrayLength = cells.length;
		if (index < arrayLength)
			return cells[index].getContents().trim();
		else
			return "";
	}

	public boolean importSailLineExcel(OrderTypeCode orderType, String urlFile) throws Exception {
		try {
			File excelFile = new File(urlFile);
			if (!excelFile.exists())
				return false;

			jxl.Workbook rwb = Workbook.getWorkbook(excelFile);
			Sheet rs = rwb.getSheet(0);

			for (int j = 1; j < rs.getRows(); j++) {
				Cell[] cells = rs.getRow(j);

				if (cells != null) {
					String startPortCityName = this.getCellValue(cells, 1);// 装货港所在区域
					String startPortName = this.getCellValue(cells, 2);// 装货港
					String endPortCityName = this.getCellValue(cells, 3);// 目的港所在区域
					String endPortName = this.getCellValue(cells, 4);// 目的港
					Integer distance = Integer.parseInt(
							StringUtil.isNumeric(this.getCellValue(cells, 5)) ? this.getCellValue(cells, 5) : "0");// 航程(公里)

					Integer weight = Integer.parseInt(
							StringUtil.isNumeric(this.getCellValue(cells, 6)) ? this.getCellValue(cells, 6) : "0");

					PortData startPort = portService.getByFullPortName(startPortCityName + "." + startPortName);
					if (startPort == null) {
						ScfPortCityCode pc = ScfPortCityCode.getByDescription(startPortCityName);
						portService.save(pc.getCode(), "", startPortName);
						startPort = portService.getByFullPortName(startPortCityName + "." + startPortName);
					}

					PortData endPort = portService.getByFullPortName(endPortCityName + "." + endPortName);
					if (endPort == null) {
						ScfPortCityCode pc = ScfPortCityCode.getByDescription(endPortCityName);
						portService.save(pc.getCode(), "", endPortName);
						endPort = portService.getByFullPortName(endPortCityName + "." + endPortName);
					}

					YydSailLine sailLine = sailLineDao.getSailLine(orderType.getWaresBigType(),
							orderType.getWaresType(), orderType.getCargoType(), startPort.getPortNo(),
							endPort.getPortNo());
					if (sailLine == null)
						sailLine = new YydSailLine();

					sailLine.setWaresBigType(orderType.getWaresBigType());
					sailLine.setWaresType(orderType.getWaresType());
					sailLine.setCargoType(orderType.getCargoType());
					sailLine.setStartPortNo(startPort.getPortNo());
					sailLine.setEndPortNo(endPort.getPortNo());
					sailLine.setDistance(distance);
					sailLine.setWeight(weight);

					String remark = "", description = "";
					if (orderType.getCargoType().getCargoBigType() == CargoBigTypeCode.container) {
						int n = 0;
						List<CargoTypeCode> ctcs = CargoTypeCode.getCodesByCargoBigType(CargoBigTypeCode.container);
						for (CargoTypeCode ctc : ctcs) {
							String price = this.getCellValue(cells, 8 + n++);
							price = StringUtil.isNumeric(price) ? price : "0";

							remark += ctc.name() + "^" + price + ",";
							description += ctc.getShortName() + ":" + price + "元/个" + ",";
						}
						remark = remark.substring(0, remark.length() - 1);
						description = description.substring(0, description.length() - 1);
					} else {
						CargoTypeCode ctc = orderType.getCargoType();
						String price = this.getCellValue(cells, 7);
						price = StringUtil.isNumeric(price) ? price : "0";

						remark += ctc.name() + "^" + price;
						description += ctc.getShortName() + ":" + price + "元/吨";
					}
					sailLine.setRemark(remark);
					sailLine.setDescription(description);

					sailLineDao.save(sailLine);
				}
			}
			return true;
		} catch (Exception e) {
			throw new Exception("错误！导入航线EXCEL失败，请检查文件中数据格式是否正确！");
		}
	}

	public boolean exportSailLineExcel(File file, List<SailLineData> sailLineDatas) {
		try {
			Workbook workbook = Workbook.getWorkbook(file);
			WritableWorkbook wb = Workbook.createWorkbook(file, workbook);
			WritableSheet sheet = wb.getSheet(0);

			for (int k = sheet.getRows() - 1; k > 0; k--) {
				sheet.removeRow(k);
			}

			for (int i = 0; i < sailLineDatas.size(); i++) {
				SailLineData sd = sailLineDatas.get(i);

				sheet.addCell(new Label(0, i + 1, i + 1 + "")); // 序号

				sheet.addCell(new Label(1, i + 1, sd.getStartPortData().getPortCity().getDescription()));
				sheet.addCell(new Label(2, i + 1, sd.getStartPortData().getPortName()));
				sheet.addCell(new Label(3, i + 1, sd.getEndPortData().getPortCity().getDescription()));
				sheet.addCell(new Label(4, i + 1, sd.getEndPortData().getPortName()));
				sheet.addCell(new Label(5, i + 1, String.valueOf(sd.getDistance())));
				sheet.addCell(new Label(6, i + 1, String.valueOf(sd.getWeight())));

				if (sd.getCargoType().getCargoBigType() == CargoBigTypeCode.container) {
					int n = 0;
					Map<Integer, Double> mapPrices = sd.getMapPrices();
					for (Integer key : mapPrices.keySet()) {
						Double price = mapPrices.get(key);
						sheet.addCell(new Label(8 + n++, i + 1, String.valueOf(price)));
					}
				} else {
					int n = 0;
					Map<Integer, Double> mapPrices = sd.getMapPrices();
					for (Integer key : mapPrices.keySet()) {// 只有一项
						Double price = mapPrices.get(key);
						sheet.addCell(new Label(7 + n++, i + 1, String.valueOf(price)));
					}
				}

			}
			wb.write();
			wb.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
