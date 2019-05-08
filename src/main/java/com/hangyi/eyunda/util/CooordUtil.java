package com.hangyi.eyunda.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hangyi.eyunda.data.ship.ShipCooordData;

public class CooordUtil {

	private static final double EARTH_RADIUS = 6378137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double GetDistance(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		// s = Math.round(s * 1000.0) / 1000.0;
		return s;
	}
	//计算时速(节=海里/小时)
	public static Double getSpeed(double lng1, double lat1, double lng2, double lat2,  String startTime, String endTime){
		double distance = GetDistance(lng1, lat1, lng2, lat2);
		//Double distanceGM = Double.valueOf(distance);//换算成公里
		Double distanceGM = Double.valueOf(distance)*0.5399568/1000.0;//换算成海里
		//Double distanceGM = Double.valueOf(distance)/1000.0;//换算成公里 
		Double time = getTimeDistance(startTime,endTime);
		Double result = distanceGM/time;
		return result;
	}
	//计算时间差
	public static Double getTimeDistance( String startTime, String endTime){
		int second = CalendarUtil.secondDiff(CalendarUtil.parseYYYY_MM_DD_HH_MM_SS(endTime), CalendarUtil.parseYYYY_MM_DD_HH_MM_SS(startTime));
		Double hour = second*1.0D/3600.0;
		return hour;
	}
	/**
	 * 计算两个节点点方位角
	 * @param lat_a
	 * @param lng_a
	 * @param lat_b
	 * @param lng_b
	 * @return
	 */
	public static double gps2d(double lat_a, double lng_a, double lat_b, double lng_b) {
		double d = 0.0;
		double radLat1 = rad(lat_a);
		double radLat2 = rad(lat_b);
		double radLng1 = rad(lng_a);
		double radLng2= rad(lng_b);
		d = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radLng2 - radLng1);
		d = Math.sqrt(1 - d * d);
		d = Math.cos(radLat2) * Math.sin(radLng2 - radLng1) / d;
		d = Math.asin(d) * 180 / Math.PI;
		// d = Math.round(d*10000);
		return 180-d;
	}
	
	/** 经度113°53'5.40\"E */
	public static double convertLon(String lon) {
		double longitude = 0.0;
		if (lon != null && !"".equals(lon)) {
			lon = lon.trim();
			double du = Double.parseDouble(lon.substring(0, lon.indexOf("°")));
			double fen = Double.parseDouble(lon.substring(lon.indexOf("°") + 1, lon.indexOf("'")));
			double miao = Double.parseDouble(lon.substring(lon.indexOf("'") + 1, lon.indexOf("\"")));
			longitude = Math.round((du + fen / 60 + miao / 60 / 60) * 1000000.0) / 1000000.0;
			if (!lon.substring(lon.length() - 1).equals("E"))
				longitude = -1 * longitude;
		}
		return longitude;
	}

	/** 纬度22°27'15.82\"N */
	public static double convertLat(String lat) {
		double latitude = 0.0;
		if (lat != null && !"".equals(lat)) {
			lat = lat.trim();
			double du = Double.parseDouble(lat.substring(0, lat.indexOf("°")));
			double fen = Double.parseDouble(lat.substring(lat.indexOf("°") + 1, lat.indexOf("'")));
			double miao = Double.parseDouble(lat.substring(lat.indexOf("'") + 1, lat.indexOf("\"")));
			latitude = Math.round((du + fen / 60 + miao / 60 / 60) * 1000000.0) / 1000000.0;
			if (!lat.substring(lat.length() - 1).equals("N"))
				latitude = -1 * latitude;
		}
		return latitude;
	}
	private static List<ShipCooordData> parseResult(String result) {
		List<ShipCooordData> list = new ArrayList<ShipCooordData>();
		JsonParser parser = new JsonParser();
		JsonElement el = parser.parse(result);
		JsonArray jsonArray = null;
		if (el.isJsonArray()) {
			jsonArray = el.getAsJsonArray();
		}
		ShipCooordData scd = new ShipCooordData();
		Gson gson = new Gson();
		Iterator<JsonElement> it = jsonArray.iterator();
		while (it.hasNext()) {
			JsonElement j = (JsonElement) it.next();
			scd = gson.fromJson(j, ShipCooordData.class);
			list.add(scd);
		}
		return list;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//double distance = GetDistance(118.06, 24.27, 121.29, 31.14);
		//System.out.println("厦门到上海的距离(km):" + distance);
		String json = "[{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:22:49\",\"longitude\":113.03036912128,\"latitude\":22.486811796237,\"speed\":2.3,\"course\":206.0,\"lng\":113.0186,\"lat\":22.483397},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:26:19\",\"longitude\":113.02826954679,\"latitude\":22.481879792436,\"speed\":7.3,\"course\":189.5,\"lng\":113.0165,\"lat\":22.4785},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:27:19\",\"longitude\":113.02776966202,\"latitude\":22.479871147789,\"speed\":7.6,\"course\":189.1,\"lng\":113.016,\"lat\":22.4765},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:28:20\",\"longitude\":113.02736940886,\"latitude\":22.477661196177,\"speed\":7.6,\"course\":187.8,\"lng\":113.0156,\"lat\":22.474297},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:29:49\",\"longitude\":113.02696614265,\"latitude\":22.474453878753,\"speed\":7.7,\"course\":189.2,\"lng\":113.015197,\"lat\":22.471097},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:30:48\",\"longitude\":113.02656557478,\"latitude\":22.472349714669,\"speed\":7.5,\"course\":181.0,\"lng\":113.014797,\"lat\":22.469},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:31:19\",\"longitude\":113.02666540849,\"latitude\":22.471348601538,\"speed\":7.5,\"course\":175.2,\"lng\":113.014897,\"lat\":22.467997},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:32:18\",\"longitude\":113.02686759416,\"latitude\":22.469254947459,\"speed\":7.5,\"course\":176.2,\"lng\":113.0151,\"lat\":22.4659},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:33:20\",\"longitude\":113.02666357024,\"latitude\":22.467048424846,\"speed\":7.4,\"course\":188.3,\"lng\":113.014897,\"lat\":22.463697},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:34:21\",\"longitude\":113.02646532852,\"latitude\":22.464947734788,\"speed\":7.4,\"course\":182.0,\"lng\":113.0147,\"lat\":22.4616},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:35:19\",\"longitude\":113.02646383278,\"latitude\":22.462944448069,\"speed\":7.4,\"course\":176.8,\"lng\":113.0147,\"lat\":22.459597},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:36:20\",\"longitude\":113.02665932524,\"latitude\":22.460947959942,\"speed\":7.4,\"course\":172.2,\"lng\":113.014897,\"lat\":22.457597},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:36:58\",\"longitude\":113.02684432311,\"latitude\":22.459890258345,\"speed\":7.2,\"course\":172.9,\"lng\":113.015083,\"lat\":22.456536},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:37:31\",\"longitude\":113.02700472244,\"latitude\":22.458892932636,\"speed\":7.1,\"course\":166.9,\"lng\":113.015244,\"lat\":22.455536},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:37:50\",\"longitude\":113.02735987361,\"latitude\":22.457960376094,\"speed\":7.1,\"course\":159.9,\"lng\":113.0156,\"lat\":22.454597},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:38:20\",\"longitude\":113.02775940377,\"latitude\":22.45707018906,\"speed\":7.1,\"course\":156.7,\"lng\":113.016,\"lat\":22.4537},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:38:59\",\"longitude\":113.02817564528,\"latitude\":22.456121554211,\"speed\":7.1,\"course\":157.1,\"lng\":113.016417,\"lat\":22.452744},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:39:32\",\"longitude\":113.02864415932,\"latitude\":22.455252521958,\"speed\":6.9,\"course\":145.7,\"lng\":113.016886,\"lat\":22.451867},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:39:52\",\"longitude\":113.02934704969,\"latitude\":22.454497606275,\"speed\":6.9,\"course\":133.8,\"lng\":113.017589,\"lat\":22.4511},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:40:20\",\"longitude\":113.030154843,\"latitude\":22.453910939884,\"speed\":7.1,\"course\":123.6,\"lng\":113.018397,\"lat\":22.4505},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:40:26\",\"longitude\":113.03024083545,\"latitude\":22.453867867419,\"speed\":7.1,\"course\":123.3,\"lng\":113.018483,\"lat\":22.450456},{\"id\":0,\"mmsi\":\"413906601\",\"shipName\":\"\",\"posTime\":\"2016-07-17 20:40:50\",\"longitude\":113.03115815783,\"latitude\":22.45342351322,\"speed\":7.2,\"course\":114.6,\"lng\":113.0194,\"lat\":22.449997}]";
		List<ShipCooordData> cooords = parseResult(json);
		for(int i=0; i<cooords.size()-1;i++){
			ShipCooordData first = cooords.get(i);
			ShipCooordData second = cooords.get(i+1);
			double distance = GetDistance(first.getLng().doubleValue(), first.getLat().doubleValue(), second.getLng().doubleValue(), second.getLat().doubleValue());
			double speed = Math.round(getSpeed(first.getLng().doubleValue(), first.getLat().doubleValue(), second.getLng().doubleValue(), second.getLat().doubleValue(),  first.getPosTime(), second.getPosTime())*10.0)/10.0;
			double angle = Math.round(gps2d( first.getLat().doubleValue() , first.getLng().doubleValue(), second.getLat().doubleValue() , second.getLng().doubleValue())*10.0)/10.0;
			Double hour = getTimeDistance(first.getPosTime(), second.getPosTime());
			System.out.println("坐标:["+first.getLatitude()+","+first.getLongitude()+"]--[" +second.getLatitude()+","+second.getLongitude()+"],原始速度："+second.getSpeed()+",原始方向："+second.getCourse()+",计算出来距离："+distance+",计算出来时间："+hour+",计算出来速度："+speed+",计算出来角度:"+angle);
		}

	}

}
