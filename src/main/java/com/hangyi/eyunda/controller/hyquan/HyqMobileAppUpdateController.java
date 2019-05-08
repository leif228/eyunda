package com.hangyi.eyunda.controller.hyquan;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.manage.UpdateInfoData;
import com.hangyi.eyunda.util.FileUtil;

@Controller
@RequestMapping("/hyquan/mobile")
public class HyqMobileAppUpdateController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** 升级 */
	@RequestMapping(value = "/appUpdate", method = { RequestMethod.GET })
	public void appUpdate(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> content = new HashMap<String, Object>();
			// 读取update.txt信息，获取升级信息
			String staticPath = request.getSession().getServletContext().getRealPath("/");
			String filePath = "";
			if ("".equals(id) || id == null) {
				filePath = staticPath + "/WEB-INF/static/phone/update.json";
			} else {
				filePath = staticPath + "/WEB-INF/static/phone/update_" + id + ".json";
			}
			String json = FileUtil.readFromFile(filePath);
			Gson gson = new Gson();
			UpdateInfoData res = gson.fromJson(json, UpdateInfoData.class);
			content.put("version", res.getVersion());// 软件当前版本

			content.put("url", res.getUrl());// 软件下载地址
			content.put("note", res.getNote());// 软件说明

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "读取数据成功");
			map.put(JsonResponser.CONTENT, content);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

}
