package com.app.smartdrive;

import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class SmartDriveAppApplication implements CommandLineRunner {

	@Resource
	PartnerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SmartDriveAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//repository.deleteAll();
	}
}
