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
import com.iprofile.assets.domain.competency.ProficiencyIndicator;
import com.iprofile.assets.exceptions.BusinessNodeNotFoundException;
import com.iprofile.assets.exceptions.CorpNodeNotCreatedException;
import com.iprofile.assets.exceptions.CorpNodeNotFoundException;

@RestController
public class CompetencyProfileRestController {
	
	protected Logger logger = Logger.getLogger(CompetencyProfileRestController.class
			.getName());
	protected BusinessNodeAccountRepository bnaRepository;
	protected CompetencyProfileRepository compProfileRepository;
	protected CompetencyRepository competencyRepository;

	
	/**
	 * Create an instance plugging in the repository of Employee.
	 * 
	 * @param bnaRepository
	 *            A Bna repository implementation.
	 */
	@Autowired
	public CompetencyProfileRestController(BusinessNodeAccountRepository bnaRepository, 
			CompetencyProfileRepository compProfileRepository, CompetencyRepository competencyRepository) {
		this.bnaRepository = bnaRepository;
		this.compProfileRepository=compProfileRepository;
		this.competencyRepository=competencyRepository;

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
	@RequestMapping("/asset/competency_profile_list")
	public List<CompetencyProfile> bycnaId(@RequestParam(value="bna_id") Integer bnaId) {

		logger.info("asset-serice for comp profile list by bna_Id invoked: " + bnaId);
		List<CompetencyProfile> compProfileList = compProfileRepository.getCompetencyProfileListApplyingToBnaId(bnaId);
		

		if (compProfileList == null)
			throw new BusinessNodeNotFoundException(bnaId);
		else {
			return compProfileList;
		}
	}

	//Create a New Business Account Node for a particular CNA Id
	
	@RequestMapping(value = "/asset/competency_profile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CompetencyProfile> create(@RequestParam(value="bna_id") Integer bnaId, @RequestBody CompetencyProfile compProfile) {

		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
		BusinessNodeAccount bna = bnaRepository.findByBnaNumber(bnaId);
		
		compProfile.setBusinessNodeAccount(bna);
		//Bipul to write the algorithm for computing the row key bna+asset(compprofile)+uniqueid
		compProfile.setCompetencyProfileRowKey("40500.comprofile.702");
		
		CompetencyProfile compProfileCreated = compProfileRepository.save(compProfile);
		

		if (compProfileCreated == null)
			throw new CorpNodeNotCreatedException();
		else {
			 return new ResponseEntity<CompetencyProfile>(compProfileCreated, HttpStatus.OK);
		}
	    
	   
	}


	//Create a New Business Account Node for a particular CNA Id
	
		@RequestMapping(value = "/asset/competency_profile", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<CompetencyProfile> update(@RequestParam(value="bna_id") Integer bnaId, @RequestBody CompetencyProfile compProfile) {

			// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
			// BusinessNodeAccount bna = bnaRepository.findByBusinessNodeId(bnaId);
			
			// compProfile.setBusinessNodeAccount(bna);
			//Bipul to write the algorithm for computing the row key bna+asset(compprofile)+uniqueid
			//compProfile.setCompetencyProfileRowKey("40500.comprofile.702");
			
			CompetencyProfile compProfileCreated = compProfileRepository.save(compProfile);
			

			if (compProfileCreated == null)
				throw new CorpNodeNotCreatedException();
			else {
				 return new ResponseEntity<CompetencyProfile>(compProfileCreated, HttpStatus.OK);
			}
		    
		   
		}


		@RequestMapping(value = "/asset/competency_profile/{assetId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<CompetencyProfile> remove(@RequestParam(value="bna_id") Integer bnaId, 
				@PathVariable("assetId") Long assetId) {

			// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
			// BusinessNodeAccount bna = bnaRepository.findByBusinessNodeId(bnaId);
			
			// compProfile.setBusinessNodeAccount(bna);
			//Bipul to write the algorithm for computing the row key bna+asset(compprofile)+uniqueid
			//compProfile.setCompetencyProfileRowKey("40500.comprofile.702");
			System.out.println("Ishaan in Delete 1.0");
			
			CompetencyProfile compProfileToDelete = compProfileRepository.findOne(assetId);
	        if (compProfileToDelete == null) {
	            System.out.println("Unable to delete. User with id " + assetId + " not found");
	            return new ResponseEntity<CompetencyProfile>(HttpStatus.NOT_FOUND);
	        }
	        
			compProfileRepository.delete(compProfileToDelete);

			return new ResponseEntity<CompetencyProfile>(compProfileToDelete, HttpStatus.OK);
		   
		}


		@RequestMapping("/asset/competency_relation_list")
		public List<Competency> competencyListByCompetencyProfile(@RequestParam
				(value="competency_profile_id") Long competencyProfileId) {

			
			List<Competency> competencyList = competencyRepository.getCompetencyListByCompetencyProfileId(competencyProfileId);
			

			if (competencyList == null)
				throw new BusinessNodeNotFoundException(5);
			else {
				return competencyList;
			}
		}
	
		
		//Add a new Competency Relation
		
		@RequestMapping(value = "/asset/competency_relation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Competency> createCompetencyProfileRelation(@RequestParam(value="competency_profile_id") Long competencyProfileId,
				 @RequestBody Competency competency){

			// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
			//In case someone else might have changed competency
			
			//use the following method when autocomplete is done. else use the next method to find the competency
			// Competency comp = competencyRepository.findOne(competency.getId());
			Competency comp = competencyRepository.findByCompetencyName(competency.getCompetencyName());
		    CompetencyProfile compProfile= compProfileRepository.findOne(competencyProfileId);
			
			comp.setCompetencyProfile(compProfile);
			
			
			Competency competencyUpdated = competencyRepository.save(comp);
			

			if (competencyUpdated == null)
				throw new CorpNodeNotCreatedException();
			else {
				 return new ResponseEntity<Competency>(competencyUpdated, HttpStatus.OK);
			}
		    
		   
		}


		@RequestMapping(value = "/asset/competency_relation/{assetId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Competency> removeCompetencyProfileRelation(@RequestParam(value="competency_profile_id") Long competencyProfileId, 
				@PathVariable("assetId") Long assetId) {

			// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
			// BusinessNodeAccount bna = bnaRepository.findByBusinessNodeId(bnaId);
			
			// compProfile.setBusinessNodeAccount(bna);
			//Bipul to write the algorithm for computing the row key bna+asset(compprofile)+uniqueid
			//compProfile.setCompetencyProfileRowKey("40500.comprofile.702");
			System.out.println("Ishaan in Delete 1.0");
			
			Competency comp = competencyRepository.findOne(assetId);
	        if (comp == null) {
	            System.out.println("Unable to delete. User with id " + assetId + " not found");
	            return new ResponseEntity<Competency>(HttpStatus.NOT_FOUND);
	        }
	        
	        comp.setCompetencyProfile(null);
	        
	        Competency competencyUpdated = competencyRepository.save(comp);

			return new ResponseEntity<Competency>(competencyUpdated, HttpStatus.OK);
		   
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


