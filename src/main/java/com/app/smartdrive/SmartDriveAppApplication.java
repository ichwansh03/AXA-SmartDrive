package com.app.smartdrive;

import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.services.service_order.claims.ClaimAssetService;
import com.app.smartdrive.api.services.service_order.claims.ClaimAssetServiceImpl;
import jakarta.annotation.Resource;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.nio.file.Files;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableTransactionManagement
public class SmartDriveAppApplication implements CommandLineRunner {

	@Autowired
	ClaimAssetService claimAssetService;

	public static void main(String[] args) {
		SpringApplication.run(SmartDriveAppApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		claimAssetService.init();

	}
}
