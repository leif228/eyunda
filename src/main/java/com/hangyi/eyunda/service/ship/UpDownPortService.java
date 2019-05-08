package com.hangyi.eyunda.service.ship;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import com.hangyi.eyunda.dao.YydUpDownPortDao;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.data.ship.UpDownPortData;
import com.hangyi.eyunda.domain.YydPort;
import com.hangyi.eyunda.domain.YydUpDownPort;
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
public class UpDownPortService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydUpDownPortDao upDownPortDao;
	@Autowired
	YydPortDao portDao;

	@Autowired
	private PortService portService;

	public List<UpDownPortData> getUpDownPortsByType(WaresBigTypeCode waresBigType, WaresTypeCode waresType,
			CargoTypeCode cargoType) {
		List<YydUpDownPort> yydUpDownPorts = upDownPortDao.getUpDownPortsByType(waresBigType, waresType, cargoType);

		List<UpDownPortData> upDownPortDatas = new ArrayList<UpDownPortData>();
		for (YydUpDownPort yydUpDownPort : yydUpDownPorts) {
			UpDownPortData upDownPortData = this.getUpDownPortData(yydUpDownPort);

			if (upDownPortData != null)
				upDownPortDatas.add(upDownPortData);
		}

		return upDownPortDatas;
	}

	public Page<UpDownPortData> getPageUpDownPortsByType(Page<UpDownPortData> pageData, WaresBigTypeCode waresBigType,
			WaresTypeCode waresType, CargoTypeCode cargoType, String keyWords) {

		List<String> lstPortNo = new ArrayList<String>();
		if (keyWords != null && !"".equals(keyWords)) {
			List<YydPort> ps = portDao.findByKeyWords(keyWords);
			for (YydPort p : ps) {
				lstPortNo.add(p.getPortNo());
			}
		}

		Page<YydUpDownPort> page = upDownPortDao.getPageUpDownPortsByType(pageData.getPageNo(), pageData.getPageSize(),
				waresBigType, waresType, cargoType, lstPortNo);

		List<UpDownPortData> upDownPortDatas = new ArrayList<UpDownPortData>();
		for (YydUpDownPort yydUpDownPort : page.getResult()) {
			UpDownPortData UpDownPortData = this.getUpDownPortData(yydUpDownPort);
			upDownPortDatas.add(UpDownPortData);
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(upDownPortDatas);

		return pageData;
	}

	public UpDownPortData getById(Long id) {
		YydUpDownPort yydUpDownPort = upDownPortDao.get(id);
		return this.getUpDownPortData(yydUpDownPort);
	}

	public UpDownPortData getUpDownPortData(YydUpDownPort yydUpDownPort) {
		UpDownPortData upDownPortData = null;
		if (yydUpDownPort != null) {
			upDownPortData = new UpDownPortData();

			CopyUtil.copyProperties(upDownPortData, yydUpDownPort);

			PortData startPortData = portService.getPortData(yydUpDownPort.getStartPortNo());
			upDownPortData.setStartPortData(startPortData);
		}
		return upDownPortData;
	}

	// 新增或修改
	public Long saveOrUpdate(UpDownPortData upDownPortData) throws Exception {
		YydUpDownPort yydUpDownPort = upDownPortDao.get(upDownPortData.getId());

		if (yydUpDownPort == null) {
			yydUpDownPort = new YydUpDownPort();
		}

		CopyUtil.copyProperties(yydUpDownPort, upDownPortData);

		upDownPortDao.save(yydUpDownPort);

		upDownPortData.setId(yydUpDownPort.getId());

		return yydUpDownPort.getId();
	}

	// 删除
	public void deleteUpDownPort(Long id) {
		YydUpDownPort yydUpDownPort = upDownPortDao.get(id);

		if (yydUpDownPort != null) {
			upDownPortDao.delete(yydUpDownPort);
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

	public boolean importUpDownPortExcel(OrderTypeCode orderType, String urlFile) {
		try {
			File excelFile = new File(urlFile);
			if (!excelFile.exists())
				return false;

			jxl.Workbook rwb = Workbook.getWorkbook(excelFile);
			Sheet rs = rwb.getSheet(0);

			for (int j = 1; j < rs.getRows(); j++) {
				Cell[] cells = rs.getRow(j);

				if (cells != null) {
					String startPortCityName = this.getCellValue(cells, 1);// 装卸港所在区域
					String startPortName = this.getCellValue(cells, 2);// 装卸港
					
					int weight = Integer.parseInt(
							StringUtil.isNumeric(this.getCellValue(cells, 3)) ?this.getCellValue(cells, 3):"0");

					PortData startPort = portService.getByFullPortName(startPortCityName + "." + startPortName);
					if (startPort == null) {
						ScfPortCityCode pc = ScfPortCityCode.getByDescription(startPortCityName);
						portService.save(pc.getCode(), "", startPortName);
						startPort = portService.getByFullPortName(startPortCityName + "." + startPortName);
					}

					YydUpDownPort upDownPort = upDownPortDao.getUpDownPort(orderType.getWaresBigType(),
							orderType.getWaresType(), orderType.getCargoType(), startPort.getPortNo());
					if (upDownPort == null)
						upDownPort = new YydUpDownPort();

					upDownPort.setWaresBigType(orderType.getWaresBigType());
					upDownPort.setWaresType(orderType.getWaresType());
					upDownPort.setCargoType(orderType.getCargoType());
					upDownPort.setStartPortNo(startPort.getPortNo());
					upDownPort.setWeight(weight);

					upDownPortDao.save(upDownPort);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean exportUpDownPortExcel(File file, List<UpDownPortData> upDownPortDatas) {
		try {
			Workbook workbook = Workbook.getWorkbook(file);
			WritableWorkbook wb = Workbook.createWorkbook(file, workbook);
			WritableSheet sheet = wb.getSheet(0);

			for (int k = sheet.getRows() - 1; k > 0; k--) {
				sheet.removeRow(k);
			}

			for (int i = 0; i < upDownPortDatas.size(); i++) {
				sheet.addCell(new Label(0, i + 1, i + 1 + "")); // 序号

				sheet.addCell(
						new Label(1, i + 1, upDownPortDatas.get(i).getStartPortData().getPortCity().getDescription()));
				sheet.addCell(new Label(2, i + 1, upDownPortDatas.get(i).getStartPortData().getPortName()));
				sheet.addCell(new Label(3, i + 1, String.valueOf(upDownPortDatas.get(i).getWeight())));
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
