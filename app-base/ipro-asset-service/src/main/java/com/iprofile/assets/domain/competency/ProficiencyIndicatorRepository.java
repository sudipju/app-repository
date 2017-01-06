package com.iprofile.assets.domain.competency;



import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;



public interface ProficiencyIndicatorRepository extends GraphRepository<ProficiencyIndicator>{
	

	// get the Parent Node based on a row key
//	Employee findByOrgNodeRowKey(String parentRowKey);
		
/*
 @Query("MATCH (proficiencyIndicator:ProficiencyIndicator {profLevel:{level}})-[:INDICATOR_OF]->(competency:Competency) " +
	           "WHERE id(competency) = {competencyId} " +
	           "RETURN proficiencyIndicator")	
 */
	
	@Query("MATCH (proficiencyIndicator:ProficiencyIndicator)-[:INDICATOR_OF]->(competency:Competency) " +
	           "WHERE id(competency) = {competencyId} " +
	           "RETURN proficiencyIndicator")
	List<ProficiencyIndicator> getProfIndicatorListByCompetencyAndLevel(@Param("level") Integer level,@Param("competencyId") Long competencyId );

//make changes to competencyLevel at CompetencyProfile for the following query. The existing test graph does not have level in the compProfile Node
	/*@Query("MATCH (proficiencyIndicator:ProficiencyIndicator)-[:INDICATOR_OF]->(competency:Competency)-[:MEMBER_TO]->(compProfile:CompetencyProfile) <-[:ASSOCIATE_TO]-(employee:Employee) " +
	           "WHERE employee.employeeRowKey={2} AND (compProfile.competencyLevel={0} OR compProfile.competencyLevel={1}) " +
	           "RETURN competency, COLLECT(proficiencyIndicator) as listProficiencyIndicator") */

	@Query("MATCH (proficiencyIndicator:ProficiencyIndicator)-[:INDICATOR_OF]->(competency:Competency)-[:MEMBER_TO]->(compProfile:CompetencyProfile) <-[:ASSOCIATE_TO]-(employee:Employee) " +
	           "WHERE employee.employeeRowKey={2} " +
	           "RETURN competency, COLLECT(proficiencyIndicator) as listProficiencyIndicator")
	   List<CompetencyProficiencyIndicator> getCompetencyProfIndicatorByEmployeeRowKey(
	   		Integer level, Integer nextLevel, String employeeRowKey);
	
	
	@Query("MATCH (proficiencyIndicator:ProficiencyIndicator)-[:INDICATOR_OF]->(competency:Competency)-[:MEMBER_TO]->(compProfile:CompetencyProfile) <-[:ASSOCIATE_TO]-(employee:Employee) " +
	           "WHERE employee.employeeRowKey={employeeRowKey} " +
	           "RETURN proficiencyIndicator")
	List<ProficiencyIndicator> getProfIndicatorListByEmployeeRowKeyAndLevel(@Param("level") Integer level,@Param("employeeRowKey") String employeeRowKey );


	
//	MATCH (proficiencyIndicator:ProficiencyIndicator)-[:INDICATOR_OF]->(competency:Competency)-[:MEMBER_TO]->(compProfile:CompetencyProfile) <-[:ASSOCIATE_TO]-(employee:Employee) WHERE employee.employeeRowKey='100.200.employee624' RETURN proficiencyIndicator

}


