package com.hangyi.eyunda.controller.manage.complain;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.complain.ComplainData;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.complain.ComplainService;
import com.hangyi.eyunda.service.manage.FilterWordsService;
import com.hangyi.eyunda.service.portal.login.UserService.ResultType;

@Controller
@RequestMapping("/manage/complain")
public class ComplainController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ComplainService complainService;
	@Autowired
	private FilterWordsService filterWordsService;
	
	@RequestMapping(value = "/complainInfo", method = { RequestMethod.POST,RequestMethod.GET })
	public ModelAndView Complain(Page<ComplainData> pageData, HttpServletRequest request , HttpServletResponse response) {
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-complain");
		
		complainService.getComplainDatas(pageData);
		
		mav.addObject("pageData", pageData);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.ORDER);
		mav.addObject("menuAct", MenuCode.ORDER_COMPLAIN);
		return mav;
	}
	
	// 查看一条建议投诉
	@RequestMapping(value = "/complainInfo/show", method = { RequestMethod.POST, RequestMethod.GET })
	public void show(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ComplainData complainData = complainService.getComplainData(id);
			
			if(complainData != null){
				map.put("complainData", complainData);
				map.put("returnCode", ResultType.SUCCESS);
				map.put("message", ResultType.SUCCESS.getDescription());
			}else {
				map.put("returnCode", ResultType.FAILURE);
				map.put("message", "该条记录不存在！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("returnCode", ResultType.FAILURE);
			map.put("message", ResultType.FAILURE.getDescription());
		}
		JsonResponser.respondWithJson(response, map);
		
		return ;
	}
		
	// 回复一条建议投诉
	@RequestMapping(value = "/complainInfo/reply", method = { RequestMethod.POST, RequestMethod.GET })
	public void reply(Long id, String reply, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			if(!"".equals(reply)){
				String newReply = filterWordsService.removeFilterWords(reply);
				
				boolean result = complainService.replyComplain(id, newReply);
				
				if(result){
					map.put("returnCode", ResultType.SUCCESS);
					map.put("message", "回复成功!");
				}else {
					map.put("returnCode", ResultType.FAILURE);
					map.put("message", "已经回复过了！");
				}
			}else { 
				map.put("returnCode", ResultType.FAILURE);
				map.put("message", "内容不能为空！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("returnCode", ResultType.FAILURE);
			map.put("message", ResultType.FAILURE.getDescription());
		}
		JsonResponser.respondWithJson(response, map);
		
		return ;
	}
	
	// 删除
	@RequestMapping(value = "/complainInfo/delete", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView deleteComplain(Long id, HttpServletRequest request, HttpServletResponse response) {
		complainService.deleteComplain(id);
		ModelAndView mav = new ModelAndView("redirect:/manage/complain/complainInfo");
		return mav;
	}
	
}
