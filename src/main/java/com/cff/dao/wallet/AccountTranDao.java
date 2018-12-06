package com.cff.dao.wallet;

import org.springframework.data.repository.CrudRepository;

import com.cff.domain.wallet.AccountTran;

public interface AccountTranDao extends CrudRepository<AccountTran, Integer> {
	
}