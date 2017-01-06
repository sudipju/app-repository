package com.iprofile.assets.domain.competency;

import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.QueryResult;


	
	@QueryResult
	public class CompetencyProficiencyIndicator {
	    Competency competency;
	  //  ProficiencyIndicator proficiencyIndicator;
		Set<ProficiencyIndicator> listProficiencyIndicator;
	    
	    
	    public CompetencyProficiencyIndicator() {
			
		}
		public CompetencyProficiencyIndicator(Competency competency, Set<ProficiencyIndicator> listProficiencyIndicator) {
			
			this.competency = competency;
			this.listProficiencyIndicator = listProficiencyIndicator;
		}
		public Competency getCompetency() {
			return competency;
		}
		public void setCompetency(Competency competency) {
			this.competency = competency;
		}
		public Set<ProficiencyIndicator> getListProficiencyIndicator() {
			return listProficiencyIndicator;
		}
		public void setListProficiencyIndicator(Set<ProficiencyIndicator> listProficiencyIndicator) {
			this.listProficiencyIndicator = listProficiencyIndicator;
		}
		


}
