package com.hangyi.eyunda.dao.sqliteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.sqliteConnectionUtil.ConnectionUtil;

@Repository
public class YydSqliteMonitorDao {
	private ResultSet rs = null;
	private Connection con = null;
	private PreparedStatement ps = null;
	private static final String gpsDataTable = "tblData";
	
	/**
	 * 获取船舶分布坐标
	 * @param url 船舶分布数文件库路径
	 * @return shipCooordData 船舶分布坐标
	 */
	public Map<String, String> getDistCooordDatas(String url) {
		try {
			this.getDistCooordDatas(url, false);
		} finally {
			ConnectionUtil.close(con, ps, rs);
		}
		return null;
	}
	
	/**
	 * 获取实时船舶动态
	 * @param url 船舶坐标数据文件路径 
	 * @param startTime 开始时间	
	 * @param endTime 结束时间
	 * @return gpsDatas 包含gps等信息的字符串集合
	 */
	public List<Map<String, String>> getShipCooords(String url, String startTime, String endTime) {
		List<Map<String, String>> gpsDatas = new ArrayList<Map<String, String>>();
		try {
			String sql = "select daData, recTime from " + gpsDataTable + " where recTime"
							+ " between '" + startTime + "' and '" + endTime + "'";
			
			con = ConnectionUtil.getConnection(url);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				Map<String, String> map = new HashMap<String, String>();
				String gpsData = rs.getString("daData");
				String recTime = rs.getString("recTime");
				map.put("gpsData",gpsData);
				map.put("recTime",recTime);
				gpsDatas.add(map);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(con, ps, rs);
		}
		return gpsDatas;
	}
	
	// 获取第一个点或最后一个点
	public Map<String, String> getDistCooordDatas(String url, boolean isFirst) {
		try {
			String sql = "";
			if(isFirst)
				sql = "select daData, recTime from " + gpsDataTable + " limit 0,1";
			else
				sql = "select daData, recTime from " + gpsDataTable + " order by daId desc limit 0,1";
			
			con = ConnectionUtil.getConnection(url);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				Map<String, String> shipCooordData = new HashMap<String, String>() ;
				
				String gpsData = rs.getString("daData");
				String recTime = rs.getString("recTime");
				shipCooordData.put("gpsData", gpsData);
				shipCooordData.put("recTime", recTime);

				return shipCooordData;
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(con, ps, rs);
		}
		return null;
	}
}