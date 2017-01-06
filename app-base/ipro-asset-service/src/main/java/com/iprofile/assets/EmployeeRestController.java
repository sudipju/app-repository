package com.iprofile.assets;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iprofile.assets.domain.bna.BusinessNodeAccount;
import com.iprofile.assets.domain.bna.BusinessNodeAccountRepository;
import com.iprofile.assets.domain.cna.CorporateNodeAccount;
import com.iprofile.assets.domain.cna.CorporateNodeAccountRepository;
import com.iprofile.assets.domain.competency.CompetencyProfile;
import com.iprofile.assets.domain.competency.CompetencyProfileRepository;
import com.iprofile.assets.domain.employee.Employee;
import com.iprofile.assets.domain.employee.EmployeeRepository;
import com.iprofile.assets.domain.employee.QueryResultEmployee;
import com.iprofile.assets.domain.orgnode.OrgNode;
import com.iprofile.assets.domain.orgnode.OrganizationNodeRepository;
import com.iprofile.assets.exceptions.BusinessNodeNotFoundException;
import com.iprofile.assets.exceptions.CorpNodeNotCreatedException;
import com.iprofile.assets.exceptions.CorpNodeNotFoundException;

@RestController
public class EmployeeRestController {
	
	protected Logger logger = Logger.getLogger(EmployeeRestController.class
			.getName());
	protected BusinessNodeAccountRepository bnaRepository;
	protected OrganizationNodeRepository orgNodeRepository;
	protected EmployeeRepository employeeRepository;
	protected CompetencyProfileRepository competencyProfileRepository;

	
	/**
	 * Create an instance plugging in the repository of Organization Node.
	 * 
	 * @param orgNodeRepository
	 *            A Organization Node repository implementation.
	 */
	@Autowired
	public EmployeeRestController(BusinessNodeAccountRepository bnaRepository, 
			OrganizationNodeRepository orgNodeRepository, 
			EmployeeRepository employeeRepository,
			CompetencyProfileRepository competencyProfileRepository) {
		this.bnaRepository = bnaRepository;
		this.orgNodeRepository=orgNodeRepository;
		this.employeeRepository=employeeRepository;
		this.competencyProfileRepository=competencyProfileRepository;

		// logger.info("AccountRepository says system has "
			//	+ employeeRepository.countAccounts() + " accounts");
		
		logger.info("Employee Repository");
	}
	

	/**
	 * Fetch the bna List with the specified cnaId.
	 * 
	 * @param     bnaId
	 *            An Integer in the form of 6785.
	 * @return The business Account Node if found.
	 * @throws BusNodeNotFoundException
	 *             If the string is not recognized.
	 */
	@RequestMapping("/asset/employee_list_1")
	public List<Employee> bybnaId(@RequestParam(value="bna_id") Integer bnaId) {

		BusinessNodeAccount bna = bnaRepository.findByBnaNumber(bnaId);
		
		logger.info("asset-service for org node list by bna_Id invoked: " + bnaId);
		List<Employee> employeeList = employeeRepository.getEmployeeListBelongingToBnaId(bnaId);
	
	//	List<Employee> employeeList = employeeRepository.findBy(bnaId);

		if (employeeList == null)
			throw new BusinessNodeNotFoundException(bnaId);
		else {
			
			for(int i=0;i<employeeList.size();i++){
				
				Employee emp = employeeList.get(i);
				Long tt= emp.getId();
				Employee e= employeeRepository.findOne(tt);
				Employee leaderEmp= e.getLeaderEmployee();
				CompetencyProfile cmpProfile = e.getCompetencyProfile();
				//.setLeaderEmployeeId(tt.getLeaderEmployee().getId());
				// tt.setCompetencyProfileId(tt.getCompetencyProfile().getId());
				// tt.setOrgNodeMemberOf(tt.get);;
				
				emp.setLeaderEmployeeId(leaderEmp.getId());
				emp.setCompetencyProfileId(cmpProfile.getId());
				//employeeList.set(i,e);
			
			}
		
			
			return employeeList;
		}
	}

	
	@RequestMapping("/asset/employee_list")
	public List<Employee> employeeListByBnaId(@RequestParam(value="bna_id") Integer bnaId) {

		BusinessNodeAccount bna = bnaRepository.findByBnaNumber(bnaId);
		
		logger.info("asset-service for org node list by bna_Id invoked: " + bnaId);
		// List<Employee> employeeList = employeeRepository.getEmployeeListBelongingToBnaId(bnaId);
	
		List<QueryResultEmployee> queryResultEmployeeList = employeeRepository.getEmployeeListForBnaId(bnaId);
		
		
	List<Employee> employeeList = new ArrayList<Employee>();

		if (queryResultEmployeeList == null)
			throw new BusinessNodeNotFoundException(bnaId);
		else {
			
			for(int i=0;i<queryResultEmployeeList.size();i++){
				// Long compProfId = Long.valueOf(-1);
				//Long leaderEmpId = Long.valueOf(-1);
				Long compProfId = null;
				Long leaderEmpId = null;
				
				QueryResultEmployee queryResultEmp = queryResultEmployeeList.get(i);
				
				if(queryResultEmp.getCompetencyProfile()!= null)
				compProfId= queryResultEmp.getCompetencyProfile().getId();
				if(queryResultEmp.getLeaderEmployee() != null)
				leaderEmpId= queryResultEmp.getLeaderEmployee().getId();
				
				Employee e= queryResultEmp.getEmployee();
				e.setLeaderEmployeeId(leaderEmpId);
				e.setCompetencyProfileId(compProfId);
				//.setLeaderEmployeeId(tt.getLeaderEmployee().getId());
				// tt.setCompetencyProfileId(tt.getCompetencyProfile().getId());
				// tt.setOrgNodeMemberOf(tt.get);;
				
				
				employeeList.add(e);
			
			}
		
			// return new ResponseEntity<List>(employeeList, HttpStatus.OK);
			
		return employeeList;
		}
	}

	
	
