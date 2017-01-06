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
import com.iprofile.assets.domain.competency.Competency;
import com.iprofile.assets.domain.competency.ProficiencyIndicator;
import com.iprofile.assets.domain.employee.Employee;
import com.iprofile.assets.domain.employee.EmployeeRepository;
import com.iprofile.assets.domain.employee.QueryResultEmployee;
import com.iprofile.assets.domain.orgnode.OrgNode;
import com.iprofile.assets.domain.orgnode.OrganizationNodeRepository;
import com.iprofile.assets.domain.orgnode.QueryResultOrgNode;
import com.iprofile.assets.exceptions.BusinessNodeNotFoundException;
import com.iprofile.assets.exceptions.CorpNodeNotCreatedException;
import com.iprofile.assets.exceptions.CorpNodeNotFoundException;

@RestController
public class OrgNodeRestController {
	
	protected Logger logger = Logger.getLogger(OrgNodeRestController.class
			.getName());
	protected BusinessNodeAccountRepository bnaRepository;
	protected OrganizationNodeRepository orgNodeRepository;	
	protected EmployeeRepository employeeRepository;

	
	/**
	 * Create an instance plugging in the repository of Organization Node.
	 * 
	 * @param orgNodeRepository
	 *            A Organization Node repository implementation.
	 */
	@Autowired
	public OrgNodeRestController(BusinessNodeAccountRepository bnaRepository, 
			OrganizationNodeRepository orgNodeRepository, EmployeeRepository employeeRepository) {
		this.bnaRepository = bnaRepository;
		this.orgNodeRepository=orgNodeRepository;
		this.employeeRepository= employeeRepository;

		// logger.info("AccountRepository says system has "
			//	+ employeeRepository.countAccounts() + " accounts");
		
		logger.info("Org Node Repository");
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
	@RequestMapping("/asset/org_node_list_1")
	public List<OrgNode> bybnaId(@RequestParam(value="bna_id") Integer bnaId) {

		logger.info("asset-service for org node list by bna_Id invoked: " + bnaId);
		List<OrgNode> orgNodeList = orgNodeRepository.getOrgNodeListBelongingToBnaId(bnaId);
	

		if (orgNodeList == null)
			throw new BusinessNodeNotFoundException(bnaId);
		else {
			return orgNodeList;
		}
	}

	@RequestMapping("/asset/org_node_list")
	public List<OrgNode> orgNodeListByBnaId(@RequestParam(value="bna_id") Integer bnaId) {

		BusinessNodeAccount bna = bnaRepository.findByBnaNumber(bnaId);
		
		logger.info("asset-service for org node list by bna_Id invoked: " + bnaId);
		// List<Employee> employeeList = employeeRepository.getEmployeeListBelongingToBnaId(bnaId);
	
		List<QueryResultOrgNode> queryResultOrgNodeList = orgNodeRepository.getOrgNodeListForBnaId(bnaId);
		
		
	List<OrgNode> orgNodeList= new ArrayList<OrgNode>();

		if (queryResultOrgNodeList == null)
			throw new BusinessNodeNotFoundException(bnaId);
		else {
			
			for(int i=0;i<queryResultOrgNodeList.size();i++){
				// Long compProfId = Long.valueOf(-1);
				//Long leaderEmpId = Long.valueOf(-1);
				Long parentNodeId = null;
				Long leaderEmpId = null;
				
				QueryResultOrgNode queryResultOrgN = queryResultOrgNodeList.get(i);
				
				if(queryResultOrgN.getParentNode()!= null)
					parentNodeId= queryResultOrgN.getParentNode().getId();
				if(queryResultOrgN.getLeaderEmployee() != null)
				leaderEmpId= queryResultOrgN.getLeaderEmployee().getId();
				
				OrgNode orgN= queryResultOrgN.getOrgNode();
				orgN.setLeaderEmployeeId(leaderEmpId);
				orgN.setParentNodeId(parentNodeId);
				//.setLeaderEmployeeId(tt.getLeaderEmployee().getId());
				// tt.setCompetencyProfileId(tt.getCompetencyProfile().getId());
				// tt.setOrgNodeMemberOf(tt.get);;
				
				
				orgNodeList.add(orgN);
			
			}
		
			// return new ResponseEntity<List>(employeeList, HttpStatus.OK);
			
		return orgNodeList;
		}
	}

	
	
	
	
	//Create a New Business Account Node for a particular CNA Id
	
	@RequestMapping(value = "/asset/org_node", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrgNode> create(@RequestParam(value="bna_id") Integer bnaId,
			 @RequestBody OrgNode orgNode) {

		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
		// BusinessNodeAccount bna = bnaRepository.findOne(bnaId);
		BusinessNodeAccount bna = bnaRepository.findByBnaNumber(bnaId);
		
		orgNode.setBusinessNodeAccount(bna);
		
		OrgNode parentNode;
		Employee leaderEmployee;
		Long leaderEmployeeId = null;
		Long parentNodeId = null;
	
		
		if(orgNode.getParentNodeId()!=null){
		parentNode=orgNodeRepository.findOne(orgNode.getParentNodeId());
		orgNode.setParentNode(parentNode);
		parentNodeId=parentNode.getId();
		}
		
		if(orgNode.getLeaderEmployeeId()!=null){
		
		leaderEmployee =employeeRepository.findOne(orgNode.getLeaderEmployeeId());	
		orgNode.setLeader(leaderEmployee);
		leaderEmployeeId=leaderEmployee.getId();
		}
		
		OrgNode orgNodeCreated = orgNodeRepository.save(orgNode,1);
		

		if (orgNodeCreated == null)
			throw new CorpNodeNotCreatedException();
		else {
			orgNodeCreated.setParentNodeId(parentNodeId);
			orgNodeCreated.setLeaderEmployeeId(leaderEmployeeId);
			 return new ResponseEntity<OrgNode>(orgNodeCreated, HttpStatus.OK);
		}
	    
	   
	}


	
	   
	@RequestMapping(value = "/asset/org_node", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrgNode> update(@RequestParam(value="bna_id") Integer bnaId,
			 @RequestBody OrgNode orgNode) {

        BusinessNodeAccount bna = bnaRepository.findByBnaNumber(bnaId);
		
		orgNode.setBusinessNodeAccount(bna);
		
		OrgNode parentNode;
		Employee leaderEmployee;
		Long leaderEmployeeId = null;
		Long parentNodeId = null;
	
		
		if(orgNode.getParentNodeId()!=null){
		parentNode=orgNodeRepository.findOne(orgNode.getParentNodeId());
		orgNode.setParentNode(parentNode);
		parentNodeId=parentNode.getId();
		}
		
		if(orgNode.getLeaderEmployeeId()!=null){
		
		leaderEmployee =employeeRepository.findOne(orgNode.getLeaderEmployeeId());	
		orgNode.setLeader(leaderEmployee);
		leaderEmployeeId=leaderEmployee.getId();
		}
		
		OrgNode orgNodeCreated = orgNodeRepository.save(orgNode,1);
		

		if (orgNodeCreated == null)
			throw new CorpNodeNotCreatedException();
		else {
			orgNodeCreated.setParentNodeId(parentNodeId);
			orgNodeCreated.setLeaderEmployeeId(leaderEmployeeId);
			 return new ResponseEntity<OrgNode>(orgNodeCreated, HttpStatus.OK);
		}
	    
	   
	}

	

	@RequestMapping(value = "/asset/org_node/{assetId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrgNode> remove( 
			@PathVariable("assetId") Long assetId) {

		
		System.out.println("Ishaan in Delete 1.0");
		
		OrgNode objectToDelete = orgNodeRepository.findOne(assetId);
        if (objectToDelete == null) {
            System.out.println("Unable to delete OrgNode with id " + assetId + " not found");
            return new ResponseEntity<OrgNode>(HttpStatus.NOT_FOUND);
        }
        
        orgNodeRepository.delete(objectToDelete);

		return new ResponseEntity<OrgNode>(objectToDelete, HttpStatus.OK);
	   
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


