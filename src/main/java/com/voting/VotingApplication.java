package com.voting;

import com.voting.service.DailyMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;

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

		ApplicationContext context = SpringApplication.run(VotingApplication.class, args);
		DailyMenuService service = context.getBean(DailyMenuService.class);
		//generateDailyMenuForFewDays(service);
	}


	private static void generateDailyMenuForFewDays(DailyMenuService service) {
		LocalDate localDate = LocalDate.now();
		service.generateDailyMenu(localDate.minusDays(1));
		service.generateDailyMenu(localDate);
		service.generateDailyMenu(localDate.plusDays(1));
		service.generateDailyMenu(localDate.plusDays(2));
	}



}

