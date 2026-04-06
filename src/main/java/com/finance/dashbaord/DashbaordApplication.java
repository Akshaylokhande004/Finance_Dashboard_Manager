package com.finance.dashbaord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DashbaordApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashbaordApplication.class, args);
	}

}
