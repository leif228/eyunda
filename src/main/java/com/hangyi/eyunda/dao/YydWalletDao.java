package com.hangyi.eyunda.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydWallet;
import com.hangyi.eyunda.domain.enumeric.ApplyReplyCode;
import com.hangyi.eyunda.domain.enumeric.FeeItemCode;
import com.hangyi.eyunda.domain.enumeric.PayStatusCode;
import com.hangyi.eyunda.domain.enumeric.SettleStyleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;

@Repository
public class YydWalletDao extends PageHibernateDao<YydWallet, Long> {

	public Page<YydWallet> findWalletByStyle(Long userId, SettleStyleCode settleStyle, PayStatusCode paymentStatus,
			ApplyReplyCode refundStatus, String startTime, String endTime, int pageNo, int pageSize) {
		Page<YydWallet> page = new Page<YydWallet>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(Page.DESC);
		page.setOrderBy("gmtPayment");

		Criteria criteria = this.getSession().createCriteria(entityClass);

		if (userId != 0L)
			criteria.add(Restrictions.or(Restrictions.eq("sellerId", userId), Restrictions.eq("brokerId", userId),
					Restrictions.eq("buyerId", userId)));

		if (settleStyle != null)
			criteria.add(Restrictions.eq("settleStyle", settleStyle));

		if (paymentStatus != null)
			criteria.add(Restrictions.eq("paymentStatus", paymentStatus));

		if (refundStatus != null)
			criteria.add(Restrictions.eq("refundStatus", refundStatus));

		if (startTime != null && !"".equals(startTime)) {
			Calendar caleStart = CalendarUtil.getTheDayZero(CalendarUtil.parseYYYY_MM_DD(startTime));
			criteria.add(Restrictions.ge("gmtPayment", caleStart));
		}

		if (endTime != null && !"".equals(endTime)) {
			Calendar caleEnd = CalendarUtil.getTheDayMidnight(CalendarUtil.parseYYYY_MM_DD(endTime));
			criteria.add(Restrictions.le("gmtPayment", caleEnd));
		}

		return findPageByCriteria(page, criteria);
	}

	public Page<YydWallet> findByRefundStatus(ApplyReplyCode refundStatus, Long userId, String startTime,
			String endTime, int pageNo, int pageSize) {
		Page<YydWallet> page = new Page<YydWallet>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(Page.DESC);
		page.setOrderBy("gmtPayment");

		Criteria criteria = this.getSession().createCriteria(entityClass);

		if (refundStatus != null)
			criteria.add(Restrictions.eq("refundStatus", refundStatus));
		else
			criteria.add(Restrictions.ne("refundStatus", ApplyReplyCode.noapply));

		if (userId != 0L)
			criteria.add(Restrictions.or(Restrictions.eq("sellerId", userId), Restrictions.eq("brokerId", userId),
					Restrictions.eq("buyerId", userId)));

		if (startTime != null && !"".equals(startTime)) {
			Calendar caleStart = CalendarUtil.getTheDayZero(CalendarUtil.parseYYYY_MM_DD(startTime));
			criteria.add(Restrictions.ge("gmtPayment", caleStart));
		}
		if (endTime != null && !"".equals(endTime)) {
			Calendar caleEnd = CalendarUtil.getTheDayMidnight(CalendarUtil.parseYYYY_MM_DD(endTime));
			criteria.add(Restrictions.le("gmtPayment", caleEnd));
		}

		return findPageByCriteria(page, criteria);
	}

	public Page<Long> findByKhsdErrorStatus(YesNoCode errorStatus, Long userId, String startTime, String endTime,
			int pageNo, int pageSize) {
		Page<Long> page = new Page<Long>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(Page.DESC);
		page.setOrderBy("gmtPayment");

		String hql = " select distinct a.id from YydWallet a, YydWalletKhsd b where a.paymentNo = b.paymentNo";

		if (errorStatus != null && errorStatus == YesNoCode.no)
			hql += " and b.rspCode = '01'";

		if (errorStatus != null && errorStatus == YesNoCode.yes)
			hql += " and b.rspCode <> '01'";

		if (userId != 0L)
			hql += " and (a.sellerId = " + userId + " or a.brokerId = " + userId + " or a.buyerId = " + userId + ")";

		if (startTime != null && !"".equals(startTime))
			hql += " and a.gmtPayment >= '" + startTime + "'";

		if (endTime != null && !"".equals(endTime))
			hql += " and a.gmtPayment <= '" + endTime + "'";

		super.findPage(page, hql);

		return page;
	}

	public Page<Long> findByZjjzErrorStatus(YesNoCode errorStatus, Long userId, String startTime, String endTime,
			int pageNo, int pageSize) {
		Page<Long> page = new Page<Long>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(Page.DESC);
		page.setOrderBy("gmtPayment");

		String hql = " select distinct a.id from YydWallet a, YydWalletZjjz b where a.paymentNo = b.paymentNo";

		if (errorStatus != null && errorStatus == YesNoCode.no)
			hql += " and b.rspCode = '000000'";

		if (errorStatus != null && errorStatus == YesNoCode.yes)
			hql += " and b.rspCode <> '000000'";

		if (userId != 0L)
			hql += " and (a.sellerId = " + userId + " or a.brokerId = " + userId + " or a.buyerId = " + userId + ")";

		if (startTime != null && !"".equals(startTime))
			hql += " and a.gmtPayment >= '" + startTime + "'";

		if (endTime != null && !"".equals(endTime))
			hql += " and a.gmtPayment <= '" + endTime + "'";

		super.findPage(page, hql);

		return page;
	}

