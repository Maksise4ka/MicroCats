package com.maksise4ka.microcats.cats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@EnableJpaRepositories("com.maksise4ka.microcats.dao.daos")
@EntityScan("com.maksise4ka.microcats.dao.entities")
@ComponentScan("com.maksise4ka.microcats.*")
@SpringBootApplication
public class CatsMain {
    public static void main(String[] args) {
        SpringApplication.run(CatsMain.class, args);
    }
}
