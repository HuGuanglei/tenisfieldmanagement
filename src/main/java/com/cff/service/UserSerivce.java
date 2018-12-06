package com.cff.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cff.common.Constant;
import com.cff.dao.UserDao;
import com.cff.dao.wallet.AccountDao;
import com.cff.dao.wallet.AccountTypeDao;
import com.cff.domain.User;
import com.cff.domain.wallet.Account;
import com.cff.domain.wallet.AccountType;
import com.cff.dto.RetCode;
import com.cff.util.DateUtil;

@Service
public class UserSerivce {
	@Autowired
	UserDao userDao;
	@Autowired
	AccountDao accountDao;
	@Autowired
	AccountTypeDao accountTypeDao;

	@Transactional
	public RetCode addUser(User user) {
		RetCode retCode = new RetCode();

		userDao.save(user);
		retCode.setErrorCode("200");
		retCode.setErrorMsg("用户新增成功！");

		AccountType accountType = accountTypeDao.findByAccType(Constant.USER_ACCT_TYPE);
		Account account = new Account();
		account.setAccType(accountType.getAccType());
		account.setBal(accountType.getInitAmt());
		account.setLockFlag(Constant.ACCT_lOCK_OK);
		account.setStatus(Constant.ACCT_OK);
		account.setOpenTime(DateUtil.format(new Date(), DateUtil.FullDatePatternNoSymbol));
		account.setUserName(user.getUserName());
		accountDao.save(account);
		retCode.setErrorMsg("用户新增成功并已开通账户！");

		return retCode;
	}
}
