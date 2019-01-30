package com.voting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableCaching
@EnableWebMvc
@EnableAsync
@SpringBootApplication
public class VotingApplication extends SpringBootServletInitializer {
    private static final Logger log = LoggerFactory.getLogger(VotingApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(VotingApplication.class);
	}

	public static void main(String[] args) {
		if(! System.getenv().containsKey("VOTING_ROOT")) {
            log.error("NEEDED ENVIRONMENT VARIABLE 'VOTING_ROOT' IS NOT EXIST!!!");
			return;
		}
		SpringApplication.run(VotingApplication.class, args);
	}

}

