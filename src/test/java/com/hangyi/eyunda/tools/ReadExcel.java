package com.hangyi.eyunda.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {
	
	private static String filePath = "E:/temp/全国各地经纬度.xls";
	/**
	 * 读取Excel文件的内容
	 * 
	 * @param file
	 *            待读取的文件
	 * @return
	 */
	public static List<PCALatLng> readExcel(File file) {
		//StringBuffer sb = new StringBuffer();
		List<PCALatLng> list = new ArrayList<PCALatLng>();

		Workbook wb = null;
		try {
			// 构造Workbook（工作薄）对象
			wb = Workbook.getWorkbook(file);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (wb == null)
			return null;

		// 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
		Sheet[] sheet = wb.getSheets();

		if (sheet != null && sheet.length > 0) {
			// 对每个工作表进行循环
			for (int i = 0; i < sheet.length; i++) {
				// 得到当前工作表的行数
				int rowNum = sheet[i].getRows();
				for (int j = 2; j < rowNum; j++) {
					// 得到当前行的所有单元格
					Cell[] cells = sheet[i].getRow(j);
					if (cells != null && cells.length > 0) {
						PCALatLng pca = new PCALatLng();
						// 对每个单元格进行循环
						for (int k = 0; k < cells.length; k++) {
							// 读取当前单元格的值
							String cellValue = cells[k].getContents();
							//sb.append(cellValue + " ");
							switch (k) {
							case 0:
								pca.setProvinceName(cellValue);
								break;
							case 1:
								pca.setCityName(cellValue);
								break;
							case 2:
								pca.setAreaName(cellValue);
								break;
							case 3:
								pca.setLongitude(cellValue);
								break;
							case 4:
								pca.setLatitude(cellValue);
								break;
							default:
								break;
							}
						}
						list.add(pca);
					}
					//sb.append(" ");
				}
			//	sb.append(" ");
			}
		}
		// 最后关闭资源，释放内存
		wb.close();
	//	return sb.toString();
		return list;
	}
	public static void main(String[] args) {
		List<PCALatLng> ss = readExcel(new File(filePath));
//		for(PCALatLng p:ss){
//			System.out.println(p.toString());
//		}
		System.out.println(ss.size());
	}
}
