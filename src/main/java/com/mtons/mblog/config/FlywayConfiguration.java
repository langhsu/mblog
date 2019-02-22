package com.mtons.mblog.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author : landy
 */
@Configuration
@ConditionalOnProperty(name = "spring.jpa.database", havingValue = "mysql", matchIfMissing = true)
public class FlywayConfiguration {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void migrate() {
        Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:scripts/migration")
                .baselineOnMigrate(true)
                .load()
                .migrate();
    }
}
