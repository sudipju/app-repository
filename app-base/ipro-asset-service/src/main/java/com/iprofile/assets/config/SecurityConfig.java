package com.iprofile.assets.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * Created by Bipul Das
 */
@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig implements ResourceServerConfigurer {

	SecurityProperties securityProperties;
	
	@Autowired
	public SecurityConfig(SecurityProperties securityProperties){
		this.securityProperties=securityProperties;
	}
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
       // resources.resourceId("iprofile-asset-manager");
    	resources.resourceId("mobile-app");
    	
    	//aadded custom web security expression handler
    	 resources.expressionHandler(new CustomOAuth2WebSecurityExpressionHandler());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
    
     // in case does not work - need to review this with will tran microservices security
    	 if (this.securityProperties.isRequireSsl()) {
             http.requiresChannel().anyRequest().requiresSecure();
         }
    // antMatchers("/documents/**").access("#oauth2.hasScopeMatching('documents.*.read') and #oauth2c.canRequestThis('doc')")	
        http
            .httpBasic().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .anonymous()
            .and()
           .authorizeRequests().antMatchers("/asset/**").access("#oauth2.hasScope('openid') and #oauth2c.validateCorporateNodeAccount('doc')")
            /*.anyRequest().permitAll()*/
            .and()
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

/*
    @Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4000"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

*/
    
    
}

