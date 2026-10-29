package com.examseating.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url:}")
    private String datasourceUrl;

    @Value("${spring.datasource.password:}")
    private String dbPassword;

    @PostConstruct
    public void validateDatabasePassword() {
        // Skip validation for H2 (test) databases
        if (datasourceUrl != null && datasourceUrl.contains("h2")) {
            return;
        }
        if (dbPassword == null || dbPassword.isBlank()) {
            throw new IllegalStateException(
                "\n\n" +
                "╔══════════════════════════════════════════════════════════════╗\n" +
                "║  ERROR: DB_PASSWORD environment variable is not set!        ║\n" +
                "║                                                            ║\n" +
                "║  Set it in PowerShell before running the application:       ║\n" +
                "║    $env:DB_PASSWORD = \"your_database_password\"              ║\n" +
                "║                                                            ║\n" +
                "║  See README.md for full setup instructions.                ║\n" +
                "╚══════════════════════════════════════════════════════════════╝\n"
            );
        }
    }
}
