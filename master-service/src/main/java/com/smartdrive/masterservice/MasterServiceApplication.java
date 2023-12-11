package com.smartdrive.masterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MasterServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MasterServiceApplication.class, args);
	}
}
