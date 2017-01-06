package com.iprofile.assets.domain.competency;



import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.iprofile.assets.domain.cna.CorporateNodeAccount;


public interface CompetencyRepository extends GraphRepository<Competency>{
	

	
	// get the Parent Node based on a row key
	// Competency findByCompetencyRowKey(String competencyRowKey);
		
	
	
	
	
	@Query("MATCH (competency:Competency)-[:APPLIES_TO]->(bna:BusinessNodeAccount) " +
	           "WHERE bna.bnaNumber= {bnaId} " +
	           "RETURN competency")
	List<Competency> getCompetencyListApplyingToBnaId(@Param("bnaId") Integer bnaId);


	@Query("MATCH (competency:Competency)-[:MEMBER_TO]->(compProfile:CompetencyProfile) " +
	           "WHERE id(compProfile) = {competencyProfileId} " +
	           "RETURN competency")
	List<Competency> getCompetencyListByCompetencyProfileId(@Param("competencyProfileId") Long competencyProfileId);


	Competency findByCompetencyName(String competencyName);
	

}
