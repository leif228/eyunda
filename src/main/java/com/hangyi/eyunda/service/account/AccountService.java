package com.hangyi.eyunda.service.account;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydUserAccountDao;
import com.hangyi.eyunda.data.account.AccountData;
import com.hangyi.eyunda.domain.YydUserAccount;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.DESHelper;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class AccountService extends BaseService<YydUserAccount, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydUserAccountDao accountDao;

	@Override
	public PageHibernateDao<YydUserAccount, Long> getDao() {
		return (PageHibernateDao<YydUserAccount, Long>) accountDao;
	}

	public AccountData getAccountData(YydUserAccount yydUserAccount) {
		if (yydUserAccount != null) {
			AccountData accountData = new AccountData();

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

	public String getAccountNoByIdCode(String idCode, String accounter) {
		String accountNo = accountDao.getAccountNoByIdCode(idCode, accounter);
		return accountNo;
	}

	public AccountData getAccountByUserId(Long userId, PayStyleCode thirdParty) {
		YydUserAccount userAccount = accountDao.getAccountByUserId(userId, thirdParty);
		return this.getAccountData(userAccount);
	}

	public List<AccountData> getAccountsLikeLoginName(String loginName, PayStyleCode thirdParty) {
		List<YydUserAccount> userAccounts = accountDao.getAccountsLikeLoginName(loginName, thirdParty);

		List<AccountData> datas = new ArrayList<AccountData>();
		for (YydUserAccount userAccount : userAccounts)
			datas.add(this.getAccountData(userAccount));

		return datas;
	}

	public boolean saveAccount(AccountData accountData) {
		try {
			YydUserAccount account = accountDao.get(accountData.getId());
			if (account == null)
				account = new YydUserAccount();

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
