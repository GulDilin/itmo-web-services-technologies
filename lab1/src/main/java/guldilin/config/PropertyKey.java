package guldilin.config;

import guldilin.exceptions.ErrorMessages;
import java.io.Serializable;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyKey implements Serializable {
    DB_DRIVER(false, "org.postgresql.Driver", "Database driver", "db-driver"),
    DB_DIALECT(false, "org.hibernate.dialect.PostgreSQLDialect", "Database dialect", "db-dialect"),
    DB_PROTOCOL(false, "jdbc:postgresql", "Database protocol", "db-protocol"),
    DB_HOST(true, null, "Database host", "db-host"),
    DB_NAME(true, null, "Database name", "db-name"),
    DB_PORT(true, null, "Database port", "db-port"),
    DB_USERNAME(true, null, "Database user", "db-user"),
    DB_PASSWORD(true, null, "Database password", "db-password"),
    DB_SHOW_SQL(false, "false", "Show SQL in console", null),
    DB_USE_SQL_COMMENTS(false, "false", "Show SQL comments", null),
    DB_CTX_LOOKUP_PATH(false, "jdbc/tws-db", "Context datasource lookup path for application server", null),
    APP_HOST(false, "http://localhost", "Application standalone server host listener", "host"),
    APP_PORT(false, "8080", "Application standalone server listener port", "port"),
    APP_URL(false, null, "Application url", null),
    ;

    private final Boolean required;
    private final String defaultValue;
    private final String description;
    private final String cmdAlias;

    public String lookupValue() {
        Optional<String> optionalSystemProp = Optional.ofNullable(System.getProperty(this.name()));
        Optional<String> optionalEnvProp = Optional.ofNullable(System.getenv(this.name()));
        if (this.getRequired())
            return optionalSystemProp.orElse(optionalEnvProp.orElseThrow(() ->
                    new IllegalArgumentException(String.format(ErrorMessages.MISSING_REQUIRED_ENV, this.name()))));
        return optionalSystemProp.orElse(optionalEnvProp.orElse(this.getDefaultValue()));
    }
}
