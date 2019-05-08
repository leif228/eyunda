package com.hangyi.eyunda.service.hyquan.wallet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.HyqUserAccountDao;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.data.hyquan.HyqAccountData;
import com.hangyi.eyunda.domain.HyqUserAccount;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.DESHelper;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class HyqAccountService extends BaseService<HyqUserAccount, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqUserAccountDao accountDao;

	@Override
	public PageHibernateDao<HyqUserAccount, Long> getDao() {
		return (PageHibernateDao<HyqUserAccount, Long>) accountDao;
	}

	public HyqAccountData getAccountData(HyqUserAccount yydUserAccount) {
		if (yydUserAccount != null) {
			HyqAccountData accountData = new HyqAccountData();

			CopyUtil.copyProperties(accountData, yydUserAccount);
			accountData.setAccountNo(
					DESHelper.DoDES(yydUserAccount.getAccountNo(), Constants.SALT_VALUE, DESHelper.DECRYPT_MODE));

			if (yydUserAccount.getIdCode() != null && !"".equals(yydUserAccount.getIdCode()))
				accountData.setIdCode(
						DESHelper.DoDES(yydUserAccount.getIdCode(), Constants.SALT_VALUE, DESHelper.DECRYPT_MODE));
			if (yydUserAccount.getMobile() != null && !"".equals(yydUserAccount.getMobile()))
				accountData.setMobile(
						DESHelper.DoDES(yydUserAccount.getMobile(), Constants.SALT_VALUE, DESHelper.DECRYPT_MODE));
			if (yydUserAccount.getPayPassWord() == null)
				accountData.setPayPassWord(YesNoCode.no);

			return accountData;
		} else
			return null;
	}

	public HyqAccountData getAccountByUserId(Long userId, PayStyleCode thirdParty) {
		HyqUserAccount userAccount = accountDao.getAccountByUserId(userId, thirdParty);
		return this.getAccountData(userAccount);
	}

	public List<HyqAccountData> getAccountsLikeLoginName(String loginName, PayStyleCode thirdParty) {
		List<HyqUserAccount> userAccounts = accountDao.getAccountsLikeLoginName(loginName, thirdParty);

		List<HyqAccountData> datas = new ArrayList<HyqAccountData>();
		for (HyqUserAccount userAccount : userAccounts)
			datas.add(this.getAccountData(userAccount));

		return datas;
	}

	public boolean saveAccount(HyqAccountData accountData) {
		try {
			HyqUserAccount account = accountDao.get(accountData.getId());
			if (account == null)
				account = new HyqUserAccount();

			account.setUserId(accountData.getUserId());
			account.setAccounter(accountData.getAccounter());
			account.setPayStyle(accountData.getPayStyle());
			account.setAccountNo(
					DESHelper.DoDES(accountData.getAccountNo(), Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE));

			if (!"".equals(accountData.getIdCode()))
				account.setIdCode(
						DESHelper.DoDES(accountData.getIdCode(), Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE));
			else
				account.setIdCode("");

			if (!"".equals(accountData.getMobile()))
				account.setMobile(
						DESHelper.DoDES(accountData.getMobile(), Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE));
			else
				account.setMobile("");

			account.setPayPassWord(accountData.getPayPassWord());

			accountDao.save(account);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
