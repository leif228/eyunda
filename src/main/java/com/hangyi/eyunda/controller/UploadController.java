package com.hangyi.eyunda.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.chat.ChatMessageService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.MultipartUtil;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private ChatMessageService chatMessageService;

	@RequestMapping(value = "/imageUpload", method = { RequestMethod.POST })
	public void imageUpload(Long roomId, MultipartFile mpf, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");

			// 上传文件到指定位置
			String url = chatMessageService.savePic(mpf, roomId);
			if (url != "" || url != null) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
				map.put(JsonResponser.CONTENT, url);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/fileUpload", method = { RequestMethod.POST })
	public void fileUpload(Long roomId, MultipartFile mpf, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");

			// 上传文件到指定位置
			String url = chatMessageService.saveFile(mpf, roomId);
			if (url != "" || url != null) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
				map.put(JsonResponser.CONTENT, url);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/voiceUpload", method = { RequestMethod.POST })
	public void voiceUpload(Long roomId, MultipartFile mpf, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");

			// 上传文件到指定位置
			String url = chatMessageService.saveVoice(mpf, roomId);
			if (url != "" || url != null) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
				map.put(JsonResponser.CONTENT, url);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/bugUpload", method = { RequestMethod.POST })
	public void bugUpload(MultipartFile mpf, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (mpf != null && !mpf.isEmpty()) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getBugDir();
				String prefix = "bug";
				String url = MultipartUtil.uploadFile(mpf, realPath, relativePath, prefix);

				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
				map.put(JsonResponser.CONTENT, url);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

}
