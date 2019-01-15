package com.slonsystems.itemsOrg.AuthMicroservice;

import com.slonsystems.itemsOrg.AuthMicroservice.config.ApplicationConfig;
import com.slonsystems.itemsOrg.AuthMicroservice.config.JpaConfig;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class ApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(new Class<?>[] {ApplicationStarter.class, ApplicationConfig.class, JpaConfig.class}, args);
	}

}

