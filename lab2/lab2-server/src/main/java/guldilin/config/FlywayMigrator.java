package guldilin.config;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

@Singleton
@Startup
@TransactionManagement(value=TransactionManagementType.BEAN)
public class FlywayMigrator {
    private static final Logger LOGGER = LogManager.getLogger(FlywayMigrator.class);

    @Inject
    private DataSource dataSource;

    /**
     * Run database migrations.
     */
    public void doMigration() {
        LOGGER.info("Start migrations running");
        final Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        LOGGER.info("Migrations finished");
    }

    /**
     * Startup.
     */
    @PostConstruct
    public void init() {
        LOGGER.info("init FlywayMigrator");
        this.doMigration();
    }
}
