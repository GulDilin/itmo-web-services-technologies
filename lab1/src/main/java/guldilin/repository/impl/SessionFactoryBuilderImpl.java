package guldilin.repository.impl;

import guldilin.config.PropertyKey;
import guldilin.entity.City;
import guldilin.exceptions.ErrorMessages;
import guldilin.repository.interfaces.SessionFactoryBuilderA;
import java.util.Optional;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

@NoArgsConstructor
public class SessionFactoryBuilderImpl implements SessionFactoryBuilderA {
    private static final Logger logger = LogManager.getLogger(SessionFactoryBuilderImpl.class);

    private void lookupDataSource() throws Exception {
        InitialContext cxt = Optional.of(new InitialContext())
                .orElseThrow(() -> new Exception("Missing Application Context to search datasource"));
        String lookupPath = Optional.ofNullable(PropertyKey.DB_CTX_LOOKUP_PATH.lookupValue())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(ErrorMessages.MISSING_REQUIRED_ENV, PropertyKey.DB_CTX_LOOKUP_PATH.name())));
        logger.info(String.format("Detected lookup path: %s", lookupPath));
        Optional.ofNullable((DataSource) cxt.lookup(lookupPath))
                .orElseThrow(() -> new Exception("Datasource lookup failed"));
    }

    private Properties generateDataSourceProperties() throws Exception {
        try {
            logger.info("Find datasource by JNDI");
            lookupDataSource();
            Properties settings = new Properties();
            settings.put(Environment.JAKARTA_JTA_DATASOURCE, PropertyKey.DB_CTX_LOOKUP_PATH.lookupValue());
            return settings;
        } catch (Exception e) {
            logger.error(String.format("Error during datasource lookup: %s", e.getMessage()));
            throw e;
        }
    }

    private Properties generateParamsProperties() {
        Properties settings = new Properties();
        settings.put(Environment.JAKARTA_JDBC_DRIVER, PropertyKey.DB_DRIVER.lookupValue());
        settings.put(Environment.DIALECT, PropertyKey.DB_DIALECT.lookupValue());
        settings.put(
                Environment.JAKARTA_JDBC_URL,
                String.format(
                        "%s://%s:%s/%s",
                        PropertyKey.DB_PROTOCOL.lookupValue(),
                        PropertyKey.DB_HOST.lookupValue(),
                        PropertyKey.DB_PORT.lookupValue(),
                        PropertyKey.DB_NAME.lookupValue()));
        settings.put(Environment.JAKARTA_JDBC_USER, PropertyKey.DB_USERNAME.lookupValue());
        settings.put(Environment.JAKARTA_JDBC_PASSWORD, PropertyKey.DB_PASSWORD.lookupValue());
        settings.put(Environment.SHOW_SQL, PropertyKey.DB_SHOW_SQL.lookupValue());
        settings.put(Environment.USE_SQL_COMMENTS, PropertyKey.DB_USE_SQL_COMMENTS.lookupValue());
        settings.put(Environment.HBM2DDL_AUTO, "validate");
        logger.info(String.format("Loaded database properties: %s", settings));
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
