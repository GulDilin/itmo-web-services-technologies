package guldilin.repository.impl;

import guldilin.config.PropertyKey;
import guldilin.entity.City;
import guldilin.exceptions.ErrorMessages;
import guldilin.repository.interfaces.SessionFactoryProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.ServiceRegistry;
import org.postgresql.ds.PGSimpleDataSource;

@NoArgsConstructor
@ApplicationScoped
public class SessionFactoryBuilderImpl implements SessionFactoryProvider {
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

    private Configuration provideHibernateConfiguration() {
        final Configuration configuration = new Configuration();
        final Properties settings = new Properties();
        settings.put(Environment.DIALECT, PropertyKey.DB_DIALECT.lookupValue());
        settings.put(
                Environment.URL,
                String.format(
                        "%s://%s:%s/%s",
                        PropertyKey.DB_PROTOCOL.lookupValue(),
                        PropertyKey.DB_HOST.lookupValue(),
                        PropertyKey.DB_PORT.lookupValue(),
                        PropertyKey.DB_NAME.lookupValue()));
        settings.put(Environment.USER, PropertyKey.DB_USERNAME.lookupValue());
        settings.put(Environment.PASS, PropertyKey.DB_PASSWORD.lookupValue());
        settings.put(Environment.USE_GET_GENERATED_KEYS, "true");
        settings.put(Environment.GENERATE_STATISTICS, "true");
        settings.put(Environment.ORDER_UPDATES, "true");
        settings.put(Environment.ORDER_INSERTS, "true");
        settings.put(Environment.SHOW_SQL, PropertyKey.DB_SHOW_SQL.lookupValue());
        settings.put(Environment.USE_SQL_COMMENTS, PropertyKey.DB_USE_SQL_COMMENTS.lookupValue());
        settings.put(Environment.HBM2DDL_DATABASE_ACTION, "validate");
        configuration.setProperties(settings);
        addAnnotatedClasses(configuration, Stream.of(City.class).collect(Collectors.toList()));
        return configuration;
    }

    @Override
    @Produces
    public SessionFactory provideSessionFactory() {
        Configuration configuration = provideHibernateConfiguration();
        DataSource dataSource = provideDataSource(configuration);
        ConnectionProvider connectionProvider = provideConnectionProvider(dataSource, configuration);

        final ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .addService(ConnectionProvider.class, connectionProvider)
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(registry);
    }

    public DataSource provideDataSource(Configuration configuration) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(configuration.getProperty(Environment.URL));
        dataSource.setUser(configuration.getProperty(Environment.USER));
        dataSource.setPassword(configuration.getProperty(Environment.PASS));
        return dataSource;
    }

    public Properties provideHibernateProperties() {
        Properties settings = new Properties();
        settings.put(Environment.SHOW_SQL, PropertyKey.DB_SHOW_SQL.lookupValue());
        settings.put(Environment.USE_SQL_COMMENTS, PropertyKey.DB_USE_SQL_COMMENTS.lookupValue());
        settings.put(Environment.HBM2DDL_DATABASE_ACTION, "validate");
        return settings;
    }

    public ConnectionProvider provideConnectionProvider(DataSource dataSource, Configuration configuration) {
        DatasourceConnectionProviderImpl connectionProvider = new DatasourceConnectionProviderImpl();
        connectionProvider.setDataSource(dataSource);

        Map<String, Object> configValues = new HashMap<>();
        configValues.put(Environment.USER, configuration.getProperty(Environment.USER));
        configValues.put(Environment.PASS, configuration.getProperty(Environment.PASS));

        connectionProvider.configure(configValues);
        return connectionProvider;
    }

    private void addAnnotatedClasses(Configuration configuration, Iterable<Class<?>> entities) {
        final SortedSet<String> entityClasses = new TreeSet<>();
        for (Class<?> klass : entities) {
            configuration.addAnnotatedClass(klass);
            entityClasses.add(klass.getCanonicalName());
        }
        logger.info("Entity classes: {}", entityClasses);
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
}
