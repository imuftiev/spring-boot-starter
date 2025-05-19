package com.project.app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;

@Getter
@Setter
@ConfigurationProperties(prefix = "log")
public class LogHttpProperties {

    private boolean enabled = true;
    private LogLevel level = LogLevel.INFO;

}
