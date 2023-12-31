package com.example.budgetmanageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BudgetManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetManageServiceApplication.class, args);
    }

}
