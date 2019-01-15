package com.voting;

import com.voting.configuration.MethodSecurityConfig;
import com.voting.configuration.PersistenceJPAConfig;
import com.voting.configuration.SecurityConfig;
import com.voting.configuration.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableCaching
@EnableWebMvc
@EntityScan(basePackages = "com.voting.model")

@SpringBootApplication
public class VotingApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(VotingApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(VotingApplication.class, args);
	}

}

