package com.future.sm.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.future.sm")
public class SpaceMigrationManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceMigrationManagerApplication.class, args);
	}

}
