package com.iprofile.assets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
import com.iprofile.assets.domain.competency.CompetencyProficiencyIndicator;
import com.iprofile.assets.domain.competency.CompetencyProfile;
import com.iprofile.assets.domain.competency.CompetencyProfileRepository;
import com.iprofile.assets.domain.competency.CompetencyRepository;
import com.iprofile.assets.domain.competency.ProficiencyIndicator;
import com.iprofile.assets.domain.competency.ProficiencyIndicatorRepository;
import com.iprofile.assets.exceptions.BusinessNodeNotFoundException;
import com.iprofile.assets.exceptions.CorpNodeNotCreatedException;
import com.iprofile.assets.exceptions.CorpNodeNotFoundException;

@RestController
public class ProficiencyIndicatorRestController {
	
	protected Logger logger = Logger.getLogger(ProficiencyIndicatorRestController.class
			.getName());

	protected CompetencyRepository competencyRepository;
	protected ProficiencyIndicatorRepository proficiencyIndicatorRepository;

	
	/**
	 * Create an instance plugging in the repository of Employee.
	 * 
	 * @param bnaRepository
	 *            A Bna repository implementation.
	 */
	@Autowired
	public ProficiencyIndicatorRestController(CompetencyRepository competencyRepository, 
			ProficiencyIndicatorRepository proficiencyIndicatorRepository) {
		this.proficiencyIndicatorRepository = proficiencyIndicatorRepository;
		this.competencyRepository=competencyRepository;
		

		// logger.info("AccountRepository says system has "
			//	+ employeeRepository.countAccounts() + " accounts");
		
		
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
	@RequestMapping("/asset/list_proficiency_indicator")
	public List<ProficiencyIndicator> byCompetencyAndLevel(@RequestParam(value="competency_id") Long competencyId
			, @RequestParam(value="proficiency_level") Integer profLevel) {

		
		List<ProficiencyIndicator> profIndicatorList = proficiencyIndicatorRepository.getProfIndicatorListByCompetencyAndLevel(profLevel,competencyId);
		

		if (profIndicatorList == null)
			throw new BusinessNodeNotFoundException(profLevel);
		else {
			return profIndicatorList;
		}
	}

	//Create a New Business Account Node for a particular CNA Id
	
	@RequestMapping(value = "/asset/proficiency_indicator", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProficiencyIndicator> create(@RequestParam(value="competency_id") Long competencyId,
			 @RequestBody ProficiencyIndicator profIndicator){

		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
		Competency competency = competencyRepository.findOne(competencyId);
	
		
		profIndicator.setCompetency(competency);
		
		
		ProficiencyIndicator profIndicatorCreated = proficiencyIndicatorRepository.save(profIndicator);
		

		if (profIndicatorCreated == null)
			throw new CorpNodeNotCreatedException();
		else {
			 return new ResponseEntity<ProficiencyIndicator>(profIndicatorCreated, HttpStatus.OK);
		}
	    
	   
	}


	@RequestMapping(value = "/asset/proficiency_indicator", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProficiencyIndicator> update(@RequestParam(value="competency_id") Long competencyId,
			 @RequestBody ProficiencyIndicator profIndicator){

		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
		Competency competency = competencyRepository.findOne(competencyId);
	
		
		profIndicator.setCompetency(competency);
		
		
		ProficiencyIndicator profIndicatorUpdated = proficiencyIndicatorRepository.save(profIndicator);
		

		if (profIndicatorUpdated == null)
			throw new CorpNodeNotCreatedException();
		else {
			 return new ResponseEntity<ProficiencyIndicator>(profIndicatorUpdated, HttpStatus.OK);
		}
	    
	   
	}
	
	

	@RequestMapping(value = "/asset/proficiency_indicator/{assetId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProficiencyIndicator> remove(@RequestParam(value="competency_id") Integer competencyId, 
			@PathVariable("assetId") Long assetId) {

		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
		// BusinessNodeAccount bna = bnaRepository.findByBusinessNodeId(bnaId);
		
		// compProfile.setBusinessNodeAccount(bna);
		//Bipul to write the algorithm for computing the row key bna+asset(compprofile)+uniqueid
		//compProfile.setCompetencyProfileRowKey("40500.comprofile.702");
		System.out.println("Ishaan in Delete 1.0");
		
		ProficiencyIndicator objectToDelete = proficiencyIndicatorRepository.findOne(assetId);
        if (objectToDelete == null) {
            System.out.println("Unable to delete. User with id " + assetId + " not found");
            return new ResponseEntity<ProficiencyIndicator>(HttpStatus.NOT_FOUND);
        }
        
        proficiencyIndicatorRepository.delete(objectToDelete);

		return new ResponseEntity<ProficiencyIndicator>(objectToDelete, HttpStatus.OK);
	   
	}


/*	
	
	@RequestMapping(value = "/asset/proficiency_indicator", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<ProficiencyIndicator> updateList(@RequestParam(value="competency_id") Long competencyId,
			 @RequestBody List<ProficiencyIndicator> profIndicators){

		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
		Competency competency = competencyRepository.findOne(competencyId);
		
		ArrayList<ProficiencyIndicator> profIndicatorList=new ArrayList<>();
		
		for (int i = 0; i < profIndicators.size(); i++) {
		
		ProficiencyIndicator profIndicator=new ProficiencyIndicator();
		profIndicator.setCompetency(competency);
		ProficiencyIndicator profIndicatorUpdated = proficiencyIndicatorRepository.save(profIndicator);		
		profIndicatorList.add(profIndicatorUpdated);
		
		}

		if (profIndicatorCreated == null)
			throw new CorpNodeNotCreatedException();
		else {
			 return new ResponseEntity<ProficiencyIndicator>(profIndicatorCreated, HttpStatus.OK);
		}
	 
		return profIndicatorList;
	   
	}

	*/
	
	@RequestMapping("/list_competency_indicator")
	public List<CompetencyProficiencyIndicator> byEmployeeRowKeyAndLevel(@RequestParam(value="employee_rowkey") String employeeRowKey
			, @RequestParam(value="proficiency_level") Integer profLevel) {

		
		
		List<CompetencyProficiencyIndicator> listCompetencyProfIndicator = proficiencyIndicatorRepository.getCompetencyProfIndicatorByEmployeeRowKey(profLevel,profLevel+1,employeeRowKey);
		

		if (listCompetencyProfIndicator == null)
			throw new BusinessNodeNotFoundException(profLevel);
		else {
			return listCompetencyProfIndicator;
		}
	}
	
	
	@RequestMapping("/list_competency_indicator_not_main")
	public List<ProficiencyIndicator> byEmployeeAndLevel(@RequestParam(value="employee_rowkey") String employeeRowKey
			, @RequestParam(value="proficiency_level") Integer profLevel) {

		
		List<ProficiencyIndicator> profIndicatorList = proficiencyIndicatorRepository.getProfIndicatorListByEmployeeRowKeyAndLevel(profLevel,employeeRowKey);
		

		if (profIndicatorList == null)
			throw new BusinessNodeNotFoundException(profLevel);
		else {
			return profIndicatorList;
		}
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


