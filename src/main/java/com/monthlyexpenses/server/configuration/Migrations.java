package com.monthlyexpenses.server.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Migrations {

    @Bean
    public void migrate() {
        Flyway flyway = Flyway.configure().dataSource(
                "jdbc:postgresql://localhost:5432/monthlyexpensesdb",
                "postgres",
                "root"
        ).load();

        flyway.migrate();
    }
}
