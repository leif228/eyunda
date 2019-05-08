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

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.YydUserBankCardDao;
import com.hangyi.eyunda.data.wallet.UserBankData;
import com.hangyi.eyunda.domain.YydUserBankCard;
import com.hangyi.eyunda.domain.enumeric.BankCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.DESHelper;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class UserBankCardService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydUserBankCardDao userBankCardDao;

	// 根据ID取得绑定银行卡
	public UserBankData getBindBankData(Long id) {
		return this.getBindBankData(userBankCardDao.get(id));
	}

	// 根据ID取得绑定银行卡
	public UserBankData getBindBankData(YydUserBankCard userBankCard) {
		UserBankData bankData = null;
		try {
			if (userBankCard != null) {
				bankData = new UserBankData();
				CopyUtil.copyProperties(bankData, userBankCard);
				
				bankData.setCardNo(DESHelper.DoDES(bankData.getCardNo(), Constants.SALT_VALUE,
						DESHelper.DECRYPT_MODE));
				
				String bindTime = CalendarUtil.toYYYY_MM_DD(userBankCard.getBindTime());
				bankData.setBindTime(bindTime);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return bankData;
	}

	// 1 根据用户ID、银行帐号、银行名称取得绑定银行卡
	public UserBankData getBindBankData(Long userId, BankCode bankCode, String accountName, String cardNo) {
		UserBankData bankData = null;
		try {
			YydUserBankCard userBankCard = userBankCardDao.findBy(userId, bankCode, accountName, cardNo);
			bankData = this.getBindBankData(userBankCard);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return bankData;
	}

	// 2 绑定的银行卡列表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page getBankDatas(Long userId, int pageNo) {
		List<UserBankData> bankDatas = new ArrayList<UserBankData>();
		Page page = userBankCardDao.findBankDatas(userId, pageNo);
		try {
			for (YydUserBankCard userBankCard : (List<YydUserBankCard>) page.getResult()) {
				UserBankData bankData = this.getBindBankData(userBankCard);
				if (bankData != null)
					bankDatas.add(bankData);
			}
			page.setResult(bankDatas);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return page;
	}

	// 3 本地数据库绑定银行卡
	public boolean localBindCard(Long userId, UserBankData bankData) {
		try {
			YydUserBankCard userBankCard = userBankCardDao.findBy(userId, bankData.getBankCode(),
					bankData.getAccountName(), bankData.getCardNo());
			if (userBankCard == null) {
				userBankCard = new YydUserBankCard();
				userBankCard.setUserId(userId);
				userBankCard.setAccountName(bankData.getAccountName());
				userBankCard.setCardNo(DESHelper.DoDES(bankData.getCardNo(), Constants.SALT_VALUE,
						DESHelper.ENCRYPT_MODE));
				userBankCard.setBankCode(bankData.getBankCode());
				userBankCard.setBindTime(Calendar.getInstance());

				userBankCardDao.save(userBankCard);
				return true;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}

	// 4 绑定的银行卡列表
	public List<UserBankData> getBankDatas(Long userId) {
		List<UserBankData> bankDatas = new ArrayList<UserBankData>();
		try {
			List<YydUserBankCard> userBankCards = userBankCardDao.findBankDatas(userId);
			for (YydUserBankCard userBankCard : userBankCards) {
				UserBankData bankData = this.getBindBankData(userBankCard);
				if (bankData != null)
					bankDatas.add(bankData);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return bankDatas;
	}

	// 4 本地数据库取消绑定银行卡
	public boolean localUnBindCard(Long bankCardId) {
		try {
			YydUserBankCard userBankCard = userBankCardDao.get(bankCardId);
			if (userBankCard != null) {
				userBankCardDao.delete(userBankCard);
				return true;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}

	public UserBankData findBankCard(Long userId) {
		YydUserBankCard userBankCard = userBankCardDao.findRcvBank(userId);
		if(userBankCard != null){
			UserBankData bankData = this.getBindBankData(userBankCard);
			return bankData;
		}
		
		return null;
	}
	
	public void setRcvCard(Long cardId, YesNoCode yesNoCode) throws Exception {
		YydUserBankCard userBankCard = userBankCardDao.get(cardId);
		if(userBankCard != null){
			userBankCard.setIsRcvCard(yesNoCode);
			userBankCardDao.save(userBankCard);
		} else {
			throw new Exception("银行卡不存在！");
		}
	}

}
