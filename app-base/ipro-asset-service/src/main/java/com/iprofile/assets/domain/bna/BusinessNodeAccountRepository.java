package com.iprofile.assets.domain.bna;



import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;



public interface BusinessNodeAccountRepository extends GraphRepository<BusinessNodeAccount>{
	

	BusinessNodeAccount findByBnaNumber(Integer bnaNumber);
	

	@Query("MATCH (bna:BusinessNodeAccount)-[:BELONGS_TO]->(cna:CorporateNodeAccount) " +
	           "WHERE cna.corporateNodeId= {cnaId} " +
	           "RETURN bna")
	List<BusinessNodeAccount> getBnaListByCnaId(@Param("cnaId") Integer cnaId);           
	/*
	@Query("MATCH (bna:BusinessNodeAccount) " +
	           "RETURN bna")
	           */


}
