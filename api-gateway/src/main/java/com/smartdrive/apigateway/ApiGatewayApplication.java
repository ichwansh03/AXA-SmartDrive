package com.smartdrive.apigateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ApiGatewayApplication.class).web(WebApplicationType.REACTIVE).run(args);
	}

}
