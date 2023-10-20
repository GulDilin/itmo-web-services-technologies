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
    DB_DRIVER(false, "org.postgresql.Driver", "Database driver", "db_driver"),
    /**
     * Database dialect.
     */
    DB_DIALECT(false, "org.hibernate.dialect.PostgreSQLDialect", "Database dialect", "db_dialect"),
    /**
     * Database protocol.
     */
    DB_PROTOCOL(false, "jdbc:postgresql", "Database protocol", "db_protocol"),
    /**
     * Database host.
     */
    DB_HOST(true, null, "Database host", "db_host"),
    /**
     * Database name.
     */
    DB_NAME(true, null, "Database name", "db_name"),
    /**
     * Database port.
     */
    DB_PORT(true, null, "Database port", "db_port"),
    /**
     * Database username.
     */
    DB_USERNAME(true, null, "Database user", "db_username"),
    /**
     * Database password.
     */
    DB_PASSWORD(true, null, "Database password", "db_password"),
    /**
     * Database show SQL to output.
     */
    DB_SHOW_SQL(false, "false", "Show SQL in console", null),
    /**
     * Database use sql comments.
     */
    DB_USE_SQL_COMMENTS(false, "false", "Show SQL comments", null),
    /**
     * Database source lookup path.
     */
    DB_JNDI_NAME(false, "java:global/tws_db", "Context datasource lookup JNDI name", null),
    /**
     * Application host.
     */
    APP_HOST(false, "http://localhost", "Application standalone server host listener", "host"),
    /**
     * Application port.
     */
    APP_PORT(false, "8080", "Application standalone server listener port", "port"),
    /**
     * Application startup url.
     */
    APP_URL(false, null, "Application url", null);

    private final Boolean required;
    private final String defaultValue;
    private final String description;
    private final String cmdAlias;

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
        //        String infoText =
        //                optionalSystemProp.map(s -> "Found in System Properties: " + s).orElse("");
        //        infoText = infoText.isEmpty()
        //                ? optionalEnvProp.map(s -> "Found in ENV: " + s).orElse("Value not found")
        //                : "Value not found";
        //        LOGGER.info(String.format("Lookup Value (Default: %s) %s. %s",
        //        this.getDefaultValue(), this.name(), infoText));
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
