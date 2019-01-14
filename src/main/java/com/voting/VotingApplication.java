package com.voting;

import com.voting.configuration.MethodSecurityConfig;
import com.voting.configuration.PersistenceJPAConfig;
import com.voting.configuration.SecurityConfig;
import com.voting.configuration.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableCaching
@SpringBootApplication
@ComponentScan("com.voting.*")
//@Import({SecurityConfig.class, MethodSecurityConfig.class, PersistenceJPAConfig.class, WebMvcConfig.class})
public class VotingApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingApplication.class, args);
	}

}

