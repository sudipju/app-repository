package com.iprofile.assets.domain.competency;

import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.annotation.QueryResult;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iprofile.assets.domain.bna.BusinessNodeAccount;
import com.iprofile.assets.domain.orgnode.OrgNode;

@NodeEntity
public class ProficiencyIndicator {
	

	@GraphId
	 private Long id;
	 
	private Integer profLevel;
	 
	// private String indicatorRowKey; //need to add a module to compute the row key for each cna
	
	private String indicatorText;
	

	//Type of the eval whether a rating question or a multiple choice - for generic feedback/survey
	
	// private String profEvalType; 
	
	//answer choices for multiple choice question
	
	
	@JsonIgnore
	@Relationship(type="INDICATOR_OF", direction = "OUTGOING")
	 private Competency competency;
	
	
	
	 public ProficiencyIndicator() {
	 }
	 public ProficiencyIndicator(Integer profLevel, String indicatorText ) {
	 this.profLevel = profLevel;
	// this.indicatorRowKey = indicatorRowKey;
	 this.indicatorText=indicatorText;
	 }
	public Integer getProfLevel() {
		return profLevel;
	}
	public void setProfLevel(Integer profLevel) {
		this.profLevel = profLevel;
	}
	
	public String getIndicatorText() {
		return indicatorText;
	}
	public void setIndicatorText(String indicatorText) {
		this.indicatorText = indicatorText;
	}
	public Competency getCompetency() {
		return competency;
	}
	public void setCompetency(Competency competency) {
		this.competency = competency;
	}
	public Long getId() {
		return id;
	}
	
	
}



