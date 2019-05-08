package com.hangyi.eyunda.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.YydSequenceDao;
import com.hangyi.eyunda.domain.YydSequence;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
public class PaySequenceService {

	private static final Map<String, Object> Ids = new ConcurrentHashMap<String, Object>();
	private static final long INIT_PAY_NO = 10000000;
	private static final long INIT_LOG_NO = 100000;
	private static final long MAX_CAPACITY = 100;

	@Autowired
	private YydSequenceDao yydSequenceDao;

	class IdPair {
		public long curVal;
		public long maxVal;

		public IdPair() {
			curVal = 0;
			maxVal = 0;
		}
	}

	/**
	 * 生成序列号. 逻辑:通过参数name判断数据库表中是否存在，如果不存在则新建，存在则取出来然后记录到一个Map里面缓存起来，然后将数值+100
	 * 后保存回数据库和设置MaxVal到缓存中。当需要取值的时候再缓存的 Map里面取出值+1，如果值已经到了MAXval的值，则再从数据库中取出来。
	 */
	private synchronized long getNo(String sequenceName, long initNo) {
		long idForThisTime;

		IdPair id = (IdPair) Ids.get(sequenceName);
		if (id == null) {
			id = new IdPair();
			Ids.put(sequenceName, id);
		}

		if (id.curVal < id.maxVal) {
			idForThisTime = id.curVal++;
			return (long) idForThisTime;
		}

		YydSequence yydSequence = yydSequenceDao.getByName(sequenceName);
		if (yydSequence == null) {
			yydSequence = new YydSequence();
			yydSequence.setSequenceName(sequenceName);
			yydSequence.setSequenceNo(initNo);

			yydSequenceDao.save(yydSequence);
		}

		long minId = yydSequence.getSequenceNo();

		yydSequence.setSequenceNo(minId + MAX_CAPACITY);
		yydSequenceDao.save(yydSequence);

		id.curVal = minId;
		id.maxVal = minId + MAX_CAPACITY;
		idForThisTime = id.curVal++;

		return idForThisTime;
	}

	/** 获取支付序列号：客户号10位＋日期8位＋序列号8位 */
	public String getPaymentNo() {
		String customerNo = Constants.CUSTOMER_NUM;// 客户号10位
		String strToday = CalendarUtil.toYYYYMMDD(CalendarUtil.now());// 日期8位
		String serial = Long.toString(this.getNo("payment", INIT_PAY_NO));// 序列号8位

		String paymentNo = customerNo + strToday + serial;

		return paymentNo;
	}

	/** 接口调用序列号：日期14位YYYYMMDDhhmmss＋序列号6位 */
	public String getLogNo() {
		String strTime = CalendarUtil.toYYYYMMDDHHmmss(CalendarUtil.now());// 日期14位
		String serial = Long.toString(this.getNo("log", INIT_LOG_NO));// 序列号6位
		if (serial.length() > 6) // 防止一天使用超过90万个序列号
			serial = serial.substring(0, 6);

		String logNo = strTime + serial;

		return logNo;
	}

	/** 获取支付序列号：客户号10位＋日期8位＋序列号8位 */
	public String getAccountNo() {
		String strToday = CalendarUtil.toYYYYMMDD(CalendarUtil.now());// 日期8位
		String serial = Long.toString(this.getNo("account", INIT_PAY_NO));// 序列号8位

		String paymentNo = strToday + serial;

		return paymentNo;
	}

}
