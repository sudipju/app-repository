package com.iprofile.assets;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.iprofile.assets.exceptions.BusinessNodeNotFoundException;
import com.iprofile.assets.exceptions.CorpNodeNotCreatedException;
import com.iprofile.assets.exceptions.CorpNodeNotFoundException;


@RestController
public class BnaController {
	
	protected Logger logger = Logger.getLogger(BnaController.class
			.getName());
	protected BusinessNodeAccountRepository bnaRepository;
	protected CorporateNodeAccountRepository cnaRepository;

	
	/**
	 * Create an instance plugging in the repository of Employee.
	 * 
	 * @param bnaRepository
	 *            A Bna repository implementation.
	 */
	@Autowired
	public BnaController(BusinessNodeAccountRepository bnaRepository, CorporateNodeAccountRepository cnaRepository) {
		this.bnaRepository = bnaRepository;
		this.cnaRepository=cnaRepository;

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
	@PreAuthorize("#oauth2.hasScope('openid')")
	@RequestMapping("/asset/bna_list")
	public List<BusinessNodeAccount> bycnaId() {

		Integer cnaId=40500;
		logger.info("asset-service for bna list by cna_Id invoked: ");
		List<BusinessNodeAccount> bnaList = bnaRepository.getBnaListByCnaId(cnaId);
		

		if (bnaList == null)
			throw new BusinessNodeNotFoundException(cnaId);
		else {
			return bnaList;
		}
	}

	//Create a New Business Account Node for a particular CNA Id
	@PreAuthorize("#oauth2.hasScope('openid')")
	@RequestMapping(value = "/asset/bna", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BusinessNodeAccount> create( @RequestBody BusinessNodeAccount bna) {

		Integer cnaId=40500;
		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
		CorporateNodeAccount cna = cnaRepository.findByCorporateNodeId(cnaId);
		
		bna.setCorpNodeAccount(cna);
		
		BusinessNodeAccount bnaCreated = bnaRepository.save(bna);
		

		if (bnaCreated == null)
			throw new CorpNodeNotCreatedException();
		else {
			 return new ResponseEntity<BusinessNodeAccount>(bnaCreated, HttpStatus.OK);
		}
	    
	   
	}

	@RequestMapping(value = "/asset/bna", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BusinessNodeAccount> update( @RequestBody BusinessNodeAccount bna) {

		Integer cnaId=40500;
		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
		// CorporateNodeAccount cna = cnaRepository.findByCorporateNodeId(cnaId);
		
		// bna.setCorpNodeAccount(cna);
		
		BusinessNodeAccount bnaUpdated = bnaRepository.save(bna);
		

		if (bnaUpdated == null)
			throw new CorpNodeNotCreatedException();
		else {
			 return new ResponseEntity<BusinessNodeAccount>(bnaUpdated, HttpStatus.OK);
		}
	    
	   
	}

	
	@RequestMapping(value = "/asset/bna/{assetId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BusinessNodeAccount> remove(@PathVariable("assetId") Long assetId) {

		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
		// BusinessNodeAccount bna = bnaRepository.findByBusinessNodeId(bnaId);
		
		// compProfile.setBusinessNodeAccount(bna);
		//Bipul to write the algorithm for computing the row key bna+asset(compprofile)+uniqueid
		//compProfile.setCompetencyProfileRowKey("40500.comprofile.702");
		System.out.println("Ishaan in Delete 1.0");
		
		BusinessNodeAccount bnaToDelete = bnaRepository.findOne(assetId);
        if (bnaToDelete == null) {
            System.out.println("Unable to delete. User with id " + assetId + " not found");
            return new ResponseEntity<BusinessNodeAccount>(HttpStatus.NOT_FOUND);
        }
        
        bnaRepository.delete(bnaToDelete);

		return new ResponseEntity<BusinessNodeAccount>(bnaToDelete, HttpStatus.OK);
	   
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


