package com.hangyi.eyunda.service.wallet;

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

import com.ecc.emp.data.InvalidArgumentException;
import com.ecc.emp.data.KeyedCollection;
import com.ecc.emp.data.ObjectNotFoundException;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydWalletKhsdDao;
import com.hangyi.eyunda.data.wallet.WalletKhsdData;
import com.hangyi.eyunda.domain.YydWalletKhsd;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class WalletKhsdService extends BaseService<YydWalletKhsd, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydWalletKhsdDao walletKhsdDao;

	@Override
	public PageHibernateDao<YydWalletKhsd, Long> getDao() {
		return (PageHibernateDao<YydWalletKhsd, Long>) walletKhsdDao;
	}

	public List<WalletKhsdData> findByPaymentNo(String paymentNo) {
		List<WalletKhsdData> datas = new ArrayList<WalletKhsdData>();

		List<YydWalletKhsd> zjjzs = walletKhsdDao.findByPaymentNo(paymentNo);
		for (YydWalletKhsd zjjz : zjjzs) {
			WalletKhsdData data = this.getWalletKhsdData(zjjz);
			datas.add(data);
		}

		return datas;
	}

	public void callBeforeRecord(String paymentNo, String tranFunc, KeyedCollection input)
			throws ObjectNotFoundException, InvalidArgumentException {

		YydWalletKhsd ywkh = walletKhsdDao.getByPayTranFunc(paymentNo, tranFunc);
		if (ywkh == null)
			ywkh = new YydWalletKhsd();

		ywkh.setCreateTime(Calendar.getInstance());
		ywkh.setPaymentNo(paymentNo);
		ywkh.setTranFunc(tranFunc);
		ywkh.setSendParames(input.toJSon());
		ywkh.setRecvParames("");
		ywkh.setRspCode("");
		ywkh.setRspMsg("");

		walletKhsdDao.save(ywkh);
	}

	public void callBehindRecord(String paymentNo, String tranFunc, KeyedCollection output) {
		String errorCode = "";// 银行返回的应答码
		String errorMsg = "";// 银行返回的应答描述
		try {
			if ("00".equals((String) output.getDataValue("status"))) {
				errorCode = "00";
				errorMsg = "跨行收单接口调用成功，处理中...";
			} else if ("01".equals((String) output.getDataValue("status"))) {
				errorCode = "01";
				errorMsg = "跨行收单接口调用成功";
			} else {
				errorCode = "02";
				errorMsg = "跨行收单接口调用失败";
			}
		} catch (Exception e) {
			try {
				if ("".equals((String) output.getDataValue("errorCode"))) {
					errorCode = "01";
					errorMsg = "跨行收单接口调用成功";
				} else {
					errorCode = "02";
					errorMsg = "跨行收单接口调用失败";
				}
			} catch (Exception e2) {
				logger.error(e2.toString());
			}
		}

		YydWalletKhsd ywkh = walletKhsdDao.getByPayTranFunc(paymentNo, tranFunc);

		ywkh.setCreateTime(Calendar.getInstance());
		ywkh.setPaymentNo(paymentNo);
		ywkh.setTranFunc(tranFunc);
		ywkh.setRecvParames(output.toJSon());
		ywkh.setRspCode(errorCode);
		ywkh.setRspMsg(errorMsg);

		walletKhsdDao.save(ywkh);
	}

	public void saveWalletKhsd(String paymentNo, String tranFunc, KeyedCollection input, KeyedCollection output) {
		String errorCode = "";// 银行返回的应答码
		String errorMsg = "";// 银行返回的应答描述
		try {
			if ("01".equals((String) output.getDataValue("status"))) {
				errorCode = "01";
				errorMsg = "跨行收单接口调用成功";
			} else {
				errorCode = "02";
				errorMsg = "跨行收单接口调用失败";
			}
		} catch (Exception e) {
			try {
				if ("".equals((String) output.getDataValue("errorCode"))) {
					errorCode = "01";
					errorMsg = "跨行收单接口调用成功";
				} else {
					errorCode = "02";
					errorMsg = "跨行收单接口调用失败";
				}
			} catch (Exception e2) {
				logger.error(e2.toString());
			}
		}

		YydWalletKhsd ywkh = walletKhsdDao.getByPayTranFunc(paymentNo, tranFunc);
		if (ywkh == null)
			ywkh = new YydWalletKhsd();

		ywkh.setCreateTime(Calendar.getInstance());
		ywkh.setPaymentNo(paymentNo);
		ywkh.setTranFunc(tranFunc);
		ywkh.setSendParames(input.toJSon());
		ywkh.setRecvParames(output.toJSon());
		ywkh.setRspCode(errorCode);
		ywkh.setRspMsg(errorMsg);

		walletKhsdDao.save(ywkh);
	}

	public int deleteByPaymentNo(String paymentNo) {
		return walletKhsdDao.batchExecute("delete from YydWalletKhsd where paymentNo = ?", new Object[] { paymentNo });
	}

	public WalletKhsdData getWalletKhsdData(YydWalletKhsd khsd) {
		if (khsd != null) {
			WalletKhsdData data = new WalletKhsdData();
			CopyUtil.copyProperties(data, khsd);

			data.setCreateTime(CalendarUtil.toYYYYMMDDHHmmss(khsd.getCreateTime()));

			return data;
		}
		return null;
	}

}
