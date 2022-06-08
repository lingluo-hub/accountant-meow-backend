package com.meow.accountant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 凌洛
 * @Description 记账喵-您身边的会计师，后端Spring Boot项目
 */
@SpringBootApplication
public class AccountantMeowBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountantMeowBackendApplication.class, args);
    }

    @Bean
    public PostgreSqlDialect postgreSqlDialect() {
        return new PostgreSqlDialect();
    }

}
