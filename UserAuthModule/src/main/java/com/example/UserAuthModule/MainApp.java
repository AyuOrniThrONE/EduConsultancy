package com.example.UserAuthModule;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.UserAuthModule.entity")
@EnableJpaRepositories(basePackages = "com.example.UserAuthModule.repository")
@ComponentScan(basePackages = "com.example.UserAuthModule")
public class MainApp {
	public static void main(String[] args){  
		SpringApplication.run(MainApp.class, args);  
	}  
}
