package com.cff.dao.wallet;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cff.domain.wallet.AccountTransflow;

public interface AccountTransFlowDao extends CrudRepository<AccountTransflow, Integer> {

	List<AccountTransflow> findByUserNameAndTransDate(String userName, String transDate);

	List<AccountTransflow> findByTransDate(String transDate);
	
}