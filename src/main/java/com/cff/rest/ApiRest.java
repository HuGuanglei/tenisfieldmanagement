package com.cff.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.cff.dao.wallet.AccountFlowDao;
import com.cff.dao.wallet.AccountTransFlowDao;
import com.cff.domain.Scourt;
import com.cff.domain.ScourtInfo;
import com.cff.domain.User;
import com.cff.domain.wallet.Account;
import com.cff.domain.wallet.AccountFlow;
import com.cff.domain.wallet.AccountTransflow;
import com.cff.dto.RetCode;
import com.cff.service.LiqManagerService;
import com.cff.util.DateUtil;

@RestController
@RequestMapping("/api")
public class ApiRest {
	private final static Logger logger = LoggerFactory.getLogger(ApiRest.class);

	@Autowired
	ScourtDao scourtDao;

	@Autowired
	ScourtInfoDao scourtInfoDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AccountTransFlowDao accountTransFlowDao;
	
	@Autowired
	AccountDao accountDao;
	
	@Autowired
	LiqManagerService liqManagerService;

	@RequestMapping(value = "/book", method = RequestMethod.POST, produces = "application/json")
	public RetCode book(@RequestBody Scourt scourt) {
		RetCode retCode = new RetCode();
		if (scourt == null) {
			retCode.setErrorCode("400");
			retCode.setErrorMsg("数据为空！");
			return retCode;
		}
		try {
			String startTime = scourt.getStartTime();
			String endTime = scourt.getEndTime();
			Date startDate = DateUtil.parse(startTime, DateUtil.FullDateHourPattern);
			Date endDate = DateUtil.parse(endTime, DateUtil.FullDateHourPattern);
			if (!DateUtil.isSameDay(startDate, endDate)) {
				retCode.setErrorCode("300");
				retCode.setErrorMsg("只能是同一天的预订！");
				return retCode;
			}

			String courtName = scourt.getCourtName();
			int courtId = Integer.parseInt(scourt.getCourtId());
			if (courtId != -1) {
				ScourtInfo scourtInfo = scourtInfoDao.findByCourtIdAndCourtName(courtId, courtName);
				if (scourtInfo == null) {
					retCode.setErrorCode("600");
					retCode.setErrorMsg("该场地不存在！");
					return retCode;
				}
			}

			scourt.setCreateTime(DateUtil.format(new Date(), DateUtil.FullDatePatternNoSymbol));
			scourt.setBdate(DateUtil.format(startDate, DateUtil.SimpleDatePattern));
			scourt.setStartHour(startDate.getHours());
			scourt.setEndHour(endDate.getHours());

			List<Scourt> tmp = scourtDao.queryExistOrNot(scourt.getStartHour(), scourt.getEndHour(), scourt.getBdate());
			if (courtId == -1) {
				List<ScourtInfo> allScourt = (List<ScourtInfo>) scourtInfoDao.findAll();
				if (allScourt == null || allScourt.size() < 1) {
					retCode.setErrorCode("800");
					retCode.setErrorMsg("暂时还没有场地开放！");
					return retCode;
				}

				ScourtInfo dst = allScourt.get(0);
				if (tmp != null && tmp.size() > 0) {
					dst = null;
					Map<String, Boolean> existItem = new HashMap<>();
					for (Scourt initem : tmp) {
						existItem.put(initem.getCourtName(), true);
					}
					for (ScourtInfo item : allScourt) {
						String tmpCourtName = item.getCourtName();
						if (existItem.get(tmpCourtName) == null) {
							dst = item;
							break;
						}
					}
				}
				if (dst == null) {
					retCode.setErrorCode("700");
					retCode.setErrorMsg(scourt.getBdate() + "日" + scourt.getStartHour() + "点至" + scourt.getEndHour()
							+ "点之间所有场地都已经有预约！");
					return retCode;
				}
				scourt.setCourtName(dst.getCourtName());
				scourt.setCourtId(String.valueOf(dst.getCourtId()));
			} else {
				if (tmp != null && tmp.size() > 0) {
					for (Scourt item : tmp) {
						if (item.getCourtId().equals(scourt.getCourtId())) {
							retCode.setErrorCode("700");
							retCode.setErrorMsg(
									item.getBdate() + "日" + item.getStartHour() + "点至" + item.getEndHour() + "点已经被预约！");
							return retCode;
						}
					}
				}
			}

			if (scourt.getUserName() == null || scourt.getUserName().trim().length() < 1) {
				String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
				scourt.setUserName(userName);
			}
			scourtDao.save(scourt);
			retCode.setErrorCode("200");
			retCode.setErrorMsg("预订成功！");
		} catch (Exception e) {
			e.printStackTrace();
			retCode.setErrorCode("500");
			retCode.setErrorMsg("预订失败！");
		}

		return retCode;
	}

	@RequestMapping(value = "/bookList", method = RequestMethod.POST, produces = "application/json")
	public List<Scourt> bookList(@RequestParam String bookTime) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		List<Scourt> list = null;
		if (bookTime == null || bookTime.trim().length() < 1) {
			list = scourtDao.findByUserName(userName);
		} else {
			list = scourtDao.findByUserNameAndBdate(userName, bookTime);
		}

