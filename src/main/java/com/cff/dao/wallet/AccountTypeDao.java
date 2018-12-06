package com.cff.dao.wallet;

import org.springframework.data.repository.CrudRepository;

import com.cff.domain.wallet.AccountType;

public interface AccountTypeDao extends CrudRepository<AccountType, Integer> {
	AccountType findByAccType(String accType);

}