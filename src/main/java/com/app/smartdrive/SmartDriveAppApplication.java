package com.app.smartdrive;

import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import jakarta.annotation.Resource;
import org.jobrunr.configuration.JobRunr;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmartDriveAppApplication implements CommandLineRunner {

	@Resource
	PartnerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SmartDriveAppApplication.class, args);

		JobRunr.configure()
				.useStorageProvider(new InMemoryStorageProvider())
				.useBackgroundJobServer()
				.useDashboard(8080)
				.initialize();
	}

	@Override
	public void run(String... args) throws Exception {
		//repository.deleteAll();
	}
}
