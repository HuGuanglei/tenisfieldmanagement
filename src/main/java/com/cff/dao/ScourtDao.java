package com.cff.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cff.domain.Scourt;

public interface ScourtDao extends CrudRepository<Scourt, Integer> {

	List<Scourt> findByUserName(String userName);
	
	List<Scourt> findByCourtName(String courtName);
	
	List<Scourt> findByUserNameAndBdate(String userName, String bdate);
	
	List<Scourt> findByCourtNameAndBdate(String courtName, String bdate);

	List<Scourt> findByBdate(String bdate);
	
	@Query(value = "SELECT * FROM (select * from scourt c where bdate = ?3 ) s where (startHour >= ?1 AND startHour < ?2) OR (startHour <= ?1 AND endHour >= ?2) OR (endHour > ?1 AND endHour <= ?2)", nativeQuery=true)
	List<Scourt> queryExistOrNot(@Param("startHour")int startHour, @Param("endHour")int endHour, @Param("bdate")String bdate);
}