	public Page<YydWallet> findByUserId(Long userId, String startTime, String endTime, int pageNo, int pageSize) {
		Page<YydWallet> page = new Page<YydWallet>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(Page.DESC);
		page.setOrderBy("gmtPayment");

		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.or(Restrictions.eq("sellerId", userId), Restrictions.eq("brokerId", userId),
				Restrictions.eq("buyerId", userId)));

		if (startTime != null && !"".equals(startTime))
			criteria.add(
					Restrictions.ge("gmtPayment", CalendarUtil.getTheDayZero(CalendarUtil.parseYY_MM_DD(startTime))));
		if (endTime != null && !"".equals(endTime))
			criteria.add(
					Restrictions.le("gmtPayment", CalendarUtil.getTheDayMidnight(CalendarUtil.parseYY_MM_DD(endTime))));

		return findPageByCriteria(page, criteria);
	}

	public YydWallet findByPaymentNo(String paymentNo) {
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("paymentNo", paymentNo));

		List<YydWallet> yydWallets = super.find(criteria);

		if (!yydWallets.isEmpty())
			return yydWallets.get(0);
		else
			return null;
	}

	public YydWallet findByAngel(Long receiverId, Long cabinId) {
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.ne("buyerId", Long.parseLong(Constants.ANGEL_ID)));
		criteria.add(Restrictions.ne("sellerId", receiverId));
		criteria.add(Restrictions.ne("orderId", cabinId));
		criteria.add(Restrictions.eq("feeItem", FeeItemCode.bonus));

		/*
		 * Calendar st = CalendarUtil.getTheDayZero(
		 * CalendarUtil.addDays(CalendarUtil.now(), -1 *
		 * Integer.parseInt(Constants.INTERVAL_DAYS))); Calendar et =
		 * CalendarUtil.getTheDayMidnight(CalendarUtil.addDays(CalendarUtil.now(
		 * ), -1)); criteria.add(Restrictions.ge("gmtPayment", st));
		 * criteria.add(Restrictions.le("gmtPayment", et));
		 */

		List<YydWallet> yydWallets = super.find(criteria);

		if (!yydWallets.isEmpty())
			return yydWallets.get(0);
		else
			return null;
	}

	public List<YydWallet> findByOrderId(FeeItemCode feeItem, Long orderId) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.ne("orderId", 0L));
		criteria.add(Restrictions.eq("orderId", orderId));
		criteria.add(Restrictions.eq("feeItem", feeItem));

		criteria.addOrder(Order.asc("id"));

		List<YydWallet> yydWallets = super.find(criteria);

		return yydWallets;
	}

	// 查询 等待托运人确认付款、资金托管中、未申请退款、过资金托管期的、各种资金托管付款记录
	public List<YydWallet> getExpiredBills() {
		String hql = " from YydWallet where settleStyle = " + SettleStyleCode.pay.ordinal() + " and (feeItem = "
				+ FeeItemCode.face.ordinal() + " or feeItem = " + FeeItemCode.prefee.ordinal() + ")"
				+ " and paymentStatus = " + PayStatusCode.WAIT_CONFIRM.ordinal() + " and refundStatus = "
				+ ApplyReplyCode.noapply.ordinal() + " and (to_days(now())-to_days(gmtPayment) > suretyDays) ";

		List<YydWallet> yydWallets = super.find(hql);

		return yydWallets;
	}

	@SuppressWarnings("rawtypes")
	public Page findByPayStatus(PayStatusCode payStatus, String startTime, String endTime, int pageNo, int pageSize) {
		Page<YydWallet> page = new Page<YydWallet>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(Page.DESC);
		page.setOrderBy("gmtPayment");

		Criteria criteria = this.getSession().createCriteria(entityClass);

		// 目前只有prefee及face项目收取平台服务费
		criteria.add(Restrictions.or(Restrictions.eq("feeItem", FeeItemCode.prefee),
				Restrictions.eq("feeItem", FeeItemCode.face)));

		if (payStatus != null)
			criteria.add(Restrictions.eq("paymentStatus", payStatus));

		if (startTime != null && !"".equals(startTime)) {
			Calendar caleStart = CalendarUtil.getTheDayZero(CalendarUtil.parseYYYY_MM_DD(startTime));
			criteria.add(Restrictions.ge("gmtPayment", caleStart));
		}
		if (endTime != null && !"".equals(endTime)) {
			Calendar caleEnd = CalendarUtil.getTheDayMidnight(CalendarUtil.parseYYYY_MM_DD(endTime));
			criteria.add(Restrictions.le("gmtPayment", caleEnd));
		}

		return findPageByCriteria(page, criteria);
	}

	public List<YydWallet> findByKhWaitStatus(YesNoCode waitStatus) {
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("khRefundFlag", waitStatus));

		List<YydWallet> yydWallets = super.find(criteria);

		return yydWallets;
	}

}
