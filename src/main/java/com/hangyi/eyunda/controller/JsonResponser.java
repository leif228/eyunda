package com.hangyi.eyunda.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hangyi.eyunda.util.MD5;

public final class JsonResponser {

	public static final String RETURNCODE = "returnCode";
	public static final String MESSAGE = "message";
	public static final String CONTENT = "content";

	public static final String CONTENTMD5 = "contentMD5";
	public static final String CONTENTMD5CHANGED = "contentMD5Changed";

	public static final String SUCCESS = "Success";
	public static final String FAILURE = "Failure";

	private JsonResponser() {
	}

	public static void respondWithJson(HttpServletResponse response, Map<String, Object> mapParam) {
		try {
			String contentMD5 = null, jsonStr = null;
			Gson gson = new Gson();

			if (mapParam.containsKey(CONTENTMD5)) {
				contentMD5 = (String) mapParam.remove(CONTENTMD5);
				mapParam.put(CONTENTMD5CHANGED, true);

				jsonStr = gson.toJson(mapParam);

				if (contentMD5 != null && !"".equals(contentMD5)) {
					String svMD5 = MD5.toMD5(jsonStr);
					if (svMD5.equals(contentMD5)) {
						Map<String, Object> mapParam2 = new HashMap<String, Object>();
						mapParam2.put(CONTENTMD5CHANGED, false);
						mapParam2.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
						jsonStr = gson.toJson(mapParam2);
					}
				}
			} else {
				jsonStr = gson.toJson(mapParam);
			}

			JsonResponser.respondWithJson(response, jsonStr);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void respondWithText(HttpServletResponse response, Map<String, Object> mapParam) {
		try {
			response.setContentType("text/html; charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter writer = response.getWriter();

			Gson gson = new Gson();
			String jsonStr = gson.toJson(mapParam);

			writer.write(jsonStr);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void respondWithJson(HttpServletResponse response, String jsonStr) {
		try {
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter writer = response.getWriter();

			writer.write(jsonStr);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
