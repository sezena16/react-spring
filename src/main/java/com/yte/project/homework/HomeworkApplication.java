package com.yte.project.homework;

import com.yte.project.homework.manageusers.util.DatabasePopulator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class HomeworkApplication {

	private final DatabasePopulator databasePopulator;

	public static void main(String[] args) {
		SpringApplication.run(HomeworkApplication.class, args);
	}

	@PostConstruct
	public void initSecurityData() {
		databasePopulator.populateDatabaseAfterInit();
	}
}
