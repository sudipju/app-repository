package com.iprofile.assets.config;

import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;

/**
 * 
 * @author Bipul Das
 *
 */
public class CustomOAuth2WebSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler  {

	@Override
	protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication,
			FilterInvocation invocation) {
		StandardEvaluationContext ec = super.createEvaluationContextInternal(authentication, invocation);
		ec.setVariable("oauth2c", new CustomOAuth2SecurityExpressionMethods(authentication, invocation.getHttpRequest()));
		return ec;
	}
}