package com.cff.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cff.common.Constant;
import com.cff.dao.ScourtDao;
import com.cff.dao.ScourtInfoDao;
import com.cff.dao.UserDao;
import com.cff.dao.wallet.AccountDao;
import com.cff.dao.wallet.AccountTransFlowDao;
import com.cff.dao.wallet.AccountTypeDao;
import com.cff.domain.Scourt;
import com.cff.domain.ScourtInfo;
import com.cff.domain.User;
import com.cff.domain.wallet.Account;
import com.cff.domain.wallet.AccountTransflow;
import com.cff.domain.wallet.AccountType;
import com.cff.dto.RetCode;
import com.cff.service.LiqManagerService;
import com.cff.service.UserSerivce;
import com.cff.util.DateUtil;

@RestController
@RequestMapping("/admin")
public class AdminRest {

	@Autowired
	ScourtDao scourtDao;
	
	@Autowired
	ScourtInfoDao scourtInfoDao;

	@Autowired
	UserDao userDao;
	
	@Autowired
	AccountTypeDao accountTypeDao;
	
	@Autowired
	AccountDao accountDao;
	
	@Autowired
	AccountTransFlowDao accountTransFlowDao;
		
	@Autowired
	LiqManagerService liqManagerService;
		
	@Autowired
	UserSerivce userSerivce;

	@RequestMapping(value = "/bookList", method = RequestMethod.POST, produces = "application/json")
	public List<Scourt> bookList(@RequestParam String bookTime) {
		List<Scourt> list = null;
		if (bookTime == null || bookTime.trim().length() < 1) {
			list = (List<Scourt>) scourtDao.findAll();	
		} else {		
			list = scourtDao.findByBdate(bookTime);
		}
		for(Scourt scourt : list){
			scourt.setStartTime(scourt.getBdate() + " " +  scourt.getStartHour());
			scourt.setEndTime(scourt.getBdate() + " " +  scourt.getEndHour());
		}
		return list;
	}
	
	@RequestMapping(value = "/addCourt", method = RequestMethod.POST, produces = "application/json")
	public RetCode addCourt(@RequestBody ScourtInfo scourtInfo) {
		RetCode retCode = new RetCode();
		if(scourtInfo == null){
			retCode.setErrorCode("400");
			retCode.setErrorMsg("数据为空！");
			return retCode;
		}
		try {
			scourtInfo.setCreateDate(DateUtil.format(new Date(), DateUtil.SimpleDatePattern));
			scourtInfoDao.save(scourtInfo);
			retCode.setErrorCode("200");
			retCode.setErrorMsg("场地添加成功！");
		} catch (Exception e) {
			retCode.setErrorCode("500");
			retCode.setErrorMsg("场地添加失败！");
		}

		return retCode;
	}

	@RequestMapping(value = "/deleteBook", method = RequestMethod.POST, produces = "application/json")
	public RetCode deleteBook(@RequestParam int id) {
		RetCode retCode = new RetCode();
		try {
			scourtDao.delete(id);
			retCode.setErrorCode("200");
			retCode.setErrorMsg("删除成功！");
		} catch (Exception e) {
			retCode.setErrorCode("500");
			retCode.setErrorMsg("删除失败！");
		}

		return retCode;
	}
	
