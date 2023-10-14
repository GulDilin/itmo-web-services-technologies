package guldilin.config;

import guldilin.entity.City;
import guldilin.exceptions.ErrorMessages;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;
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
final class SessionFactoryProvider {
    private static final Logger LOGGER = LogManager.getLogger(SessionFactoryProvider.class);

    /**
     * Add entities to configuration.
     *
     * @param configuration Hibernate Configuration (Injected)
     * @param entities Entities classes to add
     */
    private void addAnnotatedClasses(final Configuration configuration, final Iterable<Class<?>> entities) {
        final SortedSet<String> entityClasses = new TreeSet<>();
        for (Class<?> klass : entities) {
            configuration.addAnnotatedClass(klass);
            entityClasses.add(klass.getCanonicalName());
        }
        LOGGER.info("Entity classes: {}", entityClasses);
    }

    /**
     * Produces Configuration for Hibernate.
     *
     * @return Configuration.
     */
    @Produces
    @Singleton
    public Configuration provideHibernateConfiguration() {
        final Configuration configurationLocal = new Configuration();
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
        configurationLocal.setProperties(settings);
        addAnnotatedClasses(configurationLocal, Stream.of(City.class).collect(Collectors.toList()));
        return configurationLocal;
    }

    /**
     * Produces SessionFactory.
     *
     * @param configuration Hibernate Configuration (Injected)
     * @param connectionProvider Hibernate Connection Provider (Injected)
     * @return SessionFactory
     */
    @Produces
    @Singleton
    public SessionFactory provideSessionFactory(
            final Configuration configuration, final ConnectionProvider connectionProvider) {
        final ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .addService(ConnectionProvider.class, connectionProvider)
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(registry);
    }

    /**
     * Lookup for DataSource in JNDI by Lookup path from properties or env.
     *
     * @throws Exception if datasource not found.
     */
    private void lookupDataSource() throws Exception {
        InitialContext cxt = Optional.of(new InitialContext())
                .orElseThrow(() -> new Exception("Missing Application Context to search datasource"));
        String lookupPath = Optional.ofNullable(PropertyKey.DB_CTX_LOOKUP_PATH.lookupValue())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(ErrorMessages.MISSING_REQUIRED_ENV, PropertyKey.DB_CTX_LOOKUP_PATH.name())));
        LOGGER.info(String.format("Detected lookup path: %s", lookupPath));
        Optional.ofNullable((DataSource) cxt.lookup(lookupPath))
                .orElseThrow(() -> new Exception("Datasource lookup failed"));
    }

    /**
     * Produces DataSource.
     * Try to lookup in JNDI first. If not found use env or properties.
     *
     * @param configuration Hibernate Configuration (Injected)
     * @return DataSource
     */
    @Produces
    @Singleton
    public DataSource provideDataSource(final Configuration configuration) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(configuration.getProperty(Environment.URL));
        dataSource.setUser(configuration.getProperty(Environment.USER));
        dataSource.setPassword(configuration.getProperty(Environment.PASS));
        return dataSource;
    }

    /**
     * Produces ConnectionProvider.
     *
     * @param configuration Hibernate Configuration (Injected)
     * @param dataSource Datasource (Injected)
     * @return ConnectionProvider
     */
    @Produces
    @Singleton
    public ConnectionProvider provideConnectionProvider(
            final Configuration configuration, final DataSource dataSource) {
        DatasourceConnectionProviderImpl connectionProvider = new DatasourceConnectionProviderImpl();
        connectionProvider.setDataSource(dataSource);

        Map<String, Object> configValues = new HashMap<>();
        configValues.put(Environment.USER, configuration.getProperty(Environment.USER));
        configValues.put(Environment.PASS, configuration.getProperty(Environment.PASS));

        connectionProvider.configure(configValues);
        return connectionProvider;
    }

    private Properties generateDataSourceProperties() throws Exception {
        try {
            LOGGER.info("Find datasource by JNDI");
            lookupDataSource();
            Properties settings = new Properties();
            settings.put(Environment.JAKARTA_JTA_DATASOURCE, PropertyKey.DB_CTX_LOOKUP_PATH.lookupValue());
            return settings;
        } catch (Exception e) {
            LOGGER.error(String.format("Error during datasource lookup: %s", e.getMessage()));
            throw e;
        }
    }
}
