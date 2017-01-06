package com.iprofile.assets.domain.competency;



import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.iprofile.assets.domain.cna.CorporateNodeAccount;


public interface CompetencyProfileRepository extends GraphRepository<CompetencyProfile>{
	

	// get the Parent Node based on a row key
	CompetencyProfile findByCompetencyProfileRowKey(String compProfileRowKey);
		
	
	
	@Query("MATCH (competencyProfile:CompetencyProfile)-[:APPLIES_TO_1]->(bna:BusinessNodeAccount) " +
	           "WHERE bna.bnaNumber= {bnaId} " +
	           "RETURN competencyProfile")
	List<CompetencyProfile> getCompetencyProfileListApplyingToBnaId(@Param("bnaId") Integer bnaId);

	
	
}
