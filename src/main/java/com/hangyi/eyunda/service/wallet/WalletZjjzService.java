package com.hangyi.eyunda.service.wallet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydWalletZjjzDao;
import com.hangyi.eyunda.data.wallet.WalletZjjzData;
import com.hangyi.eyunda.domain.YydWalletZjjz;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class WalletZjjzService extends BaseService<YydWalletZjjz, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydWalletZjjzDao walletZjjzDao;

	@Override
	public PageHibernateDao<YydWalletZjjz, Long> getDao() {
		return (PageHibernateDao<YydWalletZjjz, Long>) walletZjjzDao;
	}

	public List<WalletZjjzData> findFetchZjjz(String startDate, String endDate) {
		List<WalletZjjzData> datas = new ArrayList<WalletZjjzData>();
		List<YydWalletZjjz> ywzs = walletZjjzDao.findFetchZjjz(startDate, endDate);
		for (YydWalletZjjz ywz : ywzs) {
			WalletZjjzData data = this.getWalletZjjzData(ywz);
			datas.add(data);
		}
		return datas;
	}

	public WalletZjjzData getByLogNo(String logNo) {
		YydWalletZjjz zjjz = walletZjjzDao.getByLogNo(logNo);
		WalletZjjzData data = this.getWalletZjjzData(zjjz);

		return data;
	}

	public List<WalletZjjzData> findByPaymentNo(String paymentNo) {
		List<WalletZjjzData> datas = new ArrayList<WalletZjjzData>();

		List<YydWalletZjjz> zjjzs = walletZjjzDao.findByPaymentNo(paymentNo);
		for (YydWalletZjjz zjjz : zjjzs) {
			WalletZjjzData data = this.getWalletZjjzData(zjjz);
			datas.add(data);
		}

		return datas;
	}

	public void saveWalletZjjz(String paymentNo, Map<String, String> sendParamesMap,
			Map<String, String> recvParamesMap) {

		String logNo = (String) sendParamesMap.get("ThirdLogNo");// 请求流水号
		String tranFunc = (String) sendParamesMap.get("TranFunc"); // 功能号4位

		String rspCode = "";// 银行返回的应答码
		String rspMsg = "";// 银行返回的应答描述
		try {
			rspCode = (String) recvParamesMap.get("RspCode");
			rspMsg = (String) recvParamesMap.get("RspMsg");
		} catch (Exception e) {
			logger.error(e.toString());
		}

		Integer funcFlag = 0;
		try {
			String strFuncFlag = (String) sendParamesMap.get("FuncFlag"); // 功能标志
			if (strFuncFlag == null || "".equals(strFuncFlag))
				strFuncFlag = (String) sendParamesMap.get("SelectFlag"); // 功能标志

			funcFlag = Integer.parseInt(strFuncFlag);
		} catch (Exception e) {
			funcFlag = 0;
		}

		YydWalletZjjz ywz = walletZjjzDao.getByPayTranFunc(paymentNo, tranFunc, funcFlag.toString());

		if (ywz == null) {
			ywz = new YydWalletZjjz();
		} else {
			// 已经处理成功，再处理是多余，肯定失败不再记录
			// if ("000000".equals(ywz.getRspCode())) {
			// return;
			// }
		}

		ywz.setCreateTime(Calendar.getInstance());
		ywz.setPaymentNo(paymentNo);
		ywz.setLogNo(logNo);
		ywz.setTranFunc(tranFunc);
		ywz.setFuncFlag(funcFlag.toString());
		ywz.setSendParames(new Gson().toJson(sendParamesMap));
		ywz.setRecvParames(new Gson().toJson(recvParamesMap));
		ywz.setRspCode(rspCode);
		ywz.setRspMsg(rspMsg);

		walletZjjzDao.save(ywz);
	}

	public WalletZjjzData getWalletZjjzData(YydWalletZjjz zjjz) {
		if (zjjz != null) {
			WalletZjjzData data = new WalletZjjzData();
			CopyUtil.copyProperties(data, zjjz);

			data.setCreateTime(CalendarUtil.toYYYYMMDDHHmmss(zjjz.getCreateTime()));

			return data;
		}
		return null;
	}

	public void updateWalletZjjz(String ulogNo) {
		this.batchExecute("update YydWalletZjjz set rspCode='000000' where logNo=?", new Object[] { ulogNo });
	}

}
