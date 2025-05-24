package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(scanBasePackages = "com.example",exclude = {SecurityAutoConfiguration.class}) // Or your appropriate base package
@SpringBootApplication(scanBasePackages = "com.example",exclude = {
		org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
		org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
		org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration.class
}) // Or your appropriate base package
//@EnableJpaRepositories("com.example.repo") // matches UserRepository
@EntityScan("com.example.entity") // matches User class


public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
