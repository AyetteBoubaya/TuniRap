package com.tuniRap.collaborationManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CollaborationManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollaborationManagementApplication.class, args);
	}

}