		for (Scourt scourt : list) {
			scourt.setStartTime(scourt.getBdate() + " " + scourt.getStartHour());
			scourt.setEndTime(scourt.getBdate() + " " + scourt.getEndHour());
		}

		return list;
	}

	@RequestMapping(value = "/courtList", method = RequestMethod.POST, produces = "application/json")
	public List<ScourtInfo> courtList() {
		List<ScourtInfo> list = (List<ScourtInfo>) scourtInfoDao.findAll();
		return list;
	}

	@RequestMapping(value = "/avaiCourtList", method = RequestMethod.POST, produces = "application/json")
	public List<ScourtInfo> avaiCourtList(@RequestParam String startTime, @RequestParam String endTime) {
		try {
			if (startTime == null || startTime.trim().length() < 1 || endTime == null || endTime.trim().length() < 1) {
				List<ScourtInfo> list = (List<ScourtInfo>) scourtInfoDao.findAll();
				return list;
			}
			List<ScourtInfo> allScourt = (List<ScourtInfo>) scourtInfoDao.findAll();
			Date startDate = DateUtil.parse(startTime, DateUtil.FullDateHourPattern);
			Date endDate = DateUtil.parse(endTime, DateUtil.FullDateHourPattern);
			if (!DateUtil.isSameDay(startDate, endDate)) {
				return null;
			}
			String bdate = DateUtil.format(startDate, DateUtil.SimpleDatePattern);
			int startHour = startDate.getHours();
			int endHour = endDate.getHours();
			List<Scourt> tmp = scourtDao.queryExistOrNot(startHour, endHour, bdate);
			Map<String, Boolean> existItem = new HashMap<>();
			for (Scourt initem : tmp) {
				existItem.put(initem.getCourtName(), true);
			}
			List<ScourtInfo> avaiCourtList = new ArrayList<>();
			for (ScourtInfo item : allScourt) {
				String tmpCourtName = item.getCourtName();
				if (existItem.get(tmpCourtName) == null) {
					avaiCourtList.add(item);
				}
			}
			return avaiCourtList;
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/getCourtId", method = RequestMethod.POST)
	public int getCourtId(@RequestParam String courtName) {
		ScourtInfo scourtInfo = scourtInfoDao.findByCourtName(courtName);
		return scourtInfo.getCourtId();
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = "application/json")
	public RetCode updateUser(@RequestBody User user) {
		RetCode retCode = new RetCode();
		try {
			User tblUser = userDao.findByUserName(user.getUserName());
			if(tblUser == null){
				retCode.setErrorCode("300");
				retCode.setErrorMsg("用户不存在！");
				return retCode;
			}
			if(user.getEmailAddr()!=null && user.getEmailAddr().trim().length() > 0){
				tblUser.setEmailAddr(user.getEmailAddr());
			}
			if(user.getMobile()!=null && user.getMobile().trim().length() > 0){
				tblUser.setMobile(user.getMobile());
			}
			if(user.getName()!=null && user.getName().trim().length() > 0){
				tblUser.setName(user.getName());
			}
			userDao.save(tblUser);
			retCode.setErrorCode("200");
			retCode.setErrorMsg("更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			
			retCode.setErrorCode("500");
			retCode.setErrorMsg("更新失败！");
		}

		return retCode;
	}
	
	@RequestMapping(value = "/userInfo", method = RequestMethod.POST, produces = "application/json")
	public User userInfo() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if (userName == null || userName.trim().length() < 1) {
			return null;
		} else {
			User user = userDao.findByUserName(userName);
			User tmp = new User();
			tmp.setUserName(user.getUserName());
			tmp.setMobile(user.getMobile());
			tmp.setName(user.getName());
			tmp.setEmailAddr(user.getEmailAddr());
			return tmp;
		}
	}
	
	@RequestMapping(value = "/checkCurrentAccount", method = RequestMethod.POST, produces = "application/json")
	public RetCode checkCurrentAccount() {
		RetCode retCode = new RetCode();
		String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
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
	
	@RequestMapping(value = "/closeCurrentAccount", method = RequestMethod.POST, produces = "application/json")
	public RetCode closeCurrentAccount() {
		RetCode retCode = new RetCode();
		String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
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
	
	@RequestMapping(value = "/accountBal", method = RequestMethod.POST, produces = "application/json")
	public Account accountBal() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if (userName == null || userName.trim().length() < 1) {
			return null;
		} else {
			Account account = accountDao.findByUserName(userName);
			return account;
		}
	}
	
	@RequestMapping(value = "/accountDetail", method = RequestMethod.POST, produces = "application/json")
	public List<AccountTransflow> accountDetail(@RequestParam String transDate ) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if (userName == null || userName.trim().length() < 1) {
			return null;
		} else {
			if(transDate == null || transDate.trim().length() < 1){
				transDate = DateUtil.format(new Date(), DateUtil.SimpleDatePattern);
			}
			List<AccountTransflow> accounts = accountTransFlowDao.findByUserNameAndTransDate(userName, transDate);
			return accounts;
		}
	}
}