	//Create a New employee for a particular BNA Id
	
	@RequestMapping(value = "/asset/employee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> create(@RequestParam(value="bna_id") Integer bnaId,
			 @RequestBody Employee employee) {

		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
		BusinessNodeAccount bna = bnaRepository.findByBnaNumber(bnaId);
		employee.setBusinessNodeAccount(bna);
		Employee leaderEmployee;
		CompetencyProfile competencyProfile;
		
		Long leaderEmployeeId = Long.valueOf(-1);
		Long competencyProfileId = Long.valueOf(-1);
	
	//OrgNode orgNodeMemberOf=orgNodeRepository.findOne(employee.getOrNodeId)
		
		if(employee.getLeaderEmployeeId()!= null){
		    leaderEmployee = employeeRepository.findOne(employee.getLeaderEmployeeId());
		    employee.setLeaderEmployee(leaderEmployee);
		    leaderEmployeeId=leaderEmployee.getId();
		}
		if(employee.getCompetencyProfileId()!= null){		
		competencyProfile=competencyProfileRepository.findOne(employee.getCompetencyProfileId());
		employee.setCompetencyProfile(competencyProfile);
		competencyProfileId=competencyProfile.getId();
		
		}
		//can add the leader name here by calling the orgNodeRepository.save(orgNodeLeaderOf)
		// to think if this is the best approach
		
	//	employee.setOrgNodeMemberOf(orgNodeMemberOf);
			
		
		Employee employeeCreated = employeeRepository.save(employee);
		

		if (employeeCreated == null)
			throw new CorpNodeNotCreatedException();
		else {
			// employeeCreated.setCompetencyProfileId(employee.getCompetencyProfileId());
			// employeeCreated.setLeaderEmployeeId(employee.getLeaderEmployeeId());
			employee.setCompetencyProfileId(competencyProfileId);
			employee.setLeaderEmployeeId(leaderEmployeeId);
			 return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		}
	    
	   
	}
	
	
	
	   
	@RequestMapping(value = "/asset/employee", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> update(@RequestParam(value="bna_id") Integer bnaId,
			 @RequestBody Employee employee) {

		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
		// BusinessNodeAccount bna = bnaRepository.findOne(bnaId);
		BusinessNodeAccount bna = bnaRepository.findByBnaNumber(bnaId);
		employee.setBusinessNodeAccount(bna);
		Employee leaderEmployee;
		CompetencyProfile competencyProfile;
		Long leaderEmployeeId = Long.valueOf(-1);
		Long competencyProfileId = Long.valueOf(-1);
	
		Employee employeeFromDatabase=employeeRepository.findOne(employee.getId());
		
		//set GCM Token during update
		
				if(employeeFromDatabase.getGcmToken()!= null){
					employee.setGcmToken(employeeFromDatabase.getGcmToken());
				}
		
		if(employee.getLeaderEmployeeId()!= null){
		    leaderEmployee = employeeRepository.findOne(employee.getLeaderEmployeeId());
		    employee.setLeaderEmployee(leaderEmployee);
		    leaderEmployeeId=leaderEmployee.getId();
		}
		if(employee.getCompetencyProfileId()!= null){		
		competencyProfile=competencyProfileRepository.findOne(employee.getCompetencyProfileId());
		employee.setCompetencyProfile(competencyProfile);
		competencyProfileId=competencyProfile.getId();
		
		}
		
		
		
		Employee employeeUpdated = employeeRepository.save(employee);
		

		if (employeeUpdated == null)
			throw new CorpNodeNotCreatedException();
		else {
			employeeUpdated.setCompetencyProfileId(competencyProfileId);
			employeeUpdated.setLeaderEmployeeId(leaderEmployeeId);
			
			 return new ResponseEntity<Employee>(employeeUpdated, HttpStatus.OK);
		}
	    
		
	    
	   
	}

	

	@RequestMapping(value = "/asset/employee/{assetId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> remove( 
			@PathVariable("assetId") Long assetId) {

		
		System.out.println("Ishaan in Employee Delete 1.0");
		
		Employee objectToDelete = employeeRepository.findOne(assetId);
        if (objectToDelete == null) {
            System.out.println("Unable to delete OrgNode with id " + assetId + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        
        employeeRepository.delete(objectToDelete);

		return new ResponseEntity<Employee>(objectToDelete, HttpStatus.OK);
	   
	}




	/*
	@RequestMapping(path="/updateCna/{cnaId}", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	//RequestMapping(value = "/rest/user", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateCorpNodeAccountWithCnaId(@PathVariable("cnaId") String cnaId, @RequestBody String gcmToken) {

		CorporateNodeAccount cna = cnaRepository.findByCorporateNodeId(cnaId);
	   //copyNonNullProperties(user, existing);
		employee.setGcmRegistrationToken(gcmToken);
		employeeRepository.save(employee);

		ResponseEntity<String> responseEntity = new ResponseEntity<>("response body gcm token update",
                HttpStatus.OK);
       return responseEntity;
	}
	
	*/
}


