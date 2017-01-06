package com.iprofile.assets;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iprofile.assets.domain.cna.CorporateNodeAccount;
import com.iprofile.assets.domain.cna.CorporateNodeAccountRepository;
import com.iprofile.assets.exceptions.CorpNodeNotCreatedException;
import com.iprofile.assets.exceptions.CorpNodeNotFoundException;

@RestController
public class CnaController {
	
	protected Logger logger = Logger.getLogger(CnaController.class
			.getName());
	protected CorporateNodeAccountRepository cnaRepository;

	
	/**
	 * Create an instance plugging in the repository of Employee.
	 * 
	 * @param employeeRepository
	 *            An employee repository implementation.
	 */
	@Autowired
	public CnaController(CorporateNodeAccountRepository cnaRepository) {
		this.cnaRepository = cnaRepository;

		// logger.info("AccountRepository says system has "
			//	+ employeeRepository.countAccounts() + " accounts");
		
		logger.info("CNA Repository has");
	}
	

	/**
	 * Fetch a cna with the specified cnaId.
	 * 
	 * @param     cnaId
	 *            A String in the form of 4567.Resource.6785.
	 * @return The employee if found.
	 * @throws CorpNodeNotFoundException
	 *             If the string is not recognized.
	 */
	@RequestMapping("/cna/{cnaId}")
	public CorporateNodeAccount bycnaId(@PathVariable("cnaId") Integer cnaId) {

		logger.info("asset-service for cna by cna Id invoked: " + cnaId);
		CorporateNodeAccount cna = cnaRepository.findByCorporateNodeId(cnaId);
		

		if (cna == null)
			throw new CorpNodeNotFoundException(cnaId);
		else {
			return cna;
		}
	}

	//Create a New Corporate Account Node
	
	@RequestMapping(value = "/cna", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CorporateNodeAccount> create(@RequestBody CorporateNodeAccount cna) {

		// TODO: call persistence layer to update directly or send it to a queue RabbitMQ to persist data asynchronously
	        
		CorporateNodeAccount cnaCreated = cnaRepository.save(cna);
		

		if (cnaCreated == null)
			throw new CorpNodeNotCreatedException();
		else {
			 return new ResponseEntity<CorporateNodeAccount>(cnaCreated, HttpStatus.OK);
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


