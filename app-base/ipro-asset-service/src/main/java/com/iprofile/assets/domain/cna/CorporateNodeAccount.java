package com.iprofile.assets.domain.cna;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class CorporateNodeAccount {
	
	@GraphId
	 private Long id;
	 
	private String corpName;
	 
	private Integer corporateNodeId;
	
	 public CorporateNodeAccount() {
	 }
	 public CorporateNodeAccount(String CorpName, Integer corporateNodeId) {
	 this.corpName = CorpName;
	 this.corporateNodeId = corporateNodeId;
	 
	 }
	
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public Integer getCorporateNodeId() {
		return corporateNodeId;
	}
	public void setCorporateNodeId(Integer corporateNodeId) {
		this.corporateNodeId = corporateNodeId;
	}

	 
}
