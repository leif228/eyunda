package com.hangyi.eyunda.chat.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hangyi.eyunda.data.ship.ShipCooordData;

public class ReportEvent extends BaseEvent {
	private static final long serialVersionUID = 6619170001649508819L;

	public static final String USERID = "userId";
	public static final String LOGIN_NAME = "loginName";
	public static final String TRUE_NAME = "trueName";
	public static final String NICK_NAME = "nickName";
	public static final String SIMNO = "simNo";
	public static final String LOCATIONS = "locations";
	
	public ReportEvent(Map<String, String> source) {
		super(source);
	}

	public void setUserId(Long userId) {
		eventMap.put(USERID, Long.toString(userId));
	}

	public Long getUserId() {
		return Long.parseLong(eventMap.get(USERID));
	}

	public void setLoginName(String loginName) {
		eventMap.put(LOGIN_NAME, loginName);
	}

	public String getLoginName() {
		return (String) eventMap.get(LOGIN_NAME);
	}

	public void setTrueName(String trueName) {
		eventMap.put(TRUE_NAME, trueName);
	}

	public String getTrueName() {
		return (String) eventMap.get(TRUE_NAME);
	}

	public void setNickName(String nickName) {
		eventMap.put(NICK_NAME, nickName);
	}

	public String getNickName() {
		return (String) eventMap.get(NICK_NAME);
	}
	
	public void setSimNo(String simNo) {
		eventMap.put(SIMNO, simNo);
	}
	
	public String getSimNo() {
		return (String) eventMap.get(SIMNO);
	}

	public List<ShipCooordData> getLoactions() {
		String result = (String) eventMap.get(LOCATIONS);
		return parseResult(result);
	}

	public void setLocations(List<ShipCooordData> ls) {
		Gson gs = new Gson();
		eventMap.put(LOCATIONS, gs.toJson(ls));
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private List<ShipCooordData> parseResult(String result) {
		List<ShipCooordData> list = new ArrayList<ShipCooordData>();
		if(result != null){
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
		}
		return list;
	}
}
