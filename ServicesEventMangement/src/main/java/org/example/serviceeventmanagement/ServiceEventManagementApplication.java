package org.example.serviceeventmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients

@SpringBootApplication
public class ServiceEventManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceEventManagementApplication.class, args);
	}

}
