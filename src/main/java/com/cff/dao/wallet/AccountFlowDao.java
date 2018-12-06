package com.cff.dao.wallet;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cff.domain.wallet.AccountFlow;

public interface AccountFlowDao extends CrudRepository<AccountFlow, Integer> {
	List<AccountFlow> findByTransFlow(int transFlow);
}