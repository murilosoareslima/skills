package com.ml.record;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RecordApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordApplication.class, args);
	}

}
