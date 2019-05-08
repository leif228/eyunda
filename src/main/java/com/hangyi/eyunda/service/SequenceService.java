package com.hangyi.eyunda.service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.YydSequenceDao;
import com.hangyi.eyunda.domain.YydSequence;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
public class SequenceService {

	private static final Map<String, Object> Ids = new ConcurrentHashMap<String, Object>();
	private static final long INIT_VALUE = 12345678L;
	private static final int MAX_RANDOM = 1000000;
	private static final int MAX_CAPACITY = 10000000;
	private Random rl = new Random();

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

	public synchronized long getNo(String sequenceName) {
		// 先从缓存中取
		IdPair id = (IdPair) Ids.get(sequenceName);
		if (id == null) {
			id = new IdPair();
			Ids.put(sequenceName, id);
		}

		// 取到则返回
		id.curVal = id.curVal + rl.nextInt(MAX_RANDOM);
		if (id.curVal < id.maxVal) {
			return id.curVal;
		}

		// 再从数据库中取
		YydSequence yydSequence = yydSequenceDao.getByName(sequenceName);
		if (yydSequence == null) {
			yydSequence = new YydSequence();
			yydSequence.setSequenceName(sequenceName);
			yydSequence.setSequenceNo(INIT_VALUE);
		}

		// 取到则修改数据库及缓存
		long maxId = yydSequence.getSequenceNo();

		yydSequence.setSequenceNo(maxId + MAX_CAPACITY);
		yydSequenceDao.save(yydSequence);

		id.curVal = maxId;
		id.maxVal = maxId + MAX_CAPACITY;

		id.curVal = id.curVal + rl.nextInt(MAX_RANDOM);
		return id.curVal;
	}

	/** 获取支付批号 */
	public long getBatchNo() {
		return this.getNo("batchNo");
	}

	/** 获取支付流水号 */
	public long getPaymentNo() {
		return this.getNo("paymentNo");
	}
}
