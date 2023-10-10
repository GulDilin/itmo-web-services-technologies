package guldilin.repository.impl;

import guldilin.config.PropertiesKeys;
import guldilin.config.PropertyKeyInfo;
import guldilin.entity.City;
import guldilin.exceptions.ErrorMessages;
import guldilin.repository.interfaces.SessionFactoryBuilder;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

@ApplicationScoped
public class SessionFactoryBuilderImpl implements SessionFactoryBuilder {
    Map<PropertiesKeys, PropertyKeyInfo> parameters;
    private static Logger logger = LogManager.getLogger(SessionFactoryBuilderImpl.class);

    public SessionFactoryBuilderImpl() {
        this.parameters = Stream.of(
                        new PropertyKeyInfo(PropertiesKeys.DB_CTX_LOOKUP_PATH, false, null),
                        new PropertyKeyInfo(PropertiesKeys.DB_DRIVER, false, "org.postgresql.Driver"),
                        new PropertyKeyInfo(
                                PropertiesKeys.DB_DIALECT, false, "org.hibernate.dialect.PostgreSQLDialect"),
                        new PropertyKeyInfo(PropertiesKeys.DB_PROTOCOL, false, "jdbc:postgresql"),
                        new PropertyKeyInfo(PropertiesKeys.DB_HOST, true, null),
                        new PropertyKeyInfo(PropertiesKeys.DB_PORT, true, null),
                        new PropertyKeyInfo(PropertiesKeys.DB_NAME, true, null),
                        new PropertyKeyInfo(PropertiesKeys.DB_USERNAME, true, null),
                        new PropertyKeyInfo(PropertiesKeys.DB_PASSWORD, true, null),
                        new PropertyKeyInfo(PropertiesKeys.DB_SHOW_SQL, false, "false"),
                        new PropertyKeyInfo(PropertiesKeys.DB_USE_SQL_COMMENTS, false, "false"))
                .collect(Collectors.toMap(PropertyKeyInfo::getKey, data -> data));
    }

    private static String getPropValue(PropertyKeyInfo info) {
        Optional<String> optionalSystemProp =
                Optional.ofNullable(System.getProperty(info.getKey().name()));
        Optional<String> optionalEnvProp =
                Optional.ofNullable(System.getenv(info.getKey().name()));
        if (info.getRequired())
            return optionalSystemProp.orElse(
                    optionalEnvProp.orElseThrow(() -> new IllegalArgumentException(String.format(
                            ErrorMessages.MISSING_REQUIRED_ENV, info.getKey().name()))));
        return optionalSystemProp.orElse(optionalEnvProp.orElse(info.getDefaultValue()));
    }

    private void lookupDataSource() throws Exception {
        InitialContext cxt = Optional.of(new InitialContext())
                .orElseThrow(() -> new Exception("Missing Application Context to search datasource"));
        String lookupPath = Optional.ofNullable(getPropValue(this.parameters.get(PropertiesKeys.DB_CTX_LOOKUP_PATH)))
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(ErrorMessages.MISSING_REQUIRED_ENV, PropertiesKeys.DB_CTX_LOOKUP_PATH.name())));
        logger.info(String.format("Detected lookup path: %s", lookupPath));
        Optional.ofNullable((DataSource) cxt.lookup(lookupPath))
                .orElseThrow(() -> new Exception("Datasource lookup failed"));
    }

    private Properties generateDataSourceProperties() throws Exception {
        try {
            logger.info("Find datasource by JNDI");
            lookupDataSource();
            Properties settings = new Properties();
            settings.put(
                    Environment.JAKARTA_JTA_DATASOURCE,
                    getPropValue(this.parameters.get(PropertiesKeys.DB_CTX_LOOKUP_PATH)));
            return settings;
        } catch (Exception e) {
            logger.error(String.format("Error during datasource lookup: %s", e.getMessage()));
            throw e;
        }
    }

    private Properties generateParamsProperties() {
        Properties settings = new Properties();
        settings.put(Environment.JAKARTA_JDBC_DRIVER, getPropValue(this.parameters.get(PropertiesKeys.DB_DRIVER)));
        settings.put(Environment.DIALECT, getPropValue(this.parameters.get(PropertiesKeys.DB_DIALECT)));
        settings.put(
                Environment.JAKARTA_JDBC_URL,
                String.format(
                        "%s://%s:%s/%s",
                        getPropValue(this.parameters.get(PropertiesKeys.DB_PROTOCOL)),
                        getPropValue(this.parameters.get(PropertiesKeys.DB_HOST)),
                        getPropValue(this.parameters.get(PropertiesKeys.DB_PORT)),
                        getPropValue(this.parameters.get(PropertiesKeys.DB_NAME))));
        settings.put(Environment.JAKARTA_JDBC_USER, getPropValue(this.parameters.get(PropertiesKeys.DB_USERNAME)));
        settings.put(Environment.JAKARTA_JDBC_PASSWORD, getPropValue(this.parameters.get(PropertiesKeys.DB_PASSWORD)));
        settings.put(Environment.SHOW_SQL, getPropValue(this.parameters.get(PropertiesKeys.DB_SHOW_SQL)));
        settings.put(
                Environment.USE_SQL_COMMENTS, getPropValue(this.parameters.get(PropertiesKeys.DB_USE_SQL_COMMENTS)));
        settings.put(Environment.HBM2DDL_AUTO, "validate");
        return settings;
    }

    @Override
    public SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        Environment.getProperties();
        Properties settings;
        try {
            settings = generateDataSourceProperties();
        } catch (Exception e) {
            settings = generateParamsProperties();
        }
        configuration.setProperties(settings);

        configuration.addAnnotatedClass(City.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
