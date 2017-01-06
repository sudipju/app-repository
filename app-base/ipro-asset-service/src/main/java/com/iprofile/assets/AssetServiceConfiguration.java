package com.iprofile.assets;


import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@EnableNeo4jRepositories(basePackages = "com.iprofile.assets")
@Configuration
@EnableTransactionManagement
public class AssetServiceConfiguration extends Neo4jConfiguration {
	
	 @Bean
	 public SessionFactory getSessionFactory() {
	 return new SessionFactory(
			 "com.iprofile.assets.domain.cna", "com.iprofile.assets.domain.bna",
			 "com.iprofile.assets.domain.orgnode","com.iprofile.assets.domain.employee",
			 "com.iprofile.assets.domain.competency");
	 }
	 
 
	// needed for session in view in web-applications
	    @Bean
	    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	    public Session getSession() throws Exception {
	        return super.getSession();
	    }
}

