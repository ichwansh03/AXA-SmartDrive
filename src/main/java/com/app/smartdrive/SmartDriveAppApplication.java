package com.app.smartdrive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmartDriveAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartDriveAppApplication.class, args);
	}

}
