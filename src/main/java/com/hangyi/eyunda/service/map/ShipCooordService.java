package com.hangyi.eyunda.service.map;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangyi.eyunda.dao.YydShipCurrRouteCooordDao;
import com.hangyi.eyunda.data.ship.ShipArvlftData;
import com.hangyi.eyunda.data.ship.ShipCooordData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.domain.YydShipCurrRouteCooord;
import com.hangyi.eyunda.domain.enumeric.ShipMonitorPlantCode;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.ship.MobileShipCooordService;
import com.hangyi.eyunda.service.ship.ShipMonitorPlantService;
import com.hangyi.eyunda.service.threads.GpsToBaiduCallable;
import com.hangyi.eyunda.service.threads.MyCallable;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CompressUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CooordUtil;
import com.hangyi.eyunda.util.FileUtil;
import com.hangyi.eyunda.util.HttpClientUtil2;

import jodd.util.Base64;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ShipCooordService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final static int THREAD_POOL_SIZE = 20;

	@Autowired
	private YydShipCurrRouteCooordDao currRouteCooordDao;
	@Autowired
	private ShipMonitorPlantService plantService;
	@Autowired
	private MobileShipCooordService mobileShipCooordService;

	// 模拟请求百度接口，将gps坐标转换为百度坐标
	public List<ShipCooordData> gps2baidu(List<ShipCooordData> shipCooordDatas) {
		try {
			// 创建一个线程池
			ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
			// 创建多个有返回值的任务
			List<Future<List<ShipCooordData>>> listFuture = new ArrayList<Future<List<ShipCooordData>>>();
			for (int i = 0; i < shipCooordDatas.size(); i += 20) {

				// 创建有返回值的任务
				Callable<List<ShipCooordData>> c = new GpsToBaiduCallable(this,
						shipCooordDatas.subList(i, Math.min(i + 20, shipCooordDatas.size())));
				// 执行任务并获取Future对象
				Future<List<ShipCooordData>> f = pool.submit(c);
				listFuture.add(f);

				Thread.sleep(100);
			}

			// 关闭线程池
			pool.shutdown();

			// List<ShipCooordData> datas = new ArrayList<ShipCooordData>();
			// 获取所有并发任务的运行结果
			for (Future<List<ShipCooordData>> f : listFuture) {
				// 从Future对象上获取任务的返回值
				// List<ShipCooordData> ds =
				f.get();
				// datas.addAll(ds);
			}
			listFuture.clear();
			listFuture = null;

			return shipCooordDatas;
		} catch (Exception e) {
			e.printStackTrace();
			return shipCooordDatas;
		}
	}

	public List<ShipCooordData> getShipCooords(String mmsi, String startTime, String endTime,
			ShipMonitorPlantCode platCode) {
		List<ShipCooordData> datas = new ArrayList<ShipCooordData>();

		try {
			if (platCode == ShipMonitorPlantCode.sailormobile) {
				datas = mobileShipCooordService.findShipSailLine(mmsi, startTime, endTime);
				return datas;
			}

			HttpClientUtil2 util = HttpClientUtil2.getInstance("UTF-8");

			String url = platCode.getCurrUrl();

			url += "?mmsi=" + mmsi;
			url += "&startTime=" + startTime.replace(" ", "%20");
			url += "&endTime=" + endTime.replace(" ", "%20");
			logger.info(url);

			// url =
			// "http://www.myships.com/myships/10026?mmsi=413900409&startTime=2015-03-04%2000:00&endTime=2015-03-14%2000:00";

			int count = 0;
			String responseStr = "";
			Map<String, Object> result = null;
			while ((result == null || result.isEmpty()) && ++count <= 5) {
				responseStr = "";
				result = null;

				Thread.sleep(200);

				try {
					responseStr = util.getStringFromGetRequest(url);
					if (responseStr != null && !"".equals(responseStr)) {
						Gson gs = new Gson();
						result = gs.fromJson(responseStr, new TypeToken<Map<String, Object>>() {
						}.getType());
					}
				} catch (Exception e) {
					continue;
				}
			}

			if (result != null && result.get("data") != null) {
				String compressData = (String) result.get("data");
				logger.info("compressData.length()=" + compressData.length());
				if (!"".equals(compressData)) {
					List<Map<String, Object>> list = null;

					while (true) {
						try {
							String decompressData = CompressUtil.getDecompressData(compressData);
							logger.info("decompressData.length()=" + decompressData.length());

							Gson gs = new Gson();
							list = gs.fromJson(decompressData, new TypeToken<List<Map<String, Object>>>() {
							}.getType());

							break;
						} catch (Exception e) {
							continue;
						}
					}

					if (list != null && !list.isEmpty()) {
						for (Map<String, Object> map : list) {
							String posTime = (String) map.get("p"); // 时间
							String lon = (String) map.get("lon"); // 经度113°53'5.40\"E
							Double longitude = (lon != null && !"".equals(lon)) ? CooordUtil.convertLon(lon) : 0.0;
							String lat = (String) map.get("lat"); // 纬度22°27'15.82\"N
							Double latitude = (lat != null && !"".equals(lat)) ? CooordUtil.convertLat(lat) : 0.0;
							String spd = (String) map.get("s");
							Double speed = (spd != null && !"".equals(spd)) ? Double.parseDouble(spd) : 0.0; // 速度
							String crs = (String) map.get("c");
							Double course = (crs != null && !"".equals(crs)) ? Double.parseDouble(crs) : 0.0; // 航向

							ShipCooordData data = new ShipCooordData();
							data.setMmsi(mmsi);
							data.setPosTime(posTime);
							data.setLongitude(longitude);
							data.setLatitude(latitude);
							data.setSpeed(speed);
							data.setCourse(course);
							data.setLng(longitude);
							data.setLat(latitude);

							datas.add(data);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}

		logger.info(mmsi + ":" + startTime + " - " + endTime + ",datas.size=" + datas.size());

		if (!datas.isEmpty())
			this.gps2baidu(datas);

		return datas;
	}

	public List<ShipCooordData> getShipCooordDatas(ShipData shipData, Calendar startTime, Calendar endTime) {
		List<ShipCooordData> shipCooordDatas = new ArrayList<ShipCooordData>();
		try {
			// 创建一个线程池
			ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
			// 创建多个有返回值的任务
			List<Future<List<ShipCooordData>>> futureList = new ArrayList<Future<List<ShipCooordData>>>();

			while (startTime.before(endTime)) {
				Calendar overTime = CalendarUtil.getTheDayMidnight(startTime);
				if (endTime.before(overTime))
					overTime = endTime;

				String sTime = CalendarUtil.toYYYY_MM_DD_HH_MM(startTime);
				String eTime = CalendarUtil.toYYYY_MM_DD_HH_MM(overTime);
				// 创建有返回值的任务
				Callable<List<ShipCooordData>> callable = new MyCallable(this, shipData.getMmsi(), sTime, eTime,
						shipData.getShipPlant());

				startTime = CalendarUtil.getTheDayZero(CalendarUtil.addDays(startTime, 1));

				// 执行任务并获取Future对象
				Future<List<ShipCooordData>> future = pool.submit(callable);
				// 添加future到集合
				futureList.add(future);
			}
			// 关闭线程池
			pool.shutdown();
			// 顺序获取返回值
			for (Future<List<ShipCooordData>> f : futureList) {
				List<ShipCooordData> cooordDatas = f.get();
				shipCooordDatas.addAll(cooordDatas);
			}

			futureList.clear();
			futureList = null;

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return shipCooordDatas;
	}

	// 分析坐标去除漂移点
	public List<ShipCooordData> analyseCooorDatas(List<ShipCooordData> datas) throws Exception {
		List<ShipCooordData> das = new ArrayList<ShipCooordData>();
		// 保存数据文件
		if (!datas.isEmpty()) {
			// 去除漂移点
			ShipCooordData ptd = datas.get(0), ntd = null, ntd2 = null;
			das.add(ptd);
			for (int i = 1; i <= datas.size() - 2; i++) {
				ntd = datas.get(i);
				ntd2 = datas.get(i + 1);
				// 如果两点足够近
				if (Math.abs(ptd.getLongitude() - ntd.getLongitude()) < 0.05
						&& Math.abs(ptd.getLatitude() - ntd.getLatitude()) < 0.05) {
					das.add(ntd);
					// 还是要放条件里面
					ptd = ntd;
				} else if (Math.abs(ntd.getLongitude() - ntd2.getLongitude()) < 0.05
						&& Math.abs(ntd.getLatitude() - ntd2.getLatitude()) < 0.05) {
					das.add(ntd);
					// 还是要放条件里面
					ptd = ntd;
				} else {
					;// 丢弃漂移点
				}
			}
			if (ntd2 != null && Math.abs(ntd.getLongitude() - ntd2.getLongitude()) < 0.05
					&& Math.abs(ntd.getLatitude() - ntd2.getLatitude()) < 0.05) {
				das.add(ntd2);
			}
		}
		return das;
	}

	// 取已有坐标数据
	public List<ShipCooordData> getShipCooordDatas(ShipData shipData, ShipArvlftData leftData) {
		List<ShipCooordData> datas = new ArrayList<ShipCooordData>();

		String realPath = Constants.SHARE_DIR;
		String url = leftData.getSailLineData();
		String jsonContent = FileUtil.readFromFile(realPath + url);

		if (jsonContent != null && !"".equals(jsonContent)) {
			Gson gs = new Gson();
			datas = gs.fromJson(jsonContent, new TypeToken<List<ShipCooordData>>() {
			}.getType());
		}

		return datas;
	}

	// 写坐标数据文件
	public String writeCooordDataFile(String mmsi, Long sailLineId, List<ShipCooordData> datas) {
		Gson gs2 = new Gson();
		String json = gs2.toJson(datas);

		String realPath = Constants.SHARE_DIR;
		String relaPath = ShareDirService.getShipDir(mmsi);
		String filename = File.separator + "SailLine" + sailLineId + ".txt";
		FileUtil.writeToFile(json, realPath + relaPath + filename);

		return relaPath + filename;
	}

	public List<ShipCooordData> getCurrShipDistCooords(String mmsis) {
		List<ShipCooordData> datas = new ArrayList<ShipCooordData>();

		String mobileMmsis = "";
		String hangxunMmsis = "";
		String baoMmsis = "";

		String[] mmsiAll = mmsis.split(",");

		for (String mmsi : mmsiAll) {
			ShipMonitorPlantCode plantCode = plantService.getPlant(mmsi);
			if (plantCode == ShipMonitorPlantCode.sailormobile) {
				mobileMmsis += "," + mmsi;
			} else if (plantCode == ShipMonitorPlantCode.shipmanagerplant) {
				hangxunMmsis += "," + mmsi;
			} else {
				baoMmsis += "," + mmsi;
			}
		}
		if (!"".equals(mobileMmsis))
			datas.addAll(this.getMobileShipCooords(mobileMmsis.substring(1)));
		if (!"".equals(hangxunMmsis))
			datas.addAll(this.getHangxunwangShipCooords(hangxunMmsis.substring(1)));
		if (!"".equals(baoMmsis))
			datas.addAll(this.getBaochuanwangShipCooords(baoMmsis.substring(1)));

		if (!datas.isEmpty())
			this.updateCurrShipCooords(datas);

		return datas;
	}

	private List<ShipCooordData> getMobileShipCooords(String mmsis) {
		List<ShipCooordData> datas = new ArrayList<ShipCooordData>();
		try {
			// 数据来源是易运达手机
			if (!"".equals(mmsis)) {
				datas = mobileShipCooordService.findShipCooords(mmsis);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return datas;
	}

	private List<ShipCooordData> getBaochuanwangShipCooords(String mmsis) {
		List<ShipCooordData> datas = new ArrayList<ShipCooordData>();
		try {
			// 数据来源是宝船网
			if (!"".equals(mmsis)) {
				DecimalFormat df = new DecimalFormat("###0");// 最多保留几位小数，就用几个#，最少位就用0来确定
				HttpClientUtil2 util = HttpClientUtil2.getInstance("UTF-8");

				String url = "http://www.myships.com/myships/10029";

				Map<String, String> params = new HashMap<String, String>();
				params.put("mmsi", mmsis);
				String responseStr = util.getStringFromPostRequest(url, params);

				Gson gs = new Gson();
				Map<String, Object> result = gs.fromJson(responseStr, new TypeToken<Map<String, Object>>() {
				}.getType());

				if (result != null && result.get("data") != null) {
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("data");

					if (list != null && !list.isEmpty()) {
						for (int i = 0; i < list.size(); i++) {
							ShipCooordData data = new ShipCooordData();
							String mmsi = df.format((Double) list.get(i).get("mmsi")); // MMSI
							String posTime = (String) list.get(i).get("updateTimeStr"); // 时间
							String lat = (String) list.get(i).get("lat"); // 纬度22°27'15.82\"N
							Double latitude = CooordUtil.convertLat(lat);
							String lon = (String) list.get(i).get("lon"); // 经度113°53'5.40\"E
							Double longitude = CooordUtil.convertLon(lon);
							String spd = (String) list.get(i).get("speed");
							String crs = (String) list.get(i).get("course");
							Double speed = Double.parseDouble("".equals(spd) ? "0" : spd); // 速度
							Double course = Double.parseDouble("".equals(crs) ? "0" : crs); // 航向

							data.setMmsi(mmsi);
							data.setPosTime(posTime);
							data.setLatitude(latitude);
							data.setLongitude(longitude);
							data.setSpeed(speed);
							data.setCourse(course);
							data.setLng(longitude);
							data.setLat(latitude);

							datas.add(data);
						}
					}

					if (!datas.isEmpty())
						this.gps2baidu(datas);

				} else {
					String[] mmsiAll = mmsis.split(",");

					List<YydShipCurrRouteCooord> yscrcs = currRouteCooordDao.getByMmsis(mmsiAll);
					for (YydShipCurrRouteCooord yscrc : yscrcs) {
						String jsonStr = yscrc.getCurrCooord();
						if (jsonStr != null && !"".equals(jsonStr)) {
							Gson gs2 = new Gson();
							ShipCooordData data2 = gs2.fromJson(jsonStr, new TypeToken<ShipCooordData>() {
							}.getType());
							datas.add(data2);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return datas;
	}

	private List<ShipCooordData> getHangxunwangShipCooords(String mmsis) {
		List<ShipCooordData> datas = new ArrayList<ShipCooordData>();
		try {
			// 如果数据来源是船管平台
			if (!"".equals(mmsis)) {
				HttpClientUtil2 util = HttpClientUtil2.getInstance("UTF-8");

				String url = "http://localhost:8080/eyunda/shipMonitor/shipDistributoin";
				url += "?mmsis=" + mmsis;
				String responseStr = util.getStringFromGetRequest(url);

				Gson gs = new Gson();
				Map<String, Object> result = gs.fromJson(responseStr, new TypeToken<Map<String, Object>>() {
				}.getType());

				if (result != null && result.get("data") != null) {
					String jsonData = gs.toJson(result.get("data"));
					if (!"".equals(jsonData)) {
						List<ShipCooordData> cooordDatas = gs.fromJson(jsonData, new TypeToken<List<ShipCooordData>>() {
						}.getType());
						if (cooordDatas != null && cooordDatas.size() > 0) {
							// 添加所有坐标到分布数据中
							datas.addAll(cooordDatas);
						}
					}
					if (!datas.isEmpty())
						this.gps2baidu(datas);
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return datas;
	}

	private void updateCurrShipCooords(List<ShipCooordData> datas) {
		try {
			if (datas != null && !datas.isEmpty()) {
				for (ShipCooordData data : datas) {
					YydShipCurrRouteCooord routeCooord = currRouteCooordDao.getByMmsi(data.getMmsi());
					if (routeCooord == null) {
						routeCooord = new YydShipCurrRouteCooord();
						routeCooord.setMmsi(data.getMmsi());
					}
					routeCooord.setCurrCooord(data.getJsonString());

					currRouteCooordDao.save(routeCooord);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	// 模拟请求百度接口，将gps坐标转换为百度坐标
	public List<ShipCooordData> twentyGps2baidu(List<ShipCooordData> shipCooordDatas) {
		try {
			HttpClientUtil2 util = HttpClientUtil2.getInstance("UTF-8");

			if (!shipCooordDatas.isEmpty()) {
				String xResults = ""; // 经度组合的字符串
				String yResults = ""; // 维度组成的字符串
				for (int i = 0; i < shipCooordDatas.size(); i++) {
					xResults += shipCooordDatas.get(i).getLng();
					yResults += shipCooordDatas.get(i).getLat();
					if (i < shipCooordDatas.size() - 1) {
						xResults += ",";
						yResults += ",";
					}
				}
				// 百度接口每次能转换坐标最大个数为20个
				String url = "http://api.map.baidu.com/ag/coord/convert?from=0&to=4&mode=1&x=" + xResults + "&y="
						+ yResults;
				String responseStr = util.getStringFromPostRequest(url, null);
				Thread.sleep(50);

				base64Decoder(responseStr, shipCooordDatas);
			}

			return shipCooordDatas;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ShipCooordData>();
		}
	}

	private void base64Decoder(String json, List<ShipCooordData> shipCooordDatas) {
		try {
			if (shipCooordDatas != null && json.indexOf("[", 0) != -1) {
				JSONArray jsonArray = new JSONArray(json);
				for (int i = 0; i < shipCooordDatas.size(); i++) {
					if (i < jsonArray.length()) {
						JSONObject jObj = jsonArray.getJSONObject(i);
						byte[] xResult = Base64.decode(String.valueOf(jObj.getString("x")));
						byte[] yResult = Base64.decode(String.valueOf(jObj.getString("y")));

						if (xResult.length != 0 && yResult.length != 0) {
							String lng = new String(xResult, "utf-8");
							String lat = new String(yResult, "utf-8");

							shipCooordDatas.get(i)
									.setLongitude(Math.round(Double.parseDouble(lng) * 1000000.0) / 1000000.0);
							shipCooordDatas.get(i)
									.setLatitude(Math.round(Double.parseDouble(lat) * 1000000.0) / 1000000.0);
						}
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/**
	 * 获取船舶监控图像
	 * 
	 * @param mmsi
	 *            船舶mmsi
	 * @param posTime
	 *            摄像时间
	 * @param cameraNo
	 *            监控设备号
	 * @return 图片地址
	 */
	public String getShipImageUrl(String mmsi, String posTime, String cameraNo) {
		try {
			HttpClientUtil2 util = HttpClientUtil2.getInstance("UTF-8");

			String url = "http://192.168.1.199:8080/eyundaService/shipMonitor/shipMonitorImage";
			url += "?mmsi=" + mmsi;
			url += "&imageTime=" + posTime.replace(" ", "%20");
			url += "&cameraNo=" + cameraNo;
			String responseStr = util.getStringFromGetRequest(url);

			if (responseStr != null && !"".equals(responseStr)) {
				Gson gs = new Gson();
				Map<String, String> result = gs.fromJson(responseStr, new TypeToken<Map<String, String>>() {
				}.getType());

				if (result != null && result.get("imageUrl") != null) {
					String imageUrl = gs.toJson(result.get("imageUrl"));
					return imageUrl;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
