package guldilin.config;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

@Singleton
public final class FlywayMigrator {
    private static final Logger LOGGER = LogManager.getLogger(FlywayMigrator.class);

    @Inject
    private DataSource dataSource;

    /**
     * Run database migrations.
     */
    @PostConstruct
    public void doMigration() {
        LOGGER.info("Start migrations running");
        final Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        LOGGER.info("Migrations finished");
    }
}
