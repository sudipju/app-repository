package com.iprofile.assets.domain.orgnode;

import org.springframework.data.neo4j.annotation.QueryResult;

import com.iprofile.assets.domain.employee.Employee;

@QueryResult
public class QueryResultOrgNode {

	        OrgNode orgNode;
	        Employee  leaderEmployee;
	        OrgNode parentNode;
			
	        public OrgNode getOrgNode() {
				return orgNode;
			}
			public void setOrgNode(OrgNode orgNode) {
				this.orgNode = orgNode;
			}
			public Employee getLeaderEmployee() {
				return leaderEmployee;
			}
			public void setLeaderEmployee(Employee leaderEmployee) {
				this.leaderEmployee = leaderEmployee;
			}
			public OrgNode getParentNode() {
				return parentNode;
			}
			public void setParentNode(OrgNode parentNode) {
				this.parentNode = parentNode;
			}
	      
	
}
