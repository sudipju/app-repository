package com.iprofile.assets.domain.orgnode;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iprofile.assets.domain.bna.BusinessNodeAccount;
import com.iprofile.assets.domain.employee.Employee;

@NodeEntity
public class OrgNode {
	

	@GraphId
	 private Long id;
	
	private String orgNodeName;
	
	private String functionalArea;
	 
	// private String orgNodeRowKey; //need to add a module to compute the row key for each cna
	
	private Integer orgNodeLevel;
	
	private Integer isDepartment; // 0 is No, 1 is Yes
	
	private Integer isBusinessUnit; //0 is No, 1 is Yes
	
	
	@JsonIgnore
	@Relationship(type="PART_OF", direction = "OUTGOING")
	private BusinessNodeAccount businessNodeAccount;
	
	@JsonIgnore
	@Relationship(type="CHILD_OF", direction = "INCOMING")
	 private OrgNode parentNode;
	
	@JsonIgnore
	 @Relationship(type="LED_BY", direction = "INCOMING")
	 private Employee leader;
	
	 // not for persistence, but required to send part of the JSON for ngrx store
	@Transient 
	private Long parentNodeId;
	
	@Transient
	private Long leaderEmployeeId;
	 
	 public OrgNode() {
	 }
	 public OrgNode(String orgNodeName) {
	 this.orgNodeName = orgNodeName;
	 }
	public String getOrgNodeName() {
		return orgNodeName;
	}
	public void setOrgNodeName(String orgNodeName) {
		this.orgNodeName = orgNodeName;
	}
	public BusinessNodeAccount getBusinessNodeAccount() {
		return businessNodeAccount;
	}
	public void setBusinessNodeAccount(BusinessNodeAccount businessNodeAccount) {
		this.businessNodeAccount = businessNodeAccount;
	}
	public Integer getOrgNodeLevel() {
		return orgNodeLevel;
	}
	public void setOrgNodeLevel(Integer orgNodeLevel) {
		this.orgNodeLevel = orgNodeLevel;
	}
	public OrgNode getParentNode() {
		return parentNode;
	}
	public void setParentNode(OrgNode parentNode) {
		this.parentNode = parentNode;
	}
	public Employee getLeader() {
		return leader;
	}
	public void setLeader(Employee leader) {
		this.leader = leader;
	}
	public String getFunctionalArea() {
		return functionalArea;
	}
	public void setFunctionalArea(String functionalArea) {
		this.functionalArea = functionalArea;
	}
	public Integer getIsDepartment() {
		return isDepartment;
	}
	public void setIsDepartment(Integer isDepartment) {
		this.isDepartment = isDepartment;
	}
	public Integer getIsBusinessUnit() {
		return isBusinessUnit;
	}
	public void setIsBusinessUnit(Integer isBusinessUnit) {
		this.isBusinessUnit = isBusinessUnit;
	}
	public Long getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(Long parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public Long getLeaderEmployeeId() {
		return leaderEmployeeId;
	}
	public void setLeaderEmployeeId(Long leaderEmployeeId) {
		this.leaderEmployeeId = leaderEmployeeId;
	}
	public Long getId() {
		return id;
	} 
	
	 
}
