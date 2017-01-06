package com.iprofile.assets.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2ExpressionUtils;
import org.springframework.security.oauth2.provider.expression.OAuth2SecurityExpressionMethods;

/**
 * 
 * @author Bipul Das
 *
 */
public class CustomOAuth2SecurityExpressionMethods extends OAuth2SecurityExpressionMethods {

	private HttpServletRequest httpServletRequest;
	
	private OAuth2Authentication oAuth2Authentication;
	
	/**
	 * 
	 * @param authentication
	 */
	public CustomOAuth2SecurityExpressionMethods(Authentication authentication, HttpServletRequest httpServletRequest) {
		super(authentication);
		this.oAuth2Authentication = (OAuth2Authentication) authentication;
		this.httpServletRequest = httpServletRequest;
	}
	
	/**
	 * 
	 * @param docId
	 * @return
	 */
	public boolean validateCorporateNodeAccount(String entityType) {
		// int pos = this.httpServletRequest.getRequestURI().lastIndexOf('/');
		// String docId = this.httpServletRequest.getRequestURI().substring(pos+1);
		String cnaId=this.httpServletRequest.getParameter("cna_id");
		 String userId = this.oAuth2Authentication.getPrincipal().toString();
		String testScope="cna-business."+cnaId+".operation";
		System.out.println("Bipul Got Scope Defined for multitenancy " + testScope + " userId " + userId);
		String  tempScope="order.36758.me";
		boolean result = hasScope(tempScope);
		
		// useful to handle complex 'allow/deny' access test
		
		// return result;
		return true;
	
	}
	
}
