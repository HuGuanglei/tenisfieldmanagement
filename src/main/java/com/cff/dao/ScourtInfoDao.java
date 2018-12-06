package com.cff.dao;

import org.springframework.data.repository.CrudRepository;

import com.cff.domain.ScourtInfo;

public interface ScourtInfoDao extends CrudRepository<ScourtInfo, Integer> {
	ScourtInfo findByCourtName(String courtName);
	
	ScourtInfo findByCourtIdAndCourtName(int courtId, String courtName);
}