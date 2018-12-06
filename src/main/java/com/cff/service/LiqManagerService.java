package com.cff.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cff.common.Constant;
import com.cff.dao.wallet.AccountDao;
import com.cff.dao.wallet.AccountFlowDao;
import com.cff.dao.wallet.AccountTransFlowDao;
import com.cff.domain.wallet.Account;
import com.cff.domain.wallet.AccountFlow;
import com.cff.domain.wallet.AccountTransflow;
import com.cff.dto.RetCode;
import com.cff.util.DateUtil;

@Service
public class LiqManagerService {
	@Autowired
	AccountDao accountDao;

	@Autowired
	AccountTransFlowDao accountTransFlowDao;

	@Autowired
	AccountFlowDao accountFlowDao;

	@Transactional
	public RetCode liq(AccountTransflow accountTransflow) {
		RetCode ret = new RetCode();
		switch (accountTransflow.getTransType()) {
		case Constant.TRANS_TYPE_CHARGE:
			List<Account> accountLoan = accountDao.findByAccType(Constant.SYS_LOAN_ACCT_TYPE);
			Account loanAccount = accountLoan.get(0);

			if (loanAccount == null) {
				ret.setErrorCode("100");
				ret.setErrorMsg("系统贷账户不存在！");
				return ret;
			}

			Account dstAccount = accountDao.findByUserName(accountTransflow.getUserName());
			
			liquid(loanAccount, dstAccount, accountTransflow);
			ret.setErrorCode("200");
			ret.setErrorMsg("充值成功！");
			return ret;
		case Constant.TRANS_TYPE_CONSUME:
			List<Account> accountLend = accountDao.findByAccType(Constant.SYS_LEND_ACCT_TYPE);
			Account lendAccount = accountLend.get(0);

			if (lendAccount == null) {
				ret.setErrorCode("100");
				ret.setErrorMsg("系统借账户不存在！");
				return ret;
			}

			Account srcAccount = accountDao.findByUserName(accountTransflow.getUserName());

			liquid(srcAccount, lendAccount, accountTransflow);
			ret.setErrorCode("200");
			ret.setErrorMsg("消费成功！");
			return ret;
		case Constant.TRANS_TYPE_TRANSFER:
			ret.setErrorCode("400");
			ret.setErrorMsg("暂未开通此交易！");
			return ret;
		case Constant.TRANS_TYPE_CLOSE:
			List<Account> accountLendClose = accountDao.findByAccType(Constant.SYS_LEND_ACCT_TYPE);
			Account lendAccountClose = accountLendClose.get(0);

			if (lendAccountClose == null) {
				ret.setErrorCode("100");
				ret.setErrorMsg("系统借账户不存在！");
				return ret;
			}

			Account srcAccountClose = accountDao.findByUserName(accountTransflow.getUserName());

			liquid(srcAccountClose, lendAccountClose, accountTransflow);
			ret.setErrorCode("200");
			ret.setErrorMsg("销户成功，账户金额已清空！");
			return ret;
		case Constant.TRANS_TYPE_CANCEL:
			List<AccountFlow>  accountFlows = accountFlowDao.findByTransFlow(accountTransflow.getId());
			AccountFlow src = null;
			AccountFlow dst = null;
			for(AccountFlow item : accountFlows){
				if(item.getDir().equals(Constant.ACCOUNT_DIR_OUT)){
					src = item;
				}else if(item.getDir().equals(Constant.ACCOUNT_DIR_IN)){
					dst = item;
				}
			}
			if(src == null || dst == null){
				ret.setErrorCode("404");
				ret.setErrorMsg("交易不正常，暂时无法撤销！");
				return ret;
			}
			if(src.getTransAmt() != dst.getOppAmt() || src.getOppAmt() != dst.getTransAmt() || src.getTransAmt() != accountTransflow.getTransAmt()){
				ret.setErrorCode("505");
				ret.setErrorMsg("交易金额不一致，暂时无法撤销！");
				return ret;
			}
			Account revocSrcAccount = accountDao.findOne(dst.getTransAccid());
			Account revocDstAccount = accountDao.findOne(src.getTransAccid());
			
			liquid(revocSrcAccount, revocDstAccount, accountTransflow);
			accountTransflow.setFlag(Constant.TRANS_FLOW_FALG_CANCEL);
			accountTransFlowDao.save(accountTransflow);
			
			ret.setErrorCode("200");
			ret.setErrorMsg("交易撤销成功！");
			return ret;
		default:
			ret.setErrorCode("404");
			ret.setErrorMsg("暂未开通此交易！");
			return ret;
		}
	}

	private void liquid(Account src, Account dst, AccountTransflow accountTransflow) {
		AccountFlow accountFlow = new AccountFlow();
		accountFlow.setTransFlow(accountTransflow.getId());
		accountFlow.setTransAccid(src.getAccountId());
		accountFlow.setOppAccid(dst.getAccountId());
		accountFlow.setTransAmt(accountTransflow.getTransAmt());
		accountFlow.setOppAmt(0);
		accountFlow.setTransBal(src.getBal());
		accountFlow.setTransDate(DateUtil.format(new Date(), DateUtil.SimpleDatePattern));
		accountFlow.setTransTime(DateUtil.format(new Date(), DateUtil.FullTimePatternNoSymbol));
		accountFlow.setTransType(accountTransflow.getTransType());
		accountFlow.setDir(Constant.ACCOUNT_DIR_OUT);
		accountFlowDao.save(accountFlow);

		src.setBal(src.getBal() - accountTransflow.getTransAmt());
		accountDao.save(src);

		AccountFlow accountDstFlow = new AccountFlow();
		accountDstFlow.setTransFlow(accountTransflow.getId());
		accountDstFlow.setTransAccid(dst.getAccountId());
		accountDstFlow.setOppAccid(src.getAccountId());
		accountDstFlow.setOppAmt(accountTransflow.getTransAmt());
		accountDstFlow.setTransAmt(0);
		accountDstFlow.setTransBal(dst.getBal());
		accountDstFlow.setTransDate(DateUtil.format(new Date(), DateUtil.SimpleDatePattern));
		accountDstFlow.setTransTime(DateUtil.format(new Date(), DateUtil.FullTimePatternNoSymbol));
		accountDstFlow.setTransType(accountTransflow.getTransType());
		accountDstFlow.setDir(Constant.ACCOUNT_DIR_IN);
		accountFlowDao.save(accountDstFlow);

		dst.setBal(dst.getBal() + accountTransflow.getTransAmt());
		accountDao.save(dst);
	}
}
