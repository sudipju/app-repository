package com.iprofile.assets.domain.employee;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iprofile.assets.domain.bna.BusinessNodeAccount;
import com.iprofile.assets.domain.competency.CompetencyProfile;
import com.iprofile.assets.domain.orgnode.OrgNode;

@NodeEntity
public class Employee {
	

	@GraphId
	private Long id;
	 
	private String employeeName;
	
	private String title;
	
	private String emailAddress;
	
	private String activationStatus; // PROVISIONED, EMAIL_SENT, ACTIVE, SUSPENDED, CANCELLED
	
	private String activationCode;
	
	@JsonIgnore
	private String gcmToken;
	
	// private String employeeRowKey; //need to add a module to compute the row key for each cna
	
	@JsonIgnore
	@Relationship(type="WORKS_FOR", direction = "OUTGOING")
	private BusinessNodeAccount businessNodeAccount;
	
	
	 @Relationship(type="SUPPORTED_BY", direction = "OUTGOING")
	 private Employee leaderEmployee;
	 
	 @JsonIgnore
	 @Relationship(type="MEMBER_OF", direction = "OUTGOING")
	 private OrgNode orgNodeMemberOf;
	
	 @JsonIgnore
	 @Relationship(type="COMPETENCY_PROFILE", direction = "OUTGOING")
	 private CompetencyProfile competencyProfile;
	 
	@Transient
	 private Long leaderEmployeeId;
	
	@Transient
	 private Long competencyProfileId;
	 
	 // private Long orgNodeId;
	 
	 public CompetencyProfile getCompetencyProfile() {
		return competencyProfile;
	}
	public void setCompetencyProfile(CompetencyProfile competencyProfile) {
		this.competencyProfile = competencyProfile;
	}
	public Employee() {
	 }
	 public Employee(String employeeName) {
	 this.employeeName = employeeName;
	 }
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public BusinessNodeAccount getBusinessNodeAccount() {
		return businessNodeAccount;
	}
	public void setBusinessNodeAccount(BusinessNodeAccount businessNodeAccount) {
		this.businessNodeAccount = businessNodeAccount;
	}
	public OrgNode getOrgNodeMemberOf() {
		return orgNodeMemberOf;
	}
	public void setOrgNodeMemberOf(OrgNode orgNodeMemberOf) {
		this.orgNodeMemberOf = orgNodeMemberOf;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Employee getLeaderEmployee() {
		return leaderEmployee;
	}
	public void setLeaderEmployee(Employee leaderEmployee) {
		this.leaderEmployee = leaderEmployee;
	}
	public Long getId() {
		return id;
	}
	public Long getLeaderEmployeeId() {
		return leaderEmployeeId;
	}
	public void setLeaderEmployeeId(Long leaderEmployeeId) {
		this.leaderEmployeeId = leaderEmployeeId;
	}
	public Long getCompetencyProfileId() {
		return competencyProfileId;
	}
	public void setCompetencyProfileId(Long competencyProfileId) {
		this.competencyProfileId = competencyProfileId;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getActivationStatus() {
		return activationStatus;
	}
	public void setActivationStatus(String activationStatus) {
		this.activationStatus = activationStatus;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public String getGcmToken() {
		return gcmToken;
	}
	public void setGcmToken(String gcmToken) {
		this.gcmToken = gcmToken;
	}
	
}
