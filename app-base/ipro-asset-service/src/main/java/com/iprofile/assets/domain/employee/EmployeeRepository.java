package com.iprofile.assets.domain.employee;



import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.iprofile.assets.domain.cna.CorporateNodeAccount;


public interface EmployeeRepository extends GraphRepository<Employee>{
	

	// get the Parent Node based on a row key
//	Employee findByOrgNodeRowKey(String parentRowKey);
		
	
	
	@Query("MATCH (employee:Employee)-[:WORKS_FOR]->(bna:BusinessNodeAccount) " +
	           "WHERE bna.bnaNumber= {bnaId} " +
	           "RETURN employee")
	List<Employee> getEmployeeListBelongingToBnaId(@Param("bnaId") Integer bnaId);
	
	/*
	MATCH (employee:Employee)-[:WORKS_FOR]->(bna:BusinessNodeAccount),  
	(employee)-[:SUPPORTED_BY]->(leaderEmployee: Employee), 
	(employee) -[:COMPETENCY_PROFILE]->(compProfile: CompetencyProfile) 
	WHERE bna.businessNodeId= 50602 RETURN employee as employee, compProfile as competencyProfile, leaderEmployee as leaderEmployee
*/	
	
	/*
	 * OPTIONAL MATCH like outer join in relational DB
	 * (bna:BusinessNodeAccount)WHERE bna.businessNodeId= 50602 OPTIONAL  
	 * MATCH (employee)-[:SUPPORTED_BY]->(leaderEmployee: Employee) OPTIONAL  
	 * MATCH (employee) -[:COMPETENCY_PROFILE]->(compProfile: CompetencyProfile)   
	 * RETURN employee as employee, leaderEmployee as leaderEmployee, 
	 * compProfile as competencyProfile
	 */

	@Query("MATCH (employee:Employee)-[:WORKS_FOR]->(bna:BusinessNodeAccount)"	
			+ "WHERE bna.bnaNumber= {bnaId} " 
			+ "OPTIONAL MATCH (employee)-[:SUPPORTED_BY]->(leaderEmployee: Employee) "
			+ "OPTIONAL MATCH (employee) -[:COMPETENCY_PROFILE]->(compProfile: CompetencyProfile) "         
	          + "RETURN employee as employee, compProfile as competencyProfile, leaderEmployee as leaderEmployee")
	List<QueryResultEmployee> getEmployeeListForBnaId(@Param("bnaId") Integer bnaId);


}
