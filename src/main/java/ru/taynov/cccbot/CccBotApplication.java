package ru.taynov.cccbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication
public class CccBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CccBotApplication.class, args);
    }

}
