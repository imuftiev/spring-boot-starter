package com.project.app.config;

import com.project.app.aspect.LogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = "log",
        name = "enabled",
        havingValue = "true")
@EnableConfigurationProperties(LogHttpProperties.class)
public class LogHttpAutoConfiguration {

    @Bean
    public LogAspect logAspect(LogHttpProperties logHttpProperties) {
        return new LogAspect(logHttpProperties);
    }

}
