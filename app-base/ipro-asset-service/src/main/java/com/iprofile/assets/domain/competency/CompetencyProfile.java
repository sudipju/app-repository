package com.iprofile.assets.domain.competency;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iprofile.assets.domain.bna.BusinessNodeAccount;
import com.iprofile.assets.domain.orgnode.OrgNode;

@NodeEntity
public class CompetencyProfile {
	

	@GraphId
	 private Long id;
	 
	private String competencyProfileName;
	 
	private String competencyProfileRowKey; //need to add a module to compute the row key for each cna
	
// this will ensure a competency profile is linked to a level while finding the questions for prof Indicators
	 private Integer competencyLevel; 
	
	 @JsonIgnore
	@Relationship(type="APPLIES_TO_1", direction = "OUTGOING")
	 private BusinessNodeAccount businessNodeAccount;
		
	
	 public CompetencyProfile() {
	 }
	 public CompetencyProfile(String competencyProfileName, String competencyProfileRowKey) {
	 this.competencyProfileName = competencyProfileName;
	 this.competencyProfileRowKey = competencyProfileRowKey;
	 }
	public String getCompetencyProfileName() {
		return competencyProfileName;
	}
	public void setCompetencyProfileName(String competencyProfileName) {
		this.competencyProfileName = competencyProfileName;
	}
	public String getCompetencyProfileRowKey() {
		return competencyProfileRowKey;
	}
	public void setCompetencyProfileRowKey(String competencyProfileRowKey) {
		this.competencyProfileRowKey = competencyProfileRowKey;
	}
	public BusinessNodeAccount getBusinessNodeAccount() {
		return businessNodeAccount;
	}
	public void setBusinessNodeAccount(BusinessNodeAccount businessNodeAccount) {
		this.businessNodeAccount = businessNodeAccount;
	}
	public Integer getCompetencyLevel() {
		return competencyLevel;
	}
	public void setCompetencyLevel(Integer competencyLevel) {
		this.competencyLevel = competencyLevel;
	}
	public Long getId() {
		return id;
	}
	
	
		
}
