package com.hangyi.eyunda.service.order;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydOrderTemplateDao;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.order.OrderTemplateData;
import com.hangyi.eyunda.domain.YydOrderTemplate;
import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class OrderTemplateService extends BaseService<YydOrderTemplate, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydOrderTemplateDao yydOrderTemplateDao;

	@Autowired
	private UserService userService;

	@Override
	public PageHibernateDao<YydOrderTemplate, Long> getDao() {
		return (PageHibernateDao<YydOrderTemplate, Long>) yydOrderTemplateDao;
	}

	// 构造合同模板Data
	public OrderTemplateData getOrderTemplateData(YydOrderTemplate yydOrderTemplate) {
		try {
			if (yydOrderTemplate != null) {
				OrderTemplateData otd = new OrderTemplateData();
				CopyUtil.copyProperties(otd, yydOrderTemplate);

				// 查找代理人
				UserData Broker = userService.getById(yydOrderTemplate.getOperatorId());
				otd.setOperator(Broker);

				return otd;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	// 取得所有的合同模板
	public List<OrderTemplateData> getOrderTemplateDatas() {
		List<OrderTemplateData> orderTemplateDatas = new ArrayList<OrderTemplateData>();
		try {
			List<YydOrderTemplate> yydOrderTemplates = yydOrderTemplateDao.getYydOrderTemplates();
			if (yydOrderTemplates != null && !yydOrderTemplates.isEmpty()) {
				for (YydOrderTemplate yot : yydOrderTemplates) {
					OrderTemplateData otd = this.getOrderTemplateData(yot);
					orderTemplateDatas.add(otd);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return orderTemplateDatas;
	}

	// 按合同类型取得单个合同模板信息
	public OrderTemplateData getTemplateByOrderType(OrderTypeCode orderType) {
		YydOrderTemplate yot = yydOrderTemplateDao.getTemplateByOrderType(orderType);
		return this.getOrderTemplateData(yot);
	}

	// 按模板ID取得单个合同模板信息
	public OrderTemplateData getOrderTemplateData(Long id) {
		if (id == null) {
			return null;
		}
		YydOrderTemplate yot = yydOrderTemplateDao.get(id);
		return this.getOrderTemplateData(yot);
	}

	public boolean saveOrderTemplateData(OrderTemplateData otd) {
		// 删除模板文件
		String realPath = Constants.SHARE_DIR;// 取得不同系统的根目录盘符
		String relativePath = ShareDirService.getOrderDir(0L) + "/" + otd.getOrderType().toString() + ".pdf";

		File otFile = new File(realPath + relativePath);
		if (otFile.exists())
			otFile.delete();

		// 保存模板记录
		YydOrderTemplate yot = yydOrderTemplateDao.get(otd.getId());

		if (yot == null)
			yot = new YydOrderTemplate();

		CopyUtil.copyProperties(yot, otd);
		yot.setCreateTime(Calendar.getInstance());
		yot.setReleaseStatus(ReleaseStatusCode.unpublish);

		yydOrderTemplateDao.save(yot);

		return true;
	}

	// 删除合同模板
	public boolean deleteOrderTemplate(Long id) {
		try {
			YydOrderTemplate yot = yydOrderTemplateDao.get(id);

			// 删除模板文件
			String realPath = Constants.SHARE_DIR;// 取得不同系统的根目录盘符
			String relativePath = ShareDirService.getOrderDir(0L) + "/" + yot.getOrderType().toString() + ".pdf";

			File otFile = new File(realPath + relativePath);
			if (otFile.exists())
				otFile.delete();

			// 删除模板记录
			yydOrderTemplateDao.delete(yot);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	// 发布
	public boolean publishOrderTemplate(Long id) {
		try {
			YydOrderTemplate yot = yydOrderTemplateDao.get(id);
			if (yot != null && yot.getReleaseStatus() == ReleaseStatusCode.unpublish) {
				yot.setReleaseStatus(ReleaseStatusCode.publish);
				yot.setReleaseTime(Calendar.getInstance());
				yydOrderTemplateDao.save(yot);
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	// 取消发布
	public boolean unpublishOrderTemplate(Long id) {
		try {
			YydOrderTemplate yot = yydOrderTemplateDao.get(id);
			if (yot != null && yot.getReleaseStatus() == ReleaseStatusCode.publish) {
				yot.setReleaseStatus(ReleaseStatusCode.unpublish);
				yydOrderTemplateDao.save(yot);
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
}
