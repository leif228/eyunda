package com.hangyi.eyunda.service.sqlite;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.sqliteDao.YydSqliteMonitorDao;
import com.hangyi.eyunda.data.ship.ShipCooordData;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.FileUtil;

/**
 * @author zhangyu
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class SqliteDataService {
	@Autowired
	private YydSqliteMonitorDao sqliteMonitorDao;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获取船舶监控数据
	 * 
	 * @param mmsi
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ShipCooordData> getShipCooordDatas(Long shipId, String mmsi, String startTime, String endTime) {
		List<ShipCooordData> shipCooordDatas = new ArrayList<ShipCooordData>();

		Calendar startYMD = CalendarUtil.parseYY_MM_DD(startTime);
		Calendar endYMD = CalendarUtil.parseYY_MM_DD(endTime);

		while (endYMD.compareTo(startYMD) > -1) {

			String yyyyMMdd = CalendarUtil.toYYYYMMDD(startYMD);

			// 船舶坐标数据库文件的路径
			String url = "/" + shipId + "/" + yyyyMMdd + "/" + "DATA" + "/" + shipId + ".dhx";

			if (FileUtil.isFileExist(Constants.SHIP_IMAGE + url)) {
				List<Map<String, String>> gpsDatas = sqliteMonitorDao.getShipCooords(url, startTime, endTime);
				if (gpsDatas.size() > 0) {
					List<ShipCooordData> cooordDatas = this.getGpsDatas(gpsDatas, mmsi);
					if (cooordDatas != null) {
						shipCooordDatas.addAll(cooordDatas);
					}
				}
			}
			startYMD = CalendarUtil.addDays(startYMD, 1);
		}
		return shipCooordDatas;
	}

	/**
	 * 将包含gps等信息的字符串转换成ShipCooordData
	 * 
	 * @param gpsDatas
	 *            包含gps等信息的字符串
	 * @return shipCooordDatas 船舶坐标集合
	 */
	private ShipCooordData getGpsDatas(Map<String, String> gpsData, String mmsi) {
		ShipCooordData shipCooordData = null;
		try {
			shipCooordData = new ShipCooordData();
			if (!"".equals(gpsData) && gpsData != null) {

				DecimalFormat decimalFormat = new DecimalFormat("######0.000000");

				String gps = gpsData.get("gpsData");

				String longitude = "";
				Double longPrefix = Double.parseDouble(gps.substring(27, 30));
				Double longSuffix = Double.parseDouble(gps.substring(30, 32) + "." + gps.substring(32, 36));
				longitude = String.valueOf(longPrefix + Double.parseDouble(decimalFormat.format(longSuffix / 60)));

				String latitude = "";
				Double latiPrefix = Double.parseDouble(gps.substring(36, 38));
				Double latiSuffix = Double.parseDouble(gps.substring(38, 40) + "." + gps.substring(40, 44));
				latitude = String.valueOf(latiPrefix + Double.parseDouble(decimalFormat.format(latiSuffix / 60)));

				String course = gps.substring(46, 49) + "." + gps.substring(49, 50);
				String speed = gps.substring(50, 54) + "." + gps.substring(54, 55);
				String posTime = gpsData.get("recTime");

				shipCooordData.setMmsi(mmsi);
				shipCooordData.setLongitude(Double.parseDouble(longitude));
				shipCooordData.setLatitude(Double.parseDouble(latitude));
				shipCooordData.setCourse(Double.parseDouble(course));
				shipCooordData.setSpeed(Double.parseDouble(speed));
				shipCooordData.setPosTime(posTime);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return shipCooordData;
	}

	/**
	 * 将包含gps等信息的字符串转集合换成ShipCooordData
	 * 
	 * @param gpsDatas
	 *            包含gps等信息的字符串
	 * @return shipCooordDatas 船舶坐标集合
	 */
	private List<ShipCooordData> getGpsDatas(List<Map<String, String>> gpsDatas, String mmsi) {
		List<ShipCooordData> shipCooordDatas = new ArrayList<ShipCooordData>();
		if (gpsDatas.size() > 0) {
			for (Map<String, String> gpsData : gpsDatas) {
				ShipCooordData shipCooordData = this.getGpsDatas(gpsData, mmsi);
				if (shipCooordData != null)
					shipCooordDatas.add(shipCooordData);
			}
		}
		return shipCooordDatas;
	}

	/**
	 * 获取船舶监控图片
	 * 
	 * @param shipId
	 *            船舶id
	 * @param imageTime
	 *            图片拍摄时间
	 * @param cameraNo
	 *            摄像头编号
	 * @return imageUrl 图片地址
	 */
	public String getMonitorImage(Long shipId, String imageTime, String cameraNo) {
		try {
			Calendar imageYMD = CalendarUtil.parseYYYY_MM_DD_HH_MM(imageTime);
			String yyyyMMdd = CalendarUtil.toYYYYMMDD(imageYMD);
			String hourMinute = CalendarUtil.toHH_mm(imageYMD).replace(":", "");

			// 图片路径
			String imageUrl = "/" + shipId + "/" + yyyyMMdd + "/" + "PIC0" + cameraNo + "/" + hourMinute + ".JPG";

			if (FileUtil.isFileExist(Constants.SHIP_IMAGE + imageUrl))
				return imageUrl;

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
}