	@RequestMapping(value = "/deleteCourt", method = RequestMethod.POST, produces = "application/json")
	public RetCode deleteCourt(@RequestParam int courtId) {
		RetCode retCode = new RetCode();
		try {
			scourtInfoDao.delete(courtId);
			retCode.setErrorCode("200");
			retCode.setErrorMsg("删除成功！");
		} catch (Exception e) {
			retCode.setErrorCode("500");
			retCode.setErrorMsg("删除失败！");
		}

		return retCode;
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST, produces = "application/json")
	public List<User> users(@RequestParam String userName) {
		List<User> list = null;
		if (userName == null || userName.trim().length() < 1) {
			list = (List<User>) userDao.findAll();
		} else {
			list = new ArrayList<>();
			list.add(userDao.findByUserName(userName));
		}
		return list;
	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST, produces = "application/json")
	public RetCode deleteUser(@RequestParam String userName) {
		RetCode retCode = new RetCode();
		try {
			userDao.delete(userName);
			retCode.setErrorCode("200");
			retCode.setErrorMsg("删除成功！");
		} catch (Exception e) {
			retCode.setErrorCode("500");
			retCode.setErrorMsg("删除失败！");
		}

		return retCode;
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json")
	public RetCode addUser(@RequestBody User user) {
		RetCode retCode = new RetCode();
		try {
			if (user.getUserName() == null || user.getUserName().trim().length() < 1 || user.getPasswd() == null
					|| user.getPasswd().trim().length() < 1){
				retCode.setErrorCode("300");
				retCode.setErrorMsg("用户名或密码不能为空！");
				return retCode;
			}
			User tblUser = userDao.findByUserName(user.getUserName());
			if(tblUser != null){
				retCode.setErrorCode("400");
				retCode.setErrorMsg("该用户已存在！");
				return retCode;
			}
			
			user.setRole("0");
			retCode = userSerivce.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			retCode.setErrorCode("500");
			retCode.setErrorMsg("用户新增失败！");
		}

		return retCode;
	}
	
	@RequestMapping(value = "/checkAccount", method = RequestMethod.POST, produces = "application/json")
	public RetCode checkAccount(@RequestParam String userName) {
		RetCode retCode = new RetCode();
		if (userName == null || userName.trim().length() < 1) {
			retCode.setErrorCode("401");
			retCode.setErrorMsg("未授权操作！");
			return retCode;
		}
		Account account = accountDao.findByUserName(userName);
		if(account == null){
			retCode.setErrorCode("300");
			retCode.setErrorMsg("查无账户信息！");
			return retCode;
		}
		
		if(account.getBal() > 0){
			retCode.setErrorCode("400");
			retCode.setErrorMsg("账户仍有余额,确定要销户么？");
			return retCode;
		}
		account.setStatus(Constant.ACCT_CLOSE);//销户
		account.setCloseTime(DateUtil.format(new Date(), DateUtil.FullDatePatternNoSymbol));
		accountDao.save(account);
		retCode.setErrorCode("200");
		retCode.setErrorMsg("销户成功！");
		
		return retCode;
	}
	
	@RequestMapping(value = "/closeAccount", method = RequestMethod.POST, produces = "application/json")
	public RetCode closeAccount(@RequestParam String userName) {
		RetCode retCode = new RetCode();
		if (userName == null || userName.trim().length() < 1) {
			retCode.setErrorCode("404");
			retCode.setErrorMsg("用户名为空！！");
			return retCode;
		}
		Account account = accountDao.findByUserName(userName);
		if(account == null){
			retCode.setErrorCode("300");
			retCode.setErrorMsg("查无账户信息！");
			return retCode;
		}
		
		if(account.getBal() > 0){
			AccountTransflow accountTransflow = new AccountTransflow();
			accountTransflow.setUserName(userName);
			accountTransflow.setTransType(Constant.TRANS_TYPE_CLOSE);
			accountTransflow.setTransAmt(account.getBal());
			accountTransflow.setRemark("销户");
			accountTransflow.setTransDate(DateUtil.format(new Date(), DateUtil.SimpleDatePattern));
			accountTransflow.setTransTime(DateUtil.format(new Date(), DateUtil.FullTimePatternNoSymbol));
			accountTransflow.setFlag(Constant.TRANS_FLOW_FALG_OK);
			AccountTransflow flow = accountTransFlowDao.save(accountTransflow);
			
			retCode = liqManagerService.liq(flow);
		}
		
		account.setStatus(Constant.ACCT_CLOSE);//销户
		account.setCloseTime(DateUtil.format(new Date(), DateUtil.FullDatePatternNoSymbol));
		accountDao.save(account);
		retCode.setErrorCode("200");
		retCode.setErrorMsg("销户成功！");
		return retCode;
	}
	
	@RequestMapping(value = "/openAccount", method = RequestMethod.POST, produces = "application/json")
	public RetCode openAccount(@RequestParam String userName) {
		RetCode retCode = new RetCode();
		if (userName == null || userName.trim().length() < 1) {
			retCode.setErrorCode("404");
			retCode.setErrorMsg("用户名为空！！");
			return retCode;
		}
		Account account = accountDao.findByUserName(userName);
		if(account == null){
			account = new Account();
			AccountType accountType = accountTypeDao.findByAccType(Constant.USER_ACCT_TYPE);
			account.setAccType(accountType.getAccType());
			account.setBal(accountType.getInitAmt());
			account.setLockFlag(Constant.ACCT_lOCK_OK);
			account.setStatus(Constant.ACCT_OK);
			account.setOpenTime(DateUtil.format(new Date(), DateUtil.FullDatePatternNoSymbol));
			account.setUserName(userName);
		}else{
			account.setStatus(Constant.ACCT_OK);//开户
		}
		accountDao.save(account);
		retCode.setErrorCode("200");
		retCode.setErrorMsg("开户成功！");
		return retCode;
	}
	
	@RequestMapping(value = "/accountInfo", method = RequestMethod.POST, produces = "application/json")
	public Account accountInfo(@RequestParam String userName) {
		if (userName == null || userName.trim().length() < 1) {
			return null;
		}
		Account account = accountDao.findByUserName(userName);
		return account;
	}
	
	@RequestMapping(value = "/consumeStatistics", method = RequestMethod.POST, produces = "application/json")
	public RetCode consumeStatistics() {
		RetCode retCode = new RetCode();
		List<Account> accountLoan = accountDao.findByAccType(Constant.SYS_LOAN_ACCT_TYPE);
		List<Account> accountLend = accountDao.findByAccType(Constant.SYS_LEND_ACCT_TYPE);
		AccountType accountTypeLoan = accountTypeDao.findByAccType(Constant.SYS_LOAN_ACCT_TYPE);
		AccountType accountTypeLend = accountTypeDao.findByAccType(Constant.SYS_LEND_ACCT_TYPE);
		
		int allDeposit = 0;
		int allIncome = 0;
		for(Account item : accountLoan){
			allDeposit += accountTypeLoan.getInitAmt() - item.getBal();
		}
		for(Account item : accountLend){
			allIncome += item.getBal() - accountTypeLend.getInitAmt();
		}
		retCode.setErrorCode(String.valueOf(allDeposit));
		retCode.setErrorMsg(String.valueOf(allIncome));
		return retCode;
	}
	
	@RequestMapping(value = "/chargeConsume", method = RequestMethod.POST, produces = "application/json")
	public RetCode chargeConsume(@RequestBody AccountTransflow accountTransflow) {
		RetCode retCode = new RetCode();
		String userName = accountTransflow.getUserName();
		User user = userDao.findByUserName(userName);
		if(user == null){
			retCode.setErrorCode("404");
			retCode.setErrorMsg("用户不存在！");
			return retCode;
		}
		Account account = accountDao.findByUserName(userName);
		if(account == null){
			retCode.setErrorCode("405");
			retCode.setErrorMsg("账户不存在！");
			return retCode;
		}
		if(!account.getStatus().equals(Constant.ACCT_OK)){
			retCode.setErrorCode("505");
			retCode.setErrorMsg("账户已销户！");
			return retCode;
		}
		
		accountTransflow.setTransDate(DateUtil.format(new Date(), DateUtil.SimpleDatePattern));
		accountTransflow.setTransTime(DateUtil.format(new Date(), DateUtil.FullTimePatternNoSymbol));
		accountTransflow.setFlag(Constant.TRANS_FLOW_FALG_OK);
		AccountTransflow flow = accountTransFlowDao.save(accountTransflow);
		
		retCode = liqManagerService.liq(flow);
		
		return retCode;
	}
	
	@RequestMapping(value = "/consumeConsume", method = RequestMethod.POST, produces = "application/json")
	public RetCode consumeConsume(@RequestBody AccountTransflow accountTransflow) {
		RetCode retCode = new RetCode();
		String userName = accountTransflow.getUserName();
		User user = userDao.findByUserName(userName);
		if(user == null){
			retCode.setErrorCode("404");
			retCode.setErrorMsg("用户不存在！");
			return retCode;
		}
		Account account = accountDao.findByUserName(userName);
		if(account == null){
			retCode.setErrorCode("405");
			retCode.setErrorMsg("账户不存在！");
			return retCode;
		}
		if(!account.getStatus().equals(Constant.ACCT_OK)){
			retCode.setErrorCode("505");
			retCode.setErrorMsg("账户已销户！");
			return retCode;
		}
		if(account.getBal() < accountTransflow.getTransAmt()){
			retCode.setErrorCode("600");
			retCode.setErrorMsg("账户余额不足！");
			return retCode;
		}
		
		accountTransflow.setTransDate(DateUtil.format(new Date(), DateUtil.SimpleDatePattern));
		accountTransflow.setTransTime(DateUtil.format(new Date(), DateUtil.FullTimePatternNoSymbol));
		accountTransflow.setFlag(Constant.TRANS_FLOW_FALG_OK);
		AccountTransflow flow = accountTransFlowDao.save(accountTransflow);
		
		retCode = liqManagerService.liq(flow);
		
		return retCode;
	}
	
	@RequestMapping(value = "/accountDetail", method = RequestMethod.POST, produces = "application/json")
	public List<AccountTransflow> accountDetail(@RequestParam String transDate ) {
		if(transDate == null || transDate.trim().length() < 1){
			transDate = DateUtil.format(new Date(), DateUtil.SimpleDatePattern);
		}
		List<AccountTransflow> accounts = accountTransFlowDao.findByTransDate(transDate);
		return accounts;
		
	}
	
	@RequestMapping(value = "/consumeRevoc", method = RequestMethod.POST, produces = "application/json")
	public RetCode consumeRevoc(@RequestParam int id ) {
		RetCode retCode = new RetCode();
		AccountTransflow accountTransflow = accountTransFlowDao.findOne(id);
		if(accountTransflow.getFlag().equals(Constant.TRANS_FLOW_FALG_CANCEL)){
			retCode.setErrorCode("300");
			retCode.setErrorMsg("该交易已撤销！");
			return retCode;
		}
		accountTransflow.setTransType(Constant.TRANS_TYPE_CANCEL);
		retCode = liqManagerService.liq(accountTransflow);
		return retCode;		
	}
}
