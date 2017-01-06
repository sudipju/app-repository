package com.iprofile.assets;

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
import com.iprofile.assets.domain.competency.CompetencyProfile;
import com.iprofile.assets.domain.competency.CompetencyProfileRepository;
import com.iprofile.assets.domain.competency.CompetencyRepository;
import com.iprofile.assets.exceptions.BusinessNodeNotFoundException;
import com.iprofile.assets.exceptions.CorpNodeNotCreatedException;
import com.iprofile.assets.exceptions.CorpNodeNotFoundException;

@RestController
public class CompetencyRestController {
	
	protected Logger logger = Logger.getLogger(CompetencyRestController.class
			.getName());
	protected BusinessNodeAccountRepository bnaRepository;
	protected CompetencyRepository competencyRepository;
	protected CompetencyProfileRepository compProfileRepository;

	
	/**
	 * Create an instance plugging in the repository of Employee.
	 * 
	 * @param bnaRepository
	 *            A Bna repository implementation.
	 */
	@Autowired
	public CompetencyRestController(BusinessNodeAccountRepository bnaRepository, CompetencyRepository competencyRepository
			,CompetencyProfileRepository compProfileRepository) {
		this.bnaRepository = bnaRepository;
		this.competencyRepository=competencyRepository;
		this.compProfileRepository=compProfileRepository;

		// logger.info("AccountRepository says system has "
			//	+ employeeRepository.countAccounts() + " accounts");
		
		logger.info("BNA Repository");
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
	@RequestMapping("/asset/competency_list")
	public List<Competency> bycnaId(@RequestParam(value="bna_id") Integer bnaId) {
		logger.info("asset-serice for comp profile list by bna_Id invoked: " + bnaId);
		List<Competency> competencyList = competencyRepository.getCompetencyListApplyingToBnaId(bnaId);
		

		if (competencyList == null)
			throw new BusinessNodeNotFoundException(bnaId);
		else {
			return competencyList;
		}
	}

	//Create a New Business Account Node for a particular CNA Id
	
	@RequestMapping(value = "/asset/competency", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Competency> create(@RequestParam(value="bna_id") Integer bnaId,
			@RequestBody Competency competency){

		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
		BusinessNodeAccount bna = bnaRepository.findByBnaNumber(bnaId);
		// CompetencyProfile compProfile=compProfileRepository.findByCompetencyProfileRowKey(profileRowKey);
		
		competency.setBusinessNodeAccount(bna);
		// competency.setCompetencyProfile(compProfile);
		
		Competency competencyCreated = competencyRepository.save(competency);
		

		if (competencyCreated == null)
			throw new CorpNodeNotCreatedException();
		else {
			 return new ResponseEntity<Competency>(competencyCreated, HttpStatus.OK);
		}
	    
	   
	}
	
	

	//Create a New Business Account Node for a particular CNA Id
	
		@RequestMapping(value = "/asset/competency", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Competency> update(@RequestParam(value="bna_id") Integer bnaId, @RequestBody Competency comp) {

			// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
			// BusinessNodeAccount bna = bnaRepository.findByBusinessNodeId(bnaId);
			
			// compProfile.setBusinessNodeAccount(bna);
			//Bipul to write the algorithm for computing the row key bna+asset(compprofile)+uniqueid
			//compProfile.setCompetencyProfileRowKey("40500.comprofile.702");
			
			Competency compCreated = competencyRepository.save(comp);
			

			if (compCreated == null)
				throw new CorpNodeNotCreatedException();
			else {
				 return new ResponseEntity<Competency>(compCreated, HttpStatus.OK);
			}
		    
		   
		}


		@RequestMapping(value = "/asset/competency/{assetId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Competency> remove(@RequestParam(value="bna_id") Integer bnaId, 
				@PathVariable("assetId") Long assetId) {

			// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
			// BusinessNodeAccount bna = bnaRepository.findByBusinessNodeId(bnaId);
			
			// compProfile.setBusinessNodeAccount(bna);
			//Bipul to write the algorithm for computing the row key bna+asset(compprofile)+uniqueid
			//compProfile.setCompetencyProfileRowKey("40500.comprofile.702");
			System.out.println("Ishaan in Delete 1.0");
			
			Competency compToDelete = competencyRepository.findOne(assetId);
	        if (compToDelete == null) {
	            System.out.println("Unable to delete. User with id " + assetId + " not found");
	            return new ResponseEntity<Competency>(HttpStatus.NOT_FOUND);
	        }
	        
			competencyRepository.delete(compToDelete);

			return new ResponseEntity<Competency>(compToDelete, HttpStatus.OK);
		   
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


