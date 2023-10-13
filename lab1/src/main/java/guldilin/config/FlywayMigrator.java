package guldilin.config;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

@Singleton
public class FlywayMigrator {
    private static final Logger logger = LogManager.getLogger(FlywayMigrator.class);

    @Inject
    DataSource dataSource;

    @PostConstruct
    public void doMigration() {
        logger.info("Start migrations running");
        final Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        logger.info("Migrations finished");
    }
}
