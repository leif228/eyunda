package com.hangyi.eyunda.controller.space;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.controller.portal.login.LoginController;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.release.CarrierIssueData;
import com.hangyi.eyunda.domain.enumeric.ColumnCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.service.EnumConst.SpaceMenuCode;
import com.hangyi.eyunda.service.account.PrivilegeService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.release.ReleaseCarrierService;
import com.hangyi.eyunda.util.CookieUtil;

@Controller
@RequestMapping("/space/release")
public class MySiteController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final int PAGE_SIZE = 10;

	@Autowired
	private UserService userService;
	@Autowired
	private ReleaseCarrierService releaseCarrierService;
	@Autowired
	private PrivilegeService privilegeService;

	// 获取当前用户对象，含用户所在公司列表及当前公司
	private UserData getLoginUserData(HttpServletRequest request) throws Exception {
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		if (userData == null)
			throw new Exception("未登录不能进行此项操作!");

		String strCompId = CookieUtil.getCookieValue(request, LoginController.CURR_COMP_ID);
		if (strCompId == null)
			strCompId = "0"; // throw new Exception("你还不属于任何公司，请先注册公司并加入!");
		Long compId = Long.parseLong(strCompId);

		CompanyData currCompanyData = userService.getCompanyData(compId);
		List<CompanyData> companyDatas = userService.getCompanyDatas(userData.getId());
		userData.setCurrCompanyData(currCompanyData);
		userData.setCompanyDatas(companyDatas);

		if (!userService.isRole(userData, UserPrivilegeCode.manager))
			throw new Exception("非管理员不能进行此项操作!");

		if (!privilegeService.isBrokerCompany(compId))
			throw new Exception("非审批的代理人公司不能进行此项操作!");

		return userData;
	}

	@RequestMapping(value = "/myRelease", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView myRelease(Page<CarrierIssueData> pageData, ColumnCode selectCode, HttpServletRequest request)
			throws Exception {
		UserData userData = this.getLoginUserData(request);
		UserData sa = privilegeService.getCompanySuperAdmin(userData.getCurrCompanyData().getId());

		ModelAndView mav = new ModelAndView("/space/space-myRelease");

		// 用户信息
		mav.addObject("userData", sa);
		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_RELEASE);

		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		mav.addObject("pageNo", pageNo);

		if (selectCode == null)
			selectCode = ColumnCode.GSJJ;

		if (releaseCarrierService.needInit(sa.getId()))
			releaseCarrierService.init(userData.getCurrCompanyData().getId(), sa.getId());

		pageData.setPageNo(pageNo);
		releaseCarrierService.getReleaseType(pageData, sa.getId(), selectCode);

		// 分页信息
		mav.addObject("pageData", pageData);
		// 选中频道
		mav.addObject("selectCode", selectCode);
		// 所有频道
		mav.addObject("columnCodes", ColumnCode.values());

		return mav;
	}

	// 添加
	@RequestMapping(value = "/myRelease/addRelease", method = { RequestMethod.GET })
	public ModelAndView addRelease(Long id, ColumnCode selectCode, HttpServletRequest request) throws Exception {
		UserData userData = this.getLoginUserData(request);
		UserData sa = privilegeService.getCompanySuperAdmin(userData.getCurrCompanyData().getId());

		ModelAndView mav = new ModelAndView("/space/space-releaseCarrier-edit");

		CarrierIssueData carrierIssueData = releaseCarrierService.getCarrierIssueData(id);
		if (carrierIssueData != null) {
			selectCode = carrierIssueData.getColumnCode();
		}

		// 页号
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		mav.addObject("pageNo", pageNo);

		mav.addObject("carrierIssueData", carrierIssueData);
		// 用户信息
		mav.addObject("userData", sa);
		// 栏目信息
		mav.addObject("selectCode", selectCode);
		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_RELEASE);

		return mav;
	}

	// 保存
	@SuppressWarnings("unused")
	@RequestMapping(value = "/myRelease/saveRelease", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView saveRelease(CarrierIssueData carrierIssueData, HttpServletRequest request) throws Exception {
		UserData userData = this.getLoginUserData(request);
		UserData sa = privilegeService.getCompanySuperAdmin(userData.getCurrCompanyData().getId());

		ModelAndView mav = new ModelAndView("redirect:/space/release/myRelease");

		releaseCarrierService.saveOrUpdate(carrierIssueData);

		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		mav.addObject("pageNo", pageNo);

		String selectCode = ServletRequestUtils.getStringParameter(request, "columnCode", "");
		mav.addObject("selectCode", selectCode);

		return mav;
	}

	// 发布
	@RequestMapping(value = "/myRelease/publish", method = { RequestMethod.GET, RequestMethod.POST })
	public void publish(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);
			UserData sa = privilegeService.getCompanySuperAdmin(userData.getCurrCompanyData().getId());

			boolean result = releaseCarrierService.publish(id, sa.getId());

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "发布成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "已发布！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作异常");
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
		return;
	}

	// 取消发布
	@RequestMapping(value = "/myRelease/unpublish", method = { RequestMethod.GET, RequestMethod.POST })
	public void unpublish(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);
			UserData sa = privilegeService.getCompanySuperAdmin(userData.getCurrCompanyData().getId());

			boolean result = releaseCarrierService.unpublish(id, sa.getId());

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取消发布成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "已取消发布！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作异常！");
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 删除
	@RequestMapping(value = "/myRelease/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView delete(Long id, ColumnCode selectCode, HttpServletRequest request) throws Exception {
		UserData userData = this.getLoginUserData(request);
		UserData sa = privilegeService.getCompanySuperAdmin(userData.getCurrCompanyData().getId());

		ModelAndView mav = new ModelAndView("redirect:/space/release/myRelease");

		releaseCarrierService.delete(id, sa.getId());

		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		mav.addObject("pageNo", pageNo);
		mav.addObject("selectCode", selectCode);

		return mav;
	}

	// 刷新
	@RequestMapping(value = "/myRelease/refresh", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView refresh(ColumnCode selectCode, HttpServletRequest request) throws Exception {
		UserData userData = this.getLoginUserData(request);
		UserData sa = privilegeService.getCompanySuperAdmin(userData.getCurrCompanyData().getId());

		ModelAndView mav = new ModelAndView("redirect:/space/release/myRelease");

		releaseCarrierService.refresh(userData.getCurrCompanyData().getId(), sa.getId());

		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		mav.addObject("pageNo", pageNo);
		mav.addObject("selectCode", selectCode);

		return mav;
	}

}
