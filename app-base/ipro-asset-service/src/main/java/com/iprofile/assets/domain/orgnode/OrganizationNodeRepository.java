package com.iprofile.assets.domain.orgnode;



import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;




public interface OrganizationNodeRepository extends GraphRepository<OrgNode>{
	

	// get the Parent Node based on a row key
//	OrgNode findByOrgNodeRowKey(String parentRowKey);
		
	
	
	@Query("MATCH (orgNode:OrgNode)-[:PART_OF]->(bna:BusinessNodeAccount) " +
	           "WHERE bna.bnaNumber= {bnaId} " +
	           "RETURN orgNode")
	List<OrgNode> getOrgNodeListBelongingToBnaId(@Param("bnaId") Integer bnaId);

	// Query required for get the OrgNode Hierarchy, leader details, 
	
	@Query("MATCH (orgNode:OrgNode)-[:PART_OF]->(bna:BusinessNodeAccount)"	
			+ "WHERE bna.bnaNumber= {bnaId} " 
			+ "OPTIONAL MATCH (orgNode)<-[:LED_BY]-(leaderEmployee: Employee) "
			+ "OPTIONAL MATCH (orgNode) <-[:CHILD_OF]-(parentNode: OrgNode) "         
	          + "RETURN orgNode as orgNode, leaderEmployee as leaderEmployee, parentNode as parentNode")
	List<QueryResultOrgNode> getOrgNodeListForBnaId(@Param("bnaId") Integer bnaId);

	
}
