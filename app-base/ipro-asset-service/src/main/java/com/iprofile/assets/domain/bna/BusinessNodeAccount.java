package com.iprofile.assets.domain.bna;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.iprofile.assets.domain.cna.CorporateNodeAccount;

@NodeEntity
public class BusinessNodeAccount {


		
		@GraphId
		private Long id;		 
		private String bnaName;		 
		private Integer bnaNumber;
		private String scimGroupId;
	    private String address;	    
	    private String address2;	    
	    private String city;
	 
	    private String stateOrProvince;	    
	    private String country;	    
	    private String zipOrPostalCode;
	    
	    private String primaryContactName;	    
	    private String primaryContactEmail;	    
	    private String primaryContactPhone;
	    private Integer numberOfUsers;
	    private Integer numberOfAssets;
	    
	
	    
		@Relationship(type="BELONGS_TO", direction = "OUTGOING")
		 private CorporateNodeAccount corpNodeAccount;
	
		 public BusinessNodeAccount() {
		 }
		 
		public Long getId() {
			return id;
		}

		public String getBnaName() {
			return bnaName;
		}

		public void setBnaName(String bnaName) {
			this.bnaName = bnaName;
		}

		public Integer getBnaNumber() {
			return bnaNumber;
		}

		public void setBnaNumber(Integer bnaNumber) {
			this.bnaNumber = bnaNumber;
		}
	
		
		public String getScimGroupId() {
			return scimGroupId;
		}

		public void setScimGroupId(String scimGroupId) {
			this.scimGroupId = scimGroupId;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		
		
		public CorporateNodeAccount getCorpNodeAccount() {
			return corpNodeAccount;
		}

		public void setCorpNodeAccount(CorporateNodeAccount corpNodeAccount) {
			this.corpNodeAccount = corpNodeAccount;
		}

		public String getAddress2() {
			return address2;
		}

		public void setAddress2(String address2) {
			this.address2 = address2;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getStateOrProvince() {
			return stateOrProvince;
		}

		public void setStateOrProvince(String stateOrProvince) {
			this.stateOrProvince = stateOrProvince;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getZipOrPostalCode() {
			return zipOrPostalCode;
		}

		public void setZipOrPostalCode(String zipOrPostalCode) {
			this.zipOrPostalCode = zipOrPostalCode;
		}

		public String getPrimaryContactName() {
			return primaryContactName;
		}

		public void setPrimaryContactName(String primaryContactName) {
			this.primaryContactName = primaryContactName;
		}

		public String getPrimaryContactEmail() {
			return primaryContactEmail;
		}

		public void setPrimaryContactEmail(String primaryContactEmail) {
			this.primaryContactEmail = primaryContactEmail;
		}

		public String getPrimaryContactPhone() {
			return primaryContactPhone;
		}

		public void setPrimaryContactPhone(String primaryContactPhone) {
			this.primaryContactPhone = primaryContactPhone;
		}

		public Integer getNumberOfUsers() {
			return numberOfUsers;
		}

		public void setNumberOfUsers(Integer numberOfUsers) {
			this.numberOfUsers = numberOfUsers;
		}

		public Integer getNumberOfAssets() {
			return numberOfAssets;
		}

		public void setNumberOfAssets(Integer numberOfAssets) {
			this.numberOfAssets = numberOfAssets;
		}
	
		

}
