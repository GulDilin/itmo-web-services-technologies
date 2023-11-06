package guldilin.config;

import guldilin.exceptions.ErrorMessages;
import java.io.Serializable;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
@AllArgsConstructor
public enum PropertyKey implements Serializable {
    /**
     * Database driver class.
     */
    DB_DRIVER(false, "org.postgresql.Driver", "Database driver"),
    /**
     * Database dialect.
     */
    DB_DIALECT(false, "org.hibernate.dialect.PostgreSQLDialect", "Database dialect"),
    /**
     * Database protocol.
     */
    DB_PROTOCOL(false, "jdbc:postgresql", "Database protocol"),
    /**
     * Database host.
     */
    DB_HOST(true, null, "Database host"),
    /**
     * Database port.
     */
    DB_PORT(true, null, "Database port"),
    /**
     * Database name.
     */
    DB_NAME(true, null, "Database name"),
    /**
     * Database username.
     */
    DB_USERNAME(true, null, "Database user"),
    /**
     * Database password.
     */
    DB_PASSWORD(true, null, "Database password"),
    /**
     * Database show SQL to output.
     */
    DB_SHOW_SQL(false, "false", "Show SQL in console"),
    /**
     * Database use sql comments.
     */
    DB_USE_SQL_COMMENTS(false, "false", "Show SQL comments"),
    /**
     * Database source lookup path.
     */
    DB_JNDI_NAME(false, "java:global/tws_db", "Context datasource lookup JNDI name"),
    /**
     * Application host.
     */
    APP_HOST(false, "http://localhost", "Application standalone server host listener"),
    /**
     * Application port.
     */
    APP_PORT(false, "8080", "Application standalone server listener port"),
    /**
     * Application startup url.
     */
    APP_URL(false, null, "Application url");

    private final Boolean required;
    private final String defaultValue;
    private final String description;

    private static final Logger LOGGER = LogManager.getLogger(PropertyKey.class);

    /**
     * Lookup for value by system properties or env vars.
     * Priority env -> system property
     *
     * @return value
     */
    public String lookupValue() {
        Optional<String> optionalSystemProp = Optional.ofNullable(System.getProperty(this.name()));
        Optional<String> optionalEnvProp = Optional.ofNullable(System.getenv(this.name()));
        LOGGER.info(String.format(
                "Lookup Value (Default: %s) %s [SYS] %s [ENV] %s",
                this.getDefaultValue(), this.name(), optionalSystemProp, optionalEnvProp));

        if (this.getRequired()) {
            return optionalSystemProp.orElseGet(() -> optionalEnvProp.orElseThrow(() ->
                    new IllegalArgumentException(String.format(ErrorMessages.MISSING_REQUIRED_ENV, this.name()))));
        }
        return optionalSystemProp.orElseGet(() -> optionalEnvProp.orElse(this.getDefaultValue()));
    }
}
