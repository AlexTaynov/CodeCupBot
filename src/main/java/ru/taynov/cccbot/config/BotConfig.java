package ru.taynov.cccbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String name;
    private String token;
}
