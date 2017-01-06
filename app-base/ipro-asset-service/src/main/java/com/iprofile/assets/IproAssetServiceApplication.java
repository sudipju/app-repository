package com.iprofile.assets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableCaching
public class IproAssetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IproAssetServiceApplication.class, args);
	}
}
