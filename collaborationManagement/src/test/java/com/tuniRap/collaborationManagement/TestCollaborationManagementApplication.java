package com.tuniRap.collaborationManagement;

import org.springframework.boot.SpringApplication;

public class TestCollaborationManagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(CollaborationManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
