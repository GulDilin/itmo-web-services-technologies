package guldilin.config;

import guldilin.entity.City;
import guldilin.exceptions.ErrorMessages;
import jakarta.annotation.PostConstruct;
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
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.ServiceRegistry;
import org.postgresql.ds.PGSimpleDataSource;

enum DatasourceMode {
    ARGUMENTS,
    JNDI_LOOKUP,
}

@NoArgsConstructor
@ApplicationScoped
final class SessionFactoryProvider {
    private static final Logger LOGGER = LogManager.getLogger(SessionFactoryProvider.class);
    private DataSource jndiDataSource;
    private DatasourceMode datasourceMode;

    @PostConstruct
    private void init() {
        LOGGER.info("Init SessionFactoryProvider");
        this.datasourceMode = DatasourceMode.ARGUMENTS;
        try {
            LOGGER.info("Start datasource lookup");
            this.jndiDataSource = lookupDataSource();
            this.datasourceMode = DatasourceMode.JNDI_LOOKUP;
            LOGGER.info("Datasource successfully found");
        } catch (Exception e) {
            LOGGER.warn("Datasource in context did not found");
        }
    }

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

    private Properties generatePropertiesByParams() {
        final Properties settings = new Properties();
        settings.put(AvailableSettings.DIALECT, PropertyKey.DB_DIALECT.lookupValue());
        settings.put(
                AvailableSettings.URL,
                String.format(
                        "%s://%s:%s/%s",
                        PropertyKey.DB_PROTOCOL.lookupValue(),
                        PropertyKey.DB_HOST.lookupValue(),
                        PropertyKey.DB_PORT.lookupValue(),
                        PropertyKey.DB_NAME.lookupValue()));
        settings.put(AvailableSettings.USER, PropertyKey.DB_USERNAME.lookupValue());
        settings.put(AvailableSettings.PASS, PropertyKey.DB_PASSWORD.lookupValue());
        settings.put(AvailableSettings.USE_GET_GENERATED_KEYS, "true");
        settings.put(AvailableSettings.GENERATE_STATISTICS, "true");
        settings.put(AvailableSettings.ORDER_UPDATES, "true");
        settings.put(AvailableSettings.ORDER_INSERTS, "true");
        settings.put(AvailableSettings.SHOW_SQL, PropertyKey.DB_SHOW_SQL.lookupValue());
        settings.put(AvailableSettings.USE_SQL_COMMENTS, PropertyKey.DB_USE_SQL_COMMENTS.lookupValue());
        return settings;
    }

    private Properties generatePropertiesByLookup() {
        final Properties settings = new Properties();
        settings.put(AvailableSettings.DATASOURCE, PropertyKey.DB_JNDI_NAME.lookupValue());
        return settings;
    }

    /**
     * Produces Configuration for Hibernate.
     *
     * @return Configuration.
     */
    @Produces
    @Singleton
    public Configuration provideHibernateConfiguration() {
        LOGGER.info("provideHibernateConfiguration");
        final Configuration configurationLocal = new Configuration();
        final Properties settings;
        if (this.datasourceMode.equals(DatasourceMode.JNDI_LOOKUP)) {
            settings = generatePropertiesByLookup();
        } else {
            settings = generatePropertiesByParams();
        }
        configurationLocal.setProperties(settings);
        addAnnotatedClasses(configurationLocal, Stream.of(City.class).collect(Collectors.toList()));
        return configurationLocal;
    }

    /**
     * Lookup for DataSource in JNDI by Lookup path from properties or env.
     *
     * @return Found DataSource
     * @throws Exception if datasource not found.
     */
    private DataSource lookupDataSource() throws Exception {
        InitialContext cxt = Optional.of(new InitialContext())
                .orElseThrow(() -> new Exception("Missing Application Context to search datasource"));
        String lookupPath = Optional.ofNullable(PropertyKey.DB_JNDI_NAME.lookupValue())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(ErrorMessages.MISSING_REQUIRED_ENV, PropertyKey.DB_JNDI_NAME.name())));
        return Optional.ofNullable((DataSource) cxt.lookup(lookupPath))
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
        LOGGER.info("provideDataSource");
        if (this.datasourceMode.equals(DatasourceMode.JNDI_LOOKUP)) {
            return this.jndiDataSource;
        }
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(configuration.getProperty(AvailableSettings.URL));
        dataSource.setUser(configuration.getProperty(AvailableSettings.USER));
        dataSource.setPassword(configuration.getProperty(AvailableSettings.PASS));
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
        LOGGER.info("provideConnectionProvider");
        DatasourceConnectionProviderImpl connectionProvider = new DatasourceConnectionProviderImpl();
        connectionProvider.setDataSource(dataSource);

        Map<String, Object> configValues = new HashMap<>();
        if (this.datasourceMode.equals(DatasourceMode.JNDI_LOOKUP)) {
            configValues.put(AvailableSettings.DATASOURCE, configuration.getProperty(AvailableSettings.DATASOURCE));
        } else {
            configValues.put(AvailableSettings.USER, configuration.getProperty(AvailableSettings.USER));
            configValues.put(AvailableSettings.PASS, configuration.getProperty(AvailableSettings.PASS));
        }

        connectionProvider.configure(configValues);
        return connectionProvider;
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
        LOGGER.info("provideSessionFactory");
        final ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .addService(ConnectionProvider.class, connectionProvider)
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(registry);
    }
}
