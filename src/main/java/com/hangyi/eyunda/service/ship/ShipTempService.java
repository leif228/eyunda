package com.hangyi.eyunda.service.ship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydShipTempDao;
import com.hangyi.eyunda.domain.YydShipTemp;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.HttpClientUtil2;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ShipTempService extends BaseService<YydShipTemp, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydShipTempDao shipTempDao;

	@Override
	public PageHibernateDao<YydShipTemp, Long> getDao() {
		return (PageHibernateDao<YydShipTemp, Long>) shipTempDao;
	}

	@SuppressWarnings("unchecked")
	public void getTempShips(int i) throws Exception {
		try {
			HttpClientUtil2 util = HttpClientUtil2.getInstance("UTF-8");

			System.out.println("处理第 " + i + " 页");

			String url = "http://www.myships.com/myships/11001?term=&no=" + i;
			String resultStr = util.getStringFromGetRequest(url);

			Gson gson = new Gson();
			HashMap<String, Object> map = gson.fromJson((String) resultStr, new TypeToken<Map<String, Object>>() {
			}.getType());

			List<HashMap<String, String>> datas = (ArrayList<HashMap<String, String>>) map.get("data");
			for (HashMap<String, String> data : datas) {
				String shipType = data.get("shipType");
				String country = data.get("country");
				if (shipType.contains("货船") && country.contains("中国")) {
					YydShipTemp ship = new YydShipTemp();

					ship.setCountry(data.get("country"));// 国家
					ship.setShipType(data.get("shipType"));// 船类
					ship.setShipName(data.get("shipName"));// 船名
					ship.setMmsi(data.get("mmsi"));// MMSI编号
					ship.setImo((data.get("imo") == null) ? "" : data.get("imo"));// IMO编号
					ship.setCallsign((data.get("callsign") == null) ? "" : data.get("callsign"));// 呼号

					String url2 = "http://www.myships.com/myships/10025?mmsi=" + data.get("mmsi");
					String resultStr2 = util.getStringFromGetRequest(url2);

					Gson gson2 = new Gson();
					Map<String, Object> map2 = gson2.fromJson((String) resultStr2,
							new TypeToken<Map<String, Object>>() {
							}.getType());

					Double status = (Double) map2.get("status");
					if (status.equals(0D)) {
						Map<String, Object> data2 = (HashMap<String, Object>) map2.get("data");

						ship.setChsName((String) data2.get("chsName"));// 中文船名
						ship.setLength((Double) data2.get("length"));// 船长
						ship.setBreadth((Double) data2.get("breadth"));// 船宽

						String strDraught = (String) data2.get("draught");
						if ("N/A".equals(strDraught) || "n/a".equals(strDraught) || "***".equals(strDraught))
							ship.setDraught(0D);
						else
							ship.setDraught(Double.parseDouble((String) data2.get("draught")));// 吃水深度

						ship.setCommTypeName((String) data2.get("commTypeName"));// 报位类型

						shipTempDao.save(ship);
					}
				}
			}
			return;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception("请求不能及时应答，重新处理！");
		}
	}

}
