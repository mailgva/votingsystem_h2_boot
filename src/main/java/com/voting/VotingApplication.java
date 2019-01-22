package com.voting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableCaching
@EnableWebMvc
@SpringBootApplication
public class VotingApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(VotingApplication.class);
	}

	public static void main(String[] args) throws Exception {
		if(! System.getenv().containsKey("VOTING_ROOT")) {
			System.out.println("ERROR!!! \nNEEDED ENVIRONMENT VARIABLE 'VOTING_ROOT' IS NOT EXIST!!!");
			return;
		}

		SpringApplication.run(VotingApplication.class, args);
	}

}

