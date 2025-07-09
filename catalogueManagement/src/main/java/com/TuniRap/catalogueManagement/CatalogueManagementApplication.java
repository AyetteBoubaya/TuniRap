package com.TuniRap.catalogueManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class CatalogueManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogueManagementApplication.class, args);
	}

}
