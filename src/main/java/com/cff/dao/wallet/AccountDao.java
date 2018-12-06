package com.cff.dao.wallet;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cff.domain.wallet.Account;

public interface AccountDao extends CrudRepository<Account, Integer> {

	Account findByUserName(String userName);
	
	List<Account> findByAccType(String accType);
}