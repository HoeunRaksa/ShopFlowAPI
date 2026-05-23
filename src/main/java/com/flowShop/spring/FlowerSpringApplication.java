package com.flowShop.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class FlowerSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowerSpringApplication.class, args);
    }

    @Bean
    CommandLineRunner testConnection(DataSource dataSource) {
        return args -> {
            System.out.println("--- 🔍 កំពុងតេស្តការភ្ជាប់ទៅកាន់ DATABASE ---");
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("✅ DATABASE CONNECTION SUCCESSFUL!");
                System.out.println("Connected to: " + conn.getMetaData().getURL());
                System.out.println("-----------------------------------------");
            } catch (Exception e) {
                System.err.println("❌ DATABASE CONNECTION FAILED!");
                System.err.println("មូលហេតុ (Error): " + e.getMessage());
                System.err.println("-----------------------------------------");
            }
        };
    }
}