package com.hangyi.eyunda.util;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.hangyi.eyunda.data.BaseData;

public class ExcelExportUtil<T> {

	// 导出数据表
	public static void export(List<BaseData> datas, File excelFile) throws Exception {
		// 打开 excel 文件
		Workbook wb = Workbook.getWorkbook(excelFile);
		// 创建一个副本，并写回源文件
		WritableWorkbook workBook = Workbook.createWorkbook(excelFile, wb);
		// 获得工作簿的第一张表
		WritableSheet sheet = workBook.getSheet(0);
		// 获取所有统计数据，并写入 excel 工作簿对象
		for (int i = 0; i < datas.size(); i++) {
			int line = i + 1;

			int j = 0;
			for (String key : datas.get(i).getMap().keySet()) {
				j++;

				Object value = datas.get(i).getMap().get(key);
				if (value instanceof Integer) {
					sheet.addCell(new Label(j, line, Integer.toString((Integer) value)));
				} else if (value instanceof Long) {
					sheet.addCell(new Label(j, line, Long.toString((Long) value)));
				} else if (value instanceof Double) {
					sheet.addCell(new Label(j, line, Double.toString((Double) value)));
				} else if (value instanceof Boolean) {
					sheet.addCell(new Label(j, line, Boolean.toString((Boolean) value)));
				} else if (value instanceof Calendar) {
					sheet.addCell(new Label(j, line, CalendarUtil.toYYYY_MM_DD((Calendar) value)));
				} else {
					sheet.addCell(new Label(j, line, (String) value));
				}

			}
		}
		// 将工作簿对象写出到磁盘，并关闭
		workBook.write();
		workBook.close();
	}

	public static String xls2String(File excelFile) {
		String result = "";
		try {
			StringBuilder sb = new StringBuilder();
			jxl.Workbook rwb = Workbook.getWorkbook(excelFile);

			Sheet[] sheet = rwb.getSheets();
			for (int i = 0; i < sheet.length; i++) {
				Sheet rs = rwb.getSheet(i);
				for (int j = 0; j < rs.getRows(); j++) {
					Cell[] cells = rs.getRow(j);
					for (int k = 0; k < cells.length; k++) {
						String content = cells[k].getContents();
						sb.append(content);
						System.out.println(content + "\t");
					}
					System.out.println("\n");
				}
			}
			result += sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		File excelFile = new File("e:/test.xls");

		String resultStr = ExcelExportUtil.xls2String(excelFile);

		System.out.println(resultStr);
	}

}
