package com.yota.puvn.minutesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MinutesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinutesServiceApplication.class, args);
	}

}
