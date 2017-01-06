package com.iprofile.assets.domain.competency;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iprofile.assets.domain.bna.BusinessNodeAccount;
import com.iprofile.assets.domain.orgnode.OrgNode;

@NodeEntity
public class Competency {
	

	@GraphId
	 private Long id;
	 
	private String competencyName;
	
	private String shortDescription;
	
	// private String competencyRowKey; //need to add a module to compute the row key for each cna
	
	 @JsonIgnore
	@Relationship(type="APPLIES_TO", direction = "OUTGOING")
	 private BusinessNodeAccount businessNodeAccount;
	
	 @JsonIgnore
	 @Relationship(type="MEMBER_TO", direction = "OUTGOING")
	 private CompetencyProfile competencyProfile;
	
	
	 public Competency() {
	 }
	 public Competency(String competencyName, String shortDescription) {
	 this.competencyName = competencyName;
	 this.shortDescription = shortDescription;
	 }
	public String getCompetencyName() {
		return competencyName;
	}
	public void setCompetencyName(String competencyName) {
		this.competencyName = competencyName;
	}
	
	public BusinessNodeAccount getBusinessNodeAccount() {
		return businessNodeAccount;
	}
	public void setBusinessNodeAccount(BusinessNodeAccount businessNodeAccount) {
		this.businessNodeAccount = businessNodeAccount;
	}
	public CompetencyProfile getCompetencyProfile() {
		return competencyProfile;
	}
	public void setCompetencyProfile(CompetencyProfile competencyProfile) {
		this.competencyProfile = competencyProfile;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public Long getId() {
		return id;
	}
	
	
	 
}
