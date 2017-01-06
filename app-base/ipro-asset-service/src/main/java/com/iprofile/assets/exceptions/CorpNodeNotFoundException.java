package com.iprofile.assets.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Allow the controller to return a 404 if an employee is not found by simply
 * throwing this exception. The @ResponseStatus causes Spring MVC to return a
 * 404 instead of the usual 500.
 * 
 * @author Bipul das
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CorpNodeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CorpNodeNotFoundException(Integer cnaId) {
		super("No such Corp Node: " + cnaId);
	}
}