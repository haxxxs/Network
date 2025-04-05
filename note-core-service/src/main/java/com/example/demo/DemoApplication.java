package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory(".").load();
		System.out.println("Database: " + dotenv.get("POSTGRES_DB"));
		System.out.println("Username: " + dotenv.get("POSTGRES_USER"));
		System.out.println("Password: " + dotenv.get("POSTGRES_PASSWORD"));
		SpringApplication.run(DemoApplication.class, args);
	}
}
