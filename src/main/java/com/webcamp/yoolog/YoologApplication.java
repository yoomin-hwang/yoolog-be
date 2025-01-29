package com.webcamp.yoolog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.webcamp.yoolog")
public class YoologApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoologApplication.class, args);
	}

}
