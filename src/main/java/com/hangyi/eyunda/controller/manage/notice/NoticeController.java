package com.hangyi.eyunda.controller.manage.notice;

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
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.notice.NoticeData;
import com.hangyi.eyunda.domain.enumeric.NtcColumnCode;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.notice.NoticeService;

@Controller
@RequestMapping("/manage/notice")
public class NoticeController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NoticeService noticeService;
	
	private static final int PAGE_SIZE = 10;
	
	// 
	@RequestMapping(value = "/notice", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView notice(NtcColumnCode selectCode, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/manage/manage-notice");
		if(selectCode==null)
			selectCode = NtcColumnCode.news;
		
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		mav.addObject("pageNo", pageNo);
		
		Page<NoticeData> pageData = noticeService.getManagePageNotices(PAGE_SIZE, pageNo, selectCode);
		mav.addObject("pageData", pageData);
		
		mav.addObject("selectCode", selectCode);
		mav.addObject("columnCodes", NtcColumnCode.values());
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.NOTICE);
		mav.addObject("menuAct", MenuCode.NOTICE_NOTICE);
		return mav;
	}
	
	// 取消发布
	@RequestMapping(value = "/notice/unpublish", method = { RequestMethod.GET, RequestMethod.POST })
	public void unpublish(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			boolean result = noticeService.unpublish(id);
			
			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取消发布成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作异常！");
		}
		JsonResponser.respondWithJson(response, map);
	}
	
	// 取消置顶
	@RequestMapping(value = "/notice/untop", method = { RequestMethod.GET, RequestMethod.POST })
	public void untop(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			boolean result = noticeService.untop(id);
			
			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取消置顶成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作异常！");
		}
		JsonResponser.respondWithJson(response, map);
	}
	
	// 发布
	@RequestMapping(value = "/notice/publish", method = { RequestMethod.GET, RequestMethod.POST })
	public void publish(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			boolean result = noticeService.publish(id);
			
			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "发布成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作异常");
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
		return ;
	}
	
	// 置顶
	@RequestMapping(value = "/notice/top", method = { RequestMethod.GET, RequestMethod.POST })
	public void top(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			boolean result = noticeService.top(id);
			
			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "置顶成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作异常");
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
		return ;
	}
	
	// 删除
	@RequestMapping(value = "/notice/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView delete(NtcColumnCode selectCode, Long id, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("redirect:/manage/notice/notice");
		
		noticeService.delete(id);
		
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		mav.addObject("pageNo", pageNo);
		
		mav.addObject("selectCode", selectCode);
		
		return mav;
	}
	
	// 添加
	@RequestMapping(value = "/notice/edit", method = { RequestMethod.GET })
	public ModelAndView edit(NtcColumnCode selectCode, Long id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/manage/manage-notice-edit");
		
		// 页号
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		mav.addObject("pageNo", pageNo);
		
		NoticeData noticeData = null;
		if(id==null)
			noticeData = new NoticeData();
		else
			noticeData = noticeService.getManageNoticeDataById(id);
		
		mav.addObject("noticeData", noticeData);
		mav.addObject("selectCode", selectCode);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.NOTICE);
		mav.addObject("menuAct", MenuCode.NOTICE_NOTICE);
		return mav;
	}
	
	// 保存
	@RequestMapping(value = "/notice/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView save(NtcColumnCode selectCode, NoticeData noticeData, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("redirect:/manage/notice/notice");
		
		noticeData.setNtcColumn(selectCode);
		noticeService.saveOrUpdate(noticeData);
		
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		mav.addObject("pageNo", pageNo);
		
		mav.addObject("selectCode", noticeData.getNtcColumn());
		
		return mav;
	}
	
	// kindeditor插件(本地图片上传)
	@RequestMapping(value = "/save/image", method = { RequestMethod.GET, RequestMethod.POST })
	public void image(MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String url = noticeService.saveImage(imgFile);
			
			String rootUrl  = request.getContextPath();
			url = rootUrl + "/download/imageDownload?url=" + url;
			
			map.put("error", 0);
			map.put("url", url);
		} catch (Exception e) {
			map.put("error", 1);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
		
	}
}