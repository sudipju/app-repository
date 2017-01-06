package com.iprofile.assets.domain.employee;

import org.springframework.data.neo4j.annotation.QueryResult;

import com.iprofile.assets.domain.competency.CompetencyProfile;

@QueryResult
public class QueryResultEmployee {

        Employee employee;
        Employee  leaderEmployee;
        CompetencyProfile competencyProfile;
		public Employee getEmployee() {
			return employee;
		}
		public void setEmployee(Employee employee) {
			this.employee = employee;
		}
		public Employee getLeaderEmployee() {
			return leaderEmployee;
		}
		public void setLeaderEmployee(Employee leaderEmployee) {
			this.leaderEmployee = leaderEmployee;
		}
		public CompetencyProfile getCompetencyProfile() {
			return competencyProfile;
		}
		public void setCompetencyProfile(CompetencyProfile competencyProfile) {
			this.competencyProfile = competencyProfile;
		}
   

}
